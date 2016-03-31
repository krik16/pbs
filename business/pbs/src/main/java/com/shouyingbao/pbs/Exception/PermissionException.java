package com.shouyingbao.pbs.Exception;

/**
 * Created by kejun on 2015/11/25.
 */
public class PermissionException extends RuntimeException{
    String code;

    public PermissionException()
    {
        super();
    }

    public PermissionException(String message)
    {
        super(message);
    }

    public PermissionException(String code, String message)
    {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
