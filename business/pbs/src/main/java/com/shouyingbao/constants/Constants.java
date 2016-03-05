package com.shouyingbao.constants;

public interface Constants {

	/**
	 * 登录用户的session key
	 */
	String USER_SESSION_KEY = "user_session_key"; //

	/**
	 * RSA加密相关
	 * 
	 * @author lim 2014年8月13日 下午8:43:44
	 */
	interface RSA {
		String PUBLIC_KEY = "publicKey";
		String PRIVATE_KEY = "privateKey";
	} 
 
	/**
	 * mq source name
	 * 
	 * @author kejun 2015年3月20日
	 */
	interface SOURCETYPE {
		String RPB = "pbs";
		String ROB = "rob";
		String OSM = "osm";
		String TMS = "tms";
		String COUPON= "coupon_order_queue";//优惠券
	}

	interface PAGE {
		Integer pageSize = 10;
	}

	interface PAYMENT_STATUS {
		int STAUS0 = 0;// 待打款
		int STAUS1 = 1;// 款已打
		int STAUS2 = 2;// 款已到
	}

	interface PAYMENT_STATUS_STR {
		String STAUS_STR0 = "待打款";
		String STAUS_STR1 = "款已打";
		String STAUS_STR2 = "款已到";
	}

	interface PAYMENT_TRADE_TYPE {
		int TRADE_TYPE0 = 0;// 购买
		int TRADE_TYPE1 = 1;// 退款
		int TRADE_TYPE2 = 2;// 打款给卖家
		int TRADE_TYPE3 = 3;// 提现
		int TRADE_TYPE4 = 4;// 异常支付
		int TRADE_TYPE5 = 5;//重复支付
		int TRADE_TYPE6 = 6;//重复支付退款
		int TRADE_TYPE7 = 7;//对账单付款
	}

	interface PAYMENT_TRADE_TYPE_STR {
		String TRADE_TYPE_STR0 = "购买";
		String TRADE_TYPE_STR1 = "退款";
		String TRADE_TYPE_STR2 = "付款";
	}

	interface PAYMENT_PAY_CHANNEL {
		int PAY_CHANNEL0 = 0;// 支付宝
		int PAY_CHANNEL1 = 1;// 微信
		int PAY_CHANNEL2 = 2;// 银行卡
	}

	interface PAYMENT_PAY_CHANNEL_STR {
		String PAY_CHANNEL_STR0 = "支付宝";
		String PAY_CHANNEL_STR1 = "微信";
		String PAY_CHANNEL_STR2 = "银行卡";
	}
	/**
	 * @Author:  柯军
	 * @Description: 用户账号状态
	 * @datetime:2015年6月4日下午2:22:07
	 *
	 **/
	interface ACCOUNT_STATUS{
	    int ACCOUNT_STATUS0 = 0;//登陆账号和支付账号正常
	    int ACCOUNT_STATUS1 = 1;//登陆账号被禁用
	    int ACCOUNT_STATUS2 = 2;//支付账号被禁用
	    int ACCOUNT_STATUS3 = 3;//账户不存在
	    
	}
	
	/**	
	 * @Author:  柯军
	 * @Description: 订单类型 
	 * @datetime:2015年6月5日下午1:47:27
	 *
	 **/
	interface ORDER_TYPE{
	    int ORDER_TYPE_0 = 0;//商品订单
	    int ORDER_TYPE_1 = 1;//优惠券订单
	}
	
	/**	
	 * @Author:  柯军
	 * @Description:支付消息回复来源 
	 * @datetime:2015年6月26日下午5:36:54
	 *
	 **/
	interface REPLAY_FLAG{
		int REPLAY_FLAG0 = 0;//支付宝客户端回复
		int REPLAY_FLAG1 = 1;//1:支付宝网页端call_back页面回复
		int REPLAY_FLAG2 = 2;//2:支付宝网页端notify_url页面回复
		int REPLAY_FLAG3 = 3;//3:微信客户端回复
		int REPLAY_FLAG4 = 4;//4:银联pos机回复
	}
	
	/**	
	 * @Author:  柯军
	 * @Description: 标记删除 
	 * @datetime:2015年7月9日上午11:18:07
	 *
	 **/
	interface DELETE_STATUS{
		int DELETE_STATUS0 = 0;//正常
		int DELETE_STATUS1 = 1;//删除
	}
	
	/**	
	 * @Author:  柯军
	 * @Description: 是否拒绝退款 
	 * @datetime:2015年8月27日下午2:31:25
	 *
	 **/
	interface REFUND_REJECTED{
		int REFUND_REJECTED0 = 0;//允许退款
		int REFUND_REJECTED1 = 1;//拒绝退款
	}

	/**	
	 * @Author:  柯军
	 * @Description: 0:手机支付宝APP支付,1:手机支付宝网页支付,2:手机微信支付，3:手机银联支付,4:支付宝退款，
	 * 5：微信退款，6：银联退款，7:异常支付打款给用户，8：提现打款，9：结算打款； 
	 * @datetime:2015年9月2日下午4:37:31
	 *
	 **/
	interface EVENT_TYPE{
		int EVENT_TYPE0 = 0;
		int EVENT_TYPE1 = 1;
		int EVENT_TYPE2 = 2;
		int EVENT_TYPE3 = 3;
		int EVENT_TYPE4 = 4;
		int EVENT_TYPE5 = 5;
		int EVENT_TYPE6 = 6;
		int EVENT_TYPE7 = 7;
		int EVENT_TYPE8 = 8;
		int EVENT_TYPE9 = 9;
		
	}
	
	interface RESULT{
		String SUCCESS = "SUCCESS";
		String FAIL = "FAIL";
	}
}
