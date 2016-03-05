package com.shouyingbao.common.pay.weixin.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.shouyingbao.common.pay.weixin.util.Configure;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:44
 * 服务的基类
 */
public class BaseService{

    //API的地址
    private String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

    public BaseService(String api) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        apiURL = api;
        Class c = Class.forName(Configure.HttpsRequestClassName);
        serviceRequest = (IServiceRequest) c.newInstance();
    }

    protected String sendPost(Object xmlObj,Configure configure) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL,xmlObj,configure);
    }
//
//    protected String sendPost(Object xmlObj,Integer weixinMchId) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        Configure configure = new Configure(weixinMchId);
//        return serviceRequest.sendPost(apiURL,xmlObj,configure);
//    }
//
//    protected String sendPost(Object xmlObj,String publicCode,String mailId) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        Configure configure = new Configure(publicCode,mailId);
//        return serviceRequest.sendPost(apiURL,xmlObj,configure);
//    }

}
