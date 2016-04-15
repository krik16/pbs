package com.shouyingbao.pbs.entity;

import java.io.Serializable;

public class UserExtendInfo implements Serializable{

    private static final long serialVersionUID = -7140806968867826584L;

    private Integer id;

    private Integer userId;

    private String custName;

    private String custPhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }
}