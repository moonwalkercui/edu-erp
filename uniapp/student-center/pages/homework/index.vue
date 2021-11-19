<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="作业列表" @clickLeft="clickLeft"
			@clickRight="clickRight" />
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" index="index"
					@click="showDetail(item.id)" :title-style="{'width': '75%'}" :arrow="true">
					<view slot="title">{{item.title}}</view>
					<view slot="label">{{item.className}}</view>
					<view :class="[!item.completed ? 'text-red':'']">{{item.completed?'已交':'未交'}}</view>
				</u-cell-item>
			</u-cell-group>
			<view class="u-padding-30 bg-white" v-if="list.length == 0">
				<u-empty mode="list" text="暂无记录"></u-empty>
			</view>
		</view>
	</view>
</template>
<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				checked: false,
				list: [],
			}
		},
		onLoad() {
			this.handleReq()

		},
		onShow() {
			this.$common.getRedpoint()
		},
		methods: {
			handleReq() {
				this.$http.get('sCenter/homework/list', {
					page: this.pageData.page
				}, res => {
					if(!res) return;
					if (!this.$common.handleResponseMsg(res)) return;
				
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			
			showDetail(id) {
				uni.navigateTo({
					url: `/pages/homework/detail?id=${id}`
				});
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.switchTab({
					url: '/pages/index/index'
				});
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
