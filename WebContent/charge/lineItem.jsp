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
<title>订单详情--巨能充</title>
<link href="static/css/charge.css?v=0.2" rel="stylesheet"/>
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-pay">
        <h1 class="title"><c:out value="${res.name }"/></h1>
        <form action="" method="post" class="form-horizontal pay" id="js-chargePay">
            <input type="hidden" name="id" class="payid" value="${res.id }"/>
            <input type="hidden" name="uid" class="uid" value=""/>
        	<ul>
        		<li>订单号：<em><c:out value="${res.trade_no }"/></em></li>
        		<li>开始时间：<em><c:out value="${res.start_time }"/></em></li>
        		<li>结束时间：<em><c:out value="${res.end_time }"/></em></li>
        		<li>充电时长(h)：<em><c:out value="${res.time_count }"/></em></li>
        		<li>已充电量(kwh)：<em><c:out value="${res.degree }"/></em></li>
        	</ul>
        	<ul class="sum" id="js-payType">
        		<li>合计金额(元)：<em class="money"><c:out value="${res.money }"/></em></li>
        		<li class="type"><strong>默认支付方式：</strong>
        			<a data-id="wx" class="act wx" href="javascript:void(0)"><img class="icon" src="static/image/icon/wxpay_icon.png" alt="">微信支付</a>
        		</li>
        	</ul>
        	<button type="button" class="btn u-gradualB">确认支付</button>
        </form>
    </div>
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2',function(require,moduel){
        var user = localStorage.getItem('user');
        if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
		var mm = require;
		// 点击选择字符方式
		$('#js-payType').on('click','.type a',function(){
			$(this).addClass('act').siblings('a').removeClass('act');
		});
		// 点击确定支付
		$('#js-chargePay').on('click','button.btn',function(){
            var payid = $('#js-chargePay').find('.payid').val();
            if (payid != '') {
                $.ajax({
                    type:'get',
                    url:'wx/getJSPayParam',
                    data:{uid:user.id,payid:payid}
                }).done(function(res){
                    if (res) {
                        InvokePay(res,'#js-chargePay');
                    };
                });
            };
			return false;
		});

        /*InvokePay();
         *调用微信支付平台函数
         *data 数据对象
         */
        function InvokePay(data,id){
            // 兼容微信平台
            if (typeof WeixinJSBridge == "undefined"){
               if( document.addEventListener ){
                   document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
               }else if (document.attachEvent){
                   document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                   document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
               }
            }else{
               onBridgeReady();
            };
            // 调用微信支付
            function onBridgeReady(){
                WeixinJSBridge.invoke('getBrandWCPayRequest',data,function(res){
                    $(id).find('uid').val(user.id);
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        $(id).attr('action','charge/getPayDetails/1');
                        // location.href = '/charging/charge/payDone.jsp?payid='+payid;
                    }else{
                        $(id).attr('action','charge/getPayDetails/0');
                        // location.href = '/charging/charge/payBust.jsp?payid='+payid;
                    };
                    $(id).submit();
                    // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。 
                }); 
            }
        };
	});

</script>
</body>
</html>