<template>
	<view class="wrap">
		<image src="../../static/bg.png" class="topbg"></image>
		<view class="top"></view>
		<view class="content ">
			<view class="text-white">你 好 ！</view>
			<view class="title text-white">欢迎注册账号</view>

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
					<u-icon name="mob" color="#fff" custom-prefix="custom-icon"></u-icon>
					姓 名
				</view>
				<view class="inputvalue">
					<input class="u-border-bottom" v-model="name" placeholder="请输入您的称呼" />
				</view>
			</view>

			<view class="inputbox u-margin-bottom-30">
				<view class="inputlabel">
					<u-icon name="pwd" color="#fff" custom-prefix="custom-icon"></u-icon>
					登录密码
				</view>
				<view class="inputvalue">
					<input class="u-border-bottom" type="password" v-model="password" placeholder="至少6位密码" />
				</view>
			</view>

			<!-- <view class="inputbox u-margin-bottom-30">
				<view class="inputlabel">
					<u-icon name="caozuo-canting" color="#fff" custom-prefix="custom-icon"></u-icon>
					就餐类型
				</view>
				<view class="inputvalue">
					<u-radio-group v-model="meal_type" @change="radioGroupChange">
						<u-radio name="han" active-color="#3abf9d">汉餐</u-radio>
						<u-radio name="qz" active-color="#3abf9d">清真餐</u-radio>
					</u-radio-group>
				</view>
			</view> -->

			<view class="u-margin-30 u-text-right text-gray">
				<text @click="forget">忘记密码？</text>
			</view>

			<button @click="submit" class="submitbtn">注 册</button>


		</view>
		<view class="buttom">
			<view class="hint">
				<u-checkbox @change="checkboxChange" v-model="checked" size="28">
					<text class="u-font-12">用户协议、隐私政策并授权使用您提供的个人信息.</text>
				</u-checkbox>
				<view class="link" @click="showTerm=true">查看协议内容</view>
			</view>
		</view>
		<TextModal :show.sync='showTerm' />
	</view>
</template>
<script>
	import TextModal from "@/components/TextModal.vue"
	import {
		domainUrl
	} from "@/util/config.js"

	export default {
		components: {
			TextModal
		},
		data() {
			return {
				showTerm: false,
				name: '',
				mobile: '',
				password: '',
				repassword: '',
				smscode: '',
				// meal_type: 'han',
				checked: false,
				agree: 0,
				disabled: false,

				redirectUrl: '',
				smsinfo: '发送短信',
				totalsec: 0,
				cursec: 0,
				inloop: false,
				timeclear: null,
			}
		},
		onLoad() {},
		methods: {
			radioGroupChange(e) {
				console.log(e);
			},
			checkboxChange(e) {
				this.agree = e.value ? 1 : 0
			},
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
					name: this.name,
					password: this.password,
					agree: this.agree,
				}
				this.disabled = true;
				const res = await this.$apis.register(fromData)
				if (!this.$common.handleResponseMsg(res)) return;
				if (res.errCode == 0) {
					this.$common.showMsg("注册成功", () => {
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
				const res = await this.$apis.sendsms(this.mobile, 'student_register' )
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
