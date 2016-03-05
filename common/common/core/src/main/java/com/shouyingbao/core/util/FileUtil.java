package com.shouyingbao.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 文件公共方法
 * @author xiaobo
 *
 */
public class FileUtil {
	/**
	 * 图片类型
	 * @author xiaobo
	 *
	 */
	public static enum PicTypeEnum {
		ADVERT, SHOP, LOGO, FLOOR, MALL_LOGO, MALL_TRAFFIC_PIC, MALL_INTRODUCTION_PIC, MALL_TERMINAL_LOGO, NAVIGATION_PIC, MALL_ACTIVITIES, TERMINAL_MEMBER, MEMBER_ACTIVITIES
	}

	/**
	 * 获得图片在云服务器上的路径
	 *
	 * @param fileName
	 * @param createDate
	 *            图片创建时间
	 * @return
	 */
	public static String getRelativetyPicPath(Object mongoId, String fileName, PicTypeEnum type, Date createDate) {
		String path = "";
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case ADVERT:
			path = "system/advertisement/picture/" + mongoId + "/" + fileName;
			return path;
		case SHOP:
			cal.setTime(createDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			path = "system/photo/file/" + year + "/" + month + "/" + mongoId + "/" + fileName;
			return path;
		case LOGO:
			path = "system/brand/icon/" + String.valueOf(mongoId) + "/" + fileName;
			return path;
		case FLOOR:
			path = "system/mall_area/picture/" + mongoId + "/" + fileName;
			return path;
		case MALL_LOGO:
			path = "system/mall/icon/" + mongoId + "/" + fileName;
			return path;
		case MALL_TRAFFIC_PIC:
			cal.setTime(createDate);
			int year1 = cal.get(Calendar.YEAR);
			int month1 = cal.get(Calendar.MONTH) + 1;
			path = "system/mall/traffic_pic/" + year1 + "/" + month1 + "/" + mongoId + "/" + fileName;
			return path;
		case MALL_INTRODUCTION_PIC:
			cal.setTime(createDate);
			int year2 = cal.get(Calendar.YEAR);
			int month2 = cal.get(Calendar.MONTH) + 1;
			path = "system/mall/introduction_pic/" + year2 + "/" + month2 + "/" + mongoId + "/" + fileName;
			return path;
		case MALL_TERMINAL_LOGO:
			path = "system/mall/terminal_logo/" + mongoId + "/" + fileName;
			return path;
		case NAVIGATION_PIC:
			// 楼层导航图
			cal.setTime(createDate);
			int year3 = cal.get(Calendar.YEAR);
			int month3 = cal.get(Calendar.MONTH) + 1;
			path = "system/mall_area/navigation_pic/" + year3 + "/" + month3 + "/" + mongoId + "/" + fileName;
			return path;
		case MALL_ACTIVITIES:
			path = "system/mallActivity/" + mongoId + "/" + fileName;
			return path;
		case MEMBER_ACTIVITIES:
			path = "system/members/" + mongoId + "/" + fileName;
			return path;
		case TERMINAL_MEMBER:
			path = "system/terminal_member/img/" + mongoId + "/" + fileName;
			return path;
		default:
			return "";
		}
	}
}
