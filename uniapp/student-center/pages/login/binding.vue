<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content ">
			<view class="title">绑定学生手机号</view>
			<view class="input-box">
				<input class="u-border-bottom" type="number" v-model="username" placeholder="请输入手机号" />
				<view class="tips">请输入学员手机号</view>
				<input class="u-border-bottom" type="password" v-model="password" placeholder="请输入密码" />
				<view class="tips">学员密码请向本机构老师获取.</view>
					<select class="u-border-bottom" v-model="relation" style="width: 100%;padding: 20rpx;border: none;">
						<option value="self">学生本人</option>
						<option value="mother">母亲</option>
						<option value="father">父亲</option>
						<option value="grandmother_f">奶奶</option>
						<option value="grandfather_f">爷爷</option>
						<option value="grandmother_m">奶奶</option>
						<option value="grandfather_m">奶奶</option>
					</select>
					<view class="tips">您与学生的关系</view>

					<button @click="submit" :style="[inputStyle]" class="getCaptcha">立即绑定</button>

			</view>
		</view>
		<view class="buttom">

			<view class="hint">
				登录代表同意<text class="link" @click="showTerm=true">用户协议、隐私政策，</text>并授权使用您提供的个人信息.
			</view>
		</view>
		<TextModal :show.sync = 'showTerm'/>
	</view>
</template>
<script>
	import TextModal from "@/components/TextModal.vue"
	import {
		domainUrl
	} from "@/util/config.js"

	export default {
		components: {TextModal},
		data() {
			return {
				showTerm: false,
				username: '',
				password: '',
				relation: '',
				acid: '',
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.username) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['success'];
				}
				return style;
			}
		},
		onLoad(option) {
			this.acid = option.acid
		},
		methods: {
			submit: async function() {
				if (this.password == '') {
					this.$common.showMsg("请输入密码")
					return;
				}
				if (this.$u.test.mobile(this.username) == false) {
					this.$common.showMsg("手机号输入有误")
				}
				var fromData = {
					username: this.username,
					password: this.password,
					relation: this.relation,
					acid: this.acid,
				}
				console.log(fromData)
				// const res = await this.$apis.login(fromData)
				// if (!this.$common.handleResponseMsg(res)) return;
				// if (res.errCode == 0) {
				// 	this.$store.commit("login", res.data)
				// 	this.$common.showMsg("登录成功")
				// 	uni.switchTab({
				// 		url: '/pages/index/index'
				// 	})
				// }
			},
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

			.input-box {
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
				padding: 150rpx 100rpx;
				justify-content: space-between;

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
