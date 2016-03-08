package com.shouyingbao.unit;

import com.google.common.base.Strings;
import com.shouyingbao.Exception.AliPayException;
import com.shouyingbao.common.pay.ali.model.AliRefundResultData;
import com.shouyingbao.common.pay.ali.util.AlipaySubmit;
import com.shouyingbao.constants.ConstantEnum;
import com.shouyingbao.constants.ConstantUtil;
import com.unionpay.acp.sdk.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付宝支付相关
 * Created by kejun on 2015/11/25.
 */
@Component
public class AliPayUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayUnit.class);


    /**
     * Description:获取支付宝支付签名
     * @param totalPrice
     * Author: 柯军
     **/




    /**
     * @param tradeNo
     * @param batchNo
     * @return
     * @Description: 查询退款状态
     * @Author: 柯军
     * @datetime:2015年8月5日上午9:34:52
     **/
    public AliRefundResultData queryRefund(String tradeNo, String batchNo) {
        LOGGER.info("查询支付宝退款状态,queryOrder tradeNo={},batchNo={}", tradeNo, batchNo);
        int status = 200;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(batchNo)) {
                throw new AliPayException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
            }
            String url = CreateRefundUrl(tradeNo, batchNo);
            HttpClient hc = new HttpClient(url, 30000, 30000);
            status = hc.send(new HashMap<String, String>(), ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
            if (status == 200)
                return xmlStringToAliRefundResultData(hc.getResult());
        } catch (AliPayException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("order query fail. status={},exception={}", status, e.getMessage());
            throw new AliPayException(ConstantEnum.EXCEPTION_ALI_QUERY_ORDER.getCodeStr(), ConstantEnum.EXCEPTION_ALI_QUERY_ORDER.getValueStr());
        }
        return null;
    }

    /**
     * @param xmlString
     * @return
     * @Description: xml 结果转换成QueryOrderParamVO对象
     * @Author: 柯军
     * @datetime:2015年8月7日上午11:17:59
     **/
    private AliRefundResultData xmlStringToAliRefundResultData(String xmlString) {
        LOGGER.info("支付宝订单查询结果数据：" + xmlString);
        AliRefundResultData aliRefundResultData = new AliRefundResultData();
        List<AliRefundResultData.ResultDetail> resultDetailList = new ArrayList<>();
        String[] resultArray = xmlString.split("&");
        aliRefundResultData.setIsSuccess(resultArray[0].endsWith("T") ? "T" : "F");
        if(resultArray.length > 1) {
            String[] details = resultArray[1].split("#");
            if(resultArray[0].endsWith("F")){
                aliRefundResultData.setErrorCode(resultArray[1].substring(11));
                return aliRefundResultData;
            }
            for (String detail : details) {
                String[] data = detail.split("\\^");
                AliRefundResultData.ResultDetail resultDetail = aliRefundResultData.new ResultDetail();
                if(data.length > 3) {
                    if(resultDetailList.isEmpty()) {
                        resultDetail.setBatchNo(data[0].substring(15));
                    }else{
                        resultDetail.setBatchNo(data[0]);
                    }
                    resultDetail.setTradeNo(data[1]);
                    resultDetail.setPrice(data[2]);
                    resultDetail.setResult(data[3]);
                    resultDetailList.add(resultDetail);
                }
            }
        }
        aliRefundResultData.setResultDetails(resultDetailList);
        LOGGER.debug("aliRefundResultData={}",aliRefundResultData);
        return aliRefundResultData;
    }

    /**
     * @param batchNo 付款单号
     * @param tradeNo    支付宝交易流水号
     * @return
     * @Description: 生成订单查询URL字符串
     * @Author: 柯军
     * @datetime:2015年8月7日上午11:20:00
     **/
    private String CreateRefundUrl(String tradeNo, String batchNo) {

        Map<String, String> params = new HashMap<>();
        params.put("service", ConstantUtil.ZhiFuBaoWebPage.QUERY_REFUND_SERVICE);
        params.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
        if (!StringUtils.isEmpty(batchNo))
            params.put("batch_no", batchNo);
        if (!StringUtils.isEmpty(tradeNo))
            params.put("trade_no", tradeNo);
        params.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
        String sign = AlipaySubmit.buildRequestMysign(params);
        String parameter = "";
        parameter = parameter + ConstantUtil.PCRefundWebPage.ALIPAY_GATEWAY_NEW;
        List<String> keys = new ArrayList<>(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String value = params.get(keys.get(i));
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            try {
                parameter = parameter + keys.get(i) + "=" + URLEncoder.encode(value, ConstantUtil.PayZhiFuBao.INPUT_CHARSET) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        parameter = parameter + "sign=" + sign + "&sign_type=" + ConstantUtil.PayZhiFuBao.SIGNTYPE;

        return parameter;

    }

    /**
     * @param outTradeNo 付款单号
     * @param tradeNo    支付宝交易流水号
     * Description: 生成订单查询URL字符串
     * Author: 柯军
     * datetime:2015年8月7日上午11:20:00
     **/
    private String CreateUrl(String tradeNo, String outTradeNo) {

        Map<String, String> params = new HashMap<>();
        params.put("service", ConstantUtil.ZhiFuBaoWebPage.QUERY_SERVICE);
        params.put("partner", ConstantUtil.PayZhiFuBao.PARTNER);
        if (!StringUtils.isEmpty(outTradeNo))
            params.put("out_trade_no", outTradeNo);
        if (!StringUtils.isEmpty(tradeNo))
            params.put("trade_no", tradeNo);
        params.put("_input_charset", ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
        String sign = AlipaySubmit.buildRequestMysign(params);
        String parameter = "";
        parameter = parameter + ConstantUtil.ZhiFuBaoWebPage.ALIPAY_QUERY_ORDER_GATEWAY;
        List<String> keys = new ArrayList<>(params.keySet());
        for(String key : keys){
            String value = params.get(key);
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            try {
                parameter = parameter + key + "=" + URLEncoder.encode(value, ConstantUtil.PayZhiFuBao.INPUT_CHARSET) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        parameter = parameter + "sign=" + sign + "&sign_type=" + ConstantUtil.PayZhiFuBao.SIGNTYPE;

        return parameter;

    }

}
