package com.shouyingbao.service.impl;

import com.shouyingbao.Exception.WeixinException;
import com.shouyingbao.constants.ConstantEnum;
import com.shouyingbao.core.bean.ResponseData;
import com.shouyingbao.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import com.shouyingbao.service.PaymentBillService;
import com.shouyingbao.service.WeixinMchService;
import com.shouyingbao.unit.IdGenUnit;
import com.shouyingbao.unit.WeixinPayUnit;
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

    private static  final Logger LOGGER = LoggerFactory.getLogger(PaymentBillServiceImpl.class);

    @Autowired
    WeixinPayUnit weixinPayUnit;

    @Autowired
    WeixinMchService weixinMchService;

    @Autowired
    IdGenUnit idGenUnit;

    @Override
    public ResponseData scanPay(Integer shopId,String authCode,Integer totalFee,String deviceInfo,Integer tradeType){
        LOGGER.info("微信扫码支付,shopId={},authCode={},totalFee={},deviceInfo={},tradeType={}",shopId,authCode,totalFee,deviceInfo,tradeType);
        ResponseData responseData=ResponseData.success();
        try{
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
            responseData = weixinPayUnit.scanPay(weixinPayVO);
        }catch (WeixinException e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            responseData = ResponseData.failure(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            responseData =  ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(),ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
        }
        return responseData;
    }
}
