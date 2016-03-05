package com.rongyi.core.enumerate;

/**
 * 
 * @author pk
 */
public enum RoaEnum {
    
    SUCCESS(0, "success"),
    
    /** 系统性错误 */
    ERROR_SYSTEM(1050001, "系统错误"),
    ERROR_SIGN_INVALID(1050002, "签名错误"),
    ERROR_PARAM_INVALID(1050003, "请求参数错误"),
    
    ERROR_CURRENTPAGE_EMPTY(1050004, "[currentPage]参数错误"),
    ERROR_PAGESIZE_INVALID(1050005, "[pageSize]参数错误");
    
    
    
    private Integer errno; 
    private String msg;
    
	private RoaEnum(Integer errno, String msg) {
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
