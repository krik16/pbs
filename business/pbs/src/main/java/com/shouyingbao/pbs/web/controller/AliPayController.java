package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.vo.AliScanPayParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * kejun
 * 2016/3/11 17:06
 **/
@Controller
public class AliPayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);
    @Autowired
    PaymentBillService paymentBillService;

    @RequestMapping("/scanPay")
    @ResponseBody
    public ResponseData scanPay(@RequestBody AliScanPayParam aliScanPayParam){
        LOGGER.info("支付宝扫码支付 aliScanPayParam={}",aliScanPayParam);
        ResponseData responseData;
       try {
           responseData = paymentBillService.aliScanPay(aliScanPayParam.getUserId(),aliScanPayParam.getAuthCode(),aliScanPayParam.getTotalFee(),aliScanPayParam.getDeviceInfo());
       }catch (Exception e){
           responseData = ResponseData.failure(ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getValueStr(),ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getCodeStr());
       }
       return responseData;
    }

    @RequestMapping("/scanRefund")
    @ResponseBody
    public ResponseData scanRefund(@RequestBody AliScanPayParam aliScanPayParam){
        LOGGER.info("支付宝扫码支付退款 aliScanPayParam={}",aliScanPayParam);
        ResponseData responseData;
        try {
            responseData = paymentBillService.aliScanRefund(aliScanPayParam.getOrderNo(),aliScanPayParam.getUserId());
        }catch (Exception e){
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getValueStr(),ConstantEnum.EXCEPTION_ALI_PAY_SCAN_FAIL.getCodeStr());
        }
        return responseData;
    }
}
