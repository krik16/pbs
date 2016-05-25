<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>

<div class="table-responsive">
    <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
    <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>公司名称</th>
            <th>公司地址</th>
            <th>分销代理</th>
            <th>交易总额(元)</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach var="entity" items="${list}" varStatus="status">
                    <tr>
                        <td><a href="${ctx}/mchSubCompany/search?companyId=${entity.id}" target="mainFrame"
                               style="text-decoration: underline;font-size: 15px;">${entity.name}</a></td>
                        <td>${entity.address}</td>
                        <td>${entity.agentName}</td>
                        <td>${entity.inTotalCount}</td>
                        <td>${entity.desc}</td>
                        <td>
                            <sec:authorize ifAnyGranted="ADMINISTRATOR,COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT">
                                <div class="col-sm-3">
                                    <a class="btn btn-primary list-add" style="width: 90px;" id="edit-button"
                                       href="${ctx}/mchCompany/edit?id=${entity.id}">
                                        <i class="fa fa-edit"></i>
                                        <span class="btn-text">修改</span>
                                    </a>
                                </div>
                                <div class="col-sm-3">
                                    <a class="btn btn btn-danger list-add" style="width: 90px;" id="cance-button"
                                       onclick="cance(${entity.id})">
                                        <i class="fa fa-times-circle"></i>
                                        <span class="btn-text">删除</span>
                                    </a>
                                </div>
                            </sec:authorize>
                        </td>
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