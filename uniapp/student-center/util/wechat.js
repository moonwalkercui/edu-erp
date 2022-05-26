import {wxJsSdkConfig} from './apis.js'
import {domainUrl} from './config.js'
var jweixin = require('./jweixin-module/index.js')  
/* 用法 www.cnblogs.com/niceyoo/p/12232641.html */

//初始化sdk配置  
const initJssdk = async (callback, url) => {
	const result = await wxJsSdkConfig({url: url|| (domainUrl + 's/') });
	jweixin.config({
		debug: false,
		appId: result.appId,
		timestamp: result.timestamp,
		nonceStr: result.nonceStr,
		signature: result.signature,
		jsApiList: [
			'checkJsApi',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'scanQRCode',
			'startRecord',
			'stopRecord',
			'onVoiceRecordEnd',
			'playVoice',
			'pauseVoice',
			'stopVoice',
			'onVoicePlayEnd',
			'uploadVoice',
			'downloadVoice',
			'downloadVoice',
			'chooseWXPay',
		]
	});
	setTimeout(() => {
		jweixin.ready(() => {
		   //配置完成后，再执行分享等功能
		   callback && callback();
		});
	}, 50)
}
export default {
	//判断是否在微信中  
	isWechat: function() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/micromessenger/i) == 'micromessenger') {
			// console.log('是微信客户端')
			return true;
		} else {
			// console.log('不是微信客户端')
			return false;
		}
	},
	// 发起支付
	wxpay: function(payParam, cb) {
		initJssdk(() => {
			jweixin.chooseWXPay({
				appId: payParam.appId,
				nonceStr: payParam.nonceStr,
				package: payParam.package,
				paySign: payParam.paySign,
				signType: payParam.signType,
				timestamp: payParam.timeStamp, // 这里坑 timestamp 是小写
				success: function (res) {
					if(res.errMsg == "chooseWXPay:ok" ){
						// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
						cb(res); 
					} else {
						uni.showToast({
							title: JSON.stringify(res),
							icon: 'none',
							duration: 30000
						});
					}
				},
				  // 支付取消回调函数
			    cancel: function (res) {
					uni.showToast({
						title: '支付已取消',
						icon: 'none',
						duration: 3000
					});
			    },
			    // 支付失败回调函数
			    fail: function (res) {
					uni.showToast({
						title: err.errMsg || JSON.stringify(err),
						icon: 'none',
						duration: 10000
					});
				}
			});
		});
	},
	/*
	实现通过右上角三个点进行分享
	在需要自定义分享的界面，简单举个例子，以首页为例，将下方在代码放在 """onload 或 onshow""" 中 ：
	if (this.$wechat && this.$wechat.isWechat()) {  
	 this.$wechat.share({
		 desc: "精选优质、有价值的好文章，转发给身边的人",  
		 img: "http://img.sscai.club/click.jpeg" 
	 });  
	}
	补充：share 方法有个 url 参数，如不指定则使用当前页面url，具体参考 wechat.js
	if (this.$wechat && this.$wechat.isWechat()) {  
	 this.$wechat.share({
		 desc: "精选优质、有价值的好文章，转发给身边的人",  
		 img: "http://img.sscai.club/click.jpeg" 
	 },"http://mp.sscai.club/#/history/history");  
	}
	*/
	share: function(data, url) {
		url = url ? url : window.location.href;
		// console.log("url:"+url)
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			var shareData = {
				title: data && data.title ? data.title : signData.site_name,
				desc: data && data.desc ? data.desc : signData.site_description,
				link: url,
				imgUrl: data && data.img ? data.img : signData.site_logo,
				success: function(res) {
					// 分享后的一些操作,比如分享统计等等
				},
				cancel: function(res) {}
			};
			//分享给朋友接口  
			jweixin.onMenuShareAppMessage(shareData);
			//分享到朋友圈接口  
			jweixin.onMenuShareTimeline(shareData);
		}, url);
	},
	scanQRCode: function(callback) {
		var url = window.location.href;
		// console.log("url:"+url)
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.scanQRCode({
				needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				success: function(res) {
					var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
					callback && callback(result)
				}
			});
		}, url);
	},
	startRecord: function(cb) {
		var url = url ? url : window.location.href;
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.startRecord();
			jweixin.onVoiceRecordEnd({
			// 录音时间超过一分钟没有停止的时候会执行 complete 回调
			  complete: function (res) {
				  cb(res.localId)
			  // var localId = res.localId;
			}
			});
		}, url);
	},
	stopRecord: function(cb) {
		var url = url ? url : window.location.href;
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.stopRecord({
			  success: function (res) {
				// var localId = res.localId;
				  cb(res.localId)
			  }
			});
		}, url);
	},
	playVoice: function(recordId) {
		var url = url ? url : window.location.href;
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.playVoice({
			  localId: recordId
			});
		}, url);
	},
	stopVoice: function(recordId) {
		var url = url ? url : window.location.href;
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.stopVoice({
			  localId: recordId
			});
		}, url);
	},
	uploadVoice: function(recordId, cb) {
		var url = url ? url : window.location.href;
		if (!this.isWechat()) {
			return;
		}
		//每次都需要重新初始化配置，才可以进行分享  
		this.initJssdk(function(signData) {
			jweixin.uploadVoice({
			  localId: recordId, // 需要上传的音频的本地ID，由stopRecord接口获得
			  isShowProgressTips: 1, // 默认为1，显示进度提示
			  success: function (res) {
				cb(res.serverId); // 返回音频的服务器端ID
			  }
			});
		}, url);
	},
	
	
}
