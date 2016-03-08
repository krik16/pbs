package com.shouyingbao.unit;

import com.shouyingbao.Exception.WeixinException;
import com.shouyingbao.common.pay.weixin.util.Configure;
import com.shouyingbao.constants.ConstantEnum;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.service.WeixinMchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Description: 初始化微信支付账号相关配置
 *  Author: kejun
  **/
@Component
public class WeixinConfigUnit{

    @Autowired
    WeixinMchService weixinMchService;

    public Configure initConfigure(Integer shopId,Integer weixinPayType) {
        Configure configure = new Configure();
            WeixinMch weixinMch = weixinMchService.selectByShopId(shopId);
            if(weixinMch == null) {
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_APPID_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_APPID_NOT_EXIST.getValueStr());
            }
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
