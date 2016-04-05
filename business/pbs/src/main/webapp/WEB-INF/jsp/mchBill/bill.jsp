<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="${ctx}/js/mchBill/bill.js" type="text/javascript"></script>
    <link href="${ctx}/css/jquery.timepicker.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/jquery-ui.css" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/js/jquery/jquery-ui.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery.jtimepicker.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>

</head>
<body>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <div class="panel panel-default">
                <div class="panel-heading">
                    交易查询
                </div>
                <div class="panel-body">
                    <div class="form-group row mb15">
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">订单号：</span>
                                <input id="orderNo" type="text" class="form-control dropdown-toggle ng-pristine ng-valid"
                                       placeholder="订单号">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">交易号：</span>
                                <input id="tradeNo" type="text"
                                       class="form-control dropdown-toggle ng-pristine ng-valid"
                                       placeholder="交易流水号">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">收款渠道：</span>
                                <select class="form-control" id="payChannel">
                                    <option value="-1">选择渠道</option>
                                    <option value="0">支付宝</option>
                                    <option value="1">微信</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">交易类型：</span>
                                <select class="form-control" id="tradeType">
                                    <option value="-1">选择类型</option>
                                    <option value="0">收款</option>
                                    <option value="1">退款</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">起始时间：</span>
                                <input name="start" type="text" value=""
                                       class="form-control dropdown-toggle ng-pristine ng-valid startTime"
                                       id="tradeStartTime"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">结束时间：</span>
                                <input name="end" type="text" value=""
                                       class="form-control dropdown-toggle ng-pristine ng-valid endTime" id="tradeEndTime"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">门店：</span>
                                <select class="form-control" id="shopId">
                                    <option value="0">选择门店</option>
                                    <c:forEach items="${shopList}" var="item">
                                        <option value="${item.id}">${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-primary" style="width: 90px;" id="search-button">
                                <i class="fa fa-search"></i>
                                <span class="btn-text">查询</span>
                            </a>
                        </div>
                    </div>
                    <div id="result" style="margin: 0 30px;"></div>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
<%@ include file="../common/confirm.jsp" %>