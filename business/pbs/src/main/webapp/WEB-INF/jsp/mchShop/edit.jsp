<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp"%>
<head>
    <script src="${ctx}/js/mchShop/mchShop.js" type="text/javascript"></script>
    <script src="${ctx}/js/common/select.js" type="text/javascript"></script>
</head>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
            <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>

            <div class="panel panel-default">
                <div class="panel-heading">
                    门店信息
                </div>
                <div class="panel-body">
                    <div class="panel-body" style="float:left;width: 45%">
                        <input id="id" type="hidden" name="id" value="${entity.id}"/>
                        <input id="authority" type="hidden" name="authority" value="${authority}"/>

                        <div class="form-group row mb15">
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>名称：</span>
                                    <input id="name" type="text" value="${entity.name}"
                                           class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                           placeholder="门店名称"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row mb15">
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext-muted">地址：</span>
                                    <input id="address" type="text" value="${entity.address}"
                                           class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                           placeholder="门店地址"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row mb15" id="company-select">
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext" >公司：</span>
                                    <select class="form-control" id="companyId"
                                            onchange="companySelect('../mchSubCompany/getByCompanyId','companyId','subCompanyId')">
                                        <option value="0">选择所属公司</option>
                                        <c:forEach items="${entity.companyList}" var="item">
                                            <option value="${item.id}"
                                                    <c:if test="${item.id==entity.companyId}">selected="true"</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row mb15"  <c:if test="${entity.subCompanyId <= 0}">style="display:none"</c:if> id="subCompany-select">
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext">分公司：</span>
                                    <select class="form-control" id="subCompanyId">
                                        <option value="0">选择所属公分司</option>
                                        <c:forEach items="${entity.subCompanyVOList}" var="item">
                                            <option value="${item.id}"
                                                    <c:if test="${item.id==entity.subCompanyId}">selected="true"</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row mb15" id="agent-select" <c:if test="${entity.agentId <= 0}">style="display:none"</c:if> >
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext">分销代理：</span>
                                    <select class="form-control" id="agentId"
                                            onchange="agentSelect('../mchSubCompany/getByCompanyId','agentId','companyId')">
                                        <option value="0">选择所属分销代理</option>
                                        <c:forEach items="${entity.agentVOList}" var="item">
                                            <option value="${item.id}"
                                                    <c:if test="${item.id==entity.agentId}">selected="true"</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row mb15">
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-addon input-group-onlytext" name="desc">备注：</span>
                                <textarea class="form-control" rows="3" id="desc"
                                          placeholder="门店备注">${entity.desc}</textarea>
                                </div>
                            </div>
                        </div>

                        <div class="form-group row mb15">
                            <div class="col-sm-4">
                                <a class="btn btn-danger" style="width: 90px;" id="cance-button"
                                   onclick="window.history.go(-1);">
                                    <i class="fa fa-reply"></i>
                                    <span class="btn-text">取消</span>
                                </a>
                            </div>
                            <div class="col-sm-4">
                                <a class="btn btn-primary" style="width: 90px;" id="save-button" onclick="save()">
                                    <i class="fa fa-floppy-o"></i>
                                    <span class="btn-text">保存</span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body" style="float:left;width: 45%">
                        <div class="form-group row mb15">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="active">
                                    <a href="#weixinConfig" data-toggle="tab">
                                        微信账号配置
                                    </a>
                                </li>
                                <li><a href="#aliPayConfig" data-toggle="tab">
                                    支付宝账号配置
                                </a>
                                </li>
                            </ul>
                            <div id="myTabContent" class="tab-content">
                                <div class="tab-pane fade in active" id="weixinConfig">
                                    <div class="form-group row mb15">
                                    <div class="input-group">
                                        <span class="input-group-addon input-group-onlytext-muted">微信商户号：</span>
                                        <input id="weixinMchId" type="text" value="${entity.weixinMchId}"
                                               class="form-control dropdown-toggle ng-pristine ng-valid"
                                               required="required"
                                               placeholder="微信商户号"/>
                                    </div>
                                        </div>
                                </div>
                                <div class="tab-pane fade" id="aliPayConfig">
                                    <div class="tab-pane fade in active" id="aliConfig">
                                        <div class="form-group row mb15">
                                                <div class="input-group">
                                                    <span class="input-group-addon input-group-onlytext-muted">PID：</span>
                                                    <input id="aliPid" type="text" value="${entity.aliPid}"
                                                           class="form-control dropdown-toggle ng-pristine ng-valid"
                                                           required="required"
                                                           placeholder="支付宝PID"/>
                                            </div>
                                        </div>
                                        <div class="form-group row mb15">
                                                <div class="input-group">
                                                    <span class="input-group-addon input-group-onlytext-muted">KEY：</span>
                                                    <input id="aliKey" type="text" value="${entity.aliKey}"
                                                           class="form-control dropdown-toggle ng-pristine ng-valid"
                                                           required="required"
                                                           placeholder="支付宝KEY"/>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<%@ include file="../common/confirm.jsp" %>