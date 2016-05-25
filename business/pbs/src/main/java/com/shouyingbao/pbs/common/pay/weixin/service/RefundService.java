package com.shouyingbao.pbs.common.pay.weixin.service;

import com.shouyingbao.pbs.common.pay.weixin.model.RefundReqData;
import com.shouyingbao.pbs.common.pay.weixin.util.Configure;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:04
 */
public class RefundService extends BaseService{

    public RefundService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.REFUND_API);
    }

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(RefundReqData refundReqData,Configure configure) throws Exception {
    	
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(refundReqData,configure);

        return responseString;
    }

}
