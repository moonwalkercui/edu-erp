// JavaScript Document

/*var member_obj={
	main_init:function(){
		var h=$(window).height()-$('#member_header').height();
		$('#member_cont, #member_menu').height(h);
	}	
}*/

var label_obj={
	input:'.label_input',
	class:'on',
	action:function(name){
		var thisObj = this,
			input = $(thisObj.input+'[name='+name+']');
		input.each(function(){
			var type = $(this).attr('type'),
            	label = $(this).parents('label');
            $(this).is(':checked') ? label.addClass(thisObj.class) : label.removeClass(thisObj.class);
		});
	}
};

var Pop_obj={
	createPop:function(content){
		var Pop = '<div id="pop_form" style="display: none;">'+content+'<div class="cls_wrap"><div class="cls" onclick="Pop_obj.closePop();"><img src="/images/images/ico-close.png" /></div></div></div>',
			Mask = '<div id="mask" style="display: none;"></div>';
		$('body').append(Pop+Mask);
		$('#pop_form,#mask').fadeIn(300);
	},
	PopUp:function(para){
		var thisObj = this;
		$.get('/member/module/pop.php',para,function(content){
			if(content){
				thisObj.createPop(content);
			}
		});
	},
	closePop:function(){
        $('#pop_form,#mask').fadeOut(300,function(){$('#pop_form,#mask').remove();});
	}
};

function PreviewImg(Input,Img){
	var Src = getObjectURL(Input.files[0]);
	Img.prop('src',Src);
}

function getObjectURL(file){
	var url = null;
	if(window.createObjectURL!=undefined){
		url = window.createObjectURL(file);
	}else if(window.URL!=undefined){
		url = window.URL.createObjectURL(file);
	}else if(window.webkitURL!=undefined){
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}

function slideSubMenu(obj){	
	var sub_menu=$('.'+obj.attr('data-sub'));
	sub_menu.slideToggle('fast');
}

var SendCode = {
    Opt: {
        Type : 'Email',
        Timer : 60,
        Tips : '请正确填写手机号码！',
        BtnTips : '{Timer}秒后重新发送',      //{Timer}为倒计时变量
        Path : '/ajax/send-code.php',       //请求地址
        SendBefore : null,
        SendAfter : null,
        TimerAfter : null
    },
    Switch : true,
    set: {
        Reg: [
            /^[1][358][0-9]{9}$/,       //电话
            /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/      //邮箱
        ],
        Timer : ''  //倒计时定制器
    },
    CountDown : function(Btn){     //倒计时
        var BtnFont = Btn.val(),
            thisObj = this,
            T = thisObj.Opt.Timer;
        Btn.val( thisObj.Opt.BtnTips.replace( '{Timer}' , thisObj.Opt.Timer ) );
        thisObj.set.Timer = setInterval(function(){
            if( T <= 1 ){       //倒计时结束
                thisObj.Switch = true;
                clearInterval( thisObj.set.Timer );
                Btn.val( BtnFont );
            }else{
                T--;
                Btn.val( thisObj.Opt.BtnTips.replace( '{Timer}' , T ) );
            }
        }, 1000);
    },
    changeMobile : function(Phone, Password, Action, Btn, Type){
        var thisObj = this;
        thisObj.Opt.Type = Type;
        if( thisObj.Switch ){
            switch(thisObj.Opt.Type){
                case 'Phone':
					PhoneNumber = Phone.val();
					Password = Password.val();
					Action = Action.val();
                    if( thisObj.set.Reg[0].test(PhoneNumber) ){
                        thisObj.Switch = false;
                        jQuery.post(thisObj.Opt.Path, {NewMobile:PhoneNumber, Password:Password, Action:Action}, function(Data){
                            thisObj.CountDown(Btn);
							alert(Data.msg);
                        },'json');
                    }else{
                        alert(thisObj.Opt.Tips);
                    }
                    break;
				case 'Email':
					Email = Email.val();
					Password = Password.val();
					Action = Action.val();
					if( thisObj.set.Reg[1].test(Email) ){
                        thisObj.Switch = false;
                        jQuery.post(thisObj.Opt.Path, {NewEmail:Email, Password:Password, Action:Action}, function(Data){
                            thisObj.CountDown(Btn);
							alert(Data.msg);
                        },'json');
                    }else{
                        alert(thisObj.Opt.Tips);
                    }
                    break;
                default:

                    break;
            }
        }
    },
    changeEmail : function(Email, Password, Action, Btn, Type){
        var thisObj = this;
        thisObj.Opt.Type = Type;
        if( thisObj.Switch ){
            switch(thisObj.Opt.Type){
				case 'Email':
					Email = Email.val();
					Password = Password.val();
					Action = Action.val();
					if( thisObj.set.Reg[1].test(Email) ){
                        thisObj.Switch = false;
                        jQuery.post(thisObj.Opt.Path, {NewEmail:Email, Password:Password, Action:Action}, function(Data){
                            thisObj.CountDown(Btn);
							alert(Data.msg);
                        },'json');
                    }else{
                        alert(thisObj.Opt.Tips);
                    }
                    break;
                default:

                    break;
            }
        }
    },
    getVerify : function(Phone, Action, Btn, Type){
        var thisObj = this;
        thisObj.Opt.Type = Type;
        if( thisObj.Switch ){
            switch(thisObj.Opt.Type){
                case 'Apply':
					PhoneNumber = Phone.val();
					Action = Action.val();
                    if( thisObj.set.Reg[0].test(PhoneNumber) ){
                        thisObj.Switch = false;
                        jQuery.post(thisObj.Opt.Path, {Mobile:PhoneNumber, Action:Action}, function(Data){
                            thisObj.CountDown(Btn);
							alert(Data.msg);
                        },'json');
                    }else{
                        alert(thisObj.Opt.Tips);
                    }
                    break;
                default:

                    break;
            }
        }
    },
	createAccount : function(Phone, Action, Btn, Type){
		var thisObj = this;
        thisObj.Opt.Type = Type;
        if( thisObj.Switch ){
            switch(thisObj.Opt.Type){
                case 'Create':
					PhoneNumber = Phone.val();
					Action = Action.val();
                    if( thisObj.set.Reg[0].test(PhoneNumber) ){
                        thisObj.Switch = false;
                        jQuery.post(thisObj.Opt.Path, {Mobile:PhoneNumber, Action:Action}, function(Data){
                            thisObj.CountDown(Btn);
							alert(Data.msg);
                        },'json');
                    }else{
                        alert(thisObj.Opt.Tips);
                    }
                    break;
                default:

                    break;
            }
        }
	}
};
