package com.shouyingbao.pbs.unit;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.shouyingbao.pbs.unit.zxing.MatrixToImageWriter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * kejun
 * 2016/3/21 10:25
 **/
public class ZxingUtil {

    public static  final void getZxing(String content){
        try {

            String path = "C:\\rongyi\\work\\pbs";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            File file1 = new File(path,"test1.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        getZxing("test");
    }
}
