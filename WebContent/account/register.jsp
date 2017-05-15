<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<jsp:include page="../static/common/head.jsp"/>
<!-- BootStrap -->
<meta name="author" content="dony">
<meta name="keywords" content="">
<meta name="description" content="">
<title>登录-巨能充</title>
<link rel="stylesheet" href="static/css/login.css">
</head>
<body>
<div class="g-wrap">
    <header class="m-header m-header-home">
        <a class="fa fa-chevron-left back" href="javascript:history.back();"></a>
        <h1 class="title">注册</h1>
    </header>
    <div class="m-login">
    	<form class="form-inline login" id="js-login" role="form" method="post">
    		<h1 class="avatar"><img class="u img-circle" src="static/image/mail-avatar.jpg" alt="name"></h1>
			<div class="input-group">
				<div class="input-group-addon icon">账户</div>
				<input class="form-control" name="name" type="text" placeholder="请输入登录用户名">
			</div>
			<div class="input-group">
				<div class="input-group-addon icon">密码</div>
				<input class="form-control" name="pwd" type="password" placeholder="请输入登录密码">
			</div>
			<div class="form-group reg">
				<a class="pull-left btn" href="account/login.jsp">用户登录</a>
				<a class="pull-right btn" href="account/forgot.jsp">忘记密码</a>
			</div>
		  	<button type="submit" class="btn btn-success submit">登录</button>
		</form>
    </div>
    <footer class="m-footer"></footer>
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2');
</script>
</body>
</html>