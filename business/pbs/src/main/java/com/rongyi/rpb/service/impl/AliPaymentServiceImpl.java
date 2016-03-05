package com.rongyi.rpb.service.impl;

import com.rongyi.core.framework.mybatis.service.impl.BaseServiceImpl;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.easy.rpb.vo.QueryOrderParamVO;
import com.rongyi.rpb.Exception.AliPayException;
import com.rongyi.rpb.service.AliPaymentService;
import com.rongyi.rpb.service.PaymentService;
import com.rongyi.rpb.unit.AliPayUnit;
import com.rongyi.rpb.unit.TimeExpireUnit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: 柯军
 * Description: 支付宝Service
 * datetime:2015年4月23日上午9:51:11
 **/
@Service
public class AliPaymentServiceImpl extends BaseServiceImpl implements AliPaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPaymentServiceImpl.class);
    @Autowired
    PaymentService paymentService;

    @Autowired
    TimeExpireUnit timeExpireUnit;

    @Autowired
    AliPayUnit aliPayUnit;

    /**
     * Description:获取支付签名
     * @param orderMaps
     * Author: 柯军
     **/

    @Override
    public Map<String, Object> getZhiFuBaoSign(Map<String, Object> orderMaps, PaymentEntityVO paymentEntityVO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String title = orderMaps.get("title").toString();
            if (StringUtils.isEmpty(title))
                title = paymentService.getTitle(paymentEntityVO.getPayNo());
            resultMap = aliPayUnit.getPaySign(orderMaps.get("totalPrice").toString(), paymentEntityVO, title);
        } catch (AliPayException e) {
            resultMap.put("code", e.getCode());
            resultMap.put("message", e.getMessage());
        } catch (Exception e) {
            resultMap.put("code", 1);// 生成失败
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * @param payNo
     * return
     * Description: 查询订单状态
     * Author: 柯军
     * datetime:2015年8月5日上午9:34:52
     **/
    @Override
    public QueryOrderParamVO queryOrder(String tradeNo, String payNo) {
        return aliPayUnit.queryOrder(tradeNo, payNo);
    }

}
