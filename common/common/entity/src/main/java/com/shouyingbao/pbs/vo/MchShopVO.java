package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchShop;
import com.shouyingbao.pbs.entity.Role;

import java.util.List;

/**
 * kejun
 * 2016/3/25 14:11
 **/
public class MchShopVO extends MchShop{

    private List<MchCompanyVO> companyList;

    private List<MchSubCompanyVO> subCompanyVOList;

    private List<AgentVO> agentVOList;

    private String companyName;

    private String subCompanyName;

    private String agentName;

    private List<Role> roleList;

    private String weixinMchId;

    private String aliPid;

    private String aliKey;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getWeixinMchId() {
        return weixinMchId;
    }

    public void setWeixinMchId(String weixinMchId) {
        this.weixinMchId = weixinMchId;
    }

    public String getAliPid() {
        return aliPid;
    }

    public void setAliPid(String aliPid) {
        this.aliPid = aliPid;
    }

    public String getAliKey() {
        return aliKey;
    }

    public void setAliKey(String aliKey) {
        this.aliKey = aliKey;
    }

    public List<AgentVO> getAgentVOList() {
        return agentVOList;
    }

    public void setAgentVOList(List<AgentVO> agentVOList) {
        this.agentVOList = agentVOList;
    }
}
