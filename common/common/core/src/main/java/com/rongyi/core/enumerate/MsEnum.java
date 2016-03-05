package com.rongyi.core.enumerate;

import com.rongyi.core.framework.exception.IErrorCode;

/**
 * Description:消息中心 错误码
 * 1 表示透传错误码 02 营销中心 10 消息中心  10210XX
 * Author: 袁波
 * DATE: 2015/12/24 15:23
 * Package:com.rongyi.core.enumerate
 * Project:message-center
 */
public enum MsEnum implements IErrorCode {
    SUCCESS(0, "正常"),
    ERROR_SYSTEM(1021001, "系统故障"),
    ERROR_PARAM(1021002, "必要参数不合法或无效");
    private int code;
    private String message;

    private MsEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getActionCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return String.valueOf(this.code);
    }

    public String getErrorMsg() {
        return this.message;
    }
    }
