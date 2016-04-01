package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.PaymentBill;

/**
 * kejun
 * 2016/4/1 10:44
 **/
public class PaymentBillVO extends PaymentBill{

    private String tradeNo;

    private String shopName;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "PaymentBillVO{" +
                "tradeNo='" + tradeNo + '\'' +
                ", shopName='" + shopName + '\'' +
                ", paymentBill='" + super.toString() + '\'' +
                '}';
    }
}
