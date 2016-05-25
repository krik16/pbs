package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class AliMch extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6887433102536665105L;
    private String pid;

    private String key;

    private Integer shopId;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}