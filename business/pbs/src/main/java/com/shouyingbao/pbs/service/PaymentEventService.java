package com.shouyingbao.pbs.service;

import com.shouyingbao.pbs.pbs.entity.PaymentEvent;

/**
 * kejun
 * 2016/3/8 19:02
 **/
public interface PaymentEventService {

    void insert(PaymentEvent paymentEvent);

    void update(PaymentEvent paymentEvent);

    PaymentEvent init(String orderNo,Integer billId,String tradeNo,String result,Integer payAmount,String buyerId,String buyerEmail,String openId);
}
