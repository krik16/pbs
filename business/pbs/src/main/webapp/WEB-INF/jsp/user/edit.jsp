<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp"%>
<head>
    <script src="${ctx}/js/common/select.js" type="text/javascript"></script>
    <script src="${ctx}/js/user/user.js" type="text/javascript"></script>
</head>
<div class="memSuper">
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
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>账号(手机号)：</span>
                                <input id="userAccount" type="text" value="${entity.userAccount}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户登录账号"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>姓名：</span>
                                <input id="userName" type="text" value="${entity.userName}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户姓名"/>
                            </div>
                        </div>
                    </div>
              <%--      <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>电话号码：</span>
                                <input id="userPhone" type="text" value="${entity.userPhone}"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="用户电话号码"/>
                            </div>
                        </div>
                    </div>--%>
                    <div class="form-group row mb15"  id="role-select">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext"><font color="red" style="position:relative; top:2px;">*</font>用户角色：</span>
                                <select class="form-control" id="roleId" onchange="roleSelect('roleId')">
                                    <option value="0">选择用户角色</option>
                                    <c:forEach items="${entity.roleList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.roleId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row mb15" <c:if test="${entity.id == null || entity.areaId == 0}">style="display:none"</c:if> id="area-select">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext"><font color="red" style="position:relative; top:2px;">*</font>所属区域：</span>
                                <select class="form-control selectpicker" data-live-search="true" id="areaId" onchange="areaSelect('../agent/getByAreaId','areaId','agentId')">
                                    <option value="0">选择所属区域</option>
                                    <c:forEach items="${entity.areaList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.areaId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row mb15" <c:if test="${entity.id == null || entity.agentId == 0}">style="display:none"</c:if> id="agent-select">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext"><font color="red" style="position:relative; top:2px;">*</font>所属代理：</span>
                                <select class="form-control selectpicker" data-live-search="true" id="agentId">
                                    <option value="0">选择所属代理</option>
                                    <c:forEach items="${entity.agentList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.agentId}">selected="true"</c:if>>${item.name}</option>
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