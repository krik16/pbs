package com.rongyi.rpb.service;

import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.rpb.common.pay.weixin.util.Configure;
import org.apache.commons.lang.StringUtils;

/**
 * Created by kejun on 2016/1/13.
 */
public interface WeixinConfigService {

    Configure initConfigure(String appId,Integer weixinPayType);

    Configure initConfigure(Integer weixinMchId);

}
