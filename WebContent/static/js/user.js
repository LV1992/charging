define(function(require,exports,moduel){
    var mm = new require('global');
    var pages = 1;
    var user = localStorage.getItem('user');
    if (user != null) {user = JSON.parse(user);}else{location.href = '/charging/account/login.jsp'};
    require('Plugins/vue.js');
    var PointData = new Vue({
        el: '.g-container',
        data: {Indentlist: '',myBooklist: '',conLists:'',reviewdeta:''}
    });
    if ($('#js-Indent').length) {
        if (mm.GetUrl('t') != null) {
            $('#js-Indent').find('a').eq(mm.GetUrl('t')).removeClass('u-gradualB').addClass('act').siblings('a').removeClass('act').addClass('u-gradualB');
            loadIndentList();
        }else{
            loadIndentList();
        };
        // 点击订单切换
        $('#js-Indent').on('click','a',function(){
            if (!$(this).hasClass('act')) {
                $(this).removeClass('u-gradualB').addClass('act').siblings('a').removeClass('act').addClass('u-gradualB');
                loadIndentList();
            };
        });
        // 点击立即支付
        $('#js-IndentList').on('click','a.pay',function(e){
            var self = $(this).data('id');
            if (self != '') {
                $.ajax({
                    type:'get',
                    url:'wx/getJSPayParam',
                    data:{uid:user.id,payid:self}
                }).done(function(res){
                    if (res) {
                        $('#js-chargePay').find('.payid').val(self);
                        $('#js-chargePay').find('.uid').val(user.id);
                        InvokePay(res,'#js-chargePay');
                    };
                }).error(function(res){
                    console.log('error'+JSON.stringify(res));
                });
            };
        });
        
    };
    if ($('#js-myBook').length) {
        loadmyBookList();
        // 点击预约切换
        $('#js-myBook').on('click','a',function(){
            if (!$(this).hasClass('act')) {
                location.href = location.origin+'/charging/user/myBook.jsp#'+$(this).data('id');
                $(this).removeClass('u-gradualB').addClass('act').siblings('a').removeClass('act').addClass('u-gradualB');
                loadmyBookList();
            };
        });
        // 点击取消预约
        $('#js-myBookList').on('click','a.call',function () {
            var daStr = $(this).data('id');
            daStr.uid = user.id;
            daStr.cpid = daStr.c_p_id;
            daStr.startTime = daStr.start_time;
            daStr.endTime = daStr.end_time;
            $.ajax({
                type:'POST',
                url:'appointment/cancelAppointment',
                data:daStr
            }).done(function(res){
                if (res.res) {
                    mm.MDialog({type:'hint',con:'取消成功...',time:1000});
                    loadmyBookList();
                };
            }).error(function(res){
                console.log('error'+JSON.stringify(res));
            });
        });
    };
    // 图片上传预览
    var demo_h5_upload_ops = {
        init:function(el){
            this.eventBind(el);
        },
        eventBind:function(el){
            var that = this;
            $(el.id).change(function(){
                var reader = new FileReader();
                reader.onload = function (e) {
                    that.compress(this.result,el);
                };
                reader.readAsDataURL(this.files[0]);
            });
        },
        compress : function (res,el) {
            var that = this;
            var img = new Image(),
                maxH = 300;
            img.onload = function () {
                var cvs = document.createElement('canvas'),
                    ctx = cvs.getContext('2d');
                if(img.height > maxH) {
                    img.width *= maxH / img.height;
                    img.height = maxH;
                }
                cvs.width = img.width;
                cvs.height = img.height;
                ctx.clearRect(0, 0, cvs.width, cvs.height);
                ctx.drawImage(img, 0, 0, img.width, img.height);
                var dataUrl = cvs.toDataURL('image/jpeg', 1);
                $('img'+el.pic).attr("src",dataUrl);
                $('img'+el.pic).show().removeClass('hide');
                // 上传略
                that.upload( dataUrl );
            };
            img.src = res;
        },
        upload:function( image_data ){
            /*这里写上次方法,图片流是base64_encode的*/
        }
    };
    /*****
     *用户信息
     *****/
     if ($('#js-personal').length) {
        user.age == null ? user.age = 0 : user.age ;
        $.each($('#js-personal .personal').find('input'),function(i,k){
            if ($(k).attr('name')) {$(k).val(user[$(k).attr('name')]);}
        });
        $('#js-personal').find('select.form-control').val(user.gender);
        $('#js-avatars').find('img').attr('src',user.head_portrait); // 用户头像
        // 禁用不可编辑
        $('#js-personal').find('.form-control').attr('disabled',true);

        // 点击编辑按钮
        $('#js-personal').on('click','.flipInX',function(i,k){
            $(this).attr('type','submit').text('提交').removeClass('flipInX');
            $('#js-personal').find('.form-control').attr('disabled',false).eq(0).click();
            return false;
        });

        // 点击编辑图片
        $('#js-avatars').on('click','img.edit',function(){
            $("#js-upavatar").find('input').click();
        });
        // 头像上传事件
        var uploader = WebUploader.create({
            auto: true,
            swf: '/charging/static/plugins/uploader/Uploader.swf',
            server: 'common/uploadUserPhoto',
            pick: '#js-upavatar',
            formData:{uid:user.id},
            accept: {
                title: '头像',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
        });
        uploader.on( 'uploadSuccess', function( res ) {
            console.log(res);
        });

        // 点击编辑
        $('#js-personal').on('click','.form-control',function(i,k){
            $('#js-personal .personal').find('input,select').css('border','1px solid #66afe9');
            // $(this).parent().siblings('li').find('input,select').css('border-color','transparent');
            // $(this).css('border','1px solid #66afe9');
            $('#js-avatars').find('img').addClass('edit');
        });

        // 点击提交
        $('#js-personal').on('submit',function(e){
            e.preventDefault();
            if (!mm.verifyCode('#js-personal')) {return false;};
            var jsonStr = mm.serializeObject('#js-personal');
            jsonStr.id = user.id;
            console.log(jsonStr);
            $.ajax({
                type:'post',
                url:'user/updateUser',
                data:{jsonStr:encodeURI(JSON.stringify(jsonStr))},
                dataType:'json',
            }).done(function(res){
                if(res.res){
                    localStorage.setItem("user", JSON.stringify(res.user));
                    mm.MDialog({type:'hint',con:'修改成功...',time:1000,undo:function(){
                        window.history.back(); //返回上一页
                    }});
                }else{
                    mm.MDialog({type:'hint',con:'更新出错了...',time:1000});
                };
            });
        });
        
        setTimeout(function(){
            $("#js-upavatar").find('input').attr('id','js-avatar');
            demo_h5_upload_ops.init({id:'#js-avatar',pic:'.img-circle'});
        },1000);
    };
    // 消息管理
    if ($('#js-MsgCen').length) {
        $('#js-MsgCen').find('.avatar').attr('src',user.head_portrait); // 用户头像
        // 默认加载
        loadMsgCenList({
            id:'#js-MsgCen',
            url:'review/getReviewListByUserID',
            data:{uid:user.id,currentPage:pages}
        });
        // 点击未读信息
        $('#js-MsgCen .header').on('click','a.btn',function(){
            var _this = $(this);
            _this.addClass('act').siblings('a').removeClass('act');
            if (_this.data('id') == 'unread') {
                location.href = location.pathname +'#w' // 未读信息
            }else  if (_this.data('id') == 'footprint'){
                console.log(_this);
            }
        });
    };
    // 消息详情管理
    if ($('#js-MsgDetail').length) {
        // 用户头像和昵称
        $('#js-MsgDetail .header').find('.avatar').attr('src',user.head_portrait);
        $('#js-MsgDetail .header').find('.name').text(user.nick_name);
        // 默认加载
        loadMsgCenList({
            id:'#js-MsgDetail',
            url:'review/getWriteBackListByUserID',
            data:{uid:user.id,sid:mm.GetUrl('sid'),reviewid:mm.GetUrl('d'),currentPage:pages}
        });
        // 点击消息评论回复
        $('#js-MsgDetail').on('click','.hf',function(){
            var daStr = $(this).parents('.ulist');
            $('#js-commReply').removeClass('hide').find('.form-control').focus().attr('placeholder','回复'+daStr.find('em').text()+':');
            $('#js-commReply').find('span.hide').text(daStr.find('em').text());
            $('#js-commReply').find('input[name="review_id"]').val(daStr.data('id'));
            $('#js-commReply').find('input[name="review_uid"]').val(daStr.find('em').data('id'));
        });
        // 点击取消消息评论回复
        $('#js-commReply').on('click','.review',function(){
            $('#js-commReply').addClass('hide');
        });

        // 点击消息评论回复提交
        $('#js-commReply').on('submit','.form',function(e){
            e.preventDefault();
            var name = $('#js-commReply').find('span.hide').text();
            var jsonStr = mm.serializeObject('#js-commReply .form');
            jsonStr.station_id = mm.GetUrl('sid');
            jsonStr.write_back_uid = user.id;
            if (jsonStr.content != '') {
                jsonStr.content = '回复'+name+':'+jsonStr.content;
                console.log(jsonStr);
                $.ajax({
                    type:'post',
                    url:'review/addWriteBack',
                    data:{"jsonStr":encodeURI(JSON.stringify(jsonStr))},
                }).done(function(res){
                    if (res.addFlag) {
                        // 默认加载
                        loadMsgCenList({
                            id:'#js-MsgDetail',
                            url:'review/getWriteBackListByUserID',
                            data:{uid:user.id,sid:mm.GetUrl('sid'),reviewid:mm.GetUrl('d'),currentPage:pages}
                        });
                        $('#js-commReply').addClass('hide');
                    }else{
                        // 调用警告提示框
                        mm.UIAlertView({id:'.g-wrap',time:3000,type:'danger',con:'回复出错，请重新尝试！',});
                    };
                });
            }else{
                // 调用警告提示框
                mm.UIAlertView({id:'.g-wrap',time:3000,type:'danger',con:'请输入回复内容...',});
            };
        });
    };
    // 车主认证
    if ($('#js-carVerify').length) {
        // 显示下拉列表
        $('#js-carVerify .list').find('input').on('focus',function(){
            $(this).nextAll('ul').slideDown();
        }).on('blur',function(){
            $(this).nextAll('ul').slideUp();
        });
        // 点击列表获取值
        $('#js-carVerify .list').find('ul').on('click','li',function(){
            $(this).parent('ul').slideUp().prevAll('input').val($(this).text());
        });
        // 点击图片上传
        $('#js-updateImg .upimg').on('click','a.up',function(){
            $(this).addClass('act').next('.img').find('input').click();
        });
        // 上传事件
        var license = WebUploader.create({
            auto: true,
            swf: '/charging/static/plugins/uploader/Uploader.swf',
            server: 'common/uploadLicensePhoto',
            pick: '#js-license',
            formData:{uid:user.id,step:0},
            accept: {
                title: '头像',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
        });
        // 上传事件
        var license1 = WebUploader.create({
            auto: true,
            swf: '/charging/static/plugins/uploader/Uploader.swf',
            server: 'common/uploadLicensePhoto',
            pick: '#js-license1',
            formData:{uid:user.id,step:1},
            accept: {
                title: '头像',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
        });
        console.log(user);
        // 显示已认证数据
        $('#js-carVerify').find('input').each(function(i,k){
            if (user[$(k).attr('name')] != null) {
                $(k).val(user[$(k).attr('name')]);
            };
            if (user['car_type'] != null) {
                $(k).attr('name') == 'model' ? $(k).val(user['car_type'].split(',')[0]) : null;
                $(k).attr('name') == 'color' ? $(k).val(user['car_type'].split(',')[2]) : null;
                $(k).attr('name') == 'car_type' ? $(k).val(user['car_type'].split(',')[1]) : null;
            };
            if (user['car_type'] != null) {
                $(k).attr('name') == 'map' ? $(k).val(user['plate_no'].substring(0,1)) : null;
                $(k).attr('name') == 'plate_no' ? $(k).val(user['plate_no'].substring(1)) : null;
            };
            if (user['license']) {
                //证件图片
                $('#js-carVerify').find('.pic').removeClass('hide').attr('src',user['license'].split('#')[0]);
                $('#js-carVerify').find('.pic1').removeClass('hide').attr('src',user['license'].split('#')[1]);
            };
            if (user['approve_status'] == 'true') {
                $('#js-carVerify').find('.btn').text('已认证').attr('disabled',true);
                $('#js-carVerify').find('input').attr('disabled',true);
            };
        });
        // 验证
        $('#js-carVerify').find('.form-control').on('blur',function(){
            if ($(this).val() == '') {
                // 调用警告提示框
                mm.UIAlertView({
                  id:'.g-wrap',
                  type:'danger',
                  con:$(this).attr('placeholder'),
                });
            };
        });
        // mm.BlurVerify('#js-carVerify');
        // 车主认证提交
        $('#js-carVerify').on('submit',function(){
            //未输入
            if (!mm.verifyCode('#js-carVerify')) {
                return false;
            }
        	var dataStr = mm.serializeObject('#js-carVerify');
            //车牌号
            dataStr.plate_no = dataStr.map+dataStr.plate_no; delete dataStr['map']; 
            //车型颜色
            dataStr.car_type = dataStr.model +','+dataStr.car_type+','+dataStr.color; delete dataStr['color'];delete dataStr['model'];
            dataStr.id = user.id;
            $.ajax({
                type:'post',
                url:'user/updateUser',
                data:{jsonStr:encodeURI(JSON.stringify(dataStr))},
                dataType:'json',
            }).done(function(res){
        		if(res.res){
                    localStorage.setItem("user", JSON.stringify(res.user));
                    mm.MDialog({type:'hint',con:'修改成功...',time:1000,undo:function(){
                        window.history.back(); //返回上一页
                    }});
                }else{
                    mm.MDialog({type:'hint',con:'更新出错了...',time:1000});
                };
            });
            return false;
        });
        setTimeout(function(){
            $("#js-license").find('input').attr('id','license');
            $("#js-license1").find('input').attr('id','license1');
            demo_h5_upload_ops.init({id:'#license',pic:'.pic'});
            demo_h5_upload_ops.init({id:'#license1',pic:'.pic1'});
        },1000);
    };
    /*
    *支付失败点击重新支付
    */
    $('#js-repay').on('click','.pay',function () {
        var self = $(this).data('id');
        if (self != '') {
            $.ajax({
                type:'get',
                url:'wx/getJSPayParam',
                data:{uid:user.id,payid:self}
            }).done(function(res){
                if (res) {
                    $('#js-chargePay').find('.payid').val(self);
                    $('#js-chargePay').find('.uid').val(user.id);
                    InvokePay(res,'#js-chargePay');
                };
            }).error(function(res){
                console.log('error'+JSON.stringify(res));
            });
        };
        
    });
    // 加载消息管理数据函数
    function loadMsgCenList(el){
        $.ajax({
            type:'get',
            url:el.url,
            data:el.data,
            dataType:'json'
        }).done(function(res){
            $(el.id).removeClass('hide');
            if (res) {
                // 加载数据函数
                PointData.conLists = res;
                // 消息管理
                if (res.reviewList) {
                    // 动画加载
                    $.each(res.reviewList,function(i,k){
                        setTimeout(function(){
                            $(el.id).find('.cent').eq(i).removeClass('hide').addClass('fadeInRight');
                        },i*150);
                    });
                    setTimeout(function(){
                        $('#js-MsgCen').find('em.im').text($(el.id).find('.cent').length);//脚印
                        $('#js-MsgCen').find('em.un').text($(el.id).find('.unread').length);//未读
                    },1500);
                    var loadData = true ; //判断是否还有数据
                    // 加载滑动翻页
                    mm.dropDownScroll('.g-wrap',{id:el.id,an:'.item .animated'},function(num) {
                        if (loadData) {
                            el.data.currentPage = num;
                            $.ajax({
                                type:'get',
                                url:el.url,
                                data:el.data,
                                dataType:'json'
                            }).done(function(res){
                                if (res.reviewList.length > 0) {
                                    $.each(res.reviewList,function(i,k){
                                        PointData.conLists.push(k);
                                    });
                                }else{
                                    loadData = false;
                                };
                            }).error(function(res){
                                console.log(res + 'error');
                            });
                        };
                    });
                };
                if (res.writeBackList) {
                    PointData.reviewdeta = res.reviewStation;
                    // 动画加载
                    $.each(res.writeBackList,function(i,k){
                        setTimeout(function(){
                            $(el.id).find('.ulist').eq(i+1).removeClass('hide').addClass('fadeInUp');
                        },i*150);
                    });
                }
            };
        }).error(function(res){
            console.log(res + 'error');
        });
    };
    // 加载预约数据函数
    function loadmyBookList(){
        if (location.hash) {
            $('#js-myBook').find(location.hash).removeClass('u-gradualB').addClass('act').siblings('a').removeClass('act').addClass('u-gradualB');
        };
        var type = $('#js-myBook').find('.act').data('type');
        $.ajax({
            type:'get',
            url:'records/getAppointmentRecords/'+user.id+'/'+pages+'/'+type,
            dataType:'json'
        }).done(function(res){
            $('#js-myBookList').removeClass('hide');
            if (res.appointmentRecordsList.length) {
                $('#js-myBookList').next().html('');
                // 加载数据
                PointData.myBooklist = res.appointmentRecordsList;
                // 动画加载
                $.each(res.appointmentRecordsList,function(i,k){
                    setTimeout(function(){
                        $('#js-myBookList').find('.item').eq(i).removeClass('hide').addClass('fadeInUp');
                    },i*150);
                });
                var loadData = true ; //判断是否还有数据
                // 加载滑动翻页
                mm.dropDownScroll('.g-wrap',{id:'#js-myBookList',an:'.indent .animated'},function(num) {
                    if (loadData) {
                        $.ajax({
                            type:'get',
                            url:'records/getAppointmentRecords/'+user.id+'/'+num+'/'+type,
                            dataType:'json'
                        }).done(function(res){
                            if (res.appointmentRecordsList.length > 0) {
                                $.each(res.appointmentRecordsList,function(i,k){
                                    PointData.myBooklist.push(k);
                                });
                            }else{
                                loadData = false;
                            };
                        }).error(function(res){
                            console.log(res + 'error');
                        });
                    };
                });
            }else{
                PointData.myBooklist = res.appointmentRecordsList;
                $('#js-myBookList').next('.norecord').html('<div class="htip"><i class="u-icn u-icn-mwd"></i><p>主人，你还没有预约记录！</p></div>');
            }
        }).error(function(res){
            console.log(res + 'error');
            $('#js-myBookList').next('.norecord').html('<div class="htip"><i class="u-icn u-icn-err"></i><p>主人，网络跟丢了！</p></div>');
        });
    };
    // 加载订单数据函数
    function loadIndentList(){
        var type = $('#js-Indent').find('.act').data('type');
        $.ajax({
            type:'get',
            url:'records/getChargeRecords/'+user.id+'/'+pages+'/'+type,
            dataType:'json'
        }).done(function(res){
            $('#js-IndentList').removeClass('hide');
            if (res.chargeRecordsList.length) {
                $('#js-IndentList').next().html('');
                // 加载数据
                PointData.Indentlist = res.chargeRecordsList ;
                // 动画加载
                $.each(res.chargeRecordsList,function(i,k){
                    setTimeout(function(){
                        $('#js-IndentList').find('.item').eq(i).removeClass('hide').addClass('fadeInUp');
                    },i*150);
                });
                var loadData = true ; //判断是否还有数据
                // 加载滑动翻页
                mm.dropDownScroll('.g-wrap',{id:'#js-IndentList',an:'.indent .animated'},function(num) {
                    if (loadData) {
                        $.ajax({
                            type:'get',
                            url:'records/getChargeRecords/'+user.id+'/'+num+'/'+type,
                            dataType:'json'
                        }).done(function(res){
                            if (res.chargeRecordsList.length > 0) {
                                $.each(res.chargeRecordsList,function(i,k){
                                    PointData.Indentlist.push(k);
                                });
                            }else{
                                loadData = false;
                            };
                        }).error(function(res){
                            console.log(res + 'error');
                        });
                    };
                });
            }else{
                PointData.Indentlist = res.appointmentRecordsList;
                $('#js-IndentList').next('.norecord').html('<div class="htip"><i class="u-icn u-icn-mwd"></i><p>主人，你还没有订单哦！</p></div>');
            }
        }).error(function(res){
            console.log(res + 'error');
            $('#js-myBookList').next('.norecord').html('<div class="htip"><i class="u-icn u-icn-err"></i><p>主人，网络跟丢了！</p></div>');
        });
    };
    /*InvokePay();
     *调用微信支付平台函数
     *data 数据对象
     */
    function InvokePay(data,id){
        // 兼容微信平台
        if (typeof WeixinJSBridge == "undefined"){
           if( document.addEventListener ){
               document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
           }else if (document.attachEvent){
               document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
               document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
           }
        }else{
           onBridgeReady();
        };
        // 调用微信支付
        function onBridgeReady(){
            WeixinJSBridge.invoke('getBrandWCPayRequest',data,function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    $(id).attr('action','charge/getPayDetails/1');
                    // location.href = '/charging/charge/payDone.jsp?payid='+payid;
                }else{
                    $(id).attr('action','charge/getPayDetails/0');
                    // location.href = '/charging/charge/payBust.jsp?payid='+payid;
                };
                $(id).submit();
                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回 ok，但并不保证它绝对可靠。 
            }); 
        }
    };
});