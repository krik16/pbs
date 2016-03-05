package com.shouyingbao.core.common.util;

import java.lang.reflect.Array;

/**
 * 
 * @author pingkang
 * @time  2014年11月28日 上午9:57:44
 */


public class ArrayUtil {
	/**
	  * 动态给数组扩容
	  * @param obj 需要扩容的数组
	  * @param addLength 给数组增加的长度
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public static Object arrayGrow(Object obj, int addLength) {
	    Class clazz = obj.getClass();
	    if(!clazz.isArray()) {
	        return null;
	    }
	    Class componentType = clazz.getComponentType();
	    int length = Array.getLength(obj);
	    int newLength = length + addLength;
	    Object newArray = Array.newInstance(componentType, newLength);
	    System.arraycopy(obj, 0, newArray, 0, length);
	    return newArray;
	}
}
