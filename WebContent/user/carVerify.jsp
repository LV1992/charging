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
<title>车主认证--巨能充</title>
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container">
    	<div class="con m-carverify">
	      <form class="form-horizontal m-form row" action="" role="form" id="js-carVerify" method="post" enctype="multipart/form-data">
	        <div class="form-group model">
	          <label class="col-md-2 control-label">品牌车型：</label>
	          <div class="col-md-10">
	          	<div class="list">
		          <input type="text" data-type='*' value="特斯拉" name="model" class="form-control"placeholder="请为爱车选着品牌"><i class="fa fa-angle-down"></i>
		            <ul>
		            	<li>特斯拉</li>
		            	<li>宝马</li>
		            	<li>奔驰</li>
		            	<li>大众</li>
		            	<li>保时捷</li>
		            	<li>兰博基尼</li>
		            	<li>奥迪</li>
		            	<li>法拉利</li>
		            	<li>菲亚特</li>
		            	<li>劳斯莱斯</li>
		            	<li>布加迪</li>
		            	<li>捷豹</li>
		            	<li>克莱斯勒</li>
		            	<li>福特</li>
		            	<li>悍马</li>
		            	<li>凯迪拉克</li>
		            	<li>林肯</li>
		            </ul>         		
	          	</div>
	          	<div class="list">
		          <input type="text" data-type='*' value="火焰红" name="color" class="form-control"placeholder="请选择颜色"><i class="fa fa-angle-down"></i>
		            <ul>
		            	<li>珍珠白</li>
		            	<li>深海蓝</li>
		            	<li>钛空金</li>
		            	<li>晶钻蓝</li>
		            	<li>月光银</li>
		            	<li>象牙白</li>
		            	<li>水晶紫</li>
		            	<li>碧玉黑</li>
		            	<li>火焰红</li>
		            </ul>	          		
	          	</div>
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-2 control-label">车型系列：</label>
	          <div class="col-md-10">
	            <input type="text" data-type='*' name="car_type" class="form-control"placeholder="请输入爱车车型">
	          </div>
	        </div>
	        <div class="form-group license">
	          <label class="col-md-2 control-label">车牌号:</label>
	          <div class="col-md-10">
	          	<div class="list">
		            <input type="text" data-type='*' name="map" class="form-control" value="粤"><i class="fa fa-angle-down"></i>
		            <ul>
						<li>京</li>
		            	<li>津</li>
		            	<li>沪</li>
		            	<li>渝</li>
		            	<li>冀</li>
		            	<li>豫</li>
		            	<li>粤</li>
		            	<li>云</li>
		            	<li>辽</li>
		            	<li>黑</li>
		            	<li>湘</li>
		            	<li>皖</li>
		            	<li>鲁</li>
		            	<li>新</li>
		            	<li>苏</li>
		            	<li>浙</li>
		            	<li>赣</li>
		            	<li>鄂</li>
		            	<li>桂</li>
		            	<li>甘</li>
		            	<li>晋</li>
		            	<li>蒙</li>
		            	<li>陕</li>
		            	<li>吉</li>
		            	<li>闽</li>
		            	<li>贵</li>
		            	<li>青</li>
		            	<li>藏</li>
		            	<li>川</li>
		            	<li>宁</li>
		            	<li>琼</li>
		            </ul>	          		
	          	</div>
	            <input type="text" data-type='*' name="plate_no" class="form-control"placeholder="请输入车牌号">
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-2 control-label">车架号：</label>
	          <div class="col-md-10">
	            <input type="text" data-type='*' name="car_frame_no" class="form-control"placeholder="请输入车辆识别号">
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-2 control-label">发动机号：</label>
	          <div class="col-md-10">
	            <input type="text" data-type='*' name="engine_no" class="form-control"placeholder="请输入发动机号">
	          </div>
	        </div>
	        <div class="form-group image" id="js-updateImg">
	        	<div class="upimg">
	        		<a class="up" href="javascript:void(0)">请上传机动车行驶证</a>
	        		<div class="img" id="js-license"></div>
		        	<img src="static/image/banner1.jpg"class="picture pic hide" alt="行驶证">
	        	</div>
	        	<div class="upimg">
	        		<a class="up" href="javascript:void(0)">请上传机动车行驶证</a>
	        		<div class="img" id="js-license1"></div>
		        	<img src="static/image/banner1.jpg"class="picture pic1 hide"alt="行驶证">
	        	</div>
	        	<a class="btn btn-default hide" href="javascript:void(0)">修改证件</a>
	        </div>
	        <button type="submit" class="btn btn-primary u-gradualB">提交</button>
	      </form>
	    </div> 
    </div>
</div>
<script type="text/javascript">
	seajs.use(['JS/global.js?v=0.2','Plugins/uploader/webuploader.js','JS/user.js'],function() {
		
	});
</script>
</body>
</html>