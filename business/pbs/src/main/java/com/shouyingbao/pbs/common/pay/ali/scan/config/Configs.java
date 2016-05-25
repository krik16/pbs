package com.shouyingbao.pbs.common.pay.ali.scan.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by liuyangkly on 15/6/27.
 */
public class Configs {
    private static Log log = LogFactory.getLog(Configs.class);
    private static Configuration configs;

    private static String openApiDomain="https://openapi.alipay.com/gateway.do";   // 支付宝openapi域名
    private static String mcloudApiDomain="http://mcloudmonitor.com/gateway.do";  // 支付宝mcloudmonitor域名
//        private static String pid="2088021602072451";             // 商户partner id
//        private static String appid="2015090800259783";           // 商户应用id
    private static String pid="2088911212323549";             // test
    private static String appid="2015051100069126";           // test

//    private static String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOG8K0ARSkqLZ+PMEFpwqnSbo96GFTqsORKwYL9mzDblmXzveZUt8s2Aa+9LeUWFOHXzqUD3VueOYyHP5EoXkVCMSS3cvSrDIoA4azsD7c238p3/er2cnTTxz03gfsfE+yijaDHfjVvnlWC96qnRt5quTtRsmU+cMo49n2OTzOifAgMBAAECgYA/UlmPj0oXvyNVc1jjBdfK/h3pvhMWcllQNMnu5xdCDwYPVd31Sv5hRzT/21wZdFVyQPepkbh/HCISyg5v+kx4lQA2ax7psy7iwQrpJoo9c8ICBZ1Fx0a9uZUYk1nL2j0MK0XwOwteWflSdbTeYecHNT/EBxS6qh6eOwvEyd/5AQJBAPOzQ2SaLTMHST1qTNwQUp4dBv7Zvk0FVXRIUe8SueDyRPi4noMTEfP8WzkPhZ3hAHyf30aVUcUWDDmkbO21YpcCQQDtIMkdKLdZ2IOgIfxlFq98xCF6vAVDYzjUYYX2g3WpF0hB8HlHmHWL40OxwpOCaYZqn62ImFqKyJ4tSEc5hVM5AkEA3iAcIlthLWzLtf3pFoCOPW3pzWr8yMk+zaGJhObFpCJO+YGVgZVlPMVdBJKAUJogTneOFJDPmltxQyz62GQG+wJASdKiKfzKECOS3uCVxhbo7UvWLHKqpM8YGy59WUCV+d1wtm6aI0r4lWNcaNPtvnUBzuIFUXg0/+3zKJ/O5L54KQJBAJ7vZv4kVRpSDykysasKUgNUvYe/wBe/+5EIE35vzcp8GPcMbH5IL8Dcv9RESW/YX0NY2s5I3fUUnnkxVqha7pk=";      // RSA私钥，用于对商户请求报文加签
//    private static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhvCtAEUpKi2fjzBBacKp0m6PehhU6rDkSsGC/Zsw25Zl873mVLfLNgGvvS3lFhTh186lA91bnjmMhz+RKF5FQjEkt3L0qwyKAOGs7A+3Nt/Kd/3q9nJ008c9N4H7HxPsoo2gx341b55Vgveqp0beark7UbJlPnDKOPZ9jk8zonwIDAQAB";       // RSA公钥，仅用于验证开发者网关
//    private static String alipayPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB"; // 支付宝RSA公钥，用于验签支付宝应答

    //test
    private static String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMvPGb+aJQX0RPjsx6iZUbcujk9GZhVT1Z7N5hky6rZWkOmO2VLwGaY1zyMwHnPkb3fYcv8lhB/+9LsGsPTdSl1qYOyApI1KLyXZTK/qmHHT9uiX1oz02uwNFuSZb9i7FbYth1vEuuM3qnZE7WmgNQkmcted9JF0f/0jK9IOqqNBAgMBAAECgYAKQmOWbIj+krxCF5E5YHZnlTVesjmDS1QOiWjSzehYw2TKDQHNlf6EimLh75Mo3E/sJX4sb9QF1Ey3eW/A877BfBDgUBDuXJUQXzUL3MJFD0w6+Zsx/xPqQmCl9gYwiR7DMeUKtgUrMmYFQFCELzwlM3stuVPcjLXMaXT8M0EeDQJBAOcDRH02CpfwvodqSzJxy0TFZtaTbKX39TwxJFcSFfV3fNYDnSdYTYswQmUWpOLP0hoxrcMGlPbhGzPjSACULE8CQQDh2o/OkVHE1aXr4u5lQ8OxUIaATGQvstOQL9UA8DmNab4QLrn0Ol8T0p2J4IHeWXa1UFtm34/2amp9VjktkYNvAkARv3uEjyFTOQi6SJ1MW9e9CdlzxNHFEn7ByBi9o8MSH8L0gkSRoEQc3HFNaOb0EflXT9fEsv3A1dyMKPsAKGIbAkEAv9meysOah+9MMCHmi9KSSu6yMg2yFOp82EApWdC1srAeKTTn9NQYq4f/Fn3FE5E/SyllWu+RJKqkpq81hsXStQJADwlOtdyik8N5DvlCSt2rsBsskz4Uiv3KUCwCqq+Lt6g/uFkrTcoBR7GHKOHyyk+l+aJjtxnDONuh2psnu0N1vh==";      // RSA私钥，用于对商户请求报文加签
    private static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLzxm/miUF9ET47MeomVG3Lo5PRmYVU9WezeYZMuq2VpDpjtlS8BmmNc8jMB5z5G932HL/JYQf/vS7BrD03UpdamDsgKSNSi8l2Uyv6phx0/bol9aM9NrsDRbkmW/YuxW2LYdbxLrjN6p2RO1poDUJJnLXnfSRdH/9IyvSDqqjQQIDAQAB";       // RSA公钥，仅用于验证开发者网关
    private static String alipayPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB"; // 支付宝RSA公钥，用于验签支付宝应答


    private static int maxQueryRetry=5;   // 最大查询次数
    private static long queryDuration=1000;  // 查询间隔（毫秒）

    private static int maxCancelRetry;  // 最大撤销次数
    private static long cancelDuration; // 撤销间隔（毫秒）

    private static long heartbeatDelay ; // 交易保障线程第一次调度延迟（秒）
    private static long heartbeatDuration ; // 交易保障线程调度间隔（秒）

    private Configs() {
        // No Constructor
    }

    // 根据文件名读取配置文件，文件后缀名必须为.properties
    public synchronized static void init(String filePath) {
        if (configs != null) {
            return;
        }

        try {
            configs = new PropertiesConfiguration(filePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (configs == null) {
            throw new IllegalStateException("can`t find file by path:" + filePath);
        }

        openApiDomain = configs.getString("open_api_domain");
        mcloudApiDomain = configs.getString("mcloud_api_domain");

        pid = configs.getString("pid");
        appid = configs.getString("appid");

        // RSA
        privateKey = configs.getString("private_key");
        publicKey = configs.getString("public_key");
        alipayPublicKey = configs.getString("alipay_public_key");

        // 查询参数
        maxQueryRetry = configs.getInt("max_query_retry");
        queryDuration = configs.getLong("query_duration");
        maxCancelRetry = configs.getInt("max_cancel_retry");
        cancelDuration = configs.getLong("cancel_duration");

        // 交易保障调度线程
        heartbeatDelay = configs.getLong("heartbeat_delay");
        heartbeatDuration = configs.getLong("heartbeat_duration");

        log.info("配置文件名: " + filePath);
        log.info(description());
    }

    public static String description() {
        StringBuilder sb = new StringBuilder("Configs{");
        sb.append("支付宝openapi网关: ").append(openApiDomain).append("\n");
        if (StringUtils.isNotEmpty(mcloudApiDomain)) {
            sb.append(", 支付宝mcloudapi网关域名: ").append(mcloudApiDomain).append("\n");
        }

        if (StringUtils.isNotEmpty(pid)) {
            sb.append(", pid: ").append(pid).append("\n");
        }
        sb.append(", appid: ").append(appid).append("\n");

        sb.append(", 商户RSA私钥: ").append(getKeyDescription(privateKey)).append("\n");
        sb.append(", 商户RSA公钥: ").append(getKeyDescription(publicKey)).append("\n");
        sb.append(", 支付宝RSA公钥: ").append(getKeyDescription(alipayPublicKey)).append("\n");

        sb.append(", 查询重试次数: ").append(maxQueryRetry).append("\n");
        sb.append(", 查询间隔(毫秒): ").append(queryDuration).append("\n");
        sb.append(", 撤销尝试次数: ").append(maxCancelRetry).append("\n");
        sb.append(", 撤销重试间隔(毫秒): ").append(cancelDuration).append("\n");

        sb.append(", 交易保障调度延迟(秒): ").append(heartbeatDelay).append("\n");
        sb.append(", 交易保障调度间隔(秒): ").append(heartbeatDuration).append("\n");
        sb.append("}");
        return sb.toString();
    }

    public static String getKeyDescription(String key) {
        int showLength = 10;
        if (StringUtils.isNotEmpty(key)) {
            return new StringBuilder(key.substring(0, showLength))
                    .append("******")
                    .append(key.substring(key.length() - showLength))
                    .toString();
        }
        return null;
    }

    public static Configuration getConfigs() {
        return configs;
    }

    public static String getOpenApiDomain() {
        return openApiDomain;
    }

    public static String getMcloudApiDomain() {
        return mcloudApiDomain;
    }

    public static void setMcloudApiDomain(String mcloudApiDomain) {
        Configs.mcloudApiDomain = mcloudApiDomain;
    }

    public static String getPid() {
        return pid;
    }

    public static String getAppid() {
        return appid;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public static int getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public static long getQueryDuration() {
        return queryDuration;
    }

    public static int getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public static long getCancelDuration() {
        return cancelDuration;
    }

    public static void setConfigs(Configuration configs) {
        Configs.configs = configs;
    }

    public static void setOpenApiDomain(String openApiDomain) {
        Configs.openApiDomain = openApiDomain;
    }

    public static void setPid(String pid) {
        Configs.pid = pid;
    }

    public static void setAppid(String appid) {
        Configs.appid = appid;
    }

    public static void setPrivateKey(String privateKey) {
        Configs.privateKey = privateKey;
    }

    public static void setPublicKey(String publicKey) {
        Configs.publicKey = publicKey;
    }

    public static void setAlipayPublicKey(String alipayPublicKey) {
        Configs.alipayPublicKey = alipayPublicKey;
    }

    public static void setMaxQueryRetry(int maxQueryRetry) {
        Configs.maxQueryRetry = maxQueryRetry;
    }

    public static void setQueryDuration(long queryDuration) {
        Configs.queryDuration = queryDuration;
    }

    public static void setMaxCancelRetry(int maxCancelRetry) {
        Configs.maxCancelRetry = maxCancelRetry;
    }

    public static void setCancelDuration(long cancelDuration) {
        Configs.cancelDuration = cancelDuration;
    }

    public static long getHeartbeatDelay() {
        return heartbeatDelay;
    }

    public static void setHeartbeatDelay(long heartbeatDelay) {
        Configs.heartbeatDelay = heartbeatDelay;
    }

    public static long getHeartbeatDuration() {
        return heartbeatDuration;
    }

    public static void setHeartbeatDuration(long heartbeatDuration) {
        Configs.heartbeatDuration = heartbeatDuration;
    }
}

