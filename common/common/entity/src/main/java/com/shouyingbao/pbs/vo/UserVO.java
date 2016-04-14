package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.*;

import java.util.List;

/**
 * kejun
 * 2016/3/24 19:13
 **/
public class UserVO extends User{

    private String companyName;

    private String subCompanyName;

    private String areaName;

    private String agentName;

    private String shopName;

    private String roleName;

    private Integer roleId;

    private List<MchCompanyVO> companyList;

    private List<MchSubCompanyVO> subCompanyVOList;

    private List<MchShopVO> shopList;

    private List<Role> roleList;

    private List<Stockholder> stockholderList;

    private List<Area> areaList;

    private List<AgentVO> agentList;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSubCompanyName() {
        return subCompanyName;
    }

    public void setSubCompanyName(String subCompanyName) {
        this.subCompanyName = subCompanyName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MchCompanyVO> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<MchCompanyVO> companyList) {
        this.companyList = companyList;
    }

    public List<MchSubCompanyVO> getSubCompanyVOList() {
        return subCompanyVOList;
    }

    public void setSubCompanyVOList(List<MchSubCompanyVO> subCompanyVOList) {
        this.subCompanyVOList = subCompanyVOList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<MchShopVO> getShopList() {
        return shopList;
    }

    public void setShopList(List<MchShopVO> shopList) {
        this.shopList = shopList;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public List<AgentVO> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<AgentVO> agentList) {
        this.agentList = agentList;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<Stockholder> getStockholderList() {
        return stockholderList;
    }

    public void setStockholderList(List<Stockholder> stockholderList) {
        this.stockholderList = stockholderList;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "companyName='" + companyName + '\'' +
                ", subCompanyName='" + subCompanyName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", areaName='" + shopName + '\'' +
                ", agentName='" + agentName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                ", companyList=" + companyList +
                ", roleList=" + roleList +
                ", subCompanyVOList=" + subCompanyVOList +
                ", shopList=" + shopList +
                ", user=" + super.toString() +
                '}';
    }
}
