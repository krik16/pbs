<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp" %>
<head>
</head>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <div class="panel panel-default">
                <div class="panel-heading">
                    交易详情
                </div>
                <div class="panel-body">
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>订单号： ${entity.orderNo}</span>

                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易单号：${entity.tradeNo}</span>

                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易金额： ${entity.payAmount/100} 元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易渠道： ${entity.payChannel == 0 ?'支付宝':'微信'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易方式：
                                    <c:choose>
                                        <c:when test="${entity.payType == 0}">
                                            支付宝扫码支付
                                        </c:when>
                                        <c:when test="${entity.payType == 1}">
                                            微信扫码支付
                                        </c:when>
                                        <c:when test="${entity.payType == 2}">
                                            支付宝扫固码支付
                                        </c:when>
                                        <c:otherwise>
                                            微信扫固码支付
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易类型： ${entity.tradeType==0 ?'收款':'退款'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易开始时间：<fmt:formatDate value="${entity.createAt}"
                                                             pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>交易结束时间： <fmt:formatDate value="${entity.finishAt}"
                                                              pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>所属区域： ${entity.areaName}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span> 所属代理： ${entity.agentName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>公司： ${entity.companyName}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span> 门店： ${entity.shopName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-8">
                            <div class="input-group-addon input-group-onlytext">
                                <a class="btn btn-danger" style="width: 90px;" id="close-button"
                                   href="javascript:window.top.close()">
                                    <i class="fa fa-reply"></i>
                                    <span class="btn-text">关闭</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
