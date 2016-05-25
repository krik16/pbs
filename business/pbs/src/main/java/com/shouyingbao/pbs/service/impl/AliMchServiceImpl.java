package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.AliMch;
import com.shouyingbao.pbs.service.AliMchService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * kejun
 * 2016/3/29 11:44
 **/
@Service
public class AliMchServiceImpl extends BaseServiceImpl implements AliMchService{


    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.AliMchMapper";


    @Override
    public void insert(AliMch aliMch) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", aliMch);
    }


    @Override
    public void update(AliMch aliMch) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", aliMch);
    }

    @Override
    public AliMch selectById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }
    @Override
    public AliMch selectByShopId(Integer shopId) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shopId);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByShopId", map);
    }
}
