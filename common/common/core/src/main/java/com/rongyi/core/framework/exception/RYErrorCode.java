package com.rongyi.core.framework.exception;



/**
 * 
 * ClassName: RYErrorCode <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 错误码的定义<br/>
 * date: 2015年3月13日 下午4:06:19 <br/>
 * 
 * @author bin.zhang
 * @version
 * @since JDK 1.6
 */
public enum RYErrorCode implements IErrorCode {
  /****
   * 参数错误
   */
  ILLEGAL_ARGUMENTS("400", "illegal arguments"), ADAPTER_NULL("10001", "adpater empty"),

  /** 系统错误 */
  SYSTEM_ERROR("1000", "System error"), SOAP_ERROR("2000", "SOAP 传输错误"), INTEGRAL_NOT_FOUND("3000",
      "礼品不存在"), INTEGRAL_NOT_ENOUGH("30001", "积分不足"), INTEGRAL_REPERTORY_NOT_ENOUGH("30002",
      "超过兑换限制"), EXCHANGE_CODE_NULL("30003", "兑换码为空"), EXCHANGE_LIMIT("30004", "库存不足"), USER_NOT_FOUND(
      "30005", "用户不存在"), SIGN_IN_MODEL_NULL("30006", "签到规则不存在"), SIGN_IN_OVERRUN("30007", "签到次数超限"), SIGN_IN_ERROR(
      "30008", "非法签到"), REGISTER_ERROR_USER_EXIST("30009", "电子用户已注册"), INTEGRAL_EXCHANGE_LIMIT(
      "30010", "超过当天兑换的最大次数"), EM_NOT_EXIST("30011", "电子会员未注册"), EM_INTEGAL_NOT_ENOUGH("30012",
      "电子会员积分不足");

  public String getErrorCode() {
    return this.errorCode;
  }

  public String getErrorMsg() {
    return this.errorMsg;
  }

  private String errorCode;

  private String errorMsg;

  private RYErrorCode(String errorCode, String errorMsg) {
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

}
