package com.rongyi.rpb.service;

import com.rongyi.easy.rpb.domain.WeixinMch;

/**
 * Created by kejun on 2016/1/13.
 */
public interface WeixinMchService {

    void insert(WeixinMch weixinMch);

    void update(WeixinMch weixinMch);

    WeixinMch selectById(Integer id);

    WeixinMch selectByPublicCodeAndUserId(String publicCode,String userId);

    WeixinMch selectByAppIdAndTradeType(String appId,Integer tradeType);

    WeixinMch selectByMchIdAndUserId(String mchId,String userId);
}
