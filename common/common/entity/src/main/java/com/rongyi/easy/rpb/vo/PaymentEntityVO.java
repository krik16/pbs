package com.rongyi.easy.rpb.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentEntityVO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * osm退款传递过来的id
     */
    private Integer paymentId;

    /**
     * 大订单号
     */
    private String orderNum;

    /**
     * 订单类型(0:商品订单，1:优惠券订单)
     */
    private Integer orderType;


    private String payNo;

    /** 子订单号（如果是大订单则不填） */
    /*
	 * private Integer orderDetailNum;
	 */

    /**
     * 订单名称
     */
    private String title;

    /**
     * 子订单号（如果是大订单则不填,多个子订单Id用逗号分隔开）
     */
    private String orderDetailNumArray;

    /**
     * 金额
     */
    private BigDecimal amountMoney;

    /**
     * 状态（款已打/款已到）
     */
    private Integer status;

    private String statusStr;

    /**
     * 交易类型（购买/退款）
     */
    private Integer tradeType;

    private String tradeTypeStr;

    /**
     * 创建时间
     */
    private Date createTime;

    private String createTimeStr;

    /**
     * 完成时间
     */
    private Date finishTime;

    private String finishTimeStr;

    /**
     * 支付工具（银行/支付宝等）
     */
    private Integer payChannel;

    private String payChannelStr;

    /**
     * 转出账号
     */
    private String outAccount;

    /**
     * 转入账号
     */
    private String inAccount;

    /**
     * 退款原因
     */
    private String desc;

    /**
     * 卖家店铺ID
     */
    private Integer showNum;

    /**
     * 支付开始时间
     **/

    private String timeStart;

    /**
     * 支付失效时间
     */
    private String timeExpire;

    /**
     * 微信APPId
     */
    private String appId;

    private String openId;

    private Integer weixinPayType;
    /**
     * 主键id
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 订单名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 订单名称
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderDetailNumArray() {
        return orderDetailNumArray;
    }

    public void setOrderDetailNumArray(String orderDetailNumArray) {
        this.orderDetailNumArray = orderDetailNumArray;
    }

    /**
     * 金额
     *
     * @return amountMoney
     */
    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    /**
     * 金额
     *
     * @param amountMoney
     */
    public void setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
    }

    /**
     * 状态（款已打/款已到）
     *
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（款已打/款已到）
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 交易类型（购买/退款）
     *
     * @return tradeType
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * 交易类型（购买/退款）
     *
     * @param tradeType
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 创建时间
     *
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 完成时间
     *
     * @return finishTime
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 完成时间
     *
     * @param finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 支付工具（银行/支付宝等）
     *
     * @return payChannel
     */
    public Integer getPayChannel() {
        return payChannel;
    }

    /**
     * 支付工具（银行/支付宝等）
     *
     * @param payChannel
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 转出账号
     *
     * @return outAccount
     */
    public String getOutAccount() {
        return outAccount;
    }

    /**
     * 转出账号
     *
     * @param outAccount
     */
    public void setOutAccount(String outAccount) {
        this.outAccount = outAccount;
    }

    /**
     * 转入账号
     *
     * @return inAccount
     */
    public String getInAccount() {
        return inAccount;
    }

    /**
     * 转入账号
     *
     * @param inAccount
     */
    public void setInAccount(String inAccount) {
        this.inAccount = inAccount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

	/*
	 * public Integer getOrderDetailNum() { return orderDetailNum; }
	 * 
	 * public void setOrderDetailNum(Integer orderDetailNum) {
	 * this.orderDetailNum = orderDetailNum; }
	 */

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getFinishTimeStr() {
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTradeTypeStr() {
        return tradeTypeStr;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTradeTypeStr(String tradeTypeStr) {
        this.tradeTypeStr = tradeTypeStr;
    }

    public String getPayChannelStr() {
        return payChannelStr;
    }

    public void setPayChannelStr(String payChannelStr) {
        this.payChannelStr = payChannelStr;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public void setShowNum(Integer showNum) {
        this.showNum = showNum;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }


    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getWeixinPayType() {
        return weixinPayType;
    }

    public void setWeixinPayType(Integer weixinPayType) {
        this.weixinPayType = weixinPayType;
    }
}