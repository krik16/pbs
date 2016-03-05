package com.shouyingbao.core.common;

import com.jianzhou.sdk.BusinessService;
import com.shouyingbao.core.constant.Constants;
import com.shouyingbao.core.sms.SmsConfig;
import com.shouyingbao.core.sms.SmsEntity;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * 类MessageManger.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年6月10日 下午2:09:18
 */
public class MessageManger {

	protected Logger logger = LoggerFactory.getLogger(MessageManger.class);
    private static MessageManger instance = new MessageManger();
    private static final String  SMS_URL  = "http://service6.baiwutong.com:8080/sms_send2.do";

	private PropertyConfigurer propertyConfigurer;


    public MessageManger() {
    }



    public static MessageManger getInstance() {
        return instance;
    }

    /**
     * 发送短信
     * 百悟短信通道
     * @param phone :手机号
     * @param message :短信内容
     */
    public String sendBaiwutongSmsMessage(String phone, String message) {
        NameValuePair[] params = new NameValuePair[] { new NameValuePair("corp_id", "6e1r001"),
                new NameValuePair("corp_pwd", "6e1r001"), new NameValuePair("corp_service", "106550939yd"),//106550939yd 这个换成 10690269yd
                new NameValuePair("mobile", phone), new NameValuePair("msg_content", message),
                new NameValuePair("corp_msg_id", ""), new NameValuePair("ext", "86") };
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
        GetMethod getMethod = new GetMethod(SMS_URL);
        getMethod.setQueryString(params);
        try {
            httpclient.executeMethod(getMethod);
            return getMethod.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            getMethod.releaseConnection();
        }
    }
    
    /**
     * 发送短信
     * 云短信通道
     * @param phone :手机号
     * @param message :短信内容
     */
    public String sendYunSmsMessage(String phone, String message) {
    	String yunsms = "http://http.yunsms.cn/tx/";
        NameValuePair[] params = new NameValuePair[] { new NameValuePair("uid", "53787"),
                new NameValuePair("pwd", "45bc8c89e1312f2accdf339d7cc52cf8"), new NameValuePair("corp_service", "106550939yd"),
                new NameValuePair("mobile", phone), new NameValuePair("content", message),
                new NameValuePair("encode", "utf8")};
        HttpClient httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
        GetMethod getMethod = new GetMethod(yunsms);
        getMethod.setQueryString(params);
        try {
            httpclient.executeMethod(getMethod);
            return getMethod.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            getMethod.releaseConnection();
        }
    }
    
    /**
     * 发送短信
     * @param phone  手机号
     * @param message 老的信息
     * @param content 新的发送信息
     * @param mallName 验证码
     * @return
     */
    public String sendValidSmsMessage(String phone,String message,String content,String mallName,String title,String time,String balance,String checkNum) {

        SmsEntity xmlentity=new SmsEntity();
        String xml=null;

        try {


        xml=this.SendValidMessage(phone,content,mallName,title,time,balance,checkNum).toString();
       // System.out.println(xml);
        xmlentity.setReturnstatus("returnstatus");
        xmlentity.setMessage("message");
        xmlentity.setRemainpoint("remainpoint");
        xmlentity.setTaskID("taskID");
        xmlentity.setSuccessCounts("successCounts");
        xmlentity=readStringXmlCommen(xmlentity, xml);
        logger.info("状态"+xmlentity.getReturnstatus()+"返回信息"+xmlentity.getMessage()+"成功条数"+xmlentity.getSuccessCounts());
        }catch (Exception e){
            logger.error("发送短信发生异常"+e.getMessage());
            e.printStackTrace();
        }

        if(null!=xmlentity&&xmlentity.getReturnstatus()!=null&&xmlentity.getReturnstatus().equals("Success")){
         return xmlentity.getReturnstatus();
        }else {
            //第一渠道发送信息失败，进入原有通道进行发送
            String messageChannel = "1";//短信通道,1:云短信通道 ，2：百悟短信通道 3:建周短信通道
            String result = null;

            if(propertyConfigurer!=null && propertyConfigurer.getProperty(Constants.Common.MESSAGE_CHANNEL) != null) {
                messageChannel = propertyConfigurer.getProperty(Constants.Common.MESSAGE_CHANNEL).toString();
            }
            //  messageChannel = "3";
                if("1".equals(messageChannel)) {
                    result = sendYunSmsMessage(phone, message);
                }else if("2".equals(messageChannel)) {
                    result = sendBaiwutongSmsMessage(phone, message);
                }else if("3".equals(messageChannel)) {
                    //建周需要自己添加签名
                message+="【容易网】";
                result = sendJianZhouSmsMessage(phone, message);
            }
            return result;

        }



    }
  //发送短信
    public StringBuffer SendValidMessage(String mobile,String content,String mallName,String title,String time,String balance,String checkNum)
    {

        BufferedReader br=null;
        URL url=null;
        HttpURLConnection con;
        String line;
        StringBuffer sub=new StringBuffer();
        String account= SmsConfig.csUserName;
        String password=SmsConfig.csPassWord;
        String userid="";
        String sendTime="";
        try {
//        	content=String.format(content, mallName,time,balance);
            content = content.replaceFirst("@", mallName).replaceFirst("@", time).replaceFirst("@", checkNum).replaceFirst("@", balance);
            //设置发送内容的编码方式
            String send_content= URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");//发送内容

            url=new URL(SmsConfig.csSendURL+"&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");
            con = (HttpURLConnection)url.openConnection();

            br=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            //br=new BufferedReader(new InputStreamReader(url.openStream()));

            while((line=br.readLine())!=null)
            {
                //追加字符串获得XML形式的字符串
                sub.append(line+"");
                //System.out.println("提取数据 :  "+line);
            }
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            return sub;
        }
    }


    /**
     * 发送短信
     * 建周短信通道
     * @param phone :手机号
     * @param message :短信内容
     */
    public static String sendJianZhouSmsMessage(String phone, String message) {
        BusinessService bs = new BusinessService();
        bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
        int temp=bs.sendBatchMessage("sdk_rongyi", "54100171", phone, message);
        return temp+"";
    }
    /**
     * 发送短信
     * @param phone
     * @param message
     * @return
     */
    public String sendSmsMessage(String phone, String message) {
    	logger.info("--------------------------------->");
    	logger.info("-----进入短信通道----->");
    	logger.info("-----phone----->"+ phone);
    	String messageChannel = "3";//短信通道,1:云短信通道 ，2：百悟短信通道，3:建周短信通道
    	String result = null;
    	String classesPath = MessageManger.class.getClassLoader().getResource("").getFile();
		classesPath += "config.properties";
		logger.info("classesPath--->"+ classesPath);
//		logger.info(classesPath);
		//Properties pro = PropertiesUtil.getProperties(classesPath);
		//messageChannel = pro.getProperty(Constants.Common.MESSAGE_CHANNEL);
		logger.info("messageChannel--->"+ messageChannel);
//    	if(propertyConfigurer!=null && propertyConfigurer.getProperty(Constants.Common.MESSAGE_CHANNEL) != null) {    		
//    		messageChannel = propertyConfigurer.getProperty(Constants.Common.MESSAGE_CHANNEL).toString();
//    	}
		
    	if("1".equals(messageChannel)) {
    		result = sendYunSmsMessage(phone, message);
    	}else if("2".equals(messageChannel)) {
    		result = sendBaiwutongSmsMessage(phone, message);
    	}else if("3".equals(messageChannel)) {
    	    //建周需要自己添加签名
    	    message =  message + "【容易网】";
    	    logger.info("message--->"+ message);
            result = sendJianZhouSmsMessage(phone, message);
            logger.info("result--->"+ result);
        }
    	return result;
    }




    //发送短信
    public StringBuffer SendMessage(String mobile,String content,String code)
    {

        BufferedReader br=null;
        URL url=null;
        HttpURLConnection con;
        String line;
        StringBuffer sub=new StringBuffer();
        String account= SmsConfig.csUserName;
        String password= SmsConfig.csPassWord;
        String userid="";
        String sendTime="";
        try {
            content=content.replaceAll("@",code);
            //设置发送内容的编码方式
            String send_content= URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");//发送内容
            url=new URL(SmsConfig.csSendURL+"&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");
            con = (HttpURLConnection)url.openConnection();
            br=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            while((line=br.readLine())!=null)
            {
                //追加字符串获得XML形式的字符串
                sub.append(line+"");
                //System.out.println("提取数据 :  "+line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            return sub;
        }
    }



    //XML字符串解析通用方法
    public SmsEntity readStringXmlCommen(SmsEntity xmlentity,String xml)
    {
        SmsEntity xe=new SmsEntity();

        Document doc=null;

        try {
            //将字符转化为XML
            doc= DocumentHelper.parseText(xml);
            //获取根节点
            Element rootElt=doc.getRootElement();
            //拿到根节点的名称
            //System.out.println("根节点：" + rootElt.getName());

            //获取根节点下的子节点的值
            if(xmlentity.getReturnstatus()!=null)
            {
                xe.setReturnstatus(rootElt.elementText(xmlentity.getReturnstatus()).trim());
            }
            if(xmlentity.getMessage()!=null)
            {
                xe.setMessage(rootElt.elementText(xmlentity.getMessage()).trim());
            }
            if(xmlentity.getRemainpoint()!=null)
            {
                xe.setRemainpoint(rootElt.elementText(xmlentity.getRemainpoint()).trim());
            }
            if(xmlentity.getTaskID()!=null)
            {
                xe.setTaskID(rootElt.elementText(xmlentity.getTaskID()).trim());
            }
            if(xmlentity.getSuccessCounts()!=null)
            {
                xe.setSuccessCounts(rootElt.elementText(xmlentity.getSuccessCounts()).trim());
            }
            if(xmlentity.getPayinfo()!=null)
            {
                xe.setPayinfo(rootElt.elementText(xmlentity.getPayinfo()).trim());
            }
            if(xmlentity.getOverage()!=null)
            {
                xe.setOverage(rootElt.elementText(xmlentity.getOverage()).trim());
            }
            if(xmlentity.getSendTotal()!=null)
            {
                xe.setSendTotal(rootElt.elementText(xmlentity.getSendTotal()).trim());
            }
            //接收状态返回的报告
            if(rootElt.hasMixedContent()==false)
            {
                System.out.println("无返回状态！");
            }
            else
            {
                for (int i = 1; i <= rootElt.elements().size(); i++) {
                    if(xmlentity.getStatusbox()!=null)
                    {
                        logger.info("状态"+i+":");
                        //获取根节点下的子节点statusbox
                        Iterator iter = rootElt.elementIterator(xmlentity.getStatusbox());
                        // 遍历statusbox节点
                        while(iter.hasNext())
                        {
                            Element recordEle = (Element) iter.next();
                            xe.setMobile(recordEle.elementText("mobile").trim());
                            xe.setTaskid(recordEle.elementText("taskid").trim());
                            xe.setStatus(recordEle.elementText("status").trim());
                            xe.setReceivetime(recordEle.elementText("receivetime").trim());
                            logger.info("短信正常返回：对应手机号："+xe.getMobile());
                            logger.info("同一批任务ID：" + xe.getTaskid());
                            logger.info("状态报告----10：发送成功，20：发送失败：" + xe.getStatus());
                            logger.info("接收时间：" + xe.getReceivetime());
                        }
                    }

                }

            }

            //错误返回的报告
            if(xmlentity.getErrorstatus()!=null)
            {
                //获取根节点下的子节点errorstatus
                Iterator itererr = rootElt.elementIterator(xmlentity.getErrorstatus());
                // 遍历errorstatus节点
                while(itererr.hasNext())
                {
                    Element recordElerr = (Element) itererr.next();
                    xe.setError(recordElerr.elementText("error").trim());
                    xe.setRemark(recordElerr.elementText("remark").trim());
                    logger.info("短信错误代码：" + xe.getError());
                    logger.info("错误描述："+xe.getRemark());
                }
            }

        } catch (DocumentException e) {
            logger.info("发送短信失败====对应手机号==："+xe.getMobile());
            e.printStackTrace();
            return null;
        }
        return xe;
    }









    public PropertyConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(PropertyConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}
}
