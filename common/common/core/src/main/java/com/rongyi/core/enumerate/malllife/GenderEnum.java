package com.rongyi.core.enumerate.malllife;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  性别枚举
 * time:  2015/11/19
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/11/19              1.0            创建文件
 *
 */

public enum GenderEnum {

    UNKNOWN("U","未知"),
    MALE("M","男"),
    FEMALE("F","女"),
    NEUTRAL("N","中性");

    private String code;
    private String name;
    private GenderEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
