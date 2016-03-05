package com.rongyi.rpb.service.impl;


import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.common.util.JsonUtil;
import com.rongyi.core.constant.PayEnum;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.domain.PaymentOrderOp;
import com.rongyi.rpb.common.pay.ali.sign.AlipayConfig;
import com.rongyi.rpb.common.pay.ali.util.AlipaySubmit;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.ConstantUtil;
import com.rongyi.rpb.constants.Constants;
import com.rongyi.rpb.nsynchronous.OrderFormNsyn;
import com.rongyi.rpb.service.PCWebPageAlipayService;
import com.rongyi.rpb.service.PaymentLogInfoService;
import com.rongyi.rpb.service.PaymentOrderOpService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rss.mallshop.user.ROAUserService;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 柯军
 * Description: PC端退付款实现
 * datetime:2015年4月23日上午10:04:32
 * 
 **/
@Service
public class PCWebPageAlipayServiceImpl extends BaseServiceImpl implements PCWebPageAlipayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PCWebPageAlipayServiceImpl.class);

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentLogInfoService paymentLogInfoService;

	@Autowired
	ROAUserService rOAUserService;

	@Autowired
	OrderFormNsyn orderFormNsyn;

	@Autowired
	PaymentOrderOpService paymentOrderOpService;


	@Override
	public Map<String, Object> getOnePayInfo(String payNo, String totalFee, String buyerEmail, String buyerName, String desc) {
		Map<String, Object> map = new HashMap<>();
		// 把请求参数打包成数组
		String detail_data = payNo + "^" + buyerEmail + "^" + buyerName + "^" + totalFee + "^" + desc;
		Map<String, String> sParaTempToken = new HashMap<>();
		sParaTempToken.put("service", ConstantUtil.PCZhiFuBaoWebPage.AUTHORIZATION_SERVICE);
		sParaTempToken.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
		sParaTempToken.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
		sParaTempToken.put("sign_type", ConstantUtil.PayZhiFuBao.SIGNTYPE);
		sParaTempToken.put("notify_url", ConstantUtil.PCZhiFuBaoWebPage.NOTIFY_URL_ZHIFUBAO_PC_WEB);
		sParaTempToken.put("account_name", ConstantUtil.PayZhiFuBao.ACCOUNT_FULL_NAME);
		sParaTempToken.put("detail_data", detail_data);
		sParaTempToken.put("batch_no", getBatchNo(payNo));
		sParaTempToken.put("batch_num", "1");
		sParaTempToken.put("batch_fee", totalFee);
		sParaTempToken.put("pay_date", DateUtil.getCurrentDateYYMMDD());
		sParaTempToken.put("email", ConstantUtil.PayZhiFuBao.SELLER_ID);
		try {
			// 建立请求
			String sHtmlTextToken = AlipaySubmit.buildRequest(sParaTempToken, "get", "确认");
			// URLDECODE返回的信息
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
			map.put("sHtmlText", sHtmlTextToken);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getBatchPayBuyerMessage(String[] idArray, String desc) {
		List<Map<String, Object>> buyerList = new ArrayList<>();
		for (String id : idArray) {
			Map<String, Object> buyerMap = new HashMap<>();
			PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(id);
			buyerMap.put("payNo", paymentEntity.getPayNo());
			buyerMap.put("total_fee", paymentEntity.getAmountMoney().toString());
			buyerMap.put("desc", desc);
			buyerMap.put("buyer_email", paymentEntity.getOutAccount());
			buyerMap.put("buyer_name", paymentEntity.getPayName());
			buyerList.add(buyerMap);
		}
		return buyerList;
	}

	@Override
	public Map<String, Object> getBatchPayInfo(List<Map<String, Object>> buyerList) {
		Map<String, Object> map = new HashMap<>();
		StringBuilder detailData = new StringBuilder();
		// 把请求参数打包成数组
		Double batch_fee = 0.0;
		for (int i = 0; i < buyerList.size(); i++) {
			detailData.append(buyerList.get(i).get("payNo"));
			detailData.append("^");
			detailData.append(buyerList.get(i).get("buyer_email"));
			detailData.append("^");
			detailData.append(buyerList.get(i).get("buyer_name"));
			detailData.append("^");
			detailData.append(buyerList.get(i).get("total_fee"));
			detailData.append("^");
			detailData.append("打款到商家");
			if (i < (buyerList.size() - 1))
				detailData.append("|");
			batch_fee += Double.valueOf(buyerList.get(i).get("total_fee").toString());
		}
		Map<String, String> sParaTempToken = new HashMap<>();
		sParaTempToken.put("service", ConstantUtil.PCZhiFuBaoWebPage.AUTHORIZATION_SERVICE);
		sParaTempToken.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
		sParaTempToken.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
		sParaTempToken.put("sign_type", ConstantUtil.PayZhiFuBao.SIGNTYPE);
		sParaTempToken.put("notify_url", ConstantUtil.PCZhiFuBaoWebPage.NOTIFY_URL_ZHIFUBAO_PC_WEB);
		sParaTempToken.put("account_name", ConstantUtil.PayZhiFuBao.ACCOUNT_FULL_NAME);
		sParaTempToken.put("detail_data", detailData.toString());
		sParaTempToken.put("batch_no", getBatchNo(null));
		sParaTempToken.put("batch_num", Integer.toString(buyerList.size()));
		sParaTempToken.put("batch_fee", batch_fee.toString());
		sParaTempToken.put("pay_date", DateUtil.getCurrentDateYYMMDD());
		sParaTempToken.put("email", ConstantUtil.PayZhiFuBao.SELLER_ID);

		try {
			// 建立请求
			String sHtmlTextToken = AlipaySubmit.buildRequest(sParaTempToken, "get", "确认");
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
			map.put("sHtmlText", sHtmlTextToken);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> getRefunInfo(PaymentEntity paymentEntity, String batch_num, String price, String tradeNo, String desc) {
		Map<String, Object> map = new HashMap<>();
		String batchNo = getBatchNo(paymentEntity.getPayNo());
		try {
			if (paymentEntity.getBatchNo() != null) {
				List<PaymentEntity> list = paymentService.selectByBatchNoAndStatus(paymentEntity.getBatchNo(), Constants.PAYMENT_STATUS.STAUS2);
				if (list.isEmpty() && paymentEntity.getBatchNo().contains(DateUtil.getCurrentDateYYMMDD())){//检验批量单号是否是当天生成使用
					batchNo = paymentEntity.getBatchNo();
				}
			}
			LOGGER.info("批量单号-->" + batchNo);
			if (paymentEntity.getBatchNo() == null || !batchNo.equals(paymentEntity.getBatchNo())) {
				paymentEntity.setBatchNo(batchNo);
				paymentService.updateByPrimaryKeySelective(paymentEntity);
			}
			String detailData = tradeNo + "^" + price + "^" + desc;
			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<>();
			sParaTemp.put("service", ConstantUtil.PCRefundWebPage.AUTHORIZATION_SERVICE);
			sParaTemp.put("partner", AlipayConfig.partner);
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("notify_url", ConstantUtil.PCRefundWebPage.NOTIFY_URL_ZHIFUBAO_PC_WEB);
			sParaTemp.put("seller_email", ConstantUtil.PayZhiFuBao.SELLER_ID);
			sParaTemp.put("refund_date", DateUtil.dateToString(DateUtil.getCurrDateTime(), "yyyy-MM-dd hh:mm:ss"));
			sParaTemp.put("batch_no", batchNo);
			sParaTemp.put("batch_num", batch_num);
			sParaTemp.put("detail_data", detailData);
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			map.put("sHtmlText", sHtmlText);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getBatchRefundBuyerMessage(String[] idArray, String desc) {
		List<Map<String, Object>> refundList = new ArrayList<>();
		String batchNo = getBatchNo(null);
		for (String id : idArray) {// 验证批量单号是否已存在
			PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(id);
			if (paymentEntity.getBatchNo() != null) {
				List<PaymentEntity> list = paymentService.selectByBatchNoAndStatus(paymentEntity.getBatchNo(), Constants.PAYMENT_STATUS.STAUS2);
				if (list.isEmpty() && paymentEntity.getBatchNo().contains(DateUtil.getCurrentDateYYMMDD())) {//验证批量单号是否是今天生成
					batchNo = paymentEntity.getBatchNo();
					break;
				}
			}
		}
		LOGGER.info("batchNo=" + batchNo);
		for (String id : idArray) {
			Map<String, Object> buyerMap = new HashMap<>();
			PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(id);
			if (paymentEntity.getBatchNo() == null || !batchNo.equals(paymentEntity.getBatchNo())) {
				paymentEntity.setBatchNo(batchNo);
				paymentService.updateByPrimaryKeySelective(paymentEntity);
			}
			PaymentEntity historyPaymentEntity;
			if (Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1 == paymentEntity.getTradeType())// 正常支付退款记录
				historyPaymentEntity = paymentService.selectByOrderNumAndTradeType(paymentEntity.getOrderNum(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2,
						paymentEntity.getPayChannel());
			else// 重复支付退款记录
				historyPaymentEntity = paymentService.selectByOrderNumAndTradeType(paymentEntity.getOrderNum(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE5, Constants.PAYMENT_STATUS.STAUS2,
						paymentEntity.getPayChannel());
			if (historyPaymentEntity != null) {
				PaymentLogInfo paymentLogInfo = paymentLogInfoService.selectByOutTradeNo(historyPaymentEntity.getPayNo(),historyPaymentEntity.getTradeType());
				if (paymentLogInfo == null)
					throw new RuntimeException("此" + paymentEntity.getPayNo() + "付款单号不存在已付款记录");
				buyerMap.put("tradeNo", paymentLogInfo.getTrade_no());
				buyerMap.put("payNo", paymentLogInfo.getOutTradeNo());
				buyerMap.put("totalFee", paymentEntity.getAmountMoney().toString());
				buyerMap.put("desc", desc);
				buyerMap.put("batchNo", batchNo);
				refundList.add(buyerMap);
			}

		}
		return refundList;
	}

	@Override
	public Map<String, Object> getBatchRefunInfo(List<Map<String, Object>> refundList) {
		Map<String, Object> map = new HashMap<>();
		String desc = "协商退款";
		StringBuilder detailData = new StringBuilder();
		for (int i = 0; i < refundList.size(); i++) {
			detailData.append(refundList.get(i).get("tradeNo"));
			detailData.append("^");
			detailData.append(refundList.get(i).get("totalFee"));
			detailData.append("^");
			detailData.append(desc);
			if (i < (refundList.size() - 1))
				detailData.append("#");
		}
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<>();
		sParaTemp.put("service", ConstantUtil.PCRefundWebPage.AUTHORIZATION_SERVICE);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", ConstantUtil.PCRefundWebPage.NOTIFY_URL_ZHIFUBAO_PC_WEB);
		sParaTemp.put("seller_email", ConstantUtil.PayZhiFuBao.SELLER_ID);
		sParaTemp.put("refund_date", DateUtil.dateToString(DateUtil.getCurrDateTime(), "yyyy-MM-dd hh:mm:ss"));
		sParaTemp.put("batch_no", refundList.get(0).get("batchNo").toString());
		sParaTemp.put("batch_num", Integer.toString(refundList.size()));
		sParaTemp.put("detail_data", detailData.toString());
		try {
			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			map.put("sHtmlText", sHtmlText);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Exception", e.getMessage());
		}
		return map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHtmlMap(MessageEvent event) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> messageMap;
		try {
			messageMap = JsonUtil.getMapFromJson(event.getBody().toString());
			String[] idArray = messageMap.get("paymentId").toString().split(",");
			Integer operateType = Integer.valueOf(messageMap.get("operateType").toString());
			if (PayEnum.DRAW_APPLY_ONE.getCode().equals(operateType) || PayEnum.EXCE_PAY_ONE.getCode().equals(operateType) || PayEnum.STATEMENT_ONE.getCode().equals(operateType)) {
				LOGGER.info("单条支付");
				PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(messageMap.get("paymentId").toString());
				map = getOnePayInfo(paymentEntity.getPayNo(), paymentEntity.getAmountMoney().toString(), paymentEntity.getOutAccount(), paymentEntity.getPayName(), messageMap.get("desc").toString());
			} else if (PayEnum.DRAW_APPLY_MORE.getCode().equals(operateType) || PayEnum.EXCE_PAY_MORE.getCode().equals(operateType) || PayEnum.STATEMENT_MORE.getCode().equals(operateType)) {
				LOGGER.info("批量支付");

				map = getBatchPayInfo(getBatchPayBuyerMessage(idArray, messageMap.get("desc").toString()));
			} else if (PayEnum.TRADE_REFUND_ONE.getCode().equals(operateType)) {
				LOGGER.info("单条退款");
				PaymentEntity paymentEntity = paymentService.selectByPrimaryKey(messageMap.get("paymentId").toString());
				PaymentEntity hisPayEntity;
				if (Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE1 == paymentEntity.getTradeType())// 正常付款记录退款
					hisPayEntity = paymentService.selectByOrderNumAndTradeType(paymentEntity.getOrderNum(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE0, Constants.PAYMENT_STATUS.STAUS2,
							paymentEntity.getPayChannel());// 根据退款单记录中的订单号找到对应的历史付款单记录（用来查找付款交易流水号）
				else// 重复支付退款记录
					hisPayEntity = paymentService.selectByOrderNumAndTradeType(paymentEntity.getOrderNum(), Constants.PAYMENT_TRADE_TYPE.TRADE_TYPE5, Constants.PAYMENT_STATUS.STAUS2,
							paymentEntity.getPayChannel());// 根据退款单记录中的订单号找到对应的历史付款单记录（用来查找付款交易流水号）
				PaymentLogInfo paymentLogInfo = paymentLogInfoService.selectByOutTradeNo(hisPayEntity.getPayNo(),hisPayEntity.getTradeType());
				map = getRefunInfo(paymentEntity, "1", paymentEntity.getAmountMoney().toString(), paymentLogInfo.getTrade_no(), messageMap.get("desc").toString());
			} else if (PayEnum.TRADE_REFUND_MORE.getCode().equals(operateType)) {
				LOGGER.info("批量退款");
				map = getBatchRefunInfo(getBatchRefundBuyerMessage(idArray, messageMap.get("desc").toString()));
			}
			//记录操作动作
			insertOp(idArray,String.valueOf(messageMap.get("userId")));

		} catch (Exception e) {
			map.put("code", "1");// 1：获取信息失败
			map.put("message", "获取页面html信息失败");
			LOGGER.error("获取支付（退款）html页面失败，请求信息：" + event.getBody().toString());
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public String getBatchNo(String payNo) {
		StringBuilder batchNo = new StringBuilder();
		batchNo.append(DateUtil.getCurrentDateYYYYMMDD());
		if (StringUtils.isEmpty(payNo))
			batchNo.append(DateUtil.getCurrentHMSSSS());
		else
			batchNo.append(payNo);
		return batchNo.toString();
	}

	/**
	 * Description:付款操作记录
	 * @param paymentIds
	 * Author:  柯军
	 **/

	private void insertOp(String[] paymentIds,String userId){
		LOGGER.info("记录操作日志 insertOp paymentIds={},userId={}",paymentIds,userId);
		try{
			for (String paymentId : paymentIds){
				PaymentOrderOp paymentOrderOp =paymentOrderOpService.selectByPaymentId(Integer.valueOf(paymentId));
				if(paymentOrderOp == null){
					paymentOrderOp = new PaymentOrderOp();
					paymentOrderOp.setCreateAt(DateUtil.getCurrDateTime());
					paymentOrderOp.setIsDelete(ConstantEnum.IS_DELETE_0.getCodeByte());
					paymentOrderOp.setPaymentId(Integer.valueOf(paymentId));
				}
				paymentOrderOp.setOpTime(DateUtil.getCurrDateTime());
				paymentOrderOp.setOpUser(userId);
				if(paymentOrderOp.getId() == null){
					paymentOrderOpService.insert(paymentOrderOp);
				}else{
					paymentOrderOpService.update(paymentOrderOp);
				}
			}
		}catch (Exception e){
			LOGGER.error("记录付款操作日志失败，忽略此异常，errorMessage={}",e.getMessage());
		}

	}

}
