package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Commodity;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:58
 **/
public interface CommodityService {

    void insert(Commodity commodity);

    void update(Commodity commodity);

    Commodity selectById(Integer id);

    List<Commodity> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize);

    Integer selectListCount(Map<String, Object> map);

    void save(Commodity commodity,Integer userId);
}
