define(function(require,exports,moduel){
    var mm = new require('global'); 
    var user = localStorage.getItem('user');
    if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
    var globals = {}; //全局对象
    if ($('#js-goingCharge').length) {
      // 请求充电桩信息
      $.ajax({
          type:'post',
          url:'preCharge/scanQrcode',
          data:{uid:user.id,cpid:mm.GetUrl('p')},
          dataType:'json'
      }).done(function(res){
        if (res.res) {
          //0 支付页面 1 监控（可以结束）2 开启充电 3 在别的桩充电中
          if(res.forwardPage == '0') {
            // 待支付订单
            location.href = '/charging/user/MyIndent.jsp?t=0';
          }else if(res.forwardPage == '1') {
            // 监控充电数据
            $('#js-datails').addClass('hide');
            OpenCharging();  //进入监控
            // funBeginDisTime($('#js-goingCharge .t'),1000);//调用倒计时
          }else if(res.forwardPage == '2') {
            // 开始充电
            $('#js-datails').removeClass('hide');
            var chargePoint = res.data;
            var type = '';
            switch(chargePoint.type){
              case '0' : type = '直流'; break;
              case '1' : type = '交流'; break;
              default: d = '-';
            };
            $('#js-datails .title').text(chargePoint.inner_no+'号充电桩（'+type+'）');
            $.each($('#js-datails ul').find('li'),function(i,k){
              if ($(k).data('id')) {$(k).find('em').text(chargePoint[$(k).data('id')]);}
            });
          }else if(res.forwardPage == '3') {
            // 调用警告提示框
            mm.UIAlertView({
              id:'.g-wrap',
              type:'warning',
              time:4500,
              con:'您在别的充电桩充电中...',
            });
          }else{
            var warning = '';
            switch(res.forwardPage){
              case '3': warning = '您在别的充电桩充电中...'; break;
              case '4': warning = '电桩正在充电中...'; break;
              default : warning = '网络错误...';
            };
            // 调用警告提示框
            mm.UIAlertView({
                id:'.g-wrap',
                type:'warning',
                time:4500,
                con:warning,
            });
          };
          var cpType = '';
          switch(res.cpType){
            case '0' : 
              cpType = '.dc'; break;
            case '1' : 
              cpType = '.ac'; break;
          };
          $('#js-goingCharge .type').find(cpType).removeClass('hide').siblings('span').addClass('hide');
        }else if (res.failCase == 1) {
          // 调用警告提示框
          mm.UIAlertView({
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
      }).error(function(res){
          console.log(res + 'error');
      });
      // 点击开启充电
      $('#js-datails').on('click','.begin',function(){
        globals.count = 1; //回调请求次数
        if (mm.GetUrl('p') != null) {
          startCharging();
        };
      });
      // 开启充电
      function startCharging(){
        $.ajax({
          type:'post',
          url:'charge/startCharging',
          data:{uid:user.id,cpid:mm.GetUrl('p'),count:globals.count},
          dataType:'json',
          beforeSend:function(res){
            $('#js-datails').find('.load').removeClass('hide');
          },
        }).done(function(res){
          // 开启充电返回状态
          if (res.startRes) {
            // 进入充电
            $('#js-datails').find('.datails').addClass('zoomOutUp');
            $('#js-datails').slideUp(1200);
            OpenCharging();  //进入监控
            localStorage.setItem('runCharge','1,'+mm.GetUrl('p')); //开启充电记录
            // funBeginDisTime($('#js-goingCharge .t'),1000);//调用倒计时
          }else if (res.failCase == -1) {// 开启失败；
            globals.count++;
            if (globals.count < 50) {
          	  	setTimeout(function(){startCharging();},1000);
            }else{
              $('#js-datails').find('.load').addClass('hide');
            };
          }else{
            $('#js-datails').find('.load').addClass('hide');
            switch(res.failCase){
              case '1' :
                globals.err = '充电机故障'; break;
              case '2' :
                globals.err = '未插充电枪'; break;
              case '3' :
                globals.err = '充电桩正在充电'; break;
              case '4' :
                globals.err = '充电桩被预约'; break;
              case '5' :
                globals.err = '充电桩已停止'; break;
              default : globals.err = '通信超时';
            };
            // 调用警告提示框
            mm.UIAlertView({
              id:'.g-wrap',
              type:'warning',
              time:4500,
              con:globals.err,
            });
            console.log(res.failCase);
          }
        }).error(function(res){
          console.log(res + 'error');
        });
      };
      // 点击停止充电
      $('#js-goingCharge').on('click','.stop',function(){
        globals.count = 1; //回调请求次数
        if (mm.GetUrl('p') != null) {
          endCharging();
        };
      });
      // 结束充电
      function endCharging(){
        $.ajax({
          type:'post',
          url:'charge/endCharging',
          data:{uid:user.id,cpid:mm.GetUrl('p'),count:globals.count},
          dataType:'json',
          beforeSend:function(res){
            console.log(res);
            $('#js-goingCharge .con').find('.load').removeClass('hide');
          },
        }).done(function(res){
          // 停止充电返回状态
          if (res.endRes) {
            // 进入支付页面
            localStorage.removeItem('runCharge'); //充电结束成功
            $('#js-Payment').find('input[name="uid"]').val(user.id);
            $('#js-Payment').find('input[name="id"]').val(res.unpayid);
            $('#js-Payment').submit();
          }else if (res.failCase == -1) {
              globals.count++;
              if (globals.count < 50) {
            	  	setTimeout(function(){endCharging();},1000);
              }else{
                $('#js-goingCharge .con').find('.load').addClass('hide');
              };
          }else{
            $('#js-goingCharge .con').find('.load').addClass('hide');
            switch(res.failCase){
              case '1' :
                globals.err = '充电机故障'; break;
              case '2' :
                globals.err = '未插充电枪'; break;
              case '3' :
                globals.err = '充电桩正在充电'; break;
              case '4' :
                globals.err = '充电桩被预约'; break;
              case '5' :
                globals.err = '充电桩已停止'; break;
              default : globals.err = '通信超时';
            };
            // 调用警告提示框
            mm.UIAlertView({
              id:'.g-wrap',
              type:'warning',
              time:4500,
              con:globals.err,
            });
          }
        }).error(function(res){
          console.log(res + 'error');
        });
      };
      // 监控常发数据
      function OpenCharging() {
        globals.timer = setInterval(function () {
          $.ajax({
            type:'get',
            url:'charge/getChargingLiveData',
            data:{cpid:mm.GetUrl('p')},
            dataType:'json',
          }).done(function(res){
            if (res) {
              var status = $('#js-goingCharge .canvas').find('span');
              status.find('em').text(res.data.charged_energy);
              $('#js-goingCharge .detection').find('.list').each(function (i,k) {
                if ($(k).data('id')) {$(k).find('em').text(res.data[$(k).data('id')]);}
              });
              // 自动结束
              if (res.status == '1' && res.method == '0') {
                // Payment();
              };
            };
          }).error(function(res){
            console.log(res + 'error');
          });
        },1000);
      }
      function Payment (res){
        $.ajax({
            type:'get',
            url:'charge/getChargingLiveData',
            data:{cpid:mm.GetUrl('p')},
            dataType:'json',
          }).done(function(res){
            if (res) {
              // 进入支付页面
              $('#js-Payment').find('input[name="uid"]').val(user.id);
              $('#js-Payment').find('input[name="id"]').val(res.unpayid);
              $('#js-Payment').submit();
            };
          }).error(function(res){
            console.log(res + 'error');
          });
      };
    };
    // 电站详情
    if ($('#js-DetailsList').length) {
      // 默认加载
      loadItemList({
        id:'#js-DetailsList',
        url:'cps/getChargePointStationMapDetails',
        data:{sid:mm.GetUrl('d')}
      });
      // 点击评论切换
      $('#js-productTag').on('click','a',function(i){
        if ($(this).hasClass('act')) {
          $(this).removeClass('act u-gradualB').siblings('a').addClass('act u-gradualB');
          // 改标题
          $('head title').text($(this).text()+'-巨能充');
          //切换内容
          $('#js-product').find('.center').eq($(this).index()).removeClass('hide').siblings('.center').addClass('hide');
          if ($(this).index()) {
            // 调用评论数据
            loadItemList({
              id:'#js-CommentList',
              url:'review/getReviewListByStationID',
              data:{sid:mm.GetUrl('d')}
            });

          }else{
            // 调用电站信息数据
            loadItemList({
              id:'#js-DetailsList',
              url:'cps/getChargePointStationMapDetails',
              data:{sid:mm.GetUrl('d')}            
            });
          };
        };
        return false;
      });      
      // 点击导航地址
      $('#js-product').on('click','a.target',function(){
        globals.myloca = sessionStorage.getItem('myloca');
        if (globals.myloca != null) {
          location.href = 'http://api.map.baidu.com/direction?origin=latlng:'+globals.myloca+'|name:我的位置&destination='+$(this).data('city')+'&mode=driving&region='+$(this).prevAll('.place').text()+'&output=html&src=yourCompanyName|yourAppName';
        };
      });
      // 点击点赞操作
      $('#js-product').on('click','.comment .like',function(){
        if (user != null) {
          var _this = $(this);
          globals.jsonStr = {};
          globals.jsonStr.sid = mm.GetUrl('d');
          globals.jsonStr.reviewid = _this.data('id');
          globals.jsonStr.addHeartUid = user.id;
          $.ajax({
            type:'post',
            url:'review/addOrCancelHeart',
            data:globals.jsonStr,
          }).done(function(res){
            if (res.flag) {
              if (res.addOrCancel == 'add') {
                _this.text(Number(_this.text())+1);                
              }else if(res.addOrCancel == 'cancel'){
                _this.text(Number(_this.text())-1);
              };
            }else{
              //提示内容
              mm.MDialog({con:'点赞失败！',type:'hint',time:1000});
            };
          });
        };
      });
      
      // 点击回复编辑
      $('#js-product').on('click','.comment .hf',function(){
        globals.ReplyEdit = this;
        $('#js-product .m-commReply').removeClass('fadeOutRight').addClass('fadeInRight');
        $('#js-product .m-commReply').find('input[name="review_id"]').val($(this).data('id'));
        $('#js-product .m-commReply').find('input[name="review_uid"]').val($(this).data('uid'));
        // 加载列表
        loadItemList({
          id:'#js-commReply',
          url:'review/getWriteBackListByReview',
          data:{sid:mm.GetUrl('d'),reviewid:$(this).data('id')}
        });
      });
      // 点击评论回复提交
      $('#js-product').on('submit','.form',function(e){
        e.preventDefault();
        var jsonStr = mm.serializeObject('#js-commReply .form');
            jsonStr.station_id = mm.GetUrl('d');
            jsonStr.write_back_uid = user.id;
          if (jsonStr.content != '') {
            $.ajax({
              type:'post',
              url:'review/addWriteBack',
              data:{"jsonStr":encodeURI(JSON.stringify(jsonStr))},
            }).done(function(res){
              if (res.addFlag) {
                $(globals.ReplyEdit).text(Number($(globals.ReplyEdit).text())+1);
                $('#js-product .m-commReply').removeClass('fadeInRight').addClass('fadeOutRight');
              };
            });
          }else{
            $(this).find('.btn').before('<span class="hint">请输入回复内容...</span>');
          };
      });
      // 点击评论取消
      $('#js-product').on('click','.form .cel',function(e){
        $('#js-product .m-commReply').removeClass('fadeInRight').addClass('fadeOutRight');
      });
    };
    // 订单评论
    if ($('#js-review').length) {
      if (mm.GetUrl('sid') != null) {
        $.ajax({
          type:'get',
          url:'cps/getStationDetails',
          data:{sid:mm.GetUrl('sid')},
          dataType:'json'
        }).done(function(res){
          globals.rbox = '';
          if (res) {
            var star = '';
            for (var i = 0; i < res.avg_score; i++) {star += '<i class="fa fa-star"></i>';};
            for (var i = 0; i < (5-res.avg_score); i++) {star += '<i class="fa fa-star-o"></i>';};
            globals.rbox += '<dd class="avt"><img src="'+res.picture+'" alt="用户"/></dd>'
                +'<dt>'+res.name+'</dt>'
                +'<dd class="kps">综合评价：'+star+'</dd>'
                +'<dd class="cent"><i class="fa fa-flag"></i>'+res.place+'</dd>';
            $('#js-Evaluation').find('.ulist').html(globals.rbox);
          };
        }).error(function(res){
          console.log('error'+res);
        });
      };
      // 检测输入字数
      $('#js-review').find('.form-control').bind('input propertychange', function() {
        $('#js-review').find('.t').text('还可输入'+(300-$(this).val().length)+'字');
      });
      
      // 选择打分数
      $('#js-review .kps').on('click','.fa',function(e){
        $(this).nextAll().removeClass("fa-star").addClass("fa-star-o");
        $(this).addClass("fa-star").removeClass('fa-star-o').prevAll().addClass("fa-star").removeClass('fa-star-o');
      });

      // 电站评论提交
      $('#js-review').on('submit',function(e){
        var text = $(this).find('.form-control');
        if (text.val() == '') {
          text.next('.hint').text('请输入评论内容！');
          return false;
        };
        text.next('.hint').text('');
        var daStr = mm.serializeObject('#js-review');
         daStr.score = $('#js-review .kps').find('.fa-star').length;
         daStr.station_id = mm.GetUrl('sid');
         daStr.review_uid = user.id;
        // 数据提交
        $.ajax({
          type:'POST',
          url:'review/addReview',
          data:{jsonStr:encodeURI(JSON.stringify(daStr))}
        }).done(function(res){
          if (res.flag) {
            mm.MDialog({con:'评论成功，欢迎使用。',time:1500,undo:function(){
              location.href = '/charging/';
            }});
          }else{
            mm.MDialog({con:'评论失败，请重试！',type:'hint',time:1000});
          };
        }).error(function(res){
          console.log('error'+res);
        });
        return false;
      });
    };
	  // 加载数据函数
    function loadItemList(el){
        $.ajax({
            type:'get',
            url:el.url,
            data:el.data,
            dataType:'json'
        }).done(function(res){
            $(el.id).removeClass('hide');
            var loadData = true ; //判断是否还有数据
            // 数据
            var PointData = new Vue({
              el: el.id,
              data : {contents: res,ByReview:''},
            });
            if (res.chargePointList) {
              /*电站数据   */
              $('#js-slider').removeClass('hide');
              //  幻灯片
              loadSlider(res.chargePointStationDetails.pictureList,'#js-slider');
              // 动画加载
              $.each(res.chargePointList,function(i,k){
                  setTimeout(function(){
                      $(el.id).find('.list').eq(i).removeClass('hide').addClass('fadeInUp');
                  },i*150);
              });
            }else if (res.reviewListItem){ 
              /*评论数据   */
              $('#js-slider').addClass('hide');
              //  评论动画加载
              $.each(res.reviewListItem,function(i,k){
                  setTimeout(function(){
                      $(el.id).find('.list').eq(i).removeClass('hide').addClass('fadeInUp');
                  },i*150);
              });
              // 加载滑动翻页
              mm.dropDownScroll('.g-wrap',{id:'#js-CommentList',an:'.center .animated'},function(num) {
                if (loadData) {
                  el.data.currentPage = num;
                  $.ajax({
                    type:'get',
                    url:el.url,
                    data:el.data,
                    dataType:'json'
                  }).done(function(res){
                    if (res.reviewListItem.length > 0) {
                      $.each(res.reviewListItem,function(i,k){
                        PointData.contents.reviewListItem.push(k);
                      });
                    }else{
                      loadData = false;
                    };
                  }).error(function(res){
                    console.log(res + 'error');
                  });
                };
              });
            }else if (res.writeBackList) {
              ByReviewList(res);
            };
        }).error(function(res){
            console.log(res + 'error');
        });
    };
    // 加载回复评论
    function ByReviewList(res){
      var box = '';
      $.each(res.writeBackList,function(i,k){
        box += '<dl class="ulist"><dd class="avt"><img class="img-circle" src="'+k.head_portrait+'" alt="用户头像"/></dd>'
            +'<dt>'+k.nick_name+'<span class="t">'+k.time+'</span></dt><dd class="con">'+k.content+'</dd></dl>';
      });
      $('#js-commReply').find('.review').html(box);
    };
    // 装载幻灯片函数
    globals.sliderNum = 1;
    function loadSlider(res,id){
      require('Plugins/amazeui/js/amazeui.min.js');
      if (res != null && globals.sliderNum == 1) {
        var box = '';
        $.each(res,function(i,k){
          box += '<li><a href="javascript:void(0)"><img src="'+k+'" alt=""></a></li>';
        });
        $(id).find('.am-slides').html(box);
        globals.sliderNum++;
      };
      // 调用幻灯片
      $('.am-slider').flexslider({
        init:function(){},
        directionNav: false,
      });
    };
    // 计时器
    function funBeginDisTime(id,num) {
        num = num + 1000;  // 节奏为一秒 
        var now = new Date(0,0,0,0,0,0,num);  
        var hour = now.getHours();
        var minutes = now.getMinutes();  
        var sec = now.getSeconds();  
        if (hour < 10) {hour = '0'+hour};
        if (minutes < 10) {minutes = '0'+minutes};
        if (sec < 10) {sec = '0'+sec};
        id.html(hour + ":" + minutes + ":" + sec);  
        // 每一秒执行一次  
        var myTime = setTimeout(function(){funBeginDisTime(id,num);}, 1000);
    };
});