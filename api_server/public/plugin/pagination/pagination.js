/**
 * 分页插件  
 * 2017/8/4 
 * author 我已不是前奏丶
 * github：https://github.com/myprelude/pagination.git
 * 关注技术交流群：621373454
 */
!function(){
	var root = this||(0,eval)('this');
	var Page = function(config){
		return new Page.prototype.init(config);
	}
	Page.prototype={
		constructor:Page,
		init:function(config){
			this.box = config.box;//存放分页器的容器
			this.count = config.count;//总的页码数
			this.num = config.num ||3;//一页显示的个数
			this.href = config.href ||'';//a标签跳转路径
			this.page = config.page || 1;//当前页面
			this.step = config.step ||3;//每次移动的步长
			this.callBack = config.callBack;//通过ajax 异步执行回调方法
			this.nextFun = config.next;//下一页的回调
			this.preFun = config.pre;//上一页的回调
			this.create();
		},
		create:function(){
			var aNum = this.count-this.num,a='',_this=this,dom='';
			if(this.href){
				if(aNum>0){
					var n = this.count-this.page-this.num+1;
					if(n>=0){
						for(var i=0;i<this.num;i++){
							var pagenumber = this.page+i;
							a+= "<a href='"+this.href+pagenumber+"' class='item "+(i==0?'active':'')+"'>"+pagenumber+"</a>";
						}	
					}else{
						for(var i=n;i<this.count-this.page+1;i++){
							a+= "<a href='"+this.href+(this.page+i)+"' class='item "+(this.page+i==this.page?'active':'')+"'>"+(this.page+i)+"</a>";
						}	
					}	
				}else{
					for(var i=0;i<this.count;i++){
						a+= "<a href='"+this.href+(1+i)+"' class='item "+(this.page==(1+i)?'active':'')+"'>"+(1+i)+"</a>";
					}	
				}
				var preurl='',nexturl='';
				preurl=this.page==1?this.href+this.page:this.href+(--this.page);
				nexturl=this.page==this.count?this.href+this.page:this.href+(++this.page+this.num);
				dom = '<div class="ui tiny right floated pagination menu"><a href="'+preurl+'" class="icon item"><i class="left chevron icon"></i></a>'+a+'<a href="'+nexturl+'" class="icon item"><i class="right chevron icon"></i></a></div>';
			}else{
				if(aNum<0){
					for(var i=0;i<this.count;i++){
						a+= "<a href='javascript:void(0);' class='item "+(i==0?'active':'')+"'>"+(1+i)+"</a>";
					}	
				}else{
					for(var i=0;i<this.num;i++){
						a+= "<a href='javascript:void(0);' class='item "+(i==0?'active':'')+" "+(this.num-i==1?'page-next':'')+"'>"+(1+i)+"</a>";
					}	
				}
				dom = '<div class="ui tiny right floated pagination menu"><a href="javascript:void(0);" class="goPre item "><i class="left chevron icon"></i></a>'+a+'<a href="javascript:void(0);" class="goNext item "><i class="right chevron icon"></i></a></div>';
			}
			
			var id = document.getElementById(_this.box);
			id.innerHTML=dom;
			var contain = id.getElementsByClassName('page-contain')[0];
			var box = id.getElementsByClassName('page-box')[0];
			var next = id.getElementsByClassName('page-next')[0];
			var goNext = id.getElementsByClassName('goNext')[0];
			var goPre = id.getElementsByClassName('goPre')[0];
			if(!_this.href){
				box.addEventListener('click',function(e){
					if(e.target.className&&e.target.className.indexOf('page-next')>-1){
						_this.next(box);	
					}	
				})
				box.addEventListener('click',function(e){
					if(e.target.className&&e.target.className.indexOf('page-pre')>-1){
						_this.pre(box);
					}
				})
				goPre.addEventListener('click',function(){
					for(var i=0,j;i<box.childNodes.length;i++){
						if(box.childNodes[i].className.indexOf('active')>-1){
							j=i;
						}
					}
					if(box.childNodes[j].innerHTML==1)return;
					if(_this.count<=_this.num){
						if(j==0)return;
						box.childNodes[i].className='';
						box.childNodes[i-1].className+=" active";
						return;
					}
					if(j==1){
						_this.pre(box);
					}else{
						box.childNodes[j].className=box.childNodes[j].className.replace('active','');
						box.childNodes[j-1].className+=" active";
					}
					var n = box .getElementsByClassName('active')[0].innerHTML;
					_this.preFun&&_this.preFun(Number(n));
					
				})
				goNext.addEventListener('click',function(){
					for(var i=0,j;i<box.childNodes.length;i++){
						if(box.childNodes[i].className.indexOf('active')>-1){
							if(box.childNodes[i].innerHTML==_this.count){return}
							j=i+1;
						}
						box.childNodes[i].className=box.childNodes[i].className.replace('active','');
					}
					if(_this.count<=_this.num){
						box.childNodes[j].className='active';
						return;
					}
					if(j==_this.num-1){
						_this.next(box);
					}else{
						box.childNodes[j].className='active';
					}
					var n = box .getElementsByClassName('active')[0].innerHTML;
					_this.nextFun&&_this.nextFun(Number(n));
				})
				box.addEventListener('click',function(e){
					if(e.target.nodeName=='A'){
						for(var i=0;i<box.childNodes.length;i++){
							box.childNodes[i].className=box.childNodes[i].className.replace('active','');
						}
						e.target.className+=" active"; 
						_this.callBack&&_this.callBack(Number(e.target.innerHTML));
					}
				})
			}
			
		},
		next:function(box){
			var _this=this;
			var n = box.childNodes[_this.num-1].innerHTML*1;
			box.childNodes[_this.num-1].className = '';
			if(n==_this.count){box.childNodes[_this.num-1].className = 'active';return;}
			var s = _this.count-n-_this.step;
			if(s>0){
				for(var i=0;i<_this.step;i++){
					box.removeChild(box.childNodes[0]);
					box.childNodes[0].className='page-pre';
					box.childNodes[_this.num-1-_this.step].className+=' active';
					box.childNodes[_this.num-2-_this.step].className = box.childNodes[_this.num-2-_this.step].className.replace('active','');
					var dom = "<a href='javascript:void(0);' class='item "+(_this.step==(i+1)?'page-next':'')+"'>"+(1+i+n)+"</a>"
					box.appendChild(returnDom(dom));
				}
				box.childNodes[_this.num-2-_this.step].className=box.childNodes[_this.num-2-_this.step].className.replace('active','');
			}else{
				for(var i=0;i<_this.step+s;i++){
					box.removeChild(box.childNodes[0]);
					box.childNodes[0].className+=' page-pre';
					var dom = "<a href='javascript:void(0);' class='item "+((_this.step+s)==(i+1)&&((1+i+n)!=_this.count)?'page-next':'')+"'>"+(1+i+n)+"</a>";
					box.appendChild(returnDom(dom));
				}
				box.childNodes[_this.num-_this.count+n-1].className+=' active';
			}
		},
		pre:function(box){
			var _this=this;
			var n = box.childNodes[0].innerHTML*1;
			var s = n-_this.step;
			if(s>0){
				box.childNodes[0].className='';
				box.childNodes[_this.num-_this.step-1].className='page-next';
				for(var i=0;i<_this.step;i++){
					box.removeChild(box.childNodes[ _this.num-1]);
					var dom = "<a href='javascript:void(0);' class='item "+(i==(_this.step-1)&&((n-i-1)!=1)?'page-pre':'')+"'>"+(n-i-1)+"</a>";
					box.insertBefore(returnDom(dom),box.childNodes[0]);	
				}
				box.childNodes[_this.step].className+=' active';
				box.childNodes[_this.step+1].className = box.childNodes[_this.step+1].className.replace('active','');
			}else{
				if(n==1){
					box.childNodes[0].className='active';
					box.childNodes[1].className='';
					return;
				}
				for(var i=0;i<n-1;i++){
					box.removeChild(box.childNodes[_this.num-1]);
					var dom= "<a href='javascript:void(0);' class='item'>"+(i+1)+"</a>";
					box.insertBefore(returnDom(dom),box.childNodes[i]);
				}
				box.childNodes[n].className='';
				box.childNodes[_this.num-1].className='page-next';
				box.childNodes[n-1].className='active';
			}
		}
	}
	Page.prototype.init.prototype = Page.prototype;
	//字符串转化为函数
	function returnDom(dom){
		var div = document.createElement('div');
		div.innerHTML = dom;
		return div.childNodes[0];
	}
	root['page'] = Page;
}();