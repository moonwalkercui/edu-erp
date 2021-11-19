<template>
	<view class="wrap">
		<image src="../../static/bg.png" class="topbg"></image>
		<view class="top"></view>
		<view class="content ">
			<view class="text-white">你 好 ！</view>
			<view class="title text-white">设置新密码</view>

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
					<u-icon name="anquan" color="#fff" custom-prefix="custom-icon"></u-icon>
					短信验证
				</view>
				<view class="inputvalue" style="display: flex;">
					<input class="u-border-bottom" v-model="smscode" placeholder="请输入"
						style="width: 240rpx; display: inline-block;" />
					<text class="u-font-14" @click="sendSms">{{smsinfo}}</text>
				</view>
			</view>

			<view class="inputbox u-margin-bottom-30">
				<view class="inputlabel">
					<u-icon name="pwd" color="#fff" custom-prefix="custom-icon"></u-icon>
					设置密码
				</view>
				<view class="inputvalue">
					<input class="u-border-bottom" type="password" v-model="password" placeholder="新密码至少6位" />
				</view>
			</view>

			<view class="u-margin-30 u-text-right text-gray">
				<text @click="tologin">去登录</text>
			</view>

			<button @click="submit" class="submitbtn" >提交新密码</button>

		</view>
		<view class="buttom">
			<view class="hint">
			</view>
		</view>

	</view>
</template>
<script>
	import {
		domainUrl
	} from "@/util/config.js"

	export default {
		data() {
			return {
				mobile: '',
				password: '',
				repassword: '',
				smscode: '',

				redirectUrl: '',
				smsinfo: '发送短信',
				totalsec: 6,
				cursec: 0,
				inloop: false,
				timeclear: null,

				disabled: false,
			}
		},
		onLoad() {},
		methods: {
			submit: async function() {
				if (this.mobile == '') {
					this.$common.showMsg("请输入手机号")
					return;
				}
				if (this.password == '') {
					this.$common.showMsg("请输入密码")
					return;
				}
				if (this.$u.test.mobile(this.mobile) == false) {
					this.$common.showMsg("手机号输入有误")
					return;
				}

				if (this.smscode == '') {
					this.$common.showMsg("请输入短信验证码")
					return;
				}

				var fromData = {
					mobile: this.mobile,
					smscode: this.smscode,
					password: this.password
				}
				this.disabled = true;
				const res = await this.$apis.forgetpwd(fromData)
				if (!this.$common.handleResponseMsg(res)) return;
				if (res.errCode == 0) {
					this.$common.showMsg(res.msg, () => {
						console.log(99999)
						this.disabled = false;
						uni.redirectTo({
							url: '/pages/login/index'
						})
					})
				}
				
			},
			sendSms: async function() {
				if (this.inloop) {
					return;
				}
				if (this.$u.test.mobile(this.mobile) == false) {
					this.$common.showMsg("手机号输入有误")
					return;
				}
				const res = await this.$apis.sendsms(this.mobile, 'student_forgetpw' )
				this.$common.showMsg(res.msg)
				if (res.errCode != 0) {
					return
				}
				this.totalsec = res.data || 30
				
				this.cursec = this.totalsec
				this.timeclear = setInterval(() => {
					if (this.cursec <= 0) {
						this.cursec = this.totalsec
						this.inloop = false;
						this.smsinfo = '发送短信'
						clearInterval(this.timeclear);
					} else {
						this.cursec--;
						this.inloop = true;
						this.smsinfo = this.cursec + '秒后重发'
					}
				}, 1000)
			},
			tologin() {
				uni.redirectTo({
					url: `/pages/login/index`
				});
			}
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
			margin-top: 120rpx;

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
