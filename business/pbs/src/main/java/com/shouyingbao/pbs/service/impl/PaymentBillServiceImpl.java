package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.Exception.WeixinException;
import com.shouyingbao.pbs.common.pay.weixin.model.RefundQueryResData;
import com.shouyingbao.pbs.common.pay.weixin.model.ScanQueryResData;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.constants.Constants;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.service.PaymentEventService;
import com.shouyingbao.pbs.service.WeixinMchService;
import com.shouyingbao.pbs.unit.IdGenUnit;
import com.shouyingbao.pbs.unit.WeixinPayUnit;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (C)
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
    WeixinMchService weixinMchService;

    @Autowired
    IdGenUnit idGenUnit;

    @Autowired
    PaymentEventService paymentEventService;

    @Override
    public ResponseData scanPay(Integer userId,String authCode,Integer totalFee,String deviceInfo,Integer tradeType){
        LOGGER.info("微信扫码支付,userId={},authCode={},totalFee={},deviceInfo={},tradeType={}", userId, authCode, totalFee, deviceInfo, tradeType);
        ResponseData responseData;
        try{
            // TODO test 根据用户查询到shopid
            Integer shopId = 1;
            WeixinPayVO weixinPayVO = new WeixinPayVO();
            weixinPayVO.setAuthCode(authCode);
            weixinPayVO.setTotalFee(totalFee);
            weixinPayVO.setDeviceInfo(deviceInfo);
//            String  bodyUtf8 = new String("扫码收钱".toString().getBytes("UTF-8"));
//            String body = URLEncoder.encode(bodyUtf8, "UTF-8");
            weixinPayVO.setBody("test");
            weixinPayVO.setShopId(shopId);
            weixinPayVO.setWeixinPayType(tradeType);
            weixinPayVO.setOrderNo(idGenUnit.getOrderNo("0"));
            WeixinMch weixinMch = weixinMchService.selectByShopId(weixinPayVO.getShopId());
            if (weixinMch == null) {
                throw new WeixinException(ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getValueStr());
            }
            //初始化支付账单数据
            PaymentBill paymentBill = initPayBill(weixinPayVO, weixinMch.getId(), userId, shopId);
            responseData = weixinPayUnit.scanPay(weixinPayVO,weixinMch);
            if("0".equals(responseData.getMeta().getErrno())){//扫码支付成功
                ScanQueryResData scanQueryResData = weixinPayUnit.scanPayQueryOrder(null,weixinPayVO.getOrderNo(),weixinMch.getId());
              //更新支付状态前再次查询确认
                if ("SUCCESS".equals(scanQueryResData.getReturn_code()) && "SUCCESS".equals(scanQueryResData.getResult_code()) && "SUCCESS".equals(scanQueryResData.getTrade_state())) {
                    updateStatusAndInsertEvent(paymentBill, scanQueryResData.getTransaction_id(), scanQueryResData.getTrade_state(), scanQueryResData.getTotal_fee(), "", "", scanQueryResData.getOpenid(),"");
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
    public ResponseData scanRefund( String orderNo,Integer userId) {
        LOGGER.info("扫码支付退款,orderNo={},userId={}",orderNo,userId);
        ResponseData responseData;
        try {
            PaymentBill paymentBill = selectByOrderAndUserIdAndTradeType(orderNo, userId, ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte());
            PaymentBill refundPaymentBill = initRefundBill(paymentBill);
            //发起退款
            weixinPayUnit.weixinRefund(paymentBill.getOrderNo(), refundPaymentBill.getPayAmount(), refundPaymentBill.getPayAmount(), refundPaymentBill.getRefundNo(), refundPaymentBill.getMchId());
            //退款结果查询
            RefundQueryResData refundQueryResData = weixinPayUnit.refundQuery(null, paymentBill.getOrderNo(), null, paymentBill.getMchId());
            if (Constants.RESULT.SUCCESS.equals(refundQueryResData.getReturn_code()) && Constants.RESULT.SUCCESS.equals(refundQueryResData.getResult_code()) &&(
                    ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr().equals(refundQueryResData.getRefund_status_0())
                            || ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(refundQueryResData.getRefund_status_0()))) {// 退款申请成功后查询退款结果
                responseData =  ResponseData.success();
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
    public void insert(PaymentBill paymentBill) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", paymentBill);
    }

    @Override
    public void update(PaymentBill paymentBill) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", paymentBill);
    }

    @Override
    public PaymentBill selectByOrderAndUserIdAndTradeType(String orderNo, Integer userId,byte tradeType) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("userId", userId);
        map.put("tradeType", tradeType);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByOrderAndUserIdAndTradeType",map);
    }

    /**
     * 初始化支付账单数据
     * @param weixinPayVO 支付请求对象
     * @param mchId 商户主键
     * @param userId 用户ID
     * @param shopId 店铺id
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initPayBill(WeixinPayVO weixinPayVO, Integer mchId, Integer shopId,Integer userId){
        PaymentBill paymentBill = PaymentBill.initBill(weixinPayVO.getOrderNo(), null, mchId, weixinPayVO.getBody(),weixinPayVO.getTotalFee(),shopId,userId,ConstantEnum.PAY_CHANNEL_1.getCodeByte(),ConstantEnum.PAY_TYPE_1.getCodeByte(),
                ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte());
        insert(paymentBill);
        return paymentBill;
    }

    /**
     * 初始化支付账单数据
     * @param paymentBill 退款请求对象
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initRefundBill(PaymentBill paymentBill){
        PaymentBill refundPaymentBill = PaymentBill.initBill(paymentBill.getOrderNo(),idGenUnit.getOrderNo("1"), paymentBill.getMchId(), paymentBill.getOrderTitle(),paymentBill.getPayAmount(),paymentBill.getShopId(),paymentBill.getUesrId(),ConstantEnum.PAY_CHANNEL_1.getCodeByte(),ConstantEnum.PAY_TYPE_1.getCodeByte(),
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
