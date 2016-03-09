/** 
* @Title: VirtualAccountEventTypeEnum.java 
* @Package com.rongyi.core.constant 
* @Description: TODO
* @author 郑亦梁  zhengyiliang@rongyi.com
* @date 2015年5月26日 上午11:36:29 
* @version V1.0   
* Copyright (C),上海容易网电子商务有限公司
*/
package com.shouyingbao.pbs.core.constant;

/**
 * @author ZhengYl
 *
 */
public enum VirtualAccountEventTypeEnum {
	DRAWAPPLY("0", "提现"),
	ACCOUNT_POST("1", "金额入账"),
	COMMISSION_BATCH_POST("2", "佣金批量入账"),
	COUPON_COMMISSION_BATCH_POST("3","优惠券核销佣金批量入账");
	
	private String code;
	private String value;

	private VirtualAccountEventTypeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}
