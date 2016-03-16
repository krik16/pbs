package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Authority;
import com.shouyingbao.pbs.entity.Role;
import com.shouyingbao.pbs.service.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/15 11:51
 **/
@Service
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.AuthorityMapper";
    @Override
    public void insert(Authority authority) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective");
    }

    @Override
    public void update(Authority authority) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective");
    }

    @Override
    public Role selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.getBaseDao().selectOneBySql(NAMESPACE+".selectByPrimaryKey",map);
    }

    @Override
    public List<Authority> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize) {
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectListByPage", map);
    }

    @Override
    public Integer selectListCount(Map<String, Object> map) {
        return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectListCount", map);
    }

    @Override
    public List<Authority> selectByRoleId(Integer roleId) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleId", roleId);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByRoleId", map);
    }
}
