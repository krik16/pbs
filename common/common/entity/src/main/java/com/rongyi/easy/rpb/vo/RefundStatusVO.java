package com.rongyi.easy.rpb.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 上海容易网电子商务有限公司
 * kejun
 * 2016/1/20 11:35
 **/
public class RefundStatusVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4210085071379425435L;

    /**
     *  容易网是否处理
     **/
    public boolean isRongyiProcess;
    /**
     * 容易网处理时间
     */
    public Date rongyiDate;

    /**
     * 支付平台是否处理
     */
    public boolean isPayPlatformProcess;

    /**
     * 支付平台处理时间
     */
    public Date payPlatformDate;

    /**
     * 退款结果
     *SUCCESS—退款成功
     *PROCESSING—退款处理中
     *FAIL—退款失败
     */
    public String refundReslt;

    /**
     *退款完成时间
     */
    public Date refundDate;

    public boolean isRongyiProcess() {
        return isRongyiProcess;
    }

    public void setIsRongyiProcess(boolean isRongyiProcess) {
        this.isRongyiProcess = isRongyiProcess;
    }

    public Date getRongyiDate() {
        return rongyiDate;
    }

    public void setRongyiDate(Date rongyiDate) {
        this.rongyiDate = rongyiDate;
    }

    public boolean isPayPlatformProcess() {
        return isPayPlatformProcess;
    }

    public void setIsPayPlatformProcess(boolean isPayPlatformProcess) {
        this.isPayPlatformProcess = isPayPlatformProcess;
    }

    public Date getPayPlatformDate() {
        return payPlatformDate;
    }

    public void setPayPlatformDate(Date payPlatformDate) {
        this.payPlatformDate = payPlatformDate;
    }

    public String getRefundReslt() {
        return refundReslt;
    }

    public void setRefundReslt(String refundReslt) {
        this.refundReslt = refundReslt;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }
}
