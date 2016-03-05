<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML>
<head>
<title>测试图片上传</title>
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
	<form action="${pageContext.request.contextPath}/fileUpload/file.htm" method="post" enctype ="multipart/form-data">
		<fieldset>
			<legend> </legend>
			<table>
				<tr>
					<td>上传图片：</td>
					<td>
						<input type="file" name="myfiles"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="上传" /> <input type="reset" value="重置" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.9.1.js"></script>
</html>
