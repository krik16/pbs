package com.shouyingbao.pbs.entity;

import com.shouyingbao.pbs.core.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class PaymentBill implements Serializable {
    private static final long serialVersionUID = 4617595439955455983L;
    private Integer id;

    private String orderNo;
    private String refundNo;

    private Byte payChannel;

    private Integer mchId;

    private Byte payType;

    private Integer payAmount;

    private Byte status;

    private Byte tradeType;

    private Date createAt;

    private Date finishAt;

    private String payAccount;

    private String payName;

    private String orderTitle;

    private Integer shopId;

    private Integer uesrId;

    private String batchNo;

    private Byte isDelete = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getTradeType() {
        return tradeType;
    }

    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUesrId() {
        return uesrId;
    }

    public void setUesrId(Integer uesrId) {
        this.uesrId = uesrId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public static PaymentBill initBill(String orderNo,String refundNo,Integer mchId,String body,Integer totalFee,Integer shopId,Integer userId,byte payChannel,byte payType,byte tradeType){
        PaymentBill paymentBill = new PaymentBill();
        paymentBill.setCreateAt(DateUtil.getCurrDateTime());
        paymentBill.setOrderNo(orderNo);
        if(refundNo != null)
            paymentBill.setRefundNo(refundNo);
        paymentBill.setMchId(mchId);
        paymentBill.setOrderTitle(body);
        paymentBill.setPayAmount(totalFee);
        paymentBill.setPayChannel(payChannel);
        paymentBill.setPayType(payType);
        paymentBill.setShopId(shopId);
        paymentBill.setStatus((byte)0);
        paymentBill.setUesrId(userId);
        paymentBill.setTradeType(tradeType);
        return paymentBill;
    }
}