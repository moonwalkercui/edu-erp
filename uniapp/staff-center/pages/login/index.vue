<template>
	<view class="content">
		<view>
			<view class="cu-form-group">
				<view class="title">登录账号</view>
				<input placeholder="请输入" name="input" v-model="name" type="text"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">登录密码</view>
				<input placeholder="请输入" name="input" v-model="pwd" type="text"></input>
			</view>

			<view class="padding flex flex-direction" style="position: fixed;bottom: 100upx; width: 100%;">
				<button class="cu-btn bg-orange margin-tb-sm lg shadow" @tap="handleLogin">立即登录</button>
			</view>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import * as db from "@/common/db.js"
	export default {
		data() {
			return {
				name: '',
				pwd: '',
			}
		},
		onShow() {},
		methods: {
			handleLogin() {
				if (this.name === '') {
					common.showError('请输入账号')
					return;
				}
				if (this.pwd === '') {
					common.showError('请输入密码')
					return;
				}
				api.post('staff/login', {
					username: this.name,
					password: this.pwd
				}, res => {
					if (res.result == 'success') {
						common.showSuccess(res.msg)
						// db.set('staffToken', res.data.token)
						uni.navigateTo({
							url: '/pages/index/index',
							animationDuration: 200
						})
					} else {
						common.showError(res.msg)
					}
				})
			},
		}
	}
</script>

<style>
</style>
