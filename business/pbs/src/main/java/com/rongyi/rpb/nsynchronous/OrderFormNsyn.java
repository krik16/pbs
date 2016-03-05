package com.rongyi.rpb.nsynchronous;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderFormNsyn {

	private static final Logger logger = LoggerFactory.getLogger(OrderFormNsyn.class);

	/**
	 * 异步更新订单价格
	 * 
	 * @param orderNumArray
	 */
	public void updateOrderPrice(final String orderNumArray) {

	}

	public int validateAccount(String orderNo, Integer payChannel) {
		return  0;
	}
}
