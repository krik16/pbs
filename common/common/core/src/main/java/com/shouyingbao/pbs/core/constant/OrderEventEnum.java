package com.shouyingbao.pbs.core.constant;

/**
 * Created by Ventures on 2015/12/24.
 * 母订单事件
 */
public enum  OrderEventEnum {
    ORDERCART_BUYER_CLOSE("28", "买家关闭母订单"),
    ORDERCART_PAY_OVERTIME("30", "母订单支付超时");
    private String code;
    private String message;

    OrderEventEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIntCode(){
        return Integer.valueOf(this.code);
    }
}
