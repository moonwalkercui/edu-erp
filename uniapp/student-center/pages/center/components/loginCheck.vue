<template>
	<u-popup v-model="visible" mode="bottom"  border-radius="14" height="500rpx" :closeable="true" :closeOnClickOverlay="false" @close="handleClose">
		<view class="u-text-center">
			<view class="u-font-16 u-p-30 text-bold">- 在线购课需要微信登录 -</view>
			<view class="u-p-30 ">
				<view class="loginType">
					<view class="wechat item" @click="getWxRedirctUrl">
						<view class="icon"><u-icon size="100" name="weixin-fill" color="rgb(83,194,64)"></u-icon></view>
						微信登录
					</view>
				</view>
			</view>
		</view>
	</u-popup>
</template>

<script>
	export default {
		props: {
		},
		data() {
			return {
				visible: false,
			}
		},
		created() {
		},
		methods: {
			needLogin() {
				return new Promise((resove, reject)=>{
					this.$http.post('sCenter/checkWxBinding', {}, res => {
						if(!res) {
							this.$common.storeRedirectUrl()
							this.visible = true
							resove(true)
						}else {
							resove(false)
						}
					})
				})
			},
			getWxRedirctUrl() {
				this.$http.get('wx/portal/default/loginUrl', {state: 'student'}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					window.open(res)
				})
			},
			handleClose() {
				this.$common.removeRedirectUrl()
			}
		}
	};
</script>

<style lang="scss" scoped>
	.loginType {
		padding: 30rpx 100rpx ;
		justify-content:space-between;
		.item {
			display: flex;
			flex-direction: column;
			align-items: center;
			color: $u-content-color;
			font-size: 28rpx;
			background-color: #cbf4d8;
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
</style>
