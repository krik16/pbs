<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><html lang="zh-CN">
<%@ include file="/common/tag.jsp"%>

<html>
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/fileuploader.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/ng-tags-input.bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/ng-tags-input.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/jquery-ui.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/summernote.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/ui.fancytree.css" type="text/css" rel="stylesheet" />     <!-- 树状插件css-->
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/js/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/jquery-ui.js" type="text/javascript"></script>
<!-- 树状插件js-->
<script src="${ctx}/js/jquery/jquery-ui.custom.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/jquery.fancytree.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/jquery.nicescroll.js" type="text/javascript"></script>
<head>
    <title></title>
</head>
<body>
<div class="navbar navbar-default">
  <div class="navbar-container">
    <button type="button" class="navbar-toggle">
      <i class="fa fa-bars"></i>
    </button>
    <h1 class="nav-logo">
      <img src="../image/logo.png">
      <span style="font-size: 20px; position: relative; top: 11px; height: 30px;">商户管理平台</span>
    </h1>
    <div class="navbar-brand nav-user" role="navigation">
      <a data-toggle="dropdown" href="#" class="dropdown-toggle">
        <i class="fa fa-user"></i> <span id="username">${sessionScope.user.userAccount}</span><i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-menu-right">
        <li>
          <a href="#/changePassword">
            <i class="ace-icon fa fa-lock"></i>
            修改密码
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href="javascript:;" id="logout">
            <i class="ace-icon fa fa-sign-out"></i>
            退出登录
          </a>
        </li>
      </ul>
    </div>
    <div class="nav-store" id="storename"></div>
  </div>
</div>
</body>
</html>
