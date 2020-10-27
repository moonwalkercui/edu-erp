<template>
	<view>
		<view class="padding text-center margin-top">
			<view class="cu-avatar xl round" :style="[{backgroundImage : (user_info.avatar ?  'url('+user_info.avatar+')' : 'url(\'./static/img/photo_df.png\')')}]"
			 style="width: 140upx;height: 140upx;"></view>
			<view class="text-center margin-top">{{user_info.name}}</view>
		</view>
		<view class="cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-title text-blue "></text> 请选择操作
			</view>
			<view class="action">
			</view>
		</view>
		<view class="cu-list grid col-3">
			<view class="cu-item" v-for="(item,index) in cuIconList" :key="index" @tap="redirect(item.url)">
				<view :class="['cuIcon-' + item.cuIcon,'text-' + item.color]">
					<view class="cu-tag badge" v-if="item.badge!=0">
						<block v-if="item.badge!=1">{{item.badge>99?'99+':item.badge}}</block>
					</view>
				</view>
				<text>{{item.name}}</text>
			</view>
		</view>
		<view class="text-xs text-center" style="color:#aaa; width: 100%; position: fixed; bottom: 50upx;">宏之博软件提供技术支持</view>
	</view>
</template>
<script>
	import {
		baseUrl
	} from "@/common/config.js"
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import wechat from '@/common/wechat.js'
	export default {
		data() {
			return {
				cuIconList: [
				{
					cuIcon: 'calendar',
					color: 'red',
					badge: 0,
					url: 'course/calendar',
					name: '课表'
				}, 
				// {
				// 	cuIcon: 'communityfill',
				// 	color: 'orange',
				// 	badge: 1,
				// 	url: 'zone/list',
				// 	name: '新作业'
				// }, 
				{
					cuIcon: 'community',
					color: 'blue',
					badge: 0,
					url: 'zone/list',
					name: '作业圈'
				}, {
					cuIcon: 'communityfill',
					color: 'orange',
					badge: 0,
					url: 'zone/tasks',
					name: '新作业'
				}, {
					cuIcon: 'write',
					color: 'cyan',
					badge: 0,
					url: 'student/bind',
					name: '绑定学生'
				}],
				user_info: {}
			};
		},
		onShow() {
			this.handleReq();
		},
		methods: {
			handleReq() {
				api.get('student/home', {}, res => {
					if (res.result == 'success') {
						this.user_info = res.data.user_info;
						var task_num = res.data.task_num;
						if(task_num > 0)
							for (var item of this.cuIconList) {
								if (item.url == 'zone/tasks') {
									item.badge = task_num;
									break;
								}
							}
					} else {
						common.modelShow('错误', res.msg)
					}
				})
			},

			redirect(url) {
				switch (url) {
					// case "scan":  this.handelScan(); break;
					// case "logout":  this.handleLogout(); break;
					default:
						uni.navigateTo({
							url: '/pages/' + url,
							animationDuration: 200
						})
				}
			},
			handleLogout() {
				console.log('logout')
			}
		}
	}
</script>

<style>
</style>
