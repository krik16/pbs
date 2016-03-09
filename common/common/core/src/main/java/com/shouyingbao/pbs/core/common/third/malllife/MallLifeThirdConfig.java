package com.shouyingbao.pbs.core.common.third.malllife;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  第三方对接参数
 * time:  2015/9/16
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/9/16              1.0            创建文件
 *
 */

import com.shouyingbao.pbs.core.common.third.md5.Md5Util;
import com.shouyingbao.pbs.core.common.third.rsa.MalllifeRsaUtil;

public class MallLifeThirdConfig {


    /**
     * 第三方公钥私钥 参数 (仅供全民财富使用)
     */
    public abstract class THIRD_RSACODER{
        public static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEinizGfKxLiFNlnjOBiEffFmCuPD+FKQif0HRjTI0507s9f7/BnK1BZY12OJt6JhYBcDhN7Z4f08MZs9oicd3NBvM5UkgfO0tUJWPjGuhffSUfmsqHaI99mctCCf6Zs91AMCKOBf2DI2cE9bOuCmlMWrpAlPmWWzPLvB8r2RMJwIDAQAB";
        public static final String PRIVATE_KEY_STR = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAISKeLMZ8rEuIU2WeM4GIR98WYK48P4UpCJ/QdGNMjTnTuz1/v8GcrUFljXY4m3omFgFwOE3tnh/Twxmz2iJx3c0G8zlSSB87S1QlY+Ma6F99JR+ayodoj32Zy0IJ/pmz3UAwIo4F/YMjZwT1s64KaUxaukCU+ZZbM8u8HyvZEwnAgMBAAECgYBSMRCyeFfg+ita0wItOO3XeaSEhS2lUqWKYwIs8fN9jSeLmO09twDxMvq4f7vCzqeShlbbE1KLnN8nYKYkPLS9V8648PbqI6OTJjVxY4WYvQZSOvf+x6mpmXp6X2AJ1EDM7QhnGrHv1X5rA3uAz44RYoSzdMpY2y2VXtgnjQoKiQJBANJlftG4OPZTBvvk4t4JsdbZq/Th17JSgFnFmszMl1/hxTVf2/tD+HccEiTJMf5LgQxiaxy9uTLeogd59tSzcusCQQChROrPwfEg3I/hEtD+1ywtLiAK78CkanwdBJ5Yi1JFDVadCisifDQcq4srRnP7vnnosApjTfDllYlZy6IcUSS1AkA94sbLz0FKCx/5xdiJcRaTWNGApVF5zVSdk0c2SFOaS2Fjkr+1B8g02NfpZBjFcNYTm3tgJ9oqwVutkU1uQR5jAkEAlug0igDDsKp0AHtpEm8rKYP1/AXp0HDMfyLpQZXtgCxBFmaDgpuS8Bkd+3rL4ZCV50WEEvTGLS/G/T3uDm1n2QJAK2jWxskvXAEvt2EZ19UrJ0EMhDmy0WtuL5JTb37YXrLRPXLpq3fDF3YaYQmU8iXDW+saxCM1tY9HUDDb0uVEkg==";
    }

    /**
     * 全民财富对接参数
     *
     */
    public abstract class QMCAIFU_CHANNEL{
        public static final String CHANNEL="QMCAIFU";//全民财富www.qmcaifu.com
        public static final String TOKEN="631YU17EF8F7D21Z0HI0E36259JI9AN7F371F";
    }


    /**
     * TO_B业务
     * 哈根达斯—老虎机活动
     */
    public abstract class TOB_RSACODER{
        public static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOIfpdq2D+IlGxjSWJNIdis8E1xRNxA343wvZOWFMHotO55d973OD8TJBY8YMdI1SLj7B6RanAAxECA54vqTe/h9S95C1HVU1UlZKqTKWltatAK17zEiwZAC99BGRYY+Bz5mWfETa27RlOMHNkblyVF3KxJbJ2XS7BgYMgqFxSxwIDAQAB";                         
        public static final String PRIVATE_KEY_STR = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI4h+l2rYP4iUbGNJYk0h2KzwTXFE3EDfjfC9k5YUwei07nl33vc4PxMkFjxgx0jVIuPsHpFqcADEQIDni+pN7+H1L3kLUdVTVSVkqpMpaW1q0ArXvMSLBkAL30EZFhj4HPmZZ8RNrbtGU4wc2RuXJUXcrElsnZdLsGBgyCoXFLHAgMBAAECgYBnmuUBupctKJ8cKaMe7Kdzj0xbgZi2XLF4vCkG2uNLQanMbIAHre5iev0NmJ7WcpaCVO9zcaxXRuTy7ug0HcmAO8HikL2HzV1l2nYwoQagoEvr/rBG7Mc2Ov4Wk4tAScmARo7U99pPZfZDUUTjzQHRJJ/D057vZvQOdWt5fiNhQQJBAOrqFaSKbMjO/TrDeqnSQzmDDSULGXfnn+XQFvGc6s7nMPJIJWYmvCfFnzJyzkyBC7d3GFAbZ56eXwKrZUIqCm8CQQCa4++BomwOeEd+U770pc4CbIL2viNl7s1U0xjl58vIqml446VRNcuRs7/ByjMicvblPYH3YOVtwaKZoFP3SUkpAkA91d5akIFwHmQz6q7IMuu1INT98j0C/fpuAOd5NAfu7JZKwEjv6bRPd8qe+e9p3fIamgPRWlVdQGzcBAfEcdKLAkBrOaofSwqVBc0wddc20+WGpgoPuFcXBLcepriQx+O6OcakSquySZRwfEgeZtT5Xy7uSaNEVs7dKXRf967I/PHpAkBsv8SUC63K/gwiRfeJLtwZofXPa3kGzv/zfGdkZsBC4SN9M5VKi9UvJac1638VWoPsYfO9Nik5mhu0mYHhOej6";
    }

    /**
     * TO_B业务
     * 哈根达斯—老虎机活动
     */
    public abstract class TOB_CHANNEL{
        public static final String CHANNEL="HGDSLHJ";//哈根达斯—老虎机活动
        public static final String TOKEN="ec037d3911591c55b84b8e0918890989";
    }


    /**
     * TO_B业务
     * 微信-翻盘购
     */
    public abstract class TOB_WECHAT_CHANNEL{
        public static final String CHANNEL="WECHATFPG";//微信-翻盘购
        public static final String TOKEN="we037d3911591c55b84b8e09188909yu";
    }





    public  static void main(String args[]){

        try {

            long times=System.currentTimeMillis();
            // String jsonStr="{'phone':'15821659415','passWd':'111111','uuid':'testId','regiTime':'"+times+"','couponId':'111'}";
            String jsonStr="{'openId':'15821659415'}";

            String  data=  MalllifeRsaUtil.encryptionStr(jsonStr, MallLifeThirdConfig.TOB_RSACODER.PUBLIC_KEY_STR);

            String str="data="+data+"&timeStamp="+times+"&channel="+MallLifeThirdConfig.TOB_WECHAT_CHANNEL.CHANNEL+"&token="+MallLifeThirdConfig.TOB_WECHAT_CHANNEL.TOKEN;
            String md5Sign= Md5Util.GetMD5Code(str);
            System.out.println("dd=" + data);
            System.out.println("md5Sign="+md5Sign);
            System.out.println("times=" + times);




        }catch (Exception e){
            e.printStackTrace();

        }



    }
}
