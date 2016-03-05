package com.rongyi.rpb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.common.util.JsonUtil;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.service.RpbEventService;

/**
 * Author: 柯军
 * Description: MQ接口信息类
 * datetime:2015年4月23日上午10:05:06
 * 
 **/
@Service
public class RpbEventServiceImpl extends BaseServiceImpl implements RpbEventService {

	@Autowired
	PaymentService paymentService;

	@Override
	@SuppressWarnings("unchecked")
	public MessageEvent messageToMessageEvent(String message) {
		MessageEvent event = new MessageEvent();
		Map<String, Object> eventMap = JsonUtil.getMapFromJson(message);
		event.setBody(eventMap.get("body"));
		event.setSource((eventMap.get("source") != null) ? eventMap.get("source").toString() : null);
		event.setTarget((eventMap.get("target") != null) ? eventMap.get("target").toString() : null);
		if (eventMap.get("timestamp") != null)
			event.setTimestamp(Long.valueOf(eventMap.get("timestamp").toString()));
		event.setType((eventMap.get("type") != null) ? eventMap.get("type").toString() : null);
		return event;
	}

	@Override
	public MessageEvent getMessageEvent(String payNo, String orderNum, String orderDetailNum, String payChannel,String payAccount, String source, String target, String type) {
		MessageEvent event = new MessageEvent();
		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("orderNum", orderNum);
		if (StringUtils.isNotEmpty(orderDetailNum))
			bodyMap.put("orderDetailNum", orderDetailNum);
		else
			bodyMap.put("orderDetailNum", "");
		if(StringUtils.isNotEmpty(payAccount))
			bodyMap.put("payAccount", payAccount);
		else
			bodyMap.put("payAccount", "");
		bodyMap.put("paymentId", payNo);
		bodyMap.put("payChannel", payChannel);
		event.setBody(bodyMap);
		event.setSource(source);
		event.setTarget(target);
		event.setTimestamp(DateUtil.getCurrDateTime().getTime());
		event.setType(type);
		return event;
	}

}
