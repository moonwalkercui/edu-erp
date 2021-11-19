<template>
	<view>
		<view class="u-flex  u-p-30 bg-primary">
			<view class="u-m-r-10">
				<u-avatar :src="info.headImg" size="100"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-10 text-white">{{info.name ? info.name : info.nickname}}</view>
				<view class="u-font-14 u-tips-color text-white">{{info.mobile}}</view>
			</view>
			<view class="u-m-l-10 u-p-10" @click="myprofile">
				<text class="u-font-sm text-white">资料</text>
				<u-icon name="arrow-right" color="#fff" size="28"></u-icon>
			</view>
		</view>
		
		<view class="u-p-30 u-flex" style="background-color: #9fe6d4;" @click="studentManage" v-if="current_student.name">
			<view class="u-flex-1">当前登录学生：{{current_student.name}}</view>
			<view>
				<u-icon name="list-dot"></u-icon>
			</view>
		</view>
		
		<u-cell-group>
			<u-cell-item v-for="(item,index) in classList" :key="index" center :is-link="true" index="index" @click="showClassDetail(item.id)" :arrow="true">
				<view slot="title">{{item.name}} {{item.beOver ? '(已结课)' : ''}}</view>
				<view slot="label">班主任: {{item.teacherName}} 课程: {{item.courseName}}</view>
				<view slot="label">{{item.startDate}} ~ {{item.endDate}}</view>
				<view>{{item.studentCount}}人</view>
			</u-cell-item>
		</u-cell-group>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="edit-pen-fill" :icon-style="{color: '#0083ff'}" title="课后点评" @click="lessonEvaluate">
					<u-badge :absolute="false" :count="counts.evaluate_count" slot="right-icon"></u-badge>
				</u-cell-item>
				<u-cell-item icon="file-text-fill" :icon-style="{color: '#1dc8bf'}" title="成绩单" @click="showGrade">
					<u-badge :absolute="false" :count="counts.grade_count" slot="right-icon"></u-badge>
				</u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="heart-fill" :icon-style="{color: '#ff745b'}" title="签约记录" @click="contractrecord"></u-cell-item>
				<u-cell-item icon="checkmark-circle-fill" :icon-style="{color: '#17bcff'}" title="签到记录" @click="signrecord"></u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="question-circle-fill" :icon-style="{color: '#ca6ddc'}" title="使用帮助" @click="helpPage"></u-cell-item>
			</u-cell-group>
		</view>
		
		<!-- <view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="setting" title="设置"></u-cell-item>
			</u-cell-group>
		</view> -->
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info: {
					name: '未登录'
				},
				classList: [],
				counts: {},
				current_student: {},
			}
		},
		onShow() {
			this.getStudentInfo();
			this.getClassList();
			this.getRedpoint();
			this.current_student = uni.getStorageSync("current-student-info")
		},
		methods: {
			getStudentInfo() {
				this.$http.get('sCenter/getCurrentUserInfo',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			getRedpoint() {
				this.$common.getRedpoint((counts) => {
					this.counts = counts
				})
			},
			getClassList() {
				this.$http.get('sCenter/class/list',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.classList = res
				})
			},
			myclass() {
				uni.navigateTo({
					url: '/pages/class/detail'
				});
			},
			myprofile() {
				uni.navigateTo({
					url: '/pages/center/profile'
				});
			},
			contractrecord() {
				uni.navigateTo({
					url: '/pages/center/contract-record'
				});
			},
			signrecord() {
				uni.navigateTo({
					url: '/pages/lesson/sign-record'
				});
			},
			lessonEvaluate() {
				uni.navigateTo({
					url: '/pages/lesson/evaluate'
				});
			},
			showGrade() {
				uni.navigateTo({
					url: '/pages/center/grade'
				});
			},
			helpPage() {
				uni.navigateTo({
					url: '/pages/help/index'
				});
			},
			showClassDetail(id) {
				uni.navigateTo({
					url: `/pages/class/detail?classId=${id}`
				});
			},
			studentManage() {
				uni.navigateTo({
					url: "/pages/student/index"
				})
			},
		},
	}
</script>

<style lang="scss">
page{
	background-color: #ededed;
}

.camera{
	width: 54px;
	height: 44px;
	
	&:active{
		background-color: #ededed;
	}
}
.user-box{
	background-color: #fff;
}
</style>
