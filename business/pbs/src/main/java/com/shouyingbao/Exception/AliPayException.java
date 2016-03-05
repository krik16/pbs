package com.shouyingbao.Exception;

/**
 * Created by kejun on 2015/11/25.
 */
public class AliPayException extends TradeException{
    public AliPayException(String code, String message)
    {
        super(message);
        this.code = code;
    }

}
