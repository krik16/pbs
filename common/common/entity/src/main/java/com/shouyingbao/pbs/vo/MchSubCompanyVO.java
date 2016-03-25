package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.MchSubCompany;

import java.util.List;

/**
 * kejun
 * 2016/3/25 11:49
 **/
public class MchSubCompanyVO extends MchSubCompany{

    private List<MchCompany> mchCompanyList;

    private String mchCompanyName;

    public List<MchCompany> getMchCompanyList() {
        return mchCompanyList;
    }

    public void setMchCompanyList(List<MchCompany> mchCompanyList) {
        this.mchCompanyList = mchCompanyList;
    }

    public String getMchCompanyName() {
        return mchCompanyName;
    }

    public void setMchCompanyName(String mchCompanyName) {
        this.mchCompanyName = mchCompanyName;
    }
}
