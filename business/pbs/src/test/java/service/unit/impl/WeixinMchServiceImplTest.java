package service.unit.impl;

import base.BaseTest;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.service.WeixinMchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * kejun
 * 2016/3/8 10:11
 **/
public class WeixinMchServiceImplTest extends BaseTest{

    @Autowired
    WeixinMchService weixinMchService;

    @Test
    public void selectByIdTest(){
        WeixinMch weixinMch = weixinMchService.selectById(1);
        System.err.println("weixinMch="+weixinMch);
    }

    @Test
    public void selectByShopIdTest(){
        WeixinMch weixinMch = weixinMchService.selectByShopId(1);
        System.err.println("weixinMch="+weixinMch);
    }
}
