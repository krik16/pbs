package com.rongyi.core.constant;

public interface OrderMessage {

	/** 未付款 */
	public static final String PLACE_ORDER_MESSAGE1 = "您有一笔订单未支付，将为您保留";
	public static final String PLACE_ORDER_MESSAGE2 = "，之后系统将关闭订单，抓紧时间支付吧~";
	public static final String PLACE_ORDER_MESSAGE3 = "在您的店铺有一笔订单，等待买家付款后即可发货。";

	/** 买家支付成功 */
	public static final String PAID_MESSAGE = "在您的店铺有一笔订单已经付款，请尽快发货哦！";

	/** 卖家发货超时 */
	public static final String UNDELIVERED_MESSAGE1 = "由于卖家";
	public static final String UNDELIVERED_MESSAGE2 = "超时未发货，系统自动同意退款，现金";
	public static final String UNDELIVERED_MESSAGE3 = "元会在5个工作日内退至您的支付账户，请注意查收";
	public static final String UNDELIVERED_MESSAGE4 = "由于您超时未发货，系统自动进行退款，现金";
	public static final String UNDELIVERED_MESSAGE5 = "元已成功退至买家的支付账户。";

	/** 卖家发货 */
	public static final String DELIVER_MESSAGE = "您有一笔订单卖家已经发货，无需物流，注意检查包裹内容后再签收哦！";
	public static final String DELIVER_MESSAGE1 = "您有一笔订单卖家已经发货，";
	public static final String DELIVER_MESSAGE2 = "，运单号";
	public static final String DELIVER_MESSAGE3 = "。注意检查包裹内容后再签收哦！";

	/** 确认收货 */
	public static final String CONFIRM_RECEIPT_MESSAGE = "在您的店铺有一笔订单已经确认收货。";

	/** 超过指定时间内未确认收货 */
	public static final String UNCONFIRMED_RECEIPT_MESSAGE1 = "您有一笔订单超时未确认收货，系统已默认确定收货。如未收到包裹或其他情况，限";
	public static final String UNCONFIRMED_RECEIPT_MESSAGE2 = "天内申请退款或维权。";
	public static final String UNCONFIRMED_RECEIPT_MESSAGE3 = "您有一笔订单超时未确认收货，系统已默认确定收货。如未收到包裹或其他情况，请联系客服。";

	/** 评价 */
	public static final String CONFIRM_EVALUATE_MESSAGE1 = "在您的店铺有一笔订单已经进行了评价。";

	/** 申请退款 */
	public static final String APPLY_REFUND_MESSAGE1 = "您有一笔订单申请了";
	public static final String APPLY_REFUND_MESSAGE2 = "，卖家将在";
	public static final String APPLY_REFUND_MESSAGE3 = "内进行处理，否则系统将自动同意退款。";
	public static final String APPLY_REFUND_MESSAGE4 = "在您店铺有一笔订单申请了";
	public static final String APPLY_REFUND_MESSAGE5 = "，请在";
	public static final String APPLY_REFUND_MESSAGE6 = "内进行处理，否则系统将自动同意退款。";

	/** 取消退款 */
	public static final String CANCEL_REFUND_MESSAGE = "在您店铺有一笔订单取消了退款";

	/** 卖家同意退款 */
	public static final String AGREE_REFUND_MESSAGE1 = "卖家";
	public static final String AGREE_REFUND_MESSAGE2 = "同意了您的退款，现金";
	public static final String AGREE_REFUND_MESSAGE3 = "元已成功退至支付账户，请注意查收。";
	public static final String AGREE_REFUND_MESSAGE4 = "您同意了";
	public static final String AGREE_REFUND_MESSAGE5 = "的退款申请，现金";
	public static final String AGREE_REFUND_MESSAGE6 = "元已成功退至买家的支付账户。";

	/** 卖家拒绝退款 */
	public static final String REFUSE_REFUND_MESSAGE1 = "卖家";
	public static final String REFUSE_REFUND_MESSAGE2 = "拒绝了您的退款，拒绝原因请查看退款详情。";

	/** 卖家指定时间内未拒绝退款申请 */
	public static final String UNREFUSED_REFUND_MESSAGE1 = "由于卖家";
	public static final String UNREFUSED_REFUND_MESSAGE2 = "超时未处理您的退款申请，系统自动同意退款，现金";
	public static final String UNREFUSED_REFUND_MESSAGE3 = "元已成功退至支付账户，请注意查收。";
	public static final String UNREFUSED_REFUND_MESSAGE4 = "由于您超时未处理";
	public static final String UNREFUSED_REFUND_MESSAGE5 = "发起的退款申请，系统自动同意退款，现金";
	public static final String UNREFUSED_REFUND_MESSAGE6 = "元已成功退至买家的支付账户。";

	/** 申请维权 */
	public static final String APPLY_ADULTS_MESSAGE1 = "您有一笔订单申请了维权，卖家";
	public static final String APPLY_ADULTS_MESSAGE2 = "将在";
	public static final String APPLY_ADULTS_MESSAGE3 = "内进行处理，如果卖家超时未处理官方将介入处理。";
	public static final String APPLY_ADULTS_MESSAGE4 = "在您店铺有一笔订单申请了维权，请在";
	public static final String APPLY_ADULTS_MESSAGE5 = "内进行处理，否则官方将进行介入处理。";

	/** 取消维权 */
	public static final String CANCEL_ADULTS_MESSAGE = "在您店铺有一笔订单取消了维权。";

	/** 官方处理维权 */
	public static final String OFFICAL_MANAGE_MESSAGE1 = "官方已对您申请的维权进行了处理，处理结果请查看维权详情。";
	public static final String OFFICAL_MANAGE_MESSAGE2 = "官方已对";
	public static final String OFFICAL_MANAGE_MESSAGE3 = "对您店铺有一笔订单发起的维权进行处理，处理结果请查看维权详情。";

	/** 佣金发放 */
	public static final String COMMISSION_ISSUE_MESSAGE1 = "官方已审核通过您的小票，佣金";
	public static final String COMMISSION_ISSUE_MESSAGE2 = "元正常发放到您的账户，请注意查收。";

	/** 佣金奖励 */
	public static final String COMMISSION_AWARD1 = "官方为您准备了接单小能手的额外奖励，奖励佣金";
	public static final String COMMISSION_AWARD2 = "元正常发放到您的账户，请注意查收。";

	/** 佣金审核失败 */
	public static final String COMMISSION_CHECK_FAIlURE_MESSAGE = "很抱歉，您上传的小票审核失败，该佣金不能正常发放到您的账户，请注意查看。";
	
	/** 卖家退款取消订单*/
	public static final String CANCEL_ORDER_MESSAGE1 = "由于卖家";
	public static final String CANCEL_ORDER_MESSAGE2 = "取消订单，系统同意退款，现金";
	public static final String CANCEL_ORDER_MESSAGE3 = "元会在5个工作日内退至您的支付账户，请注意查收";
	public static final String CANCEL_ORDER_MESSAGE4 = "由于您取消订单，系统自动进行退款至买家账户，现金";
	public static final String CANCEL_ORDER_MESSAGE5 = "元已成功退至买家的支付账户";
	
	/** 交易成功*/
	public static final String TRADE_SUCCESS_MESSAGE1 = "您有一笔订单交易成功，该订单视为交易成功，如需要退货，请联系导购进行线下退货";
	public static final String TRADE_SUCCESS_MESSAGE2 = "您有一笔订单交易成功，系统默认将全款金额发放至您的账户，请及时查看";
	
	/** 验码佣金 */
	public static final String COMMISSION_VALID1 = "官方已审核通过您的验码佣金，验码佣金";
	public static final String COMMISSION_VALID2 = "元正常发放到您的账户，请注意查收。";
	
	/** 卖家修改价格 */
	public static final String MODIFY_PRICE1 = "您于";
	public static final String MODIFY_PRICE2 = "，下单购买";
	public static final String MODIFY_PRICE3 = "店铺的商品（订单号：";
	public static final String MODIFY_PRICE4 = "），现订单价格发生改变，请注意查看。";
	public static final String MODIFY_PRICE5 = "），现订单价格发生改变，导致使用的抵扣券未满足使用条件，已重新计算订单总价并返还选用的抵扣券，请注意查看。";

}
