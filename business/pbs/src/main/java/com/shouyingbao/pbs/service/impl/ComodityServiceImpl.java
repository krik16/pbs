package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Commodity;
import com.shouyingbao.pbs.service.CommodityService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:59
 **/
@Service
public class ComodityServiceImpl extends BaseServiceImpl implements CommodityService {

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.CommodityMapper";

    @Override
    public void insert(Commodity commodity) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", commodity);
    }

    @Override
    public void update(Commodity commodity) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", commodity);
    }

    @Override
    public Commodity selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public List<Commodity> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
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

    @Override
    public void save(Commodity commodity, Integer userId) {
        if (commodity.getId() == null) {
            commodity.setCreateAt(DateUtil.getCurrDateTime());
            commodity.setCreateBy(userId);
            insert(commodity);
        } else {
            commodity.setUpdateAt(DateUtil.getCurrDateTime());
            commodity.setUpdateBy(userId);
            update(commodity);
        }
    }
}
