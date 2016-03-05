package com.rongyi.rpb.service;

import java.util.List;
import java.util.Map;

import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.vo.PayAccountUseTotal;
import com.rongyi.easy.rpb.vo.PaySuccessResponse;

/**
 * @Author: 柯军
 * @Description: 订单支付成功事件记录
 * @datetime:2015年4月23日上午10:02:34
 * 
 **/
public interface PaymentLogInfoService {

	/**
	 * 将支付宝或微信支付返回信息存入MySql数据库中
	 * 
	 * @param
	 * @return exception为存储异常，success 为正常
	 */
	public abstract Map<String, String> insert(PaymentLogInfo logInfo);

	public abstract Integer insertGetId(PaymentLogInfo logInfo);

	/**
	 * 根据付款单查询（支付事件记录）
	 * 
	 * @param outTradeNo
	 * @param tradeType
	 * @return
	 */
	public abstract PaymentLogInfo selectByOutTradeNo(String outTradeNo,Integer tradeType);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public abstract PaymentLogInfo selectById(int id);

	/**
	 * 根据notifyId查询
	 * 
	 * @param
	 * @return
	 */
	public abstract PaymentLogInfo selectByNotifyId(String notifyId);

	/**
	 * @Description: 获取交易流水号获取付款交易记录
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年5月26日下午4:30:55
	 **/
	public abstract PaymentLogInfo selectByPayTradeNo(String tradeNo);

	/**
	 * @Description: 支付完成后通知（增加支付日志，更新订单状态，发送通知到订单状态机）
	 * @param paymentLogInfo
	 * @Author: 柯军
	 * @datetime:2015年6月26日下午4:48:41
	 **/
	public abstract boolean insertPayNotify(PaymentLogInfo paymentLogInfo, Integer tradeType, Integer status, String payChannel);

	/**
	 * @Description: 支付成功发送通知消息
	 * @param paymentLogInfo
	 * @param orderNums
	 * @param orderType
	 * @Author: 柯军
	 * @datetime:2015年5月21日上午11:53:11
	 **/
	public abstract List<PaySuccessResponse> paySuccessToMessage(String out_trade_no,String buyerEmail, String orderNums, Integer orderType, String payChannel);
	

	/**
	 * @Description: 更新退款事件删除状态
	 * @param id
	 * @param deleteStatus
	 * @Author: 柯军
	 * @datetime:2015年7月9日上午11:00:39
	 **/
	public abstract void updateDeleteStatus(Integer id, Integer deleteStatus);
	
	/**	
	 * @Description: 查询支付账号使用次数 
	 * @param map
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月12日上午11:34:50
	 **/
	public abstract List<PayAccountUseTotal> selectPayAccountUseTotal(Map<String,Object> map);
	
	
	/**	
	 * @Description: 验证交易记录是否已存在 
	 * @param tradeNo
	 * @param payNo
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年9月2日下午3:13:09
	 **/
	public abstract boolean validateByTradeNoAndPayNo(String tradeNo,String payNo);
	
	
	/**	
	 * @Description: 微信异步通知结果转换 
	 * @param map
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年9月2日下午4:17:06
	 **/
	public abstract PaymentLogInfo getByWeixinNotify(Map<String,Object> map);

}