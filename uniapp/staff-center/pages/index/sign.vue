<template>
	<view>
		<view class="plan-finish-header">
			<view class="plan-finish-bg"></view>
		</view>
		<view class="plan-finish-calendar mtop1">
			<view class="calendar">
				<view class="month bottom-line">
					<view class="arrow" @tap="pickPre(currentYear, currentMonth)"><i class="mintuifont mintui-arrowright arrowleft"></i></view>
					<view class="year-month"><text>{{currentYear}}年{{currentMonth}}月</text></view>
					<view class="arrow" @tap="pickNext(currentYear, currentMonth)"><i class="mintuifont mintui-arrowright"></i></view>
				</view>
				<view class="weekdays">
					<view>日</view>
					<view>一</view>
					<view>二</view>
					<view>三</view>
					<view>四</view>
					<view>五</view>
					<view>六</view>
				</view>
				<view class="days bottom-line">
					<view v-for="(day, index) in days" :key="index">
						<text :day="day.day" class="day-li" :class="{'day-checked':day.isChecked|| isToday(day.day)}" @tap="dayCheck(day)">
							<text class="day-text" :class="{other: currentMonth != day.day.getMonth()+1, 'day-sign': day.isSign,'day-signed': day.isSigned}">{{day.day.getDate()}}</text>
						</text>
					</view>
				</view>
			</view>
			<view class="plan-calendar-info">
				<view class="calendar-info-date"><text>{{currentPlan.date}}</text></view>
				<view class="calendar-info-title"><text>{{currentPlan.title}}</text></view>
				<view class="calendar-info-list">
					<view v-for="(p, i0) in currentPlan.list" :key="i0"><text>{{p}}</text></view>
				</view>
				<!-- <view class="calendar-info-text">
		                <p>学习考点：成本管理</p>
		                <p class="calendar-info-nums">完成题目：100道</p>
		            </view> -->
			</view>
		</view>
		<view class="sign-btn">
			<button class="show-map" @tap="showLocation()">查看签到位置</button>
			<button v-if="showSignBtn" @tap="handleSign">立即签到</button>
			<button class="show-map" v-else >立即签到</button>
		</view>
	
	</view>
</template>
<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import wechat from '@/common/wechat.js'
	export default {
		data() {
			return {
				// 日历
				currentDay: 1, // 当前天
				currentMonth: 1, // 当前月
				currentYear: 1970,
				currentWeek: 0, // 一号所在的星期
				days: [], // 当月所有天数
				content: {},
				sign_days: [], // 签到日期
				is_sign: false,
				currentPlan: {},
				showSignBtn: true,
				latDb: 0,
				lngDb: 0,
			};
		},
		onLoad() {
			wechat.wxReady();
		},
		onShow() {
			this.todaySign();
		},
		methods: {
			isToday: function(day) {
				var now = new Date();
				return day.getFullYear() + '/' + (day.getMonth() + 1) + '/' + day.getDate() == now.getFullYear() + '/' + (now.getMonth() +
					1) + '/' + now.getDate();
			},
			/**
			 * 获取签到日期
			 */
			handleReq: function(param, cb) {
				param = param || {};
				api.get('staff/signList', param,(res) => {
					this.sign_days = res.data.list;
					this.latDb = res.data.lat_db;
					this.lngDb = res.data.lng_db;
					cb && cb()
				});
			},
			todaySign: function() {
				
				this.handleReq({}, ()=> {
					this.initData(null);
					var d = new Date();
					this.dayCheck({
						day: d,
						isSign: this.isVerDate(d),
						isSigned: this.isSigned(d),
					})
				});
			},
			initData: function(cur) {
				var date;
				if (cur) { // 切换上一月、下一月
					date = new Date(cur);
				} else {
					var now = new Date(); // 此处取本机时间，应改为服务器时间
					var d = new Date(this.formatDate(now.getFullYear(), now.getMonth() + 1, 1));
					d.setDate(35); // 设置天数为35天（32~59都可以，既设置到下一个月的某一天）
					date = new Date(this.formatDate(d.getFullYear(), d.getMonth(), 1));
				}
				this.currentDay = new Date().getDate(); // 今日日期 几号
				this.currentYear = date.getFullYear(); // 当前年份
				this.currentMonth = date.getMonth() + 1; // 当前月份
				this.currentWeek = date.getDay(); // 当前月1号是星期几？ 0表示星期天

				// 当前月最后一天是星期几？ 0表示星期天
				this.nextWeek = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDay();
				var str = this.formatDate(this.currentYear, this.currentMonth, 1); // 2020/01/01
				var nextStr = new Date(date.getFullYear(), date.getMonth() + 1, 0).toLocaleDateString(); // 2020/01/01
				// console.log(nextStr)
				this.days = []; // 初始化日期
				// 设置上一个月 需显示 的最后几天  铺满一周
				for (var i = this.currentWeek; i > 0; i--) {
					var d = new Date(str);
					d.setDate(d.getDate() - i);
					var dayobject = {
						day: d,
						isSign: this.isVerDate(d),
						isSigned: this.isSigned(d)
					}; // 用一个对象包装Date对象  以便为以后预定功能添加属性
					this.days.push(dayobject); // 将日期放入data 中的days数组 供页面渲染使用
				}
				// 显示当前月的天数  第二个循环为 j<= 36- this.currentWeek，
				// 因为1号是星期六的时候当前月需显示6行，如2020年8月
				this.num = 0; //第几个月 每遇到1号加1
				for (var j = 0; j <= 36 - this.currentWeek; j++) {
					var d = new Date(str);
					d.setDate(d.getDate() + j);
					var dddd = d.getDate();
					var dayobject = {
						day: d,
						isSign: this.isVerDate(d),
						isSigned: this.isSigned(d)
					};
					if (dddd == 1) {
						this.num++
					}
					if (this.num == 2) {
						break
					}
					this.days.push(dayobject);
				}
				// console.log('当前月1号是星期' + this.currentWeek)
				// console.log('当前月最后一天是星期' + this.nextWeek)
				// 设置下一个月 需显示 的最前几天铺满一周
				for (var k = 1; k <= 6 - this.nextWeek; k++) {
					var d = new Date(nextStr);
					d.setDate(d.getDate() + k);
					var dayobject = {
						day: d,
						isSign: this.isVerDate(d),
						isSigned: this.isSigned(d)
					}; // 用一个对象包装Date对象  以便为以后预定功能添加属性
					this.days.push(dayobject); // 将日期放入data 中的days数组 供页面渲染使用
				}
			},
			/**
			 * 判断该日期是否有任务
			 * @param d
			 * @returns {boolean}
			 */
			isVerDate: function(d) {
				var signdays = [];
				for (var i in this.sign_days) {
					signdays.push(this.sign_days[i].day);
				}
				return signdays.includes(d.toLocaleDateString());
			},
			/**
			 * 判断该日期是否有任务并且已完成
			 * @param d
			 * @returns {boolean}
			 */
			isSigned: function(d) {
				var signdays = [];
				for (var i in this.sign_days) {
					if (this.sign_days[i].is_sign) {
						signdays.push(this.sign_days[i].day);
					}
				}
				return signdays.includes(d.toLocaleDateString());
			},
			// 按天获取当天的签到记录
			getDayData: function(d) {
				for (var i in this.sign_days) {
					if (d.day.getFullYear() + '/' + (d.day.getMonth() + 1) + '/' + d.day.getDate() == this.sign_days[i].day) {
						this.$set(d, 'list', this.sign_days[i].list);
						break;
					}
				}
			},
			/**
			 * 上一月
			 * @param year
			 * @param month
			 */
			pickPre: function(year, month) {
				
				this.handleReq({
					y: (month == 1 ? year - 1 : year),
					m: (month == 1 ? 12 : month - 1)
				}, ()=> {
					var d = new Date(this.formatDate(year, month, 1));
					d.setDate(0);
					this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
				});
			},
			/**
			 * 下一月
			 * @param year
			 * @param month
			 */
			pickNext: function(year, month) {
				
				this.handleReq({
					y: (month == 12 ? year + 1 : year),
					m: (month == 12 ? 1 : month + 1)
				}, ()=> {
					var d = new Date(this.formatDate(year, month, 1));
					d.setDate(35);
					this.initData(this.formatDate(d.getFullYear(), d.getMonth() + 1, 1));
				});
			},
			// 返回 类似 2020/01/01 格式的字符串
			formatDate: function(year, month, day) {
				month < 10 && (month = '0' + month);
				day < 10 && (day = '0' + day);
				var data = year + '/' + month + '/' + day;
				return data;
			},
			/**
			 * 点击日期查询
			 * @param index
			 */
			dayCheck: function(day) {
				// console.log(this.isToday(day));
				this.showSignBtn = this.isToday(day.day);
				var currentPlan = {
					title: '',
					date: '',
					list: []
				};
				currentPlan.date = day.day.toLocaleDateString().split('/')[1] + '月' + day.day.toLocaleDateString().split('/')[2] +
					'日';
				for (var i in this.days) {
					this.$set(this.days[i], 'isChecked', 0)
				}
				this.$set(day, 'isChecked', 1);
				this.getDayData(day);
				if (day.isSign) {
					if (day.isSigned) {
						if (day.list)
							currentPlan.list = day.list;
						currentPlan.title = '已签到' + currentPlan.list.filter((num) => {
							return !(num == '未签到' || num == '未签退');
						}).length + '次';
					} else {
						currentPlan.title = '未签到'
					}

				} else {
					currentPlan.title = '暂无签到'
				}
				this.currentPlan = currentPlan
			},
			// 签到
			handleSign: function() {
				wechat.getLocation((latValue, lngValue) => {
					api.get('staff/signDo', {
						lat: latValue,
						lng: lngValue,
					}, (res) => {
						if(res.result == 'success') {
							common.showSuccess(res.msg)
							this.todaySign();
						} else {
							common.showError(res.msg)
						}
					});
				});
			},
			showLocation: function() {
				wechat.openLocation(this.latDb, this.lngDb, '签到坐标', '请到签到卡位置附近操作')
			},
		}
	}
</script>

<style>
	
	.plan-finish-header {
		position: relative;
		width: 100%;
		height: 480upx;
	}
	
	.plan-finish-bg {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 400upx;
		background: linear-gradient(135deg, rgba(5, 201, 221, 1) 0%, rgba(23, 196, 160, 1) 100%);
	}
	
	.plan-finish-bg::before {
		content: '';
		position: absolute;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 120upx;
		background: linear-gradient(180deg, rgba(246, 246, 246, 0) 0%, rgba(246, 246, 246, 1) 100%);
	}
	
	.plan-finish-bg::after {
		content: '';
		position: absolute;
		bottom: -50upx;
		left: 20upx;
		width: 710upx;
		height: 90upx;
		background: rgba(170, 233, 221, 1);
		border-radius: 1px;
		opacity: 0.5;
		filter: blur(48upx);
	}
	
	.plan-finish-head {
		position: relative;
		display: flex;
		justify-content: space-between;
		width: 100%;
		padding: 30upx;
		color: #fff;
	}
	
	.plan-finish-info {
		flex: 1;
		padding-top: 20upx;
	}
	
	.plan-finish-tit {
		width: 100%;
		text-align: center;
		font-size: 44upx;
	}
	
	.plan-nums {
		padding-top: 10upx;
		font-size: 48upx;
	}
	
	.plan-finish-ico {
		width: 250upx;
		height: 230upx;
	}
	
	.plan-finish-calendar {
		position: absolute;
		top: 300upx;
		left: 50%;
		transform: translateX(-50%);
		font-size: 24upx;
		padding: 0 30upx;
	}
	.plan-finish-btns{
		width: 100%;
		padding: 80upx 30upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.plan-finish-btns view{
		width:300upx;
		height:84upx;
		background:rgba(21,188,195,0.11);
		border-radius:42upx;
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 32upx;
		color: #15BCC3;
	}
	.plan-finish-btns li:last-child{
		background: #15BCC3;
		color: #fff;
	}
	
	/* 日历 */
	.calendar {
		width: 100%;
		width: 690upx;
		min-height: 500upx;
		background: #fff;
		box-shadow: 0 6upx 48upx 0 rgba(0, 0, 0, 0.1);
		border-radius: 20upx;
		overflow: hidden;
		font-size: 24upx;
		padding: 0 30upx;
	}
	
	.calendar .month {
		background: #ffffff;
		padding: 30upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		white-space: nowrap;
	}
	
	.calendar .month view {
		text-transform: uppercase;
		letter-spacing: 0;
	}
	
	.calendar .month .arrow {
		color: #333;
		width: 200upx;
		font-size: 24upx;
		display: flex;
		justify-content: center;
		align-items: center; 
	}
	
	.calendar .mintui-arrowright {
		font-size: 36upx; 
		width: 16upx;
		height: 16upx;
		border-top: 4upx solid #666;
		border-right: 4upx solid #666;
		transform: rotate(45deg);
	}
	
	.calendar .arrowleft {
		transform: rotate(-135deg);
	}
	
	.calendar .month .year-month {
		flex: 1;
		display: flex;
		justify-content: center;
		align-items: center;
		font-size: 36upx;
		color: #2D2D2D;
	}
	
	.calendar .weekdays {
		/*头部星期*/
		padding: 10upx 0;
		background-color:  rgba(21,188,195,0.1);border-radius: 40upx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		margin-bottom: 10upx;
	}
	
	.calendar .weekdays view {
		display: inline-block;
		text-align: center;
	}
	
	.calendar .days {
		/*日期*/
		background: #FFFFFF;
		display: flex;
		flex-wrap: wrap;
		align-items: center;
		justify-content: flex-start;
		padding-bottom: 20upx;
	}
	
	.calendar .days view {
		list-style-type: none;
		width: 14.2%;
		padding: 1%;
		box-sizing: border-box;
		text-align: center;
		color: #000;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.calendar .days view .day-li {
		position: relative;
		width: 68upx;
		height: 68upx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 28upx;
		border-radius: 50%;
		border: 1px solid #fff;
	}
	.calendar .days view .day-checked{
		border:1px solid #15BCC3;
	}
	.calendar .days view .day-li .day-text{
		position: relative;
		width: 60upx;
		height: 60upx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 28upx;
		border:1px solid #fff;
		border-radius: 50%;
	}
	
	.calendar .other {
		color: #ccc;
	}
	.calendar .day-sign {
		/*签到的日期*/
		background-color: #f5f5f5;
		color: #15BCC3;
	}
	.calendar .day-signed  {
		background-color: #15BCC3;
		color: #fff;
	}
	
	
	.mtop1 {
		top: 40upx;
	}
	
	.plan-calendar-info {
		width: 100%;
		padding: 32upx 0;
	}
	
	.calendar-info-date {
		position: relative;
		width: 150upx;
		height: 42upx;
		display: flex;
		padding-left: 20upx;
		align-items: center;
		font-size: 26upx;
		color: #fff;
		background-color: #5BC0DE;
		border-radius: 20upx;
	}
	
	.calendar-info-date text {
		position: relative;
	}
	
	.absolute-bg {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}
	
	.calendar-info-title {
		color: #2D2D2D;
		font-size: 32upx;
		padding: 20upx 0;
		font-weight: bold;
	}
	
	.calendar-info-list {
		display: flex;
		flex-wrap: wrap;
		padding-bottom: 20upx;
	}
	
	.calendar-info-list view {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 154upx;
		height: 50upx;
		background: #fff;
		border-radius: 25upx;
		color: #333333;
		font-size: 24upx;
		margin-bottom: 20upx;
		margin-right: 20upx;
	}
	
	.calendar-info-list view:nth-child(4n) {
		margin-right: 0
	}
	.calendar-info-text{
		width:690upx;
		padding: 24upx;
		background:#FFFFFF;
		border-radius: 20upx;
		font-size: 26upx;
		color: #2D2D2D;
	}
	.calendar-info-nums{
		color: #999999;
		padding-top: 15upx;
	}
	
	.mask-box {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		display: -webkit-box;
		display: -webkit-flex;
		display: -moz-box;
		display: -ms-flexbox;
		display: flex;
		-webkit-box-align: center;
		-webkit-align-items: center;
		-moz-box-align: center;
		-ms-flex-align: center;
		align-items: center;
		-webkit-box-pack: center;
		-webkit-justify-content: center;
		-moz-box-pack: center;
		-ms-flex-pack: center;
		justify-content: center;
		background: rgba(0, 0, 0, 0.6);
	}
	
	.swiper-box {
		position: relative;
		width: 100%;
	}
	
	.close-img-box {
		position: absolute;
		top: -80px;
		left: 80px;
		width: 50upx;
		height: 50upx;
		padding: 10upx;
	}
	
	.close-img-box image {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}
	
	.swiper-container {
		width: 100%;
	}
	
	.swiper-slide {
		width: 700upx;
	}
	
	.poster-img {
		width: 100%;
		height: auto;
		-webkit-transform: scale(0.85);
		-moz-transform:scale(0.85);
		-ms-transform: scale(0.85);
		-o-transform:scale(0.85);
		transform: scale(0.85);
		-webkit-transition: all 0.5s;
		-o-transition: all 0.5s;
		-moz-transition: all 0.5s;
		transition: all 0.5s;
		border-radius: 20upx;
		pointer-events: unset;
	}
	
	.swiper-slide-active .poster-img {
		-webkit-transform: scale(1);
		-moz-transform: scale(1);
		-ms-transform: scale(1);
		-o-transform: scale(1);
		transform: scale(1);
	}
	.poster-tips{
		position: absolute;
		font-size:24upx;
		color: #999;
		z-index: 9999;
		bottom: 30upx;
		right: 60upx;
	}
	.sign-btn{
		position: fixed;
		bottom: 20upx;
		padding: 0 40upx;
		width: 100%;
		display: flex;
	}
	.sign-btn button{
		box-sizing:border-box;
		background-color: #f2711c;
	    border-color: #f2711c;
	    color: #fff!important;
		box-shadow: 0 0 0 0 rgba(34,36,38,.15) inset;
		cursor: pointer;
	    min-height: 70upx;
	    outline: 0;
	    border: none;
	    vertical-align: baseline;
		font-weight: 700;
	    text-align: center;
		border-radius: 30upx;
		width: 100%;
		font-size: 30upx;
		margin: 20upx;
	}
	.sign-btn button.show-map{
		background-color: #cdcdcd!important;
	    border-color: #cdcdcd!important;
	}
</style>
