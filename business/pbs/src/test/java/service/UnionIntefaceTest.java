package service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import base.BaseTest;

import com.unionpay.acp.sdk.HttpClient;

public class UnionIntefaceTest extends BaseTest {

	/**
	 * @Description: 券码一次验证
	 * @Author: 柯军
	 * @datetime:2015年7月8日下午2:40:08
	 **/
	@Test
	public void testHttpCouponOneValidate() {
		Map<String, String> submitFromData = getSubmitFromData("002002");
		submitFromData.put("req_serial_no", "20130921155443030785");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("coupon_no", "31731000001587665350");
		submitFromData.put("enc_card_no", "9d224e72968b8a495606eff46fec2016");
		submitFromData.put("part_card_no", "620048******2291");
		submitFromData.put("use_shares", "1");
		submitFromData.put("term_sp_chnl_no", "001");
		submitFromData.put("trans_crrltn_no", "1234567890");
		httpPost(submitFromData, "http://localhost:8282/easy-rpb/v5/union/validateCoupon");
	}
	
	/**	
	 * @Description: 券码二次验证 	
	 * @Author:  柯军
	 * @datetime:2015年7月8日下午2:41:53
	 **/
	@Test
	public void testHttpCouponTwoValidate() {
		Map<String, String> submitFromData = getSubmitFromData("002003");
		submitFromData.put("req_serial_no", "20130921155443030786");
		submitFromData.put("orig_req_serial_no", "20130921155443030785");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("coupon_no", "31731000001587665350");
		submitFromData.put("use_shares", "1");
		submitFromData.put("term_sp_chnl_no", "001");
		submitFromData.put("orig_amt", "100");
		submitFromData.put("pay_mode", "40");
		submitFromData.put("enc_card_no", "9d224e72968b8a495606eff46fec2016");
		submitFromData.put("part_card_no", "620048******2291");
		submitFromData.put("trans_crrltn_no", "1234567890");
		httpPost(submitFromData, "http://localhost:8282/easy-rpb/v5/union/validateCoupon");
	}
	
	/**	
	 * @Description: 支付成功通知 	
	 * @Author:  柯军
	 * @datetime:2015年7月8日下午3:04:06
	 **/
	@Test
	public void testHttpPySuccess() {
		Map<String, String> submitFromData = getSubmitFromData("002100");
		submitFromData.put("req_serial_no", "20130921155443030787");
		submitFromData.put("orig_req_serial_no", "20130921155443030786");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("coupon_no", "31731000001587665350");
		submitFromData.put("use_shares", "1");
		submitFromData.put("term_sp_chnl_no", "001");
		submitFromData.put("orig_amt", "100");
		submitFromData.put("discount_amt", "40");
		submitFromData.put("pay_amt", "60");
		submitFromData.put("pay_mode", "2");
		submitFromData.put("enc_card_no", "9d224e72968b8a495606eff46fec2016");
		submitFromData.put("part_card_no", "620048******2291");
		submitFromData.put("acq_term_sn", "012345678910");
		submitFromData.put("refer_no", "10987654321");
		submitFromData.put("sett_date", "2150708");
		submitFromData.put("txn_date", "2150708");
		submitFromData.put("txn_time", "150700");
		submitFromData.put("trans_crrltn_no", "1234567890");
		submitFromData.put("equity_no", "9d224e72968b8a495606eff46fec2016");
		httpPost(submitFromData, "http://localhost:8282/easy-rpb/v5/union/successNotify");
	}
	
	/**	
	 * @Description:支付失败通知	
	 * @Author:  柯军
	 * @datetime:2015年7月8日下午3:27:59
	 **/
	@Test
	public void testHttpfailure(){
		Map<String, String> submitFromData = getSubmitFromData("002101");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("req_serial_no", "20130921155443030788");
		submitFromData.put("orig_req_serial_no", "20130921155443030787");
		submitFromData.put("trans_crrltn_no", "1234567890");
		httpPost(submitFromData, "http://localhost:8282/easy-rpb/v5/union/failureNotify");
	}
	
	/**	
	 * @Description: 退款通知 	
	 * @Author:  柯军
	 * @datetime:2015年7月8日下午3:34:58
	 **/
	@Test
	public void testHttpRefund(){
		Map<String, String> submitFromData = getSubmitFromData("002101");
		submitFromData.put("shop_no", "086310100000000099");
		submitFromData.put("term_no", "12340004");
		submitFromData.put("req_serial_no", "20130921155443030789");
		submitFromData.put("orig_req_serial_no", "20130921155443030787");
		submitFromData.put("trans_crrltn_no", "1234567890");
		httpPost(submitFromData, "http://localhost:8282/easy-rpb/v5/union/failureNotify");
	}

	private Map<String, String> getSubmitFromData(String msgTxnCode) {
		Map<String, String> submitFromData = new HashMap<String, String>();
		submitFromData.put("msg_type", "00");
		submitFromData.put("msg_txn_code", msgTxnCode);
		submitFromData.put("msg_crrltn_id", "20130921155443030785");
		submitFromData.put("msg_flg", "0");
		submitFromData.put("msg_sender", "1");
		submitFromData.put("msg_time", "20130921155423");
		submitFromData.put("msg_sys_sn", "20130921155443030785");
		submitFromData.put("msg_ver", "0.1");
		return submitFromData;
	}

	private void httpPost(Map<String, String> submitFromData, String url) {
		String parmaPlain = getParamPlain3(submitFromData);
		String privateKey = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100929ccdf660fde8b26c03ef181a7414ffe2a59b92419328e790f50bf96c32242f4d383209cc14e485f485140a8c694ab35a207899ec2dfaa8b5f918323942fa02ad55afcff870e3ac4f248be6843024bd9661aac4bf5d3f71f8fefb2b2593a0ccd161abda2deb290566bac24d70dc1a770266b2585d1fc99da890edea9b88af21020301000102818100900ac6bffd62f8aff3379a3c5021245092d8aa13f3a1255afd2c03ebb5c53d8fcb8a1e7f49a66d2ebea1d0a2dc78c60f97c8b73462ba79be2faadf7ed88b591cb294ba6f739010025d55e3ee820f2afd6dc66763348a8ef018c961deab48d5fe5868225d38b9b97c03f812eb4bdfd2b602e06148af4eb20a55704b913a0b1051024100cff5d92b440886c9509b9ebd4d447b2039477ef8ec0c87d726cef23e372bbd69a9029f4d93ef4e0d6a721ee1a15f18d5b564d8c7951e02bd1688fd0ee2dd3a3b024100b47b078fe517292cab94ac933027a58e91ff8b3a2a3087c9bcdae47558fe5ce4db984666acd15e3adec42e8c568f9a70b9965f700ca224ac277f945b63918a53024100c88d0d2c3e6602a66e884563c5fa3ecb6ab1da6f7024fd73adc771b2323e8a62354d8417aa8ce3b6e86aba015a407d90cb683feee98d91928d02fb18ba0c233502405dadb25a253e2558136363442eed9548704da515b6ca63e0f9cee0606ca5f2cfb38fd192ffc6d8d353c79ea5a75478846440421f65df4f8bb020cf7fb429da1302401ab8cbd3012684e94faf7551ade468412b19eea7c9ca69df09bad25115c28706f7ce63047c421a77ff0a110017c51ebca8afcaa80589b7772ebaac41dcd74d6d";
		String sign = com.chinaums.ysmktaln.mktaln4sp.Service.sign(parmaPlain, privateKey);
		submitFromData.put("sign", sign);
		HttpClient hc = new HttpClient(url, 30000, 30000);
		try {
			int status = hc.send(submitFromData, "utf-8");
			System.err.println("status=" + status);
			String response = hc.getResult();
			System.err.println("result=" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getParamPlain3(Map<String, String> paramsMap) {
		StringBuilder sb = new StringBuilder();
		Set<String> set = paramsMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			sb.append(key);
			sb.append('=');
			sb.append(paramsMap.get(key));
			sb.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
