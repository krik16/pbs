package com.shouyingbao.service;

import com.shouyingbao.core.bean.ResponseData;

/**
 *
 * kejun
 * 2016/3/7 16:00
 **/
public interface PaymentBillService {

    /**
     * 微信扫码支付
     * @param userId 用户id
     * @param authCode 二维码
     * @param totalFee 支付金额
     * @param deviceInfo 设备信息
     * @param tradeType 0:MICROPAY扫码支付支付
     * @return ResponseData responseData
     */
    ResponseData scanPay(Integer userId,String authCode,Integer totalFee,String deviceInfo,Integer tradeType);

}
