package service.unit;

import base.BaseTest;
import com.shouyingbao.common.pay.weixin.model.ScanPayReqData;
import com.shouyingbao.common.pay.weixin.service.ScanPayService;
import com.shouyingbao.common.pay.weixin.util.Configure;
import com.shouyingbao.unit.WeixinConfigUnit;
import com.shouyingbao.unit.WeixinPayUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.net.URLEncoder;

/**
 * kejun
 * 2016/3/8 17:13
 **/
public class WeixinUnitTest extends BaseTest{

    @Autowired
    WeixinPayUnit weixinPayUnit;

    @Autowired
    WeixinConfigUnit weixinConfigUnit;

    @Test
    public void scanPayTest(){
        try {
            Configure configure = weixinConfigUnit.initConfigure(null,0);
            String  xmString = new String("闪购".toString().getBytes("UTF-8"));
            String body = URLEncoder.encode(xmString, "UTF-8");
            ScanPayReqData scanPayReqData = new ScanPayReqData("123",body,"", "",
                    "12312312313",1, "1","","","","",configure);
            ScanPayService scanPayService = new ScanPayService();
            String result = scanPayService.request(scanPayReqData, configure);
            System.err.println("result=" + result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void queryOrderTest(){
        weixinPayUnit.scanPayQueryOrder(null, "0030814341376164130", 1);
    }

    @Test
    public void reverseOrderTest(){
        weixinPayUnit.reverseOrder("0030814341376164130",1);
    }
}
