package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.MchSubCompany;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 17:51
 **/
public interface MchSubCompanyService {

    void insert(MchSubCompany mchSubCompany);

    void update(MchSubCompany mchSubCompany);

    MchSubCompany selectById(Integer id);

    List<MchSubCompany> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
