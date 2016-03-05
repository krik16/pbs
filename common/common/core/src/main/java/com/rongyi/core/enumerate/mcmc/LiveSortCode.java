package com.rongyi.core.enumerate.mcmc;

public enum LiveSortCode {
	/**
	 * 预热
	 */
	READY(0),

	/**
	 * 上架
	 */
	ON(0),

	/**
	 * 下架
	 */
	OFF(2);

	private int value;

	private LiveSortCode(int value) {
		this.value = value;
	}

	/**
	 * 通过LiveStatus 转成 LiveSortCode
	 * @param status
	 * @return
	 */
	public static LiveSortCode fromStatus(LiveStatus status) {
		if (status != null) {
			switch (status) {
			case READY:
				return LiveSortCode.READY;
			case ON:
				return LiveSortCode.ON;
			case OFF:
				return LiveSortCode.OFF;
			}
		}
		return null;
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
