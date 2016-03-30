package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;

/**
 * kejun
 * 2016/3/30 15:21
 **/
public class MchCompanyVO extends MchCompany{

    private String agentName;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public String toString() {
        return "MchCompanyVO{" +
                "agentName='" + agentName + '\'' +
                "mchCompany='" + super.toString() + '\'' +
                '}';
    }
}
