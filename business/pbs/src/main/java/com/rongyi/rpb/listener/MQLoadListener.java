package com.rongyi.rpb.listener;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * mq server 启动类
 * 
 * @author 柯军
 */
@Component
public class MQLoadListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = Logger.getLogger(MQLoadListener.class);

	@Autowired
	SimpleMessageListenerContainer container;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!container.isActive()) {
			container.start();
			logger.info("message receiver Starting...");
		}

	}

}
