package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Authority;
import com.shouyingbao.pbs.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/15 11:51
 **/
public interface AuthorityService {

    void insert(Authority authority);

    void update( Authority authority);

    Role selectById(Integer id);

    List< Authority> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);

    List< Authority> selectByRoleId(Integer roleId);

    List<Authority> selectByUserId(Integer userId);

    boolean checkAuthority(String authorityValue,Integer userId);
}
