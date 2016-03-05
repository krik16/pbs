<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<div class="shopSelectList" id="commodityCategoryList">
	<input id="rowCont" type="hidden" name="rowCont" value="<c:out value='${rowCont}'/>" /> 
	<input id="jumpPage" type="hidden" name="jumpPage">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><input id="checkAll" type="checkbox" onclick="checkAll()"/></td>
			<td>付款单号</td>
			<!-- <td>订单名称</td> -->
			<td>金额</td>
			<td>状态</td>
			<td>交易类型</td>
			<td>支付工具</td>
			<td>创建时间</td>
			<td>完成时间</td>
			<td>操作</td>
		</tr>
		<c:choose>
			<c:when test="${not empty paymentList}">
				<c:forEach var="payment" items="${paymentList}" varStatus="status">
					<tr>
						<td><input name="sub_check" type="checkbox" value="${payment.id}/${payment.tradeType}"/></td>
						<td>${payment.payNo}</td>
						<td>${payment.orderNum}</td>
						<%-- <td>${payment.title}</td> --%>
						<td>${payment.amountMoney}</td>
						<td>${payment.statusStr}</td>
						<td>${payment.tradeTypeStr}</td>
						<td>${payment.payChannelStr}</td>
						<td>${payment.createTimeStr}</td>
						<td>${payment.finishTimeStr}</td>
						<c:if test="${payment.tradeType eq 2 && payment.status eq 0}">
							<td><a href="${ctx}/v5/PCWebPageAlipay/zhifubaoRepay.htm?paymentId=${payment.id}" class="search-button" id="pay-button" target="_blank">付款</a></td>
						</c:if>
						<c:if test="${payment.tradeType eq 1 && payment.status eq 0}">
							<td><a href="${ctx}/v5/PCWebPageAlipay/zhifubaoRepay.htm?paymentId=${payment.id}" class="search-button" id="refund-button" target="_blank">退款</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td style="text-align: center;" colspan="12">暂无记录</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>
<div class="page">
	<a href="javascript:void(0);" class="page" id="gopage" clickAction="gopage">跳转</a> 
	<input type="text" name="jumpPageIn" id="jumpPageIn" />
	<a href="javascript:void(0);" class="icon-page" id="downPage" clickAction="downPage"></a> 
	 <span><c:out value="${currpage}" />
	 <c:if test="${rowCont>1}">/<c:out value="${rowCont}" /></c:if> 
	 </span> <a href="javascript:void(0);" class="icon-page-left" id="upPage" clickAction="upPage"></a>
	 <span>条</span>
	<select id="pageSize">
		<option value ="10">10</option>
		<option value="50">50</option>
		<option value ="100">100</option>
	</select>
	<span>每页显示</span>
</div>
