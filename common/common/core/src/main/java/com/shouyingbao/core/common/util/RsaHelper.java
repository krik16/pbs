package com.shouyingbao.core.common.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

public class RsaHelper {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String RSA_PUBLICKKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe1N+tR4udbureHSxDVlRCv7SLw7LLFixyLoXEjVFNNkxpnluammzeRlZ23PU16DLPIpCI9BtYHMh+n6Al4plc4nKEfJ0OgoLr4M/VzlCReAWl7+DFFxKZV7MA7yr7gsX/eLQyWAachc17gMmLMFfXBRD/8PZB2if2XnNfcsHL5wIDAQAB";

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes =  Base64Helper.decode(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args){
    	byte[] key = Base64Helper.decode(RSA_PUBLICKKEY);
    	StringBuilder stringBuilder = new StringBuilder("");  
        for (int i = 0; i < key.length; i++) {  
            int v = key[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        System.out.println(stringBuilder.toString());
    }
}
