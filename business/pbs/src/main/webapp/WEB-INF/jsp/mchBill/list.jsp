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
            <span style="font-size: 22px;font-weight: 500;">支出</span><br>
            <span style="font-size: 18px;color: red;">-${outTradeTotal.amountTotal}&nbsp;元</span><br>
            <span style="font-size: 18px;color: gray;">${outTradeTotal.countTotal}笔</span>
        </div>
    </div>
    <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
    <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>订单号</th>
            <th>交易号</th>
            <th>收款渠道</th>
            <th>交易金额</th>
            <th>门店名称</th>
            <th>交易类型</th>
            <th>交易时间</th>
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
                        <td>${entity.shopName}</td>
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