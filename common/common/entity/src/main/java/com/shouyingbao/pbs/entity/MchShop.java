package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class MchShop  extends  BaseEntity implements Serializable {
    private static final long serialVersionUID = 3837175617670691431L;
    private String name;

    private String desc;

    private Integer companyId;

    private Integer subCompanyId;

    private String address;

    private Integer agentId;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSubCompanyId() {
        return subCompanyId;
    }

    public void setSubCompanyId(Integer subCompanyId) {
        this.subCompanyId = subCompanyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "MchShop{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", companyId=" + companyId +
                ", subCompanyId=" + subCompanyId +
                ", address='" + address + '\'' +
                ", agentId='" + agentId + '\'' +
                ", baseEntity='" + super.toString() + '\'' +
                '}';
    }
}