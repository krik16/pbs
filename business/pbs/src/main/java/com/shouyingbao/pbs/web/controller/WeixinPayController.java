package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.Exception.ParamNullException;
import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.service.AuthorityService;
import com.shouyingbao.pbs.service.PaymentBillService;
import com.shouyingbao.pbs.vo.WeixinScanPayParam;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kejun
 * 2016/3/8 17:34
 **/

@Controller
@RequestMapping("/pay/weixin")
public class WeixinPayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinPayController.class);
    @Autowired
    PaymentBillService paymentBillService;

    @Autowired
    AuthorityService authorityService;


    /**
     * 扫码支付
     *
     * @param weixinScanPayParam 请求参数
     * @return ResponseData 结果
     */
    @RequestMapping("/scanPay")
    @ResponseBody
    public ResponseData scanPay(@RequestBody WeixinScanPayParam weixinScanPayParam) {
        LOGGER.info("微信扫码支付:weixinScanPayParam={}", weixinScanPayParam);
        ResponseData responseData;
        try {
            if (weixinScanPayParam.getUserId() == null || StringUtils.isBlank(weixinScanPayParam.getAuthCode()) || weixinScanPayParam.getTotalFee() == null) {
                LOGGER.error("参数为空或不合法");
                throw new ParamNullException();
            }
            responseData = paymentBillService.weixinScanPay(weixinScanPayParam.getUserId(), weixinScanPayParam.getAuthCode(), weixinScanPayParam.getTotalFee(), weixinScanPayParam.getDeviceInfo(), 0);
        } catch (ParamNullException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_SCAN_FAIL.getValueStr());
        }
        return responseData;
    }

    /**
     * 扫码退款
     *
     * @param weixinScanPayParam 请求参数
     */
    @RequestMapping("/scanRefund")
    @ResponseBody
    public ResponseData scanRefund(@RequestBody WeixinScanPayParam weixinScanPayParam) {
        ResponseData responseData;
        try {
            if (weixinScanPayParam.getUserId() == null || StringUtils.isBlank(weixinScanPayParam.getOrderNo())) {
                LOGGER.error("参数为空或不合法");
                throw new ParamNullException();
            }
            boolean result = authorityService.checkAuthority(ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr(), weixinScanPayParam.getUserId());
            if (result) {
                responseData = paymentBillService.weixinScanRefund(weixinScanPayParam.getOrderNo(), weixinScanPayParam.getUserId());
            } else {
                return ResponseData.failure(ConstantEnum.NO_REFUND_AUTHORITY.getCodeStr(), ConstantEnum.NO_REFUND_AUTHORITY.getValueStr());
            }

        } catch (ParamNullException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            responseData = ResponseData.failure(ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getCodeStr(), ConstantEnum.EXCEPTION_WEIXIN_REFUND_FAIL.getValueStr());
        }
        return responseData;
    }

    /**
     * 固定二维码扫码结果通知
     **/
    @RequestMapping("/scanFixed")
    public void weixinNotify(HttpServletRequest request) {
        LOGGER.info("scanFixed.....");
        Map<String, Object> requestMap = parseXml(request);
        LOGGER.info("requestMap={}", requestMap);
    }

    /**
     * 解析微信异步通知中的xml元素值
     **/
    private static Map<String, Object> parseXml(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList)
                map.put(e.getName(), e.getText());
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error("解析微信返回结果xml失败");
            e.printStackTrace();
        }
        return map;
    }

}
