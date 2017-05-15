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
<meta property="wb:webmaster" content="1c888e3b1b8655f5" />
<title>登录-巨能充</title>
<link rel="stylesheet" href="static/css/login.css">
</head>
<body>
<div class="g-wrap">
    <header class="m-header m-header-home">
        <a class="fa fa-chevron-left back" href="user/"></a>
        <h1 class="title">登录</h1>
    </header>
    <div class="m-login">
    	<form class="form-inline login" id="js-login" role="form" method="post">
    		<h1 class="avatar"><img class="u img-circle" src="static/image/mail-avatar.jpg" alt="name"></h1>
			<div class="input-group">
				<div class="input-group-addon icon">账户</div>
				<input class="form-control" data-type='u' name="userName" type="text" placeholder="请输入登录用户名">
			</div>
			<div class="input-group">
				<div class="input-group-addon icon">密码</div>
				<input class="form-control" data-type='p' name="password" type="password" placeholder="请输入登录密码">
			</div>
			<div class="form-group reg">
				<a class="pull-left btn" href="account/register.jsp">立即注册</a>
				<a class="pull-right btn" href="account/forgot.jsp">忘记密码</a>
			</div>
		  	<button type="submit" class="btn btn-success submit">登录</button>
		</form>
	    <footer class="footer" id="js-acceptLogin">
	    	<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb36981de3b87bc&redirect_uri=http://mobile.chinajune.com/charging/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect" class="btn btn-info"><i class="fa fa-weixin"></i></a>
	    	<a href="javascript:void(0)" class="btn btn-info qq"><i class="fa fa-qq"></i></a>
	    	<a href="https://api.weibo.com/oauth2/authorize?client_id=3226597451&response_type=code&redirect_uri=http://mobile.chinajune.com/charging/&state=weibo3U7dX2WDVXusSw36&with_offical_account=1" class="btn btn-info"><i class="fa fa-weibo"></i></a>
	    </footer>
    </div>

</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/login.js'],function(){
		var QAppId  = '1105709980';
		var QAppKey  = 'B4SQXgDCo8sCLJm6';
		$('#js-acceptLogin').on('click','.qq',function(){
			location.href = 'https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=1105709980&pt_3rd_aid=100374397&daid=383&pt_skey_valid=0&style=35&s_url=http://Fconnect.qq.com&refer_cgi=authorize&which=&client_id=100374397&redirect_uri=http://dony8.imwork.net/charging/uer/&response_type=code&state=c99d030c4d4414236bfecce9f36d3d53&scope=get_user_info&display=mobile'
		});
	});
</script>
</body>
</html>