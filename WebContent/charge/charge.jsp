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
<title>充电--巨能充</title>
<link href="static/css/animate.css" rel="stylesheet"/>
<link href="static/css/charge.css?v=0.2" rel="stylesheet"/>
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-goingCharge" id="js-goingCharge">
    	<div class="canvas">
    		<canvas class="diffuse u-gradualB" height="280" id="js-diffuse"></canvas>
    		<canvas width="160" height="160" class="scan" id="js-Scan"></canvas>
    		<div class="type">
    			<span class="dc"><p class="e"><em>00</em>%</p>SOC</span>
    			<span class="ac hide"><em class="e">00</em>已充电量(KWH)</span>
    		</div>
    	</div>
    	<div class="con">
    		<ul class="detection">
    			<li class="list" data-id="i_out">
    				<em></em>
    				<span>电流(A)</span>
    			</li>
    			<li class="list" data-id="v_out">
	    			<em></em>
	    			<span>电压(V)</span>    				
    			</li>
    			<li class="list" data-id="charged_time">
	    			<em class="t"></em>
	    			<span>充电时间</span>    				
    			</li>
    		</ul>
    		<a href="javascript:void(0)" class="btn u-gradualB stop">停止</a>
    		<form class="form-horizontal" action="charge/getPayDetails/2" method="post" id="js-Payment">
    			<input type="hidden" name="uid" value="" />
    			<input type="hidden" name="id" value="" />
    		</form>
			<div class="load hide"><img src="static/image/openload.gif" alt="加载中..."></div>
    	</div>
    	<div class="basics" id='js-datails'>
    		<div class="datails animated">
	    		<h3 class="title">0号充电桩（直流）</h3>
	    		<ul>
	    			<li data-id="v">额定电压(V)：<em>0</em></li>
	    			<li data-id="i">额定电流(A)：<em>0</em></li>
	    			<li data-id="w">最大功率(W)：<em>0</em></li>
	    			<li data-id="price">充电费(度/元)：<em>0</em></li>
	    			<li data-id="service_fee">服务费(元)：<em>0</em></li>
	    			<li data-id="parking_fee">停车费(元)：<em>0</em></li>
	    		</ul>
    			<a class="btn u-gradualB begin"href="javascript:void(0)">充电</a>
    			<a class="btn u-gradualB"href="JavaScript:history.back()">返回</a>
    			<div class="load hide"><img src="static/image/openload.gif" alt="加载中..."></div>
    		</div>
    	</div>
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','JS/charge.js'],function(){
        CanvasDiffuse('js-diffuse');
        // 圆圈扩散动画
        function CanvasDiffuse(id){
		    var oC = document.getElementById(id);
		    var oGC = oC.getContext('2d');
		    var setArr = [];   //收集所有元素的集合(绘制的图形)
		    setInterval(function(){
		        oGC.clearRect(0,0,oC.width,oC.height);		        
		        for(var i=0;i<setArr.length;i++){
		            setArr[i].r += 1.6;
		            setArr[i].c4 -= 0.01;
		            if( setArr[i].c4 <= 0 ){
		                setArr.splice(i,1);
		            }
		        }
		        for(var i=0;i<setArr.length;i++){
		            oGC.beginPath();
		            oGC.fillStyle = 'rgba(122, 220, 242,'+setArr[i].c4+')';
		            oGC.moveTo(setArr[i].x , setArr[i].y);
		            oGC.arc( setArr[i].x , setArr[i].y , setArr[i].r ,0,360*Math.PI/180,false);
		            oGC.closePath(); oGC.fill();
		        }    
		    },1000/9);
		    setInterval(function(){
		        var x = oC.width/2;
		        var y = oC.height/2;
		        setArr.push({ x : x, y : y, r : 42, c4 : 1 });
		    },1500);
		};
		CanvasCoil('js-Scan');
		// 圆扫描动画
		function CanvasCoil(id){
		    var ctx = document.getElementById(id).getContext('2d');
	        var angle = 1,tmd = 1;
	        setInterval(function(){
	            angle+=1;tmd -= 1/540;
	            ctx.sector(80,80,68,0,angle,tmd.toFixed(2)).fill();
	            if(angle == 370){
	            	angle = 1,tmd = 1; ctx.clearRect(0,0,320,320);
	            }
	        },13);
			CanvasRenderingContext2D.prototype.sector = function(x,y,r,angle1,angle2,t){
	        	this.fillStyle = 'rgba(11, 180, 235, '+t+')';
	            this.save(); this.beginPath();
	            this.moveTo(x,y);
	            this.arc(x,y,r,angle1*Math.PI/180,angle2*Math.PI/180,false);
	            this.closePath(); this.restore();
	            return this;
	        }
		};
	});
</script>
</body>
</html>