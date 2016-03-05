package com.rongyi.core.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CopyFieldUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// copyTestA copyA = new copyTestA();
		// copyTestB copyB = new copyTestB();
		//
		// copyA.setJyouhou("testyong");
		// myCopyProperties(copyA, copyB);
		//
		// System.out.println(copyB.getJyouhou());
	}

	public static void myCopyProperties(Object objectA, Object objectB) {

		Field[] fields = objectA.getClass().getDeclaredFields();

		Method methodA = null;
		Method methodB = null;

		String methodAName = null;
		String methodBName = null;

		/**
		 * 判断methodBName在objectB里面是否存在
		 */
		Method[] mb = objectB.getClass().getMethods();//取得全部的方法
		boolean exist = false;
		//>>>
		for (Field field : fields) {
			exist = false;

			String name = field.getName();
			int length = name.length();

			methodAName = new StringBuffer("get").append(name.substring(0, 1).toUpperCase()).append(name.substring(1, length)).toString();

			methodBName = new StringBuffer("set").append(name.substring(0, 1).toUpperCase()).append(name.substring(1, length)).toString();

			try {
				for(Method m : mb) {
					if(methodBName.equals(m.getName())) {
						exist = true;
						break;
					}
				}
				if(exist) {
					methodA = objectA.getClass().getDeclaredMethod(methodAName);
					
					methodB = objectB.getClass().getDeclaredMethod(methodBName, field.getType());
	
					Object methodBParam = methodA.invoke(objectA);
	
					methodB.invoke(objectB, methodBParam);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
