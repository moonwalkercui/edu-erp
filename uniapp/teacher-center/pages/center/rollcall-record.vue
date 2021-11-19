<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="点名记录" @clickLeft="clickLeft" @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" index="index"
				:title-style="{'width': '75%'}" :arrow="false">
					<view slot="title">{{item.name}}</view>
					<view slot="label">{{item.signTime}}</view>
					<view>{{item.signState}} </view>
				</u-cell-item>
			</u-cell-group>
			<view class="u-padding-30 bg-white" v-if="list.length == 0" >
				<u-empty mode="list" text="暂无记录"></u-empty>
			</view>
		</view>
	</view>
</template>
<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	import * as api from '@/util/http.js'
	import {showError} from '@/util/common.js'
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
		methods: {
			handleReq() {
				this.$http.get('tCenter/lesson/rollCallRecord',{page: this.pageData.page}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
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
