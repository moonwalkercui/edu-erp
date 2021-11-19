<template>
	<view class="wrap">
		<view class="content ">
			<view class="u-m-b-30 bg-white boder-radius-md u-text-center u-padding-20 text-orange">
				<u-icon name="caozuo-canting" custom-prefix="custom-icon"></u-icon>
				请选择学员进行操作:
			</view>
			<view class="boder-radius-md u-margin-bottom-30 student-box u-flex" :style="{backgroundColor: item.gender == 1 ? '#98b6f3' : '#ffabe8'}"
				v-for="(item,index) in list" :key="index" @click="handlechoose(item)">
				<image class="avatar" :src="item.avatar ? item.avatar : '../static/me1.png'" mode=" center"></image>
				<view class="info">
					<view class="u-font-lg u-m-b-10">{{item.name}}</view>
					<view class="u-font-sm grid-text">{{item.school_name}}</view>
					<view class="u-font-sm grid-text">{{item.class_name}}</view>
				</view>
			</view>
<!-- 
			<view class="boder-radius-md u-margin-bottom-30 student-box u-flex" style="background-color: #ffabe8;">
				<image class="avatar" src="../static/p3.png" mode=" center"></image>
				<view class="info">
					<view class="u-font-lg u-m-b-10">王同学</view>
					<view class="u-font-sm grid-text">实现小学</view>
					<view class="u-font-sm grid-text">三年级2班</view>
				</view>
				<image class="gender" src="../static/p5.png" mode="aspectFill"></image>
			</view> -->
			
			<!-- <u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" index="index"
					@click="showList(item.id)" :title-style="{'width': '75%'}" :arrow="true">
					<view slot="title">{{item.title}}</view>
					<view slot="label">{{item.className}}</view>
					<view :class="[!item.completed ? 'text-red':'']">{{item.completed?'已交':'未交'}}</view>
				</u-cell-item>
			</u-cell-group> -->
			<view class="u-margin-bottom-30" v-if="list.length == 0">
				<u-empty mode="order" text="暂未添加学生"></u-empty>
			</view>
			<button @click="addstudent" class="submitbtn light">添加学生</button>
		</view>
	</view>
</template>
<script>
	export default {
		name:"ChooseStudent",
		data() {
			return {
				list: []
			}
		},
		created() {
			this.handleRequest()
		},
		methods: {
			handleRequest() {
				this.$http.get('student/myStudents',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = res.data
				})
			},
			handlechoose(item) {
				this.$common.setChooseStudent(item)
				var url = this.$common.getStorageUrl()
				if(url == '' || typeof url == 'undefined') {
					this.$common.showMsg("缺少跳转URl")
					return
				}
				uni.navigateTo({
					url: '/pages/' + url
				})
			},
			addstudent() {
				uni.navigateTo({
					url: '/pages/student/new'
				})
			},	
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

	
</style>
