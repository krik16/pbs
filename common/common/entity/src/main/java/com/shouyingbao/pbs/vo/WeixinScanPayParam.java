package com.shouyingbao.pbs.vo;

import java.io.Serializable;

/**
 * kejun
 * 2016/3/8 17:40
 **/
public class WeixinScanPayParam implements Serializable{
    private static final long serialVersionUID = 8887895822612218272L;

    //交易明细记录id
    private Integer id;

    private Integer userId;

    private String authCode;

    private Integer totalFee;

    private String deviceInfo;

    private Integer tradeType;

    private String orderNo;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WeixinScanPayParam{" +
                "userId=" + userId +
                ", authCode='" + authCode + '\'' +
                ", totalFee=" + totalFee +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", tradeType=" + tradeType +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
