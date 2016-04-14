package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Stockholder;
import com.shouyingbao.pbs.service.StockholderService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:59
 **/
@Service
public class StockHolderServiceImpl extends BaseServiceImpl implements StockholderService{

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.StockholderMapper";

    @Override
    public void insert(Stockholder stockHolder) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",stockHolder);
    }

    @Override
    public void update(Stockholder stockHolder) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",stockHolder);
    }

    @Override
    public Stockholder selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<Stockholder> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        if(currentPage != null && pageSize != null) {
            map.put("currentPage", (currentPage - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
