<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp"%>
<head>
    <script src="${ctx}/js/area/area.js" type="text/javascript"></script>
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
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>名称：</span>
                                <input id="name" type="text" value="${entity.name}" name="name"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="区域名称"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext"><font color="red" style="position:relative; top:2px;">*</font>股东：</span>
                                <select class="form-control selectpicker" data-live-search="true" id="stockholderId" name="stockholderId" required>
                                    <option value="0">选择所属股东</option>
                                    <c:forEach items="${entity.stockholderList}" var="item">
                                        <option value="${item.id}" <c:if test="${item.id==entity.stockholderId}">selected="true"</c:if>>${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext" name="desc">备注：</span>
                                <textarea class="form-control" rows="3" id="desc"
                                          placeholder="区域备注">${entity.desc}</textarea>
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