package com.shouyingbao.pbs.vo;

import java.io.Serializable;

/**
 * kejun
 * 2016/4/5 15:09
 **/
public class TradeTotal implements Serializable{

    private static final long serialVersionUID = 7597937371593413701L;

    private Double amountTotal;

    private Integer countTotal;

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }
}
