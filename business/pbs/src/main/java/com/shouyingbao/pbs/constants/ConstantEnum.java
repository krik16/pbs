/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年5月19日下午3:45:21
 * @Description: TODO
 *
 **/

package com.shouyingbao.pbs.constants;

/**
 * @Author:  柯军
 * @Description: 常量枚举
 * @datetime:2015年7月2日下午4:58:18
 *
 **/
public enum ConstantEnum {


	WEIXIN_REFUND_RESULT_SUCCESS("SUCCESS","退款成功!"),
	WEIXIN_REFUND_RESULT_PROCESSING("PROCESSING","微信退款处理中!"),
	WEIXIN_REFUND_RESULT_NOTSURE("NOTSURE","退款未确认,需重新发起退款!"),
	WEIXIN_REFUND_RESULT_CHANGE("CHANGE","退款转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款!"),
	WEIXIN_REFUND_RESULT_FAIL("FAIL","退款失败!"),

	EXCEPTION_PARAM_NULL("1019101","参数为空或者不合法"),
	EXCEPTION_ALI_PAY_SCAN_FAIL("1019102","支付宝扫码支付失败"),
	EXCEPTION_ALI_QUERY_ORDER("1019103","订单查询失败"),
	EXCEPTION_WEIXIN_SCAN_FAIL("1019104","微信扫码支付失败"),
	EXCEPTION_WEIXIN_REFUND_QUERY_ORDER("1019105","微信退款查询失败"),
	EXCEPTION_WEIXIN_REFUND_FAIL("1019106","微信退款失败"),
	EXCEPTION_WEIXIN_ORDER_CLOSE("1019107","微信订单关闭失败"),
	EXCEPTION_WEIXIN_QUERY_ORDER("1019108","微信订单查询失败"),
	EXCEPTION_MCH_NOT_EXIST("1019109","门店未找到微信商户信息"),
	EXCEPTION_USER_UN_PAY("1019110","用户取消订单"),
	ERROR_LOGIN("1019111","用户名不存在或密码错误"),
	EXCEPTION_MCH_SHOP_NOT_EXIST("1019112","收款用户未找到符合条件的门店账号"),

	ALI_PAY_TIME_EXPIRE_GOODS("60m","商品支付宝支付默认超时时间"),
	ALI_PAY_TIME_EXPIRE_COUPONS("15m","代金券支付宝支付默认超时时间"),
	WEIXIN_PAY_TIME_EXPIRE_GOODS(60,"商品微信支付默认超时时间"),
	WEIXIN_PAY_TIME_EXPIRE_COUPONS(15,"代金券微信宝支付默认超时时间"),

	IS_DELETE_0(0,"正常"),
	IS_DELETE_1(1,"标记删除"),

	PAY_CHANNEL_0(0,"支付宝"),
	PAY_CHANNEL_1(1,"微信"),

	PAY_TYPE_0(0,"支付宝收款"),
	PAY_TYPE_1(1,"微信收款"),
	PAY_TYPE_2(2,"支付宝扫固码"),
	PAY_TYPE_3(3,"微信扫固码"),

	PAY_STATUS_0(0,"未付款"),
	PAY_STATUS_1(1,"付款中"),
	PAY_STATUS_2(2,"已付款"),

	PAY_TRADE_TYPE_0(0,"收款"),
	PAY_TRADE_TYPE_1(1,"退款"),

	WEIXIN_SCAN_PAY_BODY("test","微信扫码支付商品名称"),
	ALI_SCAN_PAY_BODY("收款","支付宝扫码支付商品名称"),

	USER_IS_EMPLOYEE_0(0,"员工"),
	USER_IS_EMPLOYEE_1(1,"商户"),

	ALI_PAY_SUBJECT("subject","收钱"),

	WEIXIN_PAY_TRADE_TYPE_MICROPAY(0,"MICROPAY"),//即扫码支付

;

    private Object code;

    private Object value;

    private ConstantEnum(Object code, Object value) {
        this.code = code;
        this.value = value;
    }


    /**	
     * @Description: 获取code int类型值,请确保该值可转换为int值，否则会类型转换异常 
     * @return	
     * @Author:  柯军
     * @datetime:2015年5月19日下午3:51:11
     **/
    public Integer getCodeInt() {
        return Integer.valueOf(code.toString());
    }
    
    /**	
     * @Description: 获取code String类型值
     * @return	
     * @Author:  柯军
     * @datetime:2015年5月19日下午3:53:36
     **/
    public String getCodeStr() {
        return code.toString();
    }


    /**	
     * @Description:  获取value int类型值,请确保该值可转换为int值，否则会类型转换异常 
     * @return	
     * @Author:  柯军
     * @datetime:2015年5月19日下午3:53:08
     **/
    public Integer getValueInt() {
        return Integer.valueOf(value.toString());
    }
    
    /**	
     * @Description: 获取value String类型值 
     * @return	
     * @Author:  柯军
     * @datetime:2015年5月19日下午3:53:21
     **/
    public String getValueStr() {
        return value.toString();
    }

	/**
	 * @Description: 获取code byte类型值,请确保该值可转换为byte值，否则会类型转换异常
	 * @return
	 * @Author:  柯军
	 * @datetime:2015年5月19日下午3:51:11
	 **/
	public Byte getCodeByte() {
		return Integer.valueOf(code.toString()).byteValue();
	}

	/**
	 * @Description:  获取value byte类型值,请确保该值可转换为byte值，否则会类型转换异常
	 * @return
	 * @Author:  柯军
	 * @datetime:2015年5月19日下午3:53:08
	 **/
	public Byte getValueByte() {
		return Integer.valueOf(value.toString()).byteValue();
	}
    
    public Object getCode() {
        return code;
    }

    
    public Object getValue() {
        return value;
    }
}
