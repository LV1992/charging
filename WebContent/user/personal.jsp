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
<title>个人资料-巨能充</title>
<link rel="stylesheet" href="static/css/animate.css">
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <header class="m-header m-personal">
        <h2 class="avatar"><a id="js-avatars" class="u" href="javascript:void(0)"><img class=" img-circle animated bounceInDown" src="static/image/mail-avatar.jpg" alt="用户头像"/></a></h2>
    </header>
    <div class="g-container m-user m-personal con">
        <form class="m-form" id="js-personal" method="post">
            <ul class="personal">
                <li>用户名：<input type="text" data-type="u" name='nick_name' class="form-control" value="用户名"/></li>
                <li>性别：
                    <select name='gender' class="form-control" id="">
                        <option value=""></option>
                        <option value="0">男</option>
                        <option value="1">女</option>
                        <option value="">保密</option>
                    </select>
                    <!-- <input type="text" name='gender' value="性别"/> -->
                </li>
                <li>年龄：<input type="text" data-type="n" name='age' class="form-control" value="年龄"/></li>
                <li>联系方式：<input type="tel" data-type="m" name='tel' class="form-control" value="联系方式"/></li>
                <li>登录密码：<span>******</span></li>
            </ul>
            <div id="js-upavatar"></div>
            <button type="button" class="btn btn-primary animated flipInX">编&nbsp;&nbsp;辑</button>
        </form>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2','Plugins/uploader/webuploader.js'],function() {

    });
</script>
</body>
</html>