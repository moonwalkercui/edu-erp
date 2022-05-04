<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="领取试听卡" @clickLeft="clickLeft"
			@clickRight="clickRight" />
		<view class="u-m-t-20">
			<u-divider bg-color="#f4f4f4" :font-size="12">领取试听卡后可以到课表中预约课程</u-divider>
		</view>
		<view class="u-margin-30 bg-white boder-radius-md">
			<view class="meituan" v-for="(item,index) in list" :key="index">
				<view class="content">
					<view class="left">
						<view class="sum">
							<text class="num">1</text>次
						</view>
						<view class="type">试听卡</view>
					</view>
					<view class="center">
						<view class="title">名师钢琴课</view>
						<view class="valid-date">头像</view>
						<view class="valid-date">余量: 15</view>
					</view>
					<view class="right">
						<view size="mini" class="immediate-use" :round="true">立即领取</view>
					</view>
				</view>
				<view class="tips">
					<view class="circle-left"></view>
					<view class="circle-right"></view>
					<view>有效期: 2022-05-30</view>
					<view class="rule" @tap="xxx122">
						<text>查看规则</text>
						<u-icon name="arrow-right" color="" :size="20" @click="showRule"></u-icon>
					</view>
				</view>
			</view>
		</view>
	
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [1,2,3,4],
			}
		},
		onLoad() {
			// this.handleReq()
		},
		methods: {
			showRule() {
				
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	};
</script>

<style lang="scss" scoped>
	page {
		height: 100%;
		background-color: rgb(244, 244, 244);
	}

	.u-wrap {
		padding: 24rpx;
	}

	.meituan {
		background-color: #ffffff;
		width: 700rpx;
		// border: 10rpx;
		color: $u-type-warning;
		font-size: 28rpx;
		.content {
			display: flex;
			align-items: center;
			padding: 30rpx;
			border-top: 4rpx dashed #ffffff;
			background-color: #ff9900;
			color: #ffffff;
			.left {
				flex: 1;
				text-align: center;
				.sum {
					font-size: 28rpx;
					font-weight: bold;
					.num {
						font-size: 60rpx;
						font-weight: bold;
					}
				}
			}

			.center {
				flex: 2;
				margin-left: 40rpx;
				color: #ffffff;
				.title {
					font-size: 32rpx;
					font-weight: bold;
					color: $u-main-color;
					margin-bottom: 20rpx;
				}
				.valid-date{
					font-size: 26rpx;
				}
			}

			.right {
				margin-left: 30rpx;
				.immediate-use {
					padding: 0 20rpx;
					height: 50rpx;
					border-radius: 25rpx;
					line-height: 50rpx;
					background-color: #ffffff;
					color: black;
					font-size: 24rpx;
					border: none;
					word-break: keep-all;
				}
			}
		}

		.tips {
			padding: 0 20rpx;
			border: 10rpx;
			background-color: #ffffff;
			position: relative;
			color: $u-tips-color;
			display: flex;
			justify-content: space-between;
			line-height: 60rpx;
			font-size: 22rpx;

			.circle-left,
			.circle-right {
				position: absolute;
				height: 36rpx;
				width: 18rpx;
				background-color: #f2f2f2;
			}

			.circle-right {
				border-radius: 40rpx 0 0 40rpx;
				right: 0;
				top: -18rpx;
			}

			.circle-left {
				border-radius: 0 40rpx 40rpx 0;
				left: 0;
				top: -18rpx;
			}

			.rule {
				font-size: 24rpx;
				display: flex;
				align-items: center;

				text {
					margin-right: 10rpx;
					flex: 1;
				}
			}
		}
	}

</style>
