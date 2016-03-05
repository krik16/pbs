package com.rongyi.rpb.Exception;

/**
 * Created by kejun on 2015/11/25.
 */
public class WeixinException extends TradeException{

    public WeixinException(String code, String message)
    {
        super(message);
        this.code = code;
    }


}
