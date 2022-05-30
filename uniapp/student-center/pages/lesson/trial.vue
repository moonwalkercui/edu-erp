<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="领取体验卡" @clickLeft="clickLeft"
			@clickRight="clickRight" />

		<u-subsection :list="nav" :current="currentNav" @change="changeNav"></u-subsection>
		<view v-if="currentNav === 0">
			<view class="u-m-t-20">
				<u-divider bg-color="#f4f4f4" :font-size="12">领取体验卡后可以到课表中预约课程</u-divider>
			</view>
			<view class="u-margin-30 bg-white boder-radius-md">
				<view class="meituan" v-for="(item,index) in list" :key="index">
					<view class="content" :class="{notenough: item.quantity-item.recordCount <= 0}">
						<view class="left">
							<view class="sum">
								<text class="num">{{item.lessonCount}}</text>次
							</view>
							<view class="type">
								<u-tag text="体验卡" type="warning" shape="circle" />
							</view>
						</view>
						<view class="center">
							<view class="title">{{item.title}}</view>
							<view class="valid-date">课程: {{item.courseName}} · 剩余: {{item.quantity-item.recordCount}} </view>
							<view class="valid-date">截至: {{item.endDate}}</view>
						</view>
						<view class="right">
							<view size="mini" class="immediate-use none" :round="true" v-if="item.quantity-item.recordCount <= 0">已领完</view>
							<view size="mini" class="immediate-use" :round="true" v-else @click="getOne(item.id)">立即领</view>
						</view>
					</view>
					<view class="tips">
						<view class="circle-left"></view>
						<view class="circle-right"></view>
						<view>名额有限、领完为止</view>
						<view class="rule" @click="showRule()">
							<text>查看规则</text>
							<u-icon name="arrow-right" color="" :size="20"></u-icon>
						</view>
					</view>
				</view>
				<view class="u-p-40" v-if="list.length === 0">
					<u-empty text="暂无体验卡" mode="list"></u-empty>
				</view>
			</view>
		</view>

		<view v-if="currentNav === 1">
			<view class="u-margin-30 bg-white boder-radius-md">
				<view class="meituan" v-for="(item,index) in myList" :key="index">
					<view class="content">
						<view class="left">
							<view class="sum">
								<text class="num">{{item.lessonCount}}</text>次
							</view>
							<view class="type">
								<u-tag text="体验卡" type="warning" shape="circle" />
							</view>
						</view>
						<view class="center">
							<view class="title">{{item.trialTitle}}</view>
							<view class="valid-date">课程: {{item.courseName}}</view>
							<view class="valid-date">有效期: {{item.expiredDate}}</view>
							<view class="valid-date">领取时间: {{item.addTime}}</view>
						</view>
						<view class="right">
						</view>
					</view>
					<view class="tips">
						<view class="circle-left"></view>
						<view class="circle-right"></view>
						<view>领取体验卡去课时表里预约自己喜欢的课程吧~</view>
						<view class="rule" @click="showRule()">
							<text>查看规则</text>
							<u-icon name="arrow-right" color="" :size="20"></u-icon>
						</view>
					</view>
				</view>
				<view class="u-p-40" v-if="myList.length === 0">
					<u-empty text="未领取体验卡" mode="list"></u-empty>
				</view>
			</view>
		</view>
		
		<u-popup v-model="showBox" mode="bottom"  border-radius="14" height="650rpx">
			<view class="u-text-center" style="min-height: 500rpx; overflow-y: scroll;">
				<view class="u-font-16 u-p-30 text-bold">- 体验卡使用规则 -</view>
				<view class="u-p-30 ">
					<view class="u-font-14 u-text-left">
						{{courseTrialHelp}}
					</view>
				</view>
			</view>
			<view class="u-text-center">
				<u-button @click="showBox = false;" size="medium">知道了</u-button>
			</view>
		</u-popup>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				nav: [{
						name: '未领取'
					},
					{
						name: '已领取'
					}
				],
				currentNav: 0,
				list: [],
				list1: [],
				myList: [],
				courseTrialHelp: "",
				showBox: false,
			}
		},
		onLoad() {
			this.trialList()
			this.systemSetting()
		},
		methods: {
			trialList() {
				this.$http.get('sCenter/course/trialList',{}, res => {
					this.list = res
				})
			},
			myTrialList() {
				this.$http.get('sCenter/course/myTrialList',{}, res => {
					this.myList = res
				})
			},
			getOne(id) {
				this.$http.post(`sCenter/course/catchTrial/${id}`,{}, res => {
					this.$common.showMsg(res.msg, () => {
						this.changeNav(1)
					})
				})
			},
			systemSetting() {
				this.$common.systemSettings(['course_trial_help']).then(res => {
					this.courseTrialHelp = res.course_trial_help
				})
			},
			showRule() {
				this.showBox = true;
			},
			changeNav(index) {
				this.currentNav = index;
				if(index == 0) {
					this.trialList()
				}else{
					this.myTrialList()
				}
			},
			clickRight() {
				this.changeNav(this.currentNav)
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
		width: 100%;
		color: $u-type-warning;
		font-size: 28rpx;

		.notenough {
			background-color: #bbbbbb!important;
		}
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
					margin-bottom: 16rpx;

					.num {
						font-size: 48rpx;
						font-weight: bold;
					}
				}
			}

			.center {
				flex: 2;
				margin-left: 40rpx;
				color: #ffffff;

				.title {
					font-size: 28rpx;
					font-weight: bold;
					color: $u-main-color;
					margin-bottom: 20rpx;
				}
				.valid-date {
					font-size: 24rpx;
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

					&.none {
						background-color: #999999;
						color: #ffffff;
					}
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
