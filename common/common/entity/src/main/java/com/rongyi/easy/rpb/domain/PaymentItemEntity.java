package com.rongyi.easy.rpb.domain;


public class PaymentItemEntity {
	/** 主键id */
	private Integer id;

	/** 子订单号 */
	private String detailNum;

	/** 卖家店铺Id */
	private Integer paymentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetailNum() {
		return detailNum;
	}

	public void setDetailNum(String detailNum) {
		this.detailNum = detailNum;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

}