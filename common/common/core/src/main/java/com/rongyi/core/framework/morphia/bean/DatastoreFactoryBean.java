package com.rongyi.core.framework.morphia.bean;


import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.MongoClient;

/**
 *
 * @Title: DatastoreFactoryBean.java
 * @Package com.rongyi.mongo.morphia
 * @Description: TODO
 * @author wuye  wuye@rongyi.com
 * @date 2015年6月12日 上午9:50:01
 * @version V1.0
 * Copyright (C),上海容易网电子商务有限公司
 */
public class DatastoreFactoryBean extends AbstractFactoryBean<Datastore>{

    /** morphia 实例 */
    private Morphia morphia;

    /** mongo 实例 */
    private MongoClient mongo;

    /** 数据库名 */
    private String dbName;

    /** 是否确认索引存在，默认false */
    private boolean toEnsureIndexes = false;

    /** 是否确认caps存在，默认false */
    private boolean toEnsureCaps = false;

    @Override
    protected Datastore createInstance() throws Exception {
        Datastore ds = null;
        ds = this.morphia.createDatastore(this.mongo, this.dbName);
        this.logger.info("Initialzed Morphia Datastore on Database: " + ds.getDB());
        if(this.toEnsureIndexes){
            ds.ensureIndexes();
        }
        if(this.toEnsureCaps){
            ds.ensureCaps();
        }
        return ds;
    }

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        if(this.mongo == null){
            throw new IllegalStateException("mongo is not set");
        }

        if(this.morphia == null){
            throw new IllegalStateException("morphia is not set");
        }
    }

    public Morphia getMorphia() {
        return this.morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public MongoClient getMongo() {
        return this.mongo;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isToEnsureIndexes() {
        return this.toEnsureIndexes;
    }

    public void setToEnsureIndexes(boolean toEnsureIndexes) {
        this.toEnsureIndexes = toEnsureIndexes;
    }

    public boolean isToEnsureCaps() {
        return this.toEnsureCaps;
    }

    public void setToEnsureCaps(boolean toEnsureCaps) {
        this.toEnsureCaps = toEnsureCaps;
    }
}
