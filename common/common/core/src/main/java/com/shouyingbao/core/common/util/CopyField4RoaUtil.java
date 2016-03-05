package com.shouyingbao.core.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  适用于roa接口
 * （对相同属性名不同类型的属性拷贝 Date --> String） 
 */
public class CopyField4RoaUtil {
	/**
	 * @param args
	 */

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
					Object methodBParam = methodA.invoke(objectA);
					
					/*Method methodTemp = objectB.getClass().getDeclaredMethod(methodBName);
					Class<?>[] parameterTypes = methodTemp.getParameterTypes();
					Class<?> parameterType = parameterTypes[0];*/
					
					Class<?> sourceFieldType = field.getType();
					if(Date.class.getName().equals(field.getType().getName())){
						methodB = objectB.getClass().getDeclaredMethod(methodBName, String.class.getName().getClass());
						if(methodBParam instanceof Date){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							methodB.invoke(objectB, format.format(methodBParam));
						}
					}else{
						methodB = objectB.getClass().getDeclaredMethod(methodBName, sourceFieldType);
						methodB.invoke(objectB, methodBParam);
					}
	
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
	
	/**
	 * @param args
	 */

	public static void myCopyProperties2(Object objectA, Object objectB) {

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
					Object methodBParam = methodA.invoke(objectA);
					
					/*Method methodTemp = objectB.getClass().getDeclaredMethod(methodBName);
					Class<?>[] parameterTypes = methodTemp.getParameterTypes();
					Class<?> parameterType = parameterTypes[0];*/
					
					Class<?> sourceFieldType = field.getType();
					if(Date.class.getName().equals(field.getType().getName())){
						methodB = objectB.getClass().getDeclaredMethod(methodBName, String.class.getName().getClass());
						if(methodBParam instanceof Date){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							methodB.invoke(objectB, format.format(methodBParam));
						}
					}else{
						methodB = objectB.getClass().getDeclaredMethod(methodBName, sourceFieldType);
						methodB.invoke(objectB, methodBParam);
					}
	
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
