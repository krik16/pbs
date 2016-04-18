package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.CommodityCategory;
import com.shouyingbao.pbs.service.CommodityCategoryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:59
 **/
@Service
public class ComodityCategoryServiceImpl extends BaseServiceImpl implements CommodityCategoryService{

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.CommodityCategoryMapper";

    @Override
    public void insert(CommodityCategory commodityCategory) {
        this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", commodityCategory);
    }

    @Override
    public void update(CommodityCategory commodityCategory) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", commodityCategory);
    }

    @Override
    public CommodityCategory selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public List<CommodityCategory> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
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
    public void save(CommodityCategory commodityCategory,Integer userId) {
        if (commodityCategory.getId() == null) {
            commodityCategory.setCreateAt(DateUtil.getCurrDateTime());
            commodityCategory.setCreateBy(userId);
            insert(commodityCategory);
        } else {
            commodityCategory.setUpdateAt(DateUtil.getCurrDateTime());
            commodityCategory.setUpdateBy(userId);
            update(commodityCategory);
        }
    }
}
