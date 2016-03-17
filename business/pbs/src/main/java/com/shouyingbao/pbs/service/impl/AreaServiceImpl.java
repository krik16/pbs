package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:59
 **/
@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.AreaMapper";

    @Override
    public void insert(Area area) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",area);
    }

    @Override
    public void update(Area area) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",area);
    }

    @Override
    public Area selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<Area> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
