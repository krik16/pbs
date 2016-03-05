package com.shouyingbao.common.pay.weixin.model;

import java.io.Serializable;

/**
 * Created by kejun on 2016/1/13.
 */
public class PaySignData {

    private String payNo;

    private Integer totalFee;

    private String body;

    private Integer orderType;

    private String timeStart;

    private String timeExpire;

    private String appId;

    private String openId;

    private Integer weixinPayType;


    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getWeixinPayType() {
        return weixinPayType;
    }

    public void setWeixinPayType(Integer weixinPayType) {
        this.weixinPayType = weixinPayType;
    }

    @Override
    public String toString() {
        return "PaySignData{" +
                "payNo='" + payNo + '\'' +
                ", totalFee=" + totalFee +
                ", body='" + body + '\'' +
                ", orderType=" + orderType +
                ", timeStart='" + timeStart + '\'' +
                ", timeExpire='" + timeExpire + '\'' +
                ", appId='" + appId + '\'' +
                ", openId='" + openId + '\'' +
                ", weixinPayType='" + weixinPayType + '\'' +
                '}';
    }
}
