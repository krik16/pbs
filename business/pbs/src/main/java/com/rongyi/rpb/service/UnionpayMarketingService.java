package com.rongyi.rpb.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.rongyi.easy.rpb.entity.UnionActivityRegister;

/**	
 * @Author:  王志文
 * @Description: 银联营销联盟 
 * @datetime:2015年7月3日上午10:05:58
 *
 **/
public interface UnionpayMarketingService {

	/**
	 * 活动注册
	 * @return
	 */
	public abstract void insert(UnionActivityRegister unionActivityRegister);
	
	/**
	 * 通过门店查询活动号
	 * @param shop_no门店号
	 */
	public abstract UnionActivityRegister selectByShopNo(String shop_no);
	/**
	 * 根据主键改变活动状态
	 * @param id
	 * @return
	 */
	//public abstract void deleteByEventStatus(UnionActivityRegister unionActivityRegister);
	public abstract void deleteByEventStatus(Map<String, String[]> paramMap)throws ParseException;
	

	/**
	 * 验证银联商务返回的报文
	 */
	public abstract Map<String, Object> validateXML(Map<String,String[]> paramMap)throws ParseException;
}
