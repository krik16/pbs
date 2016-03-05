package com.rongyi.rpb.service;

import com.rongyi.easy.rpb.domain.PaymentOrderOp;

/**
 * Created by kejun on 2015/12/17.
 */
public interface PaymentOrderOpService {

    void insert(PaymentOrderOp paymentOrderOp);

    void update(PaymentOrderOp paymentOrderOp);

    PaymentOrderOp selectById(Integer id);

    PaymentOrderOp selectByPaymentId(Integer paymentId);
}
