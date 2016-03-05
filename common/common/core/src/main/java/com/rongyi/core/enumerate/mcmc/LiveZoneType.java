package com.rongyi.core.enumerate.mcmc;

public enum LiveZoneType {
	/**
	 * 商场
	 */
	MALL(0),
	
	/**
	 * 店铺
	 */
	SHOP(1);

	private int value;
	
	private LiveZoneType(int value) {
		this.value = value;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
