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
<title>评价电站-巨能充</title>
<link rel="stylesheet" href="static/css/charge.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-product">
		<div class="comment m-review" id="js-Evaluation">
			<dl class="ulist"></dl>
			<form class="form-horizontal form" action="" role="form" method="post" id="js-review">
				<h3 class="title">评价电站：</h3>
				<div class="c">
	                <textarea name="content" class="form-control" rows="6" placeholder="请输入你的点评..."maxlength="300"></textarea>
	                <span class="hint"></span>
	                <span class="t">还可输入300字</span>
				</div>
				<div class="kps">综合评分：<i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i></div>
                <button type="submit" class="btn btn-info u-gradualB">提交</button>
            </form>
		</div>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/charge.js?v=0.2'],function(){
	});
</script>
</body>
</html>