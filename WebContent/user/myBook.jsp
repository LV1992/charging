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
<title>预约-巨能充</title>
<link rel="stylesheet" href="static/css/animate.css">
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap m-indent">
    <nav class="m-nav" id="js-myBook">
        <a class="u-gradualB" href="JavaScript:void(0)" id="ord" data-id="ord" data-type='0'>预约中</a>
        <a class="u-gradualB" href="JavaScript:void(0)" id="cel" data-id="cel" data-type='1'>已取消</a>
        <a class="act" href="JavaScript:void(0)" id="done" data-id="done"data-type='2'>已完成</a>
    </nav>
    <div class="g-container m-booking">
        <div class="hide indent" id="js-myBookList">
            <ul class="item animated hide" v-for='item in myBooklist'>
                <li>下单时间  {{item.start_time}} <a v-if='item.status == 2' class="xq" href="javascript:void(0)">查看订单>></a></li>
                <li>预约电桩序号 <span>{{item.inner_no}}号桩（{{item.c_p_id}}）</span></li>
                <li>预约充电时间 <span>{{item.end_time}}</span></li>
                <li>预约充电地点 <span>{{item.place}}</span><a v-if='item.status == 0' href="JavaScript:void(0)" :data-id="JSON.stringify(item)" class="btn btn-default call">取消预约</a><a v-if='item.status == 1' href="JavaScript:void(0)" class="btn cel"></a><a v-if='item.status == 2' href="JavaScript:void(0)" class="btn done"></a></li>
            </ul>
        </div>
        <div class="norecord"></div>
    </div>
</div>
<script type="text/javascript">
    seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2'],function(){
    });
</script>
</body>
</html>