<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" title="订单详细信息" @clickLeft="clickLeft" />
		<view class="u-flex u-m-20 u-p-20 text-orange">
			<view class="u-flex-1 u-font-32 text-bold">{{info.state}}</view>
			<view class="u-flex-1 u-text-right">￥{{info.payMoney}}</view>
		</view>
		
		<view class="u-m-20 u-border-bottom bg-white u-p-20 boder-radius-md">
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">订单编号：<text class="u-font-12">{{info.sn}}</text></view>
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">订单时间：<text >{{info.addTime}}</text></view>
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">支付时间：<text >{{info.payTime}}</text></view>
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">订单金额：<text >{{info.orderMoney}}</text></view>
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">支付金额：<text >{{info.payMoney}}</text></view>
			<view class="u-p-t-20 u-p-b-20">备注：<text >{{info.remark}}</text></view>
		</view>
		
		<view class="order">
			<view class="item" v-for="(item, index) in info.itemList" :key="index" @click="showInfo()">
				<view class="left" v-if="item.cover">
					<image :src="item.cover" mode="aspectFill"></image>
				</view>
				<view class="content">
					<view class="title u-line-2">{{ item.itemName }}</view>
					<view class="type">{{ item.itemType }}</view>
					<!-- <view class="delivery-time">添加时间:{{ item.addTime }}</view> -->
				</view>
				<view class="right">
					<view class="price">
						￥{{ item.price }}
					</view>
				</view>
			</view>
		</view>
		<view class="u-m-20 u-border-bottom bg-white u-p-20 boder-radius-md">
			<view class="u-p-t-20 u-p-b-20 u-border-bottom">有退款：<text >{{info.refunded?'是':'否'}}</text></view>
			
			<view class="u-p-t-20 u-p-b-20 text-gray" v-for="(item, index) in info.refundList">
				<view>申请时间：{{item.addTime}}</view>
				<view>退款单号：<text class="u-font-12">{{item.refundSn}}</text></view>
				<view>退款金额：￥{{item.refundMoney}}</view>
				<view>退款类型：{{item.reasonTypeText}}</view>
				<view>退款原因：{{item.reason}}</view>
				<view>审核状态：{{item.state}}</view>
				<view>审核信息：{{item.verifyRemark}}</view>
			</view>
			
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				info: {}
			}
		},
		onLoad(option) {
			this.orderId = option.orderId
			this.handleReq()
		},
		methods: {
			handleReq() {
				this.$http.get(`sCenter/shop/orderInfo/${this.orderId}`, {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			clickLeft() {
				uni.navigateBack()
			}
		},
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

		.item {
			display: flex;
			margin: 20rpx 0 0;

			.left {
				margin-right: 20rpx;
				width: 200rpx ;
				image {
					width: 200rpx;
					height: 200rpx;
					border-radius: 10rpx;
				}
			}

			.content {
				flex: 1;
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
				.price {
					color: $u-tips-color;
					font-size: 24rpx;
				}
			}
		}

		.total {
			font-size: 24rpx;
			.total-price {
				font-size: 28rpx;
			}
		}

	}

</style>
