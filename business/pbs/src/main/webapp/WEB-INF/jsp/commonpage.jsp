<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
</head>
<body>
<h1>Common Page</h1>
<p>每个人都能访问的页面.</p>
<a href="/pbs/main/admin"> Go AdminPage </a>
<br />
<sec:authorize ifAnyGranted="TMS_F_PAY" >
<a href="/pbs/auth/login">退出登录</a>
</sec:authorize>

</body>
</html>
