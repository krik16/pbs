<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<%@ include file="../common/include.jsp" %>
<head>
</head>
<div class="memSuper">
    <div class="memSuper-main">
        <div class="page-content ng-scope">
            <div class="panel panel-default">
                <div class="panel-heading">
                    账户概览
                </div>
                <div class="panel-body">
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>登录账号： ${entity.userAccount}</span>

                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>用户名称：${entity.userName}</span>

                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>手机号： ${entity.userPhone}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>级别：${entity.roleName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>对接客服名称:${entity.custName != null ? entity.custName :'无'}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>对接客服电话： ${entity.custPhone != null ? entity.custPhone : '无'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>所属股东：${entity.stockholderName != null ? entity.stockholderName :'无'}
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>所属区域：${entity.areaName != null ? entity.areaName :'无'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span> 所属代理： ${entity.agentName != null ? entity.agentName : '无'}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>所属公司： ${entity.companyName != null ? entity.companyName : '无'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>所属分公司： ${entity.subCompanyName != null ? entity.subCompanyName : '无'}</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span> 所属门店： ${entity.shopName != null ? entity.shopName : '无'}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row mb15">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span>创建时间：<fmt:formatDate value="${entity.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
