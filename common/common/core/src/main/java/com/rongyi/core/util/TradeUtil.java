package com.rongyi.core.util;


import org.springframework.util.StringUtils;

import com.rongyi.core.constant.CouponConst;

/**
 * Created by xgq on 2015/8/26.
 */
@SuppressWarnings("deprecation")
public class TradeUtil {

    public static boolean isTradeUserCode(String code) {
        return (!StringUtils.isEmpty(code) && code.startsWith(CouponConst.COUPON_TYPE.COUPON));

    }
}
