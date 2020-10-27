<template>
	<view>
		<view class="cu-tabbar-height"></view>
		<view class="cu-bar tabbar bg-white shadow foot">
			<view class="action" @tap="toPage('verify/index')">
				<view class='cuIcon-cu-image'>
					<image :src="curPage=='verify' ? '/static/img/scan1.png' :  '/static/img/scan.png'"></image>
				</view>
				<view :class="curPage=='verify'?'text-blue':'text-gray'">核销</view>
			</view>
	
			<view class="action" @tap="toPage('activate/index')">
				<view class='cuIcon-cu-image'>
					<image :src="curPage=='activate' ? '/static/img/vip1.png' :  '/static/img/vip.png'"></image>
				</view>
				<view :class="curPage=='activate'?'text-blue':'text-gray'">年卡</view>
			</view>
			
			<view class="action" @tap="toPage('give/index')">
				<view class='cuIcon-cu-image'>
					<image :src="curPage=='give' ? '/static/img/consume1.png' :  '/static/img/consume.png'"></image>
				</view>
				<view :class="curPage=='give'?'text-blue':'text-gray'">次卡</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:{
			curPage: {
				type: String,
				default: "vip"
			}
		},
		data() {
			return {
				name: 'bottomNav'
			}
		},
		methods:{
			toPage(url) {
				uni.navigateTo({
					url: '/pages/' + url,
					animationDuration: 200
				})
			}
		}
	}
</script>

<style>
</style>
