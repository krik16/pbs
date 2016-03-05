package com.rongyi.rpb.common.pay.weixin.service;

import com.rongyi.rpb.common.pay.weixin.model.ScanPayQueryReqData;
import com.rongyi.rpb.common.pay.weixin.util.Configure;

/**
 * User: rizenguo Date: 2014/10/29 Time: 16:04
 */
public class PayQueryService extends BaseService {

	public PayQueryService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		super(Configure.PAY_QUERY_API);
	}

	/**
	 * 请求支付查询服务
	 * 
	 * @param scanPayQueryReqData
	 *            这个数据对象里面包含了API要求提交的各种数据字段
	 * @return API返回的XML数据
	 * @throws Exception
	 */
	public String request(ScanPayQueryReqData scanPayQueryReqData,Configure configure) throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Post请求到API地址
		// --------------------------------------------------------------------
		String responseString = sendPost(scanPayQueryReqData,configure);

		return responseString;
	}

}
