<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/tag.jsp" %>
<%@ include file="common/include.jsp"%>
<head>
    <script src="${ctx}/js/pwd.js" type="text/javascript"></script>
</head>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <div class="panel panel-default">
                <div class="panel-heading">
                    修改密码
                </div>
                <div class="panel-body">
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>旧密码：</span>
                                <input id="oldPwd" type="password" name="oldPwd"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="旧密码"/>

                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>新密码：</span>
                                <input id="newPwd1" type="password" name="newPwd1"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="新密码"/>

                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon input-group-onlytext-muted"><font color="red" style="position:relative; top:2px;">*</font>确认密码：</span>
                                <input id="newPwd2" type="password" name="newPwd2"
                                       class="form-control dropdown-toggle ng-pristine ng-valid" required="required"
                                       placeholder="确认密码"/>

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
                            <a class="btn btn-primary" style="width: 90px;" id="save-button" onclick="changePwd()">
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
<%@ include file="common/confirm.jsp"%>