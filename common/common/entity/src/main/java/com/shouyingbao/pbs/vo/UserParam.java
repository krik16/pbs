package com.shouyingbao.pbs.vo;

import com.shouyingbao.pbs.entity.User;

/**
 * kejun
 * 2016/3/16 15:41
 **/
public class UserParam extends User{

    private Integer userId;

    private String md5Pwd;



    public String getMd5Pwd() {
        return md5Pwd;
    }

    public void setMd5Pwd(String md5Pwd) {
        this.md5Pwd = md5Pwd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
