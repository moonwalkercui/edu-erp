<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="帮助" @clickLeft="clickLeft" @clickRight="clickRight" />
		<view class="u-demo-wrap" style="">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :index="index" :arrow="false" @click="showDetail(item.id)">
					<view slot="title" class="text-black">{{item.title}}</view>
				</u-cell-item>
			</u-cell-group>
			<u-empty v-if="list.length == 0" mode="list" text="暂无数据"></u-empty>
		</view>
	</view>
</template>
<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				list: [],
			}
		},
		onLoad() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				this.$http.get('sCenter/help', {
					page: this.pageData.page
				}, res => {
					
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			showDetail(id) {
				uni.navigateTo({
					url: `/pages/help/detail?id=${id}`
				});
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.switchTab({
					url: '/pages/center/index'
				});
			},
		}
	}
</script>
<style lang="scss" scoped>
</style>
