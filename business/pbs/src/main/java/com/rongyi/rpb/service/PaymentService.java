package com.rongyi.rpb.service;

import com.rongyi.easy.mq.MessageEvent;
import com.rongyi.easy.rpb.domain.PaymentEntity;
import com.rongyi.easy.rpb.domain.PaymentLogInfo;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: 柯军
 * @Description: 付款单
 * @datetime:2015年4月23日上午10:03:11
 * 
 **/
public interface PaymentService {

	/**
	 * 分页数据显示
	 * 
	 * @param searchValueMap
	 * @return
	 */
	public abstract List<PaymentEntityVO> selectPageListBySearch(Map<String, Object> searchValueMap);

	/**
	 * 分页数据总条数
	 * 
	 * @param searchValueMap
	 * @return
	 */
	public abstract Long selectPageListCountBySearch(Map<String, Object> searchValueMap);

	/**
	 * 根据osm传入的数据插入数据库，如果存在多个子订单则分多条插入
	 * 
	 * @param paymentEntity
	 * @param orderDetailNum
	 */
	public abstract void insertByOrderDetailNum(PaymentEntity paymentEntity, String orderDetailNum);

	/**
	 * rpbEvent body中业务数据转换为ParmentEntityVO
	 * 
	 * @param body
	 * @param type
	 * @return
	 */
	public abstract PaymentEntityVO bodyToPaymentEntity(Object body, String type);

	/**
	 * 多个订单生成一个付款单号
	 * 
	 * @author kejun 2015年3月27日
	 * @param paymentEntityVO
	 * @return
	 */
	public abstract List<PaymentEntity> getPaymemtsByMoreOrderNum(PaymentEntityVO paymentEntityVO);

	/**
	 * 根据付款单号获取所有订单号（多个订单号用逗号分隔）
	 * 
	 * @param payNo
	 * @return
	 */
	public abstract String getOrderNumStrsByPayNo(String payNo,Integer tradeType);

	public abstract List<PaymentEntity> selectByPayNo(String payNo);

	/**
	 * 生成mq同步通知信息
	 * 
	 * @param event
	 * @return
	 */
	public abstract Map<String, Object> getSendMessage(MessageEvent event);

	/**
	 * 订单事件插入数据库，返回生成的付款单号（可能存在多条订单对应同一个付款单号，最终支付使用的是付款单号）
	 * 
	 * @param event
	 * @return
	 */
	public abstract PaymentEntityVO insertOrderMessage(MessageEvent event);

	/**
	 * 验证订单号是否存在，如果已存在则是重复提交订单，直接返回直接生成的订单号
	 * 
	 * @param orderNum
	 * @return 返回时null是证明不存在
	 */
	public abstract PaymentEntity validateOrderNumExist(String orderNum, Integer payChannel, Integer tradeType);

	/**
	 * 商品价格为0时特殊处理
	 * 
	 * @author kejun 2015年4月2日
	 * @param event
	 * @return
	 */
	public abstract Map<String, Object> getZeroSendMessage(MessageEvent event,Map<String, Object> messageMap, PaymentEntityVO paymentEntityVO);

	public abstract String getTitle(String orderNum);

	public abstract List<PaymentEntity> selectByPayNoAndTradeType(String payNo, Integer tredeType);

	public abstract Map<String, String> insert(PaymentEntity paymentEntity);

	public abstract PaymentEntity selectByPrimaryKey(String id);

	public abstract List<PaymentEntity> selectByOrderNum(String orderNum,Integer tradeType,Integer payChannel);

	public abstract PaymentEntity selectByOrderNumAndTradeType(String orderNum, Integer tradeType, Integer status,Integer payChannel);
	

	public abstract int updateByPrimaryKeySelective(PaymentEntity paymentEntity);

	/**
	 * @Description: 提现申请生成待付款记录
	 * @param event
	 * @Author: 柯军
	 * @datetime:2015年5月20日上午11:53:17
	 **/
	public abstract PaymentEntity insertDrawApply(MessageEvent event);

	public abstract Map<String, Object> getBodyMap(String payNo, String orderNum, String orderDetailNum, String source, String target, String type);

	public abstract List<PaymentEntity> updateListStatusBypayNo(String payNo, Integer tradeType, Integer status);

	public abstract void deleteByPayNo(String payNo);
	
	/**	
	 * @Description: 根据订单号和退款批量单号查询 
	 * @param orderNum
	 * @param batchNo
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月3日下午4:56:12
	 **/
	public abstract PaymentEntity selectByOrderNumAndBatchNo(String orderNum,String batchNo);
	
	/**	
	 * @Description: 查询付款单号某种支付方式是否已完成支付（验证是否是重复支付） 
	 * @param payNo
	 * @param payChannel
	 * @param tradeType
	 * @param status
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月17日上午10:54:53
	 **/
	public abstract PaymentEntity selectByPayNoAndPayChannelAndTradeType(String payNo,Integer payChannel,Integer tradeType,Integer status);
	
	/**	
	 * @Description: 增加重复支付记录 
	 * @param paymentEntity
	 * @param paymentLogInfo	
	 * @Author:  柯军
	 * @datetime:2015年8月17日上午11:46:00
	 **/
	public abstract void insertRepeatPay(PaymentEntity paymentEntity,PaymentLogInfo paymentLogInfo);


	/**
	 * @Description: 验证是否是重复付款
	 * @param payNo
	 * @param newPayChannel 收到新支付通知中的支付方式
	 * @return
	 * @Author: 柯军
	 * @datetime:2015年8月17日上午11:24:04
	 **/
	public PaymentEntity validateRepeatPay(String payNo,Integer newPayChannel);
	

	/**
	 * @Description: 重复付款自动退还重复付款的金额并记录重复付款记录
	 * @param paymentEntity
	 * @param paymentLogInfo
	 * @Author: 柯军
	 * @datetime:2015年8月17日上午11:58:59
	 **/
	public  boolean repeatPayToRefund(PaymentEntity paymentEntity, PaymentLogInfo paymentLogInfo);
	
	/**	
	 * @Description: 查询退款批量单号状态是否已存在 
	 * @param batchNo
	 * @param status
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月24日上午9:19:03
	 **/
	public abstract List<PaymentEntity> selectByBatchNoAndStatus(String batchNo,Integer status);
	
	/**	
	 * @Description: 将paymentEvent中的付款方式转换成数据库中存储方式 
	 * @param payChannel
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月24日下午4:05:44
	 **/
	public abstract Integer getRealPayChannel(Integer payChannel);
	
	/**	
	 * @Description: 更新支付订单状态 
	 * @param payNo
	 * @param type
	 * @param status
	 * @param payChannel
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月24日下午4:14:11
	 **/
	public void updateListStatus(String payNo, Integer type, Integer status,Integer payChannel);
	
	
	/**	
	 * @Description:  查询是否允许退款的记录
	 * @param tradeType
	 * @param payChannel
	 * @param status
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年8月27日上午10:42:19  的                                                                                                                         
	 **/
	public List<PaymentEntity> selectByTradeTypeAndRefundRejected(Integer tradeType,Integer payChannel,Integer refundRejected,Integer status);
	
	/**	
	 * @Description:  
	 * @param id
	 * @param refundRejected	
	 * @Author:  柯军
	 * @datetime:2015年8月28日上午10:42:04
	 **/
	public void updateRefundRejected(Integer id,Integer refundRejected);

	
	/**	
	 * @Description: 验证付款记录状态 
	 * @param ids
	 * @param status
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年9月1日下午1:40:43
	 **/
	public abstract List<PaymentEntity> valiadteStatus(String[] ids,Integer status);
	
	/**	
	 * @Description: 生成付款单号 
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年9月1日下午5:30:18
	 **/
	public abstract String getPayNo();
	
	/**	
	 * @Description: 根据id更新
	 * @param ids
	 * @param map	
	 * @Author:  柯军
	 * @datetime:2015年10月14日下午6:53:35
	 **/
	public abstract void updateByIds(String[] ids,Map<String,Object> map);

	/**
	 * @Description: 查询付款单(加锁)
	 * @param:
	 * @Author:  柯军
	 **/

	public abstract PaymentEntity selectByWithLock(Integer id);

	/**
	 *  Description: 根据付款单号和订单号查询记录
	 *  @param payNo 付款单号
	 *  @param orderNo 订单号
	 *  @param tradeType 交易类型
	 *  @param status 状态
	 *  Author: kejun
	 **/
	PaymentEntity selectByPayNoAndOrderNo(String payNo,String orderNo,Integer tradeType,Integer status);
}
