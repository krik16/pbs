package com.rongyi.rpb.service;

import java.util.Map;

import com.rongyi.easy.rpb.entity.UnionCouponLogEntity;
import com.rongyi.easy.rpb.vo.UnionCouponLogVO;


/**	
 * @Author:  柯军
 * @Description: 银联支付 
 * @datetime:2015年4月23日上午10:05:23
 *
 **/

public interface UnionCouponService {
	
	/**	
	 * @Description: 券码验证
	 * @param paramMap
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年7月2日下午1:49:13
	 **/
	public abstract Map<String,Object> doMessage(Map<String,String[]> paramMap);
	
	/**	
	 * @Description: 插入 
	 * @param unionCouponLogEntity	
	 * @Author:  柯军
	 * @datetime:2015年7月2日下午2:22:13
	 **/
	public abstract void insert(UnionCouponLogEntity unionCouponLogEntity);
	
	/**	
	 * @Description: 根据查询代码和交易流水号查询 
	 * @param msgTxCode
	 * @param transCrrltnNo
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年7月6日下午6:50:45
	 **/
	public abstract UnionCouponLogEntity selectByMsgtxCodeAndTransNo(String msgTxCode,String transCrrltnNo);
	
	/**	
	 * @Description: 常规应答项
	 * @param msgCrrltnId
	 * @param msgSysSn
	 * @param msgTxnCode
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年7月7日上午9:48:45
	 **/
	public Map<String, Object> reponseUnionCouponHeader(String msgCrrltnId, String msgSysSn, String msgTxnCode);
	
	/**	
	 * @Description: 银联退款操作 
	 * @param unionCouponLogVO	
	 * @Author:  柯军
	 * @datetime:2015年7月10日下午2:58:55
	 **/
	public void refundOperate(UnionCouponLogVO unionCouponLogVO);
	
}
