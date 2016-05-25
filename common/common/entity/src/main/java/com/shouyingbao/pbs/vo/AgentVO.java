package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.entity.Area;

import java.io.Serializable;
import java.util.List;

/**
 * kejun
 * 2016/3/22 15:28
 **/
public class AgentVO extends Agent implements Serializable,Comparable{

    private String areaName;

    private List<Area> areaList;

    private Double inTotalCount;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public Double getInTotalCount() {
        return inTotalCount;
    }

    public void setInTotalCount(Double inTotalCount) {
        this.inTotalCount = inTotalCount;
    }

    @Override
    public int compareTo(Object o) {
        AgentVO agentVO = (AgentVO)o;
        return -this.getInTotalCount().compareTo(agentVO.getInTotalCount());
    }
}
