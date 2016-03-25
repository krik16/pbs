package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.MchCompany;
import com.shouyingbao.pbs.entity.User;

import java.util.List;

/**
 * kejun
 * 2016/3/24 19:13
 **/
public class UserVO extends User{

    private List<MchCompany> mchCompanieList;

    private List<MchSubCompanyVO> mchSubCompanyList;

    private List<MchShopVO> mchShopList;

    public List<MchCompany> getMchCompanieList() {
        return mchCompanieList;
    }

    public void setMchCompanieList(List<MchCompany> mchCompanieList) {
        this.mchCompanieList = mchCompanieList;
    }

    public List<MchSubCompanyVO> getMchSubCompanyList() {
        return mchSubCompanyList;
    }

    public void setMchSubCompanyList(List<MchSubCompanyVO> mchSubCompanyList) {
        this.mchSubCompanyList = mchSubCompanyList;
    }

    public List<MchShopVO> getMchShopList() {
        return mchShopList;
    }

    public void setMchShopList(List<MchShopVO> mchShopList) {
        this.mchShopList = mchShopList;
    }
}
