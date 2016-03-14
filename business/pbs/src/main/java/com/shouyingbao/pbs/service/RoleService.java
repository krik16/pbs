package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:19
 **/
public interface RoleService {
    void insert(Role role);

    void update(Role role);

    Role selectById(Integer id);

    List<Role> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
