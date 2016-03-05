/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月9日上午11:09:06
 * @Description: TODO
 *
 **/

package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import base.BaseTest;

import com.rongyi.rpb.service.PaymentLogInfoService;

/**	
 * @Author:  柯军
 * @Description: TODO 
 * @datetime:2015年7月9日上午11:09:06
 *
 **/

public class PaymentLogInfoServiceTest extends BaseTest{
	
	@Autowired
	PaymentLogInfoService paymentLogInfoService;
	
	@Test
	@Rollback(false)
	public void testUpdateDeleteStatus(){
		paymentLogInfoService.updateDeleteStatus(1268, 1);
	}
	
	@Test
	public void testSelectPayAccountUseTotal(){
		System.err.println(paymentLogInfoService.selectPayAccountUseTotal(new HashMap<String,Object>()).size());
	}
	
	@Test
	public void testValidateByTradeNoAndPayNo(){
		System.err.println(paymentLogInfoService.validateByTradeNoAndPayNo("11", "111"));
	}
}
