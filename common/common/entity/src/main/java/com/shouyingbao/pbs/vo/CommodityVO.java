package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.Commodity;

/**
 * kejun
 * 2016/4/18 16:18
 **/
public class CommodityVO extends Commodity{
    private static final long serialVersionUID = 605956953655310339L;

    private Integer userId;

    private String categoryName;

    private String categoryDesc;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public String toString() {
        return "CommodityVO{" +
                "userId=" + userId +
                ",categoryName=" + categoryName +
                ",categoryDesc=" + categoryDesc +
                ",super=" + super.toString() +
                '}';
    }
}
