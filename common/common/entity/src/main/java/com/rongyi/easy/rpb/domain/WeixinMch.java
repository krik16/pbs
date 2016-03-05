package com.rongyi.easy.rpb.domain;

import java.io.Serializable;
/**
 *  Description: 微信商户账号
 *  Author: kejun
 **/
public class WeixinMch implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3763743345647759343L;

	private Integer id;

    /**
     * APPID
     */
    private String appId;

    /**
     * 密钥
     */
    private String key;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 证书路径
     */
    private String cretPath;

    /**
     * 公众号
     */
    private String publicCode;

    /**
     * 商家ID
     */
    private String userId;

    /**
     * 交易类型  0:APP(app支付),1:JSAPI(公众号支付),2:NATIVE(原生扫码支付)多个支付方式逗号分隔
     */
    private String tradeType;

    /**
     * 是否是容易网支付账号 0:不是，1：是
     */
    private Byte isRongyiPay;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Byte getIsRongyiPay() {
        return isRongyiPay;
    }

    public void setIsRongyiPay(Byte isRongyiPay) {
        this.isRongyiPay = isRongyiPay;
    }

    @Override
    public String toString() {
        return "WeixinMch{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", key='" + key + '\'' +
                ", mchId='" + mchId + '\'' +
                ", cretPath='" + cretPath + '\'' +
                ", publicCode='" + publicCode + '\'' +
                ", userId='" + userId + '\'' +
                ", tradeType=" + tradeType +
                ", isRongyiPay=" + isRongyiPay +
                '}';
    }
}