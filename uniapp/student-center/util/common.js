import * as db from './db.js' //引入common
import * as consts from "./consts.js"
import Cookies from "./js.cookie.js"
import {get} from './http.js'

//跳转到登陆页面
function jumpToLogin() {
	uni.redirectTo({
		url: '/pages/login/index',
		animationType: 'pop-in',
		animationDuration: 200
	})
}

//把obj对象里的值覆盖到newobj里面
function deepCopy(newobj, obj) {
	if (typeof obj != 'object') {
		return obj
	}
	for (var i in obj) {
		var a = {}
		if (newobj[i]) {
			a = newobj[i]
		}
		newobj[i] = deepCopy(a, obj[i])
	}
	return newobj
}

// 一个提示框
function showMsg(title, cb) {
	uni.showToast({
		title: title,
		duration: 1600,
		icon: 'none'
	});
	cb && setTimeout(() => {
		cb()
	}, 1600)
}

//操作成功后，的提示信息
function showSuccess(msg = '提交成功', callback = function() {}) {
	setTimeout(function() {
		uni.showToast({
			title: msg,
			icon: 'success',
			duration: 1000,
			success() {
				callback && setTimeout(function() {
					callback()
				}, 1000)
			}
		})
	}, 100)
}
//操作失败的提示信息
function showError(msg = '操作失败', callback = function() {}) {
	setTimeout(function() {
		uni.showToast({
			title: msg,
			icon: 'none',
			duration: 1500,
			success() {
				callback && setTimeout(function() {
					callback()
				}, 1500)
			}
		})
	}, 100)

}

//加载显示
function loadingShow(msg = '加载中') {
	uni.showToast({
		title: msg,
		icon: 'loading'
	})
}

//加载隐藏
function loadingHide() {
	uni.hideToast()
}

// 提示框
function showAlert(
	title = '提示',
	content = '确认执行此操作吗?',
	callback = () => {},
	showCancel = true,
	cancelText = '取消',
	confirmText = '确定'
) {
	uni.showModal({
		title: title,
		content: content,
		showCancel: showCancel,
		cancelText: cancelText,
		confirmText: confirmText,
		cancelText: cancelText,
		success: function(res) {
			if (res.confirm) {
				// 用户点击确定操作
				setTimeout(() => {
					callback()
				}, 500)
			} else if (res.cancel) {
				// 用户取消操作
			}
		}
	})
}

function handleResponseMsg(res) {

	if (res && res.errCode > 0) {
		uni.showToast({
			title: res.msg,
			icon: 'none',
			duration: 1500
		});
		return false
	}
	return true
}

//时间戳转时间格式
function timeToDate(date, flag = false) {
	var date = new Date(date * 1000) //如果date为13位不需要乘1000
	var Y = date.getFullYear() + '-'
	var M =
		(date.getMonth() + 1 < 10 ?
			'0' + (date.getMonth() + 1) :
			date.getMonth() + 1) + '-'
	var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
	var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
	var m =
		(date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
	var s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
	if (flag) {
		return Y + M + D
	} else {
		return Y + M + D + h + m + s
	}
}

function time2date(micro_second) {
	var time = {}
	// 总秒数
	var second = Math.floor(micro_second)
	// 天数
	time.day = PrefixInteger(Math.floor(second / 3600 / 24), 2)
	// 小时
	time.hour = PrefixInteger(Math.floor((second / 3600) % 24), 2)
	// 分钟
	time.minute = PrefixInteger(Math.floor((second / 60) % 60), 2)
	// 秒
	time.second = PrefixInteger(Math.floor(second % 60), 2)

	var newtime = ''
	if (time.day > 0) {
		newtime =
			time.day +
			'天' +
			time.hour +
			'小时' +
			time.minute +
			'分' +
			time.second +
			'秒'
	} else {
		if (time.hour != 0) {
			newtime = time.hour + '小时' + time.minute + '分' + time.second + '秒'
		} else {
			newtime = time.minute + '分' + time.second + '秒'
		}
	}
	return newtime
}

function timeToDateObj(micro_second) {
	var time = {}
	// 总秒数
	var second = Math.floor(micro_second)
	// 天数
	time.day = Math.floor(second / 3600 / 24)
	// 小时
	time.hour = Math.floor((second / 3600) % 24)
	// 分钟
	time.minute = Math.floor((second / 60) % 60)
	// 秒
	time.second = Math.floor(second % 60)

	return time

}

//货币格式化
function formatMoney(number, places, symbol, thousand, decimal) {
	// console.log(number)
	// console.log(places)
	number = number || 0
	places = !isNaN((places = Math.abs(places))) ? places : 2
	symbol = symbol !== undefined ? symbol : '￥'
	thousand = thousand || ','
	decimal = decimal || '.'
	var negative = number < 0 ? '-' : '',
		i = parseInt((number = Math.abs(+number || 0).toFixed(places)), 10) + '',
		j = (j = i.length) > 3 ? j % 3 : 0
	return (
		symbol +
		negative +
		(j ? i.substr(0, j) + thousand : '') +
		i.substr(j).replace(/(\d{3})(?=\d)/g, '$1' + thousand) +
		(places ?
			decimal +
			Math.abs(number - i)
			.toFixed(places)
			.slice(2) :
			'')
	)
}
//金额格式化还原
function rmoney(s) {
	return parseFloat(s.replace(/[^\d\.-]/g, ""));
}

function throttle(fn, context, delay) {
	clearTimeout(fn.timeoutId)
	fn.timeoutId = setTimeout(function() {
		fn.call(context)
	}, delay)
}

// 时间格式化输出，如11:03 25:19 每1s都会调用一次
function datetimeBeauty(micro_second) {
	var time = {}
	// 总秒数
	var second = Math.floor(micro_second / 1000)
	// 天数
	time.day = PrefixInteger(Math.floor(second / 3600 / 24), 2)
	// 小时
	time.hour = PrefixInteger(Math.floor((second / 3600) % 24), 2)
	// 分钟
	time.minute = PrefixInteger(Math.floor((second / 60) % 60), 2)
	// 秒
	time.second = PrefixInteger(Math.floor(second % 60), 2)
	return time
}

//不足位数前面补0
function PrefixInteger(num, length) {
	return (Array(length).join('0') + num).slice(-length)
}

//验证是否是手机号
function isPhoneNumber(str) {
	var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/
	if (!myreg.test(str)) {
		return false
	} else {
		return true
	}
}

/**
 * 对象参数转为url参数
 */
function builderUrlParams(url, data) {
	if (typeof url == 'undefined' || url == null || url == '') {
		return ''
	}
	if (typeof data == 'undefined' || data == null || typeof data != 'object') {
		return ''
	}
	url += url.indexOf('?') != -1 ? '' : '?'
	for (var k in data) {
		url += (url.indexOf('=') != -1 ? '&' : '') + k + '=' + encodeURI(data[k])
	}
	return url
}

/**
 * 使用循环的方式判断一个元素是否存在于一个数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function isInArray(arr, value) {
	for (var i = 0; i < arr.length; i++) {
		if (value === arr[i]) {
			return true
		}
	}
	return false
}
/**
 * 统一跳转
 */
function navigateTo(url) {
	uni.navigateTo({
		url: url,
		animationType: 'pop-in',
		animationDuration: 300
	})
}

/**
 *  关闭当前页面并跳转
 */
function redirectTo(url) {
	uni.redirectTo({
		url: url,
		animationType: 'pop-in',
		animationDuration: 300
	})
}

/**
 * 获取url参数
 *
 * @param {*} name
 * @param {*} [url=window.location.serach]
 * @returns
 */
function getQueryString(name, url) {
	var url = url || window.location.href
	var reg = new RegExp('(^|&|/?)' + name + '=([^&|/?]*)(&|/?|$)', 'i')
	var r = url.substr(1).match(reg)
	if (r != null) {
		return r[2]
	}
	return null
}

/**
 * 金额相加
 * @param {Object} value1
 * @param {Object} value2
 */
function moneySum(value1, value2) {
	return (parseFloat(value1) + parseFloat(value2)).toFixed(2);
}
/**
 * 金额相减
 * @param {Object} value1
 * @param {Object} value2
 */
function moneySub(value1, value2) {
	let res = (parseFloat(value1) - parseFloat(value2)).toFixed(2);
	return res > 0 ? res : 0;
}


/**
 * 分享URL解压缩
 * @param {Object} url
 */
function shareParameterEncode(url) {
	let urlArray = url.split('-');
	let newUrl = 'type=' + urlArray[0] + '&invite=' + urlArray[1] + '&id=' + urlArray[2] + '&team_id=' + urlArray[3] +
		'&id_type=' + urlArray[4] + '&page_code=' + urlArray[5] + '&group_id=' + urlArray[6];
	return newUrl;
}

/**
 * 分享URL压缩
 * @param {Object} url
 */
function shareParameterDecode(url) {
	var urlArray = url.split('&');
	var allParameter = {
		'type': '',
		'invite': '',
		'id': '',
		'team_id': '',
		'id_type': '',
		'page_code': '',
		'group_id': '',
	};
	for (var i = 0; i < urlArray.length; i++) {
		var parameter = urlArray[i].split('=');
		allParameter[parameter[0]] = parameter[1];
	}
	var newUrl = allParameter.type + '-' + allParameter.invite + '-' + allParameter.id + '-' + allParameter.team_id +
		'-' +
		allParameter.id_type + '-' + allParameter.page_code + '-' + allParameter.group_id;
	return newUrl;
}

// 其他更多是格式化有如下:
// yyyy:mm:dd|yyyy:mm|yyyy年mm月dd日|yyyy年mm月dd日 hh时MM分等,可自定义组合
function timeFormat(dateTime = null, fmt = 'yyyy-mm-dd') {
	// 如果为null,则格式化当前时间
	if (!dateTime) dateTime = Number(new Date());
	// 如果dateTime长度为10或者13，则为秒和毫秒的时间戳，如果超过13位，则为其他的时间格式
	if (dateTime.toString().length == 10) dateTime *= 1000;
	let date = new Date(dateTime);
	let ret;
	let opt = {
		"y+": date.getFullYear().toString(), // 年
		"M+": (date.getMonth() + 1).toString(), // 月
		"d+": date.getDate().toString(), // 日
		"h+": date.getHours().toString(), // 时
		"m+": date.getMinutes().toString(), // 分
		"s+": date.getSeconds().toString() // 秒
		// 有其他格式化字符需求可以继续添加，必须转化成字符串
	};
	for (let k in opt) {
		ret = new RegExp("(" + k + ")").exec(fmt);
		if (ret) {
			fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
		};
	};
	return fmt;
}

/**
 * 每个月里有多少天
 * */
function daysInMonth(year, month) {
	if (month === 1) {
		if (year % 4 === 0 && year % 100 !== 0)
			return 29;
		else
			return 28;
	} else if ((month <= 6 && month % 2 === 0) || (month = 6 && month % 2 === 1))
		return 31;
	else
		return 30;
}

/** 
 *  增加月份
 */
function dateAddMonth(date, addMonth) {
	const y = date.getFullYear();
	const m = date.getMonth();
	let nextY = y;
	let nextM = m;
	if ((m + addMonth) > 11) {
		nextY = y + 1;
		nextM = parseInt(m + addMonth) - 12;
	} else {
		nextM = date.getMonth() + addMonth
	}
	const daysInNextMonth = daysInMonth(nextY, nextM);
	let day = date.getDate();
	if (day > daysInNextMonth) {
		day = daysInNextMonth;
	}
	return new Date(nextY, nextM, day);
}

/** 
 *  请求头
 */
function getRequestHeader() {
	return {
		[consts.ACCESS_TOKEN_NAME]: db.get(consts.ACCESS_TOKEN_NAME) ?? ''
	}
}

function getRedpoint(cb) {
	get('sCenter/student/redpoint',{}, res => {
		if(!handleResponseMsg(res)) return;
		// uni.hideTabBarRedDot({
		//   index: 2
		// })
		// uni.hideTabBarRedDot({
		//   index: 3
		// })
		// if(res.homework_count && res.homework_count > 0) {
		// 	uni.showTabBarRedDot({
		// 	   index: 2,
		// 	})
		// }
		// var ucenterCount= parseInt(res.evaluate_count) + parseInt ( res.grade_count );
		// if( ucenterCount > 0) {
		// 	uni.showTabBarRedDot({
		// 	   index: 3,
		// 	})
		// }
		cb && cb(res)
	})
}

function setAccessToken(value) {
	Cookies.set(consts.ACCESS_TOKEN_NAME, value)
	db.set(consts.ACCESS_TOKEN_NAME, value)
}
function getAccessToken() {
	return Cookies.get(consts.ACCESS_TOKEN_NAME) || db.get(consts.ACCESS_TOKEN_NAME) || ''
}
function removeAccessToken() {
	Cookies.remove(consts.ACCESS_TOKEN_NAME)
	db.del(consts.ACCESS_TOKEN_NAME)
}

function systemSettings(codes) {
	return new Promise((resove, reject)=>{
		get('sCenter/systemSettings',{codes}, res => {
			var list = []
			if(res && res.length > 0) {
				for(var option of res) {
					for(var code of codes) {
						if(option.code === code) {
							list[code] = option.value
							break
						}
					}
				}
			}
			resove(list)
		})
	})
}

export {
	deepCopy,
	jumpToLogin,
	timeToDate,
	formatMoney,
	showMsg,
	showSuccess,
	showError,
	throttle,
	time2date,
	isPhoneNumber,
	timeFormat,
	isInArray,
	daysInMonth,
	dateAddMonth,
	loadingShow,
	loadingHide,
	navigateTo,
	redirectTo,
	showAlert,
	builderUrlParams,
	datetimeBeauty,
	getQueryString,
	timeToDateObj,
	moneySum,
	moneySub,
	shareParameterEncode,
	shareParameterDecode,
	handleResponseMsg,
	rmoney,
	getRequestHeader,
	setAccessToken,
	getAccessToken,
	removeAccessToken,
	getRedpoint,
	systemSettings,
}
