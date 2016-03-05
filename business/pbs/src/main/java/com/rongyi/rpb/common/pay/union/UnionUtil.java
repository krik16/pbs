/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月3日上午9:49:27
 * @Description: TODO
 *
 **/

package com.rongyi.rpb.common.pay.union;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.rongyi.rpb.constants.ConstantEnum;

/**
 * @Author: 柯军
 * @Description: unionUtil
 * @datetime:2015年7月3日上午9:49:27
 * 
 **/

public class UnionUtil {

	/**
	 * @Description: 获取加密签名所需的拼接字符串
	 * @param params
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月3日上午9:50:37
	 **/
	public static String getParamPlain(Map<String, Object> paramsMap) {
		StringBuilder sb = new StringBuilder();
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			sb.append(key);
			sb.append('=');
			sb.append(paramsMap.get(key));
			sb.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * @Description: 获取加密签名所需的拼接字符串,value 是字符串数组
	 * @param paramsMap
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月6日下午2:44:06
	 **/
	public static String getParamPlain2(Map<String, String[]> paramsMap) {
		StringBuilder sb = new StringBuilder();
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			sb.append(key);
			sb.append('=');
			if (paramsMap.get(key).length > 0)
				sb.append(paramsMap.get(key)[0]);
			sb.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public static String getParamPlain3(Map<String,String> paramsMap) {
		StringBuilder sb = new StringBuilder();
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			sb.append(key);
			sb.append('=');
			sb.append(paramsMap.get(key));
			sb.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * @Description: 验证请求数据合法性
	 * @param paramData
	 * @param sign
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月6日下午2:39:51
	 **/
	public static boolean verify(String paramData, String sign) {
		return com.chinaums.ysmktaln.mktaln4sp.Service.verify(paramData, sign, ConstantEnum.UNION_PUBLIC_KEY.getValueStr());
	}

}
