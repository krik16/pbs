package com.shouyingbao.unit;

import com.google.common.base.Strings;
import com.shouyingbao.Exception.ParamNullException;
import com.shouyingbao.Exception.TradeException;
import com.shouyingbao.Exception.WeixinException;
import com.shouyingbao.common.pay.weixin.model.*;
import com.shouyingbao.common.pay.weixin.service.*;
import com.shouyingbao.common.pay.weixin.util.Configure;
import com.shouyingbao.common.pay.weixin.util.Util;
import com.shouyingbao.constants.ConstantEnum;
import com.shouyingbao.constants.Constants;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import com.shouyingbao.pbs.vo.WeixinQueryOrderParamVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class WeixinPayUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayUnit.class);


    @Autowired
    WeixinConfigUnit weixinConfigUnit;
    /**
     * param weixin
     * return扫码刷卡支付
     * 柯军
     * 2015年9月2日下午1:32:08
     **/
    public ScanPayResData scanPay(WeixinPayVO weixinPayVO) {
        LOGGER.info("scanPay weixin", weixinPayVO.toString());
        ScanPayResData scanPayResData;
        try {
            if (Strings.isNullOrEmpty(weixinPayVO.getOrderNo()) || null == weixinPayVO.getTotalFee() || Strings.isNullOrEmpty(weixinPayVO.getBody())) {
                throw new ParamNullException();
            }
            Configure configure = weixinConfigUnit.initConfigure(weixinPayVO.getShopId(),weixinPayVO.getWeixinPayType());
            ScanPayReqData scanPayReqData = new ScanPayReqData(weixinPayVO.getAuthCode(), weixinPayVO.getBody(),"", "",
                    weixinPayVO.getOrderNo(), weixinPayVO.getTotalFee(), weixinPayVO.getDeviceInfo(),"","","","",configure);
            ScanPayService scanPayService = new ScanPayService();
            String result = scanPayService.request(scanPayReqData, configure);
            LOGGER.info("result={}",result);
            scanPayResData = (ScanPayResData) Util.getObjectFromXML(result, ScanPayResData.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_SIGN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SIGN_FAIL.getValueStr());
        }
        return scanPayResData;
    }

    /**
     *退款
     * author 柯军
     **/

    public RefundResData weixinRefund(String payNo, double refundFee, double totalFee, String newPayNo,Integer weixinMchId) {
        LOGGER.info("开始退款,weixinRefund payNo={},refundFee={},totalFee={},newPayNo={}", payNo, refundFee, totalFee, newPayNo);
        try {
            if (Strings.isNullOrEmpty(payNo) || refundFee < 0d || totalFee < refundFee || Strings.isNullOrEmpty(newPayNo)) {
                throw new ParamNullException();
            }
            RefundService refundService = new RefundService();
            BigDecimal bigTotalFee = new BigDecimal(totalFee + "").multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal bigRefundFee = new BigDecimal(refundFee + "").multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundReqData refundReqData = new RefundReqData(null, payNo, null, newPayNo, bigTotalFee.intValue(), bigRefundFee.intValue(),null,configure);
            String result = refundService.request(refundReqData,configure);
            RefundResData refundResData = (RefundResData) Util.getObjectFromXML(result, RefundResData.class);
            if (Constants.RESULT.SUCCESS.equals(refundResData.getReturn_code()) && Constants.RESULT.SUCCESS.equals(refundResData.getResult_code())) {// 退款申请成功后查询退款结果
                LOGGER.info("退款成功,refundResData={}", refundResData);
                return refundResData;
            } else {
                LOGGER.info("退款失败 result={}", result);
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), !StringUtils.isEmpty(refundResData.getErr_code_des()) ? refundResData.getErr_code_des() : ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
            }
        } catch (WeixinException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("weixin refund fail. exception={}", e.getMessage());
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
        }
    }

    /**
     * Description:验证退款查询结果是否正确
     * param:tradeNo 交易流水号
     * param:payNo 付款单号
     * param:refundNo 退款单号
     * Author: 柯军
     **/

    public void checkRefundQueryResult(String tradeNo, String payNo, String refundNo,Integer weixinMchId) {
        RefundQueryResData refundQueryResData = refundQuery(tradeNo, payNo, refundNo,weixinMchId);
        LOGGER.info("退款申请结果检查,checkRefundQueryResult refundQueryResData={}", refundQueryResData.toString());
        if (ConstantEnum.WEIXIN_REFUND_RESULT_SUCCESS.getCodeStr().equals(refundQueryResData.getRefund_status_0())
                || ConstantEnum.WEIXIN_REFUND_RESULT_PROCESSING.getCodeStr().equals(refundQueryResData.getRefund_status_0())) {// 退款成功
            LOGGER.info("退款结果检查成功");
        } else if (ConstantEnum.WEIXIN_REFUND_RESULT_NOTSURE.getCodeStr().equals(refundQueryResData.getRefund_status_0())) {
            throw new WeixinException(ConstantEnum.WEIXIN_REFUND_RESULT_NOTSURE.getCodeStr(), ConstantEnum.WEIXIN_REFUND_RESULT_NOTSURE.getValueStr());
        } else if (ConstantEnum.WEIXIN_REFUND_RESULT_CHANGE.getCodeStr().equals(refundQueryResData.getRefund_status_0())) {
            throw new WeixinException(ConstantEnum.WEIXIN_REFUND_RESULT_CHANGE.getCodeStr(), ConstantEnum.WEIXIN_REFUND_RESULT_CHANGE.getValueStr());
        } else if (ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr().equals(refundQueryResData.getRefund_status_0())) {
            throw new WeixinException(ConstantEnum.WEIXIN_REFUND_RESULT_FAIL.getCodeStr(), ConstantEnum.WEIXIN_REFUND_RESULT_CHANGE.getValueStr());
        } else {
            LOGGER.info("退款成功，但是查询结果异常，忽略查询结果，查询结果内容=" + refundQueryResData.toString());
        }
    }

    /**
     * Description: 微信退款查询
     * param:
     * Author: 柯军
     **/

    public RefundQueryResData refundQuery(String tradeNo, String payNo, String refundNo,Integer weixinMchId) {
        LOGGER.info("退款查询,refundQuery tradeNo={},payNo={},refundNo={},weixinMchId={}", tradeNo, payNo, refundNo,weixinMchId);
        RefundQueryResData refundQueryResData;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(payNo) && Strings.isNullOrEmpty(refundNo)) {
                throw new ParamNullException();
            }
            RefundQueryService refundQueryService = new RefundQueryService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundQueryReqData refundQueryReqData = new RefundQueryReqData(tradeNo, payNo, null, refundNo, null,configure);
            String result = refundQueryService.request(refundQueryReqData,configure);
            refundQueryResData = (RefundQueryResData) Util.getObjectFromXML(result, RefundQueryResData.class);
        } catch (TradeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_QUERY_ORDER.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_QUERY_ORDER.getValueStr());
        }
        return refundQueryResData;
    }

    public void closeOrder(String payNo,Integer weixinMchId) {
        LOGGER.info("关闭订单,closeOrder payNo={}", payNo);
        try {
            if (Strings.isNullOrEmpty(payNo)) {
                throw new ParamNullException();
            }
            ReverseService reverseService = new ReverseService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            ReverseReqData reverseReqData = new ReverseReqData(null, payNo,configure);
            String response = reverseService.request(reverseReqData,configure);
            if (!response.contains("CDATA[SUCCESS]")) {
                LOGGER.error("closeOrder fail. response={}", response);
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getValueStr());
            }
        } catch (WeixinException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getValueStr());
        }
    }

    /**
     * Description:订单查询
     * param:
     * Author: 柯军
     **/

    public WeixinQueryOrderParamVO queryOrder(String tradeNo, String payNo,Integer weixinMchId) {
        LOGGER.info("订单查询,queryOrder tradeNo={},payNo={},weixinMchId={}", tradeNo, payNo,weixinMchId);
        String result = null;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(payNo)) {
                throw new ParamNullException();
            }
            PayQueryService payQueryService = new PayQueryService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(tradeNo, payNo,configure);
            result = payQueryService.request(scanPayQueryReqData,configure);
            return (WeixinQueryOrderParamVO) Util.getObjectFromXML(result, WeixinQueryOrderParamVO.class);
        } catch (Exception e) {
            LOGGER.info("微信订单查询失败，result={}", result);
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_QUERY_ORDER.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_QUERY_ORDER.getValueStr());
        }
    }

}
