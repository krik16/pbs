package com.shouyingbao.pbs.common.pay.weixin.model;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */

import com.shouyingbao.pbs.common.pay.weixin.util.Configure;
import com.shouyingbao.pbs.common.pay.weixin.util.RandomStringGenerator;
import com.shouyingbao.pbs.common.pay.weixin.util.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API需要提交的数据
 */
public class ScanFixedPayReqData {

    //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String product_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String time_stamp = "";

    /**
     * @param product_id       商户定义的商品id 或者订单号
     * @param time_stamp        系统当前时间，定义规则详见时间戳,1414488825
     */
    public ScanFixedPayReqData(String product_id, String time_stamp,Configure configure) {

        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(configure.getAppID());

        setProduct_id(product_id);

        setTime_stamp(time_stamp);

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(configure.getMchID());


        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(), configure.getKey());
        setSign(sign);//把签名数据设置到Sign这个属性中

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
