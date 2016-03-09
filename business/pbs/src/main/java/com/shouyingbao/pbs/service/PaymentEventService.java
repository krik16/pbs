package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.entity.PaymentEvent;

/**
 * kejun
 * 2016/3/8 19:02
 **/
public interface PaymentEventService {

    void insertEvent(PaymentEvent paymentEvent);

    void update(PaymentEvent paymentEvent);

    PaymentEvent insertEvent(String orderNo, Integer billId, String tradeNo, String result, Integer payAmount, String buyerId, String buyerEmail, String openId,String refundNo);
}
