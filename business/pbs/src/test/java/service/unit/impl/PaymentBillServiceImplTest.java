package service.unit.impl;

import base.BaseTest;
import com.shouyingbao.common.pay.weixin.model.ScanPayReqData;
import com.shouyingbao.common.pay.weixin.service.ScanPayService;
import com.shouyingbao.common.pay.weixin.util.Configure;
import com.shouyingbao.service.PaymentBillService;
import com.shouyingbao.unit.WeixinConfigUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.net.URLEncoder;

/**
 * kejun
 * 2016/3/7 16:21
 **/
public class PaymentBillServiceImplTest extends BaseTest{

    @Autowired
    PaymentBillService paymentBillService;

    @Autowired
    WeixinConfigUnit weixinConfigUnit;
    @Test
    public void weixinPayTest(){
        try{
            paymentBillService.scanPay(1,"130726723356995009",1,"111",0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void scanPayTest(){
        try {
            Configure configure = weixinConfigUnit.initConfigure(1,0);
           String  xmString = new String("闪购".toString().getBytes("UTF-8"));
            String body = URLEncoder.encode(xmString, "UTF-8");
            ScanPayReqData scanPayReqData = new ScanPayReqData("123",body,"", "",
                    "12312312313",1, "1","","","","",configure);
            ScanPayService scanPayService = new ScanPayService();
            String result = scanPayService.request(scanPayReqData, configure);
            System.err.println("result="+result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
