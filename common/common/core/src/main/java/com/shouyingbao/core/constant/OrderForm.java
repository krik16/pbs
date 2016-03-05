package com.shouyingbao.core.constant;

public interface OrderForm {
	/** 生成订单几个状态 */
	interface GenFormStatus {
		/** 成功生成订单 */
		String success = "0";
		/** 等待生成 */
		String wait = "1";
		/** 生成失败 */
		String fail = "3";
	}

}
