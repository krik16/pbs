package service.unit;

import base.BaseTest;
import com.rongyi.rpb.unit.TimeExpireUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by kejun on 2015/11/18.
 */
public class TimeExpireUnitTest extends BaseTest{

    @Autowired
    TimeExpireUnit timeExpireUnit;

//    @Test
    public void testAliPayTimeExpire(){
        String startTime = null;
        String expireTime = null;
        System.err.println("参数为空："+timeExpireUnit.aliPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:19:20";
        expireTime = "2015-11-18 11:19:50";
        System.err.println("间隔小于1分钟"+timeExpireUnit.aliPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:19:20";
        expireTime = "2015-12-18 11:19:50";
        System.err.println("间隔大于15天："+timeExpireUnit.aliPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:19:20";
        expireTime = "2015-11-18 12:19:50";
        System.err.println("正常间隔"+timeExpireUnit.aliPayTimeExpire(startTime, expireTime,1));


    }

    @Test
    public void testWeixinPayTimeExpire(){
        String startTime = null;
        String expireTime = null;
//        System.err.println("参数为空："+timeExpireUnit.weixinPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:15:20";
        expireTime = "2015-11-18 11:19:50";
        System.err.println("间隔小于5分钟" + timeExpireUnit.weixinPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:19:20";
        expireTime = "2015-12-18 12:19:50";
        System.err.println("间隔大于15天："+timeExpireUnit.weixinPayTimeExpire(startTime, expireTime,1));
        startTime = "2015-11-18 11:19:20";
        expireTime = "2015-11-18 11:30:50";
        System.err.println("正常间隔："+timeExpireUnit.weixinPayTimeExpire(startTime, expireTime,1));

    }
}
