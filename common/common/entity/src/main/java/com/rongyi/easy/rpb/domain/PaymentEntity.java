package com.rongyi.easy.rpb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2516018941608003955L;

	public PaymentEntity() {

	}

	public PaymentEntity(String payNo, String orderNum, Integer orderType, BigDecimal orderPrice, String title, BigDecimal amountMoney, Integer status, Integer tradeType, Date createTime,
						 Date finishTime, Integer payChannel, String outAccount, String inAccount, Integer showNum, String drawUserId, String payName) {
		super();
		this.payNo = payNo;
		this.orderNum = orderNum;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.title = title;
		this.amountMoney = amountMoney;
		this.status = status;
		this.tradeType = tradeType;
		this.createTime = createTime;
		this.finishTime = finishTime;
		this.payChannel = payChannel;
		this.outAccount = outAccount;
		this.inAccount = inAccount;
		this.showNum = showNum;
		this.drawUserId = drawUserId;
		this.payName = payName;
	}

	/** 主键id */
	private Integer id;

	/**
	 * 付款单号
	 */
	private String payNo;

	/** 大订单号 */
	private String orderNum;

	/**
	 * 订单类型(0:商品订单，1:优惠券订单)
	 */
	private Integer orderType = 0;
	/**
	 * 订单金额
	 *
	 */
	private BigDecimal orderPrice;

	/** 订单名称 */
	private String title;

	/** 子订单号（如果是大订单则不填） */
	/*
	 * private Integer orderDetailNum;
	 */
	/** 付款总金额（多个订单同时支付，价格总和） */
	private BigDecimal amountMoney;

	/** 状态（待打款/款已打/款已到） */
	private Integer status;

	/** 交易类型（购买/退款） */
	private Integer tradeType;

	/** 创建时间 */
	private Date createTime;

	/** 完成时间 */
	private Date finishTime;

	/** 支付工具（支付宝/微信/银行等） */
	private Integer payChannel;

	/** 转出账号 */
	private String outAccount;

	/** 转入账号 */
	private String inAccount;

	/** 卖家店铺Id */
	private Integer showNum;

	/**
	 * 提现用户ID
	 */
	private String drawUserId;

	/**
	 * 提现账号姓名
	 */
	private String payName;

	/**
	 * 批量退款单号
	 */
	private String batchNo;

	/**
	 * 允许退款(0:允许，1：拒绝)
	 */
	private String refundRejected;

	/**
	 * 微信支付商户账号b表对应id
	 */
	private Integer weixinMchId;

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

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * 大订单号
	 * 
	 * @return orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}

	/**
	 * 大订单号
	 * 
	 * @param orderNum
	 */
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

	/**
	 * 子订单号（如果是大订单则不填）
	 * 
	 * @return orderDetailNum
	 */
	/*
	 * public Integer getOrderDetailNum() { return orderDetailNum; }
	 *//**
	 * 子订单号（如果是大订单则不填）
	 * 
	 * @param orderDetailNum
	 */
	/*
	 * public void setOrderDetailNum(Integer orderDetailNum) {
	 * this.orderDetailNum = orderDetailNum; }
	 */

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

	public Integer getShowNum() {
		return showNum;
	}

	public void setShowNum(Integer showNum) {
		this.showNum = showNum;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getDrawUserId() {
		return drawUserId;
	}

	public void setDrawUserId(String drawUserId) {
		this.drawUserId = drawUserId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getRefundRejected() {
		return refundRejected;
	}

	public void setRefundRejected(String refundRejected) {
		this.refundRejected = refundRejected;
	}

	public Integer getWeixinMchId() {
		return weixinMchId;
	}

	public void setWeixinMchId(Integer weixinMchId) {
		this.weixinMchId = weixinMchId;
	}
}
