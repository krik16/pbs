<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
	body{
		width: 100%;
		padding: 0;
		margin: 0 auto;
		text-align: center;
	}
	.in{
		width: 140px;
	}
	form{
		width: 300px;
	}
</style>
</head>

<body>
	<form action="${pageContext.request.contextPath}/j_spring_security_check"
										method="post" onsubmit="return checkLogin(this);">
		<fieldset>
			<legend>登陆</legend>
			<table>
				<tr>
					<td>用户：</td>
					<td>
						<input class="in" type="text" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/>
					</td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input class="in" type="password" name="j_password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登陆" /> <input type="reset" value="重置" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	function checkLogin(obj) {
		if (obj.j_username.value == "") {
			alert("请输入帐号！");
			return false;
		}
		if (obj.j_password.value == "") {
			alert("请输入密码！");
			return false;
		}
		return true;
	}
</script>
</html>
