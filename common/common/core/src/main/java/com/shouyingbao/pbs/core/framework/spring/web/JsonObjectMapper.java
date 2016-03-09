package com.shouyingbao.pbs.core.framework.spring.web;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonObjectMapper extends ObjectMapper {

	public JsonObjectMapper() {
		//忽略 前端js对象的属性 序列化成 java对象没有对应的get set 方法 映射错误
		configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
