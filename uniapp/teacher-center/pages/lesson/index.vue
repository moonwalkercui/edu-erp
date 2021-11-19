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
								<view class="u-font-sm">{{item.teacherNames}} {{item.classroom}}</view>
							</view>
						</view>
						<view class="u-flex-2 u-text-right">
							<view class="u-font-sm u-margin-bottom-30">到课{{item.studentSignNum || 0}}/{{item.studentNum || 0}}</view>
							<view>
								 <u-button type="success" shape="circle" size="mini" @click="handleEvaluate(item.id)" class="u-m-r-20">点 评</u-button> 
								 <u-button type="primary" shape="circle" size="mini" @click="handleRollcall(item.id)">点 名</u-button>
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
				lessonList: []
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
				this.$http.get('tCenter/lesson/list', {
					date: this.curDate
				}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.lessonList = res
				})
			},
			lessonCountEveryDay() {
				this.$http.get('tCenter/lesson/lessonCountEveryDay', {
					date: this.curDate
				}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.markDays = res
				})
			},
			handleRollcall(lessonId) {
				uni.navigateTo({ url : `/pages/lesson/rollcall?lessonId=${lessonId}` });
			},
			handleEvaluate(lessonId) {
				uni.navigateTo({ url : `/pages/lesson/evaluate?lessonId=${lessonId}` });
			},
			onDayClick(data) {
				this.curDate = data.date;
				this.getLessons()
			},
			changeMonth(day) {
				this.curDate = day;
				this.getLessons()
				this.lessonCountEveryDay()
			}
		}
	}
</script>

<style lang="scss" scoped>
</style>
