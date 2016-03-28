package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.entity.UserRole;
import com.shouyingbao.pbs.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * kejun
 * 2016/3/28 19:03
 **/
@Service
public class UserRoleServiceImpl extends BaseServiceImpl implements UserRoleService{


    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.UserRoleMapper";
    @Override
    public void insert(UserRole userRole) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective", userRole);
    }

    @Override
    public void update(UserRole userRole) {
        this.getBaseDao().updateBySql(NAMESPACE + ".updateByPrimaryKeySelective", userRole);
    }

    @Override
    public UserRole selectById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
      return  this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByPrimaryKey", map);
    }

    @Override
    public UserRole selectByUserId(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return  this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByUserId", map);
    }

}
