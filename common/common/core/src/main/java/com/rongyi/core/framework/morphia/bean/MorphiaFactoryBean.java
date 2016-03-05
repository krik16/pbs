package com.rongyi.core.framework.morphia.bean;


import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 *
 * @Title: MorphiaFactoryBean.java
 * @Package com.rongyi.mongo.morphia
 * @Description: TODO
 * @author wuye  wuye@rongyi.com
 * @date 2015年6月12日 上午10:24:18
 * @version V1.0
 * Copyright (C),上海容易网电子商务有限公司
 */
public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia>{

    /** 要扫描并映射的包 */
    private String[] mapPackages;

    /** 要映射的类 */
    private String[] mapClasses;

    private boolean ignoreInvalidClasses;

    @Override
    protected Morphia createInstance() throws Exception {
        Morphia morphia = new Morphia();
        if(this.mapPackages != null){
            for(String packageName : this.mapPackages){
                morphia.mapPackage(packageName, this.ignoreInvalidClasses);
            }
        }
        if(this.mapClasses != null){
            for(String entityClass : this.mapClasses){
                morphia.map(Class.forName(entityClass));
            }
        }
        return morphia;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }

    public void setIgnoreInvalidClasses(boolean ignoreInvalidClasses) {
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }

}
