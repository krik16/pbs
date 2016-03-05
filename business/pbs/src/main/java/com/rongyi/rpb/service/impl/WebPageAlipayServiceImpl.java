package com.rongyi.rpb.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.rpb.Exception.TradeException;
import com.rongyi.rpb.common.pay.ali.sign.AlipayConfig;
import com.rongyi.rpb.common.pay.ali.util.AlipayNotify;
import com.rongyi.rpb.common.pay.ali.util.AlipaySubmit;
import com.rongyi.rpb.common.pay.ali.util.UtilDate;
import com.rongyi.rpb.constants.ConstantUtil;
import com.rongyi.rpb.service.WebPageAlipayService;
import com.rongyi.rpb.unit.TimeExpireUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**	
 * Author:  柯军
 * Description: 支付宝网页支付实现类
 * datetime:2015年4月23日上午10:05:58
 *
 **/
@Service
public class WebPageAlipayServiceImpl extends BaseServiceImpl implements WebPageAlipayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebPageAlipayServiceImpl.class);
	private static String requestToken;

	@Autowired
	TimeExpireUnit timeExpireUnit;

	@Override
	public Map<String, Object> getToken(PaymentEntityVO paymentEntityVO) {
		LOGGER.info("price="+paymentEntityVO.getAmountMoney());
		Map<String, Object> map = new HashMap<>();
		//支付失效时间
		String itBPay= timeExpireUnit.aliPayTimeExpire(paymentEntityVO.getTimeStart(), paymentEntityVO.getTimeExpire(),paymentEntityVO.getOrderType());

		// 把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<>();
		sParaTempToken.put("service", ConstantUtil.ZhiFuBaoWebPage.AUTHORIZATION_SERVICE);
		sParaTempToken.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
		sParaTempToken.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
		sParaTempToken.put("sec_id", ConstantUtil.ZhiFuBaoWebPage.SIGN_TYPE);
		sParaTempToken.put("format", ConstantUtil.ZhiFuBaoWebPage.FORMAT);
		sParaTempToken.put("v", ConstantUtil.ZhiFuBaoWebPage.VERSION);
		sParaTempToken.put("req_id", UtilDate.getOrderNum());
		sParaTempToken.put("req_data", getRequestDataToken(paymentEntityVO.getPayNo(), paymentEntityVO.getAmountMoney().toString(), paymentEntityVO.getTitle(),itBPay));
		try {
			// 建立请求
			String sHtmlTextToken = AlipaySubmit.buildRequest(ConstantUtil.ZhiFuBaoWebPage.ALIPAY_GATEWAY_NEW, "", "", sParaTempToken);
			if(sHtmlTextToken == null)
				throw  new TradeException("支付宝支付获取支付令牌出错");
			// URLDECODE返回的信息
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);

			// 获取token
			requestToken = AlipaySubmit.getRequestToken(sHtmlTextToken);
			String sHtmlText = getAuthandexecute();
			map.put("request_token", requestToken);
			map.put("sHtmlText", sHtmlText);
			map.put("code", 0);
		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
			map.put("Exception", e.getMessage());
		}
		return map;
	}
	@Override
	public String getAuthandexecute() {
		// 将request_token 拼如request data中
		String reqData = "<auth_and_execute_req><request_token>" + requestToken + "</request_token></auth_and_execute_req>";
		Map<String, String> sParaTemp = new HashMap<>();

		sParaTemp.put("service", ConstantUtil.ZhiFuBaoWebPage.TRADE_SERVICE);
		sParaTemp.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
		sParaTemp.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
		sParaTemp.put("sec_id", ConstantUtil.ZhiFuBaoWebPage.SIGN_TYPE);
		sParaTemp.put("format", ConstantUtil.ZhiFuBaoWebPage.FORMAT);
		sParaTemp.put("v", ConstantUtil.ZhiFuBaoWebPage.VERSION);
		sParaTemp.put("req_data", reqData);
		return AlipaySubmit.buildRequest(ConstantUtil.ZhiFuBaoWebPage.ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
	}

	/**
	 * 构造请求令牌所需要的数据 （获得token所需的工具类）
	 * 
	 * @param orderId String
	 */
	private String getRequestDataToken(String orderId, String totalFee, String itemName,String itBPay) {

		StringBuilder reqDataToken = new StringBuilder();

		reqDataToken.append("<direct_trade_create_req>");

		reqDataToken.append("<notify_url>");
		reqDataToken.append(ConstantUtil.PayNetAdress.NOTIFY_URL_ZHIFUBAO_WEB);
		reqDataToken.append("</notify_url>");

		reqDataToken.append("<call_back_url>");
		reqDataToken.append(ConstantUtil.PayNetAdress.CALL_BACK_URL_WEB);
		reqDataToken.append("</call_back_url>");

		reqDataToken.append("<merchant_url>");
		reqDataToken.append(ConstantUtil.PayNetAdress.MERCHANT_URL_WEB);
		reqDataToken.append("</merchant_url>");

		reqDataToken.append("<seller_account_name>");
		reqDataToken.append(ConstantUtil.PayZhiFuBao.SELLER_ID);
		reqDataToken.append("</seller_account_name>");

		reqDataToken.append("<out_trade_no>");
		reqDataToken.append(orderId);
		reqDataToken.append("</out_trade_no>");

		reqDataToken.append("<subject>");
		reqDataToken.append(itemName);
		reqDataToken.append("</subject>");

		reqDataToken.append("<total_fee>");
		reqDataToken.append(totalFee);
		reqDataToken.append("</total_fee>");

		reqDataToken.append("<it_b_pay>");
		reqDataToken.append(itBPay);//支付超时时间
		reqDataToken.append("</it_b_pay>");


		reqDataToken.append("</direct_trade_create_req>");

		return reqDataToken.toString();
	}

	@Override
	public Boolean verify(Map<String, String> map) {
		Map<String, String> tempMap = AlipaySubmit.buildRequestPara(map);
		return AlipayNotify.verifyReturn(tempMap);
	}
}
