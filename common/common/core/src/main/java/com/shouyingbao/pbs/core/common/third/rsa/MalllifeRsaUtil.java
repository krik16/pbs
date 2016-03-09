package com.shouyingbao.pbs.core.common.third.rsa;
/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2015/9/17
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/9/17              1.0            创建文件
 *
 */

import com.shouyingbao.pbs.core.common.third.exception.ThirdException;
import com.shouyingbao.pbs.core.common.third.malllife.MallLifeThirdConfig;
import com.shouyingbao.pbs.core.common.RSACoder;
import com.shouyingbao.pbs.core.common.util.Base64Helper;
import com.shouyingbao.pbs.core.common.util.RsaHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import sun.misc.BASE64Encoder;
import java.security.NoSuchAlgorithmException;
/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  对第三方提供加密解决公具类
 * time:  2015/9/17
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/9/17              1.0            创建文件
 *
 */
public class MalllifeRsaUtil {

    protected static  Logger logger = LoggerFactory.getLogger(MalllifeRsaUtil.class);

    /**
     * 解密
     * @param str 加密串
     * @param privateKey 解密 私钥匙
     * @return
     */
    public static String decryptStr(String str, String privateKey)throws ThirdException {
        logger.info("====解密===");
        try {
            byte[] decodedData = RSACoder.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(str.trim()), privateKey);
            str = new String(decodedData);
        }catch (Exception e){
            logger.info("====解密失败===");
            throw new ThirdException("解码失败");
        }
        return str;
    }


    /**
     * 加密
     * @param str 加密前数据
     * @param publicKey 加密公钥
     * @return
     * @throws ThirdException
     */
    public static String encryptionStr(String str, String publicKey)throws ThirdException{
        logger.info("====加密===");
        try {
            byte[] encodeUserName = RsaHelper.encryptByPublicKey(str.getBytes(), publicKey);

            str= Base64Helper.encode(encodeUserName);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("====加密失败===");
            throw new ThirdException("加密失败");
        }
        return str;
    }



    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        byte[] publicKey = key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        byte[] privateKey =key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
    /**
     * 生成 公私钥匙
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main(String  ...args) {

        try {
            Map<String, Object> keyMap;
            try {

                //生成 公钥 私钥
                /*
                 keyMap = initKey();
                String publicKey =  getPublicKey(keyMap);
                String privateKey =  getPrivateKey(keyMap);
                System.out.println("publicKey="+publicKey);
                System.out.println("privateKey="+privateKey);
                */

                //全民财富
                /*
                String jsonStr="{'phone':'15821659415','passWd':'111111','uuid':'1111','regiTime':'1442838385146'}";
                System.out.println("加密前="+jsonStr);
                String encryStr=  MalllifeRsaUtil.encryptionStr(jsonStr,MallLifeThirdConfig.THIRD_RSACODER.PUBLIC_KEY_STR);
                System.out.println("加密后="+encryStr);
                String decryStr=MalllifeRsaUtil.decryptStr(encryStr, MallLifeThirdConfig.THIRD_RSACODER.PRIVATE_KEY_STR);
                System.out.println("解密后="+decryStr);
                */

                //ToB 业务
                String jsonStr="{'phone':'15821659415','passWd':'111111','uuid':'1111','regiTime':'1442838385146','couponId',:'couponId'}";
                System.out.println("加密前="+jsonStr);
                String encryStr=  MalllifeRsaUtil.encryptionStr(jsonStr,MallLifeThirdConfig.TOB_RSACODER.PUBLIC_KEY_STR);
                System.out.println("加密后="+encryStr);
                String decryStr=MalllifeRsaUtil.decryptStr(encryStr, MallLifeThirdConfig.TOB_RSACODER.PRIVATE_KEY_STR);
                System.out.println("解密后="+decryStr);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
    }



}
