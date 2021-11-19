<template>
	<view class="u-card-wrap">
		<view class="content">
			<ren-calendar ref='ren' :markDays='markDays' @onDayClick='onDayClick' @onChangeMonth="changeMonth"></ren-calendar>
			
			<u-card :title="curDate+'课表'"  :sub-title="lessonList.length>0 ? `本日${lessonList.length}节课` : '本日无课'" :padding="20">
				<view class="" slot="body">
					<view class="bg-light-gray u-flex boder-radius-md u-padding-20 u-margin-bottom-10"
						v-for="(item,index) in lessonList" :key="index">
						<view class="u-flex-3">
							<view class="line-height-md">
								<view class="u-black u-type-primary-dark">{{item.startTime}} ~ {{item.endTime}} {{item.state}}</view>
								<view class="u-font-md text-black">{{item.className}} {{item.courseName}}</view>
								<view class="u-font-sm">老师: {{item.teacherNames}} 教室: {{item.classroom}}</view>
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
				markDays: [],

				lessonList: [{
						time: "14: 00"
					},
					{
						time: "14: 00"
					},
					{
						time: "14: 00"
					},
				]
			}
		},
		onReady() {
			let today = this.$refs.ren.getToday().date;
			this.curDate = today;
		},
		onShow() {
			this.$nextTick(() => {
				this.getLessons()
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
				this.getLessons()
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
						this.$common.showMsg("已签到")
						this.getLessons()
					})
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
</style>
