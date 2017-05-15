<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
    <base href="<%= basePath %>"/>
    <meta name="author" content="dony">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!-- 360 使用Chrome内核 -->
	<meta name="renderer" content="webkit">
	<!-- 禁止百度转码 -->
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	
	<!-- 启用全屏 -->
	<meta name="apple-mobile-web-app-capable" content="yes">
	<!-- UC强制竖屏，全屏，应用模式 -->
	<meta name="screen-orientation" content="portrait">
	<meta name="full-screen" content="yes">
	<meta name="browsermode" content="application">
	<!-- QQ强制竖屏，全屏，应用模式 -->
	<meta name="x5-orientation"content="portrait">
	<meta name="x5-fullscreen" content="true">
	<meta name="x5-page-mode" content="app">
	
	<link rel="shortcut icon" href="static/image/logo_icon.ico"type="image/x-icon">
	<!-- bootstrap css -->
	<link href="static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="static/plugins/bootstrap/css/font-awesome.min.css" rel="stylesheet"/>
	<link href="static/css/common.css?v=0.2" rel="stylesheet"/>
	<!-- 加载seajs -->
	<script src="static/plugins/seajs/sea.js?v=0.2"></script>
	<script src="static/js/config.js?v=0.2"></script>