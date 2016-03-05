/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年8月25日上午11:00:03
 * @Description: TODO
 *
 **/

package service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rongyi.easy.rpb.vo.RefundQueryParamVO;
import com.rongyi.rpb.unit.AliPayUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import base.BaseTest;
import com.rongyi.core.common.util.DateUtil;
import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.WeixinMch;
import com.rongyi.easy.rpb.vo.PaymentParamVO;
import com.rongyi.rss.rpb.IRpbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**	
 * @Author:  柯军
 * @Description: TODO 
 * @datetime:2015年8月25日上午11:00:03
 *
 **/

public class RpbServiceTest extends BaseTest{

	@Autowired
	IRpbService iRpbService;

	@Autowired
	AliPayUnit aliPayUnit;
	
//	@Test
	public void testOperateWeixinRefund(){
		Map<String,Object> map = iRpbService.operateWeixinRefund(14376);
		System.err.println(map.toString());
	}
	
	@Test
	public void testGetPaySign(){
//		{"body":{"weidianId":"\"\"","title":"测试优惠券 勿买","orderType":"1",
//		"orderDetailNum":"\"\"","orderNum":"\"1009351119361126\"","totalPrice":0.01},"source":"coupon_order_queue","target":"rpb","timestamp":1444361283613,"type":"5"}
		MessageEvent event = new MessageEvent();
		event.setSource("coupon_order_queue");
		event.setTarget("rpb");
		event.setType("5");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("weidianId","\"\"");
		map.put("title", "卡券测试1");
		map.put("timeStart", "2016-02-16 17:17:19");
		map.put("timeExpire", "2016-02-16 17:52:19");
		map.put("appId", "wxb0af59268c136d7d");
		map.put("openId", "o0BDmjr5jmDDZieVha8Evg_aJdQM");
		map.put("orderDetailNum", "\"\"");
		map.put("orderNum", "\"021644108544151726\"");
		map.put("weixinPayType", "1");
		map.put("orderType", "1");
		map.put("totalPrice", 2);
		event.setBody(map);
		iRpbService.getPaySign(event);
	}
	
//	@Test
	@Rollback(false)
	public void testGeneratePayment(){
		PaymentParamVO paymentParamVO = new PaymentParamVO();
		paymentParamVO.setCreateAt(DateUtil.getCurrDateTime());
		paymentParamVO.setOperateNo("1232131231");
		paymentParamVO.setOperateType("15");
		paymentParamVO.setPayAccount("13564452580");
		paymentParamVO.setPayChannel(0);
		paymentParamVO.setPayName("柯军");
		paymentParamVO.setPayTotal(1d);
		paymentParamVO.setUserId("superadmin");
		Map<String,Object> map = iRpbService.generatePayment(paymentParamVO);
		System.err.println(map.toString());
	}
	@Test
	@Rollback(false)
	public void testAddWeixinMch(){
		WeixinMch weixinMch = new WeixinMch();
		weixinMch.setIsRongyiPay((byte)0);
		weixinMch.setTradeType("1");
		weixinMch.setAppId("aaaa");
		weixinMch.setCretPath("/test/test2");
		weixinMch.setKey("1231");
		weixinMch.setMchId("1001");
		weixinMch.setPublicCode("asda");
		weixinMch.setUserId("1231");
		iRpbService.addWeixinMch(weixinMch);
	}

	@Test
	public void testgetRefundStatus(){
		RefundQueryParamVO refundQueryParamVO = new RefundQueryParamVO();
		refundQueryParamVO.setRefundNo("0012658247424173305");
		iRpbService.getRefundStatus(refundQueryParamVO);
	}

	@Test
	public void testqueryRefund(){
		aliPayUnit.queryRefund(null,"201601210012111979520171917");
	}


	private static Date addWorkDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mod = num % 5;
		int other = num / 5 * 7;
		for (int i = 0; i < mod;) {
			cal.add(Calendar.DATE, 1);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.SUNDAY:
				case Calendar.SATURDAY:
					break;
				default:
					i++;
					break;
			}
		}
		if (other > 0)
			cal.add(Calendar.DATE, other);
		return cal.getTime();
	}

	public static void main(String[] args){
		System.err.println("newTime="+addWorkDay(new Date(), 8));
	}
}
