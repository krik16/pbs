package com.rongyi.easy.rpb.vo;

import java.io.Serializable;

/**
 * @Copyright (C), 上海容易网电子商务有限公司
 * kejun
 * 2016/1/20 11:48
 **/
public class RefundQueryParamVO  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4210085071379422435L;

    /**
     * 订单号
     */
    public String orderNo;

    /**
     * 退款单号
     */
    public String refundNo;

    /**
     * 付款方式（0:支付宝，1:微信）
     */
    public  Integer paychannel;

    /**
     * 容易网操作预计天数
     */
    public Integer rongyiPayDay=3;

    /**
     * 第三方支付平台操作预计天数
     */
    public Integer platformPayDay=2;

    /**
     * 退款完成预计天数
     */
    public Integer refundedDay=3;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Integer getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(Integer paychannel) {
        this.paychannel = paychannel;
    }

    public Integer getRongyiPayDay() {
        return rongyiPayDay;
    }

    public void setRongyiPayDay(Integer rongyiPayDay) {
        this.rongyiPayDay = rongyiPayDay;
    }

    public Integer getPlatformPayDay() {
        return platformPayDay;
    }

    public void setPlatformPayDay(Integer platformPayDay) {
        this.platformPayDay = platformPayDay;
    }

    public Integer getRefundedDay() {
        return refundedDay;
    }

    public void setRefundedDay(Integer refundedDay) {
        this.refundedDay = refundedDay;
    }

    @Override
    public String toString() {
        return "RefundQueryParamVO{" +
                "orderNo='" + orderNo + '\'' +
                ", refundNo='" + refundNo + '\'' +
                ", paychannel=" + paychannel +
                ", rongyiPayDay=" + rongyiPayDay +
                ", platformPayDay=" + platformPayDay +
                ", refundedDay=" + refundedDay +
                '}';
    }
}
