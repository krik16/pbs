package com.rongyi.rpb.web.controller.v5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.constant.PaymentEventType;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.rpb.common.pay.ali.util.AliAppPayNotify;
import com.rongyi.rpb.common.pay.ali.util.AlipayNotify;
import com.rongyi.rpb.common.pay.weixin.util.XMLParser;
import com.rongyi.rpb.common.pay.weixin.util.XMLUtil;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.mq.Sender;
import com.rongyi.rpb.service.PaymentLogInfoService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.service.WebPageAlipayService;
import com.rongyi.rpb.service.WeixinPayService;
import com.rongyi.rpb.web.controller.BaseController;
import com.rongyi.rss.rpb.IRpbService;
import com.rongyi.rss.rpb.OrderNoGenService;

/**
 * @author kejun 包含支付宝网页支付： 1.进入收银台之前页面，2.支付宝支付成功回调接口，3.支付宝支付失败接口
 */
@Controller
@RequestMapping(value = "/v5/WebPageAlipay")
public class WebPageAlipayController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WebPageAlipayController.class);


	@Autowired
	private PaymentLogInfoService paymentLogInfoService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	Sender sender;

	@Autowired
	IRpbService rpbService;

	@Autowired
	WeixinPayService weixinPayService;

	@Autowired
	OrderNoGenService orderNoGenService;

	/**
	 * @Description: 支付宝网页支付回调接口
	 * @param request
	 * @param model
	 * @param sign
	 * @param result
	 * @param out_trade_no
	 * @param trade_no
	 * @param request_token
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月26日下午6:24:16
	 **/
	@RequestMapping("/call_back.htm")
	public String callBack(HttpServletRequest request, Model model, String sign, String result, String out_trade_no, String trade_no, String request_token) {
		LOGGER.info("支付宝手机网页同步通知开始,trade_no={},out_trade_no={},result={}",trade_no ,out_trade_no ,result);
		try {
			Map<String, String> verifyMap = beforeVerify(request);
			if (!AlipayNotify.verifyReturn(verifyMap)) {
				LOGGER.info("支付宝网页支付同步通知-->支付宝验证签名不通过，返回消息不是支付宝发出的合法消息!");
				return "zhifuFail";
			}
			boolean bool = rpbService.queryOrderPayStatus(trade_no, out_trade_no, Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0,null);
			if (!bool) {
				LOGGER.info("支付宝订单查询结果是未支付状态");
				return "zhifuFail";
			}
			String orderNums = paymentService.getOrderNumStrsByPayNo(out_trade_no, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
			PaymentEntity paymentEntity = paymentService.selectByPayNoAndPayChannelAndTradeType(out_trade_no, Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, null);
			if (paymentEntity != null && paymentEntity.getStatus() != Constants.PAYMENT_STATUS.STAUS2) {
				paymentService.updateListStatus(out_trade_no, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2, Constants.PAYMENT_PAY_CHANNEL.PAY_CHANNEL0);// 修改付款单状态
				paymentLogInfoService.paySuccessToMessage(out_trade_no, null, orderNums, paymentEntity.getOrderType(), PaymentEventType.PAYMENT);
			}
			LOGGER.info("支付宝手机网页同步通知结束 ");
			return "zhifuSuccess";
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "zhifuFail";
	}

	/**
	 * @Description: 手机网页支付成功通知
	 * @param request
	 * @param model
	 * @param notify_data
	 * @param sign
	 * @Author: 柯军
	 * @datetime:2015年6月26日下午6:24:05
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("/notify_url.htm")
	public void notify(HttpServletRequest request, Model model, String notify_data, String sign) {
		Map<String, String> verifyMap = beforeVerify(request);
		if (!AlipayNotify.verifyNotify(verifyMap)) {
			LOGGER.info("支付宝网页支付异步通知-->支付宝验证签名不通过，返回消息不是支付宝发出的合法消息!");
			return;
		}
		PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
		try {
			Map<String, Object> map = XMLUtil.doXMLParse(notify_data);
			if (map != null && map.get("out_trade_no") != null) {
				PaymentLogInfo result = paymentLogInfoService.selectByOutTradeNo(map.get("out_trade_no").toString(), null);
				if (result != null)
					return;
				if (!validateTradeStatus(map.get("trade_status").toString()))
					return;
				LOGGER.info("支付宝手机网页支付异步通知开始,notify_data={},tradeNo={}", notify_data,map.get("trade_no"));
				PaymentLogInfo tradeNoResult = paymentLogInfoService.selectByPayTradeNo(map.get("trade_no").toString());
				if (tradeNoResult != null) {
					LOGGER.info("支付状态变更通知，退款会收到，trade_no={}",map.get("trade_no").toString());
					return;
				}
				paymentLogInfo.setNotifyId(map.get("notify_id").toString());
				if (map.get("buyer_email") != null)
					paymentLogInfo.setBuyer_email(map.get("buyer_email").toString());
				if (map.get("buyer_id") != null)
					paymentLogInfo.setBuyer_id(map.get("buyer_id").toString());
				if (map.get("notify_time") != null)
					paymentLogInfo.setNotifyTime(DateUtil.stringToDate(map.get("notify_time").toString()));
				if (map.get("notify_type") != null)
					paymentLogInfo.setNotifyType(map.get("notify_type").toString());
				if (map.get("out_trade_no") != null)
					paymentLogInfo.setOutTradeNo(map.get("out_trade_no").toString());
				if (map.get("gmt_payment") != null)
					paymentLogInfo.setTimeEnd(DateUtil.stringToDate(map.get("gmt_payment").toString()));
				if (map.get("total_fee") != null)
					paymentLogInfo.setTotal_fee(Double.valueOf(map.get("total_fee").toString()));
				if (map.get("trade_no") != null)
					paymentLogInfo.setTrade_no(map.get("trade_no").toString());
				paymentLogInfo.setTradeMode("1");
				paymentLogInfo.setReplayFlag(2);
				paymentLogInfo.setRequest_time(DateUtil.getCurrDateTime());
				paymentLogInfo.setBuyer_type(0);// 买家
				paymentLogInfo.setEventType(Constants.EVENT_TYPE.EVENT_TYPE1);
				paymentLogInfo.setResult(map.get("trade_status").toString());
				paymentLogInfo.setTradeType(Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
				paymentLogInfoService.insertPayNotify(paymentLogInfo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2, PaymentEventType.PAYMENT);
				LOGGER.info("支付宝手机网页支付异步通知结束,tradeNo={}", notify_data,map.get("trade_no"));
				model.addAttribute("content", "支付成功");
			}
		} catch (Exception e) {
			LOGGER.error("支付成功变更订单状态出错，notify_data={}",notify_data);
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 手机APP支付通知
	 * @param request
	 * @param model
	 * @param notify_time
	 * @param notify_type
	 * @param notify_id
	 * @param sign_type
	 * @param sign
	 * @param out_trade_no
	 * @param trade_no
	 * @param buyer_id
	 * @param buyer_email
	 * @param total_fee
	 * @param trade_status
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年6月26日下午6:23:55
	 **/
	@RequestMapping("/app/notify_url.htm")
	public String appNotify(HttpServletRequest request, Model model, String notify_time, String notify_type, String notify_id, String sign_type, String sign, String out_trade_no, String trade_no,
			String buyer_id, String buyer_email, String total_fee, String trade_status) {
		PaymentLogInfo result = paymentLogInfoService.selectByNotifyId(notify_id);
		if (result != null)
			return "appwebpage/notify";
		if (!validateTradeStatus(trade_status))
			return "appwebpage/notify";
		PaymentLogInfo tradeNoResult = paymentLogInfoService.selectByPayTradeNo(trade_no);
		if (tradeNoResult != null) {
			LOGGER.info("支付状态变更通知，退款会收到，trade_no={}",trade_no);
			return "appwebpage/notify";
		}
		Map<String, String> verifyMap = beforeVerify(request);
		if (!AliAppPayNotify.verify(verifyMap)) {
			LOGGER.info("支付宝验证签名不通过，返回消息不是支付宝发出的合法消息!");
			return "appwebpage/notify";
		}
		LOGGER.info("支付宝手机APP支付异步通知开始,交易流水号,trade_no={}",trade_no);
		PaymentLogInfo paymentLogInfo = getPaymentLogInfo(trade_no, out_trade_no, notify_id, notify_type, DateUtil.getCurrDateTime(), sign, sign_type, 0, Constants.EVENT_TYPE.EVENT_TYPE0, total_fee, buyer_email, buyer_id,
				Constants.REPLAY_FLAG.REPLAY_FLAG0, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0);
		paymentLogInfoService.insertPayNotify(paymentLogInfo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2, PaymentEventType.APP);
		LOGGER.info("支付宝手机APP支付异步通知结束,交易流水号,trade_no={}",trade_no);
		return "appwebpage/notify";
	}


	/**
	 * @Description: V3版本微信支付异步通知
	 * @param model
	 * @param request
	 * @param response
	 * @Author: 柯军
	 * @datetime:2015年9月2日下午4:48:21
	 **/
	@RequestMapping("/weixin/notify_url.htm")
	public void weixinNotify(Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = parseXml(request);
		if ("SUCCESS".equals(requestMap.get("result_code"))) {
			LOGGER.info("微信支付异步通知开始,tradeNo={}",requestMap.get("transaction_id").toString());
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("return_code", Constants.RESULT.SUCCESS);
			responseMap.put("return_msg", "OK");
			boolean bool = paymentLogInfoService.validateByTradeNoAndPayNo(requestMap.get("transaction_id").toString(), requestMap.get("out_trade_no").toString());
			if (bool) {
				LOGGER.info("微信支付结果已正确处理,应答微信支付结果处理成功tradeNo={}",requestMap.get("transaction_id").toString());
				setResponse(response, responseMap);
				return;
			}
			PaymentLogInfo paymentLogInfo = paymentLogInfoService.getByWeixinNotify(requestMap);
			paymentLogInfoService.insertPayNotify(paymentLogInfo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2, PaymentEventType.WEIXIN_PAY);// 支付通知正常处理
			LOGGER.info("微信支付异步通知结束,tradeNo={}",paymentLogInfo.getTrade_no());
		} else {
			LOGGER.info("支付未成功,通知内容" + requestMap.toString());
		}
	}
	
	/**
	 * @Description: 解析微信异步通知中的xml元素值
	 * @param request
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年8月11日下午3:33:45
	 **/
	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseXml(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			InputStream inputStream = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
			inputStream.close();
		} catch (Exception e) {
			LOGGER.error("解析微信返回结果xml失败");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @Description: response 结果到微信
	 * @param response
	 * @param responseMap
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午3:23:10
	 **/
	private void setResponse(HttpServletResponse response, Map<String, Object> responseMap) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String xml = XMLParser.getXmlFormMap(responseMap, true);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(xml);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * 支付失败时支付宝端回调接口
	 * 
	 * @return 支付失败页面
	 */
	@RequestMapping("/merchant_url.htm")
	public String merchant_url() {
		return "zhifuFail";
	}

	/**
	 * 验证支付宝通知结果之前封装验证所需数据
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> beforeVerify(HttpServletRequest request) {
		Map<String, String> verifyMap = new HashMap<>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			verifyMap.put(name, valueStr);
		}
		return verifyMap;
	}

	private PaymentLogInfo getPaymentLogInfo(String tradeNo, String outTradeNo, String notifyId, String notifyType, Date notifyTime, String sign, String signType, Integer buyerType,
			Integer eventType, String totalFee, String buyerEmail, String buyerId, Integer replayFlag, Integer tradeType) {
		PaymentLogInfo paymentLogInfo = new PaymentLogInfo();
		paymentLogInfo.setTrade_no(tradeNo);// 交易流水号
		paymentLogInfo.setOutTradeNo(outTradeNo);
		paymentLogInfo.setNotifyId(notifyId);
		paymentLogInfo.setNotifyType(notifyType);
		paymentLogInfo.setNotifyTime(notifyTime);
		paymentLogInfo.setReplayFlag(replayFlag);
		paymentLogInfo.setSign(sign);
		paymentLogInfo.setTradeMode("1");
		paymentLogInfo.setSignType(signType);
		paymentLogInfo.setTimeEnd(DateUtil.getCurrDateTime());
		paymentLogInfo.setBuyer_type(buyerType);// 买家账号
		paymentLogInfo.setEventType(eventType);
		paymentLogInfo.setTotal_fee(Double.valueOf(totalFee));
		paymentLogInfo.setResult("success");
		paymentLogInfo.setBuyer_email(buyerEmail);
		paymentLogInfo.setBuyer_id(buyerId);
		paymentLogInfo.setTradeType(tradeType);
		return paymentLogInfo;
	}

	/**
	 * @Description: 验证支付宝订单状态
	 * @param tradeStatus
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年4月24日上午10:47:02
	 **/
	private boolean validateTradeStatus(String tradeStatus) {
		LOGGER.info("支付宝订单状态：" + tradeStatus);
		return "TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus);
	}
}
