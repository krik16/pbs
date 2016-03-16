package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.service.PaymentBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * kejun
 * 2016/3/8 17:33
 **/
@Controller
@RequestMapping("/bill")
public class PaymentbIllController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentbIllController.class);
    @Autowired
    PaymentBillService paymentBillService;

    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(@RequestBody Map<String,Object> map){
        LOGGER.info("detail:map={}",map);
        ResponseData responseData;
        try {
            PaymentBill paymentBill = paymentBillService.selectById(Integer.valueOf(map.get("id").toString()));
            responseData = ResponseData.success(paymentBill);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_BILL_DETAIL.getCodeStr(),ConstantEnum.EXCEPTION_BILL_DETAIL.getValueStr());
        }
        return responseData;
    }
}
