package com.shouyingbao.core.util;


import com.shouyingbao.core.constant.CouponConst;
import org.springframework.util.StringUtils;

/**
 * Created by xgq on 2015/8/26.
 */
@SuppressWarnings("deprecation")
public class TradeUtil {

    public static boolean isTradeUserCode(String code) {
        return (!StringUtils.isEmpty(code) && code.startsWith(CouponConst.COUPON_TYPE.COUPON));

    }
}
