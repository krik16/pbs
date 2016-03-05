package com.rongyi.core.enumerate;

/**
 * 商家后台错误码常量
 * @author pk
 */
public enum BsomsEnum {
    
    SUCCESS(0, "success"),
    
    /** 系统性错误 */
    ERROR_SYSTEM(1080001, "系统错误"),
    ERROR_SIGN_INVALID(1080002, "签名错误"),
    ERROR_PARAM_INVALID(1080003, "请求参数错误"),
    ERROR_BSST_EMPTY(1080004, "[bsst]参数为空"),
    ERROR_BSST_INVALID(1080005, "[bsst]参数错误"),
    ERROR_CHANNEL_EMPTY(1080006, "[channel]参数为空"),
    
    /** 正常 但没有数据*/
    ERROR_USER_NOFOUND(1080401, "用户不存在或未登录");
    
    
    private Integer errno; 
    private String msg;
    
	private BsomsEnum(Integer errno, String msg) {
		this.errno = errno;
		this.msg = msg;
	}
	public Integer getErrno() {
		return errno;
	}
	public void setErrno(Integer errno) {
		this.errno = errno;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    
	
    
}
