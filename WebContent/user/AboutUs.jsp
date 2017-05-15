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
<title>巨能充</title>
<link rel="stylesheet" href="static/css/user.css?v=0.2">
</head>
<body>
<div class="g-wrap">
    <div class="g-container m-settings" id="js-AboutUs">
        <div class="con hide" id="bundle">
            <form class="form-horizontal m-form row" action="" id="js-bundle" role="form" method="post">
                <div class="form-group">
                  <div class="col-md-10">
                    <input type="text" name="name" class="form-control"placeholder="请输入要绑定的用户名">
                  </div>
                  <label class="col-md-2 control-label fa fa-user"></label>
                </div>
                <div class="form-group">
                  <div class="col-md-10">
                    <input type="text" name="tel" class="form-control"placeholder="请输入绑定账号的手机号码" maxlength="11">
                  </div>
                  <label class="col-md-2 control-label fa fa-mobile"></label>
                </div>
                <div class="form-group verify">
                  <div class="col-md-10">
                    <input type="text" name="ver" class="form-control"placeholder="请输入短信验证码">
                  </div>
                  <a href="javascript:void(0)" class="btn btn-default">获取验证码</a>
                </div>
                <button type="submit" class="btn btn-primary u-gradualB">绑定</button>
              </form>
        </div>
        <div class="con about hide" id="about">
            <h3 class="title"><img src="static/image/logo.png" alt="中工巨能">深圳市中工巨能科技有限公司</h3>
            <p>深圳市中工巨能科技有限公司是一家能源环保领域集研发、生产、销售于一体的科技公司，以持续创新为驱动力，致力于为新能源应用、综合管理提供整体技术与产品解决方案。公司由众多行业内经验丰富的投资人和技术专家创立。我们将持续为电信运营商、新能源汽车制造商、建筑能源服务商等提供优质的产品和服务。公司主要产品包括BMS、充电桩、中小型储能系统和能效管理系统等。相关业务主要集中在华南、华中和华东地区，同时具备优质的售后服务体系，全天候响应客户的服务需求。</p>
            <footer class="footer">
                <div>CopyRight© 2016</div>
                <div class="firm">深圳市中工巨能科技有限公司</div>
            </footer>
        </div>
        <div class="con feedback hide" id="feedback">
            <form class="form-horizontal m-form " id="js-feedback" action="" role="form" method="post">
                <h3 class="title">意见反馈</h3>
                <textarea name="content" class="form-control" rows="6" placeholder="为了更好的服务到您，请留下您的宝贵意见或建议！" maxlength="300"></textarea>
                <span><em>0</em>/300 字</span>
                <button type="submit" class="btn btn-primary u-gradualB">提交</button>
            </form>
        </div>
        <div class="con hide" id="help">配置</div>
    </div>
</div>
<script type="text/javascript">
	seajs.use('JS/global.js?v=0.2',function(require,exports,moduel){
		var typeid = location.hash;
        var mm = require;
        var user = localStorage.getItem('user');
        if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
        // 判断显示的页面内容
		if (typeid) {
			$('#js-AboutUs').find(typeid).removeClass('hide');
		}else{
            $('#js-AboutUs').find('#about').removeClass('hide');
        };
        // 点击获取验证码
        $('#js-bundle .verify').find('.btn').on('click',function(){
           var tel = $('#js-bundle').find('input[name="tel"]').val();
           console.log(tel);
        });
        // 绑定账号提交
        $('#js-bundle').on('submit',function(){
            var name = $('#js-bundle').find('input[name="name"]');
            if (name.val() == '') {
                name.after('<span class="hint">请输入用户名</span>');
                return false;
            };
            var jsonStr = mm.serializeObject('#js-bundle');
            // 绑定账号数据提交
            $.ajax({
                type:'post',
                url:'user/bindUser',
                data:{wxuid : user.id,userName : jsonStr.name},
                success:function(res){
                    if (res.res) {
                        localStorage.setItem("user", JSON.stringify(res.user));
                        mm.MDialog({con:'提交成功，谢谢您的提交的建议',type:'hint',time:2000,undo:function(){location.href = '/charging/';}});
                    }else{ mm.MDialog({con:'提交失败，请重新尝试！',type:'hint',time:1000}); };
                },
                error:function(res){console.log('error'+res);},
            }); return false;
        });
        // 当前输入内容显示
        $('#js-feedback').find('.form-control').bind('input propertychange',function(){
            $('#js-feedback span').find('em').text($(this).val().length);
        });
        // 反馈意见提交
        $('#js-feedback').on('submit',function(){
            var text = $(this).find('textarea');
            if (text.val() == '') {
                text.after('<span class="hint">请输入您的宝贵建议...</span>'); return false;
            };
            text.next('.hint').remove();
            var jsonStr = mm.serializeObject('#js-feedback');
            jsonStr.user_name = user.nick_name; jsonStr.version = '';
            // 意见数据提交
            $.ajax({
                type:'get',
                url:'feedback/addFeedback',
                data:{jsonStr:encodeURI(JSON.stringify(jsonStr))},
                success:function(res){
                    if (res.res) {
                        mm.MDialog({con:'提交成功，谢谢您的提交的建议',time:2000,undo:function(){location.href = '/charging/';}});
                    }else{ mm.MDialog({con:'提交失败，请重新尝试！',time:1000}); };
                },
                error:function(res){console.log('error'+res);},
            }); return false;
        });
	});
</script>
</body>
</html>