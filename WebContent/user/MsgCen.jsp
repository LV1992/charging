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
<link rel="stylesheet" href="static/css/animate.css">
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-msgcen hide" id="js-MsgCen">
        <header class="header">
            <img class="avatar img-circle animated slideInLeft" src="static/image/mail-avatar.jpg" alt="用户"/>
            <a class="btn" data-id='footprint' href="javascript:void(0)"><em class="im">0</em>我的脚印</a>
            <a class="btn" data-id='unread'href="javascript:void(0)"><em class="un">0</em>未读信息</a>
        </header>
    	<div class="con">
            <article class="item" v-for='item in conLists.reviewList'>
                <div class="date" v-if="item.time != ''">
                    <em class="month"><strong>{{item.time.slice(8,10)}}</strong>/</em>{{item.time.slice(5,7)}}月<span class="hours">{{item.time.slice(10)}}</span>
                </div>
                <!-- 未读 -->
                <div v-if="item.is_read == 'N'" class="cent unread animated hide" id='w'>
                    <a :href="'user/MsgDetail.jsp?d='+item.id+'&sid='+item.station_id">
                        <img :src="item.picture.split('#')[0]" alt=""/>
                        <p>{{item.content}}</p>                      
                    </a>
                </div>
                <!-- 已读 -->
                <div v-if="item.is_read == 'Y'" class="cent animated hide">
                    <a :href="'user/MsgDetail.jsp?d='+item.id+'&sid='+item.station_id">
                        <img :src="item.picture.split('#')[0]" alt=""/>
                        <p>{{item.content}}</p>                      
                    </a>
                </div>
            </article>
    	</div>
    </div>
</div>
<script type="text/javascript">
    seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2'],function(){
    });
</script>
</body>
</html>