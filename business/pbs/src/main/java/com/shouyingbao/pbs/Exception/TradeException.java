package com.shouyingbao.pbs.Exception;

/**
 * Created by kejun on 2015/11/25.
 */
public class TradeException extends RuntimeException{
    String code;

    public TradeException()
    {
        super();
    }

    public TradeException(String message)
    {
        super(message);
    }

    public TradeException(String code, String message)
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
