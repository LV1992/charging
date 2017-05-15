define(function(require,exports,module){
	var basePath = location.origin+'/charging/';
	var global = {}; //全局对象
	var mm = new require('global');
	require('Plugins/MD5');
	// 点击登录
	$('#js-login').on('submit',function(e){
		e.preventDefault();
		if (!mm.verifyCode('#js-login')) {
			return false;
		};
		var daStr = mm.serializeObject('#js-login');
			daStr.password = MD5(mm.serializeObject('#js-login').password);
		$.ajax({
			type:'POST',
			url:basePath+'login/Login',
			data:daStr
		}).done(function(res){
			localStorage.removeItem("user");
			if (res.msg == '账号不存在') {
				$('#js-login').find('input[name="userName"]').next('.hint').text(res.msg);
			}else if(res.msg == '密码错误'){
				$('#js-login').find('input[name="password"]').next('.hint').text(res.msg);
			}else if(res.msg == '成功'){
				localStorage.setItem("user", JSON.stringify(res.user));
				window.history.back(); //返回上一页
			};
		});
	});
	var user = localStorage.getItem("user");
    if(user == null){
        location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeb36981de3b87bc&redirect_uri=http://mobile.chinajune.com/charging/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
    }
});