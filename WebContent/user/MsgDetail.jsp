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
    <div class="g-container m-msgdetail hide" id="js-MsgDetail">
        <header class="header">
            <img class="avatar img-circle animated slideInLeft" src="static/image/mail-avatar.jpg" alt="用户"/>
            <div class="details">
            	<div class="name"></div>
            	<span>{{reviewdeta.time}}</span>            
                <p>{{reviewdeta.content}}</p>
            	<dl class="ulist">
					<dd class="avt"><img :src="reviewdeta.picture"alt="用户"/></dd>
                    <dt>{{reviewdeta.name}}</dt>
					<dd>综合评价：<i class="fa fa-star"v-for='nub in reviewdeta.avg_score'></i></dd>
					<dd class="cent"><i class="fa fa-flag"></i>{{reviewdeta.place}}</dd>
				</dl>
            </div>
        </header>
    	<div class="centent">
			<dl :data-id="item.review_id" class="ulist animated hide" v-for='(item,index) in conLists.writeBackList'>
				<dd class="avt"><img class="img-circle" :src="item.head_portrait" alt="用户"/></dd>
				<dt><em :data-id="item.write_back_uid">{{item.nick_name}}</em><span class="t">{{item.time}}</span></dt>
				<dd class="cent">{{item.content}}</dd>
				<dd class="edit"><a class="btn hf" href="javascript:void(0)">{{item.avg_score}}</a></dd>
			</dl>
        </div>
        <div class="commReply hide" id="js-commReply">
            <form class="form-horizontal form animated fadeInDown" role="form" action="" method="post">
                <input type="hidden" class="name" name="review_id" value="">
                <input type="hidden" class="name" name="review_uid" value="">
                <input type="text" class="form-control" name="content" placeholder="回复">
                <button type="submit" class="btn btn-info u-gradualB">提交</button>
                <span class="hide"></span>
            </form>
            <div class="review"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2'],function(){
    });
</script>
</body>
</html>