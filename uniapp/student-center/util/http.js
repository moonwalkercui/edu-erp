import {
	baseUrl
} from './config.js';
import * as common from './common.js' //引入common
import * as consts from './consts.js' //引入common
import store from 'store/index.js'

const methodsToken = [];

export const post = (method, data, callback, complete) => {
	uni.showLoading();
	var headers = {};
	headers['Content-Type'] = 'application/json'
	headers[consts.ACCESS_TOKEN_NAME] = common.getAccessToken()
	uni.request({
		url: baseUrl + method,
		data: data,
		header: headers,
		method: 'POST',
		success: (response) => {
			refreshTokenAndStore(response);
			uni.hideLoading();
			if(handleErrorCode(response.data) == false) return;
			if(needLogin(response.data)) {return}
			if (callback) {
				callback(response.data);
			} else {
				common.handleResponseMsg(response.data)
			}
		},
		complete: (response) => {
			complete ? complete() : "";
		},
		fail: (error) => {
			uni.hideLoading();
			if (error && error.response) {
				handleErrorCode(error.response);
			} else {
				uni.showLoading({
					title: '网络开小差了'
				});
			}
		},
	});
}

export const postForm = (method, data, callback, complete) => {
	uni.showLoading();
	var headers = {};
	headers['Content-Type'] = 'application/x-www-form-urlencoded'
	headers[consts.ACCESS_TOKEN_NAME] = common.getAccessToken()
	uni.request({
		url: baseUrl + method,
		data: data,
		header: headers,
		method: 'POST',
		success: (response) => {
			refreshTokenAndStore(response);
			uni.hideLoading();
			if(handleErrorCode(response.data) == false) return;
			if(needLogin(response.data)) {return}
			if (callback) {
				callback(response.data);
			} else {
				common.handleResponseMsg(response.data)
			}
		},
		fail: (error) => {
			uni.hideLoading();
			
			if (error && error.response) {
				handleErrorCode(error.response);
			} else {
				uni.showLoading({
					title: '网络开小差了'
				});
			}
		},
		complete: (response) => {
			complete ? complete() : "";
		},
	});
}

export const get = (url, data, callback, apiUrl) => {
	var apiUrl = apiUrl || baseUrl
	uni.showLoading();
	var headers = {};
	headers[consts.ACCESS_TOKEN_NAME] = common.getAccessToken()
	uni.request({
		url: apiUrl + url,
		data: data,
		header: headers,
		method: 'GET',
		success: (response) => {
			refreshTokenAndStore(response);
			uni.hideLoading();
			if(handleErrorCode(response.data) == false) return;
			if(needLogin(response.data)) {return}
			if (callback) {
				callback(response.data);
			} else {
				common.handleResponseMsg(response.data)
			}
		},
		fail: (error) => {
			uni.hideLoading();
			if (error && error.response) {
				handleErrorCode(error.response);
			}
		},
		complete: () => {
		}
	});
}

export const uploadAvatar = (path, cb) => {
	var headers = {};
	headers[consts.ACCESS_TOKEN_NAME] = common.getAccessToken()
	uni.uploadFile({
		url: baseUrl + 'sCenter/student/uploadAvatar',
		filePath: path,
		header: headers,
		name: 'file',
		complete: (res) => {
			if(res.statusCode != 200) {
				uni.showToast({
					title: '上传出错',
					icon: 'none',
					duration: 1000,
				});
			}
			cb && cb(res.data)
		}
	});
}

// 是否需要登录
function needLogin(responseData) {
	if(responseData.errCode > 2000 && responseData.errCode <= 2100) {
		uni.showToast({
			title: responseData.msg || "登录状态失效, 需重新登录",
			icon: 'none',
			duration: 1500,
			success: () => {
				setTimeout(()=>{
					common.jumpToLogin()
				}, 1500)
			}
		});
		return true;
	}
	return false;
}

function refreshTokenAndStore(response) {
	const headers = response.header
	const refreshToken = headers[consts.ACCESS_TOKEN_NAME]
	if(typeof refreshToken != "undefined") {
		store.commit("login", refreshToken)
	}
}

export const handleErrorCode = error => {
	let errorMsg = ''
	if(typeof error.status == 'undefined') return true;
	switch (error.status) {
		case 400:
			errorMsg = '请求错误400'
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
		case 405:
			errorMsg = '请求方法错误'
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
		title: errorMsg || '请求出错',
		icon: 'none',
		duration: 1000,
		complete: function() {
			setTimeout(function() {
				uni.hideToast();
			}, 1000);
		}
	});
	return false;
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
			for (var tempFilePath of chooseImageRes.tempFilePaths) {
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
							handleErrorCode(error.response);
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
							handleErrorCode(error.response);
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
