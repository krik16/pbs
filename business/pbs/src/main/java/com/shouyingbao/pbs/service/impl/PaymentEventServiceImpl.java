package com.shouyingbao.pbs.service.impl;

import com.shouyingbao.pbs.core.common.util.DateUtil;
import com.shouyingbao.pbs.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.shouyingbao.pbs.pbs.entity.PaymentEvent;
import com.shouyingbao.pbs.service.PaymentEventService;
import org.springframework.stereotype.Service;

/**
 * kejun
 * 2016/3/8 19:02
 **/
@Service
public class PaymentEventServiceImpl extends BaseServiceImpl implements PaymentEventService{


    private static final String NAMESPACE = "com.shouyingbao.pbs.mapper.PaymentEventMapper";

    @Override
    public void insert(PaymentEvent paymentEvent) {
        this.getBaseDao().insertBySql(NAMESPACE+".insertSelective",paymentEvent);
    }

    @Override
    public void update(PaymentEvent paymentEvent) {
        this.getBaseDao().insertBySql(NAMESPACE+".updateByPrimaryKeySelective",paymentEvent);
    }

    @Override
    public PaymentEvent init(String orderNo,Integer billId,String tradeNo,String result,Integer payAmount,String buyerId,String buyerEmail,String openId) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderNo(orderNo);
        paymentEvent.setPaymentBillId(billId);
        paymentEvent.setCreateAt(DateUtil.getCurrDateTime());
        paymentEvent.setFinishAt(DateUtil.getCurrDateTime());
        paymentEvent.setTradeNo(tradeNo);
        paymentEvent.setResult(result);
        paymentEvent.setPayAmount(payAmount);
        paymentEvent.setBuyerId(buyerId);
        paymentEvent.setBuyerEmail(buyerEmail);
        paymentEvent.setOpenId(openId);
        insert(paymentEvent);
        return paymentEvent;
    }
}
