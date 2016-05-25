package com.shouyingbao.pbs.service;


        import com.shouyingbao.pbs.entity.WeixinMch;

/**
 * Created by kejun on 2016/1/13.
 */
public interface WeixinMchService {

    void insert(WeixinMch weixinMch);

    void update(WeixinMch weixinMch);

    WeixinMch selectById(Integer id);

    WeixinMch selectByShopId(Integer shopId);
}
