package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class Agent extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1875419709216731429L;
    private String name;

    private String desc;

    private Integer areaId;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", areaId=" + areaId +
                ", BaseEntity=" + super.toString() +
                '}';
    }
}