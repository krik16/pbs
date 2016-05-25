package com.shouyingbao.pbs.unit;


import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

/**
 * 资源派接口
 * kejun
 * 2016/4/5 11:30
 **/
public class yunkoudaUnit {
    public String invokeRemoteFuc() {
        String endpoint = "http://svc.ziyuanpai.com/PayService.asmx?wsdl";
        String result = "no result!";
        Service service = new Service();
        Call call;
        Object[] object = new Object[1];
        object[0] = "Dear I miss you";//Object是用来存储方法的参数
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);// 远程调用路径
            call.setOperationName("pay");// 调用的方法名

            // 设置参数名:
            call.addParameter("str1", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'

            // 设置返回值类型：
            call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String

            result = (String) call.invoke(object);// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        yunkoudaUnit t = new yunkoudaUnit();
        String result = t.invokeRemoteFuc();
        System.out.println(result);
    }

/* public static void main(String[] args) {

        try {
//            String wsUrl = "http://zong.bjchyedu.cn/ids/services/account?wsdl";    //wsdl地址
//            String method = "webservice_methodwebservice_method";//webservice的方法名
            String endpoint = "http://zong.bjchyedu.cn/ids/services/account?wsdl";
            //直接引用远程的wsdl文件
            //以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("login");//WSDL里面描述的接口名称
            call.addParameter("getUsersByNameAndOrgid", org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
            String temp = "测试人员";
            String result = (String)call.invoke(new Object[]{temp});
            //给方法传递参数，并且调用方法
            System.out.println("result is "+result);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }*/

}
