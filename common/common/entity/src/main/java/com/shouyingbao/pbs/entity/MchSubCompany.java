package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class MchSubCompany extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3901371630900231916L;

    private String name;

    private String desc;

    private String address;

    private Integer companyId;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "MchSubCompany{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", address='" + address + '\'' +
                ", companyId=" + companyId +
                ", baseEntity=" + super.toString() +
                '}';
    }
}