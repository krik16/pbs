<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>

<div class="table-responsive">
    <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
    <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>门店名称</th>
            <th>门店地址</th>
            <th>所属公司</th>
            <th>所属分公司</th>
            <th>所属分销代理</th>
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
                        <td>${entity.name}</td>
                        <td>${entity.address}</td>
                        <td>${entity.companyName}</td>
                        <td>${entity.subCompanyName}</td>
                        <td>${entity.agentName}</td>
                        <td>${entity.inTotalCount}</td>
                        <td>${entity.desc}</td>
                        <td>
                            <sec:authorize ifAnyGranted="ADMINISTRATOR,COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY,MCH_SUB_COMPANY,MCH_SHOPKEEPER">
                                <div class="col-sm-4">
                                    <a class="btn btn-primary list-add" style="width: 90px;" id="edit-button"
                                       href="${ctx}/mchShop/edit?id=${entity.id}">
                                        <i class="fa fa-edit"></i>
                                        <span class="btn-text">修改</span>
                                    </a>
                                </div>
                                <div class="col-sm-4">
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