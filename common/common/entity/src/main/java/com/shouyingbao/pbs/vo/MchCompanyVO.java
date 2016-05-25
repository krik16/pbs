package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;

/**
 * kejun
 * 2016/3/30 15:21
 **/
public class MchCompanyVO extends MchCompany implements Comparable{

    private String agentName;

    private Double inTotalCount;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Double getInTotalCount() {
        return inTotalCount;
    }

    public void setInTotalCount(Double inTotalCount) {
        this.inTotalCount = inTotalCount;
    }

    @Override
    public String toString() {
        return "MchCompanyVO{" +
                "agentName='" + agentName + '\'' +
                "mchCompany='" + super.toString() + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        MchCompanyVO mchCompanyVO = (MchCompanyVO)o;
        return -this.getInTotalCount().compareTo(mchCompanyVO.getInTotalCount());
    }
}
