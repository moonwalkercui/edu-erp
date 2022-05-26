<template>
	<view>
		<view class="u-margin-30 u-p-40 u-p-t-80 u-p-b-80 bg-white boder-radius-md u-text-center">
			<view class="icon-box">
				<u-icon name="checkbox-mark" color="#ffffff" size="64"></u-icon>
			</view>
			<view class="u-font-18 text-bold u-m-t-80">支付成功</view>
			<view class="u-m-t-80 u-flex">
				<view class="u-flex-1 u-m-10">
					<u-button @click="toHome()">返回首页</u-button>
				</view>
				<view class="u-flex-1 u-m-10">
					<u-button @click="myOrder()">我的订单</u-button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
			}
		},
		methods: {
			toHome() {
				uni.switchTab({
					url: '/pages/index/index'
				})
			},
			myOrder() {
				uni.redirectTo({
					url: '/pages/shop/myOrder'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.icon-box {
		width: 200rpx;
		height: 200rpx;
		line-height: 220rpx;
		border-radius: 50%;
		background-color: $u-type-primary;
		margin: 50rpx auto;
	}
</style>
