/**
 * Project Name:easy-entity File Name:RYException.java Package Name:com.rongyi.easy.exception
 * Date:2015年3月31日上午11:02:38 Copyright (c) 2015, vipshop.com All Rights Reserved.
 * 
 */

package com.rongyi.core.framework.exception;



/**
 * ClassName:RYServiceException <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 容易网业务异常<br/>
 * Date: 2015年3月31日 上午11:02:38 <br/>
 * 
 * @author bin.zhang
 * @version
 * @since JDK 1.6
 * @see
 */
public class RYServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;


  /** 异常码 */
  private IErrorCode errorCode;

  /**
   * 异常描述。
   */
  private String description;

  protected RYServiceException() {}

  public RYServiceException(String code, String desc) {
    this.errorCode = new ErrorCode(code, desc);
    description = desc;
  }

  public RYServiceException(IErrorCode errorCode) {
    this(errorCode, null);
  }

  public RYServiceException(String description, IErrorCode errorCode) {
    this(errorCode.getErrorCode(), description);
  }

  public RYServiceException(String description, IErrorCode errorCode, Throwable cause) {
    this(errorCode, cause);
    this.description = description;
  }

  public RYServiceException(IErrorCode errorCode, Throwable cause) {
    super(cause);
    if (errorCode == null) {
      throw new java.lang.IllegalArgumentException("error code could not null.");
    }
    this.errorCode = errorCode;
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String getMessage() {
    return "errorCode=" + this.errorCode.getErrorCode() + ",errorMsg="
        + this.errorCode.getErrorMsg();
  }
}
