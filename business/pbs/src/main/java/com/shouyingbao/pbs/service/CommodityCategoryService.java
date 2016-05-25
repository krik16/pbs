package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.CommodityCategory;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:58
 **/
public interface CommodityCategoryService {

    void insert(CommodityCategory commodityCategory);

    void update(CommodityCategory commodityCategory);

    CommodityCategory selectById(Integer id);

    List<CommodityCategory> selectListByPage(Map<String, Object> map, Integer currentPage, Integer pageSize);

    Integer selectListCount(Map<String, Object> map);

    void save(CommodityCategory commodityCategory,Integer userId);
}
