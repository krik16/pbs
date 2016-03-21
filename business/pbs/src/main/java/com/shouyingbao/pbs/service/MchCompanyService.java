package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.MchCompany;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 17:23
 **/
public interface MchCompanyService {
    void insert(MchCompany mchCompany);

    void update(MchCompany mchCompany);

    MchCompany selectById(Integer id);

    List<MchCompany> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
