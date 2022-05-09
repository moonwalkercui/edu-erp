<template>
	<view class="navigation">
		<view class="left">
			<view class="item" @click="contactService">
				<u-icon name="server-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
				<view class="text u-line-1">客服</view>
			</view>
			<view class="item" @click="showHelp">
				<u-icon name="question-circle" :size="40" :color="$u.color['contentColor']"></u-icon>
				<view class="text u-line-1">报名须知</view>
			</view>
			<view class="item">
			</view>
		</view>
		<view class="right">
			<view class="buy btn u-line-1" @click="submitOrder">立即报名</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['courseInfo'],
		methods: {
			submitOrder() {
				console.log(111)
				console.log(this.courseInfo.id)
			},
			contactService() {
				
			},
			showHelp() {
				
			}
		}
	};
</script>

<style lang="scss" scoped>
	.navigation {
		position: fixed;
		bottom: 0;
		width: 100%;
		display: flex;
		margin-top: 100rpx;
		border: solid 2rpx #f2f2f2;
		background-color: #ffffff;
		padding: 16rpx 30rpx;

		.left {
			flex: 1;
			display: flex;
			font-size: 20rpx;

			.item {
				flex: 1;
				margin: 0 30rpx;
				text-align: center;

				&.car {
					text-align: center;
					position: relative;

					.car-num {
						position: absolute;
						top: -10rpx;
						right: -10rpx;
					}
				}
			}
		}

		.right {
			display: flex;
			font-size: 28rpx;
			align-items: center;
			flex: 1;

			.btn {
				line-height: 66rpx;
				padding: 0 30rpx;
				border-radius: 36rpx;
				color: #ffffff;
			}

			.cart {
				background-color: #ed3f14;
				margin-right: 30rpx;
			}

			.buy {
				background-color: #ff7900;
				width: 100%;
				text-align: center;
			}
		}
	}
</style>
