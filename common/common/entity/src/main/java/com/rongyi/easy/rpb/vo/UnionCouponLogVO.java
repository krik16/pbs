/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年7月2日下午2:31:19
 * @Description: TODO
 *
 **/

package com.rongyi.easy.rpb.vo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.rongyi.easy.rpb.entity.UnionCouponLogEntity;

/**
 * @Author: 柯军
 * @Description: UnionCouponLogVO
 * @datetime:2015年7月2日下午2:31:19
 * 
 **/
public class UnionCouponLogVO extends UnionCouponLogEntity implements Serializable {

	private static final long serialVersionUID = 1153599173271240259L;

	private Double payAmt;

	private Double discountAmt;

	public Double getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Double payAmt) {
		this.payAmt = payAmt;
	}

	public Double getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(Double discountAmt) {
		this.discountAmt = discountAmt;
	}

	public static UnionCouponLogVO mapToUnCouponLogVO(Map<String, String[]> paramMap) {
		UnionCouponLogEntity unionCouponLog = mapToEntity(paramMap);
		UnionCouponLogVO unionCouponLogVO = new UnionCouponLogVO();
		BeanUtils.copyProperties(unionCouponLog, unionCouponLogVO);
		if (paramMap.get("pay_amt") != null && paramMap.get("pay_amt").length > 0)
			unionCouponLogVO.setPayAmt(Double.valueOf(paramMap.get("pay_amt")[0]));
		if(paramMap.get("discount_amt") != null && paramMap.get("discount_amt").length > 0)
			unionCouponLogVO.setDiscountAmt(Double.valueOf(paramMap.get("discount_amt")[0]));
		return unionCouponLogVO;
	}
}
