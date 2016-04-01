package com.shouyingbao.pbs.service.impl;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shouyingbao.pbs.Exception.AliPayException;
import com.shouyingbao.pbs.Exception.WeixinException;
import com.shouyingbao.pbs.common.pay.weixin.model.RefundQueryResData;
import com.shouyingbao.pbs.common.pay.weixin.model.ScanQueryResData;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.constants.Constants;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.service.*;
import com.shouyingbao.pbs.unit.AliPayUnit;
import com.shouyingbao.pbs.unit.IdGenUnit;
import com.shouyingbao.pbs.unit.WeixinPayUnit;
import com.shouyingbao.pbs.vo.PaymentBillVO;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C)
 * kejun
 * 2016/3/7 16:01
 **/
@Service
public class PaymentBillServiceImpl extends BaseServiceImpl implements PaymentBillService {

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.PaymentBillMapper";

    private static  final Logger LOGGER = LoggerFactory.getLogger(PaymentBillServiceImpl.class);

    @Autowired
    WeixinPayUnit weixinPayUnit;

    @Autowired
    AliPayUnit aliPayUnit;

    @Autowired
    WeixinMchService weixinMchService;

    @Autowired
    IdGenUnit idGenUnit;

    @Autowired
    PaymentEventService paymentEventService;

    @Autowired
    UserService userService;

    @Autowired
    MchShopService mchShopService;

    @Override
    public List<PaymentBillVO> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        if(currentPage != null && pageSize != null) {
            map.put("currentPage", (currentPage - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }

    @Override
    public ResponseData weixinScanPay(Integer userId, String authCode, Integer totalFee, String deviceInfo, Integer tradeType){
        LOGGER.info("微信扫码支付,userId={},authCode={},totalFee={},deviceInfo={},tradeType={}", userId, authCode, totalFee, deviceInfo, tradeType);
        ResponseData responseData;
        try{
            User user = userService.selectById(userId);
            if(user == null || ConstantEnum.USER_IS_EMPLOYEE_0.getCodeByte().equals(user.getIsEmployee())){
                throw new WeixinException(ConstantEnum.EXCEPTION_MCH_SHOP_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_MCH_SHOP_NOT_EXIST.getValueStr());
            }
            String body = ConstantEnum.WEIXIN_SCAN_PAY_BODY.getCodeStr();
            MchShop mchShop = mchShopService.selectById(user.getShopId());
            if(mchShop != null){
                body = mchShop.getName();
            }
            WeixinPayVO weixinPayVO = new WeixinPayVO();
            weixinPayVO.setAuthCode(authCode);
            weixinPayVO.setTotalFee(totalFee);
            weixinPayVO.setDeviceInfo(deviceInfo);
            weixinPayVO.setBody(body);
            weixinPayVO.setShopId(user.getShopId());
            weixinPayVO.setWeixinPayType(tradeType);
            weixinPayVO.setOrderNo(idGenUnit.getOrderNo("0"));
            WeixinMch weixinMch = weixinMchService.selectByShopId(weixinPayVO.getShopId());
            if (weixinMch == null) {
                throw new WeixinException(ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getValueStr());
            }
            //初始化支付账单数据
            PaymentBill paymentBill = initWeixinPayBill(weixinPayVO, weixinMch.getId(), userId, user.getId());
            responseData = weixinPayUnit.scanPay(weixinPayVO,weixinMch);
            if("0".equals(responseData.getMeta().getErrno())){//扫码支付成功
                ScanQueryResData scanQueryResData = weixinPayUnit.scanPayQueryOrder(null,weixinPayVO.getOrderNo(),weixinMch.getId());
              //更新支付状态前再次查询确认
                if ("SUCCESS".equals(scanQueryResData.getReturn_code()) && "SUCCESS".equals(scanQueryResData.getResult_code()) && "SUCCESS".equals(scanQueryResData.getTrade_state())) {
                    updateStatusAndInsertEvent(paymentBill, scanQueryResData.getTransaction_id(), scanQueryResData.getTrade_state(), scanQueryResData.getTotal_fee(), "", "", scanQueryResData.getOpenid(), "");
                    responseData =  ResponseData.success(paymentBill);
                }

            }
        }catch (WeixinException e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            responseData = ResponseData.failure(e.getCode(), e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            responseData =  ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
        }
        return responseData;
    }

    @Override
    public ResponseData weixinScanRefund(String orderNo, Integer userId) {
        LOGGER.info("扫码支付退款,orderNo={},userId={}",orderNo,userId);
        ResponseData responseData;
        try {
            PaymentBill paymentBill = selectByOrderNoAndTradeType(orderNo,ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte(),ConstantEnum.PAY_CHANNEL_1.getCodeByte(),ConstantEnum.PAY_STATUS_2.getCodeByte());
            PaymentBill refundPaymentBill = initRefundBill(paymentBill,ConstantEnum.PAY_CHANNEL_1.getCodeByte(),ConstantEnum.PAY_TYPE_1.getCodeByte(),userId);
            //发起退款
            weixinPayUnit.weixinRefund(paymentBill.getOrderNo(), refundPaymentBill.getPayAmount(), refundPaymentBill.getPayAmount(), refundPaymentBill.getRefundNo(), refundPaymentBill.getMchId());
            //退款结果查询
            RefundQueryResData refundQueryResData = weixinPayUnit.refundQuery(null, paymentBill.getOrderNo(), null, paymentBill.getMchId());
            if (Constants.RESULT.SUCCESS.equals(refundQueryResData.getReturn_code()) && Constants.RESULT.SUCCESS.equals(refundQueryResData.getResult_code()) &&(
                    ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr().equals(refundQueryResData.getRefund_status_0())
                            || ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(refundQueryResData.getRefund_status_0()))) {// 退款申请成功后查询退款结果
                responseData =  ResponseData.success(refundPaymentBill);
                updateStatusAndInsertEvent(refundPaymentBill,refundQueryResData.getTransaction_id(),refundQueryResData.getRefund_status_0(),Integer.valueOf(refundQueryResData.getRefund_fee_0()),"","","",refundQueryResData.getOut_refund_no_0());
            } else {
                LOGGER.info("退款失败 refundQueryResData={}", refundQueryResData);
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), !StringUtils.isEmpty(refundQueryResData.getErr_code_des()) ? refundQueryResData.getErr_code_des() : ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
            }
            return responseData;
        }catch (WeixinException e){
            responseData = ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
        }
        return  responseData;
    }

    @Override
    public ResponseData aliScanPay(Integer userId, String authCode, Integer totalFee, String deviceInfo) {
        ResponseData responseData;
       try{
           Integer shopId = 1;
           BigDecimal totalAmount = new BigDecimal(totalFee).divide(new BigDecimal(100),RoundingMode.HALF_DOWN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
           WeixinMch weixinMch = weixinMchService.selectByShopId(shopId);
           //初始化支付账单数据
           PaymentBill paymentBill = initAliPayBill(idGenUnit.getOrderNo("0"), weixinMch.getId(), ConstantEnum.ALI_SCAN_PAY_BODY.getCodeStr(),totalFee, userId, shopId);
           responseData = aliPayUnit.scanPay(paymentBill.getOrderTitle(),totalAmount.doubleValue(),authCode,"","",
                   userId.toString(),shopId.toString(), "5m");
           if("0".equals(responseData.getMeta().getErrno())){//扫码支付成功
               AlipayTradeQueryResponse response = aliPayUnit.scanPayQuery(paymentBill.getOrderNo());
               //更新支付状态前再次查询确认
               if ("SUCCESS".equals(response.getTradeStatus()) || "TRADE_CLOSED".equals(response.getTradeStatus())) {
                   BigDecimal resultTotalAmount = new BigDecimal(response.getTotalAmount()).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_DOWN);
                   updateStatusAndInsertEvent(paymentBill, response.getTradeNo(), response.getTradeStatus(), resultTotalAmount.intValue(), response.getBuyerUserId(), response.getBuyerLogonId(), response.getOpenId(),"");
                   responseData =  ResponseData.success(paymentBill);
               }

           }
       }catch (AliPayException e){
           LOGGER.error(e.getMessage());
           e.printStackTrace();
           responseData = ResponseData.failure(ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getValueStr());

       }
        return responseData;
    }

    @Override
    public ResponseData aliScanRefund(String orderNo, Integer userId) {
        ResponseData responseData;
        PaymentBill paymentBill = selectByOrderNoAndTradeType(orderNo, ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte(),ConstantEnum.PAY_CHANNEL_0.getCodeByte(),ConstantEnum.PAY_STATUS_2.getCodeByte());
        PaymentBill refundPaymentBill = initRefundBill(paymentBill, ConstantEnum.PAY_CHANNEL_0.getCodeByte(), ConstantEnum.PAY_TYPE_0.getCodeByte(),userId);
        BigDecimal totalAmount = new BigDecimal(paymentBill.getPayAmount()).divide(new BigDecimal(100), RoundingMode.HALF_DOWN).setScale(0, BigDecimal.ROUND_HALF_DOWN);
        responseData = aliPayUnit.scanPayRefund(orderNo,totalAmount.doubleValue(),"退款",String.valueOf(paymentBill.getShopId()));
        if("0".equals(responseData.getMeta().getErrno())){//扫码支付成功
            AlipayTradeQueryResponse response = aliPayUnit.scanPayQuery(paymentBill.getOrderNo());
            //更新支付状态前再次查询确认
            if ("SUCCESS".equals(response.getTradeStatus()) || "TRADE_CLOSED".equals(response.getTradeStatus())) {
                BigDecimal resultTotalAmount = new BigDecimal(response.getTotalAmount()).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_DOWN);
                updateStatusAndInsertEvent(refundPaymentBill, response.getTradeNo(), response.getTradeStatus(), resultTotalAmount.intValue(), response.getBuyerUserId(), response.getBuyerLogonId(), response.getOpenId(),"");
                responseData =  ResponseData.success(paymentBill);
            }
        }
        return responseData;
    }

    @Override
    public void insert(PaymentBill paymentBill) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", paymentBill);
    }

    @Override
    public void update(PaymentBill paymentBill) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", paymentBill);
    }

    @Override
    public PaymentBill selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public PaymentBill selectByOrderNoAndTradeType(String orderNo,byte tradeType,byte payChannel,byte status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("tradeType", tradeType);
        map.put("payChannel", payChannel);
        map.put("status", status);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByOrderNoAndTradeType",map);
    }

    /**
     * 初始化微信支付账单数据
     * @param weixinPayVO 支付请求对象
     * @param mchId 商户主键
     * @param userId 用户ID
     * @param shopId 店铺id
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initWeixinPayBill(WeixinPayVO weixinPayVO, Integer mchId, Integer shopId, Integer userId){
        PaymentBill paymentBill = PaymentBill.initBill(weixinPayVO.getOrderNo(), null, mchId, weixinPayVO.getBody(),weixinPayVO.getTotalFee(),shopId,userId,ConstantEnum.PAY_CHANNEL_1.getCodeByte(),ConstantEnum.PAY_TYPE_1.getCodeByte(),
                ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte());
        insert(paymentBill);
        return paymentBill;
    }

    /**
     * 初始化支付宝支付账单数据
     * @param orderNo 订单号
     * @param mchId 商户主键
     * @param userId 用户ID
     * @param shopId 店铺id
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initAliPayBill(String orderNo,Integer mchId, String body,Integer totalFee, Integer shopId, Integer userId){
        PaymentBill paymentBill = PaymentBill.initBill(orderNo, null, mchId, body,totalFee,shopId,userId,ConstantEnum.PAY_CHANNEL_0.getCodeByte(),ConstantEnum.PAY_TYPE_0.getCodeByte(),
                ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte());
        insert(paymentBill);
        return paymentBill;
    }



    /**
     * 初始化支付账单数据
     * @param paymentBill 退款请求对象
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initRefundBill(PaymentBill paymentBill,byte payChannel,byte payType,Integer userId){
        PaymentBill refundPaymentBill = PaymentBill.initBill(paymentBill.getOrderNo(),idGenUnit.getOrderNo("1"), paymentBill.getMchId(), paymentBill.getOrderTitle(),paymentBill.getPayAmount(),paymentBill.getShopId(),userId,payChannel,payType,
                ConstantEnum.PAY_TRADE_TYPE_1.getCodeByte());
        insert(refundPaymentBill);
        return refundPaymentBill;
    }


    /**
     * 支付(退款)成功后处理
     * @param paymentBill 支付单
     * @param tradeNo 第三方交易流水号
     * @param result 支付结果
     * @param totalFee 金额
     * @param buyerId 支付宝买家id
     * @param buyerEmail 支付宝买家账号
     * @param openId 微信买家id
     */
    private void updateStatusAndInsertEvent(PaymentBill paymentBill, String tradeNo, String result, Integer totalFee, String buyerId, String buyerEmail, String openId,String refundNo){
        paymentBill.setStatus(ConstantEnum.PAY_STATUS_2.getCodeByte());
        paymentBill.setFinishAt(DateUtil.getCurrDateTime());
        update(paymentBill);
        paymentEventService.insertEvent(paymentBill.getOrderNo(), paymentBill.getId(), tradeNo, result, totalFee,
                buyerId, buyerEmail, openId,refundNo);
    }


}
