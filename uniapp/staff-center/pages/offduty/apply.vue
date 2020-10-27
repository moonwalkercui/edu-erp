<template>
	<view class="content">
		<view class="cu-form-group">
			<view class="title">请假类型:</view>
			<picker @change="PickerChange" :value="typeIndex" :range="types">
				<view class="picker">
					{{types[typeIndex]}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<view class="title">开始日期:</view>
			<picker mode="date" :value="startDate" start="2020-08-01" end="2028-09-01" @change="startDateChange">
				<view class="picker">
					{{startDate}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<view class="title">结束日期:</view>
			<picker mode="date" :value="endDate" start="2020-08-01" end="2028-09-01" @change="endDateChange">
				<view class="picker">
					{{endDate}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入请假原因..."></textarea>
		</view>
		<view class="cu-form-group">
			<view class="title">附图:</view>
		</view>
		<imgUpload :max="3" />
		<!-- 	<view class="cu-bar bg-white solid-bottom margin-top">
			<view class="action">
				<text class="cuIcon-title text-orange "></text> 请假记录
			</view>
		</view>
		<view class="cu-list menu sm-border" style="padding-bottom: 200upx;">
			<view class="cu-item " v-for="(item, index) in list" :key="index">
				<view class="content padding-sm">
					<view class="text-black text-df">{{item.date}}</view>
				</view>
				<view class="action text-right">
					<view class="text-orange" @tap="handleCancel(item.date)">取消</view>
				</view>
			</view>
			<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view> -->

		<view class="padding flex flex-direction" style="position: fixed;bottom: 0 ; width: 100%;">
			<button class="cu-btn bg-white margin-tb-sm lg shadow" @tap="showHistory">查看请假记录</button>
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
				// list: [],
				types: [],
				typeIndex: 0,
				startDate: '',
				endDate: '',
				reason: '',
				imgList: []
			}
		},
		onLoad() {
			var today = new Date();
			this.startDate = this.endDate = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate();
			
			this.handleReq();
			uni.$on('choosedImg', res => {
				this.imgList.push(res)
			})
		},
		// onShow() {
		// 	this.handleReq();
		// },
		methods: {
			textareaInput(e) {
				this.reason = e.detail.value
			},
			PickerChange(e) {
				this.typeIndex = e.detail.value
			},
			startDateChange(e) {
				this.startDate = e.detail.value
			},
			endDateChange(e) {
				this.endDate = e.detail.value
			},
			handleReq() {
				api.get('staff/offdutyTypes', {}, res => {
					if (res.result == 'success') {
						this.types = res.data;
					} else {
						common.showError(res.msg)
					}
				})
			},
			// handleReq() {
			// 	api.get('staff/offdutyList', {}, res => {
			// 		if (res.result == 'success') {
			// 			this.list = res.data;
			// 		} else {
			// 			common.showError(res.msg)
			// 		}
			// 	})
			// },
			showHistory() {
				uni.navigateTo({
					url: '/pages/offduty/index',
					animationDuration: 200
				})
			},
			handleSubmit() {
				if (this.startDate == '') {
					common.showError("请输入开始日期")
					return
				}
				if (this.endDate == '') {
					common.showError("请输入结束日期")
					return
				}
				if (this.reason == '') {
					common.showError("请输入事由")
					return
				}
				common.modelShow('请确认', '确认请假时间', () => {
					api.post('staff/offdutyApply', {
						startDate: this.startDate,
						endDate: this.endDate,
						reason: this.reason,
						images: this.imgList,
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

			// handleCancel(date) {
			// 	common.modelShow('确认取消请假', '点击确认后取消请假', () => {
			// 		api.post('offdutyCancel', {
			// 			date: date,
			// 		}, res => {
			// 			if (res.result == 'success') {
			// 				common.showSuccess(res.msg, () => {
			// 					this.handleReq()
			// 				})
			// 			} else {
			// 				common.showError(res.msg)
			// 			}
			// 		})
			// 	})
			// },
		}
	}
</script>


<style>
</style>
