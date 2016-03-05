package com.rongyi.easy.rpb.entity;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.BeanUtils;

/**
 * @Author: 柯军
 * @Description: 银联券码验证日志记录
 * @datetime:2015年7月2日下午1:45:08
 * 
 **/
public class UnionCouponLogEntity extends UnionBaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -785975250506759858L;

	/**
	 * 一次查询流水号
	 */
	private String reqSerialNo;

	/**
	 * 二次查询流水号
	 */
	private String twoReqSerialNo;

	/**
	 * 门店号
	 */
	private String shopNo;

	/**
	 * 终端号
	 */
	private String termNo;

	/**
	 * 券码
	 */
	private String couponNo;

	/**
	 * 付款单号（券码去除前4位）
	 */
	private String payNo;

	/**
	 * 终端指定的sp渠道号
	 */
	private String termSpChnlNo;

	/**
	 * 交易关联流水号
	 */
	private String transCrrltnNo;

	/**
	 * 全单金额
	 */
	private Double origAmt;

	/**
	 * 优惠金额
	 */
	private Double avlAmt;

	/**
	 * 支付方式 1:现金;2:刷卡;10:翼支付;
	 */
	private Integer payMode;

	/**
	 * 加密卡号
	 */
	private String encCardNo;

	/**
	 * 部分卡号
	 */
	private String partCardNo;

	/**
	 * 密码 SP网站的会员密码，需使用SP提供的公钥进行加密 目前仅支持6位纯数字
	 */
	private String spPwd;

	/**
	 * 签名域
	 */
	private String sign;

	public String getReqSerialNo() {
		return reqSerialNo;
	}

	public void setReqSerialNo(String reqSerialNo) {
		this.reqSerialNo = reqSerialNo;
	}

	public String getTwoReqSerialNo() {
		return twoReqSerialNo;
	}

	public void setTwoReqSerialNo(String twoReqSerialNo) {
		this.twoReqSerialNo = twoReqSerialNo;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getTermSpChnlNo() {
		return termSpChnlNo;
	}

	public void setTermSpChnlNo(String termSpChnlNo) {
		this.termSpChnlNo = termSpChnlNo;
	}

	public String getTransCrrltnNo() {
		return transCrrltnNo;
	}

	public void setTransCrrltnNo(String transCrrltnNo) {
		this.transCrrltnNo = transCrrltnNo;
	}

	public Double getOrigAmt() {
		return origAmt;
	}

	public void setOrigAmt(Double origAmt) {
		this.origAmt = origAmt;
	}

	public Double getAvlAmt() {
		return avlAmt;
	}

	public void setAvlAmt(Double avlAmt) {
		this.avlAmt = avlAmt;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public String getEncCardNo() {
		return encCardNo;
	}

	public void setEncCardNo(String encCardNo) {
		this.encCardNo = encCardNo;
	}

	public String getPartCardNo() {
		return partCardNo;
	}

	public void setPartCardNo(String partCardNo) {
		this.partCardNo = partCardNo;
	}

	public String getSpPwd() {
		return spPwd;
	}

	public void setSpPwd(String spPwd) {
		this.spPwd = spPwd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * @Description: 银联map信息to entity
	 * @param paramMap
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月2日下午4:50:51
	 **/
	public static UnionCouponLogEntity mapToEntity(Map<String, String[]> paramMap) {
		UnionCouponLogEntity unionCouponLogEntity = new UnionCouponLogEntity();
		UnionBaseEntity unionBaseEntity = UnionBaseEntity.mapToBaseEntity(paramMap);
		BeanUtils.copyProperties(unionBaseEntity, unionCouponLogEntity);
		if (paramMap.get("req_serial_no") != null && paramMap.get("req_serial_no").length > 0)
			unionCouponLogEntity.setReqSerialNo(paramMap.get("req_serial_no")[0].toString());
		if (paramMap.get("two_req_serial_no") != null && paramMap.get("two_req_serial_no").length > 0)
			unionCouponLogEntity.setTwoReqSerialNo(paramMap.get("two_req_serial_no")[0].toString());
		if (paramMap.get("shop_no") != null && paramMap.get("shop_no").length > 0)
			unionCouponLogEntity.setShopNo(paramMap.get("shop_no")[0].toString());
		if (paramMap.get("term_no") != null && paramMap.get("term_no").length > 0)
			unionCouponLogEntity.setTermNo(paramMap.get("term_no")[0].toString());
		if (paramMap.get("coupon_no") != null && paramMap.get("coupon_no").length > 0) {
			String couponNo = paramMap.get("coupon_no")[0].toString();
			unionCouponLogEntity.setCouponNo(couponNo);
			unionCouponLogEntity.setPayNo(couponNo.substring(4));
		}
		if (paramMap.get("term_sp_chnl_no") != null && paramMap.get("term_sp_chnl_no").length > 0)
			unionCouponLogEntity.setTermSpChnlNo(paramMap.get("term_sp_chnl_no")[0].toString());
		if (paramMap.get("trans_crrltn_no") != null && paramMap.get("trans_crrltn_no").length > 0)
			unionCouponLogEntity.setTransCrrltnNo(paramMap.get("trans_crrltn_no")[0].toString());
		if (paramMap.get("orig_amt") != null && paramMap.get("orig_amt").length > 0)
			unionCouponLogEntity.setOrigAmt(Double.valueOf(paramMap.get("orig_amt")[0].toString()));
		if (paramMap.get("avl_amt") != null && paramMap.get("avl_amt").length > 0)
			unionCouponLogEntity.setAvlAmt(Double.valueOf(paramMap.get("avl_amt")[0].toString()));
		if (paramMap.get("pay_mode") != null && paramMap.get("pay_mode").length > 0)
			unionCouponLogEntity.setPayMode(Integer.valueOf(paramMap.get("pay_mode")[0].toString()));
		if (paramMap.get("enc_card_no") != null && paramMap.get("enc_card_no").length > 0)
			unionCouponLogEntity.setEncCardNo(paramMap.get("enc_card_no")[0].toString());
		if (paramMap.get("part_card_no") != null && paramMap.get("part_card_no").length > 0)
			unionCouponLogEntity.setPartCardNo(paramMap.get("part_card_no")[0].toString());
		if (paramMap.get("sp_pwd") != null && paramMap.get("sp_pwd").length > 0)
			unionCouponLogEntity.setSpPwd(paramMap.get("sp_pwd")[0].toString());
		if (paramMap.get("sign") != null && paramMap.get("sign").length > 0)
			unionCouponLogEntity.setSign(paramMap.get("sign")[0].toString());
		return unionCouponLogEntity;
	}
}