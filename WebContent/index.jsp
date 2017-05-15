<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<jsp:include page="static/common/head.jsp"/>
<link rel="stylesheet" href="static/plugins/LArea/LArea.css">
<link rel="stylesheet" href="static/css/animate.css">
<meta name="author" content="dony">
<meta name="keywords" content="">
<meta name="description" content="">
<meta property="wb:webmaster" content="1c888e3b1b8655f5" />
<title>附近-巨能充</title>
</head>
<body>
<div class="g-wrap">
    <header class="m-header m-header-home">
        <div class="map" id="js-cityMap">
            <a href="javascript:void(0);" class="city"><em class="val">北京市</em><i class="fa fa-angle-down"></i></a>
            <input id="value" type="hidden" value="20,234,504">
        </div>
        <form id="js-homeSearch"class="search form-horizontal" method="get">
            <input class="form-control" type="text" name="search" placeholder="搜索电站名/电站地址" />
            <button type="submit" class="btn"><i class="fa fa-search"></i></button>
        </form>
        <div class="type" id="js-FilterType">
            <a class="btn list u-gradualB" href="javascript:void(0);">列表</a>
        </div>
    </header>
    <div class="g-container m-map animated" id='js-MapContainer'>
        <a class="guide btn" href="JavaScript:void(0)">一键导航</a>
    	<div id="js-mapObj" class="map"></div>
    </div>
    <section class="g-container m-listCon hide animated" id='js-ListContainer'>
        <article class="m-summary list" v-for='item in ListCon'>
            <div class="con animated fadeInUp">
                <h3 class="title"><a  :href="'product/index.jsp?d='+item.id">{{item.name}}<span>详情 >></span></a></h3>
                <div class="cen">
                    <div class="comm">评分：
                        <i class="fa fa-star"v-for='nub in item.avgScore'></i>
                        <i class="fa fa-star-o"v-for='nub in 5-item.avgScore'></i>
                    <span>{{item.avgScore*2}}分</span></div>
                    <div class="point"><span class="all"><i>总</i><em>{{item.totalSize}}</em></span><span class="spare"><i>闲</i><em>{{item.freeSize}}</em></span></div>
                    <p class="loc"><a href="javascript:void(0);">地址:<em class="place">{{item.place}}</em><i class="fa fa-map-marker"></i><em :data-city="item.location" class="dist">{{item.distance}}km</em><i class="fa fa-share dh"></i></a></p>
                </div>
            </div>            
        </article>
    </section>
    <div class="listCon"></div>
    <nav class="m-nav home home2" id="js-navigation">
        <a class="ch" href="charge/">
            <i class="icon"></i>
            <span>充电</span>
        </a>
        <a class="ne act hide" href="javascript:void(0);">
            <i class="icon"></i>
            <span>附近</span>
        </a>
        <a class="u" href="user/">
            <i class="icon"></i>
            <span>我的</span>
        </a>
    </nav>
</div>
<!-- 百度地图接口 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=X40I2az9TIWsO6uwcvnabxSe"></script>
<script type="text/javascript">
    // 加载百度地图 
    var mapObj = new BMap.Map("js-mapObj");
	seajs.use(['JS/global.js?v=0.2','Plugins/vue.js','JS/index.js','Plugins/LArea/LArea.js','Plugins/LArea/LAreaData.js'],function (require,exports,module) {
        var area = new LArea();
        area.init({
            'trigger': '#js-cityMap .city', //触发选择控件的文本框，同时选择完毕后name属性输出到该位置
            'valueTo': '#value', //选择完毕后id属性输出到该位置
            'keys': {
                id: 'id',
                name: 'name'
            }, //绑定数据源相关字段 id对应valueTo的value属性输出 name对应trigger的value属性输出
            'type': 1, //数据源类型
            'data': LAreaData //数据源
        });
        area.value=[1,13,4];//控制初始位置，注意：该方法并不会影响到input的value
        var mm = require;
        var user = localStorage.getItem("user");
        if(user == null){
	         if (mm.GetUrl('code') == null) {
	             location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb36981de3b87bc&redirect_uri=http://mobile.chinajune.com/charging/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
	         }else{
                // console.log('传过来的code'+mm.GetUrl('code'));
	             $.ajax({
	                 type:"post",
	                 url:'login/autoLogin',
	                 data:{code:mm.GetUrl('code')},
	                 success:function(res){
                        // alert('传过user'+JSON.stringify(res));
                         if (res.user) {
                             localStorage.setItem('user',JSON.stringify(res.user));
	                     }else{
	                     }
	                 },
	                 error:function(res){
                        alert('连接服务错误');
                        // alert('error'+JSON.stringify(res));
	                     // console.log('error'+JSON.stringify(res));
	                 }
	             });
	         };	
        }
    });
</script>
</body>
</html>
