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
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="heart-fill" :icon-style="{color: '#ff745b'}" title="报名记录" @click="contractrecord"></u-cell-item>
				<u-cell-item icon="checkmark-circle-fill" :icon-style="{color: '#17bcff'}" title="签到记录" @click="signrecord"></u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="question-circle-fill" :icon-style="{color: '#ca6ddc'}" title="使用说明" @click="helpPage"></u-cell-item>
				<!-- <u-cell-item icon="setting" title="设置"></u-cell-item> -->
			</u-cell-group>
		</view>
	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info: {
					name: '未登录'
				},
				counts: {},
				current_student: {},
			}
		},
		onShow() {
			this.getStudentInfo();
			this.current_student = uni.getStorageSync("current-student-info")
		},
		methods: {
			getStudentInfo() {
				this.$http.get('sCenter/getCurrentUserInfo',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.info = res
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
			helpPage() {
				uni.navigateTo({
					url: '/pages/help/index'
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
