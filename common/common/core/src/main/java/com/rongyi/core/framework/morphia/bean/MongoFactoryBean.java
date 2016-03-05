package com.rongyi.core.framework.morphia.bean;

import com.mongodb.*;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuye  wuye@rongyi.com
 * @version V1.0
 *          Copyright (C),上海容易网电子商务有限公司
 * @Title: MongoFactoryBean.java
 * @Package com.rongyi.mongo.morphia
 * @Description: TODO
 * @date 2015年6月12日 上午10:23:54
 */
public class MongoFactoryBean extends AbstractFactoryBean<MongoClient> {
    /**
     * 表示服务器列表（主从复制或者分片）的字符串数组
     */
    private String serverStrings;

    private String userName;

    private String database;

    private String password;

    /**
     * mongoDB配置
     */
    private MongoClientOptions mongoOptions;

    /**
     * 是否主从分离(读取从库)，默认读写都在主库
     */
    private boolean readSecondary = false;

    /**
     * 设定写策略(出错时是否抛异常)，默认采用SAFE模式（需要抛异常）
     */
    private WriteConcern writeConcern = WriteConcern.SAFE;

    @Override
    protected MongoClient createInstance() throws Exception {
        MongoClient mongo = this.initMongo();
        /** 设定主从分离  */
        if (this.readSecondary) {
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }

        /** 设定读写分离  */
        mongo.setWriteConcern(this.writeConcern);
        return mongo;
    }

    @Override
    public Class<?> getObjectType() {
        return Mongo.class;
    }

    /**
     * 初始化mongo实例
     *
     * @return
     * @throws Exception
     */
    private MongoClient initMongo() throws Exception {
        MongoClient mongo = null;
        List<ServerAddress> serverList = this.getServerList();

//        this.logger.info("[Mongo Client] username: " + this.userName + ", password: " + this.password + ", userdb: " + this.database);
        MongoCredential credential = MongoCredential.createCredential(this.userName, this.database, this.password.toCharArray());

        for (ServerAddress serverAddress : serverList) {
            this.logger.info("[Mongo Client] server: " + serverAddress.getHost() + ":" + serverAddress.getPort());
        }

        if (this.mongoOptions != null) {
            mongo = new MongoClient(serverList, Arrays.asList(credential), this.mongoOptions);
            this.logger.info("[Mongo Client] " + this.showOptions(this.mongoOptions));

        } else {
            this.logger.info("[Mongo Client] mongoOptions is empty!");
            mongo = new MongoClient(serverList, Arrays.asList(credential));
        }
        return mongo;
    }

    /**
     * 根据服务器字符串列表，解析出服务器对象列表
     *
     * @return
     * @throws Exception
     */
    private List<ServerAddress> getServerList() throws Exception {
        List<ServerAddress> serverList = new ArrayList<ServerAddress>();
        try {
            for (String serverString : this.serverStrings.split(",")) {
                String[] temp = serverString.trim().split(":");
                String host = temp[0];
                if (temp.length > 2) {
                    throw new IllegalArgumentException("Invalid server address string:" + serverString);
                }
                if (temp.length == 2) {
                    serverList.add(new ServerAddress(host, Integer.parseInt(temp[1])));
                } else {
                    serverList.add(new ServerAddress(host));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while converting serverString to ServerAddressList");
        }
        return serverList;
    }

    public void setServerStrings(String serverStrings) {
        this.serverStrings = serverStrings;
    }

    public void setMongoOptions(MongoClientOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public void setReadSecondary(boolean readSecondary) {
        this.readSecondary = readSecondary;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@formatter:off
    private String showOptions(MongoClientOptions mongoOptions) {
        return "WriteConcern: " + mongoOptions.getWriteConcern()                // WriteConcern.SAFE
//                + ", CodecRegistry: "               + mongoOptions.getCodecRegistry()
                + ", MinConnectionsPerHost: " + mongoOptions.getMinConnectionsPerHost()       // 10
                + ", ConnectionsPerHost: " + mongoOptions.getConnectionsPerHost()          // Default is 100
                + ", ThreadsAllowedToBlockForConnectionMultiplier: " + mongoOptions.getThreadsAllowedToBlockForConnectionMultiplier()   // 20
                + ", ServerSelectionTimeout: " + mongoOptions.getServerSelectionTimeout()      // Default is 30,000
                + ", MaxWaitTime: " + mongoOptions.getMaxWaitTime()                 // Default is 120,000
                + ", MaxConnectionIdleTime: " + mongoOptions.getMaxConnectionIdleTime()       // Default is 0
                + ", MaxConnectionLifeTime: " + mongoOptions.getMaxConnectionLifeTime()       // Default is 0
                + ", ConnectTimeout: " + mongoOptions.getConnectTimeout()              // Default is 10,000
                + ", SocketTimeout: " + mongoOptions.getSocketTimeout()               // Default is 0
                + ", SocketKeepAlive: " + mongoOptions.isSocketKeepAlive()              // Default is false
                + ", SslEnabled: " + mongoOptions.isSslEnabled()                   // Default is false
//                + ", SslInvalidHostNameAllowed: "   + mongoOptions.isSslInvalidHostNameAllowed()
//                + ", AlwaysUseMBeans: "             + mongoOptions.isAlwaysUseMBeans()
//                + ", HeartbeatFrequency: "          + mongoOptions.getHeartbeatFrequency()
//                + ", MinHeartbeatFrequency: "       + mongoOptions.getMinHeartbeatFrequency()
//                + ", HeartbeatConnectTimeout: "     + mongoOptions.getHeartbeatConnectTimeout()
//                + ", HeartbeatSocketTimeout: "      + mongoOptions.getHeartbeatSocketTimeout()
//                + ", LocalThreshold: "              + mongoOptions.getLocalThreshold()
                + ", RequiredReplicaSetName: " + mongoOptions.getRequiredReplicaSetName()
//                + ", DbDecoderFactory: "            + mongoOptions.getDbDecoderFactory()
//                + ", DbEncoderFactory: "            + mongoOptions.getDbEncoderFactory()
//                + ", SocketFactory: "               + mongoOptions.getSocketFactory()
//                + ", CursorFinalizerEnabled: "      + mongoOptions.isCursorFinalizerEnabled()
                ;
    }
}
