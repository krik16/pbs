<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>

<div class="table-responsive">
    <div>
        <div style="float: left;margin-left: 200px">
            <span style="font-size: 22px;font-weight: 500;">收入</span><br>
            <span style="font-size: 18px;color: green;">+${inTradeTotal.amountTotal} &nbsp;元</span><br>
            <span style="font-size: 18px;color: gray;">${inTradeTotal.countTotal}笔</span>
        </div>
        <div style="float: right;margin-right: 200px">
            <span style="font-size: 22px;font-weight: 500;">退款</span><br>
            <span style="font-size: 18px;color: red;">-${outTradeTotal.amountTotal}&nbsp;元</span><br>
            <span style="font-size: 18px;color: gray;">${outTradeTotal.countTotal}笔</span>
        </div>
    </div>
    <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
    <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>
    <table class="table table-striped table-bordered table-hover" style="table-layout:fixed">
        <thead>
        <tr>
            <th width="16%">订单号</th>
            <th width="25%">交易流水号</th>
            <th width="6%">渠道</th>
            <th width="6%">金额</th>
            <th width="18%">门店名称</th>
            <th width="7%">收银员</th>
            <th width="5%">类型</th>
            <th width="15%">交易时间</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach var="entity" items="${list}" varStatus="status">
                    <tr>
                        <td><a href="${ctx}/bill/info?id=${entity.id}" target="_blank"
                               style="text-decoration: underline;font-size: 15px;">${entity.orderNo}</a></td>
                        <td>${entity.tradeNo}</td>
                        <td>${entity.payChannel == 0 ? '支付宝':'微信'}</td>
                        <td>${entity.payAmount/100}</td>
                        <td class="overflow"><a style="font-size: 15px;color: #363b42;" rel="tooltip" title="${entity.shopName}">${entity.shopName}</a></td>
                        <td class="overflow"><a style="font-size: 15px;color: #363b42;" rel="tooltip" title="${entity.userName}">${entity.userName}</a></td>
                        <td>${entity.tradeType == 0 ? '收款':'退款'}</td>
                        <td><fmt:formatDate value="${entity.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td style="text-align: center;" colspan="18">暂无记录</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
<%@ include file="../footer.jsp" %>