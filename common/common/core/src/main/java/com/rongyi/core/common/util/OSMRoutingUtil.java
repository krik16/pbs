/** 
 * @Title: OSMRoutingUtil.java 
 * @Package com.rongyi.core.common.util 
 * @Description: 根据订单号的奇偶转发到不同的osm消息队列
 * @author 郑亦梁  zhengyiliang@rongyi.com
 * @date 2015年6月1日 下午6:03:33 
 * @version V1.0   
 * Copyright (C),上海容易网电子商务有限公司
 */
package com.rongyi.core.common.util;

/**
 * @author ZhengYl
 * 
 */
public class OSMRoutingUtil {

	private static String ODD_QUEUE_NAME = "odd_osm";
	private static String EVEN_QUEUE_NAME = "even_osm";

	public static String getOsmRoutingKey(String orderNo) {
		String key = "";
		try {
			long orderNum = Long.parseLong(orderNo);
			if ((orderNum & 1) == 0) {
				key = EVEN_QUEUE_NAME;
			} else {
				key = ODD_QUEUE_NAME;
			}
		} catch (NumberFormatException e) {
			key = ODD_QUEUE_NAME;
		}
		return key;

	}

}
