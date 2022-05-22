<template>
	<view>
		<view>
			<view class="navigation">
				<view class="left">
					<view class="item" @click="contactService">
						<u-icon name="server-fill" :size="40"></u-icon>
						<view class="text u-line-1">客服</view>
					</view>
					<view class="item" @click="showHelp">
						<u-icon name="question-circle" :size="40"></u-icon>
						<view class="text u-line-1">报名须知</view>
					</view>
					<view class="item">
					</view>
				</view>
				<view class="right">
					<u-button class="buy btn" @click="submitOrder" type="primary">立即报名</u-button>
				</view>
			</view>
		</view>
		<view>
			<u-popup v-model="showBox" mode="bottom" border-radius="14">
				<view class="u-text-center" style="min-height: 400rpx;">
					<view class="u-p-30 ">
						<view class="u-p-b-20 u-border-bottom">报名须知</view>
						<view class="u-p-t-20 u-p-b-30 u-text-left">
							<text>{{shoppingGuide}}</text>
						</view>
					</view>
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
	export default {
		props: ['courseInfo'],
		data() {
			return {
				hotline: '',
				shoppingGuide: '',
				showBox: false,
			}
		},
		created() {
			this.systemSetting()
		},
		methods: {
			systemSetting() {
				this.$common.systemSettings(['hotline', 'shopping_guide']).then(res => {
					this.hotline = res.hotline
					this.shoppingGuide = res.shopping_guide
				})
			},
			submitOrder() {
				uni.setStorageSync("order-confirm-course", this.courseInfo)
				uni.redirectTo({
					url: "/pages/shop/orderConfirm"
				})
			},
			contactService() {
				if (!this.hotline || this.hotline == '') {
					this.$common.showMsg("未设置客服电话");
					return;
				}
				uni.makePhoneCall({
					phoneNumber: this.hotline
				})
			},
			showHelp() {
				this.showBox = true;
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
			}
			.buy {
				width: 100%;
				text-align: center;
			}
		}
	}
</style>
