/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年8月7日上午10:36:30
 * @Description: TODO
 *
 **/

package com.rongyi.easy.rpb.vo;

import java.io.Serializable;

/**	
 * @Author:  柯军
 * @Description: 订单查询返回参数 
 * @datetime:2015年8月7日上午10:36:30
 *
 **/

public class QueryOrderParamVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4210085071379425434L;

	/**
	 * 查询是否成功(T:成功，F:失败)
	 */
	private String is_success;
	
	/**
	 * 失败信息
	 */
	private String error;
	
	/**
	 * 商品描述
	 */
	private String body;
	
	/**
	 * 买家账号
	 */
	private String buyer_email;
	
	/**
	 * 买家ID
	 */
	private String buyer_id;
	
	/**
	 * 折扣
	 */
	private String discount;
	
	/**
	 * 交易冻结状态（1 表示冻结；0 表示未冻结）
	 */
	private String flag_trade_locked;
	
	/**
	 * 交易创建时间
	 */
	private String gmt_create;
	
	/**
	 * 交易最近一次修改时间
	 */
	private String gmt_last_modified_time;
	
	/**
	 * 付款时间
	 */
	private String gmt_payment;
	
	/**
	 * 交易金额是否调整过(调整过－T； 没有调整过－F)
	 */
	private String is_total_fee_adjust;
	
	/**
	 * 交易的创建人角色(B：买家;S：卖家)
	 */
	private String operator_role;
	
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	
	/**
	 * 收款类型
	 */
	private String payment_type;
	
	/**
	 * 商品单价
	 */
	private String price;
	
	/**
	 * 购买数量
	 */
	private String quantity;
	
	/**
	 * 卖家的支付宝账号
	 */
	private String seller_email;
	
	/**
	 * 卖家的支付宝ID
	 */
	private String seller_id;
	
	/**
	 * 商品名称
	 */
	private String subject;
	
	/**
	 * 累计的已经退款金额
	 */
	private String to_buyer_fee;
	
	/**
	 * 累计的已打款给卖家的金额
	 */
	private String to_seller_fee;
	
	/**
	 * 交易总金额
	 */
	private String total_fee;
	
	/**
	 * 交易流水号
	 */
	private String trade_no;
	
	/**
	 * 交易状态
	 */
	private String trade_status;
	
	/**
	 * 是否有使用红包（使用过－T； 未使用过－F）
	 */
	private String use_coupon;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getFlag_trade_locked() {
		return flag_trade_locked;
	}

	public void setFlag_trade_locked(String flag_trade_locked) {
		this.flag_trade_locked = flag_trade_locked;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_last_modified_time() {
		return gmt_last_modified_time;
	}

	public void setGmt_last_modified_time(String gmt_last_modified_time) {
		this.gmt_last_modified_time = gmt_last_modified_time;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}

	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}

	public String getOperator_role() {
		return operator_role;
	}

	public void setOperator_role(String operator_role) {
		this.operator_role = operator_role;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo_buyer_fee() {
		return to_buyer_fee;
	}

	public void setTo_buyer_fee(String to_buyer_fee) {
		this.to_buyer_fee = to_buyer_fee;
	}

	public String getTo_seller_fee() {
		return to_seller_fee;
	}

	public void setTo_seller_fee(String to_seller_fee) {
		this.to_seller_fee = to_seller_fee;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getUse_coupon() {
		return use_coupon;
	}

	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
