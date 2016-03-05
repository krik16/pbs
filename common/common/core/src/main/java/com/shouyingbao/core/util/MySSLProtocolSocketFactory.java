package com.shouyingbao.core.util;

import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;  
import java.net.InetSocketAddress;  
import java.net.Socket;  
import java.net.SocketAddress;  
import java.net.URL;
import java.net.UnknownHostException;  
import java.security.KeyManagementException;  
import java.security.NoSuchAlgorithmException;  
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
  
import javax.net.SocketFactory;  
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;  
import javax.net.ssl.X509TrustManager;  
  
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.ConnectTimeoutException;  
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;  
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;   
public class MySSLProtocolSocketFactory implements ProtocolSocketFactory {  
  
  private SSLContext sslcontext = null;   
   
  private SSLContext createSSLContext() {   
      SSLContext sslcontext=null;   
      try {   
          sslcontext = SSLContext.getInstance("SSL");   
          sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());   
      } catch (NoSuchAlgorithmException e) {   
          e.printStackTrace();   
      } catch (KeyManagementException e) {   
          e.printStackTrace();   
      }   
      return sslcontext;   
  }   
   
  private SSLContext getSSLContext() {   
      if (this.sslcontext == null) {   
          this.sslcontext = createSSLContext();   
      }   
      return this.sslcontext;   
  }   
   
  public Socket createSocket(Socket socket, String host, int port, boolean autoClose)   
          throws IOException, UnknownHostException {   
      return getSSLContext().getSocketFactory().createSocket(   
              socket,   
              host,   
              port,   
              autoClose   
          );   
  }   
  
  public Socket createSocket(String host, int port) throws IOException,   
          UnknownHostException {   
      return getSSLContext().getSocketFactory().createSocket(   
              host,   
              port   
          );   
  }   
   
   
  public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)   
          throws IOException, UnknownHostException {   
      return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);   
  }   
  
  public Socket createSocket(String host, int port, InetAddress localAddress,   
          int localPort, HttpConnectionParams params) throws IOException,   
          UnknownHostException, ConnectTimeoutException {   
      if (params == null) {   
          throw new IllegalArgumentException("Parameters may not be null");   
      }   
      int timeout = params.getConnectionTimeout();   
      SocketFactory socketfactory = getSSLContext().getSocketFactory();   
      if (timeout == 0) {   
          return socketfactory.createSocket(host, port, localAddress, localPort);   
      } else {   
          Socket socket = socketfactory.createSocket();   
          SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);   
          SocketAddress remoteaddr = new InetSocketAddress(host, port);   
          socket.bind(localaddr);   
          socket.connect(remoteaddr, timeout);   
          return socket;   
      }   
  }   
   
  //自定义私有类   
  private static class TrustAnyTrustManager implements X509TrustManager {   
      
      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {   
      }   
  
      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {   
      }   
  
      public X509Certificate[] getAcceptedIssuers() {   
          return new X509Certificate[]{};   
      }   
  }
  
  /**
   * get方式访问https
   * 
   * @param url
   * @return
   */
  public String getMethod( String url){
	  String returnString = null;
	  try {
		  HttpClient http = new HttpClient();  
		  Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
		  Protocol.registerProtocol("https", myhttps);   
		  GetMethod get = new GetMethod(url);
//		  get.setRequestHeader("Connection", "close");
		  int status = http.executeMethod(get);
		  returnString = new String(get.getResponseBodyAsString()
					.getBytes("utf-8"), "utf-8");
		  System.out.println("https returnString="+returnString);
	} catch (Exception e) {
		// TODO: handle exception
	}
	return returnString;
  }
  
  /**
   * post方式访问https
   * 
   * @param url
   * @return
   */
  public static String postMethod( String url){
	  String returnString = null;
	  try {
		  HttpClient http = new HttpClient();  
		 
		  		  
		  Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
		  Protocol.registerProtocol("https", myhttps);
		  PostMethod post = new PostMethod(url);
		  post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		  int status = http.executeMethod(post);
		  returnString = new String(post.getResponseBodyAsString()
				  .getBytes("utf-8"), "utf-8");
		  
		  System.out.println("https postMethod returnString="+returnString);
	  } catch (Exception e) {
		  // TODO: handle exception
	  }
	  return returnString;
  }
  /**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpsURLConnection httpUrlConn  = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			 httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			 inputStream = httpUrlConn.getInputStream();
			 inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			 bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
 			System.out.println("https httpsRequest :"+buffer);
			jsonObject = JSONObject.fromObject(buffer.toString());
 		
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("请求微信创建接口失败"+e.getMessage());

		}finally{
			try {
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				httpUrlConn.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return jsonObject;
	}
    public static String httpsRequestForStr(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
        	ce.printStackTrace();
            System.err.println("连接超时："+ ce.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
        	System.err.println("https 错误："+ e.getMessage());
        }
        return null;
    }
  public static void main(String[] args){
	  /*MySSLProtocolSocketFactory sslFactory = new MySSLProtocolSocketFactory();
//	  String url = "https://api.weibo.com/2/account/get_uid.json?access_token=2.00ixudXC61MNIC395ca73e27FrY6vC";
	  String param = "{'UID':'0003','APPLY_USER_ID':'','APPLY_NUM':'2000','APPLY_DES':'申请，谢谢',APPLY_TIME:''}";
	  try {
		param = URLEncoder.encode(param, "utf-8");
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	  String url = "https://localhost/CreditManageHK/updateCreditMasterAdjustApply.do?json="+param;
	  System.out.println(MySSLProtocolSocketFactory.postMethod(url));
//	  System.out.println(sslFactory.getMethod(url));
*/	  
	  String name = "CreditStandardWithApp.html?appkey=13872446633172964&creditAmo";
	  if( name.contains("?")){
			name = name.substring(0, name.indexOf(".html"));
		}
	  System.out.println(name);
  }

}
class MyX509TrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
