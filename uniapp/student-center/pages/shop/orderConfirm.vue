<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" title="订单确认" @clickLeft="clickLeft" />

		<view class="u-margin-30 bg-white boder-radius-md" style="border: 8rpx solid #9ee8d0">
			<view class="u-padding-20 u-font-md u-flex">
				<view class="u-flex-3">要购买的课程</view>
			</view>
			<view class=" u-p-20 bg-white boder-radius-md u-flex">
				<view style="width: 35%;" class="u-p-r-20" v-if="courseInfo.cover">
					<u-lazy-load height="200" :image="courseInfo.cover"></u-lazy-load>
				</view>
				<view class="u-flex-1 u-font-13">
					<view>
						<u-tag :text="courseInfo.lessonType" type="warning" size="mini"
							v-if="courseInfo.lessonType=='大班课'" />
						<u-tag :text="courseInfo.lessonType" type="error" size="mini"
							v-if="courseInfo.lessonType=='小班课'" />
						<u-tag :text="courseInfo.lessonType" type="success" size="mini"
							v-if="courseInfo.lessonType=='一对一'" />
						<text class="u-font-md text-bold u-m-l-10">{{courseInfo.name}}</text>
					</view>
					<view class="u-m-t-10 text-gray">{{courseInfo.lessonCount}}课时 · {{courseInfo.expireMonths}}个月有效期
					</view>
					<view class="u-m-t-10 text-gray">{{courseInfo.closeDate ? '报名截至日 ' + courseInfo.closeDate : ''}}
					</view>
					<view class="u-flex u-m-t-10">
						<view class="u-flex-2">
							{{courseInfo.teacherInfo}}
						</view>
						<view class="u-flex-1 u-text-right" style="color: #ff6600;">
							￥<text class="u-font-16 text-bold">{{courseInfo.price}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="u-margin-30 bg-white boder-radius-md" style="overflow: hidden;">
			<view class="u-padding-20 u-font-md u-flex">
				<view class="u-flex-3">备注</view>
			</view>
			<u-form>
				<view class="u-p-l-20 u-p-b-20 u-p-r-20">
					<u-form-item prop="content">
						<u-input type="textarea" v-model="orderInfo.remark" autoHeight></u-input>
					</u-form-item>
				</view>
			</u-form>
		</view>
		
		<view class="u-margin-30 bg-white boder-radius-md" style="overflow: hidden;">
			<u-cell-group title="">
				<u-cell-item title="学生姓名" :arrow="false">
					<text class="text-black"> {{studentInfo.name}}
						<u-icon class="u-m-l-10" name="woman" color="#ff557f" size="28" v-if="studentInfo.gender == '女'"></u-icon>
						<u-icon class="u-m-l-10" name="man" color="#2979ff" size="28" v-if="studentInfo.gender == '男'"></u-icon>
					</text>
				</u-cell-item>
				<u-cell-item title="支付方式" :arrow="false">
					<text class="text-black">微信支付</text>
				</u-cell-item>
			</u-cell-group>
		</view>

		<view class="bottom-nav">
			<view class="left">
				<view class="item u-text-left">
					<view class="text-gray u-font-14 text-orange">￥<text
							class="u-font-18 text-black u-m-r-30">{{orderInfo.price}}</text></view>
				</view>
			</view>
			<view class="right">
				<u-button class="buy btn" @click="payOrder" :disabled="disabled" type="warning">立即支付</u-button>
			</view>
		</view>
	</view>
</template>

<script>
	import wechat from '@/util/wechat.js'
	export default {
		data() {
			return {
				disabled: false,
				courseInfo: {},
				orderInfo: {
					price: 0,
					courseId: '',
					remark: ''
				},
				orderCreated: {},
				studentInfo: {}
			}
		},
		onLoad() {
			this.courseInfo = uni.getStorageSync("order-confirm-course")
			this.studentInfo = uni.getStorageSync("current-student-info")
			this.orderInfo.courseId = this.courseInfo.id
			this.orderInfo.price = this.courseInfo.price
			this.orderInfo.studentId = this.studentInfo.id
		},
		methods: {
			payOrder() {
				this.disabled = true
				this.disabled = false
				if(this.orderCreated.sn) {
					// 如果已经生成订单，则直接支付即可
					this.wxpay()
				} else {
					this.$http.post('sCenter/shop/orderConfirm', this.orderInfo, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						console.log('订单信息', res)
						this.orderCreated = res
						this.wxpay()
					})
				}
			},
			wxpay() {
				this.$http.post('sCenter/shop/createOrder', this.orderCreated, res1 => {
					if (res1 && res1.errCode > 0) {
						uni.showToast({
							title: res1.msg,
							icon: 'error',
							duration: 10000
						});
						return
					}
					console.log('wxpayparam', res1)
					wechat.wxpay({
						"appId": res1.appId,
						"nonceStr": res1.nonceStr,
						"package": res1.package,
						"paySign": res1.paySign,
						"signType": res1.signType,
						"timeStamp": res1.timeStamp,
					}, (res2) => {
						console.log('支付结果', res1)
						uni.redirectTo({
							url: "/pages/shop/payResult"
						})
					})
				})
			},
			clickLeft() {
				uni.navigateBack()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.bottom-nav {
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
			font-size: 28rpx;

			.item {
				flex: 1;
				margin: 0 30rpx;
				line-height: 80rpx;
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
