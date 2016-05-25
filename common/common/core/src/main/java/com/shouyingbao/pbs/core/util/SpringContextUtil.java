/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.shouyingbao.pbs.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Spring context util
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Gets spring bean by bean name
     * 
     * @param name bean name
     * @return spring bean
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
    public static Object getBean(String name, Class<?> requiredType){
        return applicationContext.getBean(name, requiredType);
    }
    
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

    /**
     * 根据Class获取实例
     * @param type
     * @return
     */
    public static <T> T getBeanOfType(Class<T> type) {
        Map<String, T> beans = getBeansOfType(type);
        if (beans.size() == 0) {
            throw new NoSuchBeanDefinitionException(type, "Unsatisfied dependency of type [" + type
                    + "]: expected at least 1 matching bean");
        }
        if (beans.size() > 1) {
            throw new NoSuchBeanDefinitionException(type, "expected single matching bean but found " + beans.size()
                    + ": " + beans.keySet());
        }
        return beans.values().iterator().next();
    }
}
