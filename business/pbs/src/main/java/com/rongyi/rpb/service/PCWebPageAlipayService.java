package com.rongyi.rpb.service;

import java.util.List;
import java.util.Map;

import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
/**
 * @Author: 柯军
 * @Description: PC端退付款实现
 * @datetime:2015年4月23日上午10:04:32
 * 
 **/
public interface PCWebPageAlipayService {

	/**
	 * 单条退款
	 * 
	 * @param
	 * @return token
	 */
	public abstract Map<String, Object> getOnePayInfo(String payNo, String totalFee, String buyerEmail, String buyerName, String desc);

	/**
	 * 批量付款获取付款信息列表
	 * 
	 * @author kejun 2015年3月31日
	 * @param
	 * @return
	 */
	public abstract List<Map<String, Object>> getBatchPayBuyerMessage(String[] idArray, String desc);

	/**
	 * 批量付款
	 * 
	 * @author kejun 2015年4月1日
	 * @param buyerList
	 * @return
	 */
	public abstract Map<String, Object> getBatchPayInfo(List<Map<String, Object>> buyerList);

	/**
	 * 单条退款
	 * 
	 * @param orderId订单号
	 * @param batch_num退款笔数
	 * @param price价格
	 * @param tradeNo交易号
	 * @param desc交易描述
	 * @return
	 */
	/**
	 *
	 * @param payNo
	 * @param batch_num
	 * @param price
	 * @param tradeNo
	 * @param desc
	 * @return
	 */
	public abstract Map<String, Object> getRefunInfo(PaymentEntity paymentEntity, String batch_num, String price, String tradeNo, String desc);

	/**
	 * 批量退款获取付款信息列表
	 * 
	 * @author kejun 2015年3月31日
	 * @param desc
	 * @return
	 */
	public abstract List<Map<String, Object>> getBatchRefundBuyerMessage(String[] idArray, String desc);

	/**
	 * 批量退款接口
	 * 
	 * @author kejun 2015年4月1日
	 * @param refundList
	 * @return
	 */
	public abstract Map<String, Object> getBatchRefunInfo(List<Map<String, Object>> refundList);

	/**
	 * @Description: 获取支付（付款）html页面
	 * @param event
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年5月19日下午5:37:16
	 **/
	public abstract Map<String, Object> getHtmlMap(MessageEvent event);

	/**
	 * 生成满足支付宝要求格式的批次号(8位时间格式加至少三位的订单号格式)
	 * 
	 * @param
	 * @return
	 */
	public abstract String getBatchNo(String payNo);
	
}