package com.shouyingbao.pbs.vo;

/**
 * Created by kejun on 2016/03/07.
 */
public class WeixinPayVO {

    private String orderNo;

    private Integer totalFee;

    private String body;

    private Integer orderType;

    private String timeStart;

    private String timeExpire;

    private String appId;

    private String openId;

    private Integer weixinPayType;

    private String authCode;

    private String deviceInfo;

    private Integer shopId;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "WeixinPayVO{" +
                "orderNo='" + orderNo + '\'' +
                ", totalFee=" + totalFee +
                ", body='" + body + '\'' +
                ", orderType=" + orderType +
                ", timeStart='" + timeStart + '\'' +
                ", timeExpire='" + timeExpire + '\'' +
                ", appId='" + appId + '\'' +
                ", openId='" + openId + '\'' +
                ", weixinPayType='" + weixinPayType + '\'' +
                ", authCode='" + authCode + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
