package com.rongyi.rpb.common.pay.weixin.model;

import com.rongyi.rpb.common.pay.weixin.service.BaseService;
import com.rongyi.rpb.common.pay.weixin.util.Configure;



/**	
 * @Author:  柯军
 * @Description: 微信扫码支付 
 * @datetime:2015年6月25日下午3:16:46
 *
 **/
public class ScanPayService extends BaseService{

    public ScanPayService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.PAY_API);
    }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(ScanPayReqData scanPayReqData,Configure configure) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(scanPayReqData,configure);

        return responseString;
    }
}
