package com.shouyingbao.core.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class ReflectionUtils {


  /**
   * 通过反射获取T类型
   * 
   * @param 传入类型
   * @return 返回泛型中第一个类型
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> Class<T> getSuperClassGenericType(final Class clazz) {
    return getSuperClassGenricType(clazz, 0);
  }

  /**
   * 
   * @param clazz
   * @param index 默认为0
   * @return 返回泛型中的类型
   */
  @SuppressWarnings({"rawtypes"})
  public static Class getSuperClassGenricType(final Class clazz, final int index) {

    Type genType = clazz.getGenericSuperclass();// 获取父类

    if (!(genType instanceof ParameterizedType)) {
      return Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();// 获取父类的泛型

    if (index >= params.length || index < 0) {
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      return Object.class;
    }

    return (Class) params[index];
  }
  
  public static Object convertMap(Class type, Map map)
          throws IntrospectionException, IllegalAccessException,
          InstantiationException, InvocationTargetException {
      BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
      Object obj = type.newInstance(); // 创建 JavaBean 对象

      // 给 JavaBean 对象的属性赋值
      PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
      for (int i = 0; i< propertyDescriptors.length; i++) {
          PropertyDescriptor descriptor = propertyDescriptors[i];
          String propertyName = _upToLow(descriptor.getName());
          
          if (map.containsKey(propertyName)) {
              // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
              Object value = map.get(propertyName);

              Object[] args = new Object[1];
              args[0] = value;

              descriptor.getWriteMethod().invoke(obj, args);
          }
      }
      return obj;
  }

  private static String _upToLow(String str){
		String newStr = new String("");
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c>='A'  &&  c<='Z') {
				newStr = newStr + "_" + String.valueOf(c).toLowerCase();
			}else{
				newStr += String.valueOf(c);
			}
		}
		return newStr;
  	}
}
