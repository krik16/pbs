<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>容易移动商品管理系统</title>
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/js/util.js" type="text/javascript"></script>
<script src="${ctx}/js/msgBoxBg.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
<style type="text/css">
/*平台同步*/
.Platform1{
	width: 100px;
	height: 30px;
	line-height: 30px;
/* 	position: absolute; */
	top: 10px;
	right: 10px;
	background: #1ca823;
	z-index: 100;
	border-radius: 3px;
}
.Platform1 a{
	display: block;
	color: #fff;
/* 	padding: 0 20px; */
	font-size: 14px;
	background: url("../images/icon_3.png") 90% 50% no-repeat;
}
.PlatformList1{
	width: 100px;
/* 	position: absolute; */
	top: 35px;
	right: 10px;
	z-index: 99;
	background: #e7e8eb;
	display: none;
}
.PlatformList1 ul{
	width: 100px;
}
.PlatformList1 ul li{
	width: 100px;
	height: 30px;
}
.PlatformList1 ul li a{
/* 	width: 60px; */
	height: 30px;
	line-height: 30px;
/* 	padding: 0 20px; */
	display: block;
	font-size: 14px;
}
/* .PlatformList1 ul li a.checked,.PlatformList1 ul li a:hover{ */
/* 	background: url("../images/icon_checked.png") 85% 50% no-repeat #cfcfcf; */
/* } */
</style>
</head>
<body>
<div class="main">



<div class="MsgBoxBg"></div>
<div class="MsgBox">
	<p></p>
	<a href="javascript:void(0);" class="Button checked" id="MsgBoxBtn">确定</a>
</div> 
</body>
</html>
