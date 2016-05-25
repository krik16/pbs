package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Commodity;
import com.shouyingbao.pbs.entity.CommodityCategory;

import java.util.List;

/**
 * kejun
 * 2016/4/18 15:38
 **/
public class CommodityCategoryVO extends CommodityCategory{

    private Integer userId;

    private List<Commodity> commodityList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

    @Override
    public String toString() {
        return "CommodityCategoryVO{" +
                "userId=" + userId +
                ", commodityList=" + commodityList +
                ", super=" + super.toString() +
                '}';
    }
}
