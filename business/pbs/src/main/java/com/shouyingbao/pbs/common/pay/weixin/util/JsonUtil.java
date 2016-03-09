package com.shouyingbao.pbs.common.pay.weixin.util;

import org.json.JSONObject;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

public class JsonUtil {

	private JsonUtil() {

	}

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	public static String getJsonValue(String rescontent, String key) {
		JSONObject jsonObject;
		String v = null;
		try {
			jsonObject = new JSONObject(rescontent);
			v = jsonObject.getString(key);
		} catch (Exception e1) {
			LOGGER.error(e1);
		}
		return v;
	}
}
