package com.shouyingbao.pbs.core.bean;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Map;

/**
 * 对象转换
 * @author baodk
 */
public class ObjectConvert {

    /**
     * 将sources中和clazz相同属性的字段copy到clazz对象中
     * @param clazz 待转对象类型
     * @param sources 源对象
     * @return 新对象
     */
    public static Object convertFrom(Class<?> clazz, Object ...sources){
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor descriptor : descriptors){
                String propertyName = descriptor.getName();
                Method writeMethod = descriptor.getWriteMethod();
                for(Object source : sources){
                    if (writeMethod != null) {
                        PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), propertyName);
                        if(sourcePd == null){
                            continue;
                        }

                        Method readMethod = sourcePd.getReadMethod();
                        if (readMethod != null) {
                            try {
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                    readMethod.setAccessible(true);
                                }
                                Object value = readMethod.invoke(source);
                                if(StringUtils.isEmpty(value)){
                                    continue;
                                }
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                try{
                                    writeMethod.invoke(obj, value);
                                }catch(Exception e){
                                    writeMethod.invoke(obj, value.toString());
                                }
                            }
                            catch (Throwable ex) {
                                throw new RuntimeException(
                                        "Could not copy property '" + propertyName + "' from source to target", ex);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 根据映射表将一个实体映射到多个实体上
     * @param source
     * @param targets
     * @return
     */
    public static void convertTo(Object source, Class<?> relationClass, Object ...targets){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd : descriptors){
                String propertyName = pd.getName();
                if("class".equals(propertyName)){
                    continue;
                }
//                System.out.println(propertyName);
                Method readMethod = pd.getReadMethod();
                if (readMethod != null) {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(source);
                    if(StringUtils.isEmpty(value)){
                        continue;
                    }
                    for(Object target : targets){
                        String realName = BeanUtil.getPropertyValue(relationClass, propertyName);
                        if(StringUtils.isEmpty(realName)){
                            continue;
                        }
//                        System.out.println(realName);
                        if(!BeanUtil.exsitProperty(target.getClass(), realName)){
                            continue;
                        }
                        BeanUtil.adapt(target, realName, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Map中和clazz相同属性的字段copy到clazz对象中
     * @param clazz 待转对象类型
     * @param sources 源对象
     * @return 新对象
     */
    public static Object convertFromMap(Class<?> clazz, Map<String, Object> sources) {
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                String propertyName = descriptor.getName();
                Method writeMethod = descriptor.getWriteMethod();
                if (writeMethod != null) {
                    try {
                        Object value = sources.get(propertyName);
                        if (StringUtils.isEmpty(value)) {
                            continue;
                        }
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }

                        String type = BeanUtil.getFiledType(obj, propertyName);
                        if ((value instanceof Integer) && type.toLowerCase().indexOf("long") != -1) {
                            long _value = (Integer) value;
                            writeMethod.invoke(obj, _value);
                        } else if ((value instanceof String) && type.toLowerCase().indexOf("date") != -1) {
//							String _value = (String) value;
                            Date date = null;
                            if(value instanceof Date) {
                                date = (Date)value;
                            }else {
                                date = DateUtil.stringToDate(value.toString(), "yyyy-MM-dd");
                                if(date == null) {
                                    date = DateUtil.stringToDate(value.toString(), "yyyy/MM/dd");//2014/12/22 14:39:51
                                }
                            }
                            writeMethod.invoke(obj, date);
                        } else if ((value instanceof String) && type.toLowerCase().indexOf("objectid") != -1) {
                            ObjectId _value = new ObjectId(value.toString());
                            writeMethod.invoke(obj, _value);
                        } else {
                            writeMethod.invoke(obj, value);
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                        throw new RuntimeException("Could not copy property '" + propertyName + "' from source to target", ex);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将source里对应相同的字段拷贝到target上面
     * @param source
     * @param target
     * @return
     */
    public static void convertTo(Object source, Object target){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd : descriptors){
                String propertyName = pd.getName();
                if("class".equals(propertyName)){
                    continue;
                }
//                System.out.println(propertyName);
                Method readMethod = pd.getReadMethod();
                if (readMethod != null) {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(source);
                    if(StringUtils.isEmpty(value)){
                        continue;
                    }
//                    for(Object target : targets){
//                        String realName = BeanUtil.getPropertyValue(relationClass, propertyName);
//                        if(StringUtils.isEmpty(realName)){
//                            continue;
//                        }
//                        System.out.println(realName);
                    if(!BeanUtil.exsitProperty(target.getClass(), propertyName)){
                        continue;
                    }
                    BeanUtil.adapt(target, propertyName, value);
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
