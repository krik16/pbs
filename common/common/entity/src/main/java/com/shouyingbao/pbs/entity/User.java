package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class User extends BaseEntity implements Serializable{
    private static final long serialVersionUID = -8143315016693212851L;

    private String userPhone;

    private String userAccount;

    private String userPwd;

    private String userName;

    private String userNickName;

    private String cardId;

    private Byte isEmployee;

    private String desc;

    private Integer shopId;

    private Integer companyId;

    private Integer subCompanyId;

    private Integer stockholderId;

    private Integer areaId;

    private Integer agentId;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public Byte getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Byte isEmployee) {
        this.isEmployee = isEmployee;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }


    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getStockholderId() {
        return stockholderId;
    }

    public void setStockholderId(Integer stockholderId) {
        this.stockholderId = stockholderId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userPhone='" + userPhone + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", cardId='" + cardId + '\'' +
                ", isEmployee=" + isEmployee +
                ", desc='" + desc + '\'' +
                ", shopId=" + shopId +
                ", companyId=" + companyId +
                ", subCompanyId=" + subCompanyId +
                ", areaId=" + areaId +
                ", agentId=" + agentId +
                ", BaseEntity=" + super.toString() +
                '}';
    }
}