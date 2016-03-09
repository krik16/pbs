package com.shouyingbao.pbs.unit;

import com.google.common.base.Strings;
import com.shouyingbao.pbs.Exception.ParamNullException;
import com.shouyingbao.pbs.Exception.WeixinException;
import com.shouyingbao.pbs.common.pay.weixin.model.*;
import com.shouyingbao.pbs.common.pay.weixin.service.*;
import com.shouyingbao.pbs.common.pay.weixin.util.Configure;
import com.shouyingbao.pbs.common.pay.weixin.util.Util;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.constants.Constants;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.WeixinMch;
import com.shouyingbao.pbs.service.WeixinMchService;
import com.shouyingbao.pbs.vo.WeixinPayVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WeixinPayUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayUnit.class);

    private int retryTimes = 3;

    private long retryInterval = 5000;

    private boolean needRecallReverse = false;

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
    public ResponseData scanPay(WeixinPayVO weixinPayVO, WeixinMch weixinMch) {
        LOGGER.info("微信扫码支付", weixinPayVO.toString());
        ScanPayResData scanPayResData;
        ResponseData responseData = ResponseData.success();
        try {
            if (Strings.isNullOrEmpty(weixinPayVO.getOrderNo()) || null == weixinPayVO.getTotalFee() || Strings.isNullOrEmpty(weixinPayVO.getBody())) {
                throw new ParamNullException();
            }
            //初始化配置信息
            Configure configure = weixinConfigUnit.initConfigure(weixinMch, weixinPayVO.getWeixinPayType());
            //封装请求参数
            ScanPayReqData scanPayReqData = new ScanPayReqData(weixinPayVO.getAuthCode(), weixinPayVO.getBody(), "", "",
                    weixinPayVO.getOrderNo(), weixinPayVO.getTotalFee(), weixinPayVO.getDeviceInfo(), "", "", "", "", configure);
            ScanPayService scanPayService = new ScanPayService();
            //发起支付请求
            String result = scanPayService.request(scanPayReqData, configure);
            LOGGER.debug("result={}", result);
            //处理支付结果
            scanPayResData = (ScanPayResData) Util.getObjectFromXML(result, ScanPayResData.class);
            LOGGER.info("scanPayResData={}", scanPayResData);
            if ("FAIL".equals(scanPayResData.getReturn_code())) {//通信错误
                responseData = ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
            } else if ("FAIL".equals(scanPayResData.getResult_code())) {//业务错误
                if ("USERPAYING".equals(scanPayResData.getErr_code())) {
                    LOGGER.info("用户正在输入密码，等待中...");
                    waitUserPaying(weixinPayVO.getOrderNo(), weixinMch.getId());
                } else {
                    responseData = ResponseData.failure(scanPayResData.getErr_code(), scanPayResData.getErr_code_des());
                    reverseOrder(weixinPayVO.getOrderNo(), weixinMch.getId());
                }
            }
        } catch (WeixinException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
        }
        return responseData;
    }


    /**
     * 扫码支付等待用户支付处理
     * @param orderNo
     * @param weixinMchId
     */
    private void waitUserPaying(String orderNo, Integer weixinMchId) {
        boolean result = false;//支付结果
        for (int i = 0; i < this.retryTimes; i++) {
            try {
                ScanQueryResData scanQueryResData = scanPayQueryOrder(null, orderNo, weixinMchId);
                LOGGER.info("scanQueryResData={}", scanQueryResData);
                if ("SUCCESS".equals(scanQueryResData.getReturn_code()) && "SUCCESS".equals(scanQueryResData.getResult_code()) && "SUCCESS".equals(scanQueryResData.getTrade_state())) {
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

    public ResponseData weixinRefund(String orderNo, Integer refundFee, Integer totalFee, String refundNo, Integer weixinMchId) {
        LOGGER.info("开始退款,weixinRefund orderNo={},refundFee={},totalFee={},newPayNo={}", orderNo, refundFee, totalFee, refundNo);
        try {
            if (Strings.isNullOrEmpty(orderNo) || refundFee < 0d || totalFee < refundFee || Strings.isNullOrEmpty(refundNo)) {
                throw new ParamNullException();
            }
            RefundService refundService = new RefundService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundReqData refundReqData = new RefundReqData(null, orderNo, null, refundNo, refundFee, totalFee, null, configure);
            String result = refundService.request(refundReqData, configure);
            RefundResData refundResData = (RefundResData) Util.getObjectFromXML(result, RefundResData.class);
            if (Constants.RESULT.SUCCESS.equals(refundResData.getReturn_code()) && Constants.RESULT.SUCCESS.equals(refundResData.getResult_code())) {// 退款申请成功后查询退款结果
                LOGGER.info("退款成功,refundResData={}", refundResData);
                return ResponseData.success();
            } else {
                LOGGER.info("退款失败 result={}", result);
                throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), !StringUtils.isEmpty(refundResData.getErr_code_des()) ? refundResData.getErr_code_des() : ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
            }
        } catch (WeixinException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("weixin refund fail. exception={}", e.getMessage());
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
        }
    }


    /**
     * Description: 微信退款查询
     * param:
     * Author: 柯军
     **/

    public RefundQueryResData refundQuery(String tradeNo, String orderNo, String refundNo, Integer weixinMchId) {
        LOGGER.info("退款查询,refundQuery tradeNo={},orderNo={},refundNo={},weixinMchId={}", tradeNo, orderNo, refundNo, weixinMchId);
        RefundQueryResData refundQueryResData;
        String result="";
        try {
            if (Strings.isNullOrEmpty(tradeNo) && Strings.isNullOrEmpty(orderNo) && Strings.isNullOrEmpty(refundNo)) {
                throw new ParamNullException();
            }
            RefundQueryService refundQueryService = new RefundQueryService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            RefundQueryReqData refundQueryReqData = new RefundQueryReqData(tradeNo, orderNo, null, refundNo, null, configure);
            result = refundQueryService.request(refundQueryReqData, configure);
             refundQueryResData = (RefundQueryResData) Util.getObjectFromXML(result, RefundQueryResData.class);
        } catch (Exception e) {
            LOGGER.info("查询结果result={}",result);
            LOGGER.error(e.getMessage());
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

        LOGGER.info("撤销订单开始,reverseOrder orderNo={}", orderNo);
        try {
            Thread.sleep(this.retryInterval);
            if (Strings.isNullOrEmpty(orderNo)) {
                throw new ParamNullException();
            }
            ReverseService reverseService = new ReverseService();
            Configure configure = weixinConfigUnit.initConfigure(weixinMchId);
            ReverseReqData reverseReqData = new ReverseReqData(null, orderNo, configure);
            String response = reverseService.request(reverseReqData, configure);
            ReverseResData reverseResData = (ReverseResData) Util.getObjectFromXML(response, ReverseResData.class);
            if (!"SUCCESS".equals(reverseResData.getReturn_code()) || !"SUCCESS".equals(reverseResData.getResult_code()) || !"SUCCESS".equals(reverseResData.getTrade_state()))
            {
                LOGGER.error("撤销订单失败，response={}", response);
            }
        } catch (WeixinException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinException(ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_ORDER_CLOSE.getValueStr());
        }
        LOGGER.info("撤销订单结束,reverseOrder orderNo={}", orderNo);
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
