<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<base href="<%= basePath %>"/>
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
<link rel="shortcut icon"href="static/image/logo_icon.ico"type="image/x-icon">
<!-- bootstrap css -->
<link href="static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="static/plugins/bootstrap/css/font-awesome.min.css" rel="stylesheet"/>
<link href="static/css/common.css?v=0.2" rel="stylesheet"/>
<link href="static/css/charge.css?v=0.2" rel="stylesheet"/>
<meta name="author" content="dony">
<meta name="keywords" content="">
<meta name="description" content="">
<title>巨能充</title>
</head>
<body class="charging_bg">
<div class="g-wrap">
    <div class="g-container m-charge" id="js-charge">
        <h1 class="title">请输入充电桩序列号</h1>
        <div class="items CDKey">
            <form class="form-horizontal form" id="js-CDKey">
                <input name="cp_id" class="form-control" type="text" placeholder="请输入充电桩序列号">
                <button type="submit" class="btn u-gradualB">确定</button>
            </form>
        </div>
        <div class="way" id="js-SwitchWay"><a id="js-QRcode" class="btn" href="JavaScript:void(0);">使用微信扫描二维码</a></div>
    </div>
    <nav class="m-nav home home2">
        <a class="ch act hide" href="javascript:void(0);">
            <i class="icon"></i>
            <span>充电</span>
        </a>
        <a class="ne" href="./">
            <i class="icon"></i>
            <span>附近</span>
        </a>
        <a class="u" href="user/">
            <i class="icon"></i>
            <span>我的</span>
        </a>
    </nav> 
</div>
<div class="particles-bg" id="js-particles"></div>
<script type="text/javascript"src="static/plugins/jquery-1.10.2.min.js"></script>
<script type="text/javascript"src="static/plugins/particles/particles.min.js"></script>
<script type="text/javascript"src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function(){
    var user = localStorage.getItem('user');
    if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
    var basePath = location.origin;
    $.ajax({
        type:'GET',
        url:basePath+'/charging/wx/getSignature',
        data:{url:location.href}
    }).done(function(res){
        // 微信配置
        wx.config({
            debug: false,
            appId: 'wxbeb36981de3b87bc',
            timestamp: res.timestamp,
            nonceStr: res.nonceStr,
            signature: res.signature,
            jsApiList: [
                'checkJsApi',
                'getLocation',
                'scanQRCode',
            ]
        });
        // 微信加载
        wx.ready(function (res) {
            // 扫描二维码并返回结果
            function scanQRCode(){
                // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
                wx.checkJsApi({
                  jsApiList: [
                    'scanQRCode',
                    'getLocation'
                  ],
                  success: function (res) {
                    console.log(JSON.stringify(res));
                  }
                });
                $('#js-QRcode').on('click',function(){
                    // 调用微信二维码扫描
                    wx.scanQRCode({
                        needResult: 1,
                        desc: 'scanQRCode desc',
                        success: function (res) {
                            if (res.resultStr) {
                                chargingFun(res.resultStr);
                            };
                        }
                    });
                });
            };
            scanQRCode();                
        });
        // 微信处理失败
        wx.error(function (res) {
            console.log(res);
            alert('微信错误！'+JSON.stringify(res));
        });
    });
    var cp_id = $('#js-CDKey').find('input[name="cp_id"]');
    //  在充电中
    var runCharge = localStorage.getItem('runCharge');
    if(runCharge != null){
        MDialog({
            type:'dialog',//提示类型
            con:'您正在'+runCharge.split(',')[1]+'桩充电,要去查看吗？',//提示内容
            fun:function(){
                if (runCharge.split(',')[0] == '1') {
                    chargingFun(runCharge.split(',')[1]);
                };
            },//确定回调
            undo:function(){}//取消回调
        });
    };
    // 输入序列号提交
    $('#js-CDKey').on('submit',function(e){
        e.preventDefault();
        if (cp_id.val() != '') {
            cp_id.next('.hint').text('');
            chargingFun(cp_id.val());
        }else{
            cp_id.after('<span class="hint">请输入充电桩序列号</span>'); 
        };
        return false;
    });
    // 充电接口调用函数
    function chargingFun(cpid){
        $.ajax({
            type:'POST',
            url:basePath+'/charging/preCharge/scanQrcode',
            data:{uid:user.id,cpid:cpid}
        }).done(function(res){
            // alert('cpid:'+JSON.stringify(res));
            if (res.res) {
                cp_id.next('.hint').text('');
                if (res.forwardPage == '0') {
                    location.href = basePath+'/charging/user/MyIndent.jsp?t=0';
                }else if(res.forwardPage == '1') {
                    location.href =basePath+'/charging/charge/charge.jsp?p='+cpid;
                }else if(res.forwardPage == '2') {
                    location.href =basePath+'/charging/charge/charge.jsp?p='+cpid;
                }else{
                    var warning = '';
                    switch(res.forwardPage){
                      case '3':
                        warning = '您在别的充电桩充电中...'; break;
                      case '4':
                        warning = '电桩正在充电中...'; break;
                      default : warning = '网络错误...';
                    };
                    // 调用警告提示框
                    UIAlertView({
                        id:'.g-wrap',
                        type:'warning',
                        time:4500,
                        con:warning,
                    });
                };
            }else if (res.failCase == 1) {
                // 调用警告提示框
                UIAlertView({
                    id:'.g-wrap',
                    type:'warning',
                    time:2000,
                    con:'用户数据错误',
                });
                setTimeout(function(){
                    // 用户不存在
                    localStorage.removeItem('user'); //清除用户数据
                    location.href = '/charging/';
                },2000);
            }else if (res.failCase == 2) {
                // 输入充电桩错误
                cp_id.after('<span class="hint">请输入正确的充电桩序列号</span>');
            }else{
                cp_id.after('<span class="hint">输入充电桩序列号错误</span>');
            };
            console.log(res);
        }).error(function(res){
            console.log('error'+res);
        });
    };
    
    
    
    // 警告提示框
    function UIAlertView(el){
        if (el.type == undefined) {el.type = 'success';};
        var box = '<dialog id="js-AlertDialog" class="alert alert-'+el.type+' alert-dismissible m-alert fadeInUpBig" role="alert">';
        box += '<button type="button" class="close">&times;</button>'+el.con+'</dialog>';
        $(el.id).append(box);
        $('#js-AlertDialog').slideDown();
        $('#js-AlertDialog').on('click','.close',function(){
            $('#js-AlertDialog').removeClass('fadeInUpBig').addClass('fadeOutDownBig');
            setTimeout(function(){$('#js-AlertDialog').remove();},1000);
        });
        if (el.time != undefined) {
            setTimeout(function(){
                $('#js-AlertDialog .close').click();
            },el.time);
        };
    };
    /*
     *模态对话框
     *MDialog对象调用
     *title标题con内容fun确定回调
     **/
    function MDialog(e){
        if (!e) {return false;};
        var btn = '<div class="foter"><button type="button"  class="btn btn-info undo ">取消</button></div>';
        if(e.type == 'hint') {
            btn = '';
        }else if (e.type == 'dialog') {
            btn = '<div class="foter"><button type="button"  class="btn btn-default undo ">取消</button><button type="button" class="btn btn-success confirm">确定</button></div>';
        };
        var header = '<button type="button" class="glyphicon glyphicon-remove-circle close undo"></button>';
        if (e.title != undefined) {
            header = '<div class="header"><button type="button" class="glyphicon glyphicon-remove-circle close undo"></button><h4 class="tle">'+e.title+'</h4></div>';
        };
        var boxhtml ='<div class="mm-dialog" id="js-mdialog"><div class="md-content">'+header+'<div class="con">'+e.con+'</div>'+btn+
        '</div><div class="backdrop">&nbsp;</div></div>';
        $('body').append(boxhtml);
        $('#js-mdialog').find('.md-content').show(300);
        $('#js-mdialog').on('click','.undo',function(){//取消
            if (e.undo != undefined && typeof e.undo == 'function') {e.undo();};
            $('#js-mdialog').find('.md-content').slideUp('normal',function(){
                $('#js-mdialog').remove();
            });
        });
        $('#js-mdialog').on('click','.backdrop',function(){//屏蔽罩
            $('#js-mdialog').find('.md-content').slideUp('normal',function(){
                $('#js-mdialog').remove();
            });
        });
        $('#js-mdialog').on('click','.confirm',function(){//确定
            if (typeof e.fun == 'function') {
                $('#js-mdialog').find('.md-content').slideUp('normal',function(){
                $('#js-mdialog').remove();
                    e.fun();
                });  
            };
        });
    };

    /* Otherwise just put the config content (json): */
    particlesJS('js-particles',{
        "particles": {
          "number": {
            "value": 80,
            "density": {
              "enable": true,
              "value_area": 600
            }
          },
          "color": {
            "value": "#58d5f3"
          },
          "shape": {
            "type": "circle",
            "stroke": {
              "width": 0,
              "color": "#000000"
            },
            "polygon": {
              "nb_sides": 5
            },
            "image": {
              "src": "img/github.svg",
              "width": 100,
              "height": 100
            }
          },
          "opacity": {
            "value": 0.5,
            "random": false,
            "anim": {
              "enable": false,
              "speed": 1,
              "opacity_min": 0.1,
              "sync": false
            }
          },
          "size": {
            "value": 5,
            "random": true,
            "anim": {
              "enable": false,
              "speed": 40,
              "size_min": 0.1,
              "sync": false
            }
          },
          "line_linked": {
            "enable": true,
            "distance": 150,
            "color": "#bdbdbd",
            "opacity": 0.4,
            "width": 1
          },
          "move": {
            "enable": true,
            "speed": 2,
            "direction": "none",
            "random": false,
            "straight": false,
            "out_mode": "out",
            "attract": {
              "enable": false,
              "rotateX": 600,
              "rotateY": 1200
            }
          }
        },
        "interactivity": {
          "detect_on": "canvas",
          "events": {
            "onhover": {
              "enable": false,
              "mode": "repulse"
            },
            "onclick": {
              "enable":false,
              "mode": "push"
            },
            "resize": true
          },
          "modes": {
            "grab": {
              "distance": 400,
              "line_linked": {
                "opacity": 1
              }
            },
            "bubble": {
              "distance": 400,
              "size": 40,
              "duration": 2,
              "opacity": 8,
              "speed": 3
            },
            "repulse": {
              "distance": 200
            },
            "push": {
              "particles_nb": 4
            },
            "remove": {
              "particles_nb": 2
            }
          }
        },"retina_detect": true,
        "config_demo": {
          "hide_card": false,
          "background_color": "#b61924",
          "background_image": "",
          "background_position": "50% 50%",
          "background_repeat": "no-repeat",
          "background_size": "cover"
        }
      }
    );
});
    
</script> 
</body>
</html>