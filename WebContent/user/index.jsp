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
<title>个人中心-巨能充</title>
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body class="bg_user">
<div class="g-wrap">
    <header class="m-header m-header-home hide">
        <h1 class="title grzx">个人中心</h1>
    </header>
    <div class="g-container m-user">
        <h2 class="avatar" id="js-avatar">
            <div class="act hide">
                <a class="u cartoon user" href="user/personal.jsp"><img class=" img-circle" src="static/image/mail-avatar.jpg" alt="用户"/></a>
                <div class="shadow"></div>   
                <div class="name">SunshineGir<p>女<em>20岁</em></p></div>    
            </div>
            <div class="act">
                <span class="user"><img class="u img-circle"src="static/image/mail-avatar.jpg"alt="用户"/></span>
                <div class="shadow"></div>
                <a href="account/login.jsp" class="name">登录/注册</a>
            </div>
        </h2>
        <ul class="nav cartoon" id='js-pageNav'>
            <li class="cz"><a href="user/carVerify.jsp">车主认证</a></li>
            <li class="wd"><a href="user/myBook.jsp">我的预约</a></li>
            <li class="dd"><a href="user/MyIndent.jsp">订单</a></li>
            <li class="xx"><a href="user/MsgCen.jsp">消息</a></li>
            <li class="wm"><a href="user/Settings.jsp">设置</a></li>
        </ul>
    </div>
    <nav class="m-nav home home2">
        <a class="ch" href="charge/">
            <i class="icon"></i>
            <span>充电</span>
        </a>
        <a class="ne" href="./">
            <i class="icon"></i>
            <span>附近</span>
        </a>
        <a class="act hide u" href="javascript:void(0);">
            <i class="icon"></i>
            <span>我的</span>
        </a>
    </nav> 
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2',function(require,exports,moduel){
        var mm = require;
        var user = localStorage.getItem("user");
        console.log(JSON.parse(user));
        if (user == null) {
            $('#js-avatar').find('.act').eq(0).addClass('hide').siblings('.act').removeClass('hide');
        }else{
            user = JSON.parse(user);
            $('#js-avatar .user').find('img').attr('src',user.head_portrait);
            user.gender == '0' ? user.gender ='男' : user.gender ='女' ;
            user.age == null ? user.age = 0 : user.age;
            $('#js-avatar').find('.name').html(user.nick_name+'<p>'+user.gender+'<em>'+user.age+'岁</em></p>');
            $('#js-avatar').find('.act').eq(1).addClass('hide').siblings('.act').removeClass('hide');
        };
    });
</script>
</body>
</html>