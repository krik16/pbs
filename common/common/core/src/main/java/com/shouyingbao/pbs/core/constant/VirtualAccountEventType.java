/** 
* @Title: VirtualAccountEventType.java 
* @Package com.rongyi.core.constant 
* @Description: 虚拟账户事件类型
* @author 郑亦梁  zhengyiliang@rongyi.com
* @date 2015年5月13日 下午2:50:21 
* @version V1.0   
* Copyright (C),上海容易网电子商务有限公司
*/
package com.shouyingbao.pbs.core.constant;

/**
 * @author ZhengYl
 *
 */
public interface VirtualAccountEventType {

    /** 返佣 */
    public static final int COMMISSION = 1;
    
    /** 奖金 */
    public static final int BONUS = 2;
    
    /** 支付款 */
    public static final int PAYMENT = 3;
    
    /** 罚款 */
    public static final int FINE = 4;
    
    /** 提现 */
    public static final int DRAW = 5;
    
    /**
     * 优惠券返佣
     */
    public static final int COUPON_COMMISSION = 6;
    
    /** 修改虚拟账户 (tms) */

    /** 提现支付 (rpb) */
    public static final String WITHDRAW_PAY = "1";
    
    /** 返佣金额入账 (tms) */
    public static final String COMMISSION_POST = "2";
    
    /** 提现请求 (tms) */
    public static final String WITHDRAW_REQUEST = "3";
    
    /** 红包金额入账 (osm) */
    public static final String COUPON_POST = "4";
    
    /** 创建虚拟账户 (tms) */
    public static final String ACCOUNT_CREATE = "5";
    
    /** 修改虚拟账户 (tms) */
    public static final String ACCOUNT_EDIT = "6";
    
    /** 冻结/启用虚拟账户 (tms) */
    public static final String ACCOUNT_LOCK_UNLOCK = "7";

    /** 提现支付金额入账成功 (va) */
    public static final String WITHDRAW_COLLECTED = "8";

}
