package service.unit.impl;

import base.BaseTest;
import com.shouyingbao.core.bean.ResponseData;
import com.shouyingbao.service.PaymentBillService;
import com.shouyingbao.unit.WeixinConfigUnit;
import com.shouyingbao.unit.WeixinPayUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * kejun
 * 2016/3/7 16:21
 **/
public class PaymentBillServiceImplTest extends BaseTest{

    @Autowired
    PaymentBillService paymentBillService;

    @Autowired
    WeixinConfigUnit weixinConfigUnit;

    @Autowired
    WeixinPayUnit weixinPayUnit;

    @Test
    public void weixinPayTest(){
        try{
            ResponseData responseData = paymentBillService.scanPay(1,"130646746181879109",1000000,"111",0);
            System.err.println("ResponseData="+responseData);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
