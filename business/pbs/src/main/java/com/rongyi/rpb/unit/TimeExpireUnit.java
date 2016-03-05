package com.rongyi.rpb.unit;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejun on 2015/11/17.
 * 支付失效时间检查
 */
@Component
public class TimeExpireUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeExpireUnit.class);

    /**
     * Description: 支付宝支付超时时间获取
     * @param timeStart String
     * @param timeExpire String
     * @param orderType int
     * Author: 柯军
     **/

    public String aliPayTimeExpire(String timeStart, String timeExpire, int orderType) {
        //默认支付失效时间分钟
        String itBPay = ConstantEnum.ALI_PAY_TIME_EXPIRE_GOODS.getCodeStr();
        if (Constants.ORDER_TYPE.ORDER_TYPE_1 == orderType) {
            itBPay = ConstantEnum.ALI_PAY_TIME_EXPIRE_COUPONS.getCodeStr();
        }
        try {
            if (StringUtils.isEmpty(timeStart) || StringUtils.isEmpty(timeExpire)) {
                LOGGER.info("支付宝支付失效时间参数不合法,timeStart={},timeExpire={}", timeStart, timeExpire);
            } else if (DateUtil.dateDiff(DateUtil.stringToDate(timeStart), DateUtil.stringToDate(timeExpire)) < 1) {
                LOGGER.info("支付宝支付失效时间小于1分钟，属于无效条件，timeStart={},timeExpire={}", timeStart, timeExpire);
            } else if (DateUtil.dateDiff(DateUtil.stringToDate(timeStart), DateUtil.stringToDate(timeExpire)) > (15 * 24 * 60)) {
                LOGGER.info("支付宝支付失效时间大于15天，属于无效条件，timeStart={},timeExpire={}", timeStart, timeExpire);
            } else {
                itBPay = String.valueOf(DateUtil.dateDiff(DateUtil.stringToDate(timeStart), DateUtil.stringToDate(timeExpire)));
            }
        } catch (Exception e) {
            LOGGER.warn("支付宝支付失效时间参数不合法,忽略，返回默认值{},timeStart={},timeExpire={},exception={}",itBPay,timeStart, timeExpire, e.getMessage());
        }
        return itBPay;
    }

    /**
     *
     * Description: 微信支付超时时间获取
     * @param timeStart String
     * @param timeExpire String
     * @param orderType int
     * Author: 柯军
     **/
    public Map<String,String> weixinPayTimeExpire(String timeStart, String timeExpire, int orderType) {
        Map<String,String> map = new HashMap<>();
        //默认支付失效时间分钟
        int itBPay = ConstantEnum.WEIXIN_PAY_TIME_EXPIRE_GOODS.getCodeInt();
        if (Constants.ORDER_TYPE.ORDER_TYPE_1 == orderType) {
            itBPay = ConstantEnum.WEIXIN_PAY_TIME_EXPIRE_COUPONS.getCodeInt();
        }
        try {
            if (StringUtils.isEmpty(timeStart) || StringUtils.isEmpty(timeExpire)) {
                LOGGER.info("微信支付失效时间参数不合法,默认给予失效时间为{}分钟，timeStart={},timeExpire={}",itBPay, timeStart, timeExpire);
                timeStart = DateUtil.getCurrDateTime().toString();
                timeExpire = DateUtil.dateToString(DateUtil.addTime(DateUtil.stringToDate(timeStart), itBPay, Calendar.MINUTE), "yyyy-MM-dd HH:mm:ss");
            } else if(DateUtil.dateDiff(DateUtil.stringToDate(timeExpire), DateUtil.getCurrDateTime()) > 0){
                LOGGER.info("微信支付失效时间小于当前时间戳，属于无效条件，默认给予失效时间为{}分钟，timeStart={},timeExpire={}",itBPay, timeStart, timeExpire);
                timeStart = DateUtil.dateToString(DateUtil.getCurrDateTime(),"yyyy-MM-dd HH:mm:ss");
                timeExpire = DateUtil.dateToString(DateUtil.addTime(DateUtil.stringToDate(timeStart), itBPay, Calendar.MINUTE), "yyyy-MM-dd HH:mm:ss");
            }else if (DateUtil.dateDiff(DateUtil.stringToDate(timeStart), DateUtil.stringToDate(timeExpire)) < 5) {
                LOGGER.info("微信支付失效时间间隔小于5分钟，默认给予失效时间为5分钟，timeStart={},timeExpire={}", timeStart, timeExpire);
                timeExpire = DateUtil.dateToString(DateUtil.addTime(DateUtil.stringToDate(timeStart), 5, Calendar.MINUTE), "yyyy-MM-dd HH:mm:ss");
            } else if (DateUtil.dateDiff(DateUtil.stringToDate(timeStart), DateUtil.stringToDate(timeExpire)) > (15 * 24 * 60)) {
                LOGGER.info("微信支付失效时间大于15天，属于无效条件，默认给予失效时间为{}分钟，timeStart={},timeExpire={}",itBPay, timeStart, timeExpire);
                timeExpire = DateUtil.dateToString(DateUtil.addTime(DateUtil.stringToDate(timeStart), itBPay, Calendar.MINUTE), "yyyy-MM-dd HH:mm:ss");
            }
            timeStart = DateUtil.dateToString(DateUtil.stringToDate(timeStart), "yyyyMMddHHmmss");
            timeExpire = DateUtil.dateToString(DateUtil.stringToDate(timeExpire), "yyyyMMddHHmmss");
        } catch (Exception e) {
            LOGGER.warn("微信支付失效时间参数不合法,忽略，返回默认值,timeStart={},timeExpire={},exception={}", timeStart, timeExpire, e.getMessage());
            timeExpire = DateUtil.dateToString(DateUtil.addTime(DateUtil.getCurrDateTime(), itBPay, Calendar.MINUTE), "yyyyMMddHHmmss");
        }
        //默认支付失效时间是30分钟
         map.put("timeStart",timeStart);
         map.put("timeExpire",timeExpire);
        return map;

    }
}
