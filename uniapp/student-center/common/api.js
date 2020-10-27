import {baseUrl, debug } from './config.js';
import * as common from './common.js' //引入common
import * as db from './db.js' //引入common
// 需要登陆的，都写到这里，否则就是不需要登陆的接口
const methodsToken = [];
function handleLogin(data) {
	if(data.code == 2) {
		setTimeout(() => {
			common.jumpToLogin()
		}, 1000)
		return true;
	}
	return false;
}
export const post = (method, data, callback, complete) => {
	uni.showLoading({
		title: '加载中'
	});
	// data['staffToken'] = db.get('staffToken')
	if(debug == true) data.debug = 1;
	uni.request({
		url: baseUrl + method,
		data: data,
		header: {
			// 'Accept': 'application/json',
			'Content-Type': 'application/json',
			// 'Content-Type': 'application/x-www-form-urlencoded', // 自定义请求头信息
		},
		method: 'POST',
		success: (response) => {
			uni.hideLoading();
			if(handleLogin(response.data)) return;
			if(callback) {
				callback(response.data);
			} else {
				handleErr(response.data)
			}
			// const result = response.data
			// if (!result.status) {
			// 	// 登录信息过期或者未登录
			// 	if (result.data === 14007 || result.data === 14006) {
			// 		db.del("userToken");
			// 		uni.showToast({
			// 			title: result.msg,
			// 			icon: 'none',
			// 			duration: 1000,
			// 			complete: function() {
			// 				setTimeout(function() {
			// 					uni.hideToast();
			// 					// #ifdef H5 || APP-PLUS || APP-PLUS-NVUE
			// 					uni.navigateTo({
			// 						url: '/pages/login/login/index1'
			// 					})
			// 					// #endif
			// 					// #ifdef MP-WEIXIN || MP-ALIPAY || MP-TOUTIAO
			// 					uni.navigateTo({
			// 						url: '/pages/login/choose/index',
			// 						animationType: 'pop-in',
			// 						animationDuration: 200
			// 					});
			// 					// #endif
			// 				}, 1000)
			// 			}
			// 		});
			// 	}
			// }
			// callback(result);
		},
		complete: (response) => {
			setTimeout(function() {
				uni.hideLoading();
			}, 1000)
			complete ? complete() : "";
		},
		fail: (error) => {
			uni.showLoading({
				title: '网络开小差了'
			});
		
			if (error && error.response) {
				handleError(error.response);
			} else {

			}
			
			uni.hideLoading();
		},
	});
}
export const get = (url, data, callback) => {
	uni.showLoading({
		title: '加载中'
	});
	// data['staffToken'] = db.get('staffToken')
	if(debug == true) data.debug = 1;
	uni.request({
		url: baseUrl + url,
		data: data,
		header: {
			// 'Accept': 'application/json',
			'Content-Type': 'application/x-www-form-urlencoded', //自定义请求头信息
		},
		method: 'GET',
		success: (response) => {
			uni.hideLoading();
			if(handleLogin(response.data)) return;
		
			if(callback) {
				callback(response.data);
			} else {
				handleErr(response.data)
			}
		},
		fail: (error) => {
			uni.hideLoading();
			if (error && error.response) {
				handleError(error.response);
			}
		},
		complete: () => {
			setTimeout(function() {
				uni.hideLoading();
			}, 250);
		}
	});
}

export const handleErr = res => {
	if (res.result != 'success') {
		uni.showToast({
			title: res.msg,
			icon: 'none',
			duration: 1000,
			complete: function() {
				setTimeout(function() {
					uni.hideToast();
				}, 1500);
			}
		});
	}
}

export const handleError = error => {
	let errorMsg = ''
	switch (error.status) {
		case 400:
			errorMsg = '请求参数错误'
			break
		case 401:
			errorMsg = '未授权，请登录'
			break
		case 403:
			errorMsg = '跨域拒绝访问'
			break
		case 404:
			errorMsg = `请求地址出错: ${error.config.url}`
			break
		case 408:
			errorMsg = '请求超时'
			break
		case 500:
			errorMsg = '服务器内部错误'
			break
		case 501:
			errorMsg = '服务未实现'
			break
		case 502:
			errorMsg = '网关错误'
			break
		case 503:
			errorMsg = '服务不可用'
			break
		case 504:
			errorMsg = '网关超时'
			break
		case 505:
			errorMsg = 'HTTP版本不受支持'
			break
		default:
			errorMsg = error.msg
			break
	}

	uni.showToast({
		title: errorMsg,
		icon: 'none',
		duration: 1000,
		complete: function() {
			setTimeout(function() {
				uni.hideToast();
			}, 1000);
		}
	});
}

// 文件上传
export const uploadFiles = (callback, count = 9, fileName = 'image') => {
 	uni.chooseImage({
		count: count, //默认9
		sizeType: ['original', 'compressed'],
		sourceType: ['album'], //从相册选择
		success: (chooseImageRes) => {
			uni.showLoading({
				title: '上传中...'
			});
			for(var tempFilePath of chooseImageRes.tempFilePaths ) {
				const uploadTask = uni.uploadFile({
					url: baseUrl + 'uploadimg.html',
					filePath: tempFilePath,
					fileType: 'image',
					name: fileName,
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'multipart/form-data',
					},
					formData: {
						'method': 'images.upload',
						'upfile': tempFilePath
					},
					success: (uploadRes) => {
						callback(JSON.parse(uploadRes.data));
					},
					fail: (error) => {
						if (error && error.response) {
							handleError(error.response);
						}
					},
					complete: () => {
						setTimeout(function() {
							uni.hideLoading();
						}, 250);
					}
				});
				// 					uploadTask.onProgressUpdate((res) => {
				//             console.log('上传进度' + res.progress);
				//             console.log('已经上传的数据长度' + res.totalBytesSent);
				//             console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
				//
				//             // 测试条件，取消上传任务。
				//             if (res.progress > 50) {
				//                 uploadTask.abort();
				//             }
				// 					});
			}
		
		}
	});
}

// 上传图片
export const uploadImage = (num, callback) => {
	uni.chooseImage({
		count: num,
		success: (res) => {
			uni.showLoading({
				title: '上传中...'
			});
			let tempFilePaths = res.tempFilePaths
			for (var i = 0; i < tempFilePaths.length; i++) {
				uni.uploadFile({
					url: baseUrl + 'api.html',
					filePath: tempFilePaths[i],
					fileType: 'image',
					name: 'file',
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'multipart/form-data',
					},
					formData: {
						'method': 'images.upload',
						'upfile': tempFilePaths[i]
					},
					success: (uploadFileRes) => {
						callback(JSON.parse(uploadFileRes.data));
					},
					fail: (error) => {
						if (error && error.response) {
							handleError(error.response);
						}
					},
					complete: () => {
						setTimeout(function() {
							uni.hideLoading();
						}, 250);
					},
				});
			}
		}
	});
}
