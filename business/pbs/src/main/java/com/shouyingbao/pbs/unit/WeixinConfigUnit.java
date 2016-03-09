package com.shouyingbao.pbs.unit;

import com.shouyingbao.pbs.common.pay.weixin.util.Configure;
import com.shouyingbao.pbs.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.service.WeixinMchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 初始化微信支付账号相关配置
 * Author: kejun
 **/
@Component
public class WeixinConfigUnit {

    @Autowired
    WeixinMchService weixinMchService;

    public Configure initConfigure(WeixinMch weixinMch, Integer weixinPayType) {
        Configure configure = new Configure();
        weixinMch.setTradeType(weixinPayType.toString());
        configure.init(weixinMch);
        return configure;
    }

    public Configure initConfigure(Integer weixinMchId) {
        Configure configure = new Configure();
        if (weixinMchId != null && weixinMchId > 0) {
            WeixinMch weixinMch = weixinMchService.selectById(weixinMchId);
            configure.init(weixinMch);
        }
        return configure;
    }
}
