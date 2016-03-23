package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Agent;
import com.shouyingbao.pbs.entity.Area;

import java.util.List;

/**
 * kejun
 * 2016/3/22 15:28
 **/
public class AgentVO extends Agent{

    private String areaName;

    private List<Area> areaList;


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
}
