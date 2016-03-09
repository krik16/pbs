package com.shouyingbao.pbs.core.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

/**
 * 绿地科传 对象转换器
 * @author baodk
 */
public class BeanUtil {
    
    /**
     * 将value 赋值给target 的 property属性
     * @return
     */
    public static void adapt(Object target, String property, Object value){
        PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), property);
        if(targetPd == null){
            throw new RuntimeException("class:" + target.getClass() + " does not contain property:" + property);
        }
        
        Method writeMethod = targetPd.getWriteMethod();
        if(writeMethod == null){
            throw new RuntimeException("class:" + target.getClass() + " property:" + property +" does not contain write method!");
        }
        
        try {
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            Class<?>[] parameterTypes = writeMethod.getParameterTypes();
            if(parameterTypes != null && parameterTypes.length > 0 && value != null && StringUtils.isNotBlank(value.toString())){
                Class<?> parameterType = parameterTypes[0];
                if(Date.class.getName().equals(parameterType.getName())){
                	Date date = null;
                	if(value instanceof Date) {
                		date = (Date)value;
                	}else {
	                    date = DateUtil.stringToDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
	                    if(date == null) {
	                    	date = DateUtil.stringToDate(value.toString(), "yyyy/MM/dd HH:mm:ss");//2014/12/22 14:39:51
	                    }
	                    if(date == null){
	                        date = DateUtil.stringToDate(value.toString(), "yyyy-MM-dd");
	                    }
                	}
                    writeMethod.invoke(target, date);
                }else if(Integer.class.getName().equals(parameterType.getName())){
                    BigDecimal bd = new BigDecimal(value.toString());
                    writeMethod.invoke(target, bd.intValue());
                }else if("true".equals(value) || "false".equals(value)){
                	boolean boo = false;
                    if("true".equals(value)) {
                    	boo = false;
                    }else {
                    	boo = true;
                    }
                    writeMethod.invoke(target, boo);
                }else if(List.class.getName().equals(parameterType.getName())){
                    writeMethod.invoke(target, (List)value);
                }else{
                	if(parameterType.getName().indexOf("ObjectId") != -1) {
                		ObjectId oid = new ObjectId(value.toString());
                		writeMethod.invoke(target, oid);
                	}else {
                		writeMethod.invoke(target, value.toString());
                	}
                }
            }
        }
        catch (Throwable ex) {
        	ex.printStackTrace();
            throw new RuntimeException(
                    "Could not copy property '" + property + "' from source to target", ex);
        }
    }
    
    /**
     * 获取对象clazz的静态属性property的值
     * @param clazz
     * @param field
     * @return
     */
    public static String getPropertyValue(Class<?> clazz, String property){
        String value = "";
        try {
        	Field[] _fields = clazz.getDeclaredFields();
        	boolean hasProperty = false;
        	for(Field _f : _fields) {
        		if(_f.getName().equals(property)) {
        			hasProperty = true;
        			break;
        		}
        	}
        	if(hasProperty) {
	            Field field = clazz.getDeclaredField(property);
	            if(!Modifier.isStatic(field.getModifiers())){
	                throw new RuntimeException(clazz + " property:" + property + " should be static!");
	            }
	            value = field.get(null).toString();
        	}else {
        		return null;
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    /**
     * clazz是否包含某个属性
     * @param clazz
     * @param property
     * @return
     */
    public static boolean exsitProperty(Class<?> clazz, String property){
        try {
            clazz.getDeclaredField(property);
        } catch (NoSuchFieldException | SecurityException e) {
            Class<?> superClass = findSuperClass(clazz);
            if(superClass == null){
                return false;
            }
            
            if(superClass.getName().equals(Object.class.getName())){
                return false;
            }
            
            return exsitProperty(superClass, property);
        }
        return true;
    }
    
    /**
     * 获取父类
     * @param clazz
     * @return
     */
    public static Class<?> findSuperClass(Class<?> clazz){
        return clazz.getSuperclass();
    }
    
    /** 
     * 根据属性名获取属性值 
     * */  
    public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			if("boolean".equals(field.getType().toString().toLowerCase())) {
				getter = "is" + firstLetter + fieldName.substring(1);
			}
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * 根据属性名获取属性类型
     * @param o
     * @param name
     * @return
     */
    public static String getFiledType(Object o, String name){
    	Field fields = null;
		try {
			fields = o.getClass().getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
    	return fields.getType().toString();
    }

}
