package com.rongyi.rpb.service;

import java.util.Map;

import com.rongyi.rpb.common.pay.weixin.model.PaySignData;
import org.json.JSONException;

import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.easy.rpb.vo.WeixinQueryOrderParamVO;
import com.rongyi.rpb.common.pay.weixin.model.RefundQueryResData;
import com.rongyi.rpb.common.pay.weixin.model.RefundResData;

/**
 * @Author: 柯军
 * @Description: 微信支付
 * @datetime:2015年4月23日上午10:07:12
 * 
 **/
public interface WeixinPayService {

	/**
	 * 获取微信支付签名
	 * 
	 * @author kejun 2015年4月2日
	 * @param paySignData
	 * @return
	 */
	Map<String, Object> getAppWeXinSign(PaySignData paySignData);

	/**
	 * 验证是否是微信退款，是则返回true
	 * 
	 * @param event
	 * @return
	 */
	 boolean validateIsWeixinPay(MessageEvent event);


	/**
	 * mq接收微信退款消息
	 * 
	 * @param paymentEntityVO
	 * @param hisPayEntity
	 */
	 boolean messageToRefund(PaymentEntityVO paymentEntityVO, PaymentEntity hisPayEntity);

	/**
	 * 
	 * 微信退款
	 * 
	 * @param payNo 历史付款单号
	 * @param totalFee 历史付款单总额
	 * @param refundFee 退款总额
	 * @param newPayNo 付款单号
	 * @param tradeType 交易类型（0:购买 1:退款 2:打款给卖家 3:提现 4:异常支付 5:重复支付 6：重复支付退款）
	 */
	 Map<String,Object>  weixinRefund(String payNo, double refundFee, double totalFee, String newPayNo,Integer tradeType,Integer weixinMchId);

	 void savePaymentLogInfo(RefundResData refundResData, Integer tradeType);

	/**
	 * 微信关闭订单
	 */
	 void closeOrder(String payNo,Integer weixinMchId);

	/**
	 * @Description: 查询订单
	 * @param tradeNo 交易流水号
	 * @param payNo 付款单号
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年8月10日下午2:28:05
	 **/
	 WeixinQueryOrderParamVO queryOrder(String tradeNo,String payNo,Integer weixinMchId);

	/**	
	 * @Description: 微信定时批量退款 
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月27日上午10:38:00
	 **/
	 void batchTriggerWeixinRefund();
	

}