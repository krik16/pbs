package com.shouyingbao.pbs.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * bson 公共类
 * 
 * @author bo
 *
 */
public class BsonUtil {
	/**
	 * 将objectId列表转化为string列表
	 * 
	 * @param objIdList
	 * @return
	 */
	public static List<String> toStringList(List<ObjectId> objIdList) {
		List<String> resultList = null;
		if (objIdList != null && objIdList.size() > 0) {
			resultList = new ArrayList<String>();
			for (ObjectId id : objIdList) {
				resultList.add(String.valueOf(id));
			}
		}
		return resultList;
	}

	/**
	 * 将string列表转化为objectId列表
	 * 
	 * @param strIdList
	 * @return
	 */
	public static List<ObjectId> toObjectIdList(List<String> strIdList) {
		List<ObjectId> resultList = null;
		if (strIdList != null && strIdList.size() > 0) {
			resultList = new ArrayList<ObjectId>();
			for (String id : strIdList) {
				resultList.add(new ObjectId(id));
			}
		}
		return resultList;
	}
}
