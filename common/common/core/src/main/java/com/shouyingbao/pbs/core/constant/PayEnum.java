/**
 * @Copyright (C),上海容易网电子商务有限公司	
 * @Author: 柯军 
 * @datetime:2015年5月19日下午5:31:46
 * @Description: TODO
 *
 **/

package com.shouyingbao.pbs.core.constant;

/**	
 * @Author:  柯军
 * @Description: 交易类型
 * @datetime:2015年5月19日下午5:31:46
 *
 **/

public enum PayEnum {
    
    DRAW_APPLY_ONE(0,"佣金提现页面申请（单条付款）"),
    DRAW_APPLY_MORE(1,"佣金提现页面申请（多条付款）"),
    TRADE_REFUND_ONE(2,"交易退款页面申请（单条退款）"),
    TRADE_REFUND_MORE(3,"交易退款页面申请（多条退款）"),
    EXCE_PAY_ONE(4,"异常交易页面申请（单条付款）"),
    EXCE_PAY_MORE(5,"异常交易页面申请（单条付款）"),
    STATEMENT_ONE(6,"对账单付款（单条付款）"),
    STATEMENT_MORE(7,"对账单付款（批量付款）");
    

    PayEnum(Integer code,String value){
        this.code = code;
        this.value = value;
    }

    
    private Integer code;
    
    private String value;
    
    public Integer getCode() {
        return code;
    }



    public String getValue() {
        return value;
    }


}
