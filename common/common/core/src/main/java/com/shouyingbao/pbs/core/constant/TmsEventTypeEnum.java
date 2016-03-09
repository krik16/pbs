/** 
* @Title: TmsEventTypeEnum.java 
* @Package com.rongyi.core.constant 
* @Description: TMS消息类型枚举
* @author 郑亦梁  zhengyiliang@rongyi.com
* @date 2015年5月22日 下午5:15:01 
* @version V1.0   
* Copyright (C),上海容易网电子商务有限公司
*/
package com.shouyingbao.pbs.core.constant;

/**
 * @author ZhengYl
 *
 */
public enum TmsEventTypeEnum {
	DRAWAPPLY("0", "提现"),
	COMMISSION_POST("1", "佣金上传"),
	COUPON_COMMISSION("2","优惠券佣金"),
	INVITE_COMMISSION("3","邀请佣金");
	

	private String code;
	private String value;

	private TmsEventTypeEnum(String code, String value) {
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
