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
import com.shouyingbao.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import com.shouyingbao.service.WeixinMchService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class WeixinPayUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayUnit.class);

    private int retryTimes = 3;

    private long retryInterval = 10000;

    @Autowired
    WeixinConfigUnit weixinConfigUnit;

    @Autowired
    WeixinMchService weixinMchService;

    /**
     * param weixin
     * return扫码刷卡支付
     * 柯军
     * 2015年9月2日下午1:32:08
     **/
    public ResponseData scanPay(WeixinPayVO weixinPayVO) {
        LOGGER.info("scanPay weixin", weixinPayVO.toString());
        ScanPayResData scanPayResData;
        ResponseData responseData = ResponseData.success();
        try {
            if (Strings.isNullOrEmpty(weixinPayVO.getOrderNo()) || null == weixinPayVO.getTotalFee() || Strings.isNullOrEmpty(weixinPayVO.getBody())) {
                throw new ParamNullException();
            }
            WeixinMch weixinMch = weixinMchService.selectByShopId(weixinPayVO.getShopId());
            if (weixinMch == null) {
                throw new WeixinException(ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getCodeStr(), ConstantEnum.EXCEPTION_MCH_NOT_EXIST.getValueStr());
            }
            Configure configure = weixinConfigUnit.initConfigure(weixinMch, weixinPayVO.getWeixinPayType());
            ScanPayReqData scanPayReqData = new ScanPayReqData(weixinPayVO.getAuthCode(), weixinPayVO.getBody(), "", "",
                    weixinPayVO.getOrderNo(), weixinPayVO.getTotalFee(), weixinPayVO.getDeviceInfo(), "", "", "", "", configure);
            ScanPayService scanPayService = new ScanPayService();
            String result = scanPayService.request(scanPayReqData, configure);
            scanPayResData = (ScanPayResData) Util.getObjectFromXML(result, ScanPayResData.class);
            LOGGER.info("scanPayResData={}", scanPayResData);
            if ("FAIL".equals(scanPayResData.getReturn_code())) {//通信错误
                responseData = ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
            } else if ("FAIL".equals(scanPayResData.getResult_code())) {//业务错误
                if ("USERPAYING".equals(scanPayResData.getErr_code())) {
                    LOGGER.info("用户正在输入密码，等待中...");
                    waitUserPaying(weixinPayVO.getOrderNo(), weixinMch.getId());
                }else {
                    responseData = ResponseData.failure(scanPayResData.getErr_code(), scanPayResData.getErr_code_des());
                }
            }
        }catch (WeixinException e){
            throw  e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
        }
        return responseData;
    }

    private void waitUserPaying(String orderNo, Integer weixinMchId) {
        boolean result = false;//支付结果
        for (int i = 0; i < this.retryTimes; i++) {
            try {
                ScanQueryResData scanQueryResData = scanPayQueryOrder(null, orderNo, weixinMchId);
                LOGGER.info("scanQueryResData={}", scanQueryResData);
                if ("SUCCESS".equals(scanQueryResData.getReturn_code()) && "SUCCESS".equals(scanQueryResData.getResult_code()) && !"USERPAYING".equals(scanQueryResData.getTrade_state())) {
                    LOGGER.info("用户密码输入完成，成功支付");
                    result = true;
                    break;
                }
                Thread.sleep(this.retryInterval);
            } catch (Exception e) {
                e.printStackTrace();
                if (i == this.retryTimes - 1) {
                    LOGGER.error("query order failed after retry {} times!", this.retryTimes);
                    break;
                }
                try {
                    Thread.sleep(this.retryInterval);
                } catch (InterruptedException e1) {
                    LOGGER.warn("query order process has been blocked. Wait {} seconds and retry {}", this.retryInterval / 1000, i + 1);
                }
            }
        }
        if (!result) {
            LOGGER.info("用户超时未完成支付,调用撤销订单,用户需重新支付");
            reverseOrder(orderNo, weixinMchId);
            throw new WeixinException(ConstantEnum.EXCEPTION_USER_UN_PAY.getCodeStr(), ConstantEnum.EXCEPTION_USER_UN_PAY.getValueStr());
        }
    }

    /**
     * 退款
     * author 柯军
     **/

    public RefundResData weixinRefund(String payNo, double refundFee, double totalFee, String newPayNo, Integer weixinMchId) {
        LOGGER.info("开始退款,weixinRefund payNo={},refundFee={},totalFee={},newPayNo={}", payNo, refundFee, totalFee, newPayNo);
        try {
            if (Strings.isNullOrEmpty(payNo) || refundFee < 0d || totalFee < refundFee || Strings.isNullOrEmpty(newPayNo)) {
                throw new ParamNullException();
            }
            RefundService refundService = new RefundService();
            BigDecimal bigTotalFee = new BigDecimal(totalFee + "").multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal bigRefundFee = new BigDecimal(refundFee + "").multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundReqData refundReqData = new RefundReqData(null, payNo, null, newPayNo, bigTotalFee.intValue(), bigRefundFee.intValue(), null, configure);
            String result = refundService.request(refundReqData, configure);
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

    public void checkRefundQueryResult(String tradeNo, String payNo, String refundNo, Integer weixinMchId) {
        RefundQueryResData refundQueryResData = refundQuery(tradeNo, payNo, refundNo, weixinMchId);
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

    public RefundQueryResData refundQuery(String tradeNo, String payNo, String refundNo, Integer weixinMchId) {
        LOGGER.info("退款查询,refundQuery tradeNo={},payNo={},refundNo={},weixinMchId={}", tradeNo, payNo, refundNo, weixinMchId);
        RefundQueryResData refundQueryResData;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(payNo) && Strings.isNullOrEmpty(refundNo)) {
                throw new ParamNullException();
            }
            RefundQueryService refundQueryService = new RefundQueryService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundQueryReqData refundQueryReqData = new RefundQueryReqData(tradeNo, payNo, null, refundNo, null, configure);
            String result = refundQueryService.request(refundQueryReqData, configure);
            refundQueryResData = (RefundQueryResData) Util.getObjectFromXML(result, RefundQueryResData.class);
        } catch (TradeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_QUERY_ORDER.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_QUERY_ORDER.getValueStr());
        }
        return refundQueryResData;
    }

    /**
     * 撤销订单
     *
     * @param orderNo
     * @param weixinMchId
     */
    public void reverseOrder(String orderNo, Integer weixinMchId) {
        LOGGER.info("关闭订单,closeOrder orderNo={}", orderNo);
        try {
            if (Strings.isNullOrEmpty(orderNo)) {
                throw new ParamNullException();
            }
            ReverseService reverseService = new ReverseService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            ReverseReqData reverseReqData = new ReverseReqData(null, orderNo, configure);
            String response = reverseService.request(reverseReqData, configure);
            if (!response.contains("CDATA[SUCCESS]")) {
                LOGGER.error("reverseOrder fail. response={}", response);
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
     * Description:扫码支付订单查询
     * param:
     * Author: 柯军
     **/

    public ScanQueryResData scanPayQueryOrder(String tradeNo, String orderNo, Integer weixinMchId) {
        LOGGER.info("订单查询,queryOrder tradeNo={},orderNo={},weixinMchId={}", tradeNo, orderNo, weixinMchId);
        String result = null;
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(orderNo)) {
                throw new ParamNullException();
            }
            PayQueryService payQueryService = new PayQueryService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(tradeNo, orderNo, configure);
            result = payQueryService.request(scanPayQueryReqData, configure);
            LOGGER.debug("result={}", result);
            return (ScanQueryResData) Util.getObjectFromXML(result, ScanQueryResData.class);
        } catch (Exception e) {
            LOGGER.info("微信订单查询失败，result={}", result);
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_QUERY_ORDER.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_QUERY_ORDER.getValueStr());
        }
    }

}
