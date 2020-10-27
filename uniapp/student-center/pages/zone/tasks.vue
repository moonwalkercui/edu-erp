<template>
	<view class="content">
		<!-- <scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item flex-sub" :class="index==TabCur?'text-orange cur':''" v-for="(item,index) in tabs" :key="index" @tap="tabSelect" :data-id="index">
					{{tabs[index]}}
				</view>
			</view>
		</scroll-view> -->
	<!-- 	<view class="cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-title text-orange "></text>新任务
			</view>
		</view> -->
		
		<view class="cu-list menu sm-border" style="padding-bottom: 200upx;">
			<view class="cu-item " :class="{arrow: item.is_finish==0}" v-for="(item, index) in list" :key="index">
				<view class="content padding-sm">
					<view class="text-black text-df">{{index+1}}、{{item.content}}</view>
				</view>
				<view class="action text-right">
					<view class="text-orange" v-if="item.is_finish==0" @tap="toFinish(item.id)">去完成</view>
					<view class="text-gray" v-else>已完成</view>
				</view>
			</view>
				<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view>
		
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import paginateMix from "@/common/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				list: []
			}
		},
		onLoad() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				api.get('student/taskList', {page: this.pageData.page}, res => {
					if (res.result == 'success') {
						this.list = this.list.concat(res.data.data)
						this.pageData.page = parseInt(res.data.current_page);
						this.pageData.lastPage = parseInt(res.data.last_page);
					} else {
						common.showError(res.msg)
					}
				})
			},
			handleSearch() {
				this.pageData.page = 1;
				this.list = [];
				this.handleReq();
			},
			toFinish(id) {
				uni.navigateTo({
					url: '/pages/zone/publish?id=' + id ,
					animationDuration: 200
				})
			},
		}
	}
</script>


<style>
</style>
