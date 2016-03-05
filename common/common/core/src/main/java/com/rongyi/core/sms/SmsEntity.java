package com.rongyi.core.sms;

import java.io.Serializable;

/*
 * Copyright (C),上海容易网电子商务有限公司
 * Author:  俞志坚
 * Description:  
 * time:  2015/7/22
 * History: 变更记录
 * <author>           <time>             <version>        <desc>
 * 俞志坚             2015/7/22              1.0            创建文件
 *
 */
public class SmsEntity implements Serializable{

    private static final long serialVersionUID = 42L;

    private String returnsms;
    private String returnstatus;
    private String message;
    private String remainpoint;
    private String taskID;
    private String successCounts;
    private String payinfo;
    private String overage;
    private String sendTotal;
    private String statusbox;
    private String mobile;
    private String status;
    private String receivetime;
    private String errorstatus;
    private String error;
    private String remark;
    private String callbox;
    private String taskid;
    private String content;

    public String getReturnsms() {
        return returnsms;
    }

    public void setReturnsms(String returnsms) {
        this.returnsms = returnsms;
    }

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemainpoint() {
        return remainpoint;
    }

    public void setRemainpoint(String remainpoint) {
        this.remainpoint = remainpoint;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(String successCounts) {
        this.successCounts = successCounts;
    }

    public String getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(String payinfo) {
        this.payinfo = payinfo;
    }

    public String getOverage() {
        return overage;
    }

    public void setOverage(String overage) {
        this.overage = overage;
    }

    public String getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(String sendTotal) {
        this.sendTotal = sendTotal;
    }

    public String getStatusbox() {
        return statusbox;
    }

    public void setStatusbox(String statusbox) {
        this.statusbox = statusbox;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getErrorstatus() {
        return errorstatus;
    }

    public void setErrorstatus(String errorstatus) {
        this.errorstatus = errorstatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCallbox() {
        return callbox;
    }

    public void setCallbox(String callbox) {
        this.callbox = callbox;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
