package com.rongyi.rpb.web.controller.v5;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentItemEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.vo.PayNotifyVO;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.service.*;
import com.rongyi.rpb.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuChao 2014年8月26日16:17 支付宝网页支付API。 包含支付宝网页支付：
 *         1.进入收银台之前页面，2.支付宝支付成功回调接口，3.支付宝支付失败接口
 */
@Controller
@RequestMapping(value = "/v5/PCWebPageAlipay")
public class PCWebPageAlipayController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PCWebPageAlipayController.class);

	@Autowired
	private PaymentLogInfoService paymentLogInfoService;

	@Autowired
	RpbEventService rpbEventService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentItemService paymentItemService;

	@Autowired
	Sender sender;

	@Autowired
	WebPageAlipayService webPageAlipayService;
	/**
	 * @Description: 支付宝页面支付，接收APP前台传来订单号进行支付宝网页支付相关操作（测试使用）
	 * @param orderId
	 * @param model
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月26日下午6:24:31
	 **/
	@RequestMapping("/zhifubaoRepay.htm")
	public String zhifubaoRepayTest(@RequestParam String orderId, Model model) {
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setPayNo(orderId);
		paymentEntity.setOrderNum(orderId);
		paymentEntity.setTradeType(0);
		paymentEntity.setOrderType(0);
		paymentService.insert(paymentEntity);
		PaymentEntityVO paymentEntityVO = new PaymentEntityVO();
		BeanUtils.copyProperties(paymentEntity, paymentEntityVO);
		paymentEntityVO.setAmountMoney(new BigDecimal("0.01"));
		paymentEntityVO.setTitle("test");
		paymentEntityVO.setTimeStart("2015-02-01 13:40");
		paymentEntityVO.setTimeExpire("2015-02-01 14:48");
		paymentEntityVO.setPayNo("1231231231231");
		String total_fee = "0.01";
		String itemName = "容易2015-03-25";
		try {
			Map<String, Object> map = webPageAlipayService.getToken(paymentEntityVO);
			model.addAttribute("content", map.get("sHtmlText"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "zhifu";
	}

	/**
	 * 支付宝打款成功通知
	 * 
	 * @author kejun 2015年3月25日
	 * @param model
	 * @param notify_id
	 * @param sign_type
	 * @param sign
	 * @param batch_no
	 * @param pay_user_id
	 * @param pay_user_name
	 * @param pay_account_no
	 * @param success_details
	 * @return
	 */
	@RequestMapping("/pay_notify_url.htm")
	public String notifyPay(Model model, String notify_time, String notify_type, String notify_id, String sign_type, String sign, String batch_no, String pay_user_id, String pay_user_name,
			String pay_account_no, String success_details,String fail_details) {
		PaymentLogInfo result = paymentLogInfoService.selectByNotifyId(notify_id);
		if (result != null)
			return null;
		List<PaymentEntity> allList = new ArrayList<>();
		if(success_details != null){
			LOGGER.info("支付宝打款成功记录,success_details={}", success_details);
			String[] detailList = success_details.split("\\|");
			if (detailList.length > 0) {
				for (String details : detailList) {
					PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
					parsPayDetail(paymentLogInfo, details);
					paymentLogInfo.setNotifyId(notify_id);
					paymentLogInfo.setNotifyType(notify_type);
					paymentLogInfo.setNotifyTime(DateUtil.stringToDate(notify_time));
					paymentLogInfo.setReplayFlag(2);
					paymentLogInfo.setResult("success");
					paymentLogInfo.setSign(sign);
					paymentLogInfo.setTradeMode("1");
					paymentLogInfo.setSignType(sign_type);
					paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
					paymentLogInfo.setBuyer_type(1);
					List<PaymentEntity> paymentList = paymentService.updateListStatusBypayNo(paymentLogInfo.getOutTradeNo(), null, Constants.PAYMENT_STATUS.STAUS2);// 修改打款状态(提现或打款给卖家)
					if (paymentList != null && !paymentList.isEmpty()) {
						allList.add(paymentList.get(0));
						paymentLogInfo.setTradeType(paymentList.get(0).getTradeType());
						if (paymentList.get(0).getTradeType() == Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE3) {
							paymentLogInfo.setEventType(Constants.EVENT_TYPE.EVENT_TYPE8);
						} else if (paymentList.get(0).getTradeType() == Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE7) {
							paymentLogInfo.setEventType(Constants.EVENT_TYPE.EVENT_TYPE9);
						}
					}

					paymentLogInfoService.insertGetId(paymentLogInfo);
				}
			}
		}
		if(fail_details != null){
			LOGGER.info("支付宝打款失败记录,不更新打款状态，fail_details={}", fail_details);

		}
		if (!allList.isEmpty()) {
			paySuccessToMessage(allList, allList.get(0).getTradeType());
		}
		LOGGER.info("支付宝打款异步通知结束");
		return "payManager/notify";
	}

	/**
	 * @Description: 支付成功发送通知消息
	 * @param
	 * @param
	 * @Author: 柯军
	 * @datetime:2015年5月21日上午11:53:11
	 **/
	public void paySuccessToMessage(List<PaymentEntity> list, Integer tradeType) {
		List<PayNotifyVO> payNotifylist = new ArrayList<>();
		Map<String, Object> bodyMap = new HashMap<>();
		for (PaymentEntity paymentEntity : list) {
			PayNotifyVO payNotifyVO = new PayNotifyVO();
			payNotifyVO.setDrawNo(paymentEntity.getOrderNum());
			payNotifyVO.setTradeNo(paymentEntity.getPayNo());
			payNotifylist.add(payNotifyVO);
		}
		MessageEvent event = new MessageEvent();
		event.setSource(Constants.SOURCETYPE.RPB);
		event.setTarget(Constants.SOURCETYPE.TMS);
		event.setTimestamp(DateUtil.getCurrDateTime().getTime());
		event.setBody(bodyMap);
		if (tradeType.equals(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE3)) {// 提现通知
			event.setType(PaymentEventType.PAY_NOTIFY);
			bodyMap.put("drawApplyList", payNotifylist);
		} else if (tradeType.equals(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE7)) {// 结算付款单更新
			event.setType(PaymentEventType.STATEMENT_PAY_NOTIFY);
			bodyMap.put("statementList", payNotifylist);
		}
		if (!bodyMap.isEmpty())
			sender.convertAndSend(event);
	}

	private PaymentLogInfo parsPayDetail(PaymentLogInfo paymentLogInfo, String details) {
		String[] detail = details.split("\\^");
		if (detail.length > 0)
			paymentLogInfo.setOutTradeNo(detail[0]);// 订单号是流水号8位之后的值
		if (detail.length > 1)
			paymentLogInfo.setBuyer_email(detail[1]);
		if (detail.length > 3)
			paymentLogInfo.setTotal_fee(Double.valueOf(detail[3]));
		if (detail.length > 6)
			paymentLogInfo.setTrade_no(detail[6]);
		return paymentLogInfo;
	}

	/**
	 * 支付宝退款成功回调
	 * 
	 * @author kejun 2015年3月25日
	 * @param model
	 * @param notify_time
	 * @param notify_type
	 * @param notify_id
	 * @param sign_type
	 * @param sign
	 * @param batch_no
	 * @param success_num
	 * @param result_details
	 * @return
	 */
	@RequestMapping("/refund_notify_url.htm")
	public String notifyRefund(Model model, String notify_time, String notify_type, String notify_id, String sign_type, String sign, String batch_no, String success_num, String result_details) {
		PaymentLogInfo result = paymentLogInfoService.selectByNotifyId(notify_id);
		if (result != null)
			return "payManager/notify";
		LOGGER.info("返回结果：result_details={},batch_no={}", result_details, batch_no);
		if (!validateTradeStatus(result_details)) {
			LOGGER.info("该订单状态支付宝不允许退款，退款状态未变更，退款事件记录未生成！");
			return "payManager/notify";
		}
		LOGGER.info("支付宝退款成功异步通知开始");
		String[] batchDetail = result_details.split("#");
			try {
				for (int i = 0; i < batchDetail.length; i++) {
					PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
					String[] details = batchDetail[i].split("\\^");
					if (details != null && validateTradeStatus(details[2])) {
						PaymentLogInfo oldPaymentLogInfo = paymentLogInfoService.selectByPayTradeNo(details[0]);
						if (oldPaymentLogInfo != null)
							paymentLogInfo.setBuyer_email(oldPaymentLogInfo.getBuyer_email());
						paymentLogInfo.setTrade_no(details[0]);// 交易流水号
						paymentLogInfo.setTotal_fee(Double.valueOf(details[1]));
						paymentLogInfo.setTransactionId(batch_no);
						paymentLogInfo.setNotifyId(notify_id);
						paymentLogInfo.setNotifyType(notify_type);
						paymentLogInfo.setNotifyTime(DateUtil.stringToDate(notify_time));
						paymentLogInfo.setReplayFlag(2);
						paymentLogInfo.setResult(details[2]);
						paymentLogInfo.setSign(sign);
						paymentLogInfo.setTradeMode("1");
						paymentLogInfo.setSignType(sign_type);
						paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
						paymentLogInfo.setBuyer_type(0);// 买家账号
						paymentLogInfo.setEventType(Constants.EVENT_TYPE.EVENT_TYPE4);
						PaymentEntity refundPaymentEntity = getPaymentByPayTradeNo(paymentLogInfo.getTrade_no(), batch_no);
						if (refundPaymentEntity != null) {
							paymentLogInfo.setTradeType(refundPaymentEntity.getTradeType());
							paymentLogInfo.setOutTradeNo(refundPaymentEntity.getPayNo());
							paymentLogInfoService.insertGetId(paymentLogInfo);
							LOGGER.info("更改退款项状态 0--->2,退款单号={}", refundPaymentEntity.getPayNo());
							paymentService.updateListStatusBypayNo(refundPaymentEntity.getPayNo(), refundPaymentEntity.getTradeType(), Constants.PAYMENT_STATUS.STAUS2);// 修改打款状态
							//正常退款的才会发送退款消息，重复支付的退款无需发送退款通知
							if (refundPaymentEntity.getTradeType() == Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1) {
								refundNotifyMq(refundPaymentEntity);
							}
						} else {
							LOGGER.info("订单不存在，退款状态更新失败,tradeNo={},batchNo={}", paymentLogInfo.getTrade_no(), batch_no);
						}
					} else{//退款失败处理，更新付款状态为待付款
						LOGGER.info("支付宝退款失败，不更新退款状态,result_details={}", result_details);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		LOGGER.info("支付宝退款成功异步通知结束");
		return "payManager/notify";
	}

	/**
	 * @Description: 支付宝退款通知成功后发送退款成功消息到订单系统
	 * @param refundPaymentEntity
	 * @Author: 柯军
	 * @datetime:2015年7月12日上午10:22:20
	 **/
	private void refundNotifyMq(PaymentEntity refundPaymentEntity) {
		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("orderNum", refundPaymentEntity.getOrderNum());
		bodyMap.put("totalPrice", refundPaymentEntity.getAmountMoney());
		bodyMap.put("paymentId", refundPaymentEntity.getPayNo());
		List<PaymentItemEntity> itemList = paymentItemService.selectByPaymentId(refundPaymentEntity.getId());
		bodyMap.put("orderDetailNum", paymentItemService.getDetailNum(itemList));
		MessageEvent event = new MessageEvent();
		if (Constants.ORDER_TYPE.ORDER_TYPE_1 == refundPaymentEntity.getOrderType())// 优惠券订单退款
			event.setTarget(Constants.SOURCETYPE.COUPON);
		else
			// 商品订单
			event.setTarget(Constants.SOURCETYPE.OSM);
		event.setSource(Constants.SOURCETYPE.RPB);
		event.setTimestamp(DateUtil.getCurrDateTime().getTime());
		event.setType(PaymentEventType.REFUND);
		event.setBody(bodyMap);
		sender.convertAndSend(event);
	}

	/**
	 * @Description: 
	 *               退款成功根据交易流水号获得付款记录，根据付款记录中付款单号可获得支付订单号，支付订单号和退款订单号相同，根据订单号获得退款单号
	 * @param tradeNo
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年5月26日下午5:45:59
	 **/
	private PaymentEntity getPaymentByPayTradeNo(String tradeNo, String batchNo) {
		PaymentEntity refundPaymentEntity = null;
		PaymentLogInfo oldPaymentLogInfo = paymentLogInfoService.selectByPayTradeNo(tradeNo);
		List<PaymentEntity> paymentEntityList = paymentService.selectByPayNoAndTradeType(oldPaymentLogInfo.getOutTradeNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
		if (paymentEntityList != null && !paymentEntityList.isEmpty()) {
			refundPaymentEntity = paymentService.selectByOrderNumAndBatchNo(paymentEntityList.get(0).getOrderNum(), batchNo);
		}
		return refundPaymentEntity;
	}

	/**
	 * 支付失败时支付宝端回调接口
	 * 
	 * @return 支付失败页面
	 */
	@RequestMapping("/merchant_url.htm")
	public String merchantUrl() {
		return "zhifuFail";
	}

	/**
	 * @Description: 验证支付宝订单状态
	 * @param tradeResult
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年4月24日上午10:47:02
	 **/
	private boolean validateTradeStatus(String tradeResult) {
		return tradeResult.contains("TRADE_SUCCESS") || tradeResult.contains("SUCCESS") || tradeResult.contains("success");
	}
}
