/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月2日下午2:34:56
 * @Description: TODO
 *
 **/

package com.rongyi.easy.rpb.vo;

import java.io.Serializable;
import java.util.Date;

/**	
 * @Author:  柯军
 * @Description: 银联券码验证消息对应字段实体
 * @datetime:2015年7月2日下午2:34:56
 *
 **/

public class UnionCouponLogMapVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8470822767570304857L;


    /**
     * 报文类型
     */
    private String msg_type;

    /**
     * 交易代码 002002:一次查询，002003二次查询
     */
    private String msg_txn_code;

    /**
     * 报文流水号
     */
    private String msg_crrltn_id;

    /**
     * 报文发送方
     */
    private String msg_sender;

    /**
     * 报文日期
     */
    private Date msg_time;

    /**
     * 平台流水号
     */
    private String msg_sys_sn;

    /**
     * 报文版本号
     */
    private String msg_ver;

    /**
     * 一次查询流水号
     */
    private String req_serial_no;

    /**
     * 二次查询流水号
     */
    private String two_req_serial_no;

    /**
     * 门店号
     */
    private String shop_no;

    /**
     * 终端号
     */
    private String term_no;

    /**
     * 券码
     */
    private String coupon_no;

    /**
     * 终端指定的sp渠道号
     */
    private String term_sp_chnl_no;

    /**
     * 交易关联流水号
     */
    private String trans_crrltn_no;

    /**
     *  全单金额
     */
    private Double orig_amt;

    /**
     * 优惠金额
     */
    private Double avl_amt;

    /**
     *  支付方式 1:现金;2:刷卡;10:翼支付;
     */
    private Integer pay_mode;

    /**
     * 加密卡号
     */
    private String enc_card_no;

    /**
     * 部分卡号
     */
    private String part_card_no;

    /**
     * 密码  SP网站的会员密码，需使用SP提供的公钥进行加密 目前仅支持6位纯数字
     */
    private String sp_pwd;

    /**
     * 签名域
     */
    private String sign;

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getMsg_txn_code() {
		return msg_txn_code;
	}

	public void setMsg_txn_code(String msg_txn_code) {
		this.msg_txn_code = msg_txn_code;
	}

	public String getMsg_crrltn_id() {
		return msg_crrltn_id;
	}

	public void setMsg_crrltn_id(String msg_crrltn_id) {
		this.msg_crrltn_id = msg_crrltn_id;
	}

	public String getMsg_sender() {
		return msg_sender;
	}

	public void setMsg_sender(String msg_sender) {
		this.msg_sender = msg_sender;
	}

	public Date getMsg_time() {
		return msg_time;
	}

	public void setMsg_time(Date msg_time) {
		this.msg_time = msg_time;
	}

	public String getMsg_sys_sn() {
		return msg_sys_sn;
	}

	public void setMsg_sys_sn(String msg_sys_sn) {
		this.msg_sys_sn = msg_sys_sn;
	}

	public String getMsg_ver() {
		return msg_ver;
	}

	public void setMsg_ver(String msg_ver) {
		this.msg_ver = msg_ver;
	}

	public String getReq_serial_no() {
		return req_serial_no;
	}

	public void setReq_serial_no(String req_serial_no) {
		this.req_serial_no = req_serial_no;
	}

	public String getTwo_req_serial_no() {
		return two_req_serial_no;
	}

	public void setTwo_req_serial_no(String two_req_serial_no) {
		this.two_req_serial_no = two_req_serial_no;
	}

	public String getShop_no() {
		return shop_no;
	}

	public void setShop_no(String shop_no) {
		this.shop_no = shop_no;
	}

	public String getTerm_no() {
		return term_no;
	}

	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}

	public String getCoupon_no() {
		return coupon_no;
	}

	public void setCoupon_no(String coupon_no) {
		this.coupon_no = coupon_no;
	}

	public String getTerm_sp_chnl_no() {
		return term_sp_chnl_no;
	}

	public void setTerm_sp_chnl_no(String term_sp_chnl_no) {
		this.term_sp_chnl_no = term_sp_chnl_no;
	}

	public String getTrans_crrltn_no() {
		return trans_crrltn_no;
	}

	public void setTrans_crrltn_no(String trans_crrltn_no) {
		this.trans_crrltn_no = trans_crrltn_no;
	}

	public Double getOrig_amt() {
		return orig_amt;
	}

	public void setOrig_amt(Double orig_amt) {
		this.orig_amt = orig_amt;
	}

	public Double getAvl_amt() {
		return avl_amt;
	}

	public void setAvl_amt(Double avl_amt) {
		this.avl_amt = avl_amt;
	}

	public Integer getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(Integer pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getEnc_card_no() {
		return enc_card_no;
	}

	public void setEnc_card_no(String enc_card_no) {
		this.enc_card_no = enc_card_no;
	}

	public String getPart_card_no() {
		return part_card_no;
	}

	public void setPart_card_no(String part_card_no) {
		this.part_card_no = part_card_no;
	}

	public String getSp_pwd() {
		return sp_pwd;
	}

	public void setSp_pwd(String sp_pwd) {
		this.sp_pwd = sp_pwd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
    
}
