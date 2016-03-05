package com.rongyi.core.common.third.malllife;/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  容易逛错误码
 * time:  2015/9/16
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/9/16              1.0            创建文件
 *
 */

/**
 * 错误码说明：错误码共7位
 * 第1位表示错误码类型，1表示透传错误码，错误提示(msg)直接用后端返回的,2表示需特殊处理的错误码
 * 第2、3位  固定为 06
 * 4、后4位表示子系统内的具体错误
 * 容易逛内部错误编码 使用AppErrorCode.java
 */
public enum MallLifeErrorCode {

    SUCCESS(0, "正常"), //原系统都定义为正常情况 编码规则不变
    FAILURE(-1, "请求失败"), //原系统都定义为正常情况 编码规则不变

    /** 外部渠道编码 后4位区间  8000---9000*/
    NECESSARY_PARAMETER_ISNULL(1068001,"必要参数不能为空"),
    SIGNATURE_FAILURE(1068110,"签名无效"),
    TIME_STAMP_FORMAT(1068111,"请求时间戳格式不对"),
    REQUEST_EXPIRATION(1068112,"请求已过期"),
    DECRYPTION_FAILURE(1068115,"解码失败,加密格式不对"),
    /*  8200-8250 参数合法性验证 */
    MOBILE_NUMBER_ILLEGAL(1068201,"手机号码不合法"),
    USER_PHONE_ISEXIST(1068205,"注册手机号已经存在"),
    PARAMETER_FORMAT (1068208,"参数格式不合法"),

    /**toB业务 */
    COUPON_OPENID_ISEXIST(1069111,"用户不存在"),
    COUPON_IDS_ISEXIST(1069101,"优惠卷(红包)ID为空")
    ;



    /**
     *
     */
    private int code;
    private String message;
    private MallLifeErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
