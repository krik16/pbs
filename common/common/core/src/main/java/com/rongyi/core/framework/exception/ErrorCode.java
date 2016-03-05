package com.rongyi.core.framework.exception;


public class ErrorCode implements IErrorCode {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    

	private String errorMsg;

	void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ErrorCode(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
}