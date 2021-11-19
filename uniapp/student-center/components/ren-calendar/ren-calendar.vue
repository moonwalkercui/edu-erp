<template>
	<view class="calendar-wrapper">
		<view class="header" v-if="headerBar">
			<view class="pre" @click="changeMonth('pre')">上个月</view>
			<view @click="changeMonth('this')">{{y+'年'+formatNum(m)+'月'}}</view>
			<view class="next" @click="changeMonth('next')">下个月</view>
		</view>
		<view class="week">
			<view class="week-day" v-for="(item, index) in weekDay" :key="index">{{ item }}</view>
		</view>
		<view :class="{ hide: !monthOpen }" class="content" :style="{ height: height }">
			<view :style="{ top: positionTop + 'rpx' }" class="days">
				<view class="item" v-for="(item, index) in dates" :key="index">
					<view class="day" @click="selectOne(item, $event)" :class="{
                            choose: choose == `${item.year}-${item.month}-${item.date}`&&item.isCurM,
                            nolm: !item.isCurM,
                            today: isToday(item.year, item.month, item.date),
                            isWorkDay: isWorkDay(item.year, item.month, item.date)
                        }">
						{{ Number(item.date) }}
					</view>
					<view class="markDay" v-if="isMarkDay(item.year, item.month, item.date)&&item.isCurM">
						{{isMarkDay(item.year, item.month, item.date)}}</view>
				</view>
			</view>
		</view>
		<image src="https://i.loli.net/2020/07/16/2MmZsucVTlRjSwK.png" mode="scaleToFill" v-if="collapsible"
			@click="toggle" class="weektoggle" :class="{ down: monthOpen }"></image>
	</view>
</template>

<script>
	// https://ext.dcloud.net.cn/plugin?id=1795
	export default {
		name: 'ren-calendar',
		props: {
			// 星期几为第一天(0为星期日)
			weekstart: {
				type: Number,
				default: 1
			},
			// 标记的日期 [{date: '2021-09-09', num: 1}...]
			markDays: {
				type: Array,
				default: () => {
					return [];
				}
			},
			//是否展示月份切换按钮
			headerBar: {
				type: Boolean,
				default: true
			},
			// 是否展开
			open: {
				type: Boolean,
				default: true
			},
			//是否可收缩
			collapsible: {
				type: Boolean,
				default: true
			},
			//未来日期是否不可点击
			disabledAfter: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				weektext: ['日', '一', '二', '三', '四', '五', '六'],
				y: new Date().getFullYear(), // 年
				m: new Date().getMonth() + 1, // 月
				dates: [], // 当前月的日期数据
				positionTop: 0,
				monthOpen: true,
				choose: ''
			};
		},
		created() {
			this.dates = this.monthDay(this.y, this.m);
			!this.open && this.toggle();
		},
		mounted() {
			this.choose = this.getToday().date;
		},
		computed: {
			// 顶部星期栏
			weekDay() {
				return this.weektext.slice(this.weekstart).concat(this.weektext.slice(0, this.weekstart));
			},
			height() {
				return (this.dates.length / 7) * 80 + 'rpx';
			}
		},
		methods: {
			formatNum(num) {
				let res = Number(num);
				return res < 10 ? '0' + res : res;
			},
			getToday() {
				let date = new Date();
				let y = date.getFullYear();
				let m = date.getMonth();
				let d = date.getDate();
				let week = new Date().getDay();
				let weekText = ['日', '一', '二', '三', '四', '五', '六'];
				let formatWeek = '星期' + weekText[week];
				let today = {
					date: y + '-' + this.formatNum(m + 1) + '-' + this.formatNum(d),
					week: formatWeek
				};
				return today;
			},
			// 获取当前月份数据
			monthDay(y, month) {
				let dates = [];
				let m = Number(month);
				let firstDayOfMonth = new Date(y, m - 1, 1).getDay(); // 当月第一天星期几
				let lastDateOfMonth = new Date(y, m, 0).getDate(); // 当月最后一天
				let lastDayOfLastMonth = new Date(y, m - 2, 0).getDate(); // 上一月的最后一天
				let weekstart = this.weekstart == 7 ? 0 : this.weekstart;
				let startDay = (() => {
					// 周初有几天是上个月的
					if (firstDayOfMonth == weekstart) {
						return 0;
					} else if (firstDayOfMonth > weekstart) {
						return firstDayOfMonth - weekstart;
					} else {
						return 7 - weekstart + firstDayOfMonth;
					}
				})();
				let endDay = 7 - ((startDay + lastDateOfMonth) % 7); // 结束还有几天是下个月的
				for (let i = 1; i <= startDay; i++) {
					dates.push({
						date: this.formatNum(lastDayOfLastMonth - startDay + i),
						day: weekstart + i - 1 || 7,
						month: m - 1 >= 0 ? this.formatNum(m - 1) : 12,
						year: m - 1 >= 0 ? y : y - 1
					});
				}
				for (let j = 1; j <= lastDateOfMonth; j++) {
					dates.push({
						date: this.formatNum(j),
						day: (j % 7) + firstDayOfMonth - 1 || 7,
						month: this.formatNum(m),
						year: y,
						isCurM: true //是否当前月份
					});
				}
				for (let k = 1; k <= endDay; k++) {
					dates.push({
						date: this.formatNum(k),
						day: (lastDateOfMonth + startDay + weekstart + k - 1) % 7 || 7,
						month: m + 1 <= 11 ? this.formatNum(m + 1) : 0,
						year: m + 1 <= 11 ? y : y + 1
					});
				}
				// console.log(dates);
				return dates;
			},
			isWorkDay(y, m, d) {
				//是否工作日
				let ymd = `${y}/${m}/${d}`;
				let formatDY = new Date(ymd.replace(/-/g, '/'));
				let week = formatDY.getDay();
				if (week == 0 || week == 6) {
					return false;
				} else {
					return true;
				}
			},
			isFutureDay(y, m, d) {
				//是否未来日期
				let ymd = `${y}/${m}/${d}`;
				let formatDY = new Date(ymd.replace(/-/g, '/'));
				let showTime = formatDY.getTime();
				let curTime = new Date().getTime();
				if (showTime > curTime) {
					return true;
				} else {
					return false;
				}
			},
			// 标记日期
			isMarkDay(y, m, d) {
				let flag = false;
				for (let i = 0; i < this.markDays.length; i++) {
					let dy = `${y}-${m}-${d}`;
					// if (this.markDays[i].date == dy) {
					//     flag = true;
					//     break;
					// }
					if (this.markDays[i].date == dy) {
						flag = this.markDays[i].num;
						break;
					}
				}
				return flag;
			},
			isToday(y, m, d) {
				let checkD = y + '-' + m + '-' + d;
				let today = this.getToday().date;
				if (checkD == today) {
					return true;
				} else {
					return false;
				}
			},
			// 展开收起
			toggle() {
				this.monthOpen = !this.monthOpen;
				if (this.monthOpen) {
					this.positionTop = 0;
				} else {
					let index = -1;
					this.dates.forEach((i, x) => {
						this.isToday(i.year, i.month, i.date) && (index = x);
					});
					this.positionTop = -((Math.ceil((index + 1) / 7) || 1) - 1) * 80;
				}
			},
			// 点击回调
			selectOne(i, event) {
				let date = `${i.year}-${i.month}-${i.date}`;
				let selectD = new Date(date).getTime();
				let curTime = new Date().getTime();
				let week = new Date(date).getDay();
				let weekText = ['日', '一', '二', '三', '四', '五', '六'];
				let formatWeek = '星期' + weekText[week];
				let response = {
					date: date,
					week: formatWeek
				};
				if (!i.isCurM) {
					// console.log('不在当前月范围内');
					return false;
				}
				if (selectD > curTime) {
					if (this.disabledAfter) {
						// console.log('未来日期不可选');
						return false;
					} else {
						this.choose = date;
						this.$emit('onDayClick', response);
					}
				} else {
					this.choose = date;
					this.$emit('onDayClick', response);
				}
				console.log(response);
			},
			//改变年月
			changYearMonth(y, m) {
				this.dates = this.monthDay(y, m);
				this.y = y;
				this.m = m;
			},
			changeMonth(type) {
				if (type == 'pre') {
					if (this.m + 1 == 2) {
						this.m = 12;
						this.y = this.y - 1;
					} else {
						this.m = this.m - 1;
					}
				} else if (type == 'next') {
					if (this.m + 1 == 13) {
						this.m = 1;
						this.y = this.y + 1;
					} else {
						this.m = this.m + 1;
					}
				} else {
					// 本月
					this.y = new Date().getFullYear();
					this.m = new Date().getMonth() + 1;
				}
				this.dates = this.monthDay(this.y, this.m);
				this.$emit('onChangeMonth', this.y + "-" + (this.m < 10 ? '0' + this.m : this.m) + '-01')
			}
		}
	};
</script>

<style lang="scss" scoped>
	.calendar-wrapper {
		color: #bbb7b7;
		font-size: 28rpx;
		text-align: center;
		background-color: #fff;
		padding-bottom: 10rpx;

		.header {
			display: flex;
			align-items: center;
			justify-content: center;
			height: 88rpx;
			color: #42464A;
			font-size: 32rpx;
			font-weight: bold;
			border-bottom: 2rpx solid #f2f2f2;

			.pre,
			.next {
				color: #4d7df9;
				font-size: 28rpx;
				font-weight: normal;
				padding: 8rpx 15rpx;
				border-radius: 10rpx;
				border: 2rpx solid #dcdfe6;
			}

			.pre {
				margin-right: 30rpx;
			}

			.next {
				margin-left: 30rpx;
			}
		}

		.week {
			display: flex;
			align-items: center;
			height: 80rpx;
			line-height: 80rpx;
			border-bottom: 1rpx solid rgba(255, 255, 255, 0.2);

			view {
				flex: 1;
			}
		}

		.content {
			position: relative;
			overflow: hidden;
			transition: height 0.4s ease;

			.days {
				transition: top 0.3s;
				display: flex;
				align-items: center;
				flex-wrap: wrap;
				position: relative;

				.item {
					position: relative;
					display: block;
					height: 80rpx;
					line-height: 80rpx;
					width: calc(100% / 7);

					.day {
						font-style: normal;
						display: inline-block;
						vertical-align: middle;
						width: 60rpx;
						height: 60rpx;
						line-height: 60rpx;
						overflow: hidden;
						border-radius: 60rpx;

						&.choose {
							background-color: #4d7df9;
							color: #fff;
						}

						&.nolm {
							color: #fff;
							opacity: 0.3;
						}
					}

					.isWorkDay {
						color: #42464a;
					}

					.notSigned {
						font-style: normal;
						width: 8rpx;
						height: 8rpx;
						background: #fa7268;
						border-radius: 10rpx;
						position: absolute;
						left: 50%;
						bottom: 0;
						pointer-events: none;
					}

					.today {
						color: #fff;
						background-color: #a8c0ff;
					}

					.workDay {
						font-style: normal;
						width: 8rpx;
						height: 8rpx;
						background: #4d7df9;
						border-radius: 10rpx;
						position: absolute;
						left: 50%;
						bottom: 0;
						pointer-events: none;
					}

					.markDay {
						font-style: normal;
						width: 26rpx;
						height: 26rpx;
						background: #CCD;
						border-radius: 10rpx;
						position: absolute;
						right: 10rpx;
						top: 0rpx;
						pointer-events: none;
						font-size: 10rpx;
						color: white;
						// color: #fc7a64;
						line-height: 26rpx;
						z-index: 90;
					}
				}
			}
		}

		.hide {
			height: 80rpx !important;
		}

		.weektoggle {
			width: 85rpx;
			height: 32rpx;
			position: relative;
			bottom: -42rpx;

			&.down {
				transform: rotate(180deg);
				bottom: 0;
			}
		}
	}
</style>
