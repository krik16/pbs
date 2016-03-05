package com.rongyi.rpb.service;

import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;
/**
 * @Author: 柯军
 * @Description: mq消息处理类
 * @datetime:2015年5月29日下午6:17:54
 * 
 **/
public interface MqReceiverService {

	/**	
	 * @Description: mq回调函数 
	 * @param message
	 * @param channel
	 * @param messageString	
	 * @Author:  柯军
	 * @datetime:2015年6月25日下午4:07:57
	 **/
	public abstract void callBack(Message message, Channel channel, String messageString);

}