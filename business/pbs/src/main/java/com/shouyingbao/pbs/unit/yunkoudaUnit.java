package com.shouyingbao.pbs.unit;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 资源派接口
 * kejun
 * 2016/4/5 11:30
 **/
public class yunkoudaUnit {

    public static void main(String[] args) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        String wsUrl = "http://zong.bjchyedu.cn/ids/services/account?wsdl";    //wsdl地址
        String method = "webservice_method";//webservice的方法名
        Client client = dcf.createClient(wsUrl);
        Object[] res = null;
        try {
            res = client.invoke(method);//调用webservice
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("res:"+res[0]);
        System.exit(0);
    }
}
