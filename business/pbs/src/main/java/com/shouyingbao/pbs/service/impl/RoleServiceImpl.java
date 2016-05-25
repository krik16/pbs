package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Role;
import com.shouyingbao.pbs.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:22
 **/
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.RoleMapper";
    @Override
    public void insert(Role role) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",role);
    }

    @Override
    public void update(Role role) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective",role);
    }

    @Override
    public Role selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".updateByPrimaryKeySelective");
    }

    @Override
    public List<Role> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
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
    public List<Role> selectByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByUserId", map);
    }

    @Override
    public List<Role> selectByType(byte type) {
        Map<String,Object> map = new HashMap<>();
        map.put("type", type);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByType", map);
    }

    @Override
    public List<Role> selectByTypeAndIdLimit(byte type, Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("type", type);
        map.put("id", id);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByTypeAndIdLimit", map);
    }
}
