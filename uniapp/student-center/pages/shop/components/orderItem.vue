<template>
	<view class="order">
		<view class="top">
			<view class="left">
				<view class="store">{{ orderInfo.sn }}</view>
				<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
			</view>
			<view class="right">{{orderInfo.state}}</view>
		</view>
		<view class="item" v-for="(item, index) in orderInfo.itemList" :key="index">
			<view class="left">
				<image :src="item.cover" mode="aspectFill"></image>
			</view>
			<view class="content">
				<view class="title u-line-2">{{ item.itemName }}</view>
				<view class="type">{{ item.itemType }}</view>
				<view class="delivery-time">添加时间:{{ item.addTime }}</view>
			</view>
			<view class="right">
				<view class="price">
					￥{{ priceInt(item.price) }}
					<text class="decimal">.{{ priceDecimal(item.price) }}</text>
				</view>
				<view class="number">x1</view>
			</view>
		</view>
	    <view class="total">
			合计:
			<text class="total-price">
				￥{{ priceInt(orderInfo.payMoney) }}.
				<text class="decimal">{{ priceDecimal(orderInfo.payMoney) }}</text>
			</text>
		</view> 
		<view class="bottom">
			<!-- <view class="more">
				<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
			</view> -->
			<view class="exchange btn" v-if="orderInfo.state == '已付款' && orderInfo.cancelAble">申请退款</view>
			<view class="evaluate btn" v-if="orderInfo.state == '已付款'" @click="evaluate()">评 价</view>
			<!-- <view class="exchange btn" v-if="orderInfo.state == '未付款'">重新付款</view> -->
		</view>
	</view>
</template>

<script>
	export default {
		props: ['orderInfo'],
		data() {
			return {
			}
		},
		computed: {
			// 价格小数
			priceDecimal() {
				return val => {
					if (val !== parseInt(val)) return val.slice(-2);
					else return '00';
				};
			},
			// 价格整数
			priceInt() {
				return val => {
					if (val !== parseInt(val)) return val.split('.')[0];
					else return val;
				};
			}
		},
		methods: {
			evaluate() {
				this.$emit('handleEvaluate', this.orderInfo.id)
			}
		}
	}
</script>


<style lang="scss" scoped>
	.order {
		width: 710rpx;
		background-color: #ffffff;
		margin: 20rpx auto;
		border-radius: 20rpx;
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;

		.top {
			display: flex;
			justify-content: space-between;

			.left {
				display: flex;
				align-items: center;

				.store {
					margin: 0 10rpx;
					font-size: 20rpx;
					font-weight: bold;
				}
			}

			.right {
				color: $u-type-warning-dark;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0;

			.left {
				margin-right: 20rpx;

				image {
					width: 200rpx;
					height: 200rpx;
					border-radius: 10rpx;
				}
			}

			.content {
				.title {
					font-size: 28rpx;
					line-height: 50rpx;
				}

				.type {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}

				.delivery-time {
					color: #e5d001;
					font-size: 24rpx;
				}
			}

			.right {
				margin-left: 10rpx;
				padding-top: 20rpx;
				text-align: right;

				.decimal {
					font-size: 24rpx;
					margin-top: 4rpx;
				}

				.number {
					color: $u-tips-color;
					font-size: 24rpx;
				}
			}
		}

		.total {
			margin-top: 20rpx;
			text-align: right;
			font-size: 24rpx;

			.total-price {
				font-size: 32rpx;
			}
		}

		.bottom {
			display: flex;
			margin-top: 40rpx;
			padding: 0 10rpx;
			justify-content: flex-end;
			align-items: center;

			.btn {
				line-height: 52rpx;
				width: 160rpx;
				border-radius: 26rpx;
				border: 2rpx solid $u-border-color;
				font-size: 26rpx;
				text-align: center;
				color: $u-type-info-dark;
				margin-left: 30rpx;
			}

			.evaluate {
				color: $u-type-warning-dark;
				border-color: $u-type-warning-dark;
			}
		}
	}

</style>
