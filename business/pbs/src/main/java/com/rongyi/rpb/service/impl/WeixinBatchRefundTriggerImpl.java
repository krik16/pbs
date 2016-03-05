/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年9月14日上午11:07:32
 * @Description: TODO
 *
 **/

package com.rongyi.rpb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.rpb.service.WeixinPayService;
import com.rongyi.rss.service.trigger.WeixinBatchRefundTrigger;

/**	
 * Author:  柯军
 * Description: 微信定时打款
 * datetime:2015年9月14日上午11:07:32
 *
 **/
@Service
public class WeixinBatchRefundTriggerImpl implements WeixinBatchRefundTrigger{

	@Autowired
	WeixinPayService weixinPayService;
	@Override
	public void batchRefundTrigger() {
		weixinPayService.batchTriggerWeixinRefund();
	}

}
