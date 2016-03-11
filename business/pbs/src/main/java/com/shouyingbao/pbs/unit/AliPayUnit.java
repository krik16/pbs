package com.shouyingbao.pbs.unit;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.shouyingbao.pbs.common.pay.ali.scan.model.builder.AlipayTradePayContentBuilder;
import com.shouyingbao.pbs.common.pay.ali.scan.model.builder.AlipayTradeRefundContentBuilder;
import com.shouyingbao.pbs.common.pay.ali.scan.model.result.AlipayF2FPayResult;
import com.shouyingbao.pbs.common.pay.ali.scan.model.result.AlipayF2FQueryResult;
import com.shouyingbao.pbs.common.pay.ali.scan.model.result.AlipayF2FRefundResult;
import com.shouyingbao.pbs.common.pay.ali.scan.service.AlipayTradeService;
import com.shouyingbao.pbs.common.pay.ali.scan.service.impl.AlipayTradeServiceImpl;
import com.shouyingbao.pbs.common.pay.ali.scan.utils.Utils;
import com.shouyingbao.pbs.core.bean.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付相关
 * Created by kejun on 2016/3/11.
 */
@Component
public class AliPayUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayUnit.class);

    @Autowired
    IdGenUnit idGenUnit;

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的alipayrisk10.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
//        Configs.init("zfbtest19.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

    }

    /**
     *支付宝扫码支付
     * @param subject  订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
     * @param totalAmount  订单总金额，单位为元，不能超过1亿元
     * @param authCode  (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
     * @param sellerId  卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)  如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
     * @param body 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
     * @param operatorId 商户操作员编号，添加此参数可以为商户操作员做销售统计
     * @param storeId (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
     * @param timeExpress 支付超时，线下扫码交易定义为5分钟
     */
    public ResponseData scanPay(String subject,Double totalAmount,String authCode,String sellerId,String body,String operatorId,String storeId,String timeExpress) {
        ResponseData responseData;
        String orderNo = idGenUnit.getOrderNo("0");
        // 创建请求builder，设置请求参数
        AlipayTradePayContentBuilder builder = new AlipayTradePayContentBuilder()
                .setOutTradeNo(orderNo)
                .setSubject(subject)
                .setAuthCode(authCode)
                .setTotalAmount(totalAmount.toString())
                .setStoreId(storeId)
//                .setUndiscountableAmount(undiscountableAmount)
                .setBody(body)
                .setOperatorId(operatorId)
//                .setExtendParams(extendParams)
                .setSellerId(sellerId)
//                .setGoodsDetailList(goodsDetailList)
                .setTimeExpress(timeExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                LOGGER.info("支付宝支付成功");
                responseData = ResponseData.success();
                break;
            case FAILED:
                LOGGER.error("支付宝支付失败");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;

            case UNKNOWN:
                LOGGER.error("系统异常，订单状态未知!!!");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;

            default:
                LOGGER.error("不支持的交易状态，交易返回异常!!!");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;
        }
        return responseData;
    }

//     测试当面付2.0查询订单
    public void scanPayQuery(String orderNo) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态

        AlipayF2FQueryResult result = tradeService.queryTradeResult(orderNo);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                LOGGER.info("查询返回该订单支付成功: )");
                AlipayTradeQueryResponse response = result.getResponse();
                LOGGER.info("交易结果查询tradeStatus={}",response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        LOGGER.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;
            case TRADE_CLOSED:
                LOGGER.info("查询返回该订单状态关闭!!!");
            case FAILED:
                LOGGER.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                LOGGER.error("系统异常，订单支付状态未知!!!");
                break;
            default:
                LOGGER.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

    /**
     *
     * @param orderNo (必填) 外部订单号，需要退款交易的商户外部订单号
     * @param refundAmount (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
     * @param refundReason  (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
     * @param storeId (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
     */
    public ResponseData scanPayRefund(String orderNo,Double refundAmount,String refundReason,String storeId) {
        ResponseData responseData;
        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "";

        AlipayTradeRefundContentBuilder builder = new AlipayTradeRefundContentBuilder()
                .setOutTradeNo(orderNo)
                .setRefundAmount(refundAmount.toString())
                .setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo)
                .setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                LOGGER.info("支付宝退款成功: )");
                responseData = ResponseData.success();
                break;

            case FAILED:
                LOGGER.error("支付宝退款失败!!!");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;

            case UNKNOWN:
                LOGGER.error("系统异常，订单退款状态未知!!!");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;

            default:
                LOGGER.error("不支持的交易状态，交易返回异常!!!");
                responseData = ResponseData.failure(result.getResponse().getCode(),result.getResponse().getSubMsg());
                break;
        }
        return responseData;
    }

}
