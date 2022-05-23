<template>
	<view class="wrap">
		<image src="../../static/bg.png" class="topbg"></image>
		<view class="top"></view>
		<view class="content ">
			<view class="text-white">你 好 ！</view>
			<view class="title text-white">欢迎登录</view>

			<view class="inputbox u-margin-bottom-30">
				<view class="inputlabel">
					<u-icon name="mob" color="#fff" custom-prefix="custom-icon"></u-icon>
					手机号码
				</view>
				<view class="inputvalue">
					<input class="u-border-bottom" type="number" v-model="mobile" placeholder="请输入手机号" />
				</view>
			</view>

			<view class="inputbox u-margin-bottom-30">
				<view class="inputlabel">
					<u-icon name="pwd" color="#fff" custom-prefix="custom-icon"></u-icon>
					登录密码
				</view>
				<view class="inputvalue">
					<input class="u-border-bottom" type="password" v-model="password" placeholder="请输入密码" />
				</view>
			</view>

			<view class="u-margin-30 u-text-right text-gray">
				<text>登录密码请向老师获取，或</text>
				<text class="text-black" @click="forget">找回密码</text> 
			</view>

			<button @click="submit" :style="[inputStyle]" class="submitbtn">登 录</button>
			<button @click="register" class="submitbtn light" style="margin-top: 50rpx;">注册新账号</button>

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
				<!-- 欢迎登录，使用中遇到的问题可以通过意见反馈发给我们。 -->
			<!-- 	<view class="link u-m-t-20">
					为保障您的利益，请使用您本人的微信登录本系统。
				</view> -->
			</view>
		</view>
	</view>
</template>
<script>
	import {domainUrl} from "@/util/config.js"
	import wechat from "@/util/wechat.js"
	import {debug} from "@/util/config.js"
	export default {
		data() {
			return {
				mobile: '',
				password: '',
				inWeixin: false,
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (!this.mobile) {
					style.color = "#fff";
					style.backgroundColor = 'gray';
				}
				return style;
			}
		},
		onLoad() {
			this.inWeixin = wechat.isWechat()
			// this.$common.getUserInfo()
		},
		methods: {
			wxLogin() {
				this.getWxRedirctUrl();
			},
			getWxRedirctUrl() {
				this.$http.get('wx/portal/default/loginUrl', {state: 'student'}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					//window.location.href = res
					window.open(res)
				})
			},
			submit: async function() {
				if(this.password == '') {
					this.$common.showMsg("请输入密码")
					return;
				}
				if(this.$u.test.mobile(this.mobile) == false) {
					this.$common.showMsg("手机号输入有误")
				}
				var fromData = {
					username: this.mobile,
					password: this.password
				}
			
				const res = await this.$apis.login(fromData)
				if(!this.$common.handleResponseMsg(res)) return;
				if(res.errCode == 0) {
					this.$store.commit("login", res.data)
					this.$common.showMsg("登录成功")
					uni.removeStorageSync("current-student-info")
					uni.switchTab({
						url: '/pages/index/index'
					})
				}
			},
			register() {
				uni.navigateTo({
					url: `/pages/login/register`
				});
			},
			forget() {
				uni.navigateTo({
					url: `/pages/login/forget`
				});
			},
			// getcode() {
			// 	if(this.password == '') {
			// 		this.$common.showMsg("请输入密码")
			// 		return;
			// 	}
			// 	if(this.$u.test.mobile(this.mobile) == false) {
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
			margin: 40rpx 40rpx 0;

			.title {
				text-align: left;
				font-size: 50rpx;
				font-weight: 500;
				margin-bottom: 140rpx;
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

			.alternative {
				color: $u-tips-color;
				display: flex;
				justify-content: space-between;
				margin-top: 30rpx;
			}
		}
		.buttom {
			margin-top: 100rpx;
			.loginType {
				padding: 30rpx 100rpx ;
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
