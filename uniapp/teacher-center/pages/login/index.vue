<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content ">
			<view class="title">欢迎使用教务系统</view>
			<view class="input-box">
				<input class="u-border-bottom" type="text" v-model="username" placeholder="请输入账号" />
				<view class="tips">若忘记账号请联系系统管理员.</view>
				<input class="u-border-bottom" type="password" v-model="password" placeholder="请输入密码" />
		
				<button @click="submit" class="getCaptcha">登录系统</button>
				<!-- <view class="alternative">
					<view class="password">密码登录</view>
					<view class="issue">遇到问题</view>
				</view> -->
			</view>
		</view>
		<view class="buttom">
			<view class="loginType">
			  <!-- <view class="item" @click="wxLogin" v-if="inWeixin">
					<view class="icon"><u-icon size="70" name="weixin-fill" color="rgb(83,194,64)"></u-icon></view>
					微信登录
				</view> -->
			</view>
			<view class="hint">
				<!-- 登录代表同意<text class="link">本机构的用户协议、隐私政策，</text>并授权使用您的个人信息. -->
				本系统由宏之博信息技术提供技术支持, 热线15666323771
			</view>
		</view>
	</view>
</template>

<script>
import {showMsg, getToken} from '../../util/common.js'
import * as db from '../../util/db.js'
import wechat from "@/util/wechat.js"
import Cookies from "@/util/js.cookie.js"
export default {
	data() {
		return {
			username: 'admin',
			password: '123',
		}
	},
	onLoad() {
		this.inWeixin = wechat.isWechat()
		console.log(this.$common.getAccessToken())
	},
	methods: {
		wxLogin() {
			this.getWxRedirctUrl();
		},
		getWxRedirctUrl() {
			this.$http.get('wx/portal/default/loginUrl', {state: 'teacher'}, res => {
				if (!this.$common.handleResponseMsg(res)) return;
				window.location.href = res
			})
		},
		submit() {
			var formData = {username: this.username, password: this.password}
			this.$http.postForm("common/login", formData, res => {
				if(!this.$common.handleResponseMsg(res)) return;
				if(res.errCode == 0) {
					this.$store.commit("login", res.data)
					showMsg("登录成功")
					uni.switchTab({
						url: '/pages/index/index'
					})
				}
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
		margin: 80rpx 40rpx 0;

		.title {
			text-align: left;
			font-size: 60rpx;
			font-weight: 500;
			margin-bottom: 100rpx;
		}
		.input-box{
			background-color: #fff;
			border-radius: 30rpx;
			padding: 60rpx 30rpx;
		}
		input {
			text-align: left;
			margin-bottom: 10rpx;
			padding-bottom: 6rpx;
		}
		.tips {
			color: $u-type-info;
			margin-bottom: 60rpx;
			margin-top: 8rpx;
			font-size: 24rpx;
		}
		.getCaptcha {
			margin-top: 60rpx;
			background-color: #2979ff;
			color: #fff;
			border: none;
			font-size: 32rpx;
			padding: 12rpx 0;
			
			&::after {
				border: none;
			}
		}
		.alternative {
			color: $u-tips-color;
			display: flex;
			justify-content: space-between;
			margin-top: 30rpx;
		}
	}
	.buttom {
		.loginType {
			display: flex;
			padding: 150rpx;
			justify-content: center;
			.item {
				color: $u-content-color;
				font-size: 28rpx;
				text-align: center;
			}
		}
		
		.hint {
			padding: 20rpx 40rpx;
			font-size: 20rpx;
			color: $u-tips-color;
			text-align: center;
			.link {
				color: $u-type-warning;
			}
		}
	}
}
</style>
