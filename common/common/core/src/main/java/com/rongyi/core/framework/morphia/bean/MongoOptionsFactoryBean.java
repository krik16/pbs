/**
 * @Title: MongoOptionsFactoryBean.java
 * @Package com.rongyi.mongo.morphia
 * @Description: TODO
 * @author wuye  wuye@rongyi.com
 * @date 2015年6月12日 上午10:55:59
 * @version V1.0
 * Copyright (C),上海容易网电子商务有限公司
 */
package com.rongyi.core.framework.morphia.bean;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.WriteConcern;

/**
 * @Title: MongoOptionsFactoryBean.java
 * @Package com.rongyi.mongo.morphia
 * @Description: TODO
 * @author wuye  wuye@rongyi.com
 * @date 2015年6月12日 上午10:55:59
 * @version V1.0
 * Copyright (C),上海容易网电子商务有限公司
 */
public class MongoOptionsFactoryBean extends AbstractFactoryBean<MongoClientOptions> {

    MongoClientOptions mongoOptions;
    private String writeConcern;
    private String codecRegistry;
    private Integer minConnectionsPerHost;
    private Integer connectionsPerHost;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    private Integer serverSelectionTimeout;
    private Integer maxWaitTime;
    private Integer maxConnectionIdleTime;
    private Integer maxConnectionLifeTime;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private boolean socketKeepAlive;
    private String sslEnabled;
    private String sslInvalidHostNameAllowed;
    private String alwaysUseMBeans;
    private String heartbeatFrequency;
    private String minHeartbeatFrequency;
    private String heartbeatConnectTimeout;
    private String heartbeatSocketTimeout;
    private String localThreshold;
    private String requiredReplicaSetName;
    private String dbDecoderFactory;
    private String dbEncoderFactory;
    private String socketFactory;
    private String cursorFinalizerEnabled;

    @Override
    protected MongoClientOptions createInstance() throws Exception {
        Builder optionsBuilder = new MongoClientOptions.Builder();
        //@formatter:off
        optionsBuilder.writeConcern(WriteConcern.SAFE);
        if(this.minConnectionsPerHost != null) optionsBuilder.minConnectionsPerHost(this.minConnectionsPerHost);
        if(this.connectionsPerHost != null) optionsBuilder.connectionsPerHost(this.connectionsPerHost);
        if(this.threadsAllowedToBlockForConnectionMultiplier != null) optionsBuilder.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier);
        if(this.serverSelectionTimeout != null) optionsBuilder.serverSelectionTimeout(this.serverSelectionTimeout);
        if(this.maxWaitTime != null) optionsBuilder.maxWaitTime(this.maxWaitTime);
        if(this.maxConnectionIdleTime != null) optionsBuilder.maxConnectionIdleTime(this.maxConnectionIdleTime);
        if(this.maxConnectionLifeTime != null) optionsBuilder.maxConnectionLifeTime(this.maxConnectionLifeTime);
        if(this.connectTimeout != null) optionsBuilder.connectTimeout(this.connectTimeout);
        if(this.socketTimeout != null) optionsBuilder.socketTimeout(this.socketTimeout);
        if(this.socketKeepAlive == true) optionsBuilder.socketKeepAlive(this.socketKeepAlive);

/*
        this.mongoOptions = new MongoClientOptions.Builder().socketKeepAlive(true) // 是否保持长链接
                .connectTimeout(5000) // 链接超时时间
                .socketTimeout(5000) // read数据超时时间
                .readPreference(ReadPreference.primary()) // 最近优先策略
//                .autoConnectRetry(false) // 是否重试机制
                .connectionsPerHost(30) // 每个地址最大请求数
                .sslEnabled(true)
                .maxWaitTime(1000 * 60 * 2) // 长链接的最大等待时间
                .threadsAllowedToBlockForConnectionMultiplier(50) // 一个socket最大的等待请求数
                .writeConcern(WriteConcern.NORMAL).build();
 */
        //@formatter:on
        this.mongoOptions = optionsBuilder.build();
        return this.mongoOptions;
    }



    @Override
    public Class<?> getObjectType() {
        // TODO Auto-generated method stub
        return null;
    }

    public MongoClientOptions getMongoOptions() {
        return this.mongoOptions;
    }

    public void setMongoOptions(MongoClientOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public String getWriteConcern() {
        return this.writeConcern;
    }

    public void setWriteConcern(String writeConcern) {
        this.writeConcern = writeConcern;
    }

    public String getCodecRegistry() {
        return this.codecRegistry;
    }

    public void setCodecRegistry(String codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public Integer getMinConnectionsPerHost() {
        return this.minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public Integer getConnectionsPerHost() {
        return this.connectionsPerHost;
    }

    public void setConnectionsPerHost(Integer connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
        return this.threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public Integer getServerSelectionTimeout() {
        return this.serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public Integer getMaxWaitTime() {
        return this.maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getMaxConnectionIdleTime() {
        return this.maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public Integer getMaxConnectionLifeTime() {
        return this.maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSocketKeepAlive() {
        return this.socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public String getSslEnabled() {
        return this.sslEnabled;
    }

    public void setSslEnabled(String sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getSslInvalidHostNameAllowed() {
        return this.sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(String sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public String getAlwaysUseMBeans() {
        return this.alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(String alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public String getHeartbeatFrequency() {
        return this.heartbeatFrequency;
    }

    public void setHeartbeatFrequency(String heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public String getMinHeartbeatFrequency() {
        return this.minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(String minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public String getHeartbeatConnectTimeout() {
        return this.heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(String heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public String getHeartbeatSocketTimeout() {
        return this.heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(String heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public String getLocalThreshold() {
        return this.localThreshold;
    }

    public void setLocalThreshold(String localThreshold) {
        this.localThreshold = localThreshold;
    }

    public String getRequiredReplicaSetName() {
        return this.requiredReplicaSetName;
    }

    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }

    public String getDbDecoderFactory() {
        return this.dbDecoderFactory;
    }

    public void setDbDecoderFactory(String dbDecoderFactory) {
        this.dbDecoderFactory = dbDecoderFactory;
    }

    public String getDbEncoderFactory() {
        return this.dbEncoderFactory;
    }

    public void setDbEncoderFactory(String dbEncoderFactory) {
        this.dbEncoderFactory = dbEncoderFactory;
    }

    public String getSocketFactory() {
        return this.socketFactory;
    }

    public void setSocketFactory(String socketFactory) {
        this.socketFactory = socketFactory;
    }

    public String getCursorFinalizerEnabled() {
        return this.cursorFinalizerEnabled;
    }

    public void setCursorFinalizerEnabled(String cursorFinalizerEnabled) {
        this.cursorFinalizerEnabled = cursorFinalizerEnabled;
    }

}
