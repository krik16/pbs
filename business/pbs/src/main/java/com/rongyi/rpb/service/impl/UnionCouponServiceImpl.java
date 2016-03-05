package com.rongyi.rpb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.rmmm.vo.OrderWithCouponInfoVO;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.entity.UnionActivityRegister;
import com.rongyi.easy.rpb.entity.UnionCouponLogEntity;
import com.rongyi.easy.rpb.vo.UnionCouponLogVO;
import com.rongyi.rpb.common.pay.union.UnionUtil;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.service.PaymentLogInfoService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.service.UnionCouponService;
import com.rongyi.rpb.service.UnionpayMarketingService;
import com.rongyi.rss.mallshop.order.ROAOrderFormServiceWithCouponInfo;
import com.rongyi.rss.rpb.OrderNoGenService;

/**
 * @Author: 柯军
 * @Description: 银联支付
 * @datetime:2015年4月23日上午10:05:23
 * 
 **/
@Service
public class UnionCouponServiceImpl extends BaseServiceImpl implements UnionCouponService {

	private static final String NAMESPACE = "com.rongyi.rpb.mapper.UnionCouponLogMapper";

	private static final Logger LOGGER = Logger.getLogger(UnionCouponServiceImpl.class);

	@Autowired
	PaymentService paymentService;

	@Autowired
	UnionpayMarketingService unionpayMarketingService;

	@Autowired
	ROAOrderFormServiceWithCouponInfo rOAOrderFormServiceWithCouponInfoImpl;

	@Autowired
	OrderNoGenService orderNoGenService;

	@Autowired
	PaymentLogInfoService paymentLogInfoService;

	@Override
	public Map<String, Object> doMessage(Map<String, String[]> paramMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		UnionCouponLogEntity unionCouponLogEntity = UnionCouponLogEntity.mapToEntity(paramMap);
		// Map<String, Object> vadateMessageMap =
		// validateCoupon(unionCouponLogEntity.getCouponNo(),unionCouponLogEntity.getShopNo());//
		// 券码验证结果消息
		UnionActivityRegister unionActivityRegister = unionpayMarketingService.selectByShopNo(unionCouponLogEntity.getShopNo());
		if (ConstantEnum.UNION_MSG_TXN_CODE_TWO.getValueStr().equals(unionCouponLogEntity.getMsgTxnCode())) {
			if (paramMap.get("orig_req_serial_no") != null)
				unionCouponLogEntity.setReqSerialNo(paramMap.get("orig_req_serial_no")[0].toString());
			if (paramMap.get("req_serial_no") != null)
				unionCouponLogEntity.setTwoReqSerialNo(paramMap.get("req_serial_no")[0].toString());
			map = responseUnionCouponTwo(unionCouponLogEntity.getMsgCrrltnId(), unionCouponLogEntity.getMsgSysSn(), unionActivityRegister.getEventDesc(), unionActivityRegister.getEventNo(),
					unionCouponLogEntity.getCouponNo());
			LOGGER.info("第二次验证券码发送消息：" + JSONObject.fromObject(map).toString());
		} else {
			map = responseUnionCouponOne(unionCouponLogEntity.getMsgCrrltnId(), unionCouponLogEntity.getMsgSysSn(), unionActivityRegister.getEventDesc(), unionActivityRegister.getEventNo());
			LOGGER.info("第一次验证券码发送消息：" + JSONObject.fromObject(map).toString());
		}
		insert(unionCouponLogEntity);

		return map;
	}

	@Override
	public void insert(UnionCouponLogEntity unionCouponLogEntity) {
		this.getBaseDao().insertBySql(NAMESPACE + ".insertSelective", unionCouponLogEntity);
	}

	@Override
	public UnionCouponLogEntity selectByMsgtxCodeAndTransNo(String msgTxnCode, String transCrrltnNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgTxnCode", msgTxnCode);
		map.put("transCrrltnNo", transCrrltnNo);
		return this.getBaseDao().selectOneBySql(NAMESPACE + ".selectByMsgtxCodeAndTransNo", map);
	}

	/**
	 * @Description: 券码一次验证response消息
	 * @param msgCrrltnId
	 * @param msgSysSn
	 * @param posMonitor
	 * @param eventNo
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月6日上午11:34:10
	 **/
	private Map<String, Object> responseUnionCouponOne(String msgCrrltnId, String msgSysSn, String posMonitor, String eventNo) {
		Map<String, Object> map = reponseUnionCouponHeader(msgCrrltnId, msgSysSn, ConstantEnum.UNION_MSG_TXN_CODE.getValueStr());
		map.put(ConstantEnum.UNION_CONSUME_TYPE.getCodeStr(), ConstantEnum.UNION_CONSUME_TYPE.getValueStr());
		map.put(ConstantEnum.UNION_AMOUNT_MODE.getCodeStr(), ConstantEnum.UNION_AMOUNT_MODE.getValueStr());
		map.put(ConstantEnum.UNION_POS_MONITOR.getCodeStr(), (posMonitor != null) ? posMonitor : "");
		map.put(ConstantEnum.UNION_EVENT_NO.getCodeStr(), eventNo);
		map.put(ConstantEnum.UNION_PWD_MODE.getCodeStr(), ConstantEnum.UNION_PWD_MODE.getValueStr());
		String parmaPlain = UnionUtil.getParamPlain(map);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		map.put(ConstantEnum.UNION_SIGN.getCodeStr(), sign);
		return map;
	}

	/**
	 * @Description: 券码二次验证response消息
	 * @param msgCrrltnId
	 * @param msgSysSn
	 * @param posMonitor
	 * @param eventNo
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月6日上午11:34:51
	 **/
	private Map<String, Object> responseUnionCouponTwo(String msgCrrltnId, String msgSysSn, String posMonitor, String eventNo, String couponNo) {
		Map<String, Object> map = reponseUnionCouponHeader(msgCrrltnId, msgSysSn, ConstantEnum.UNION_MSG_TXN_CODE_TWO.getValueStr());
		OrderWithCouponInfoVO orderWithCouponInfoVO = getOrderCouponInfo(couponNo);
		if (orderWithCouponInfoVO.getCouponInfoList() == null || orderWithCouponInfoVO.getCouponInfoList().isEmpty()) {// 优惠金额
			map.put("discount_amt", "0");
			map.put("pos_monitor", "无优惠活动");
		} else {
			map.put("discount_amt", orderWithCouponInfoVO.getCouponInfoList().get(0).getCouponDiscount().doubleValue() * 100);
			map.put("pos_monitor", orderWithCouponInfoVO.getCouponInfoList().get(0).getRecommend());
		}
		map.put("pay_amt", orderWithCouponInfoVO.getTotalAmount().doubleValue() * 100);// 实付金额
		map.put("pos_confirm_mode", 0);
		map.put(ConstantEnum.UNION_EVENT_NO.getCodeStr(), eventNo);
		String parmaPlain = UnionUtil.getParamPlain(map);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		map.put(ConstantEnum.UNION_SIGN.getCodeStr(), sign);
		return map;
	}

	/**
	 * @Description: 券码验证消息应答常规项
	 * @param msgCrrltnId
	 * @param msgSysSn
	 * @param posMonitor
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月3日下午5:38:02
	 **/
	@Override
	public Map<String, Object> reponseUnionCouponHeader(String msgCrrltnId, String msgSysSn, String msgTxnCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ConstantEnum.UNION_MSG_TYPE.getCodeStr(), ConstantEnum.UNION_MSG_TYPE.getValueStr());
		map.put(ConstantEnum.UNION_MSG_TXN_CODE.getCodeStr(), msgTxnCode);
		map.put(ConstantEnum.UNION_MSG_CRRLTN_ID.getCodeStr(), msgCrrltnId);
		map.put(ConstantEnum.UNION_MSG_FLG.getCodeStr(), ConstantEnum.UNION_MSG_FLG.getValueStr());
		map.put(ConstantEnum.UNION_MSG_SENDER.getCodeStr(), ConstantEnum.UNION_MSG_SENDER.getValueStr());
		map.put(ConstantEnum.UNION_MSG_TIME.getCodeStr(), ConstantEnum.UNION_MSG_TIME.getValueStr());
		map.put(ConstantEnum.UNION_MSG_SYS_SN.getCodeStr(), msgSysSn);
		map.put(ConstantEnum.UNION_MSG_VER.getCodeStr(), ConstantEnum.UNION_MSG_VER.getValueStr());
		map.put(ConstantEnum.UNION_MSG_RSP_CODE.getCodeStr(), ConstantEnum.UNION_MSG_RSP_CODE.getValueStr());
		map.put(ConstantEnum.UNION_MSG_RSP_DESC.getCodeStr(), ConstantEnum.UNION_MSG_RSP_DESC.getValueStr());
		return map;
	}

	/**
	 * @Description: 调用优惠券系统验证券码
	 * @param couponNo
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日上午9:52:26
	 **/
	// private Map<String, Object> validateCoupon(String couponNo,String shopNo)
	// {
	// Map<String, Object> map = new HashMap<String, Object>();
	// PaymentEntity paymentEntity = removeUnionPrefixPayNo(couponNo);
	// // TODO 验证券码合理性
	// return map;
	// }

	/**
	 * @Description: 根据订单号查找对应订单及优惠券信息
	 * @param orderNum
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午5:30:04
	 **/
	private OrderWithCouponInfoVO getOrderCouponInfo(String couponNo) {
		OrderWithCouponInfoVO orderWithCouponInfoVO = null;
		try {
			PaymentEntity paymentEntity = removeUnionPrefixPayNo(couponNo);
			if (paymentEntity == null)
				throw new RuntimeException("银联扫码-->" + couponNo + "未找到对应的付款单信息！");
			orderWithCouponInfoVO = rOAOrderFormServiceWithCouponInfoImpl.getOrderCouponInfo(paymentEntity.getOrderNum());
			if (orderWithCouponInfoVO == null)
				throw new RuntimeException("订单号-->" + paymentEntity.getOrderNum() + "未找到对应的订单信息！");
			if (orderWithCouponInfoVO.getCouponInfoList() == null || orderWithCouponInfoVO.getCouponInfoList().isEmpty())
				LOGGER.info("订单号-->" + paymentEntity.getOrderNum() + "未找到对应的现金券信息！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderWithCouponInfoVO;
	}

	/**
	 * @Description: 移除银联特殊格式的付款单前缀并查询对应付款信息
	 * @param orderNo
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月6日下午3:24:45
	 **/
	private PaymentEntity removeUnionPrefixPayNo(String prefixPayNo) {
		try {
			String payNo = prefixPayNo.split(ConstantEnum.UNION_COUPON_PREFIX.getValueStr())[1];
			List<PaymentEntity> paymentEntityList = paymentService.selectByPayNo(payNo);
			if (paymentEntityList != null && !paymentEntityList.isEmpty())
				return paymentEntityList.get(0);
		} catch (Exception e) {
			LOGGER.error("去除银联格式的订单号前缀失败，订单号=" + prefixPayNo + "失败原因：");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void refundOperate(UnionCouponLogVO unionCouponLogVO) {
		List<PaymentEntity> oldPaymentEntityList = paymentService.selectByPayNoAndTradeType(unionCouponLogVO.getPayNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
		if (oldPaymentEntityList == null || oldPaymentEntityList.isEmpty())
			LOGGER.error("付款单号-->" + unionCouponLogVO.getPayNo() + "未找到付款记录。");
		PaymentEntity orderPaymentEntity = oldPaymentEntityList.get(0);
		PaymentEntity paymentEntity = new PaymentEntity(paymentService.getPayNo(), orderPaymentEntity.getOrderNum(), Constants.ORDER_TYPE.ORDER_TYPE_0, orderPaymentEntity.getOrderPrice(),
				orderPaymentEntity.getTitle(), orderPaymentEntity.getAmountMoney(), Constants.PAYMENT_STATUS.STAUS2, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE2, DateUtil.getCurrDateTime(),
				DateUtil.getCurrDateTime(), Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL2, null, null, null, null, null);
		paymentService.insert(paymentEntity);// 生成退款记录
		PaymentLogInfo paymentLogInfo = new PaymentLogInfo(Constants.REPLAY_FLAG.REPLAY_FLAG4, unionCouponLogVO.getMsgCrrltnId(), null, null, DateUtil.getCurrDateTime(), null, null,
				unionCouponLogVO.getTransCrrltnNo(), unionCouponLogVO.getEncCardNo(), unionCouponLogVO.getPartCardNo(), 0, "1", unionCouponLogVO.getReqSerialNo(), DateUtil.getCurrDateTime(),
				unionCouponLogVO.getPayNo(), null, unionCouponLogVO.getPayAmt(), "success", 3,0);
		paymentLogInfoService.insert(paymentLogInfo);// 生成退款事件
	}

}
