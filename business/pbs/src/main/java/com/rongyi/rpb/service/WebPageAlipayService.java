package com.rongyi.rpb.service;

import com.rongyi.easy.rpb.vo.PaymentEntityVO;

import java.util.Map;
/**	
 * @Author:  柯军
 * @Description: 支付宝网页支付实现类 
 * @datetime:2015年4月23日上午10:05:58
 *
 **/
public interface WebPageAlipayService {

	/**
	 * 通过基本参数和业务参数获取支付宝令牌token (支付宝网页支付后台流程第一步)
	 * 
	 * @param paymentEntityVO
	 * @return token
	 */
	public abstract Map<String, Object> getToken(PaymentEntityVO paymentEntityVO);

	/**
	 * 得到所要加载在跳转支付宝收银台之前页面的form表单
	 * 
	 * @return form表单（display none,将它隐藏在页面上自动提交）
	 */
	public abstract String getAuthandexecute();

	/**
	 * 验证call_back 返回数据（还未使用）
	 * 
	 * @param map
	 * @return 验证结果
	 */
	public abstract Boolean verify(Map<String, String> map);

}