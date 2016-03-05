package com.rongyi.rpb.unit;

import com.google.common.base.Strings;
import com.rongyi.core.bean.ObjectConvert;
import com.rongyi.easy.rpb.vo.PaymentEntityVO;
import com.rongyi.easy.rpb.vo.QueryOrderParamVO;
import com.rongyi.rpb.Exception.AliPayException;
import com.rongyi.rpb.common.pay.ali.model.AliRefundResultData;
import com.rongyi.rpb.common.pay.ali.sign.RSA;
import com.rongyi.rpb.common.pay.ali.util.AlipaySubmit;
import com.rongyi.rpb.common.pay.weixin.util.XMLUtil;
import com.rongyi.rpb.constants.ConstantEnum;
import com.rongyi.rpb.constants.ConstantUtil;
import com.unionpay.acp.sdk.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TimeExpireUnit timeExpireUnit;

    /**
     * Description:获取支付宝支付签名
     * @param totalPrice
     * Author: 柯军
     **/

    public Map<String, Object> getPaySign(String totalPrice, PaymentEntityVO paymentEntityVO, String title) {
        LOGGER.info("获取支付宝签名,getPaySign totalPrice={},payNo={},title={}", totalPrice, paymentEntityVO.getPayNo(), title);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (Strings.isNullOrEmpty(totalPrice) || Strings.isNullOrEmpty(paymentEntityVO.getPayNo()) || Strings.isNullOrEmpty(title)) {
                throw new AliPayException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
            }
            //获取支付失效时长
            String itBPay = timeExpireUnit.aliPayTimeExpire(paymentEntityVO.getTimeStart(), paymentEntityVO.getTimeExpire(), paymentEntityVO.getOrderType());
            //获取需加密的签名串
            String signContent = assembleSign(paymentEntityVO.getPayNo(), title, totalPrice, itBPay);
            String rsaSign = RSA.sign(signContent, ConstantUtil.PayZhiFuBao.PRIVATE_KEY, ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
            if(rsaSign == null)
                throw new AliPayException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
            String zhifubaoSign = signContent + "&sign=\"" + URLEncoder.encode(rsaSign, "utf-8") + "\"&sign_type=\"RSA\"";
            resultMap.put("code", 0);
            resultMap.put("zhifubaoSign", zhifubaoSign);
            resultMap.put("title", title);
            resultMap.put("totlePrice", totalPrice);
        } catch (AliPayException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AliPayException(ConstantEnum.EXCEPTION_ALI_PAY_SIGN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_ALI_PAY_SIGN_FAIL.getValueStr());
        }
        return resultMap;
    }

    /**
     * Description:组装签名串
     * Author: 柯军
     **/

    private String assembleSign(String orderId, String title, String totlePrice, String itBPay) {
        StringBuilder sb = new StringBuilder();
        sb.append("partner=\"");
        sb.append(ConstantUtil.PayZhiFuBao.PARTNER);
        sb.append("\"&out_trade_no=\"");
        sb.append(orderId); // orderId
        sb.append("\"&subject=\"");
        sb.append(title); // title
        sb.append("\"&body=\"容易网商品");
        sb.append("");// title
        sb.append("\"&total_fee=\"");
        sb.append(totlePrice); // totlePrice
        sb.append("\"&notify_url=\"");
        try {
//			sb.append(URLEncoder.encode(ConstantUtil.PayNetAdress.NOTIFY_URL_ZHIFUBAO_APP, "utf-8"));
            sb.append(ConstantUtil.PayNetAdress.NOTIFY_URL_ZHIFUBAO_APP);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        // 网址需要做URL编码
        sb.append("\"&service=\"");
        sb.append(ConstantUtil.PayZhiFuBao.SDK_SERVICE);
        sb.append("\"&_input_charset=\"utf-8");
        sb.append("\"&payment_type=\"1");
        sb.append("\"&seller_id=\"");
        sb.append(ConstantUtil.PayZhiFuBao.SELLER_ID);

        sb.append("\"&it_b_pay=\"");
        //支付失效时长
        sb.append(itBPay);
        sb.append("\"");

        return new String(sb);
    }

    /**
     * @param payNo
     * Description: 查询订单状态
     * Author: 柯军
     * datetime:2015年8月5日上午9:34:52
     **/
    public QueryOrderParamVO queryOrder(String tradeNo, String payNo) {
        LOGGER.info("查询支付宝订单状态,queryOrder tradeNo={},payNo={}", tradeNo, payNo);
        int status = 200;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(payNo)) {
                throw new AliPayException(ConstantEnum.EXCEPTION_PARAM_NULL.getCodeStr(), ConstantEnum.EXCEPTION_PARAM_NULL.getValueStr());
            }
            String url = CreateUrl(tradeNo, payNo);
            HttpClient hc = new HttpClient(url, 30000, 30000);
            status = hc.send(new HashMap<String, String>(), ConstantUtil.PayZhiFuBao.INPUT_CHARSET);
            if (status == 200)
                return xmlStringToQueryOrderParamVO(hc.getResult());
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
     * return
     * Description: xml 结果转换成QueryOrderParamVO对象
     * Author: 柯军
     * datetime:2015年8月7日上午11:17:59
     **/
    @SuppressWarnings("unchecked")
    private QueryOrderParamVO xmlStringToQueryOrderParamVO(String xmlString) {
        LOGGER.info("支付宝订单查询结果数据：" + xmlString);
        QueryOrderParamVO queryOrderParamVO = new QueryOrderParamVO();
        try {
            Map<String, Object> xmlMap = XMLUtil.doXMLParse(xmlString);
            if(xmlMap != null) {
                if ("T".equals(xmlMap.get("is_success"))) {
                    String responseStr = xmlMap.get("response").toString();
                    Map<String, Object> responseMap = XMLUtil.doXMLParse(responseStr);
                    queryOrderParamVO = (QueryOrderParamVO) ObjectConvert.convertFromMap(QueryOrderParamVO.class, responseMap);
                    queryOrderParamVO.setIs_success("T");
                } else {
                    queryOrderParamVO.setIs_success("F");
                    queryOrderParamVO.setError(ConstantEnum.ALI_QUERY_ORDER_ERROR_CODE.getValueStr());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryOrderParamVO;
    }


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
