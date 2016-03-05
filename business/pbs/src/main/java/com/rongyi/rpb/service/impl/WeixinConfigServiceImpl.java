package com.rongyi.rpb.service.impl;

import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.rpb.Exception.WeixinException;
import com.rongyi.rpb.common.pay.weixin.util.Configure;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.service.WeixinConfigService;
import com.rongyi.rpb.service.WeixinMchService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Description: 初始化微信支付账号相关配置
 *  Author: kejun
  **/
@Service
public class WeixinConfigServiceImpl extends BaseServiceImpl implements WeixinConfigService{

    @Autowired
    WeixinMchService weixinMchService;

    @Override
    public Configure initConfigure(String appId,Integer weixinPayType) {
        Configure configure = new Configure();
        if (StringUtils.isNotBlank(appId) && !"null".equals(appId)) {
            WeixinMch weixinMch = weixinMchService.selectByAppIdAndTradeType(appId, weixinPayType);
            if(weixinMch == null){
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_APPID_NOT_EXIST.getCodeStr(),ConstantEnum.EXCEPTION_WEIXIN_APPID_NOT_EXIST.getValueStr());
            }
            weixinMch.setTradeType(String.valueOf(weixinPayType));
            configure.init(weixinMch);
        }
        return configure;
    }

    @Override
    public Configure initConfigure(Integer weixinMchId) {
        Configure configure = new Configure();
        if (weixinMchId != null && weixinMchId > 0) {
            WeixinMch weixinMch = weixinMchService.selectById(weixinMchId);
            configure.init(weixinMch);
        }
        return configure;
    }
}
