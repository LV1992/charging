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
<title>巨能充</title>
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-settings m-settings-bg">
        <ul class="guide">
            <li><a href="user/AboutUs.jsp#bundle">绑定账号<i class="fa fa-angle-right"></i></a></li>
            <li><a href="user/AboutUs.jsp#about">关于我们<i class="fa fa-angle-right"></i></a></li>
            <li><a href="user/AboutUs.jsp#feedback">意见反馈<i class="fa fa-angle-right"></i></a></li>
            <li><a href="javascript:void(0)">帮助<i class="fa fa-angle-right"></i></a></li>
            <!-- <li><a href="user/AboutUs.jsp#help">帮助<i class="fa fa-angle-right"></i></a></li> -->
        </ul>
        <a href="javascript:void(0)" id="js-Logout" class="btn exit u-gradualB btn-info">退出登录</a>
        <footer class="footer">
            <div>CopyRight© 2016</div>
            <div class="firm">深圳市中工巨能科技有限公司</div>
        </footer>
    </div>
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2',function () {
        // 点击退出登录
        $('#js-Logout').on('click',function(){
            localStorage.removeItem('user');
            location.href = '/charging/';
        })
    });
</script>
</body>
</html>