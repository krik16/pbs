package com.shouyingbao.pbs.Exception;

/**
 * Created by kejun on 2015/11/25.
 */
public class UserNotFoundException extends RuntimeException{
    String code;

    public UserNotFoundException()
    {
        super();
    }

    public UserNotFoundException(String message)
    {
        super(message);
    }

    public UserNotFoundException(String code, String message)
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
