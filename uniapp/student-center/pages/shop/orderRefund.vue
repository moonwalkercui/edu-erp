<template>
	<view class="bg-white" style="">
		<view class="u-p-20 text-gray u-border-bottom">
			订单编号：{{formData.orderSn}}
		</view>
		<view class="u-p-20 u-border-bottom">
			<view>退款金额：<text class="text-black">{{formData.refundMoney}}元</text></view>
		</view>
		<view class="u-p-20 u-border-bottom">
			<view class="u-flex">
				<view class="u-flex-1">
					<text class="u-m-r-20">退款类型: </text> {{formData.reasonTypeText}}
				</view>
				<view style="width: 200rpx;text-align: right;">
					<text @click="show = true" class="text-gray">选择类型 <u-icon name="arrow-down"></u-icon></text>
				</view>
			</view>
		</view>
		<u-select v-model="show" :list="reasonList" @confirm="confirm"></u-select>
		<view>
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					退款原因:
				</view>
			</view>
			<view class="u-p-20 u-p-t-0">
				<u-input v-model="formData.reason" type="textarea" :border="true" :height="240" :auto-height="true"
					placeholder="请输入" />
			</view>

			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					提交退款申请
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				show: false,
				reasonList: [],
				formData: {
					orderId: '',
					orderSn: '',
					refundMoney: 0,
					reasonType: '',
					reasonTypeText: '',
					reason: '',
				}
			}
		},
		onLoad(option) {
			this.formData.orderId = option.orderId
			this.formData.orderSn = option.orderSn
			this.formData.refundMoney = option.payMoney
			this.loadDict()
		},
		methods: {
			confirm(e) {
				if(e.length == 0) {
					this.$common.showMsg('请选择');
					return;
				}
				this.formData.reasonType = e[0].value
				this.formData.reasonTypeText = e[0].label 
			},
			loadDict() {
				this.$http.get('sCenter/dictList', {
					code: 'refund_type'
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					let list = [];
					for(const item of res) {
						list.push({
							value: item.id,
							label: item.name
						})
					}
					this.reasonList = list
				})
			},
			toSubmit() {
				if(this.formData.reasonType == '') {
					this.$common.showMsg('请选择退款类型');
					return;
				}
				if(this.formData.reason == '') {
					this.$common.showMsg('请输入原因');
					return;
				}
				this.$common.showAlert("确认提示", `确认发起退款？`, () => {
					this.$http.post('sCenter/shop/orderRefund', this.formData, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						this.$common.showMsg(res.msg, () => {
							uni.navigateBack()
						})
					})
				})
			},
		}
	}
</script>
