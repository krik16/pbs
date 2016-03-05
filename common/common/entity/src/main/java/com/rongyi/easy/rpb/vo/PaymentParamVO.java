/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年5月20日上午10:26:35
 * @Description: TODO
 *
 **/

package com.rongyi.easy.rpb.vo;

import java.io.Serializable;
import java.util.Date;


/**	
 * @Author:  柯军
 * @Description: 付款请求操作参数 
 * @datetime:2015年9月28日上午11:53:02
 *
 **/
public class PaymentParamVO implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7647979818874548559L;

	/**
	 * 付款用户账号
	 */
	private String userId;

	/**
	 * 提现金额
	 */
	private Double payTotal;

	/**
	 * 操作流水号
	 */
	private String operateNo;


	/**
	 * (支付账号类型，0：支付宝，1：微信，2:银联)
	 */
	private Integer payChannel;

	/**
	 *付款账号
	 */
	private String payAccount;

	/**
	 * 付款姓名
	 */
	private String payName;
	
	/**
	 * 付款类型(对账单类型:15,...)
	 */
	private String operateType;
	
	/**
	 *  创建时间
	 */
	private Date createAt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(Double payTotal) {
		this.payTotal = payTotal;
	}

	public String getOperateNo() {
		return operateNo;
	}

	public void setOperateNo(String operateNo) {
		this.operateNo = operateNo;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
