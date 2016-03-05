package com.rongyi.core.enumerate;

/**
 * 大运营后台错误码常量
 * @author pk
 */
public enum RyomsEnum {
    
    SUCCESS(0, "success"),
    
    /** 系统性错误 */
    ERROR_SYSTEM(1090001, "系统错误"),
    ERROR_SIGN_INVALID(1090002, "签名错误"),
    ERROR_PARAM_INVALID(1090003, "请求参数错误"),
    ERROR_RYST_EMPTY(1090004, "[ryst]参数为空"),
    ERROR_RYST_INVALID(1090005, "[ryst]参数错误"),
    ERROR_CHANNEL_EMPTY(1090006, "[channel]参数为空"),
    
    /** 正常 但没有数据*/
    ERROR_USER_NOFOUND(1090401, "用户不存在或未登录");
    
    
    private Integer errno; 
    private String msg;
    
	private RyomsEnum(Integer errno, String msg) {
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
