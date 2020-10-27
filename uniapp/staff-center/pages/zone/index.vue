<template>
	<view class="content">
		<view class="cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-title text-blue "></text> 选择班级
			</view>
			<view class="action">
			</view>
		</view>
		<view class="cu-list menu sm-border">
			<view class="cu-item arrow" v-for="(item, index) in list" :key="index" @tap = "showDetails(item.id)">
				<view class="content">
					<text class="cuIcon-friend text-grey"></text>
					<text class="text-grey">{{item.name}}</text>
				</view>
				<view class="action" v-if="item.count > 0">
					<view class="cu-tag round bg-red sm">{{item.count}}</view>
				</view>
			</view>
			<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view>

	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	// import bttomNav from "@/components/bttomNav.vue"
	// import wechat from '@/common/wechat.js'
	export default {
		components: {
			// bttomNav
		},
		data() {
			return {
				mobile: "",
				CustomBar: 10,
				list: []
			}
		},
		onShow() {
			this.handleReq()
			// wechat.share({
			// 	desc: "精选优质、有价值的好文章，转发给身边的人",  
			// });
		},
		methods: {
			handleReq() {
				api.get('staff/clazzList', {}, res => {
					if (res.result == 'success') {
						this.list = res.data
					} else {
						common.showError(res.msg)
					}
				})
			},
			showDetails(id) {
				uni.navigateTo({
					url: '/pages/zone/list?id=' + id,
					animationDuration: 200
				})
			},
		}
	}
</script>


<style>
</style>
