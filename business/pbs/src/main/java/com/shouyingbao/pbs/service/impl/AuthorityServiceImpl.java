package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.Authority;
import com.shouyingbao.pbs.entity.Role;
import com.shouyingbao.pbs.service.AuthorityService;
import com.shouyingbao.pbs.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    RoleService roleService;


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
    public List<Authority> selectByRoleId(Integer roleId) {
        Map<String,Object> map = new HashMap<>();
        map.put("roleId", roleId);
        return this.getBaseDao().selectListBySql(NAMESPACE + ".selectByRoleId", map);
    }

    @Override
    public List<Authority> selectByUserId(Integer userId) {
        List<Authority> authList = new ArrayList<>();
        List<Role> roleList = roleService.selectByUserId(userId);
        for (Role role :roleList){
            authList.addAll(selectByRoleId(role.getId()));
        }
        return authList;
    }

    @Override
    public boolean checkAuthority(String authorityValue, Integer userId) {
        boolean result = false;
        List<Authority> list = selectByUserId(userId);
        for (Authority authority : list){
            if(authority.getValue().equals(authorityValue)){
                result = true;
                break;
            }
        }
        return result;
    }
}
