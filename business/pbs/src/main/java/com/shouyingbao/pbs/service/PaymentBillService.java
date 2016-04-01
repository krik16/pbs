package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.PaymentBill;
import com.shouyingbao.pbs.vo.PaymentBillVO;

import java.util.List;
import java.util.Map;

/**
 *
 * kejun
 * 2016/3/7 16:00
 **/
public interface PaymentBillService {


    List<PaymentBillVO> selectListByPage(Map<String,Object> map,Integer currentPage,Integer pageSize);

    Integer selectListCount(Map<String,Object> map);

    /**
     * 微信扫码支付
     * @param userId 用户id
     * @param authCode 二维码
     * @param totalFee 支付金额
     * @param deviceInfo 设备信息
     * @param tradeType 0:MICROPAY扫码支付支付
     * @return ResponseData responseData
     */
    ResponseData weixinScanPay(Integer userId, String authCode, Integer totalFee, String deviceInfo, Integer tradeType);

    /**
     * 微信扫码退款
     * @param orderNo 订单号
     * @param userId 用户id
     * @return ResponseData responseData
     */
    ResponseData weixinScanRefund(String orderNo, Integer userId);

    /**
     * 支付宝扫码支付
     * @param userId 用户id
     * @param authCode 二维码
     * @param totalFee 支付金额
     * @param deviceInfo 设备信息
     * @return ResponseData responseData
     */
    ResponseData aliScanPay(Integer userId, String authCode, Integer totalFee, String deviceInfo);

    /**
     * 支付宝扫码支付退款
     * @param orderNo 订单号
     * @param userId 用户id
     * @return ResponseData responseData
     */
    ResponseData aliScanRefund(String orderNo, Integer userId);

    void insert(PaymentBill paymentBill);

    void update(PaymentBill paymentBill);

    PaymentBill selectById(Integer id);

    PaymentBill selectByOrderNoAndTradeType(String orderNo,byte tradeType,byte payChannel,byte status);

}
