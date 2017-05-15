define(function(require,exports,module){
	var basePath = location.origin;
    
	// 加载百度地图
	mapObj.centerAndZoom(new BMap.Point(114.066134,22.547313),13);// 初始化地图,设置城市和地图级别。
	mapObj.enableScrollWheelZoom(true); //启动缩放
    var globals = {}; //全局对象
	var geolocation = new BMap.Geolocation();
	var gc = new BMap.Geocoder(); //初始化，Geocoder类
	// 根据浏览器定位当前位置
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			$('#js-cityMap .city').find('em').html((r.address.city).substring(0,5)); //添加当前城市
			var myIcon = new BMap.Icon("static/image/icon/mymap_icon.gif", new BMap.Size(16,16));
			var marker = new BMap.Marker(r.point,{icon:myIcon});  // 创建标注
			mapObj.panTo(r.point);
			mapObj.addOverlay(marker);
			globals.myloca = r.point;
			gc.getLocation(r.point, function (rs) {
				console.log(rs);
			});
			// console.log('您的位置：'+r.point.lng+','+r.point.lat);
			sessionStorage.setItem('myloca',globals.myloca.lat+','+globals.myloca.lng);
		}else{console.log('failed'+this.getStatus());}        
		loadMap();
	},{enableHighAccuracy: true});


	// 添加工具控件
	mapObj.enableScrollWheelZoom();
	mapObj.enableInertialDragging();
	mapObj.enableContinuousZoom();
	mapObj.addControl(new BMap.CityListControl({ anchor: BMAP_ANCHOR_TOP_LEFT,offset: new BMap.Size(20,10)}));

	// 添加带有定位的缩放导航控件
	var navigationControl = new BMap.NavigationControl({
		anchor: BMAP_ANCHOR_BOTTOM_RIGHT,// 靠右下角位置
		type: BMAP_NAVIGATION_CONTROL_LARGE,// LARGE类型
		enableGeolocation: true// 启用显示定位
	});
	mapObj.addControl(navigationControl);
	// 添加定位控件
	var geolocationControl = new BMap.GeolocationControl();
	mapObj.addControl(geolocationControl);
    var PointData = new Vue({
      el: '#js-ListContainer',
      data: {ListCon: ''}
    }); 
    function loadMap(){
		// 添加电站地图坐标
		$.ajax({
			type : 'GET',
			url : 'cps/getChargePointStationMap'
		}).done(function(data){
			if (data.mapData.length) {
				$.each(data.mapData,function(i,k){
					var point = {};
					if (k.location != null) {
						point.lat =	Number(k.location.split(',')[0]); // 纬度
						point.lng = Number(k.location.split(',')[1]);// 经度
						// 创建图标
						var siteIcon = new BMap.Icon("static/image/icon/site_icon.png", new BMap.Size(18,25));
						var marker = new BMap.Marker(point,{icon:siteIcon});  // 创建标注
						mapObj.addOverlay(marker);
		                addEventInfo(k,marker);
					};
				});
		    	//调用电站距离
		    	Distance();
			};
		});
    };
	// 添加点击介绍
	function addEventInfo(res,marker){
		//位置 
		var myloca = sessionStorage.getItem('myloca');
		if (res.location != null) {
			var comment = '';
			for (var i = 0; i < res.avgScore; i++) { comment += '<i class="fa fa-star"></i>';};
			for (var i = 0; i < 5-res.avgScore; i++) { comment += '<i class="fa fa-star-o"></i>';};
			var Navigate = 'http://api.map.baidu.com/direction?origin=latlng:'+myloca.split(',')[0]+','+myloca.split(',')[1]+'|name:我的位置&destination='+res.location+'&mode=driving&region='+res.place+'&output=html&src=yourCompanyName|yourAppName';
			var pointA = new BMap.Point(myloca.split(',')[1],myloca.split(',')[0]);  // 创建点坐标A
			var pointB = new BMap.Point(res.location.split(',')[1],res.location.split(',')[0]);  // 创建点坐标B
			var getDistance = (mapObj.getDistance(pointA,pointB) / 1000).toFixed(2);
			var boxhtml ='<div class="m-summary" id="js-Summary"><div class="con animated fadeInUp"><h3 class="title">'+res.name+'</h3>'
	            		+'<div class="cen"><div class="comm">综合评价：'+comment+'</div><div class="point"><span class="all"><i>总</i><em>'+ res.totalSize+'</em>个</span><span class="spare"><i>闲</i><em>'+res.freeSize+'</em>个</span></div>'
	                	+'<p class="loc"><a href="javascript:void(0);"><i class="fa fa-flag"></i>'+res.place+'</a><span><i class="fa fa-map-marker"></i>'+ getDistance+'</span></p></div>'
	            		+'<div class="footer"><a class="btn" id="js-MapNavigation" href="'+Navigate+'"><i class="fa fa-map-marker"></i>导航</a><a class="btn" href="'+basePath+'/charging/product/index.jsp?d='+res.id+'"><i class="fa fa-file-alt"></i>详情</a></div>'
	        			+'</div><div class="Shield"></div></div>';
			// 点击地图坐标
			marker.addEventListener("click",function(e){
				$('#js-MapContainer').append(boxhtml);
			});
			// 点击详情
			$('#js-MapContainer').on('click','#js-Summary .Shield',function(){
				$('#js-Summary .con').removeClass('fadeInUp').addClass('fadeOutDown');
				setTimeout(function(){$('#js-Summary').remove();},1000);
			});
		};
	};
	// 点击切换到列表按钮
	$('#js-FilterType').on('click','.btn',function(e){
		if ($(this).hasClass('list')) {
			$('#js-homeSearch').submit();
		}else{
			$('#js-ListContainer').addClass('fadeOutUp hide');
			$('#js-MapContainer').removeClass('hide fadeOutUp').addClass('fadeInUp');
		    $(this).addClass('list').text('列表');
			Distance();
		};
	});
	// 点击一键导航
	$('#js-MapContainer').on('click','a.guide',function(){
		var myloca = sessionStorage.getItem('myloca');
		globals.guide = [];
		$.each($('#js-ListContainer').find('.dist'),function(i,k){
			var num = Number($(k).text().slice(0,$(k).text().length-2));
			globals.guide.push(num);
			if (num == Number(Math.min.apply(null,globals.guide))) {
				location.href = 'http://api.map.baidu.com/direction?origin=latlng:'+myloca.split(',')[0]+','+myloca.split(',')[1]+'|name:我的位置&destination='+$(k).data('city')+'&mode=driving&region='+$(k).prevAll('.place').text()+'&output=html&src=yourCompanyName|yourAppName';
			};
		});
	});
	// 地图搜索事件
	$('#js-homeSearch').on('submit',function(e){
		e.preventDefault();
		var text = $(this).find('input').val();
		Distance(text);
		// 延迟切换内容
		setTimeout(function(){
			$('#js-FilterType').find('.btn').removeClass('list').text('地图');
			$('#js-MapContainer').addClass('fadeOutUp hide');
			$('#js-ListContainer').removeClass('hide fadeOutUp').addClass('fadeInUp');
		},1000);
	});
	// 获取当前位置与电站距离
	function Distance(str){
		setTimeout(function(){
			var myloca = sessionStorage.getItem('myloca');
			var jsonStr = {currentLocation:myloca}
			if (str != undefined) {jsonStr.searchText = str;};
			if (myloca != null) {
				$.ajax({
					type : 'GET',
					url : 'cps/getCPSList',
					data:jsonStr,
				}).done(function(data){
					if (data.mapData) {
						// 加载数据列表
	    				PointData.ListCon = data.mapData;
	    				if (data.mapData.length) {
	    					$('#js-ListContainer').next('.listCon').html('')
	    				}else{
	    					$('#js-ListContainer').next('.listCon').html('<span class="hint">未搜索到该电站哦！</span>');
	    				};
					};
				}).error(function(res){
					console.log('error'+res);
				});
				// 列表点击导航
				$('.g-wrap').on('click','.loc .dh',function(){
					location.href = 'http://api.map.baidu.com/direction?origin=latlng:'+myloca+'|name:我的位置&destination='+$(this).prevAll('.dist').data('city')+'&mode=driving&region='+$(this).prevAll('.place').text()+'&output=html&src=yourCompanyName|yourAppName';
				});
			}
		},1000);
	};
});