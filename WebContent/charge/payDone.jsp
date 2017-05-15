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
    <div class="g-container m-pay status">
      <h1 class="title done"><i></i>支付完成</h1>
    	<ul class="pay" id="js-payDone">
        <li>订单号：<em><c:out value="${res.trade_no }"/></em></li>
        <li>交易时间：<em><c:out value="${res.end_time }"/></em></li>
        <li>支付金额(元)：<em><c:out value="${res.money }"/></em></li>
    	</ul>
      <a class="btn u-gradualB" id="js-details" href="javascript:void(0)">查看详情</a>
      <a class="btn u-gradualB" href="charge/ReviewPoint.jsp?sid=${res.station_id }">去评价</a>
    </div>
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2',function(require,moduel){
		var mm = require;
		// 全局
    var global = {};
        global.jsonStr =  {
           "touser":"OPENID",
           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
           "url":"http://weixin.qq.com/download",            
           "data":{
                   "first": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
                   "keynote1":{
                       "value":"巧克力",
                       "color":"#173177"
                   },
                   "keynote2": {
                       "value":"39.8元",
                       "color":"#173177"
                   },
                   "keynote3": {
                       "value":"2014年9月22日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"欢迎再次购买！",
                       "color":"#173177"
                   }
           }
        };
        
        $('#js-details').on('click',function(){
            $.ajax({
                type:'post',
                url:'https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN',
                data:global.jsonStr
            }).done(function(res){
                console.log(res);
            })

        });

	});

</script>
</body>
</html>