package com.rongyi.core.constant;

/**
 * 订单事件类型常量类
 * @author baodk
 */
public interface OrderEventType {

    /** 买家下单 (rob) */
    public static final String PLACE_ORDER = "1";
    
    /** 买家支付成功 (rpb) */
    public static final String PAID = "2";
    
    /** 卖家发货 (rob) */
    public static final String DELIVER_GOOD = "3";
    
    /** 买家确认收货 (rob) */
    public static final String CONFIRM_RECEIPT = "4";
    
    /** 买家指定时间内未付款 (osm) */
    public static final String UNPAID_WITHIN_TIME = "5";
    
    /** 卖家指定时间内未发货 (osm) */
    public static final String UNDELIVERED_WITHIN_TIME = "6";
    
    /** 超过指定时间内未确认收货 (osm) */
    public static final String UNCONFIRMED_RECEIPT_WITHIN_TIME = "7";

    /** 买家申请退款 (rob) */
    public static final String APPLY_REFUND = "8";
    
    /** 卖家指定时间内未拒绝退款申请 (osm) */
    public static final String UNREFUSED_APPLY_REFUND_WITHIN_TIME = "9";
 
    /** 卖家同意退款申请 (rob) */
    public static final String AGREE_APPLY_REFUND = "10";

    /** 卖家拒绝退款申请 (rob) */
    public static final String REFUSE_APPLY_REFUND = "11";
 
    /** 买家申请维权 (rob) */
    public static final String APPLY_ADULTS = "12";

    /** 买家取消维权 (rob) */
    public static final String CANCEL_ADULTS = "13";
    
    /** 客服判定退款 (rob) */
    public static final String JUDGE_REFUND = "14";

    /** 客服判定不退款 (rob) */
    public static final String JUDGE_NO_REFUND = "15";
    
    /** 用户下单后支付前卖家主动关闭 (rob) */
    public static final String MANUAL_CLOSED = "16";
    
    /** 卖家在买家提交订单后付款前操作修改价格 (rob) */
    public static final String MODIFY_PRICE = "17"; 
    
    /** 买家取消退款 (rob) */
    public static final String CANCEL_REFUND = "18";
    
    /** 订单确认收货状态超时 */
    public static final String CONFIRM_RECEIPT_STATUS_TIMEOUT= "19";
    
    /**确认评价*/
    public static final String CONFIRM_EVALUATE= "20";
    
    /**订单关闭*/
    public static final String ORDER_CLOSED = "21";
    
    /**卖家在买家维权后同意退款*/
    public static final String AGREE_APPLY_REFUND_AFTER_ADULTS = "22";
    

    /**卖家撤销订单(买家付款后,需要退款)*/
    public static final String CANCEL_BY_SELLER= "23";
    
    /** C2C卖家修改价格 */
    public static final String C2C_MODIFY_PRICE = "24";
    
    /** 交易成功*/
    public static final String TRADE_SUCCESS = "25";
    
    /** 未支付提醒*/
    public static final String PAYMENT_DELAY_ALERT = "26";
    
    /** 评价关闭*/
    public static final String COMMENT_CLOSE = "27";

    /** 买家关闭订单*/
    public static final String CANCEL_BY_BUYER = "28";
    
    /** 重新加载订单 */
    public static final String DEBUG_RELOAD_ORDER = "Debug.Reload";
    
    
    public interface EventErrorCode {
    	/**
    	 * 用户事件请求成功
    	 */
    	public static final int SUCCESS = 0;
    	
    	/**
    	 * 用户事件请求失败
    	 */
    	public static final int FAILED = 1;
    	
    	/**
    	 * 用户请求不满足状态要求
    	 */
    	public static final int REQUEST_ORDER_INVALID_STATUS = 2;
    	
    	/**
    	 * 用户请求不满足时间要求
    	 */
    	public static final int REQUEST_TIMEOUT = 3;
    	
    	/**
    	 * 用户请求的订单已经存在
    	 */
    	public static final int REQUEST_ORDER_EXISTS = 4;
    	
    	/**
    	 * 用户请求的订单不存在
    	 */
    	public static final int REQUEST_ORDER_NOT_EXITS = 5;
    	
    	/**
    	 * 用户请求的子订单不存在
    	 */
    	public static final int REQUEST_ORDERDETAIL_NOT_EXITS = 6;
    	
    	/**
    	 * 用户请求的子订单状态不正确
    	 */
    	public static final int REQUEST_ORDERDETAIL_INVALID_STATUS = 7;
    	
    	/**
    	 * 用户必须申请过退款才能申请维权
    	 */
    	public static final int REQUEST_REFUND_FIRST = 8;

    	/**
    	 * 官方已经判定
    	 */
    	public static final int OFFICIAL_JUDGED_ALREADY = 9;

    	/**
    	 * 修改价格大于原价
    	 */
    	public static final int PRICE_MODIFY_ILLEGAL = 10;
    	
    	/**
    	 * 优惠券异常
    	 */
    	public static final int ORDER_COUPON_ERROR = 11;
    	
    	
    	/**
    	 * 积分异常
    	 */
    	public static final int ORDER_SCORE_ERROR=12;
    }
}
