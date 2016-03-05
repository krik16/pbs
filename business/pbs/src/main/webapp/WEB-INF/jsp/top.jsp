<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>容易移动商品管理系统</title>
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
</head>

<body>
<div class="top">
	<div class="top-left">
		<div class="logo"></div>
		<span><a href="${ctx}/main/index" target="_parent">容易移动商品管理系统</a> </span>
	</div>
	<div class="top-right">
		<span>${curdate}</span>
		<span>欢迎：${name} |  &nbsp;<a href="${ctx}/j_spring_security_logout" target="_parent">注销</a></span>
	</div>
	
</div>
</body>
</html>
