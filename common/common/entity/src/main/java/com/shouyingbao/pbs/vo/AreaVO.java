package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Area;
import com.shouyingbao.pbs.entity.Stockholder;

import java.io.Serializable;
import java.util.List;

/**
 * kejun
 * 2016/4/14 13:42
 **/
public class AreaVO extends Area implements Serializable{
    private static final long serialVersionUID = -761009568812605922L;

    private String stockholderName;

    private List<Stockholder> stockholderList;

    public String getStockholderName() {
        return stockholderName;
    }

    public void setStockholderName(String stockholderName) {
        this.stockholderName = stockholderName;
    }

    public List<Stockholder> getStockholderList() {
        return stockholderList;
    }

    public void setStockholderList(List<Stockholder> stockholderList) {
        this.stockholderList = stockholderList;
    }
}
