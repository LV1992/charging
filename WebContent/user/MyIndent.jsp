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
<title>订单-巨能充</title>
<link rel="stylesheet" href="static/css/animate.css">
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap m-indent">
	<nav class="m-nav home2" id="js-Indent">
		<a class="u-gradualB" href="JavaScript:void(0)" data-type='0'>待支付订单</a>
		<a class="act" href="JavaScript:void(0)"data-type='1'>已完成订单</a>
	</nav>
    <div class="g-container">
    	<div class="hide indent" id="js-IndentList">
    		<ul class="item animated hide" v-for='item in Indentlist'>
				<li>订单号 {{item.trade_no}}<span class="xq">{{item.end_time}}</span></li>
    			<li>充电地点<span>{{item.place}}</span></li>
    			<li>充电时间<span>{{item.start_time}}</span></li>
    			<li>断电时间<span>{{item.end_time}}</span></li>
    			<li>充电时长<span>{{item.time_count}}h</span></li>
    			<li>充电单价<span>{{item.e_price}}元/度（{{item.price_time}}）</span></li>
    			<li>已充电量<span>{{item.degree}}kw/h</span></li>
    			<li>消费合计<span>{{item.money}}元</span><a v-if='item.trade_status == 0' href="JavaScript:void(0)" v-bind:data-id="item.id" class="btn btn-default pay">立即支付</a><a v-if='item.trade_status == 1' href="JavaScript:void(0)" class="btn done"></a></li>
    		</ul>   		
    	</div>
    	<div class="norecord"></div>
        <form action="" method="post" class="form-horizontal pay" id="js-chargePay">
            <input type="hidden" name="id" class="payid" value=""/>
            <input type="hidden" name="uid" class="uid" value=""/>
        </form>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2'],function(){

	});
</script>
</body>
</html>

