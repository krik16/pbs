package com.rongyi.easy.rpb.entity;

import java.math.BigDecimal;
import java.util.List;

public class PayAndRefundOrderEntity {

    /** 订单号 */
    private Integer orderId;

    private Integer orderDetailId;
    /** 总金额 */
    private BigDecimal totalPrice;

    /** 支付款idList） */
    private List<Integer> paymentIdList;


	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}



	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Integer> getPaymentIdList() {
		return paymentIdList;
	}

	public void setPaymentIdList(List<Integer> paymentIdList) {
		this.paymentIdList = paymentIdList;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}



}