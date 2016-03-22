<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>代理名称</th>
            <th>代理描述</th>
            <th>所属区域</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach var="entity" items="${list}" varStatus="status">
                    <tr>
                        <td>${entity.name}</td>
                        <td>${entity.desc}</td>
                        <td>${entity.areaId}</td>
                        <td>${entity.areaId}</td>
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