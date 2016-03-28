package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.Role;

import java.util.List;

/**
 * kejun
 * 2016/3/25 14:11
 **/
public class MchShopVO extends MchShop{

    private List<MchCompany> companyList;

    private List<MchSubCompanyVO> subCompanyVOList;

    private String companyName;

    private String subCompanyName;

    private List<Role> roleList;

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
}
