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
<link rel="stylesheet" href="static/css/charge.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="m-bookSelect" id="js-Booking">
		<div class="time">
			<h3 class="title">请选择预约时间：</h3>
			<a data-time='10' class="btn" href="JavaScript:void(0)">10分钟</a>
			<a data-time='20' class="btn u-gradualB" href="JavaScript:void(0)">20分钟</a>
			<a data-time='30' class="btn" href="JavaScript:void(0)">30分钟</a>
			<a data-time='40' class="btn" href="JavaScript:void(0)">40分钟</a>
			<a data-time='50' class="btn" href="JavaScript:void(0)">50分钟</a>
			<a data-time='60' class="btn" href="JavaScript:void(0)">60分钟</a>
		</div>
		<ul class="details" id="js-details">
			<li><span>预约充电时间：</span><em class="t"></em></li>
			<li data-id="m"><span>预约电桩序号：</span><em></em>号桩</li>
			<li data-id="map"><span>预约充电地址：</span><em></em></li>
		</ul>
		<div class="tips">
			<button type="button" class="btn u-gradualB">立即预约</button>
			<h3>温馨提示：</h3>
			<p>1.请在预约时间内到达电桩充电，超出预约时间                          将自动取消预约订单；</p>
			<p>2.预约只能提前预约1小时（包含1小时）。</p>
		</div>
		<div class="load hide"><img src="static/image/openload.gif" alt="加载中..."><p class="htip"></p></div>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2'],function(require,exports,module){
		var mm = require;
		var user = localStorage.getItem('user');
    	if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
    	var globals = {}; //全局对象
    	// 显示数据
    	$('#js-details').find('li').eq(1).find('em').text(getQueryString('m'));
    	$('#js-details').find('li').eq(2).find('em').text(getQueryString('map'));

		CreateTiem('#js-details .t',20);
		// 选择当前标签
		$('#js-Booking .time').on('click','a',function(){
			$(this).addClass('u-gradualB').siblings('a').removeClass('u-gradualB');
			CreateTiem('#js-details .t',$(this).data('time'));
		});
		//点击立即预约按钮提交数据
		$('#js-Booking .tips').on('click','.btn',function(){
			globals.count = 1;
			var times = $('#js-Booking .time').find('.u-gradualB').data('time');
			makeAppointment(times);
		});
		function makeAppointment(times){
			var status = false; //返回成功与否
			var statu = ''; //返回失败内容
			$.ajax({
				type:'post',
				url:'appointment/makeAppointment',
				data:{cpid:mm.GetUrl('pid'),uid:user.id,orderTime:times,count:globals.count},
				dataType:'json',
				beforeSend:function(res){
		        	$('#js-Booking').find('.load').removeClass('hide');
		        },
			}).done(function(res){
				if (res.res[0] == '-1') {
					globals.count++;
		            if (globals.count < 50) {
		          	  	setTimeout(function(){makeAppointment(times);},1000);
		            }else{
		              $('#js-Booking').find('.load').addClass('hide');
		            };
		            return false;
				};
				$('#js-Booking').find('.load').addClass('hide');
				switch(res.res[0]){
					case 0 : status = true; break;
					case 1 : statu = '存在未完成支付,<a class="t-blue" href="user/MyIndent.jsp?t=0">去支付</a>'; break;
					case 2 : statu = '完成当前充电再预约'; break;
					case 3 : statu = '通信超时'; break;
					case 4 : statu = '充电机故障'; break;
					case 5 : statu = '未插充电枪'; break;
					case 6 : statu = '充电桩正在充电（预约失败）'; break;
					case 7 : statu = '充电桩被预约'; break;
					case 8 : statu = '有未完成的预约'; break;
					case 9 : statu = '电桩不存在或在充电'; break;
					default : statu = '充电桩未响应';
				};
				if (status) {
					mm.MDialog({
						type:'dialog',con:'恭喜您预约成功！是否返回首页？',time:3000,
						fun:function(){location.href = location.origin+'/charging';},
						undo:function(){location.href = location.origin+'/charging/user/myBook.jsp#ord';}
					});										
				}else{
					// 调用警告提示框
			    	mm.UIAlertView({id:'.g-wrap',type:'info',time:60000,con:statu,});
				};				
			}).error(function(res){
				console.log(res+'error');
			});
		};
		// 创建显示预约时间
		function CreateTiem(id,n) {
            var Time=new Date();
            Time.setMinutes(Time.getMinutes() + n);
            var minutes = Time.getMinutes();
            if (minutes < 10) {
                minutes = '0'+minutes;
            };
            var Timecon = Time.getFullYear()+'-'+(Time.getMonth()+1)+'-'+Time.getDate()+'&nbsp;'+Time.getHours()+':'+minutes;
            $(id).html(Timecon);                         
		};
		// 获取url参数
		function getQueryString(key){
	        var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
	        var result = window.location.search.substr(1).match(reg);
	        return result?decodeURIComponent(result[2]):null;
	    }
	});
</script>
</body>
</html>