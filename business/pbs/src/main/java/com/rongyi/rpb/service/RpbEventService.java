package com.rongyi.rpb.service;

import com.rongyi.easy.mq.MessageEvent;

/**
 * @Author: 柯军
 * @Description: MQ接口信息类
 * @datetime:2015年4月23日上午10:05:06
 * 
 **/
public interface RpbEventService {

	/**
	 * 事件消息转换为RpbEvent对象
	 * 
	 * @param message
	 * @return
	 */
	public abstract MessageEvent messageToMessageEvent(String message);

	/**
	 * 支付成功后回调此函数，将结果通知给订单系统
	 * 
	 * @param payId付款id
	 * @param orderNum订单号
	 * @param orderDetailNum子订单号
	 *            (以逗号分隔)
	 * @param source数据源
	 * @param target数据目的地
	 * @param type支付类型
	 */
	public abstract MessageEvent getMessageEvent(String payNo, String orderNum, String orderDetailNum, String payChannel,String payAccount, String source, String target, String type);
}