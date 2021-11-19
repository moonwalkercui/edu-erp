<template>
	<view>
		<uni-nav-bar :right-text="staffInfo.name" title="系统工作台" @clickRight="clickRight" />
		<u-image width="100%" height="200rpx" src="../../static/banner.jpg" mode="widthFix"></u-image>
		<view class="u-card-wrap">
			<u-card title="常用功能" :padding="20">
				<view class="" slot="body">
						<u-grid :col="4" @click="click" :border="false">
							<u-grid-item name="item1" :index="0" @click="toLessonPage">
								<u-badge :count="lessonList.length" :offset="[14, 20]"></u-badge>
								<view class="main-icon main-icon-blue">
									<u-icon name="calendar-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">课表</view>
							</u-grid-item>
							<u-grid-item :index="1">
								<view class="main-icon main-icon-cyan" @click="newContract">
									<u-icon name="rmb-circle-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">报单</view>
							</u-grid-item>
							<u-grid-item :index="2">
								<view class="main-icon main-icon-orange" @click="toClass">
									<u-icon name="banjiguanli" :size="46" color="white" custom-prefix="custom-icon">
									</u-icon>
								</view>
								<view class="grid-text">班级</view>
							</u-grid-item>
							<u-grid-item :index="3">
								<view class="main-icon main-icon-sky" @click="toCourse">
									<u-icon name="grid-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">课程</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/student/new')">
								<view class="main-icon main-icon-yellow">
									<u-icon name="plus-people-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">添加学员</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/student/index')">
								<view class="main-icon main-icon-ocean">
									<u-icon name="account-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">我的客户</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/contract/index')">
								<view class="main-icon main-icon-purple">
									<u-icon name="rmb-circle-fill" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">报单记录</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/center/rollcall-record')">
								<view class="main-icon main-icon-cyan">
									<u-icon name="list-dot" :size="46" color="white"></u-icon>
								</view>
								<view class="grid-text">点名记录</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/homework/index')">
								<view class="main-icon main-icon-cyan">
									<u-icon name="dazuoye" custom-prefix="custom-icon" :size="46" color="white">
									</u-icon>
								</view>
								<view class="grid-text">作业</view>
							</u-grid-item>

							<u-grid-item :index="3" @click="handleNav('/pages/evaluate/index')">
								<view class="main-icon main-icon-orange">
									<u-icon name="mianqiangxingxing" custom-prefix="custom-icon" :size="46"
										color="white"></u-icon>
								</view>
								<view class="grid-text">点评记录</view>
							</u-grid-item>
						</u-grid>
				</view>
			</u-card>

			<u-card title="今日课表" :sub-title="lessonList.length>0 ? `今日${lessonList.length}节课` : '无课'" :padding="20">
				<view class="" slot="body">
					<view class="bg-light-gray u-flex boder-radius-md u-padding-20 u-margin-bottom-10"
						v-for="(item,index) in lessonList" :key="index">
						<view class="u-flex-3">
							<view class="line-height-md">
								<view class="u-black u-type-primary-dark">{{item.startTime}} ~ {{item.endTime}}</view>
								<view class="u-font-md text-black">{{item.className}} {{item.courseName}}</view>
								<view class="u-font-sm">{{item.teacherNames}} {{item.classroom}}</view>
							</view>
						</view>
						<view class="u-flex-1 u-text-right">
							<view class="u-flex-1 u-text-right">
								<view class="u-font-sm u-margin-bottom-40">
									到课{{item.studentSignNum || 0}}/{{item.studentNum || 0}}</view>
								<view>
									<u-button type="primary" shape="circle" size="mini"
										@click="handleRollcall(item.id)">点 名</u-button>
								</view>
							</view>
						</view>
					</view>
					<u-empty v-if="lessonList.length == 0" mode="list" text="今日无课"></u-empty>
				</view>
			</u-card>

			<view class="u-margin-30 bg-white boder-radius-sm">
				<view class="u-padding-20">我的本月数据</view>
				<view class="u-padding-left-20 u-padding-bottom-20 u-padding-right-20">
					<u-grid :col="2" @click="click" :border="true">
						<u-grid-item :index="0" @click="itemClick">
							<view style="width: 70%;">
								<view class="text-black u-type-default u-margin-bottom-10">{{counts.lesson_count}}
								</view>
								<view class="text-gray u-font-xs">本月课次数</view>
							</view>
						</u-grid-item>
						<u-grid-item :index="1">
							<view style="width: 70%;">
								<view class="text-black u-type-default u-margin-bottom-10">{{counts.rollcall_count}}
								</view>
								<view class="text-gray u-font-xs">本月点名人次</view>
							</view>
						</u-grid-item>
						<u-grid-item :index="2">
							<view style="width: 70%;">
								<view class="text-black u-type-default u-margin-bottom-10">{{counts.course_count}}
								</view>
								<view class="text-gray u-font-xs">月报单金额</view>
							</view>
						</u-grid-item>
						<u-grid-item :index="3">
							<view style="width: 70%;">
								<view class="text-black u-type-default u-margin-bottom-10">{{counts.student_count}}
								</view>
								<view class="text-gray u-font-xs">月新增学员</view>
							</view>
						</u-grid-item>
					</u-grid>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				staffInfo: {},
				lessonList: [],
				counts: {},
			}
		},
		onLoad() {
			this.getLoginStaff()
		},
		onShow() {
			this.getLessons()
			this.getCounts()
		},
		methods: {
			getCounts() {
				this.$http.get('tCenter/base/getHomeCounts', {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					console.log(res)
					this.counts = res
				})
			},
			getLoginStaff() {
				this.$http.get('tCenter/base/getLoginStaff', {
					isToday: true
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.staffInfo = res
				})
			},
			toLessonPage() {
				uni.switchTab({
					url: '/pages/lesson/index'
				})
			},
			toClass() {
				uni.switchTab({
					url: '/pages/class/index'
				})
			},
			toCourse() {
				uni.navigateTo({
					url: '/pages/course/index'
				})
			},
			newContract() {
				uni.navigateTo({
					url: '/pages/contract/new'
				})
			},
			getLessons() {
				this.$http.get('tCenter/lesson/list', {
					isToday: true
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.lessonList = res
				})
			},
			click() {

			},
			itemClick() {

			},
			handleRollcall(lessonId) {
				this.handleNav(`/pages/lesson/rollcall?lessonId=${lessonId}`)
			},
			handleNav(url) {
				uni.navigateTo({
					url
				});
			},
			clickRight() {
				uni.navigateTo({
					url: '/pages/center/profile'
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.main-icon {
		padding: 8px;
		border-radius: 30rpx;
		margin-bottom: 10rpx;
	}

	.main-icon-blue {
		background-color: #0083ff;
	}

	.main-icon-cyan {
		background-color: #1dc8bf;
	}

	.main-icon-yellow {
		background-color: #facf21;
	}

	.main-icon-orange {
		background-color: #ff745b;
	}

	.main-icon-sky {
		background-color: #17bcff;
	}

	.main-icon-purple {
		background-color: #ca6ddc;
	}

	.main-icon-ocean {
		background-color: #6289ff;
	}
</style>
