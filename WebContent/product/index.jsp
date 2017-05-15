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
<title>充电站信息-巨能充</title>
<link rel="stylesheet" href="static/plugins/amazeui/css/amazeui.min.css">
<link rel="stylesheet" href="static/css/animate.css">
<link rel="stylesheet" href="static/css/charge.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-product" id="js-product">
		<nav class="m-nav home2" id="js-productTag">
			<a href="javascript:void(0);">充电站信息</a>
			<a class="act u-gradualB" href="javascript:void(0);">车友评论</a>
		</nav>
		<div id="js-slider"class="m-slide am-slider am-slider-a1">
			<ul class="am-slides"></ul>
		</div>
		<div class="center details hide" id="js-DetailsList">
			<h2 class="tle">{{contents.chargePointStationDetails.name}}<span>(开放时间:{{contents.chargePointStationDetails.start_time}}-{{contents.chargePointStationDetails.end_time}})</span></h2>
			<div class="intro">
			   <span>服务费：{{contents.chargePointStationDetails.service_fee}}/元</span>
			   <span>停车费：{{contents.chargePointStationDetails.parking_fee}}/元</span>
				<map class="map" name="map"><i class="fa fa-flag"></i><em class="place">{{contents.chargePointStationDetails.place}}</em><a :data-city="contents.chargePointStationDetails.location" class="btn t-blue target" href="javascript:void(0);"><i class="fa fa-share"></i>去这里</a></map>
			</div>
			<h3 class="tle">桩群列表<span>(直流{{contents.dcSize}}/{{contents.dcfreeSize}}  交流{{contents.acSize}}/{{contents.acfreeSize}})</span></h3>
			<ul class="items">
				<li class="list animated hide" v-for='(item,index) in contents.chargePointList'>
					<span v-if="item.c_p_type == 0">直流电桩 #<em>{{item.inner_no}}</em></span>
					<span v-if="item.c_p_type == 1">交流电桩 #<em>{{item.inner_no}}</em></span>
					<div><span>序列号:{{item.c_p_id}}</span><span>额定功率:{{item.e_price}}kw</span><span>充电费:{{item.e_price}}元/度</span>
					<a v-if="item.is_free == 0" :href="'product/Booking.jsp?pid='+item.c_p_id+'&m='+item.inner_no+'&map='+contents.chargePointStationDetails.place" class="btn u-gradualB">预约</a>
					<a v-if="item.is_free != 0" href="javascript:void(0)" class="btn">预约</a>
					</div>					
				</li>
			</ul>
		</div>
		<div class="center comment hide" id="js-CommentList">
			<h2 class="tle">充电站</h2>
			<div class="comm">综合评论：<i class="fa fa-star"v-for='nub in contents.avgScore'></i><i class="fa fa-star-o"v-for='nub in 5-contents.avgScore'></i><span class="pf"><em>{{contents.avgScore*2}}</em>分</span></div>			
			<dl class="ulist list animated hide" v-for='item in contents.reviewListItem'>
				<dd class="avt"><img class="img-circle" :src="item.review.head_portrait" alt="用户头像"/></dd>
				<dt>{{item.review.nick_name}}</dt>
				<dd><i class="fa fa-star"v-for='nub in item.review.score'></i><i class="fa fa-star-o"v-for='nub in 5-item.review.score'></i><span class="t">{{item.review.time}}</span></dd>
				<dd class="con">{{item.review.content}}</dd>
				<dd class="edit"><a class="btn like" v-bind:data-id="item.review.id" href="javascript:void(0)">{{item.addHeartCount}}</a>
					<a v-bind:data-id="item.review.id" v-bind:data-uid="item.review.review_uid"  class="btn hf" href="javascript:void(0)">{{item.writeBackCount}}</a>
				</dd>
			</dl>
		</div>
		<div class="comment m-commReply animated hide" id="js-commReply">
			<form class="form-horizontal form" role="form" action="" method="post">
				<input type="hidden" class="name" name="review_id" value="">
				<input type="hidden" class="name" name="review_uid" value="">
				<textarea class="form-control" name="content" rows="4"placeholder="回复"></textarea>
				<button type="submit" class="btn btn-info u-gradualB">提交</button>
				<button type="reset" class="btn btn-default cel">返回</button>
			</form>
			<div class="review"></div>
		</div>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','Plugins/vue.js','JS/charge.js?v=0.2'],function(){
	});
</script>
</body>
</html>