package com.rongyi.easy.rpb.entity;

import java.math.BigDecimal;

public class PaymentOrderEntity {
    /** 主键id */
    private Integer id;

    /** 大订单号 */
    private Integer orderId;

    /** 子订单号（如果是大订单则不填） */
    private Integer orderDetailId;

    /** 总金额 */
    private BigDecimal totalPrice;

    /** 支付款id） */
    private Integer paymentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}



	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}


}