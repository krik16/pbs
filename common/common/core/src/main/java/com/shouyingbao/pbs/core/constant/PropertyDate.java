package com.shouyingbao.pbs.core.constant;

import java.util.HashMap;
import java.util.Map;

public class PropertyDate {

	public PropertyDate(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Map<String,String> toMap(PropertyDate[] pd) {
		Map<String,String> map = new HashMap<String,String>(pd.length);
		for(PropertyDate p : pd) {
			map.put(p.getKey(), p.getValue());
		}
		return map;
	}
}
