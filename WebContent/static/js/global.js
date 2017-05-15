define(function(require,exports,module){
/*!Dony feng  Date: 2016-6*/
function dony(e){var Verify={rxpOBJ:{"*":/^\S/,"n":/^([+-]?)\d*\.?\d+$/,"u":/^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/,"zh":/^[\u4e00-\u9fa5]+$/,"m":/^0?(11|12|13|14|15|16|17|18|19)[0-9]{9}$/,"e":/\w+((-w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+/,"p":/^([A-Za-z0-9]){6,16}$/,"v":/^([A-Za-z0-9]){4,8}$/,"rp":/^\S/},rxpOBJ_tips:{"*":"请输入...","n":"只能输入数字","u":"请输入正确的用户名","zh":"只能输入中文","m":"手机号码格式错误","e":"错误的邮件地址","p":"请输入正确的密码","v":"验证码输入错误","rp":"两次密码不一致"}};dony.prototype.verifyCode=function(form){var form=$(form);var formState=false;form.find("input[data-type]:enabled").each(function(i,k){var type=$(this).data("type");var rxp=(type=="rxp")?$(this).data("rxp"):Verify.rxpOBJ[type];rxp=new RegExp(rxp);formState=rxp.test($(this).val());if(!$(this).next("span").length){$(this).after('<span class="hint"></span>')}if(!formState){$(this).focus().val("").next("span").text(Verify.rxpOBJ_tips[type]);return formState}if(type=="rp"&&$(this).val()!=form.find("input[data-type=p]").val()){$(this).focus().val("").next("span").text(Verify.rxpOBJ_tips[type]);return formState=false}$(this).next("span").text("")});return formState};dony.prototype.BlurVerify=function(form){var put=$(form).find("input[data-type]:enabled");var formState=false;put.on("blur",function(){var type=$(this).data("type");var rxp=(type=="rxp")?$(this).data("rxp"):Verify.rxpOBJ[type];rxp=new RegExp(rxp);formState=rxp.test($(this).val());if(!$(this).next("span").length){$(this).after('<span class="hint"></span>')}if(!formState){$(this).val("").next("span").text(Verify.rxpOBJ_tips[type]);return formState}if(type=="rp"&&$(this).val()!=form.find("input[data-type=p]").val()){$(this).val("").next("span").text(Verify.rxpOBJ_tips[type]);return formState=false}$(this).next("span").text("")})};dony.prototype.serializeObject=function(form){var o={};var data=$(form).serializeArray();if(data.length!=0){$.each(data,function(){if(o[this.name]!==undefined){if(!o[this.name].push){o[this.name]=[o[this.name]]}o[tnis.name]=this.value||""}else{o[this.name]=this.value||""}})}return o};dony.prototype.GetNav=function(id){var nav=$(id).children("li");var urlStr=window.location.pathname;nav.children("a").each(function(i,k){if(urlStr.search($(k).attr("href"))>=0){$(id).children("li").eq(i).addClass("active").siblings().removeClass("active")}if(i==0){$(id).children("li").eq(i).addClass("active").siblings().removeClass("active")}})};dony.prototype.GetUrl=function(name){var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");var r=window.location.search.substr(1).match(reg);if(r!=null){return unescape(r[2])}return null};dony.prototype.MDialog=function(e){if(!e){return false}var btn='<div class="foter"><button type="button"  class="btn btn-info undo ">取消</button></div>';if(e.type=="hint"){btn=""}else{if(e.type=="dialog"){btn='<div class="foter"><button type="button"  class="btn btn-default undo ">取消</button><button type="button" class="btn btn-success confirm">确定</button></div>'}}var header='<button type="button" class="glyphicon glyphicon-remove-circle close undo"></button>';if(e.title!=undefined){header='<div class="header"><button type="button" class="glyphicon glyphicon-remove-circle close undo"></button><h4 class="tle">'+e.title+"</h4></div>"}var boxhtml='<div class="mm-dialog" id="js-mdialog"><div class="md-content">'+header+'<div class="con">'+e.con+"</div>"+btn+'</div><div class="backdrop">&nbsp;</div></div>';$("body").append(boxhtml);$("#js-mdialog").find(".md-content").show(300);$("#js-mdialog").on("click",".undo",function(){if(e.undo!=undefined&&typeof e.undo=="function"){e.undo()}$("#js-mdialog").find(".md-content").slideUp("normal",function(){$("#js-mdialog").remove()})});$("#js-mdialog").on("click",".backdrop",function(){$("#js-mdialog").find(".md-content").slideUp("normal",function(){$("#js-mdialog").remove()})});$("#js-mdialog").on("click",".confirm",function(){if(typeof e.fun=="function"){$("#js-mdialog").find(".md-content").slideUp("normal",function(){$("#js-mdialog").remove();e.fun()})}});if(e.time==undefined){setTimeout(function(){$("#js-mdialog").find(".undo").click()},3000)}else{setTimeout(function(){$("#js-mdialog").find(".undo").click()},e.time)}};dony.prototype.UIAlertView=function(el){if(el.type==undefined){el.type="success"}var box='<dialog id="js-AlertDialog" class="alert alert-'+el.type+' alert-dismissible m-alert fadeInUpBig" role="alert">';box+='<button type="button" class="close">&times;</button>'+el.con+"</dialog>";$(el.id).append(box);$("#js-AlertDialog").slideDown();$("#js-AlertDialog").on("click",".close",function(){$("#js-AlertDialog").removeClass("fadeInUpBig").addClass("fadeOutDownBig");setTimeout(function(){$("#js-AlertDialog").remove()},1000)});if(el.time!=undefined){setTimeout(function(){$("#js-AlertDialog .close").click()},el.time)}};dony.prototype.dropDownScroll=function(e,el,fun){var drop={};drop.num=1;$(el.id).on("touchstart",function(e){drop.y=e.originalEvent.targetTouches[0].pageY
});$(el.id).on("touchend",function(e){drop._y=e.originalEvent.changedTouches[0].pageY;if((drop.y-drop._y)>50){drop.num++;if(typeof fun=="function"){fun(drop.num);animated(el.an)}}});function animated(id){setTimeout(function(){$(id).each(function(i,k){if(i>9){i=5}setTimeout(function(){$(k).removeClass("hide").addClass("fadeInUp")},i*150)})},600)}}}var mm=new dony();module.exports=mm;var user=localStorage.getItem("user")});