package com.rongyi.core.bean;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2015/12/30
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/12/30              1.0            创建文件
 *
 */

public class MallShopResponseVo implements java.io.Serializable {


    public Boolean istrue;

    public String code;

    public String msg;


    public MallShopResponseVo(){

    }

    public Boolean getIstrue() {
        return istrue;
    }

    public void setIstrue(Boolean istrue) {
        this.istrue = istrue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
