package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.vo.MchShopVO;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/14 14:39
 **/
public interface MchShopService {

    void insert(MchShop mchShop);

    void update(MchShop mchShop);

    MchShop selectById(Integer id);

    List<MchShopVO> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
