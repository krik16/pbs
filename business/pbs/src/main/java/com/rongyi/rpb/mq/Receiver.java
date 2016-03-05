package com.rongyi.rpb.mq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rongyi.core.common.util.DateUtil;
import com.rongyi.rpb.service.MqReceiverService;

/**	
 * Author:  柯军
 * Description: mq 消息接收器
 * datetime:2015年6月25日下午4:37:28
 *
 **/
@Component
public class Receiver implements ChannelAwareMessageListener {

	@Autowired
	MqReceiverService mqReceiverService;

    private static final Logger LOGGER = Logger.getLogger(Receiver.class);


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
    	LOGGER.info("消息接收到时间-->"+DateUtil.getCurrDateTime().getTime());
        String messageString = new String(message.getBody());
        LOGGER.info("接收消息：" + messageString);
        try {
            mqReceiverService.callBack(message, channel, messageString);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("消息处理失败，失败原因：");
            e.printStackTrace();
        }
    }
}
