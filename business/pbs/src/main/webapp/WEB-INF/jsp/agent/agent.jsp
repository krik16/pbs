<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ include file="../common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>订单详情</title>
        <script src="${ctx}/js/agent/agent.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="memSuper">
            <div class="memSuper-title">对账管理 >结算明细</div>
            <div class="memSuper-main">
        <%--        <div class="alert alert-warning">
                    <a href="#" class="close" data-dismiss="alert">
                        &times;
                    </a>
                    <strong>警告！</strong>您的网络连接有问题。
                </div>--%>
                <div class="page-content ng-scope">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            代理列表
                        </div>
                        <div class="panel-body">
                            <div class="form-group row mb15">
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <span class="input-group-addon input-group-onlytext">名称：</span>
                                        <input id="name" type="text" class="form-control dropdown-toggle ng-pristine ng-valid" placeholder="区域名称">
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-primary" style="width: 90px;" id="search-button">
                                        <i class="fa fa-search"></i>
                                        <span class="btn-text" >查询</span>
                                    </a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-primary" style="width: 90px;" href="${ctx}/agent/edit">
                                        <i class="fa fa-edit"></i>
                                        <span class="btn-text" >新建</span>
                                    </a>
                                </div>
                            </div>
                            <div id="result" style="margin: 0 30px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            </div>

        </div>
    </body>
</html>
<%@ include file="../common/confirm.jsp"%>