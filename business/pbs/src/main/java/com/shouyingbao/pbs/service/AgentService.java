package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.vo.AgentVO;

import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/17 14:53
 **/
public interface AgentService {

    void insert(Agent agent);

    void update(Agent agent);

    Agent selectById(Integer id);

    List<AgentVO> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);
}
