package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.PaymentBill;

/**
 * kejun
 * 2016/4/1 10:44
 **/
public class PaymentBillVO extends PaymentBill{

    private String tradeNo;

    private String shopName;

    private String areaName;

    private String agentName;

    private String companyName;

    private String subCompanyName;

    private String userName;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PaymentBillVO{" +
                "tradeNo='" + tradeNo + '\'' +
                ", shopName='" + shopName + '\'' +
                ", paymentBill='" + super.toString() + '\'' +
                '}';
    }
}
