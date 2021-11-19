<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content ">
			<view class="title">欢迎登录</view>
			<view class="input-box">
				<input class="u-border-bottom" type="number" v-model="username" placeholder="请输入手机号" />
				<view class="tips">请输入手机号进行登录.</view>
				<input class="u-border-bottom" type="password" v-model="password" placeholder="请输入密码" />
				<view class="tips">登录密码请向本机构老师获取.</view>
				<button @click="submit" :style="[inputStyle]" class="getCaptcha">登 录</button>
				<!-- <view class="alternative">
					<view class="password">密码登录</view>
					<view class="issue">遇到问题</view>
				</view> -->
			</view>
		</view>
		<view class="buttom">
			<view class="loginType">
				<view class="wechat item" @click="wxLogin" v-if="inWeixin">
					<view class="icon"><u-icon size="100" name="weixin-fill" color="rgb(83,194,64)"></u-icon></view>
					微信登录
				</view>
			</view>
			<view class="hint">
				登录代表同意<text class="link">用户协议、隐私政策，</text>并授权使用您提供的个人信息.
			</view>
		</view>
	</view>
</template>
<script>
import {domainUrl} from "@/util/config.js"
import wechat from "@/util/wechat.js"
export default {
	data() {
		return {
			username: '15666323771',
			password: '123',
			redirectUrl: '',
			inWeixin: false,
		}
	},
	computed: {
		inputStyle() {
			let style = {};
			if(this.username) {
				style.color = "#fff";
				style.backgroundColor = this.$u.color['success'];
			}
			return style;
		}
	},
	onLoad() {
		this.inWeixin = wechat.isWechat()
	},
	methods: {
		wxLogin() {
			this.getWxRedirctUrl();
		},
		getWxRedirctUrl() {
			this.$http.get('wx/portal/default/loginUrl', {state: 'student'}, res => {
				if (!this.$common.handleResponseMsg(res)) return;
				window.location.href = res
			})
		},
		submit: async function() {
			if(this.password == '') {
				this.$common.showMsg("请输入密码")
				return;
			}
			if(this.$u.test.mobile(this.username) == false) {
				this.$common.showMsg("手机号输入有误")
			}
			var fromData = {
				username: this.username,
				password: this.password
			}
		
			const res = await this.$apis.login(fromData)
			if(!this.$common.handleResponseMsg(res)) return;
			if(res.errCode == 0) {
				this.$store.commit("login", res.data)
				this.$common.showMsg("登录成功")
				uni.switchTab({
					url: '/pages/index/index'
				})
			}
			
		},
		
		// getcode() {
		// 	if(this.password == '') {
		// 		this.$common.showMsg("请输入密码")
		// 		return;
		// 	}
		// 	if(this.$u.test.mobile(this.username) == false) {
		// 		this.$common.showMsg("手机号输入有误")
		// 	}
		// 	this.$u.route({
		// 		url: 'pages/login/code'
		// 	})
		// }
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
			color: $u-type-info-disabled;
			margin-bottom: 60rpx;
			margin-top: 8rpx;
			font-size: 24rpx;
		}
		.getCaptcha {
			background-color: $u-type-success-light;
			color: $u-type-success;
			border: none;
			font-size: 30rpx;
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
			padding: 130rpx 100rpx ;
			justify-content:space-between;
			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				color: $u-content-color;
				font-size: 28rpx;
				background-color: #fff;
				width: 180rpx;
				height: 180rpx;
				padding-top: 20rpx;
				border-radius: 50%;
				margin: 0 auto;
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
