<template>
	<view class="content">
		<view class="cu-form-group">
			<view class="title">打分:</view>
			<picker @change="PickerChange" :value="starIndex" :range="stars">
				<view class="picker">
					{{stars[starIndex]}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group no-border">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入点评内容..." style="height: 400upx;"></textarea>
		</view>

		<view class="padding flex flex-direction" style="position: fixed;bottom: 0 ; width: 100%;">
			<button class="cu-btn bg-blue margin-tb-sm lg shadow" @tap="handleSubmit">提交点评</button>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	export default {
		data() {
			return {
				stars: ['1分', '2分', '3分', '4分', '5分'],
				content: '',
				starIndex: 0,
				zoneId: 0,
				clazzId: 0,
			}
		},
		onLoad(q) {
			this.zoneId = q.id;
			this.clazzId = q.clazz_id;
		},
		methods: {
			PickerChange(e) {
				this.starIndex = e.detail.value
			},
			handleSubmit() {
				if (this.content == '') {
					common.showError("请输入内容")
					return
				}
				common.modelShow('请确认', '确认发布点评', () => {
					api.post('staff/zoneCheck', {
						id: this.zoneId,
						score: this.starIndex + 1,
						content: this.content,
					}, res => {
						if (res.result == 'success') {
							common.showSuccess(res.msg, () => {
								uni.navigateTo({
									url: '/pages/zone/list?id=' + this.clazzId,
									animationDuration: 200
								})
							})
						} else {
							common.showError(res.msg)
						}
					})
				})
			},
			textareaInput(e) {
				this.content = e.detail.value
			},
		}
	}
</script>

<style>
</style>
