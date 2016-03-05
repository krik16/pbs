package com.shouyingbao.core.common.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

/**
 * 类Argument.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月22日 下午4:46:03
 */
public class Argument {

    /**
     * 正整数
     * 
     * @param argument
     * @return
     */
    public static boolean isPositive(Integer argument) {
        return argument != null && argument > 0;
    }

    /**
     * 非正整数
     * 
     * @param argument
     * @return
     */
    public static boolean isNotPositive(Integer argument) {
        return argument == null || argument <= 0;
    }
    public static boolean isNotPositive(Double argument) {
        return argument == null || argument <= 0;
    }

    public static boolean isNull(Object argument) {
        return argument == null;
    }

    public static boolean isBlank(String argument) {
        return StringUtils.isBlank(argument);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection argument) {
        return isNull(argument) || argument.isEmpty();
    }

    public static boolean isNotNull(Object argument) {
        return argument != null;
    }

    /**
     * 判断一个集合部位空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection argument) {
        return !isEmpty(argument);
    }

    /**
     * 2个Integer是否相等 <br>
     * 
     * @param num1
     * @param num2
     * @return
     */
    public static boolean integerEqual(Integer num1, Integer num2) {
        return num1 == null ? num2 == null : num1.equals(num2);
    }


	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if(StringUtils.isBlank(str)){
			return false;
		}
		str=str.replaceAll("\\s{1}","");
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
}
