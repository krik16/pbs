package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 14:54
 **/
@Service
public class AgentServiceImpl extends BaseServiceImpl implements AgentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.AgentMapper";
    @Override
    public void insert(Agent agent) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",agent);
    }

    @Override
    public void update(Agent agent) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective",agent);
    }

    @Override
    public Agent selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<Agent> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }
}
