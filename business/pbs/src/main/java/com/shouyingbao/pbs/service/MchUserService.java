package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.MchUser;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:46
 **/
public interface MchUserService {

    void insert(MchUser mchUser);

    void update(MchUser mchUser);

    MchUser selectById(Integer id);

    List<MchUser> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
