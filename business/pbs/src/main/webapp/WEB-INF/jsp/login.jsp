<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><html lang="zh-CN">
<%@ include file="/common/tag.jsp"%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>欢迎</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <meta name="description" content="test" />
    <meta name="renderer" content="webkit">
    <link href="${ctx}/css/login.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
    <%--<script src="${ctx}/js/loader.js" type="text/javascript"></script>--%>
    <!--[if lte IE 9]>
    <meta http-equiv="refresh" content="1;url=ie.html" tppabs="http://msp.shouqianba.com/error/ie.html">
    <script src="xdomain.min.js" tppabs="http://msp.wosaimg.com/js/lib/build/xdomain/0.6.12/xdomain.min.js" slave="http://api.wosai.cn/static/proxy.html"></script>
    <![endif]-->
</head>

<body>

<div class="login-form" ng-controller="loginController">
    <div class="form-wrap" role="main" ng-show="type=='login'">
        <h1 class="logo">
            <a href="${ctx}/main/index">
                <img style="width: 142px;" src="../image/logo.png">
            </a>
        </h1>
        <form action="../j_spring_security_check" method="post">

            <%--<div class="login-wrap login-loading">--%>

                <div class="form-group">
                    <div class="form-item">
                        <label for="j_username">
                            <i class="fa fa-user"></i>
                            <span>用户名</span>
                        </label>
                        <input type="text" autofocus="autofocus" id="j_username" name="j_username" ng-model="username" class="form-control" placeholder="用户名" />
                    </div>
                    <div class="form-item">
                        <label for="j_password">
                            <i class="fa fa-lock"></i>
                            <span>密码</span>
                        </label>
                        <input type="password" name="j_password" id="j_password" ng-model="password" class="form-control" placeholder="密码" />
                    </div>
                   <%-- <div class="form-code" ng-show="needCaptcha">
                        <input type="text" ng-model="captchaCode" placeholder="验证码"/>
                        <img class="code" ng-src="{{captchaImg}}" tppabs="http://msp.shouqianba.com/login/{{captchaImg}}">
                        <a class="refresh" href="javascript:;" ng-click="changeAuthcode()">看不清楚,换一张</a>
                    </div>--%>
                </div>
                <div class="form-btn">
                    <button type="submit" class="btn btn-primary" id="login-btn" >登录</button>
                </div>
            <%--</div>--%>

        </form>
        <div class="footer" role="contentinfo">
            <p class="copyright">
                Copyright &copy; 2013 - 2014 Wosai Inc. All Rights Reserved
            </p>
        </div>
    </div>
</div>

<%--<script>
    $LAB.script(LIBS.jQuery)
            .wait()
            .script(LIBS.angular)
            .script(LIBS.bootstrap)
//            .script(COMMON.ERRORCODE)
            .script(LIBS.md5)
            .wait()
            .script(IWOSAI.LOGIN)
</script>--%>
</body>
