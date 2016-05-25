package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class CommodityCategory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3276208276769110386L;
    private String name;

    private String desc;

    private Integer shopId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "CommodityCategory{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", shopId=" + shopId +
                ", BaseEntity=" + super.toString() +
                '}';
    }
}