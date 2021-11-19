<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" :title="info.name + '的资料'" @clickLeft="clickLeft" />
		<view>
			<u-cell-group>
				<u-cell-item :arrow="false" title="姓名"><view class="u-font-md text-black">{{info.name}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="手机号码"><view class="u-font-md text-black">{{info.mobile}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="性别"><view class="u-font-md text-black">{{info.gender}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="年龄"><view class="u-font-md text-black">{{info.age}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="生日"><view class="u-font-md text-black">{{info.birthday}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="类型"><view class="u-font-md text-black">{{info.stage}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="备注"><view class="u-font-md text-black">{{info.remark}}</view></u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="bg-white u-padding-30 u-flex u-m-t-20">
			<view class="u-flex-3">所报课程</view>
			<view class="u-flex-1 u-text-right">
			</view>
		</view>
		
		<view class="u-margin-bottom-20">
			<!-- <u-read-more :show-height="500" close-text="全部学员"> -->
				<u-cell-group title="">
					<u-cell-item v-for="(item,index) in courseList" :key='index' center :is-link="false" :arrow="false" @click="studentdetail(item.id)">
						<view slot="title">{{item.courseName}}</view>
						<view slot="label">学习进度 {{item.countLessonComplete}} / {{item.countLessonTotal}}</view>
						<view slot="label">有效期 {{item.expireDate}}</view>
						<view >剩余: {{item.countLessonRemaining||0}}次</view>
					</u-cell-item>
				</u-cell-group>
			<!-- </u-read-more> -->
			<view class="u-padding-30 bg-white" v-if="courseList.length == 0" >
				<u-empty mode="list" text="暂无学员"></u-empty>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				info: {},
				courseList: [],
			}
		},
		onLoad(option) {
			this.studentId = option.studentId
			this.getInfo()
			this.getCourseList()
		},
		methods: {
			getInfo() {
				this.$http.get('tCenter/student/info', {
					id: this.studentId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			getCourseList() {
				this.$http.get('tCenter/student/courseStats', {
					studentId: this.studentId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.courseList = res
				})
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ededed;
	}

	.camera {
		width: 54px;
		height: 44px;

		&:active {
			background-color: #ededed;
		}
	}

	.user-box {
		background-color: #fff;
	}
</style>
