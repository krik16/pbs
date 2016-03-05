package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import base.BaseTest;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.rpb.mq.Sender;

public class MQTest extends BaseTest {

	@Autowired
	Sender sender;

	@Test
	public void testSendMQToCoupon() {

		MessageEvent event = new MessageEvent();
		event.setSource("rpb");
		event.setTimestamp(DateUtil.getCurrDateTime().getTime());
		event.setType("4");
		event.setTarget("coupon_order_queue");
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		for (int i = 0; i < 1; i++) {
			bodyMap.put("paymentId", "100000" + i);
			bodyMap.put("payChannel", "3");
			bodyMap.put("orderNum", "20150805" + i);
			bodyMap.put("orderDetailNum", i);
			event.setBody(bodyMap);
			sender.convertAndSend(event);
		}

	}
}
