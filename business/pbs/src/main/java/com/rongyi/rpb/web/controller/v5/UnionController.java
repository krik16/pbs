/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月3日上午11:38:44
 * @Description: TODO
 *
 **/

package com.rongyi.rpb.web.controller.v5;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.constant.PaymentEventType;
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
import com.rongyi.rpb.web.controller.BaseController;

/**
 * @Author: 柯军
 * @Description: 银联contorller
 * @datetime:2015年7月3日上午11:38:44
 * 
 **/
@Controller
@RequestMapping(value = "/v5/union")
public class UnionController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(UnionController.class);

	@Autowired
	UnionCouponService unionCouponService;

	@Autowired
	PaymentLogInfoService paymentLogInfoService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	UnionpayMarketingService unionpayMarketingService;
	
	@RequestMapping("/index")
	public String index() {
		return "union/002002";
	}

	@RequestMapping("/index2")
	public String index2() {
		return "union/002003";
	}

	/**
	 * @Description: 券码验证
	 * @param request
	 * @param response
	 * @Author: 柯军
	 * @datetime:2015年7月8日下午4:21:37
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping("/validateCoupon")
	public void validateCoupon(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			LOGGER.error(e1);
		}
		Map<String, String[]> paramMap = request.getParameterMap();
		if (!validateMessage(paramMap))
			throw new RuntimeException("交易消息不是银联合法的交易请求消息，消息内容-->" + JSONObject.fromObject(paramMap).toString());
		Map<String, Object> responseMap = unionCouponService.doMessage(paramMap);
		setResponse(response, responseMap);
	}

	/**
	 * @Description: 银联支付成功通知
	 * @param request
	 * @param response
	 * @Author: 柯军
	 * @datetime:2015年7月6日下午7:18:38
	 **/
	@RequestMapping("/successNotify")
	public void successNotity(HttpServletRequest request, HttpServletResponse response) {
		UnionCouponLogVO unionCouponLogVO = getUnionCouponLogEntity(request, ConstantEnum.UNION_MSG_TXN_CODE_TWO.getValueStr());
		PaymentLogInfo paymentLogInfo = new PaymentLogInfo(Constants.REPLAY_FLAG.REPLAY_FLAG4, unionCouponLogVO.getMsgCrrltnId(), null, null, DateUtil.getCurrDateTime(), null, null,
				unionCouponLogVO.getTransCrrltnNo(), unionCouponLogVO.getEncCardNo(), unionCouponLogVO.getPartCardNo(), 0, "1", null, DateUtil.getCurrDateTime(), unionCouponLogVO.getPayNo(), null,
				unionCouponLogVO.getPayAmt(), "success", 0,0);
		paymentLogInfoService.insertPayNotify(paymentLogInfo, Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2, PaymentEventType.UNION_PAY);
		Map<String, Object> responseMap = getSuccessNotifyResponse(unionCouponLogVO);
		setResponse(response, responseMap);
	}

	/**
	 * @Description: 银联支付失败通知
	 * @param request
	 * @param response
	 * @Author: 柯军
	 * @datetime:2015年7月7日上午10:35:27
	 **/
	@RequestMapping("/failureNotify")
	public void failureNotify(HttpServletRequest request, HttpServletResponse response) {
		UnionCouponLogVO unionCouponLogVO = getUnionCouponLogEntity(request, ConstantEnum.UNION_MSG_TXN_CODE_TWO.getValueStr());
		List<PaymentEntity> list = paymentService.selectByPayNoAndTradeType(unionCouponLogVO.getPayNo(), Constants.PAYMENT_STATUS.STAUS2);
		if (list != null && !list.isEmpty()) {
			paymentService.updateListStatusBypayNo(unionCouponLogVO.getPayNo(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS0);// 修改付款单状态
			// 付款事件记录标记删除
			PaymentLogInfo paymentLogInfo = paymentLogInfoService.selectByOutTradeNo(unionCouponLogVO.getPayNo(),null);
			if (paymentLogInfo != null)
				paymentLogInfoService.updateDeleteStatus(paymentLogInfo.getId(), Constants.DELETE_STATUS.DELETE_STATUS1);
			// TODO 发送支付失败事件到订单状态机(待确定)
		}
		Map<String, Object> responseMap = getFailureNotifyResponse(unionCouponLogVO);
		setResponse(response, responseMap);
	}

	/**
	 * @Description: 银联支付退款通知
	 * @param request
	 * @param response
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午1:37:23
	 **/
	@RequestMapping("/refundNotify")
	public void refundNotify(HttpServletRequest request, HttpServletResponse response) {
		UnionCouponLogVO unionCouponLogVO = getUnionCouponLogEntity(request, ConstantEnum.UNION_MSG_TXN_CODE_TWO.getValueStr());
		// TODO处理退款业务，生成退款项以及退款事件。。。通知状态机订单状态更改（待确定）
		getRefundNotifyResponse(unionCouponLogVO);
	}

	/**
	 * @Description: 将接收到的银联支付消息转换为对象
	 * @param request
	 * @param msgTxnCode
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日上午10:55:56
	 **/
	@SuppressWarnings("unchecked")
	private UnionCouponLogVO getUnionCouponLogEntity(HttpServletRequest request, String msgTxnCode) {
		Map<String, String[]> paramMap = request.getParameterMap();
		if (!validateMessage(paramMap))
			throw new RuntimeException("交易消息不是银联合法的交易请求消息，消息内容-->" + JSONObject.fromObject(paramMap).toString());
		UnionCouponLogVO unionCouponLogVO = UnionCouponLogVO.mapToUnCouponLogVO(paramMap);
		UnionCouponLogEntity unionCouponLogEntity = unionCouponService.selectByMsgtxCodeAndTransNo(msgTxnCode, unionCouponLogVO.getTransCrrltnNo());
		if (unionCouponLogEntity == null)
			throw new RuntimeException("交易流水号-->" + unionCouponLogVO.getTransCrrltnNo() + "未找到对应交易代码为-->" + msgTxnCode + "的券码验证记录！");
		unionCouponLogVO.setPayNo(unionCouponLogEntity.getPayNo());
		return unionCouponLogVO;
	}

	/**
	 * @Description: 银联支付成功通知响应消息
	 * @param unionCouponLogVO
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日上午10:29:54
	 **/
	private Map<String, Object> getSuccessNotifyResponse(UnionCouponLogVO unionCouponLogVO) {
		Map<String, Object> map = unionCouponService.reponseUnionCouponHeader(unionCouponLogVO.getMsgCrrltnId(), unionCouponLogVO.getMsgSysSn(), unionCouponLogVO.getMsgTxnCode());
		map.put("orig_amt", unionCouponLogVO.getOrigAmt());// 原始金额
		map.put("discount_amt", unionCouponLogVO.getDiscountAmt());// 折扣金额
		map.put("pay_amt", unionCouponLogVO.getPayAmt());// 支付金额
		map.put("commission", unionCouponLogVO.getOrigAmt() * 0.2);// 佣金
		map.put("serv_chg", unionCouponLogVO.getOrigAmt() * 0.2 * 0.4);// 服务费
		UnionActivityRegister unionActivityRegister = unionpayMarketingService.selectByShopNo(unionCouponLogVO.getShopNo());
		if (unionActivityRegister != null)
			map.put(ConstantEnum.UNION_EVENT_NO.getCodeStr(), unionActivityRegister.getEventNo());
		String parmaPlain = UnionUtil.getParamPlain(map);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		map.put(ConstantEnum.UNION_SIGN.getCodeStr(), sign);
		return map;
	}

	/**
	 * @Description: 银联支付失败通知响应消息
	 * @param unionCouponLogVO
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日上午11:46:02
	 **/
	private Map<String, Object> getFailureNotifyResponse(UnionCouponLogVO unionCouponLogVO) {
		Map<String, Object> map = unionCouponService.reponseUnionCouponHeader(unionCouponLogVO.getMsgCrrltnId(), unionCouponLogVO.getMsgSysSn(), unionCouponLogVO.getMsgTxnCode());
		String parmaPlain = UnionUtil.getParamPlain(map);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		map.put(ConstantEnum.UNION_SIGN.getCodeStr(), sign);
		return map;
	}

	/**
	 * @Description: 银联交易退款通知响应消息
	 * @param unionCouponLogVO
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午3:26:10
	 **/
	private Map<String, Object> getRefundNotifyResponse(UnionCouponLogVO unionCouponLogVO) {
		Map<String, Object> map = unionCouponService.reponseUnionCouponHeader(unionCouponLogVO.getMsgCrrltnId(), unionCouponLogVO.getMsgSysSn(), unionCouponLogVO.getMsgTxnCode());
		// TODO 可增加广告信息
		String parmaPlain = UnionUtil.getParamPlain(map);
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, ConstantEnum.UNION_PRIVATE_KEY.getValueStr());
		map.put(ConstantEnum.UNION_SIGN.getCodeStr(), sign);
		return map;
	}

	/**
	 * @Description: response 结果到银联
	 * @param response
	 * @param responseMap
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午3:23:10
	 **/
	private void setResponse(HttpServletResponse response, Map<String, Object> responseMap) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		JSONObject jsonObject = JSONObject.fromObject(responseMap);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(jsonObject.toString());
			LOGGER.info("银联请求应答数据：" + jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * @Description: 验证银联请求消息的合法性
	 * @param paramMap
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年7月7日下午4:16:00
	 **/
	private boolean validateMessage(Map<String, String[]> paramMap) {
		String sign = paramMap.get("sign")[0];
		Map<String, String[]> copyMap = new HashMap<String, String[]>();
		copyMap.putAll(paramMap);
		copyMap.remove("sign");
		String paramData = UnionUtil.getParamPlain2(copyMap);
		if (!UnionUtil.verify(paramData, sign)) {
			return false;
		}
		return true;
	}

}
