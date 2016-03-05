package com.rongyi.core.enumerate.mcmc;

/**
 * 直播状态
 * 
 * @author rongyi
 *
 */
public enum LiveStatus {	
	/**
	 * 预热
	 */
	READY(0),

	/**
	 * 上架
	 */
	ON(1),

	/**
	 * 下架
	 */
	OFF(2);
	 
	private int value;

	private LiveStatus(int value) {
		this.value = value;
	}
	
	/**
	 * int 转换为 LiveStatus
	 * 
	 * @param v
	 * @return
	 */
	public static LiveStatus fromValue(int v) {
		switch (v) {
		case 0:
			return LiveStatus.READY;
		case 1:
			return LiveStatus.ON;
		case 2:
			return LiveStatus.OFF;
		default:
			return null;
		}
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
