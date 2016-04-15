package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.vo.AreaVO;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 10:58
 **/
public interface AreaService {

    void insert(Area area);

    void update(Area area);

    Area selectById(Integer id);

    List<Area> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    List<AreaVO> selectListVOByPage(Map<String, Object> map, Integer currentPage, Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
