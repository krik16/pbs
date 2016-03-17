package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class Area extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2986888824002246067L;
    private String name;

    private String desc;

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

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", baseEntity='" +  super.toString() + '\'' +
                '}';
    }
}