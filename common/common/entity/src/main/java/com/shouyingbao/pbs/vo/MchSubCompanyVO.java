package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchSubCompany;

import java.util.List;

/**
 * kejun
 * 2016/3/25 11:49
 **/
public class MchSubCompanyVO extends MchSubCompany implements Comparable{

    private List<MchCompanyVO> mchCompanyList;

    private String mchCompanyName;

    private Double inTotalCount;

    public List<MchCompanyVO> getMchCompanyList() {
        return mchCompanyList;
    }

    public void setMchCompanyList(List<MchCompanyVO> mchCompanyList) {
        this.mchCompanyList = mchCompanyList;
    }

    public String getMchCompanyName() {
        return mchCompanyName;
    }

    public void setMchCompanyName(String mchCompanyName) {
        this.mchCompanyName = mchCompanyName;
    }

    public Double getInTotalCount() {
        return inTotalCount;
    }

    public void setInTotalCount(Double inTotalCount) {
        this.inTotalCount = inTotalCount;
    }

    @Override
    public int compareTo(Object o) {
        MchSubCompanyVO mchSubCompanyVO = (MchSubCompanyVO)o;
        return -this.getInTotalCount().compareTo(mchSubCompanyVO.getInTotalCount());
    }
}
