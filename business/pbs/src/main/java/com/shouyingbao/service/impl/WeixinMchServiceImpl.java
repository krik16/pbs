package com.shouyingbao.service.impl;


import com.shouyingbao.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.service.WeixinMchService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众支付
 * Author kejun
 */
@Service
public class WeixinMchServiceImpl extends BaseServiceImpl implements WeixinMchService {

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.WeixinMchMapper";

    @Override
    public void insert(WeixinMch weixinMch) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", weixinMch);
    }


    @Override
    public void update(WeixinMch weixinMch) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", weixinMch);
    }

    @Override
    public WeixinMch selectById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public WeixinMch selectByShopId(Integer shopId) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shopId);
       return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByShopId", map);
    }


}
