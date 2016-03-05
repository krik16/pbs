/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年5月29日下午6:17:54
 * @Description: TODO
 *
 **/

package com.rongyi.rpb.service.impl;

import com.rabbitmq.client.Channel;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.service.*;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: 柯军
 * Description: mq消息处理类
 * datetime:2015年5月29日下午6:17:54
 * 
 **/
@Service
public class MqReceiverServiceImpl implements MqReceiverService {

	private static final Logger LOGGER = Logger.getLogger(MqReceiverServiceImpl.class);

	private static final List<String> payTypeList = new ArrayList<>();

	@Autowired
	PaymentService paymentService;

	@Autowired
	WeixinPayService weixinPayService;

	@Autowired
	PCWebPageAlipayService pcWebPageAlipayService;

	@Autowired
	RpbEventService rpbEventService;

	@Autowired
	Sender sender;

	@Override
	public void callBack(Message message, Channel channel, String messageString) {
		MessageEvent event = rpbEventService.messageToMessageEvent(messageString);
		if (Integer.valueOf(event.getType()) <= 10) {
			business(message, channel, event);
		} else {
			business2(message, channel, event);
		}
	}

	/**
	 * Description: 此处业务处理版本一中的支付业务（包括支付宝支付退款，微信支付退款等）
	 * @param message Message
	 * @param channel Channel
	 * @param event MessageEvent
	 * Author: 柯军
	 * datetime:2015年6月3日上午10:32:08
	 **/
	private void business(Message message, Channel channel, MessageEvent event) {
		Map<String, Object> messageMap = paymentService.getSendMessage(event);
		if (messageMap != null && isAppPay(event.getType())) {// 支付消息回复
			sender.rpcSend(messageMap, message, channel);
		}
	}

	/**
	 * Description: 此处处理版本二中的佣金提现及生成支付页面退付款
	 * @param message Message
	 * @param channel Channel
	 * @param event MessageEvent
	 * Author: 柯军
	 * datetime:2015年6月3日上午10:33:02
	 **/
	private void business2(Message message, Channel channel, MessageEvent event) {
		if (PaymentEventType.DRAW_PAY.equals(event.getType()) || PaymentEventType.EXCE_PAY.equals(event.getType())) {// 提现(或异常支付)生成付款记录
			paymentService.insertDrawApply(event);
			return;
		}
		if (PaymentEventType.OUTER_TO_PAY.equals(event.getType())) {// 获取付款（退款）html页面
			Map<String, Object> map = pcWebPageAlipayService.getHtmlMap(event);
			sender.rpcSend(map, message, channel);
			LOGGER.info("返回给" + event.getSource() + "支付html字符串：" + map.toString());
		}
	}

	/**
	 * Description: 获取osm routing key
	 * return
	 * Author: 柯军
	 * datetime:2015年6月2日下午1:38:36
	 **/
	@SuppressWarnings("unchecked")
//	public static String getOSMRoutingKey(MessageEvent event) {
//		Map<String, Object> bodyMap = (Map<String, Object>) event.getBody();
//		String orderNo = (String) bodyMap.get("orderNum");
//		return OSMRoutingUtil.getOsmRoutingKey(orderNo);
//	}

	/**
	 * @Description: 验证是否是支付请求
	 * @param type
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月5日下午3:14:52
	 **/
	public static boolean isAppPay(String type) {
		return payTypeList.contains(type);
	}

	static {
		payTypeList.add(PaymentEventType.PAYMENT);
		payTypeList.add(PaymentEventType.APP);
		payTypeList.add(PaymentEventType.WEIXIN_PAY);
		payTypeList.add(PaymentEventType.UNION_PAY);
	}
}
