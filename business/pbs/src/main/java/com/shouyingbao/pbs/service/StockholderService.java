package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Stockholder;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/4/14 10:24
 **/
public interface StockholderService {

    void insert(Stockholder stockholder);

    void update(Stockholder stockholder);

    Stockholder selectById(Integer id);

    List<Stockholder> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
