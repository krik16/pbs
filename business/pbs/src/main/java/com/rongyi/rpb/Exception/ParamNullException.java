package com.rongyi.rpb.Exception;

import com.rongyi.rpb.constants.ConstantEnum;

/**
 * Created by kejun on 2015/11/25.
 */
public class ParamNullException extends TradeException {

    public ParamNullException(String code, String message)
    {
        super(message);
        this.code = code;
    }

    public ParamNullException(){
       throw new WeixinException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
   }
}
