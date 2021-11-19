<template>
	<view class="wrap">
		<view class="key-input">
			<view class="title">输入验证码</view>
			<view class="tips">验证码已发送至 +150****9320</view>
			<u-message-input :focus="true" :value="value" @change="change" @finish="finish" mode="bottomLine" :maxlength="maxlength"></u-message-input>
			<text :class="{ error: error }">验证码错误，请重新输入</text>
			<view class="captcha">
				<text :class="{ noCaptcha: noCaptcha }" @tap="handleNoCaptcha">收不到验证码点这里</text>
				<text :class="{ regain: !noCaptcha }">{{ second }}秒后重新获取验证码</text>
			</view>
		</view>
	</view>
</template>

<script>
	const repeatSecond = 3
export default {
	data() {
		return {
			maxlength: 4,
			value: '',
			second: repeatSecond,
			error: false,
			noCaptcha: false,
		};
	},
	computed: {},
	onLoad() {
		this.loop()
	},
	methods: {
		loop() {
			let interval = setInterval(() => {
				this.second--;
				if (this.second <= 0) {
					this.noCaptcha = true
					clearInterval(interval);
				}
			}, 1000);
		},
		// 收不到验证码选择时的选择
		handleNoCaptcha() {
			uni.showActionSheet({
				itemList: ['重新获取验证码'],
				success: (res) => {
					if(res.tapIndex == 0) {
						console.log('重发成功后执行:')
						this.noCaptcha = false
						this.second = repeatSecond
						this.loop()
					}
					// console.log(res)
				},
				fail: function(res) {
					console.log('fail')
				}
			});
		},
		// change事件侦听
		change(value) {
			// console.log('change', value);
		},
		// 输入完验证码最后一位执行
		finish(value) {
			console.log('finish', value);
		}
	}
};
</script>

<style lang="scss" scoped>
.wrap {
	padding: 80rpx;
}

.box {
	margin: 30rpx 0;
	font-size: 30rpx;
	color: 555;
}

.key-input {
	padding: 30rpx 0;
	text {
		display: none;
	}
	.error {
		display: block;
		color: red;
		font-size: 30rpx;
		margin: 20rpx 0;
	}
}

.title {
	font-size: 50rpx;
	color: #333;
}

.key-input .tips {
	font-size: 30rpx;
	color: #333;
	margin-top: 20rpx;
	margin-bottom: 60rpx;
}
.captcha {
	color: $u-type-warning;
	font-size: 30rpx;
	margin-top: 40rpx;
	.noCaptcha {
		display: block;
	}
	.regain {
		display: block;
	}
}
</style>
