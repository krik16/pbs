package com.rongyi.rpb.service.impl;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.vo.PayAccountUseTotal;
import com.rongyi.easy.rpb.vo.PaySuccessResponse;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.service.PaymentLogInfoService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.service.RpbEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 柯军
 * Description: 订单支付成功事件记录
 * datetime:2015年4月23日上午10:02:34
 **/
@Service
public class PaymentLogInfoServiceImpl extends BaseServiceImpl implements PaymentLogInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentLogInfoServiceImpl.class);

    private static final String LOG_NAMESPACE = "com.rongyi.rpb.mapper.PaymentLogInfoLogMapper";

    @Autowired
    PaymentService paymentService;

    @Autowired
    Sender sender;

    @Autowired
    RpbEventService rpbEventService;

    @Override
    public Map<String, String> insert(PaymentLogInfo logInfo) {
        Map<String, String> map = new HashMap<>();
        try {
            this.getBaseDao().insertBySql(LOG_NAMESPACE + ".insert", logInfo);
            map.put("message", "成功插入返回的message数据！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            map.put("message", e.getMessage());
        }
        return map;
    }

    @Override
    public Integer insertGetId(PaymentLogInfo logInfo) {
        try {
            this.getBaseDao().insertBySql(LOG_NAMESPACE + ".insert", logInfo);
            return logInfo.getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @Override
    public PaymentLogInfo selectByOutTradeNo(String outTradeNo, Integer tradeType) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("outTradeNo", outTradeNo);
        resultMap.put("tradeType", tradeType);
        return this.getBaseDao().selectOneBySql(LOG_NAMESPACE + ".selectByPayNoAndTradeType", resultMap);
    }

    @Override
    public PaymentLogInfo selectById(int id) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        return this.getBaseDao().selectOneBySql(LOG_NAMESPACE + ".selectById", resultMap);
    }

    @Override
    public PaymentLogInfo selectByNotifyId(String notifyId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("notifyId", notifyId);
        return this.getBaseDao().selectOneBySql(LOG_NAMESPACE + ".selectByNotifyId", resultMap);
    }

    @Override
    public PaymentLogInfo selectByPayTradeNo(String tradeNo) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("tradeNo", tradeNo);
        return this.getBaseDao().selectOneBySql(LOG_NAMESPACE + ".selectByTradeNo", resultMap);
    }

    @Override
    public boolean insertPayNotify(PaymentLogInfo paymentLogInfo, Integer tradeType, Integer status, String payChannel) {
        Integer realPayChannel = paymentService.getRealPayChannel(Integer.valueOf(payChannel));
        LOGGER.info("第三方支付成功通知更新付款状态并记录付款事件,insertPayNotify payNo={},tradeNo={},payChannel={},realPayChannel={}", paymentLogInfo.getOutTradeNo(), paymentLogInfo.getTrade_no(), payChannel, realPayChannel);
        try {
            PaymentEntity paymentEntity = paymentService.selectByPayNoAndPayChannelAndTradeType(paymentLogInfo.getOutTradeNo(), realPayChannel, tradeType, null);
            if (paymentEntity != null) {
                //获取行锁
                PaymentEntity withLockPaymentEntity = paymentService.selectByWithLock(paymentEntity.getId());
                if (validateRepeatPay(withLockPaymentEntity.getPayNo(), paymentLogInfo, realPayChannel)) { // 验证是否是重复支付
                    LOGGER.info("此笔订单属于重复支付，付款单号payNo={}", withLockPaymentEntity.getPayNo());
                    return false;
                }
                if (Constants.PAYMENT_STATUS.STAUS2 != withLockPaymentEntity.getStatus()) {
                    synchronized (this) {
                        boolean bool = validateByTradeNoAndPayNo(paymentLogInfo.getTrade_no(), paymentLogInfo.getOutTradeNo());
                        if (bool) {
                            LOGGER.info("支付重复通知，忽略此通知，tradeNo={},payChannel={}", paymentLogInfo.getTrade_no(), payChannel);
                            return false;
                        }
                        insertGetId(paymentLogInfo);
                    }
                    String orderNums = paymentService.getOrderNumStrsByPayNo(paymentLogInfo.getOutTradeNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
                    paymentService.updateListStatus(paymentLogInfo.getOutTradeNo(), tradeType, status, realPayChannel);// 修改付款单状态
                    paySuccessToMessage(paymentLogInfo.getOutTradeNo(), paymentLogInfo.getBuyer_email(), orderNums, withLockPaymentEntity.getOrderType(), payChannel);
                    LOGGER.info("更新付款单状态，记录付款事件成功，payNo={}", withLockPaymentEntity.getPayNo());
                    return true;
                }
            }
            LOGGER.error("支付单号未查询到未支付状态付款记录，忽略此笔支付通知,payNo={}", paymentLogInfo.getOutTradeNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateRepeatPay(String payNo, PaymentLogInfo paymentLogInfo, Integer payChannel) {
        boolean bool = validateByTradeNoAndPayNo(paymentLogInfo.getTrade_no(), paymentLogInfo.getOutTradeNo());
        if (bool) {
            LOGGER.info("支付重复通知，忽略此通知，tradeNo={},payChannel={}", paymentLogInfo.getTrade_no(), payChannel);
            return false;
        }
        PaymentEntity paymentEntity = paymentService.validateRepeatPay(payNo, payChannel);
        if (paymentEntity != null) {// 重复支付
            paymentLogInfo.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE5);
            return paymentService.repeatPayToRefund(paymentEntity, paymentLogInfo);
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PaySuccessResponse> paySuccessToMessage(String out_trade_no, String buyerEmail, String orderNums, Integer orderType, String payChannel) {
        List<PaySuccessResponse> responseList = new ArrayList<>();
        if (orderNums != null) {
            String[] orderNumArray = orderNums.split(",");
            String target = Constants.SOURCETYPE.COUPON;// 优惠券订单
            for(String orderNum : orderNumArray){
                if (Constants.ORDER_TYPE.ORDER_TYPE_0 == orderType)// 商品订单
                    target = Constants.SOURCETYPE.OSM;
                MessageEvent event = rpbEventService.getMessageEvent(out_trade_no, orderNum, null, payChannel, buyerEmail, Constants.SOURCETYPE.RPB, target, PaymentEventType.BUYER_PAID);
                String response = sender.convertSendAndReceive(event);
                if (response == null)
                    continue;
                MessageEvent responseEvent = rpbEventService.messageToMessageEvent(response);
                Map<String, Object> map = (Map<String, Object>) responseEvent.getBody();
                if (map.get("orderNum") != null && map.get("result") != null) {
                    PaySuccessResponse paySuccessResponse = new PaySuccessResponse((String) map.get("orderNum"), (Map<String, Object>) map.get("result"));
                    responseList.add(paySuccessResponse);
                }
            }
        }
        return responseList;
    }

    @Override
    public void updateDeleteStatus(Integer id, Integer deleteStatus) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        resultMap.put("deleteStatus", deleteStatus);
        this.getBaseDao().updateBySql(LOG_NAMESPACE + ".updateDeleteStatus", resultMap);
    }

    @Override
    public List<PayAccountUseTotal> selectPayAccountUseTotal(Map<String, Object> map) {
        return this.getBaseDao().selectListBySql(LOG_NAMESPACE + ".selectPayAccountUseTotal", map);
    }

    @Override
    public boolean validateByTradeNoAndPayNo(String tradeNo, String payNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("tradeNo", tradeNo);
        map.put("payNo", payNo);
        Integer count = this.getBaseDao().selectOneBySql(LOG_NAMESPACE + ".validateByTradeNoAndPayNo", map);
        return (count != null && count > 0);
    }

    @Override
    public PaymentLogInfo getByWeixinNotify(Map<String, Object> map) {
        PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
        paymentLogInfo.setTrade_no(map.get("transaction_id").toString());// 交易流水号
        paymentLogInfo.setOutTradeNo(map.get("out_trade_no").toString());
        paymentLogInfo.setNotifyTime(DateUtil.getCurrDateTime());
        paymentLogInfo.setReplayFlag(Constants.REPLAY_FLAG.REPLAY_FLAG3);
        paymentLogInfo.setSign(map.get("sign").toString());
        paymentLogInfo.setTradeMode("1");
        paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
        paymentLogInfo.setBuyer_type(0);// 买家账号
        paymentLogInfo.setEventType(Constants.EVENT_TYPE.EVENT_TYPE2);
        paymentLogInfo.setTotal_fee(Double.valueOf(map.get("total_fee").toString()) / 100);// 微信金额单位为分
        paymentLogInfo.setResult(map.get("return_code").toString());
        paymentLogInfo.setBuyer_email(map.get("openid").toString());
        paymentLogInfo.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
        return paymentLogInfo;
    }
}
