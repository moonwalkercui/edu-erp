$(function(){
	window.onscroll=function(){
	var autoheight=document.body.scrollTop||document.documentElement.scrollTop;
	if(autoheight>100){
		$(".go_top").fadeIn()
		}else{
			$(".go_top").fadeOut()
		}
	}
	$(".btn_top").mousedown(
		function(){
			$("html, body").animate({"scroll-top":0},"fast");
		}
	)
})

// 预加载
window.onload = function () {
	document.getElementById('loading-mask').style.display = 'none';
}

//占位框高度获取
$(function(){
	var top_h=$(".zy_search_top_box").height()
	$(".top_zhanwei_box").css("height",top_h)
})

// 登录注册及会员中心表单填写下划线变色
$(function(){
	$(".form_line label input,.form_line label textarea").focusin(
		function(){
			$(this).parent("label").css("border-color","#f2b300")
		}
	).focusout(
		function(){
			$(this).parent("label").css("border-color","#ddd")
		}
	)
})

// 滑动js
var key=0;
pyz=40;
var startX,startY,moveEndX,moveEndY,X,Y;
function huadong(name1,name2){
	$(name1).on("touchstart", function(e) {
    startX = e.originalEvent.changedTouches[0].pageX;
    startY = e.originalEvent.changedTouches[0].pageY;
    });
    $(name1).on("touchend", function(e) {
        // 判断默认行为是否可以被禁用
        moveEndX = e.originalEvent.changedTouches[0].pageX,
        moveEndY = e.originalEvent.changedTouches[0].pageY,
        X = moveEndX - startX,
        Y = moveEndY - startY;
        // alert(X+" "+Y);
        //判断是否下上
        if ((Y>pyz ||Y<-pyz) && (X>pyz || X<-pyz)) {
            return;
        }
        if (X!=0 && key==1) {
		    	$(name1).stop().animate({left:0},150);
		    	$(name2).stop().animate({right:'-24%'},150);
		    	key=0;
		    	return;
		}
        //左滑
        if ( X > 0 && X>20 && Y<pyz) {
            $(name1).stop().animate({left:0},150);
		    $(name2).stop().animate({right:'-24%'},150);
		    key=0;
		    return;
        }
        //右滑
        else if ( X < 0 && X<-20 && Y<pyz) {
            $(this).stop().animate({left:'-24%'},150);
		    $(this).next().stop().animate({right:0},150);
		    key=1;
        }
        
    });
}

var Utils = function() {};
Utils = {
	curIndex: null,
	msg: function(title, cb){
		layer.open({
			content: title, skin: 'msg' ,time: 2
		});
		setTimeout(function() {
			cb && cb();
		}, 2000);
	},
	alert: function(title) {
		layer.open({
			content: title
			,btn: '我知道了'
		});
	},
	confirm:function(title) {
		layer.open({
			content: title
			,btn: ['确定', '取消']
			,yes: function(index){
				location.reload();
				layer.close(index);
			}
		});
	},
	loading: function() {
		this.curIndex = layer.open({type: 2});
	},
	unloading: function() {
		layer.close(this.curIndex)
	},
	//验证是否是手机号
	mobile_vali: function (str) {
		var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
		return !myreg.test(str) === false;
	},
};

var HttpUtils = {
	get: function(url, params, cb){
		if(typeof params === "function") {
			cb = params;
		}
		var index;
		$.ajax({
			type: 'GET',
			url: url,    //当前路径
			data: params,
			dataType: 'json',
			beforeSend: function () {
				index = Utils.loading();
			},
			success: function (data) {
				cb && cb(data);
			},
			error: function (data) {
				console.log('服务异常，请稍后重试');
				console.log(data);
				alert('服务异常');
			},
			complete: function() {
				Utils.unloading(index);
			}
		});
	},
	post: function(url, params, cb){
		if(typeof params === "function") {
			cb = params;
		}
		var index;
		$.ajax({
			type: 'POST',
			url: url,    //当前路径
			data: params,
			dataType: 'json',
			beforeSend: function () {
				index = Utils.loading();
			},
			success: function (data) {
				cb && cb(data);
			},
			error: function (data) {
				console.log('服务异常，请稍后重试');
				console.log(data);
			},
			complete: function() {
				Utils.unloading(index);
			}
		});
	},
	upload: function(url, params, cb){
		if(typeof params === "function") {
			cb = params;
		}
		var index;
		$.ajax({
			type: 'POST',
			url: url,    //当前路径
			data: params,
			dataType: 'json',
			processData : false, // 使数据不做处理
			contentType : false, // 不要设置Content-Type请求头
			beforeSend: function () {
				index = Utils.loading();
			},
			success: function (data) {
				cb && cb(data);
			},
			error: function (data) {
				console.log('服务异常，请稍后重试');
				console.log(data);
			},
			complete: function() {
				Utils.unloading(index);
			}
		});
	},
};
function sendSmsCode(ele, mobId, check_func) {
	var mob = $(mobId).val();
	if(false === Utils.mobile_vali(mob)) {
		Utils.alert('请输入正确的手机号码');
		return;
	}
	var capsrc = $('#captch-img').attr('src');
	// 显示验证码
	layer.open({
		title: '输入图形验证码'
		,anim: 'up'
		,content: '<img id="captch-img" src="'+capsrc+'"><br> <input id="cpcode" value="" style="padding: 10px; border-radius: 5px; font-size: 120%; border: 1px solid #999;text-align: center;">'
		,btn: ['发送验证码']
		,yes: function() {
			if(check_func) {
				if(check_func(mob) == false) sendSms(ele, mobId);
			} else {
				sendSms(ele, mobId);
			}
		}
	});
	setTimeout(function(){
		$('#captch-img').click(function(){
			$(this).attr("src", "/captcha.html?"+ Math.random());
		}).trigger('click');
	}, 100);
}
function sendSms(ele, mobId) {
	var mob = $(mobId).val();
	if(false === Utils.mobile_vali(mob)) {
		Utils.alert('请输入正确的手机号码');
		return;
	}
	var cpcode = $('#cpcode').val();
	if('' ==cpcode) {
		Utils.alert('请输入图形验证码');
		return;
	}
	// 显示验证码
	$(ele).removeAttr('onclick');
	var $ele = $(ele);
	$.post('/sendsmscode.html',{mob: mob, cpcode: $('#cpcode').val()}, function(res) {
		var t;
		if(res.code == 0) {
			t = res.data;
			var f = setInterval(function(){
				if(t===0) {
					clearInterval(f);
					$ele.attr('onclick','sendSmsCode(this,"'+mobId+'")');
					$ele.html('发送验证码');
				} else {
					t--;
					$ele.html(t + '秒后重发');
				}
			}, 1000);
		} else {
			$ele.html('短信已发送');
		}
		Utils.msg(res.msg);
	});
}