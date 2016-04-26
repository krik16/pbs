package com.shouyingbao.pbs.Exception;

import com.shouyingbao.pbs.constants.ConstantEnum;

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
       throw new ParamNullException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
   }
}
