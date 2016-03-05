package service;

import base.BaseTest;
import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.rpb.service.WeixinMchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @Copyright (C), 上海容易网电子商务有限公司
 * kejun
 * 2016/1/26 11:10
 **/
public class WeixinMchServiceTest extends BaseTest{

    @Autowired
    WeixinMchService weixinMchService;

    @Test
    public void selectByAppIdAndTradeTypeTest(){
        WeixinMch weixinMch = weixinMchService.selectByAppIdAndTradeType("wxb0af59268c136d7d",1);
        System.err.println("weixinMch="+weixinMch);
    }
}
