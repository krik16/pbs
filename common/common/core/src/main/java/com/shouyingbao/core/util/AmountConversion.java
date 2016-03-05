package com.shouyingbao.core.util;

import java.math.BigDecimal;

/**
 * 金额换算工具
 * 支持元转分，分转元
 * Created by Breggor on 2015/10/28.
 */
public abstract class AmountConversion {


    /**
     * 分转元
     *
     * @param fen
     * @return 元单位金额
     */
    public static double fenToYuan(int fen) {
        double val = 0D;
        if (fen > 0) {
            val = BigDecimal.valueOf(fen).divide(BigDecimal.valueOf(100.00D)).setScale(2).doubleValue();
        }
        return val;
    }

    /**
     * 元转分
     *
     * @param yuan
     * @return 分单位金额
     */
    public static int yuanToFen(double yuan) {
        int val = 0;
        if (yuan > 0D) {
            val = BigDecimal.valueOf(yuan).multiply(BigDecimal.valueOf(100.00D)).setScale(2).intValue();
        }
        return val;
    }

//    public static void main(String[] args) {
//        System.out.println(AmountConversion.fenToYuan(1990));
//        System.out.println(AmountConversion.yuanToFen(39.109));
//    }
}
