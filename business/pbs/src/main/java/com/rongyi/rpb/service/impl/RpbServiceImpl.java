/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年6月4日下午2:54:04
 * @Description: TODO
 *
 **/

package com.rongyi.rpb.service.impl;

import com.rongyi.core.bean.ResponseData;
import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.constant.PayEnum;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentItemEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.easy.rpb.vo.*;
import com.rongyi.rpb.Exception.AliPayException;
import com.rongyi.rpb.Exception.TradeException;
import com.rongyi.rpb.common.pay.ali.model.AliRefundResultData;
import com.rongyi.rpb.common.pay.weixin.model.RefundQueryResData;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.nsynchronous.OrderFormNsyn;
import com.rongyi.rpb.service.*;
import com.rongyi.rpb.unit.AliPayUnit;
import com.rongyi.rpb.unit.WeixinPayUnit;
import com.rongyi.rss.rpb.IRpbService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 柯军
 * Description: rpb dubbo 接口实现
 * datetime:2015年6月4日下午2:54:04
 * 
 **/
public class RpbServiceImpl implements IRpbService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpbServiceImpl.class);

	@Autowired
	OrderFormNsyn orderFormNsyn;

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentLogInfoService paymentLogInfoService;

	@Autowired
	WeixinPayService weixinPayService;

	@Autowired
	RpbEventService rpbEventService;

	@Autowired
	Sender sender;

	@Autowired
	PaymentItemService paymentItemService;

	@Autowired
	AliPaymentService aliPaymentService;

	@Autowired
	WeixinMchService weixinMchService;

	@Autowired
	WeixinPayUnit weixinPayUnit;

	@Autowired
	AliPayUnit aliPayUnit;

	@Override
	public Map<Integer, String> validateAccount(String paymentIds) {
		if (paymentIds == null)
			return null;
		String[] ids = paymentIds.split(",");
		Map<Integer, String> map = new HashMap<>();
		int value;
		if (ids.length > 0)
			for(String id : ids){
				PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(id);
				if (paymentEntity == null)
					continue;
				value = orderFormNsyn.validateAccount(paymentEntity.getOrderNum(), paymentEntity.getPayChannel());
				if (Constants.ACCOUNT_STATUS.ACCOUNT_STATUS0 != value) {
					map.put(value, paymentEntity.getOrderNum());
					break;
				}
			}
		return map;
	}

	@Override
	public PaymentLogInfo selectByTradeNo(String tradeNo) {
		return paymentLogInfoService.selectByPayTradeNo(tradeNo);

	}

	@Override
	public Map<String, Object> operateWeixinRefund(Integer id) {
		LOGGER.info("微信退款人工操作，id="+id);
		PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(id.toString());
		PaymentEntity oldPaymentEntity = paymentService.selectByOrderNumAndTradeType(paymentEntity.getOrderNum(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2,
				paymentEntity.getPayChannel());
		Map<String, Object> refundResultMap = weixinPayService.weixinRefund(oldPaymentEntity.getPayNo(), paymentEntity.getAmountMoney().doubleValue(), oldPaymentEntity.getAmountMoney().doubleValue(),
				paymentEntity.getPayNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1,oldPaymentEntity.getWeixinMchId());
		if (Constants.RESULT.SUCCESS.equals(refundResultMap.get("result")) || ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(refundResultMap.get("result"))) {
			paymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS2);
			paymentEntity.setFinishTime(DateUtil.getCurrDateTime());
			paymentService.updateByPrimaryKeySelective(paymentEntity);
			refundResultMap.put("success", true);
			String target = Constants.SOURCETYPE.OSM;
			if (Constants.ORDER_TYPE.ORDER_TYPE_1 == oldPaymentEntity.getOrderType()) {
				target = Constants.SOURCETYPE.COUPON;
			}
			List<PaymentItemEntity> itemList = paymentItemService.selectByPaymentId(paymentEntity.getId());
			String orderDetailNum = paymentItemService.getDetailNum(itemList);
			MessageEvent event = rpbEventService.getMessageEvent(paymentEntity.getPayNo(), paymentEntity.getOrderNum(), orderDetailNum, paymentEntity.getPayChannel().toString(), null,
					Constants.SOURCETYPE.RPB, target, PaymentEventType.REFUND);
			sender.convertAndSend(event);
		}else{
			refundResultMap.put("success", false);
		}
		return refundResultMap;
	}

	@Override
	public Map<String, Object> paySuccessNotify(String orderNo, Double totalAmount) {
		LOGGER.info("参数：ordeNo=" + orderNo + ",totalAmount=" + totalAmount);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("errno", "0");
		resultMap.put("errMsg", "支付成功");
//		if (totalAmount == 0) {
//			List<PaymentEntity> list = paymentService.selectByOrderNum(orderNo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0,null);
//			兼容老版本APP0元支付不走签名，在下个强制更新版本后此代码需删除
//			if (list.isEmpty()) {
//				insertZeroOrder(orderNo, totalAmount);
//				LOGGER.info("老版本0元商品购买,增加0元购买记录,orderNo-->" + orderNo);
//			}
//			return resultMap;
//		}
		PaymentEntity paymentEntity = paymentService.selectByOrderNumAndTradeType(orderNo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, null, null);
		if (paymentEntity == null) {
			resultMap.put("errno", "100");
			resultMap.put("errMsg", "订单支付记录不存在！");
			return resultMap;
		}

		String orderNums = paymentService.getOrderNumStrsByPayNo(paymentEntity.getPayNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
		String payChannel = PaymentEventType.WEIXIN_PAY;
		String payAccount = null;
		PaymentLogInfo paymentLogInfo = paymentLogInfoService.selectByOutTradeNo(paymentEntity.getPayNo(),null);
		if (paymentEntity.getPayChannel() != null && Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0 == paymentEntity.getPayChannel()) {
			payChannel = PaymentEventType.APP;
			if(paymentLogInfo != null && paymentLogInfo.getEventType() != null && paymentLogInfo.getEventType() == Constants.EVENT_TYPE.EVENT_TYPE1){
				payChannel = PaymentEventType.PAYMENT;
			}
			QueryOrderParamVO queryOrderParamVO = aliPaymentService.queryOrder(null, paymentEntity.getPayNo());
			payAccount = queryOrderParamVO.getBuyer_email();
		}
		if (Constants.PAYMENT_STATUS.STAUS2 != paymentEntity.getStatus()) {// 异步通知暂未收到
			if (queryOrderPayStatus(null, paymentEntity.getPayNo(), paymentEntity.getPayChannel(),paymentEntity.getWeixinMchId())) {
				LOGGER.info("发送同步支付通知,订单号-->" + orderNo);
				List<PaySuccessResponse> responseList = paymentLogInfoService.paySuccessToMessage(paymentEntity.getPayNo(), payAccount, orderNums, paymentEntity.getOrderType(), payChannel);
				if (!responseList.isEmpty()) {
					resultMap = responseList.get(0).getResult();
				}else{
					resultMap.put("errno", "10");
					resultMap.put("errMsg", "订单系统处理异常");
				}
			}
		} else {
			List<PaySuccessResponse> responseList = paymentLogInfoService.paySuccessToMessage(paymentEntity.getPayNo(), payAccount, orderNums, paymentEntity.getOrderType(), payChannel);
			if (!responseList.isEmpty())
				resultMap = responseList.get(0).getResult();
		}
		LOGGER.info("订单系统订单处理结果" + resultMap.toString());
		return resultMap;
	}

	/**
	 * Description:增加0元支付（此版本临时增加此方法，原因是前端APP在优惠券0元购买是未请求获取支付签名，导致系统无0元支付记录，故在支付成功通知时增加交易记录）
	 * @param orderNo String
	 * @param totalAmount Double
	 * Author: 柯军
	 * datetime:2015年7月30日下午5:23:52
	 **/
//	private void insertZeroOrder(String orderNo, Double totalAmount) {
//		MessageEvent event = new MessageEvent();
//		Map<String, Object> bodyMap = new HashMap<String, Object>();
//		// TODO
//		// 此处orderNo不加双引号转义，JSOBObject.formObject逗比方法会把orderNo当做int处理，丢弃首位的0
//		bodyMap.put("orderNum", "\"" + orderNo + "\"");
//		bodyMap.put("orderType", Constants.ORDER_TYPE.ORDER_TYPE_1);
//		bodyMap.put("totalPrice", totalAmount);
//		event.setBody(bodyMap);
//		event.setTimestamp(DateUtil.getCurrDateTime().getTime());
//		event.setType(PaymentEventType.PAYMENT);
//		PaymentEntityVO paymentEntityVO = paymentService.insertOrderMessage(event);
//		PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
//		paymentLogInfo.setOutTradeNo(paymentEntityVO.getPayNo());
//		paymentLogInfo.setNotifyTime(DateUtil.getCurrDateTime());
//		paymentLogInfo.setResult("success");
//		paymentLogInfo.setTradeMode("1");
//		paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
//		paymentLogInfo.setTotal_fee(0.00);
//		paymentLogInfoService.insertGetId(paymentLogInfo);
//
//		paymentLogInfoService.paySuccessToMessage(paymentEntityVO.getPayNo(), null, orderNo, Constants.ORDER_TYPE.ORDER_TYPE_1, paymentEntityVO.getPayChannel().toString());
//	}

	/**
	 * Description: 查询订单在第三方交易系统中状态
	 * @param tradeNo String
	 * @param payNo String
	 * @param payChannel Integer
	 * @param weixinMchId Integer
	 * Author: 柯军
	 * datetime:2015年8月11日下午4:17:19
	 **/
	@Override
	public boolean queryOrderPayStatus(String tradeNo, String payNo, Integer payChannel,Integer weixinMchId) {
		if (Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0 == payChannel) {
			QueryOrderParamVO queryOrderParamVO = aliPaymentService.queryOrder(tradeNo, payNo);
			if (queryOrderParamVO != null && ("TRADE_SUCCESS".equals(queryOrderParamVO.getTrade_status()) || "TRADE_FINISHED".equals(queryOrderParamVO.getTrade_status()))) {
				return true;
			}
			LOGGER.info("支付宝订单状态-->" + queryOrderParamVO);
		} else if (Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL1 == payChannel) {
			WeixinQueryOrderParamVO weixinQueryOrderParamVO = weixinPayService.queryOrder(tradeNo, payNo,weixinMchId);
			if (weixinQueryOrderParamVO != null && "SUCCESS".equals(weixinQueryOrderParamVO.getResult_code())
					&& ("SUCCESS".equals(weixinQueryOrderParamVO.getTrade_state()) || "REFUND".equals(weixinQueryOrderParamVO.getTrade_state()))) {
				return true;
			}
			LOGGER.info("微信订单支付未成功-->" + weixinQueryOrderParamVO);
		} else {
			LOGGER.info("未找到对应付款方式-->payChannel=" + payChannel + ",tradeNo=" + tradeNo + ",payNo=" + payNo);
		}
		return false;
	}


	@Override
	public QueryOrderParamVO queryOrder(Map<String, Object> map) {
		String tradeNo = (map.get("tradeNo") != null) ? map.get("tradeNo").toString() : null;
		String payNo = (map.get("payNo") != null) ? map.get("payNo").toString() : null;
		return aliPaymentService.queryOrder(tradeNo, payNo);
	}

	@Override
	public List<PayAccountUseTotal> selectPayAccountUseTotal(Map<String, Object> map) {
		return paymentLogInfoService.selectPayAccountUseTotal(map);
	}

	@Override
	public Map<String, Object> getPaySign(MessageEvent event) {
		JSONObject jsonObject = JSONObject.fromObject(event);
		LOGGER.info("支付签名请求，签名参数-->" + jsonObject.toString());
		MessageEvent event2 = rpbEventService.messageToMessageEvent(jsonObject.toString());
		Map<String, Object> resultMap = paymentService.getSendMessage(event2);
		LOGGER.info("返回签名结果-->" + resultMap.toString());
		return resultMap;
	}

	@Override
	public PaymentEntity selectByOrderNumAndTradeType(String orderNum, Integer tradeType, Integer status, Integer payChannel) {
		return paymentService.selectByOrderNumAndTradeType(orderNum, tradeType, status, payChannel);
	}

	@Override
	public Map<String, Object> weixinRefundRejected(Integer paymentId, Integer refundRejected) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", refundRejected == Constants.REFUND_REJECTED.REFUND_REJECTED0 ? "同意退款操作成功" : "拒绝退款操作成功");
		try {
			paymentService.updateRefundRejected(paymentId, refundRejected);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", refundRejected == Constants.REFUND_REJECTED.REFUND_REJECTED0 ? "同意退款操作失败" : "拒绝退款操作失败");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> validatePayHtml(String[] ids, Integer operateType) {
		Map<String, Object> map = new HashMap<>();
		List<PaymentEntity> list = paymentService.valiadteStatus(ids, Constants.PAYMENT_STATUS.STAUS2);
		if (!list.isEmpty()) {
			map.put("success", false);
			if (PayEnum.DRAW_APPLY_ONE.getCode().equals(operateType) || PayEnum.EXCE_PAY_ONE.getCode().equals(operateType) || PayEnum.STATEMENT_ONE.getCode().equals(operateType))
				map.put("message", "该条记录已完成支付,无法再次支付!请刷新页面后再次操作!");
			else if (PayEnum.DRAW_APPLY_MORE.getCode().equals(operateType) || PayEnum.EXCE_PAY_MORE.getCode().equals(operateType))
				map.put("message", "批量列表中存在已完成支付的记录,无法再次支付!请刷新页面后再次操作!");
			else if (PayEnum.TRADE_REFUND_ONE.getCode().equals(operateType))
				map.put("message", "该条退款记录已完成支付,无法再次支付!请刷新页面后再次操作!");
			else if (PayEnum.TRADE_REFUND_MORE.getCode().equals(operateType))
				map.put("message", "批量列表中存在已完成退款的记录,无法再次退款!请刷新页面后再次操作!");
			else if (PayEnum.STATEMENT_MORE.getCode().equals(operateType))
				map.put("message", "批量列表中存在已完成支付的记录,无法再次支付!请刷新页面后再次操作!");
			else
				map.put("message", "未知错误！");
			return map;
		}
		map.put("success", true);
		map.put("message", "验证通过");
		return map;
	}
	@Override
	public Map<String, Object> generatePayment(PaymentParamVO paymentParamVO) {
		Map<String,Object> map = new HashMap<>();
		try {
//			PaymentEntity oldPaymentEntity = paymentService.validateOrderNumExist(paymentParamVO.getOperateNo(), paymentParamVO.getPayChannel(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE7);
//			if (oldPaymentEntity != null && StringUtils.isNotEmpty(oldPaymentEntity.getPayNo())) {
//				LOGGER.info("单号已存在，返回历史付款单号" + oldPaymentEntity.getPayNo());
//			}
			PaymentEntity paymentEntity = new PaymentEntity();
			if (PaymentEventType.STATEMENT_PAY.equals(paymentParamVO.getOperateType())) {// 对账单付款
				LOGGER.info("生成对账单付款记录，对账单号：" + paymentParamVO.getOperateNo());
				paymentEntity.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE7);
				paymentEntity.setPayNo(paymentService.getPayNo());
				paymentEntity.setOrderNum(paymentParamVO.getOperateNo());
			} else {//无该类型的支付申请
				LOGGER.info("无该类型的支付申请，请验证申请类型是否正确！");
				map.put("success",false);
				map.put("message", "未知支付申请类型");
				return map;
			}
			if (paymentParamVO.getPayTotal() != null)
				paymentEntity.setAmountMoney(BigDecimal.valueOf(paymentParamVO.getPayTotal()));
			paymentEntity.setStatus(Constants.PAYMENT_STATUS.STAUS0);
			paymentEntity.setCreateTime(paymentParamVO.getCreateAt());
			paymentEntity.setPayChannel(paymentParamVO.getPayChannel());
			paymentEntity.setOutAccount(paymentParamVO.getPayAccount());
			paymentEntity.setPayName(paymentParamVO.getPayName());
			paymentEntity.setDrawUserId(paymentParamVO.getUserId());
			paymentService.insert(paymentEntity);
			map.put("success", true);
			map.put("message", "申请成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "申请异常");
			e.printStackTrace();
		}
	
		return map;
	}

	@Override
	public void updatePaymentStatus(String[] ids, Integer status, Integer tradeType) {
		if(ids != null  && Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE7 == tradeType){
			Map<String,Object> map = new HashMap<>();
			map.put("status", status);
			paymentService.updateByIds(ids, map);
		}
	}

	@Override
	public Map<String, Object> exceCancelPay(Integer paymentId, Integer refundRejected) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "取消异常付款操作成功");
		try {
			paymentService.updateRefundRejected(paymentId, refundRejected);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "取消异常付款操作失败");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public RefundStatusVO getRefundStatus(RefundQueryParamVO refundQueryParamVO) {
		LOGGER.info("退款详情接口，refundQueryParamVO={}", refundQueryParamVO.toString());
		RefundStatusVO refundStatusVO = new RefundStatusVO();
		PaymentEntity paymentEntity = paymentService.selectByPayNoAndPayChannelAndTradeType(refundQueryParamVO.getRefundNo(), null, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1, null);
		if (paymentEntity == null)
			throw new TradeException("该退款单不存在，refundNo={}", refundQueryParamVO.getRefundNo());
		int totalDay;
		if(paymentEntity.getPayChannel() == null){//0元单退款
			refundStatusVO.setIsRongyiProcess(true);
			refundStatusVO.setRongyiDate(paymentEntity.getFinishTime());
			refundStatusVO.setPayPlatformDate(paymentEntity.getFinishTime());
			refundStatusVO.setIsPayPlatformProcess(true);
			refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr());
			return refundStatusVO;
		}
		if (paymentEntity.getStatus() != Constants.PAYMENT_STATUS.STAUS2) {//容易网未操作
			totalDay = refundQueryParamVO.getRongyiPayDay();
			refundStatusVO.setRongyiDate(DateUtil.addWorkDay(paymentEntity.getCreateTime(), totalDay));
			totalDay += refundQueryParamVO.getPlatformPayDay();
			refundStatusVO.setPayPlatformDate(DateUtil.addWorkDay(paymentEntity.getCreateTime(), totalDay));
			totalDay += refundQueryParamVO.getRefundedDay();
			refundStatusVO.setRefundDate(DateUtil.addWorkDay(paymentEntity.getCreateTime(), totalDay));
			refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr());
		} else {//容易网已操作
			refundStatusVO.setIsRongyiProcess(true);
			refundStatusVO.setRongyiDate(paymentEntity.getFinishTime());
			String status = "";
			if (Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0 == paymentEntity.getPayChannel()) {//支付宝退款
				AliRefundResultData aliRefundResultData = aliPayUnit.queryRefund(null, paymentEntity.getBatchNo());
				LOGGER.info("支付宝退款查询返回结果，aliRefundResultData={}", aliRefundResultData.toString());
				if ("T".equals(aliRefundResultData.getIsSuccess())) {//查询成功
					PaymentLogInfo paymentLogInfo = paymentLogInfoService.selectByOutTradeNo(paymentEntity.getPayNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1);
					if (paymentLogInfo == null || StringUtils.isBlank(paymentLogInfo.getTrade_no())) {
						throw new AliPayException(ConstantEnum.EXCEPTION_PAYMENT_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_PAYMENT_NOT_EXIST.getValueStr());
					}
					for (AliRefundResultData.ResultDetail resultDetail : aliRefundResultData.getResultDetails()) {
						if (paymentLogInfo.getTrade_no().equals(resultDetail.getTradeNo())) {
							if (validateTradeStatus(resultDetail.getResult())) {
								status = ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr();
							} else {
								status = ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr();
							}
							break;
						}
					}
				} else {
					status = ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr();
				}
				if (ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr().equals(status)) {//退款完成
					refundStatusVO.setIsPayPlatformProcess(true);
					refundStatusVO.setPayPlatformDate(paymentEntity.getFinishTime());
					//比较支付宝已完成时间加预计时间是否大于当前时间
					if (DateUtil.dateDiff(DateUtil.getCurrDateTime(), DateUtil.addWorkDay(paymentEntity.getFinishTime(), refundQueryParamVO.getRefundedDay())) < 0) {
						refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr());
					} else {
						refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr());
						refundStatusVO.setRefundDate(DateUtil.addWorkDay(paymentEntity.getFinishTime(), refundQueryParamVO.getRefundedDay()));
					}
				} else {//退款失败
					refundStatusVO.setIsPayPlatformProcess(false);
					refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr());
				}
			} else if (Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL1 == paymentEntity.getPayChannel()) {//微信退款
				RefundQueryResData refundQueryResData = weixinPayUnit.refundQuery(null, null, refundQueryParamVO.getRefundNo(),null);
				LOGGER.info("微信退款查询返回结果，refundQueryResData={}", refundQueryResData.toString());
				status = refundQueryResData.getRefund_status_0();
				if (ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(status)) {//退款处理中
					totalDay = refundQueryParamVO.getPlatformPayDay();
//					refundStatusVO.setPayPlatformDate(DateUtil.addWorkDay(paymentEntity.getFinishTime(), totalDay));
					refundStatusVO.setPayPlatformDate(paymentEntity.getFinishTime());
					refundStatusVO.setIsPayPlatformProcess(true);
					totalDay += refundQueryParamVO.getRefundedDay();
					refundStatusVO.setRefundDate(DateUtil.addWorkDay(paymentEntity.getFinishTime(), totalDay));
					refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr());
				} else if (ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr().equals(status)) {//退款完成
					refundStatusVO.setIsPayPlatformProcess(true);
					refundStatusVO.setPayPlatformDate(paymentEntity.getFinishTime());
					refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr());
				} else {//退款失败
					refundStatusVO.setIsPayPlatformProcess(false);
					refundStatusVO.setRefundReslt(ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr());
				}
			}
		}
		return refundStatusVO;
	}

	private boolean validateTradeStatus(String tradeStatus) {
		return "SUCCESS".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus) || "TRADE_HAS_CLOSED".equals(tradeStatus);
	}

	@Override
	public WeixinMch selectByParam(String publicCode, String mallId) {
		return weixinMchService.selectByPublicCodeAndUserId(publicCode,mallId);
	}

	@Override
	public ResponseData addWeixinMch(WeixinMch weixinMch) {
		LOGGER.info("第三方系统记录微信商户相关信息 weixinMch={}",weixinMch.toString());
		ResponseData responseData;
		try{
			WeixinMch oldWeixinMch = weixinMchService.selectByMchIdAndUserId(weixinMch.getMchId(),weixinMch.getUserId());
			if(weixinMch.getIsRongyiPay() == null){
				weixinMch.setIsRongyiPay(ConstantEnum.WEIXIN_IS_RONGYI_PAY_0.getCodeByte());
			}
			if(oldWeixinMch == null) {
				weixinMchService.insert(weixinMch);
			}else{
				weixinMch.setId(oldWeixinMch.getId());
				weixinMchService.update(weixinMch);
			}
			responseData = ResponseData.success();
		}catch (Exception e){
			e.printStackTrace();
			responseData = ResponseData.failure();
		}
		return responseData;
	}
}

