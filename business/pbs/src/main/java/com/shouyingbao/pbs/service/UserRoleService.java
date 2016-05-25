package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.UserRole;

/**
 * kejun
 * 2016/3/14 14:46
 **/
public interface UserRoleService {

    void insert(UserRole userRole);

    void update(UserRole userRole);

    UserRole selectById(Integer id);

    UserRole selectByUserId(Integer userId);
}
