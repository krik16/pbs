package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class Stockholder extends  BaseEntity implements Serializable {

    private static final long serialVersionUID = 1696543058446213586L;
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
        return "Stockholder{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", BaseEntity='" + super.toString() + '\'' +
                '}';
    }
}