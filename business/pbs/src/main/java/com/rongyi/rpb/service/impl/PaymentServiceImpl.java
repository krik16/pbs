package com.rongyi.rpb.service.impl;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.common.util.JsonUtil;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentItemEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.easy.tms.vo.MQDrawParam;
import com.rongyi.rpb.Exception.TradeException;
import com.rongyi.rpb.common.pay.weixin.model.PaySignData;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.nsynchronous.OrderFormNsyn;
import com.rongyi.rpb.service.*;
import com.rongyi.rpb.web.controller.v5.WebPageAlipayController;
import com.rongyi.rss.rpb.OrderNoGenService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author: 柯军
 * Description: 付款单
 * datetime:2015年4月23日上午10:03:11
 **/
@Service
public class PaymentServiceImpl extends BaseServiceImpl implements PaymentService {

    private static final String PAYMENTENTITY_NAMESPACE = "com.rongyi.rpb.mapper.PaymentEntityMapper";

    @Autowired
    AliPaymentService aliPaymentService;

    @Autowired
    WebPageAlipayService webPageAlipayService;

    @Autowired
    WebPageAlipayController webPageAlipayController;

    @Autowired
    PaymentLogInfoService paymentLogService;

    @Autowired
    PaymentItemService paymentItemService;

    @Autowired
    OrderNoGenService orderNoGenService;

    @Autowired
    RpbEventService rpbEventService;

    @Autowired
    PaymentLogInfoService paymentLogInfoService;

    @Autowired
    WeixinPayService weixinPayService;

    @Autowired
    OrderFormNsyn orderFormNsyn;

    @Autowired
    Sender sender;

    @Autowired
    WeixinMchService weixinMchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public List<PaymentEntityVO> selectPageListBySearch(Map<String, Object> searchValueMap) {
        return null;
    }

    @Override
    public Long selectPageListCountBySearch(Map<String, Object> searchValueMap) {
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectPageListCountBySearch", searchValueMap);
    }

    @Override
    public void insertByOrderDetailNum(PaymentEntity paymentEntity, String orderDetailNum) {
        try {
            this.getBaseDao().insertBySql(PAYMENTENTITY_NAMESPACE + ".insert", paymentEntity);
            // 子订单是分成多条存入
            if (StringUtils.isNotEmpty(orderDetailNum)) {
                String[] detailNumArray = orderDetailNum.split(",");
                for (String detailNum : detailNumArray) {
                    PaymentItemEntity paymentItemEntity = new PaymentItemEntity();
                    paymentItemEntity.setDetailNum(detailNum);
                    paymentItemEntity.setPaymentId(paymentEntity.getId());
                    paymentItemService.insert(paymentItemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入付款单记录失败，失败原因:" + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaymentEntityVO bodyToPaymentEntity(Object body, String type) {
        if (body == null)
            return null;
        PaymentEntityVO paymentEntityVO = new PaymentEntityVO();
        Map<String, Object> bodyMap = JsonUtil.getMapFromJson(body.toString());
        paymentEntityVO.setOrderNum(bodyMap.get("orderNum").toString());
        if (bodyMap.get("orderType") != null)
            paymentEntityVO.setOrderType(Integer.valueOf(bodyMap.get("orderType").toString()));
        else
            paymentEntityVO.setOrderType(Constants.ORDER_TYPE.ORDER_TYPE_0);
        if (bodyMap.get("totalPrice") != null)
            paymentEntityVO.setAmountMoney(BigDecimal.valueOf(Double.valueOf(bodyMap.get("totalPrice").toString())));
        else
            paymentEntityVO.setAmountMoney(new BigDecimal(0));
        paymentEntityVO.setCreateTime(DateUtil.getCurrDateTime());
        paymentEntityVO.setTitle(getTitle(paymentEntityVO.getOrderNum()));
        if (bodyMap.get("orderDetailNum") != null)
            paymentEntityVO.setOrderDetailNumArray(bodyMap.get("orderDetailNum").toString());
        else
            paymentEntityVO.setOrderDetailNumArray("");
        if (bodyMap.get("weidianId") != null && StringUtils.isNotEmpty(bodyMap.get("weidianId").toString()))
            paymentEntityVO.setShowNum(Integer.valueOf(bodyMap.get("weidianId").toString()));
        if (bodyMap.get("paymentId") != null && StringUtils.isNotEmpty(bodyMap.get("paymentId").toString()))
            paymentEntityVO.setPayNo(bodyMap.get("paymentId").toString());
        paymentEntityVO.setStatus(0);
        if (PaymentEventType.WEIXIN_PAY.equals(type))
            paymentEntityVO.setPayChannel(1);
        else if (PaymentEventType.UNION_PAY.equals(type))
            paymentEntityVO.setPayChannel(2);
        else
            paymentEntityVO.setPayChannel(0);
        //交易开始时间
        if (bodyMap.get("timeStart") != null) {
            paymentEntityVO.setTimeStart(bodyMap.get("timeStart").toString());
        }
        //交易失效时间
        if (bodyMap.get("timeExpire") != null) {
            paymentEntityVO.setTimeExpire(bodyMap.get("timeExpire").toString());
        }
        if (bodyMap.get("appId") != null) {
            paymentEntityVO.setAppId(bodyMap.get("appId").toString());
        } else {
            paymentEntityVO.setAppId("");
        }
        if (bodyMap.get("openId") != null) {
            paymentEntityVO.setOpenId(bodyMap.get("openId").toString());
        }
        if (bodyMap.get("weixinPayType") != null && !"null".equals(bodyMap.get("weixinPayType").toString())) {
            paymentEntityVO.setWeixinPayType(Integer.valueOf(bodyMap.get("weixinPayType").toString()));
        }

        return paymentEntityVO;
    }

    @Override
    public List<PaymentEntity> getPaymemtsByMoreOrderNum(PaymentEntityVO paymentEntityVO) {
        List<PaymentEntity> paymentEntityList = new ArrayList<>();
        String[] orderNumArray = paymentEntityVO.getOrderNum().split(",");
        PaymentEntity paymentEntity;
        String payNo = getPayNo();// 生成付款单号,多个订单号付款单号一样
        LOGGER.info("生成付款单号：" + payNo);
        if (orderNumArray.length > 0) {
            for (String orderNum : orderNumArray) {
                paymentEntity = new PaymentEntity();
                BeanUtils.copyProperties(paymentEntityVO, paymentEntity);
                paymentEntity.setOrderNum(orderNum);
                paymentEntity.setPayNo(payNo);
                paymentEntityList.add(paymentEntity);
            }
        } else {
            paymentEntity = new PaymentEntity();
            BeanUtils.copyProperties(paymentEntityVO, paymentEntity);
            paymentEntity.setPayNo(payNo);
            paymentEntityList.add(paymentEntity);
        }
        return paymentEntityList;

    }

    @Override
    public String getOrderNumStrsByPayNo(String payNo, Integer tradeType) {
        String orderNum = "";
        List<PaymentEntity> list = selectByPayNoAndTradeType(payNo, tradeType);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                orderNum += list.get(i).getOrderNum();
                if (i < list.size() - 1) {
                    orderNum += ",";
                }
            }
        }
        return orderNum;
    }

    @Override
    public List<PaymentEntity> selectByPayNo(String payNo) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("payNo", payNo);
            return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".selectByPayNo", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getSendMessage(MessageEvent event) {
        Map<String, Object> messageMap = new HashMap<>();
        try {
            if (PaymentEventType.BUYER_PAID.equals(event.getType()))// 支付成功回调通知
                return null;
            PaymentEntityVO paymentEntityVO = insertOrderMessage(event);// 插入数据库
            messageMap.put("timestamp", DateUtil.getCurrDateTime().getTime());
            messageMap.put("source", Constants.SOURCETYPE.RPB);
            messageMap.put("type", event.getType());
            if (paymentEntityVO.getAmountMoney() == null || isZero(paymentEntityVO.getAmountMoney())) {// 如果支付价格为0，则不用获取支付宝的签名，直接返回数据
                LOGGER.info("商品价格为0");
                return getZeroSendMessage(event, messageMap, paymentEntityVO);
            }
            messageMap.put("body", JSONObject.fromObject(getBodyMap(paymentEntityVO, event)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("处理订单信息失败：" + event.getBody().toString() + ",失败原因：" + e.getMessage());
        }

        return messageMap;
    }

    /**
     * Description: 生成消息主体
     *
     * @param paymentEntityVO PaymentEntityVO
     * @param event           Author: 柯军
     *                        datetime:2015年4月24日上午11:37:09
     **/
    @SuppressWarnings("unchecked")
    private Map<String, Object> getBodyMap(PaymentEntityVO paymentEntityVO, MessageEvent event) {

        Map<String, Object> bodyMap = new HashMap<>();
        try {
            if (PaymentEventType.APP.equals(event.getType())) {// 手机APP支付
                LOGGER.info("支付宝APP支付");
                bodyMap = aliPaymentService.getZhiFuBaoSign((Map<String, Object>) event.getBody(), paymentEntityVO);
            } else if (PaymentEventType.PAYMENT.equals(event.getType())) {// 手机网页支付
                LOGGER.info("支付宝网页支付");
                bodyMap = webPageAlipayService.getToken(paymentEntityVO);
            } else if (PaymentEventType.WEIXIN_PAY.equals(event.getType())) {// 微信支付
                LOGGER.info("微信支付");
                PaySignData paySignData = getPaySignData(paymentEntityVO);
//                bodyMap = weixinPayService.getAppWeXinSign(paymentEntityVO.getPayNo(), Double.valueOf(paymentEntityVO.getAmountMoney().toString()), paymentEntityVO.getTimeStart(), paymentEntityVO.getTimeExpire(), paymentEntityVO.getOrderType());
                bodyMap = weixinPayService.getAppWeXinSign(paySignData);
            } else if (PaymentEventType.UNION_PAY.equals(event.getType())) {// 银联支付
                LOGGER.info("银联支付");
                bodyMap.put("unionOrderNum", ConstantEnum.UNION_COUPON_PREFIX.getValueStr() + paymentEntityVO.getPayNo());
            } else if (PaymentEventType.PAY_TO_SELLER.equals(event.getType()) || PaymentEventType.REFUND.equals(event.getType())) {
                LOGGER.info("申请退款或打款给卖家");
                bodyMap.put("paymentId", paymentEntityVO.getPayNo());
            }
            bodyMap.put("totalPrice", paymentEntityVO.getAmountMoney().doubleValue() * 100d);
            bodyMap.put("orderNum", paymentEntityVO.getOrderNum());
            bodyMap.put("orderDetailNum", paymentEntityVO.getOrderDetailNumArray());
        } catch (Exception e) {
            LOGGER.error("处理订单信息失败：" + event.getBody().toString() + ",失败原因：" + e.getMessage());
            LOGGER.error(e.getMessage());
        }

        return bodyMap;

    }


    private PaySignData getPaySignData(PaymentEntityVO paymentEntityVO) {
        PaySignData paySignData = new PaySignData();
        BeanUtils.copyProperties(paymentEntityVO, paySignData);
        BigDecimal totalFee = new BigDecimal(Double.valueOf(paymentEntityVO.getAmountMoney().toString())).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
        paySignData.setTotalFee(totalFee.intValue());
        paySignData.setBody("容易网商品");
        return paySignData;
    }

    @Override
    public PaymentEntityVO insertOrderMessage(MessageEvent event) {
        PaymentEntityVO paymentEntityVO = bodyToPaymentEntity(event.getBody(), event.getType());
        String payNo;
        if (MqReceiverServiceImpl.isAppPay(event.getType())) {// 前端支付验证订单号是否已存在
            PaymentEntity paymentEntity = validateOrderNumExist(paymentEntityVO.getOrderNum(), paymentEntityVO.getPayChannel(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
            if (paymentEntity != null) {
                payNo = paymentEntity.getPayNo();
                boolean bool = validateWeixinModifyPrice(event.getType(), paymentEntityVO, paymentEntity);// 验证是否微信修改价格
                if (!bool) {
                    paymentEntityVO.setPayNo(payNo);
                    paymentEntity.setAmountMoney(paymentEntityVO.getAmountMoney());
                    paymentEntity.setCreateTime(DateUtil.getCurrDateTime());
                    updateByPrimaryKeySelective(paymentEntity);
                    LOGGER.info("订单号已存在，返回历史付款单号" + payNo);
                    return paymentEntityVO;
                } else if (paymentLogInfoService.selectByOutTradeNo(payNo, null) == null) {
                    LOGGER.info("微信支付修改价格，重新生成支付单号-->");
                    weixinPayService.closeOrder(payNo, paymentEntity.getWeixinMchId());
                }
            }

        }
        List<PaymentEntity> paymentEntityList = getPaymemtsByMoreOrderNum(paymentEntityVO);// 多个订单号生成多条记录对应一条付款单号
        String oldPayNo = paymentEntityVO.getPayNo();// 原订单号
        payNo = paymentEntityList.get(0).getPayNo();// 新付款单号
        paymentEntityVO.setPayNo(payNo);
        if (StringUtils.isEmpty(paymentEntityVO.getTitle())) {
            paymentEntityVO.setTitle(getTitle(payNo));
        }
        insertList(paymentEntityList, paymentEntityVO, event, oldPayNo);
        orderFormNsyn.updateOrderPrice(paymentEntityVO.getOrderNum());
        return paymentEntityVO;
    }

    /**
     * 验证是否是微信修改价格支付
     *
     * @param type            String
     * @param paymentEntityVO PaymentEntityVO
     * @param paymentEntity   PaymentEntity
     *                        Author kejun
     */
    private boolean validateWeixinModifyPrice(String type, PaymentEntityVO paymentEntityVO, PaymentEntity paymentEntity) {
        return (paymentEntity != null && PaymentEventType.WEIXIN_PAY.equals(type) && paymentEntity.getAmountMoney().doubleValue() != paymentEntityVO.getAmountMoney().doubleValue());
    }

    private void insertList(List<PaymentEntity> paymentEntityList, PaymentEntityVO paymentEntityVO, MessageEvent event, String oldPayNo) {
        for (PaymentEntity paymentEntity : paymentEntityList) {
            paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);// 默认支付
            if (PaymentEventType.PAY_TO_SELLER.equals(event.getType())) {// 打款给卖家
                LOGGER.info("打款给卖家");
                paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE2);
            } else if (PaymentEventType.REFUND.equals(event.getType())) {// 后端退款
                LOGGER.info("买家申请退款");
                paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1);
                // 退款时根据付款单号找到对应付款记录中的付款方式
                PaymentEntity historyPayment = selectByPayNoAndPayChannelAndTradeType(oldPayNo, null, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2);
                if (historyPayment == null)
                    throw new TradeException(ConstantEnum.EXCEPTION_PAYMENT_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_PAYMENT_NOT_EXIST.getValueStr());
                paymentEntity.setPayChannel(historyPayment.getPayChannel());
            }
            paymentEntity.setTitle(paymentEntityVO.getTitle());
            if (paymentEntityVO.getAmountMoney() == null || isZero(paymentEntityVO.getAmountMoney())) {// 支付款是0元是直接设置支付状态为已支付
                paymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS2);
                paymentEntity.setFinishTime(DateUtil.getCurrDateTime());
            }
            if (paymentEntityVO.getAmountMoney().doubleValue() == 0)
                paymentEntity.setPayChannel(null);
            if (StringUtils.isNotBlank(paymentEntityVO.getAppId())) {
                WeixinMch weixinMch = weixinMchService.selectByAppIdAndTradeType(paymentEntityVO.getAppId(), paymentEntityVO.getWeixinPayType());
                if (weixinMch != null) {
                    paymentEntity.setWeixinMchId(weixinMch.getId());
                }
            }
            insertByOrderDetailNum(paymentEntity, paymentEntityVO.getOrderDetailNumArray());// 插入数据库
            LOGGER.info("==================插入数据库成功==============");
        }
    }

    @Override
    public PaymentEntity validateOrderNumExist(String orderNum, Integer payChannel, Integer tradeType) {
        if (orderNum == null)
            return null;
        String[] orderNumArray = orderNum.split(",");
        //验证请求付款记录是否已存在，如果存在，若有同一类型支付方式则返回原有数据，如果没有同一类型则创建一个订单号和付款单号同一个的付款记录，如果不存在则新建
        for (String orderNo : orderNumArray) {
            LOGGER.info("orderNum={},tradeType={},payChannel={}", orderNo, tradeType, payChannel);
            List<PaymentEntity> list = selectByOrderNum(orderNo, tradeType, null);
            if (list != null && !list.isEmpty()) {
                PaymentEntity newPaymentEntity = new PaymentEntity();
                for (PaymentEntity paymentEntity : list) {
                    if (Constants.PAYMENT_STATUS.STAUS2 == paymentEntity.getStatus() && payChannel == paymentEntity.getPayChannel()) {// 订单已完成支付后重新发起支付请求
                        throw new RuntimeException("此订单已成功支付,此次请求属于订单重复支付请求,请重新下单，订单号-->" + orderNum);
                    }
                    BeanUtils.copyProperties(paymentEntity, newPaymentEntity);
                    newPaymentEntity.setId(null);
                    newPaymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS0);
                    if (paymentEntity.getPayChannel() != null && payChannel.equals(paymentEntity.getPayChannel())) {
                        LOGGER.info("此订单payChannel={}支付方式未支付单已存在，直接返回此笔付款单记录,orderNum={}", paymentEntity.getPayChannel(), orderNo);
                        break;
                    } else {
                        PaymentEntity oldPaymentEntity = selectByPayNoAndPayChannelAndTradeType(paymentEntity.getPayNo(), payChannel, tradeType, Constants.PAYMENT_STATUS.STAUS0);
                        if (oldPaymentEntity == null) {
                            LOGGER.info("此订单payChannel={}支付方式未支付单不存在，新增新付款方式同支付单号待付款记录,orderNum={}", payChannel, orderNo);
                            newPaymentEntity.setCreateTime(DateUtil.getCurrDateTime());
                            newPaymentEntity.setPayChannel(payChannel);
                            insert(newPaymentEntity);
                        }
                        break;
                    }
                }
                return newPaymentEntity;
            }
        }
        return null;
    }

//    private void modifyPayChannelByMoreRequest(List<PaymentEntity> list, Integer payChannel) {
//        for (PaymentEntity paymentEntity : list) {
//            paymentEntity.setPayChannel(payChannel);
//            updateByPrimaryKeySelective(paymentEntity);
//        }
//    }

    /**
     * @param event           MessageEvent
     * @param messageMap      Map<String, Object>
     * @param paymentEntityVO PaymentEntityVO
     * @return Description: 0元支付或退款默认成功，消息立马返回
     * Author: 柯军
     * datetime:2015年7月12日下午2:29:33
     **/
    @Override
    public Map<String, Object> getZeroSendMessage(MessageEvent event, Map<String, Object> messageMap, PaymentEntityVO paymentEntityVO) {
        PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
        paymentLogInfo.setOutTradeNo(paymentEntityVO.getPayNo());
        paymentLogInfo.setNotifyTime(DateUtil.getCurrDateTime());
        paymentLogInfo.setResult("success");
        paymentLogInfo.setTradeMode("1");
        paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
        paymentLogInfo.setTotal_fee(0.00);
        paymentLogInfo.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
        if (PaymentEventType.REFUND.equals(event.getType())) {
            paymentLogInfo.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1);
        }
        paymentLogInfoService.insertGetId(paymentLogInfo);
        String type = PaymentEventType.BUYER_PAID;
        if (PaymentEventType.REFUND.equals(event.getType()))
            type = PaymentEventType.REFUND;
        String target = Constants.SOURCETYPE.OSM;
        if (Constants.ORDER_TYPE.ORDER_TYPE_1 == paymentEntityVO.getOrderType())
            target = Constants.SOURCETYPE.COUPON;
        event = rpbEventService.getMessageEvent(paymentEntityVO.getPayNo(), paymentEntityVO.getOrderNum(), paymentEntityVO.getOrderDetailNumArray(), PaymentEventType.ZERO_PAY, null,
                Constants.SOURCETYPE.RPB, target, type);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("totlePrice", 0);
        resultMap.put("orderNum", paymentEntityVO.getOrderNum());
        resultMap.put("paymentId", paymentEntityVO.getPayNo());
        resultMap.put("orderDetailNum", paymentEntityVO.getOrderDetailNumArray());
        messageMap.put("body", resultMap);
        sender.convertAndSend(event);
        return messageMap;
    }

    @Override
    public String getTitle(String orderNum) {
        return "容易网商品,订单号:" + orderNum;
    }

    @Override
    public List<PaymentEntity> selectByPayNoAndTradeType(String payNo, Integer tradeType) {
        Map<String, Object> params = new HashMap<>();
        params.put("payNo", payNo);
        params.put("tradeType", tradeType);
        return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".selectByPayNoAndTradeType", params);
    }

    @Override
    public Map<String, String> insert(PaymentEntity paymentEntity) {
        Map<String, String> map = new HashMap<>();
        try {
            this.getBaseDao().insertBySql(PAYMENTENTITY_NAMESPACE + ".insert", paymentEntity);
            map.put("message", "成功插入返回的message数据！");
        } catch (Exception e) {
            map.put("message", e.getMessage());
            LOGGER.error(e.getMessage());
        }
        return map;
    }

    @Override
    public PaymentEntity selectByPrimaryKey(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByPrimaryKey", params);
    }

    @Override
    public List<PaymentEntity> selectByOrderNum(String orderNum, Integer tradeType, Integer payChannel) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderNum);
        params.put("tradeType", tradeType);
        params.put("payChannel", payChannel);
        return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".selectByOrderNum", params);
    }

    @Override
    public PaymentEntity selectByOrderNumAndTradeType(String orderNum, Integer tradeType, Integer status, Integer payChannel) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderNum);
        params.put("tradeType", tradeType);
        params.put("status", status);
        params.put("payChannel", payChannel);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByOrderNumAndTradeType", params);
    }

    @Override
    public int updateByPrimaryKeySelective(PaymentEntity paymentEntity) {
        return this.getBaseDao().updateBySql(PAYMENTENTITY_NAMESPACE + ".updateByPrimaryKeySelective", paymentEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaymentEntity insertDrawApply(MessageEvent event) {
        PaymentEntity paymentEntity = new PaymentEntity();
        try {
            Map<String, Object> bodyMap = JsonUtil.getMapFromJson(event.getBody().toString());
            MQDrawParam mqDrawParam = MQDrawParam.mapToEntity(bodyMap);
//            PaymentEntity oldPaymentEntity = validateOrderNumExist(mqDrawParam.getDrawNo(), mqDrawParam.getPayType(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE3);
//            if (oldPaymentEntity != null && StringUtils.isNotEmpty(oldPaymentEntity.getPayNo())) {
//                LOGGER.info("提现单号已存在，返回历史付款单号" + oldPaymentEntity.getPayNo());
//            }
            if (mqDrawParam.getOrderType() != null)
                paymentEntity.setOrderType(mqDrawParam.getOrderType());
            if (mqDrawParam.getDrawAmount() != null)
                paymentEntity.setAmountMoney(BigDecimal.valueOf(mqDrawParam.getDrawAmount()));
            paymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS0);
            if (PaymentEventType.DRAW_PAY.equals(event.getType())) {// 提现
                LOGGER.info("生成提现申请记录，提现单号：" + mqDrawParam.getDrawNo());
                paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE3);
                paymentEntity.setPayNo(getPayNo());
                paymentEntity.setOrderNum(mqDrawParam.getDrawNo());
            } else {// 异常支付
                LOGGER.info("生成异常记录，异常单号：" + bodyMap.get("exceTradeNo"));
                paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE4);
                paymentEntity.setPayNo(String.valueOf(bodyMap.get("exceTradeNo")));
                paymentEntity.setOrderNum(mqDrawParam.getOrderNo());
            }
            paymentEntity.setCreateTime(DateUtil.getCurrDateTime());
            paymentEntity.setPayChannel(mqDrawParam.getPayType());
            paymentEntity.setOutAccount(mqDrawParam.getPayAccount());
            paymentEntity.setPayName(mqDrawParam.getPayName());
            paymentEntity.setDrawUserId(mqDrawParam.getUserId());
            insert(paymentEntity);
        } catch (Exception e) {
            LOGGER.error("插入提现(或异常支付)申请记录失败，失败原因：");
            e.printStackTrace();
        }
        return paymentEntity;
    }

    @Override
    public Map<String, Object> getBodyMap(String payNo, String orderNum, String orderDetailNum, String source, String target, String type) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("orderNum", orderNum);
        if (StringUtils.isNotEmpty(orderDetailNum))
            bodyMap.put("orderDetailNum", orderDetailNum);
        else
            bodyMap.put("orderDetailNum", "");
        bodyMap.put("paymentId", payNo);
        return bodyMap;
    }

    public static boolean isZero(BigDecimal arg) {
        return arg.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public List<PaymentEntity> updateListStatusBypayNo(String payNo, Integer type, Integer status) {
        return updateListStatusBypayNoAndPayChannel(payNo, type, status, null);
    }

    @Override
    public void deleteByPayNo(String payNo) {
        this.getBaseDao().delete(PAYMENTENTITY_NAMESPACE + ".deleteByPayNo", payNo);
    }

    @Override
    public PaymentEntity selectByOrderNumAndBatchNo(String orderNum, String batchNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNum", orderNum);
        params.put("batchNo", batchNo);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByOrderNumAndBatchNo", params);
    }

    @Override
    public PaymentEntity selectByPayNoAndPayChannelAndTradeType(String payNo, Integer payChannel, Integer tradeType, Integer status) {
        Map<String, Object> params = new HashMap<>();
        params.put("payNo", payNo);
        params.put("payChannel", payChannel);
        params.put("tradeType", tradeType);
        params.put("status", status);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByPayNoAndPayChannelAndTradeType", params);
    }

    @Override
    public void insertRepeatPay(PaymentEntity paymentEntity, PaymentLogInfo paymentLogInfo) {
        if (paymentEntity.getId() == null)
            insert(paymentEntity);
        else
            updateByPrimaryKeySelective(paymentEntity);
        paymentLogInfoService.insert(paymentLogInfo);
    }

    @Override
    public PaymentEntity validateRepeatPay(String payNo, Integer newPayChannel) {
        PaymentEntity paymentEntity = selectByPayNoAndPayChannelAndTradeType(payNo, null, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2);
        if (paymentEntity == null)// 无重复付款项
            return null;
        LOGGER.info("重复支付,已存在支付记录支付方式为-->" + paymentEntity.getPayChannel() + "重复支付方式为-->" + newPayChannel + ",重复付款单号-->" + payNo);
        PaymentEntity newPaymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(paymentEntity, newPaymentEntity);
        newPaymentEntity.setId(null);
        newPaymentEntity.setPayChannel(newPayChannel);
        newPaymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS2);
        newPaymentEntity.setFinishTime(DateUtil.getCurrDateTime());
        newPaymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE5);
        return newPaymentEntity;
    }

    @Override
    public boolean repeatPayToRefund(PaymentEntity paymentEntity, PaymentLogInfo paymentLogInfo) {
        try {
            if (paymentEntity != null) {// 重复支付
                insertRepeatPay(paymentEntity, paymentLogInfo);// 增加重复付款记录
                String payNo = getPayNo();
                PaymentEntity refundPaymentEntity = new PaymentEntity();
                BeanUtils.copyProperties(paymentEntity, refundPaymentEntity);
                refundPaymentEntity.setId(null);
                refundPaymentEntity.setFinishTime(null);
                refundPaymentEntity.setPayNo(payNo);
                refundPaymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE6);
                refundPaymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS0);
                LOGGER.info(Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0 == paymentEntity.getPayChannel() ? "支付宝" : "微信" + "重复支付直接退款-->退款单号" + payNo);
                if (Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL1 == paymentEntity.getPayChannel()) {// 微信自动退款
                    Map<String, Object> resultMap = weixinPayService.weixinRefund(paymentEntity.getPayNo(), paymentEntity.getAmountMoney().doubleValue(), paymentEntity.getAmountMoney().doubleValue(),
                            payNo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE6, paymentEntity.getWeixinMchId());
                    if (Constants.RESULT.SUCCESS.equals(resultMap.get("result")) || ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(resultMap.get("result")))
                        refundPaymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS2);
                    refundPaymentEntity.setFinishTime(DateUtil.getCurrDateTime());
                }
                insert(refundPaymentEntity);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<PaymentEntity> selectByBatchNoAndStatus(String batchNo, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("batchNo", batchNo);
        map.put("status", status);
        return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".selectByBatchNoAndStatus", map);
    }

    @Override
    public Integer getRealPayChannel(Integer payChannel) {
        switch (payChannel) {
            case 1:
                return Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0;
            case 3:
                return Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0;
            case 5:
                return Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL1;
            case 6:
                return Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL2;
            default:
                break;
        }
        return null;
    }

    @Override
    public void updateListStatus(String payNo, Integer tradeType, Integer status, Integer payChannel) {
        PaymentEntity paymentEntity = selectByPayNoAndPayChannelAndTradeType(payNo, payChannel, tradeType, null);
        paymentEntity.setFinishTime(DateUtil.getCurrDateTime());
        paymentEntity.setPayChannel(payChannel);
        paymentEntity.setStatus(status);
        updateByPrimaryKeySelective(paymentEntity);// 修改打款状态
    }

    private List<PaymentEntity> updateListStatusBypayNoAndPayChannel(String payNo, Integer type, Integer status, Integer payChannel) {
        List<PaymentEntity> paymentEntityList = selectByPayNoAndTradeType(payNo, type);
        if (paymentEntityList != null && !paymentEntityList.isEmpty()) {
            for (PaymentEntity paymentEntity : paymentEntityList) {
                if (paymentEntity != null) {
                    paymentEntity.setFinishTime(DateUtil.getCurrDateTime());
                    paymentEntity.setStatus(status);
                    if (payChannel != null)
                        paymentEntity.setPayChannel(payChannel);
                    updateByPrimaryKeySelective(paymentEntity);// 修改打款状态
                }
            }
        }
        return paymentEntityList;
    }

    @Override
    public List<PaymentEntity> selectByTradeTypeAndRefundRejected(Integer tradeType, Integer payChannel, Integer refundRejected, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("tradeType", tradeType);
        map.put("payChannel", payChannel);
        map.put("refundRejected", refundRejected);
        map.put("status", status);
        map.put("today", DateUtil.getToday());
        return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".selectByTradeTypeAndRefundRejected", map);
    }

    @Override
    public void updateRefundRejected(Integer id, Integer refundRejected) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("refundRejected", refundRejected);
        this.getBaseDao().updateBySql(PAYMENTENTITY_NAMESPACE + ".updateRefundRejected", map);
    }

    @Override
    public List<PaymentEntity> valiadteStatus(String[] ids, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("status", status);
        return this.getBaseDao().selectListBySql(PAYMENTENTITY_NAMESPACE + ".valiadteStatus", map);
    }

    @Override
    public String getPayNo() {
        return orderNoGenService.getOrderNo("0");
    }

    @Override
    public void updateByIds(String[] ids, Map<String, Object> map) {
        map.put("ids", ids);
        this.getBaseDao().updateBySql(PAYMENTENTITY_NAMESPACE + ".updateByIds", map);
    }

    @Override
    public PaymentEntity selectByWithLock(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByWithLock", params);
    }

    @Override
    public PaymentEntity selectByPayNoAndOrderNo(String payNo, String orderNo, Integer tradeType, Integer status) {
        Map<String,Object> map = new HashMap<>();
        map.put("payNo",payNo);
        map.put("orderNo",orderNo);
        map.put("tradeType",tradeType);
        map.put("status",status);
        return this.getBaseDao().selectOneBySql(PAYMENTENTITY_NAMESPACE + ".selectByPayNoAndOrderNo", map);
    }
}
