package com.rongyi.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2015/6/2
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/6/2              1.0            创建文件
 *
 */
public class EasyStrUtil {




    /**
     * 汉语转换成拼音
     * @param name 汉语
     * @return
     */
    public static String cnToSpell(String name) {
        String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if ((nameChar[i] >= 0x4e00) && (nameChar[i] <= 0x9fbb)) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }




    /**
     * 如果是null，返回空字符
     *
     * @param str
     * @return
     */
    public static String ifStringNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }


    /**
     * 如果是null，返回0
     *
     * @return
     */
    public static int ifIntegerNull(Integer in) {
        if (in == null) {
            return 0;
        } else {
            return in;
        }
    }



}
