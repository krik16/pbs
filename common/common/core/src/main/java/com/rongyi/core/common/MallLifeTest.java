package com.rongyi.core.common;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2016/1/21
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2016/1/21              1.0            创建文件
 *
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MallLifeTest {

    public static void main(String[] args) throws Exception {

        MallLifeTest mallLifeTest = new MallLifeTest();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        long startT=mallLifeTest.fromDateStringToLong("2016-01-21 14:51:23"); //定义上机时间
        long endT=mallLifeTest.fromDateStringToLong("2016-01-25 16:53:25");  //定义下机时间

        long ss=(endT-startT)/(1000); //共计秒数
        int MM = (int)ss/60;   //共计分钟数
        int hh=(int)ss/3600;  //共计小时数
        int dd=(int)hh/24;   //共计天数

       // System.out.println("共"+dd+"天 准确时间是："+hh+" 小时 "+MM+" 分钟"+ss+" 秒 共计："+ss*1000+" 毫秒");


       // System.out.println("输出具体时间======"+mallLifeTest.formatTime(endT-startT));

      //  System.out.println("输出具体时间2222======"+mallLifeTest.formatTime2(endT-startT));

        System.out.println(mallLifeTest.toHexString("126887817c44ae552e351a862289fca380"));
        System.out.println(mallLifeTest.toHexString("126887817c44ae552e351a862289fca380").length());
        System.out.println(mallLifeTest.toStringHex1(mallLifeTest.toHexString("126887817c44ae552e351a862289fca380")));


    }


    public long fromDateStringToLong(String inVal) { //此方法计算时间毫秒
        Date date = null;   //定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            date = inputFormat.parse(inVal); //将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();   //返回毫秒数
    }

    /*
 * 毫秒转化时分秒毫秒
 */
    public   String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }


    /*
* 毫秒转化
*/
    public   String formatTime2(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strDay+"天"+strHour+"小时"+strMinute + " 分钟 " + strSecond + " 秒";
    }




    //转化字符串为十六进制编码
    public   String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    // 转化十六进制编码为字符串
    public   String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}
