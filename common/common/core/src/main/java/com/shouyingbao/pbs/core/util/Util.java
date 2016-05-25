package com.shouyingbao.pbs.core.util;

import com.shouyingbao.pbs.core.common.third.md5.Md5Util;
import com.shouyingbao.pbs.core.constant.Const;

import java.util.*;

/**
 * 签名公共方法
 * 
 * @author xiaobo
 *
 */
public class Util {

	/**
	 * 签名判断
	 * @param params
	 * @return
	 */
	public static Boolean signValidate(HashMap<String, Object> params, String sign) {
		if (params.size() > 0) {
			Collection<String> keyset = params.keySet();
			List<String> list = new ArrayList<>(keyset);
			//对key键值按字典升序排序
			Collections.sort(list);
			String sb = new String();
			for (int i = 0; i < list.size(); i++) {
				sb = sb.concat(list.get(i)).concat("=").concat(String.valueOf(params.get(list.get(i)))).concat("&");
			}
			sb = sb.concat("token=").concat(Const.CHANNEL_TOKEN.get(params.get("channel")));
			String md5Sign = Md5Util.GetMD5Code(sb.toString());
			if (md5Sign.equals(sign)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断时间是否超过2秒
	 * @param time 请求时间
	 * @return
	 */
	public static boolean timeValidate(long time) {
		try {			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(time));
			calendar.add(Calendar.SECOND, Const.REQUEST_TIME_OUT);

			if (System.currentTimeMillis() <= calendar.getTimeInMillis()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}



}
