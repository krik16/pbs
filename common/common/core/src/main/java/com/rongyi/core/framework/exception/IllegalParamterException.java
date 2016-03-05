package com.rongyi.core.framework.exception;



/**
 * 
 * ClassName: IllegalParamterException <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2015年3月31日 上午11:07:01 <br/>  
 *  
 * @author bin.zhang  
 * @version   
 * @since JDK 1.6
 */
public class IllegalParamterException extends RYServiceException {

	private static final long serialVersionUID = -9145714915360036533L;

	public IllegalParamterException() {
		this(null);
	}

	public IllegalParamterException(String description) {
		super(description, RYErrorCode.ILLEGAL_ARGUMENTS);
	}

}