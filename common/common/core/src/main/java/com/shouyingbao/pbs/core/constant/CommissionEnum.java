/**
 * 
 */
package com.shouyingbao.pbs.core.constant;

/**
 * Copyright (C),上海容易网电子商务有限公司 author chenjun Description ：佣金事件类型 time
 * ：2015年5月27日 History: 变更记录 <author> <time> <version> <desc> chenjun 2015年5月27日
 * 1.0 创建文件
 */
public enum CommissionEnum {

	COMMISSION_ISSUE(0, "佣金发放"),
	COMMISSION_AWARD(1, "奖励佣金"),
	COMMISSION_CHECK_FAIlURE(2, "佣金审核失败"),
	COMMISSION_VALID(3, "验码佣金");

	private Integer code;

	private String value;

	CommissionEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
