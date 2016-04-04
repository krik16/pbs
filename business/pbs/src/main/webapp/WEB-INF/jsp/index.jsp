<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>首页</title>
</head>
<frameset rows="54,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${ctx}/main/top" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="212px,*" frameborder="no" border="0" framespacing="0">
    <frame src="${ctx}/main/left" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" style="border: 1px solid #d9dadc"/>
    <frame src="${ctx}/bill/search" name="mainFrame" id="mainFrame" title="mainFrame" style="border: 1px solid #d9dadc;border-left: 0px;" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>