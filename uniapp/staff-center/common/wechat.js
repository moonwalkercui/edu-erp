import * as api from './api.js'
var jweixin = require('@/components/jweixin-module/index.js');
/* 用法 www.cnblogs.com/niceyoo/p/12232641.html */
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
	//初始化sdk配置  
	initJssdk: function(callback, url) {
		api.post(
			'wxJsSdkConfig', {
				url: url
			},
			function(res) {
				let result = res.data;
				if (res.result == 'success') {
					jweixin.config({
						debug: false,
						appId: result.appId,
						timestamp: result.timestamp,
						nonceStr: result.nonceStr,
						signature: result.signature,
						jsApiList: result.jsApiList
					});
					if (callback) { callback(result); }
				}
			}
		);
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
		if (!this.isWechat()) {return;}
		this.initJssdk(function(signData) {
			jweixin.ready(function() {
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
			});
		}, url);
	},
	scanQRCode: function(callback) {
		var url = window.location.href;
		if (!this.isWechat()) {return;}
		this.initJssdk(function(signData) {
			jweixin.ready(function() {
				jweixin.scanQRCode({
					needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
					scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
					success: function(res) {
						var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
						callback && callback(result)
					}
				});
			});
		}, url);
	},
	wxReady: function(url) {
		url = url ? url : window.location.href;
		if (!this.isWechat()) {return;}
		this.initJssdk(function(signData) {
			jweixin.ready(function() {
			});
		}, url);
	},
	getLocation: function(cb) {
		var url = window.location.href;
		if (!this.isWechat()) { return; }
		// this.initJssdk(function(signData) {
		// 	jweixin.ready(function() {
				jweixin.getLocation({
					type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
					success: function (res) {
						// latValue = res.latitude; // 纬度，浮点数，范围为90 ~ -90
						// lngValue = res.longitude; // 经度，浮点数，范围为180 ~ -180。
						// var speed = res.speed; // 速度，以米/每秒计
						// var accuracy = res.accuracy; // 位置精度
						cb( res.latitude, res.longitude)
					}
				});
		// 	});
		// }, url);
	},
	openLocation: function(latDb, lngDb, title, desc) {
		var url = window.location.href;
		if (!this.isWechat()) { return; }
		// this.initJssdk(function(signData) {
		// 	jweixin.ready(function() {
				jweixin.openLocation({
					latitude: parseFloat(latDb), // 纬度，浮点数，范围为90 ~ -90
					longitude: parseFloat(lngDb), // 经度，浮点数，范围为180 ~ -180。
					name: title, // 位置名
					address: desc, // 地址详情说明
					scale: 16, // 地图缩放级别,整形值,范围从1~28。默认为最大
					// infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
				});
		// 	});
		// }, url);
	},
}
