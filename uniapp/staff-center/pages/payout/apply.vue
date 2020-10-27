<template>
	<view class="content">
		<view class="cu-form-group">
			<view class="title">请款类型:</view>
			<picker @change="PickerChange" :value="typeIndex" :range="types">
				<view class="picker">
					{{types[typeIndex]}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<view class="title">请款金额:</view>
			<input placeholder="请输入" name="input" v-model="money" type="number"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">收款姓名:</view>
			<input placeholder="请输入" name="input" v-model="payee" type="text"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">收款账号:</view>
			<input placeholder="请输入" name="input" v-model="payee_account" type="text"></input>
		</view>
	
		<view class="cu-form-group">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入请款原因..."></textarea>
		</view>
		<view class="cu-form-group">
			<view class="title">附图:</view>
		</view>
		<imgUpload :max="3" />
		<view class="padding flex flex-direction" style="position: fixed;bottom: 0 ; width: 100%;">
			<button class="cu-btn bg-white margin-tb-sm lg shadow" @tap="showHistory">查看请款记录</button>
			<button class="cu-btn bg-blue margin-tb-sm lg shadow" @tap="handleSubmit">提交申请</button>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import {
		formatTime
	} from "@/common/util.js"
	import imgUpload from "@/components/imgUpload.vue"

	export default {
		components: {
			imgUpload
		},
		data() {
			return {
				money: 0,
				payee: '',
				payee_account: '',
				types: [],
				typeIndex: 0,
				reason: '',
				imgList: []
			}
		},
		onLoad() {
			this.handleReq();
			uni.$on('choosedImg', res => {
				this.imgList.push(res)
			})
		},
	
		methods: {
			textareaInput(e) {
				this.reason = e.detail.value
			},
			PickerChange(e) {
				this.typeIndex = e.detail.value
			},
			handleReq() {
				api.get('staff/payoutTypes', {}, res => {
					if (res.result == 'success') {
						this.types = res.data;
					} else {
						common.showError(res.msg)
					}
				})
			},
			showHistory() {
				uni.navigateTo({
					url: '/pages/payout/index',
					animationDuration: 200
				})
			},
			handleSubmit() {
				if (this.money == '') {
					common.showError("请输入金额")
					return
				}
				if (this.payee == '') {
					common.showError("请输入收款人")
					return
				}
				if (this.reason == '') {
					common.showError("请输入内容")
					return
				}
			
				common.modelShow('请确认', '确认提交请款申请', () => {
					api.post('staff/payoutApply', {
						money: this.money,
						payee: this.payee,
						payee_account: this.payee_account,
						images: this.imgList,
						reason: this.reason,
						type: this.types[this.typeIndex],
					}, res => {
						if (res.result == 'success') {
							common.showSuccess(res.msg, () => {
								this.showHistory()
							})
						} else {
							common.showError(res.msg)
						}
					})
				})
			},

			
		}
	}
</script>


<style>
</style>
