package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.Role;
import com.shouyingbao.pbs.entity.User;

import java.util.List;

/**
 * kejun
 * 2016/3/24 19:13
 **/
public class UserVO extends User{

    private String companyName;

    private String subCompanyName;

    private String shopName;

    private String roleName;

    private Integer roleId;

    private List<MchCompany> companyList;

    private List<MchSubCompanyVO> subCompanyVOList;

    private List<MchShop> shopList;

    private List<Role> roleList;


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

    public List<MchCompany> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<MchCompany> companyList) {
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

    public List<MchShop> getShopList() {
        return shopList;
    }

    public void setShopList(List<MchShop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "companyName='" + companyName + '\'' +
                ", subCompanyName='" + subCompanyName + '\'' +
                ", shopName='" + shopName + '\'' +
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
