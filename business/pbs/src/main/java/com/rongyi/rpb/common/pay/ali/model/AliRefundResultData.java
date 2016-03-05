package com.rongyi.rpb.common.pay.ali.model;

import java.util.List;

/**
 * @Copyright (C), 上海容易网电子商务有限公司
 * kejun
 * 2016/1/21 14:09
 **/
public class AliRefundResultData {

    /**
     *T 成功
     *F 失败
     */
    private String isSuccess;

    private List<ResultDetail> resultDetails;

    private String errorCode;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<ResultDetail> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetail> resultDetails) {
        this.resultDetails = resultDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public class  ResultDetail{
        /**
         *  批次号
         **/
        private String batchNo;

        /**
         * 交易流水号
         */
        private String tradeNo;

        /**
         * 价格
         */
        private String price;

        /**
         * 退款结果
         */
        private String result;

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ResultDetail{" +
                    "batchNo='" + batchNo + '\'' +
                    ", tradeNo='" + tradeNo + '\'' +
                    ", price='" + price + '\'' +
                    ", result='" + result + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AliRefundResultData{" +
                "isSuccess='" + isSuccess + '\'' +
                ", resultDetails=" + resultDetails +
                ", errorCode=" + errorCode +
                '}';
    }
}

