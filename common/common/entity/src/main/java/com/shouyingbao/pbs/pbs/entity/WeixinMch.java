package com.shouyingbao.pbs.pbs.entity;

import java.io.Serializable;

public class WeixinMch implements Serializable {
    private static final long serialVersionUID = -4744016445528355890L;
    private Integer id;

    private String appId;

    private String key;

    private String mchId;

    private String cretPath;

    private String publicCode;

    private String shopId;

    private String tradeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}