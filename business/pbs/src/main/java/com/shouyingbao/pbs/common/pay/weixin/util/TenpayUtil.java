package com.shouyingbao.pbs.common.pay.weixin.util;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TenpayUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenpayUtil.class);
	
	public static String toString(Object obj) {
		if(obj == null)
			return "";
		
		return obj.toString();
	}

	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return a;
	}

	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return outFormat.format(now);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) (random * num);
	}

	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		
		if(null == request || null == response) {
			return "UTF-8";
		}
		
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		
		if(null == enc || "".equals(enc)) {
			enc = "UTF-8";
		}
		
		return enc;
	}

	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}
		
		return date.getTime()/1000;
	}

	public static String date2String(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

}
