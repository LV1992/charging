<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<jsp:include page="../static/common/head.jsp"/>
<meta name="author" content="dony">
<meta name="keywords" content="">
<meta name="description" content="">
<title>支付完成--巨能充</title>
<link href="static/css/charge.css?v=0.2" rel="stylesheet"/>
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-pay status" id="js-repay">
        <h1 class="title bust"><i></i>支付失败</h1>
        <a class="btn u-gradualB pay" data-id="${res.id }" href="javascript:void(0)">重新支付</a>
    </div>
    <form action="" method="post" class="form-horizontal pay" id="js-chargePay">
        <input type="hidden" name="id" class="payid" value="${res.id }"/>
        <input type="hidden" name="uid" class="uid" value=""/>
    </form>
</div>
<script type="text/javascript">
    seajs.use(['JS/global.js?v=0.2','JS/user.js?v=0.2'],function(require,moduel){

    });
</script>
</body>
</html>