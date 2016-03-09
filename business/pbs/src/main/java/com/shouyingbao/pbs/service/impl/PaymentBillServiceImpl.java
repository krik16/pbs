package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.Exception.WeixinException;
import com.shouyingbao.pbs.common.pay.weixin.model.ScanQueryResData;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.pbs.vo.WeixinPayVO;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.service.PaymentEventService;
import com.shouyingbao.pbs.service.WeixinMchService;
import com.shouyingbao.pbs.unit.IdGenUnit;
import com.shouyingbao.pbs.unit.WeixinPayUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        LOGGER.info("微信扫码支付,userId={},authCode={},totalFee={},deviceInfo={},tradeType={}",userId,authCode,totalFee,deviceInfo,tradeType);
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
            PaymentBill paymentBill = initPaymentBill(weixinPayVO, weixinMch.getId(), userId, shopId);
            responseData = weixinPayUnit.scanPay(weixinPayVO,weixinMch,paymentBill);
            if("0".equals(responseData.getMeta().getErrno())){//扫码支付成功
                ScanQueryResData scanQueryResData = weixinPayUnit.scanPayQueryOrder(null,weixinPayVO.getOrderNo(),weixinMch.getId());
              //更新支付状态前再次查询确认
                if ("SUCCESS".equals(scanQueryResData.getReturn_code()) && "SUCCESS".equals(scanQueryResData.getResult_code()) && "SUCCESS".equals(scanQueryResData.getTrade_state())) {
                    paymentBill.setStatus(ConstantEnum.PAY_STATUS_2.getCodeByte());
                    paymentBill.setFinishAt(DateUtil.getCurrDateTime());
                    update(paymentBill);
                    paymentEventService.init(weixinPayVO.getOrderNo(),paymentBill.getId(),scanQueryResData.getTransaction_id(),scanQueryResData.getTrade_state(),scanQueryResData.getTotal_fee(),
                            "","",scanQueryResData.getOpenid());
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
    public void insert(PaymentBill paymentBill) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",paymentBill);
    }

    @Override
    public void update(PaymentBill paymentBill) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",paymentBill);
    }

    /**
     * 初始化支付账单数据
     * @param weixinPayVO 支付请求对象
     * @param mchId 商户主键
     * @param userId 用户ID
     * @param shopId 店铺id
     * @return  PaymentBill 初始化对账
     */
    private PaymentBill initPaymentBill(WeixinPayVO weixinPayVO,Integer mchId,Integer userId,Integer shopId){
        PaymentBill paymentBill = new PaymentBill();
        paymentBill.setCreateAt(DateUtil.getCurrDateTime());
        paymentBill.setOrderNo(weixinPayVO.getOrderNo());
        paymentBill.setIsDelete(ConstantEnum.IS_DELETE_0.getCodeByte());
        paymentBill.setMchId(mchId);
        paymentBill.setOrderTitle(weixinPayVO.getBody());
        paymentBill.setPayAmount(weixinPayVO.getTotalFee());
        paymentBill.setPayChannel(ConstantEnum.PAY_CHANNEL_1.getCodeByte());
        paymentBill.setPayType(ConstantEnum.PAY_TYPE_1.getCodeByte());
        paymentBill.setShopId(shopId);
        paymentBill.setStatus(ConstantEnum.PAY_STATUS_0.getCodeByte());
        paymentBill.setUesrId(userId);
        paymentBill.setTradeType(ConstantEnum.PAY_TRADE_TYPE_0.getCodeByte());
        insert(paymentBill);
        return paymentBill;
    }
}
