package com.rongyi.core.constant;

/**
 * 支付事件类型常量类
 * 
 * @author
 */
public interface PaymentEventType {

	/** 0元支付 */
	public static final String ZERO_PAY = "0";

	/** 支付宝网页支付 */

	public static final String PAYMENT = "1";

	/** 退款 */
	public static final String REFUND = "2";

	/** 支付宝手机APP支付 */
	public static final String APP = "3";

	/** 用户支付成功 */
	public static final String BUYER_PAID = "4";

	/** 微信支付 */
	public static final String WEIXIN_PAY = "5";

	/** 银联支付 */
	public static final String UNION_PAY = "6";

	/** 付款给卖家 */
	public static final String PAY_TO_SELLER = "7";

	/* ============================================== */

	/**
	 * 提现生成付款记录
	 */
	public static final String DRAW_PAY = "11";

	/**
	 * 其他系统调用支付
	 */
	public static final String OUTER_TO_PAY = "12";

	/**
	 * 管理界面支付成功通知
	 */
	public static final String PAY_NOTIFY = "13";

	/**
	 * 异常支付生成付款记录
	 */
	public static final String EXCE_PAY = "14";
	
	/**
	 * 对账单付款
	 */
	public static final String STATEMENT_PAY = "15";
	
	/**
	 * 对账单支付成功结果通知
	 */
	public static final String STATEMENT_PAY_NOTIFY = "16";
	
}
