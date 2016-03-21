package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class MchCompany extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5086817767144992315L;

    private String name;

    private String desc;

    private String address;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MchCompany{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", address='" + address + '\'' +
                ", baseEntity='" + super.toString() + '\'' +
                '}';
    }
}