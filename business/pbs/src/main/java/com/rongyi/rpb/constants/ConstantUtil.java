package com.rongyi.rpb.constants;

import com.rongyi.core.common.PropertyConfigurer;
import com.rongyi.core.framework.spring.context.utils.SpringContextUtil;

/**
 * 常量
 * Author kejun
 */
public interface ConstantUtil {
	PropertyConfigurer propertyConfigurer = (PropertyConfigurer) SpringContextUtil.getBean("propertyConfigurer");
	interface NOTIFY_URL {
		String outernetAddress = propertyConfigurer.getProperty("outernet.address").toString();
		String innernetAddress = propertyConfigurer.getProperty("innernet.address").toString();
	}


	interface PayZhiFuBao {
//		String PARTNER = "2088701977493485"; // 商户签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
//		String SELLER_ID = "hr@rongyi.com"; // 卖家支付宝帐号
//		String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKzzEv3f+2RSlEaXSlVEp2/ZRVa6EZavxfeqB6s71slX8wuSFrkdx2j2BWpMMhGYM0fld1ZD5MF4bFMAozH62TUHfRxswc/AFMf4tTV3jzchNeRCxDmnI+Fu6f0mTvw5lXcFoIyAb0pBXWDioB8/S5bchMLr3EUF9B890zTd4gBLAgMBAAECgYBCLgmYaDsUWp0unfntwwWnQSbNVwof9Ub1zHR+y7kW9rakONTFJWYG/g14bYvDO9BztJUDx5sjhWc+I8JO/8RZaxVClKUY9pGtC9Waa1Sy/77i+uyghf2Gvc7UvWzRLpdaEln8iPteTapKbM5XpSHemjjIAiWbSiiC6aGD/Kta0QJBAOBDKnmwcGa0CzRP0W+Ur9YAapTJvHeXk1tZnaG1Mqk4Tv5KVFQNXJlt6uOQCjB91z2ycAQPKkct3U9fgvL9hcUCQQDFbOMg0va9Sep2vwlAMZZ/1+Er+LKwyGH3/GPiawxaB9mshkU1odF4kEJAD3xO9OjhfLIObslzLMb7qCs1et7PAkBbtRu5h51vYlDyo3FR38vIB4OONLENdr6brJn5ds+ww4YKFvtFI/GqDHjxdgW0rsFGSaHlBw6S7P6DZtt14pbBAkAT18TgWLPaBKLfOoY4gINq6AzGuaFkKU/HeAOmDzJXnjBfqcMtB82g+4bdH3FUEkOR3lGbJ8FxpQ9+3cEyB957AkEAqaEy5vffZVfxS6oAFC4cL8M3n6bKkQbq2Q14irks38zPRhKLNayBr1QUlGgVQ1h5fS8/EOzo1fhaKtEhYpgzHA==";
//		String ALI_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		
		String PARTNER = propertyConfigurer.getProperty("ALI.PARTNER").toString(); // 商户签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
		String SELLER_ID = propertyConfigurer.getProperty("ALI.SELLER_ID").toString(); // 卖家支付宝帐号
		String PRIVATE_KEY = propertyConfigurer.getProperty("ALI.PRIVATE_KEY").toString();
//		String ALI_PUBLIC_KEY = propertyConfigurer.getProperty("ALI.ALI_PUBLIC_KEY").toString();
//		String KEY = propertyConfigurer.getProperty("ALI.KEY").toString();
		String ACCOUNT_FULL_NAME = propertyConfigurer.getProperty("ACCOUNT_FULL_NAME").toString();

		String SDK_SERVICE = "mobile.securitypay.pay";
		String INPUT_CHARSET = "utf-8";
//		String UPDATE_BY = "用户";
		String SIGNTYPE = "MD5";
	}

	interface ZhiFuBaoWebPage {
		String AUTHORIZATION_SERVICE = "alipay.wap.trade.create.direct"; // 支付宝授权接口
		String TRADE_SERVICE = "alipay.wap.auth.authAndExecute"; // 支付宝交易接口
		String FORMAT = "xml"; // 请求参数格式XML
		String VERSION = "2.0"; // 接口版本号
		// String SIGN_TYPE = "0001"; //
		// 签名方式，选择项：0001(RSA)、MD5。无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA
		String SIGN_TYPE = "MD5";
		String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";// 支付宝网关地址
		
		String ALIPAY_QUERY_ORDER_GATEWAY = "https://www.alipay.com/cooperate/gateway.do?"; //订单查询接口
		String QUERY_SERVICE = "single_trade_query";//支付宝查询服务--单笔查询服务(不可修改)
		String QUERY_REFUND_SERVICE="refund_fastpay_query";//支付宝退款查询接口
		
	}

	interface PCZhiFuBaoWebPage {// 支付宝PC端批量付款
		String AUTHORIZATION_SERVICE = "batch_trans_notify"; // 支付宝授权接口
//		String TRADE_SERVICE = "alipay.wap.auth.authAndExecute"; // 支付宝交易接口
		// 签名方式，选择项：0001(RSA)、MD5。无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA
//		String SIGN_TYPE = "MD5";

//		String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
//		String ALIPAY_GATEWAY_NEW2 = "http://wappaygw.alipay.com/service/rest.htm?";// 支付宝网关地址
		String NOTIFY_URL_ZHIFUBAO_PC_WEB = "http://" + NOTIFY_URL.outernetAddress + "/v5/PCWebPageAlipay/pay_notify_url.htm";// 支付宝异步回调通知路径
//		String RETURN_URL_ZHIFUBAO_PC_WEB = "http://" + NOTIFY_URL.innernetAddress + "/v5/PCWebPageAlipay/refund_call_back.htm";// 支付宝同步返回页面路径
//		String PAYMENT_TYPE = "1";// 支付类型
//		String ACCOUNT_NAME = "容易网";
//		String ACCOUNT_FULL_NAME = "上海容易网电子商务有限公司";
//		String ACCOUNT_FULL_NAME = "上海摩购电子商务有限公司";

	}

	interface PCRefundWebPage {// 支付宝PC端即时交易接口
		String AUTHORIZATION_SERVICE = "refund_fastpay_by_platform_pwd"; // 支付宝授权接口
//		String TRADE_SERVICE = "alipay.wap.auth.authAndExecute"; // 支付宝交易接口
		// 签名方式，选择项：0001(RSA)、MD5。无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA
//		String SIGN_TYPE = "MD5";

		String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
//		String ALIPAY_GATEWAY_NEW2 = "http://wappaygw.alipay.com/service/rest.htm?";// 支付宝网关地址
		String NOTIFY_URL_ZHIFUBAO_PC_WEB = "http://" + NOTIFY_URL.outernetAddress + "/v5/PCWebPageAlipay/refund_notify_url.htm";// 支付宝异步回调通知路径
//		String PAYMENT_TYPE = "1";// 支付类型

	}

	interface PayNetAdress {
		// 支付宝网页支付主动通知商户网站里指定的页面http路径
		String CALL_BACK_URL_WEB = "http://" + NOTIFY_URL.innernetAddress + "/v5/WebPageAlipay/call_back.htm";
		String NOTIFY_URL_ZHIFUBAO_APP = "http://" + NOTIFY_URL.outernetAddress + "/v5/WebPageAlipay/app/notify_url.htm";
		String NOTIFY_URL_ZHIFUBAO_WEB = "http://" + NOTIFY_URL.outernetAddress + "/v5/WebPageAlipay/notify_url.htm";
		String MERCHANT_URL_WEB = "http://" + NOTIFY_URL.innernetAddress + "/v5/WebPageAlipay/merchant_url.htm";
	}
	/**
	 * Author:  柯军
	 * Description: V3版本微信常量配置
	 * datetime:2015年9月1日下午6:29:53
	 *
	 **/
	interface PayWeiXin_V3{
		
		  String APP_ID = propertyConfigurer.getProperty("WEIXIN.APP_ID").toString();
		  String KEY = propertyConfigurer.getProperty("WEIXIN.KEY").toString();
		  String MCH_ID = propertyConfigurer.getProperty("WEIXIN.MCH_ID").toString();
		 String cretDirectory = propertyConfigurer.getProperty("cret.directory").toString();
//		  String PASSWORD = propertyConfigurer.getProperty("WEIXIN.PASSWORD").toString();

		public static String  WEIXIN_NOTIFY_URL = "http://" + NOTIFY_URL.outernetAddress + "/v5/WebPageAlipay/weixin/notify_url.htm";//支付成功异步通知接口
		

		
	}
}
