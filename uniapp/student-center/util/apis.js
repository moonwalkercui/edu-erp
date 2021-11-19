import * as http from './http.js'

export function login(formData) {
	return new Promise((resove, reject) => {
		http.postForm('student/login', formData, res => {
			resove(res)
		})
	})
}

export function getCurrendUser() {
	return new Promise((resove, reject) => {
		http.get('common/auth/currentUser', {}, res => {
			resove(res)
		})
	})
}

export function wxJsSdkConfig(params) {
	return new Promise((resove, reject) => {
		http.get('wx/jsapi/default/getJsapiTicket', params, res => {
			resove(res)
		})
	})
}

export function forgetpwd(formData) {
	return new Promise((resove, reject) => {
		http.post('sCenter/open/forgetPwd', formData, res => {
			resove(res)
		})
	})
}

export function register(formData) {
	return new Promise((resove, reject) => {
		http.post('sCenter/open/register', formData, res => {
			resove(res)
		})
	})
}

export function sendsms(mobile, scene) {
	return new Promise((resove, reject) => {
		http.post('common/open/sendSms', {mobile, scene}, res => {
			resove(res)
		})
	})
}

// export function getSchoolList() {
// 	return new Promise((resove, reject) => {
// 		http.get('school/schoolList', {}, res => {
// 			resove(res)
// 		})
// 	})
// }
