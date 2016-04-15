package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.entity.Stockholder;

import java.io.Serializable;
import java.util.List;

/**
 * kejun
 * 2016/4/14 13:42
 **/
public class AreaVO extends Area implements Serializable,Comparable {
    private static final long serialVersionUID = -761009568812605922L;

    private String stockholderName;

    private Double inTotalCount;

    private List<Stockholder> stockholderList;

    public String getStockholderName() {
        return stockholderName;
    }

    public void setStockholderName(String stockholderName) {
        this.stockholderName = stockholderName;
    }

    public Double getInTotalCount() {
        return inTotalCount;
    }

    public void setInTotalCount(Double inTotalCount) {
        this.inTotalCount = inTotalCount;
    }

    public List<Stockholder> getStockholderList() {
        return stockholderList;
    }

    public void setStockholderList(List<Stockholder> stockholderList) {
        this.stockholderList = stockholderList;
    }


    @Override
    public int compareTo(Object o) {
        AreaVO areaVO = (AreaVO)o;
        return -this.getInTotalCount().compareTo(areaVO.getInTotalCount());
    }
}
