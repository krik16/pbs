package com.rongyi.easy.rpb.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author LiuChao 2014/08/28 22:34 支付宝客户端支付，网页支付，微信支付回调信息domain类
 * 
 */
public class PaymentLogInfo implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 2869168663044591621L;

	public PaymentLogInfo() {

	}

	public PaymentLogInfo(Integer replayFlag, String notifyId, String notifyType, String signType, Date notifyTime, String request_token, String sign, String trade_no, String buyer_id,
			String buyer_email, Integer buyer_type, String tradeMode, String transactionId, Date timeEnd, String outTradeNo, Date request_time, double total_fee, String result, Integer eventType,Integer tradeType) {
		super();
		this.replayFlag = replayFlag;
		this.notifyId = notifyId;
		this.notifyType = notifyType;
		this.signType = signType;
		this.notifyTime = notifyTime;
		this.request_token = request_token;
		this.sign = sign;
		this.trade_no = trade_no;
		this.buyer_id = buyer_id;
		this.buyer_email = buyer_email;
		this.buyer_type = buyer_type;
		this.tradeMode = tradeMode;
		this.transactionId = transactionId;
		this.timeEnd = timeEnd;
		this.outTradeNo = outTradeNo;
		this.request_time = request_time;
		this.total_fee = total_fee;
		this.result = result;
		this.eventType = eventType;
		this.tradeType = tradeType;
	}

	// 回复标志
	// 0:支付宝客户端回复，1:支付宝网页端call_back页面回复，2:支付宝网页端notify_url页面回复，3:微信客户端回复
	private Integer replayFlag;

	// 支付宝客户端支付返回
	private Integer id;
	private String notifyId; // 通知校验ID
	private String notifyType; // 通知类型
	private String signType; // 签名方式 支付宝取值为RSA，微信取值MD5
	private Date notifyTime; // 通知时间

	// 支付宝网页支付返回
	private String request_token; // 授权令牌，调用“手机网页即 时到账授权接口
									// (alipay.wap.trade.create.direct)”成功后返回的值
	// 支付宝网页与客户端公用属性
	private String sign; // 签名
	private String trade_no; // 该交易在支付宝系统中的交易 流水号。 最短16位，最长64位
	private String buyer_id; // 买家支付宝账号对应的支付宝唯一用户号,以2088开头的纯16位数字
	private String buyer_email; // 买家支付宝账号，可以是Email或手机号码。
	private Integer buyer_type;// 账号类型，买家/卖家（收款方）

	// 微信独有
	private String tradeMode; // 交易1是即时到帐，其他保留
	private String transactionId; // 交易号，28 位长的数值，其中前 10 位为商户号，之后 8 位为订单产生的日期，如
									// 20090415，最后10 位是流水号。
	private Date timeEnd; // 支付完成时间，格式为时间 yyyyMMddhhmmss，如 2009年 12 月 27 日 9 点
							// 10 分10 秒表示为20091227091010。时区为GMT+8 beijing。

	// ---三者公用属性---

	private String outTradeNo; // 商户系统的订单号，与请求一致

	private Date request_time; // 接收到回调的时间

	private double total_fee; // 总金额 网页端不返回此值，默认为null

	// 网页端判断支付结果及交易状态。只有支付成功时（即result=success），才会跳转到支付成功页面，result有且只有success一个交易状态。
	// 客户端 交易状态，例如TRADE_SUCCESS
	// 微信支付结果，0成功，其他保留
	private String result;

	/**
	 * 是否标记删除（0：正常，1：删除）
	 */
	private Integer deleteStatus = 0;

	/**
	 * 
	 * 0:手机APP支付,1:手机网页支付,2:PC端付款给商家，3:PC端退款给买家
	 */
	private Integer eventType;
	
	/**
	 * 交易类型（同paymentOrder定义相同）
	 */
	private Integer tradeType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId == null ? null : notifyId.trim();
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType == null ? null : notifyType.trim();
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign == null ? null : sign.trim();
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType == null ? null : signType.trim();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId == null ? null : transactionId.trim();
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getReplayFlag() {
		return replayFlag;
	}

	public String getRequest_token() {
		return request_token;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public String getResult() {
		return result;
	}

	public Date getRequest_time() {
		return request_time;
	}

	public double getTotal_fee() {
		return total_fee;
	}

	public void setReplayFlag(Integer replayFlag) {
		this.replayFlag = replayFlag;
	}

	public void setRequest_token(String request_token) {
		this.request_token = request_token;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public Integer getBuyer_type() {
		return buyer_type;
	}

	public void setBuyer_type(Integer buyer_type) {
		this.buyer_type = buyer_type;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	
}