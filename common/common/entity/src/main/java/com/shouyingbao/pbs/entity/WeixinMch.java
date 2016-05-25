package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class WeixinMch extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4744016445528355890L;
    private String appId;

    private String key;

    private String mchId;

    private String cretPath;

    private String publicCode;

    private Integer shopId;

    private String tradeType;
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getCretPath() {
        return cretPath;
    }

    public void setCretPath(String cretPath) {
        this.cretPath = cretPath;
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}