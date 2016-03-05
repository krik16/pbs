<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>$!{title}</title>
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
</head>
</head>
<body>
	<p style="margin-top: 15px;">支付宝支付页面</p>
	<div style="border-color: #ccc; border-style: none none dotted; border-width: 0 0 1px;"></div>
	<p>客官请稍候... ...</p>
	${content}
</body>
<script>
	$(function() {
		var imglist = $('img');
		var size = imglist.size();
		for (var i = 0; i < size; i++) {
			imglist.eq(i).removeAttr('style');
			imglist.eq(i).css('max-width', '100%');
		}
	})
	</html>
