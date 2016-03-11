package service.unit.impl;

import base.BaseTest;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.unit.WeixinConfigUnit;
import com.shouyingbao.pbs.unit.WeixinPayUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

/**
 * kejun
 * 2016/3/7 16:21
 **/
public class PaymentBillServiceImplTest extends BaseTest{

    @Autowired
    PaymentBillService paymentBillService;

    @Autowired
    @Qualifier("weixinConfigUnit")
    WeixinConfigUnit weixinConfigUnit;

    @Autowired
    @Qualifier("weixinPayUnit")
    WeixinPayUnit weixinPayUnit;

    @Test
    public void weixinPayTest(){
        try{
            ResponseData responseData = paymentBillService.weixinScanPay(1, "130646746181879109", 1000000, "111", 0);
            System.err.println("ResponseData="+responseData);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
