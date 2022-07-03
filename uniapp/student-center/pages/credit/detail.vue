<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" title="礼品兑换" @clickLeft="clickLeft" />
		<view class="u-margin-30 bg-white boder-radius-md" style="border: 8rpx solid #9ee8d0">
			<view class="u-padding-20 u-font-md u-flex">
				<view class="u-flex-3">要兑换的礼品</view>
			</view>
			<view class=" u-p-20 bg-white boder-radius-md u-flex">
				<view style="width: 35%;" class="u-p-r-20" v-if="info.cover">
					<u-lazy-load height="200" :image="info.cover"></u-lazy-load>
				</view>
				<view class="u-flex-1 u-font-13">
					<view>
						<text class="u-font-md text-bold">{{info.name}}</text>
					</view>
					<view class="u-m-t-10 text-gray">剩余 {{info.storage > 100 ? '99+' : info.storage}}</view>
					<view class="u-flex u-m-t-20">
						<view class="u-flex-1 u-text-left" style="color: #ff6600;">
							<text class="u-font-16 text-bold">{{info.credit}} 积分</text>
						</view>
						<view class="u-flex-1 u-text-right" style="color: #ff6600;">
							<u-number-box v-model="order.num" :min="1" :max="info.storage" @change="valChange"></u-number-box>
						</view>
					</view>
				</view>
			</view>
			<view class="u-p-20 bg-white boder-radius-md u-flex" v-if="info.content && info.content != ''">
				<text class="u-font-13">{{info.content}}</text>
			</view>
		</view>
		
		<view class="u-margin-30 bg-white boder-radius-md" style="overflow: hidden;z-index: 100;">
			<u-cell-group title="">
				<u-cell-item title="领取人与积分" :arrow="false">
					<text class="text-black u-m-r-20"> {{studentInfo.name}} </text> 剩余<text class="text-black">{{mycredit}}</text>
				</u-cell-item>
				<u-cell-item title="取货门店" :arrow="false">
					<text class="text-black">{{info.schoolName}}</text>
				</u-cell-item>
				<u-cell-item title="取货方式" :arrow="false">
					<text class="text-black">门店自取</text>
				</u-cell-item>
			</u-cell-group>
		</view>
		
		<view class="u-margin-30 bg-white boder-radius-md" style="overflow: hidden;z-index: 100;">
			<view class="u-padding-30 u-font-sm">
				<view class="u-text-center u-m-b-20">- 兑换说明 -</view>
				<text>{{help}}</text>
			</view>
		</view>

		<view class="bottom-nav">
			<view class="left">
				<view class="item u-text-left">
					<view class="text-gray u-font-14 text-orange">
						<text class="u-font-18 text-black u-m-r-30">{{total}} 积分</text>
					</view>
				</view>
			</view>
			<view class="right">
				<u-button class="buy btn" @click="handelConfirm()" :disabled="disabled" type="warning">立即兑换</u-button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				disabled: false,
				mycredit: 0,
				help: '',
				studentInfo: {},
				info: {},
				order: {
					id: '',
					num: 1,
				},
			}
		},
		computed: {
			total: function() {
				return this.info.credit * this.order.num
			}
		},
		onLoad() {
			this.info = uni.getStorageSync("credit-detail")
			this.order.id = this.info.id
			this.getStudentInfo()
			this.systemSetting()
			this.addViewNum(this.order.id)
		},
		methods: {
			addViewNum(id) {
				this.$http.post(`sCenter/student/addViewNum/${id}`)
			},
			getStudentInfo() {
				this.$http.get('sCenter/student/currentStudent',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.studentInfo = res
					this.mycredit = res.credit
					this.valChange({value: 1})
				})
			},
			systemSetting() {
				this.$common.systemSettings(['credit_mall_help']).then(res => {
					this.help = res.credit_mall_help
				})
			},
			valChange(e) {
				if(e.value * this.info.credit > this.mycredit) {
					this.disabled = true
					this.$common.showMsg("积分不足")
				} else {
					this.disabled = false
				}
			},
			handelConfirm() {
				if(this.order.num <= 0) {
					this.$common.showMsg("数量有误")
					return;
				}
				this.$common.showAlert("确认提示", `确认花费积分兑换该礼品？`, () => {
					this.$http.post('sCenter/credit/exchange', this.order, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						if (res.errCode == 0) {
							this.$common.showMsg(res.msg, () => {
								uni.switchTab({
									url: "/pages/index/index"
								})
							})
						}
					})
				})
			},
			clickLeft() {
				uni.redirectTo({
					url: "/pages/credit/index"
				})
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
