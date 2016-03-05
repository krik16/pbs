package com.rongyi.rpb.service;

import java.util.List;

import com.rongyi.easy.rpb.domain.PaymentItemEntity;

/**	
 * @Author:  柯军
 * @Description: 子订单service 
 * @datetime:2015年4月23日上午10:02:14
 *
 **/
public interface PaymentItemService {

	/**
	 * 根据osm传入的数据插入数据库，如果存在多个子订单则分多条插入
	 * 
	 * @param paymentEntity
	 * @param orderDetailNum
	 */
	public abstract void insert(PaymentItemEntity paymentItemEntity);
	
	/**	
	 * @Description: 根据paymentId 查询对应子订单信息 
	 * @param id	
	 * @Author:  柯军
	 * @datetime:2015年7月14日上午10:43:30
	 **/
	public abstract List<PaymentItemEntity> selectByPaymentId(Integer paymentId);
	
	/**	
	 * @Description: 获取子订单拼接字符串
	 * @param itemList
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年7月20日上午11:53:37
	 **/
	public String getDetailNum(List<PaymentItemEntity> itemList);

}