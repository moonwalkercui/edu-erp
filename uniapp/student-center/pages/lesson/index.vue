<template>
	<view class="u-card-wrap">
		<view class="content">
			<ren-calendar ref='ren' :markDays='markDays' @onDayClick='onDayClick' @onChangeMonth="changeMonth"></ren-calendar>
			
			<u-card :title="curDatePretty+' 课表'"  :sub-title="lessonList.length>0 ? `${lessonList.length}节` : '本日无课'" :padding="20">
				<view class="" slot="body">
					<view class="bg-light-gray u-flex boder-radius-md u-padding-20 u-margin-bottom-10"
						v-for="(item,index) in lessonList" :key="index">
						<view class="u-flex-3">
							<view class="line-height-md">
								<view class="u-black u-type-primary-dark">{{item.startTime}} ~ {{item.endTime}} {{item.state}}</view>
								<view class="u-font-md text-black">{{item.className}} {{item.courseName}}</view>
								<view class="u-font-sm">老师: {{item.teacherNames}} {{item.classroom ? '教室:' + item.classroom : ''}}</view>
							</view>
						</view>
						<view class="u-flex-1 u-text-right">
							<view class="u-flex-1 u-text-right">
								<view class="u-font-sm u-margin-bottom-40">{{item.studentSignState}}</view>
								<view>
									<u-button v-if="item.studentSignState == '未到课'" type="primary" shape="circle" size="mini" @click="handleSign(item)">签 到
									</u-button>
								</view>
							</view>
						</view>
					</view>
					<u-empty v-if="lessonList.length == 0" mode="list" text="本日无课"></u-empty>
				</view>
			</u-card>
			
			<u-card :title="curDatePretty+' 预约课'"  :sub-title="bookableList.length>0 ? `${bookableList.length}节` : ''" :padding="20">
				<view class="" slot="body">
					<view class="bg-light-gray u-flex boder-radius-md u-padding-20 u-margin-bottom-10"
						v-for="(item,index) in bookableList" :key="index">
						<view class="u-flex-3">
							<view class="line-height-md">
								<view class="u-black u-type-primary-dark">{{item.startTime}} ~ {{item.endTime}} {{item.state}}</view>
								<view class="u-font-md text-black">{{item.className}} {{item.courseName}}</view>
								<view class="u-font-sm">老师: {{item.teacherNames}} {{item.classroom ? '教室:' + item.classroom : ''}}</view>
							</view>
						</view>
						<view class="u-flex-1 u-text-right">
							<view class="u-flex-1 u-text-right">
								<view class="u-font-sm u-margin-bottom-40">{{item.studentSignState}}</view>
								<view>
									<u-button type="primary" shape="circle" size="mini" @click="appoint(item)">预 约
									</u-button>
								</view>
							</view>
						</view>
					</view>
					<u-empty v-if="bookableList.length == 0" mode="list" text="本日无预约课"></u-empty>
				</view>
			</u-card>

		</view>
	</view>
</template>
<script>
	import RenCalendar from '@/components/ren-calendar/ren-calendar.vue'
	export default {
		components: {
			RenCalendar
		},
		data() {
			return {
				curDate: '',
				curDatePretty: '',
				markDays: [],
				lessonList: [],
				bookableList: [],
			}
		},
		onReady() {
			let today = this.$refs.ren.getToday().date;
			this.curDate = today;
			this.curDatePretty = this.datePretty(today);
		},
		onShow() {
			this.$nextTick(() => {
				this.getLessons()
				this.getBookable()
				this.lessonCountEveryDay()
			})
		},
		methods: {
			getLessons() {
				this.$http.get('sCenter/lesson/list', {
					date: this.curDate
				}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.lessonList = res
				})
			},
			getBookable() {
				this.$http.get('sCenter/lesson/list', {
					date: this.curDate,
					bookable: true
				}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.bookableList = res
				})
			},
			lessonCountEveryDay() {
				this.$http.get('sCenter/lesson/lessonCountEveryDay', {
					date: this.curDate
				}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.markDays = res || []
				})
			},
			handleRollcall(lessonId) {
				uni.navigateTo({ url : `/pages/lesson/rollcall?lessonId=${lessonId}` });
			},
			onDayClick(data) {
				this.curDate = data.date;
				this.curDatePretty = this.datePretty(data.date);
				this.getLessons()
				this.getBookable()
			},
			changeMonth(day) {
				this.curDate = day;
				this.getLessons()
				this.lessonCountEveryDay()
			},
			handleSign(lesson) {
				this.$common.showAlert("签到确认", `确认签到本课次？`, ()=> {
					this.$http.post(`sCenter/lesson/sign/${lesson.id}`, {}, res => {
						if(!this.$common.handleResponseMsg(res)) return;
						this.$common.showMsg("已签到", () => {
							this.getLessons()
						})
					})
				})
			},
			datePretty(date) {
				const weekDayZh = ['日', '一', '二', '三', '四', '五', '六']
				let day = new Date(date);
				return (day.getMonth() + 1) + "." + day.getDate()
			},
			appoint(lesson) {
				this.$common.showAlert("预约确认", `确认预约本课？`, ()=> {
					this.$http.post(`sCenter/lesson/appoint/${lesson.id}`, {}, res => {
						if(!this.$common.handleResponseMsg(res)) return;
						this.$common.showMsg("已提交预约", () => {
							this.getLessons()
							this.getBookable()
						})
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
</style>
