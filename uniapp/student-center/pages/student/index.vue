<template>
	<view class="wrap">
		<image src="../../static/bg.png" class="topbg"></image>
		<view class="top"></view>
		<view class="content u-m-b-30">
			<view class="title text-white">学生列表</view>

			<view class="boder-radius-md u-margin-bottom-30 student-box u-flex bg-white" style="position: relative;" v-for="(item,index) in list" :key="index">
				<u-avatar :src="item.headImg ? item.headImg : '../../static/me1.png'" size="120" class="u-m-l-30"></u-avatar>
				<view class="info" @click="showProfile(item.id)">
					<view class="u-font-lg u-m-b-20">{{item.name}} 
						<u-icon name="woman" color="#ff557f" size="28" v-if="item.gender == '女'"></u-icon>
						<u-icon name="man" color="#2979ff" size="28" v-if="item.gender == '男'"></u-icon>
					</view>
					<view class="u-font-sm grid-text">{{item.age? item.age + '岁':''}}</view>
				</view>
				<u-button type="primary" shape="circle" size="medium" class="u-p-10 u-m-30 " @click="handleChange(item.id)">切换</u-button>
			</view>
		
			<view class="u-margin-bottom-30" v-if="list.length == 0">
				<u-empty mode="order" text="暂未添加学生"></u-empty>
			</view>

			<button @click="addstudent" class="submitbtn orange">添加学生</button>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				list: []
			}
		},
		onShow() {
			this.handleRequest()
		},
		methods: {
			handleRequest() {
				this.$http.get('sCenter/student/studentList',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = res
				})
			},
			addstudent() {
				uni.navigateTo({
					url: '/pages/student/new'
				})
			},	
			showProfile(id) {
				uni.navigateTo({
					url: `/pages/student/new?studentId=${id}`
				})
			},
			handleChange(id) {
				this.$http.get('sCenter/student/switchStudent',{id}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					uni.switchTab({
						url: "/pages/index/index"
					})
				})
			}
		}
	};
</script>

<style lang="scss" scoped>
	.wrap {
		font-size: 28rpx;
		height: 100%;

		.content {
			margin: 40rpx 40rpx 0;
			.title {
				text-align: left;
				font-size: 50rpx;
				font-weight: 500;
				margin-bottom: 140rpx;
			}
		}
	}
	.change-btn{
		position: absolute;
		bottom: 0rpx;
		right: 0rpx;
	}
</style>
