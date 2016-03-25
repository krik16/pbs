<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}/css/index.css" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery.poshytip.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>

    <script src="${ctx}/js/common/confirm.js" type="text/javascript"></script>
    <script src="${ctx}/js/common/list_common.js" type="text/javascript"></script>
    <script src="${ctx}/js/user/user.js" type="text/javascript"></script>

    <link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet"/>


    <link href="${ctx}/css/jquery.alerts.css" rel="stylesheet" type="text/css"/>
</head>
<div class="memSuper">
    <div class="memSuper-title">公司管理 >区域管理 >编辑</div>
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <input id="currpage" type="hidden" name="currpage" value="${currpage}"/>
            <input id="rowCount" type="hidden" name="rowCount" value="${rowCount}"/>

            <div class="panel panel-default">
                <div class="panel-heading">
                    编辑
                </div>
                <div class="panel-body">
                    <input id="id" type="hidden" name="id" value="${entity.id}"/>

                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted">账号：</span>
                                <input id="userAccount" type="text" value="${entity.userAccount}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户登录账号"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted">姓名：</span>
                                <input id="userName" type="text" value="${entity.userName}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户姓名"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted">电话号码：</span>
                                <input id="userPhone" type="text" value="${entity.userPhone}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户电话号码"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted">是否公司员工：</span>
                                <input id="isEmployee" type="text" value="${entity.is_employee}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户是否是公司内部员工"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">所属公司：</span>
                                <select class="form-control" id="mchCompanyId">
                                    <option value="0">选择所属公司</option>
                                    <c:forEach items="${entity.mchCompanyList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.mchCompanyId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">所属子公司：</span>
                                <select class="form-control" id="mchSubCompanyId">
                                    <option value="0">选择所属子公司</option>
                                    <c:forEach items="${entity.mchSubCompanyList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.mchSubCompanyId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext">所属门店：</span>
                                <select class="form-control" id="mchShopId">
                                    <option value="0">选择所属门店</option>
                                    <c:forEach items="${entity.mchShopList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.mchShopId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row mb15">
                        <div class="col-sm-2">
                            <a class="btn btn-danger" style="width: 90px;" id="cance-button"
                               onclick="window.history.go(-1);">
                                <i class="fa fa-reply"></i>
                                <span class="btn-text">取消</span>
                            </a>
                        </div>
                        <div class="col-sm-2">
                            <a class="btn btn-primary" style="width: 90px;" id="save-button" onclick="save()">
                                <i class="fa fa-floppy-o"></i>
                                <span class="btn-text">保存</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<%@ include file="../common/confirm.jsp"%>