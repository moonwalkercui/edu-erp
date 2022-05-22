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
		
		<u-popup v-model="showBox" mode="bottom"  border-radius="14" height="1200rpx">
			<view class="u-text-center" style="min-height: 1050rpx; overflow-y: scroll;">
				<view class="u-font-14 u-p-30 text-bold">{{detail.title}}</view>
				<view class="u-font-12 text-gray">{{detail.addTime}}</view>
				<view class="u-p-30 ">
					<u-image v-if="detail.cover && detail.cover!=''" style="width: 100%;" height="600rpx" :src="detail.cover"></u-image>
					<view class="u-font-14 u-text-left u-m-t-20">
						<text v-html="detail.content"></text>
					</view>
				</view>
			</view>
			<view class="u-text-center">
				<u-button @click="showBox = false;" size="medium">知道了</u-button>
			</view>
		</u-popup>
	</view>
</template>
<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				showBox: false,
				checked: false,
				list: [],
				detail: {
					content: '',
					title: '',
					addTime: '',
					cover: null,
				},
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
				this.showBox = true;
				this.detail = item
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
