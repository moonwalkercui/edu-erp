<template>
	<view class="content">
		<view class="cu-form-group">
			<view class="title">姓名</view>
			<input placeholder="请输入" name="input" v-model="mobile" type="number"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">我的介绍</view>
			<input placeholder="请输入" name="input" v-model="amount" type="number"></input>
		</view>
	
		<view class="padding flex flex-direction" style="position: fixed;bottom: 0 ; width: 100%;">
			<button class="cu-btn bg-blue margin-tb-sm lg shadow" @tap="handleGive">保 存</button>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	export default {
		components: {
		},
		data() {
			return {
				mobile: '',
				money: '',
				amount: '',
				radio: '',
				list: [],
				selected: []
			}
		},
		onShow() {
			this.handleReq()
		},
		methods: {
			
			handleReq() {
			},
		}
	}
</script>


<style>
</style>
