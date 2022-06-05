<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="公告列表" @clickLeft="clickLeft" @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="false" index="index"
				:value-style="{'width': '350rpx'}" :arrow="true" @click="showDetail(item)">
					<view slot="title">{{index+1}}. {{item.title}}</view>
				</u-cell-item>
			</u-cell-group>
			<view class="u-padding-30 bg-white" v-if="list.length == 0" >
				<u-empty mode="list" text="暂无记录"></u-empty>
			</view>
		</view>
		<adDetail ref="adDetail"/>
	</view>
</template>
<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	import adDetail from "./components/adDetail.vue"
	export default {
		mixins: [paginateMix],
		components: {
			adDetail
		},
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
				this.$http.get('sCenter/advertisementList',{page: this.pageData.page}, res => {
					if(!res) return;
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			showDetail(item) {
				this.$refs.adDetail.show(item)
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
