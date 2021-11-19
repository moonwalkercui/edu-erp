<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" title="点评记录" @clickLeft="clickLeft" right-icon="reload"  @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" index="index" :arrow="false">
					<view slot="title">{{item.name}}</view>
					<view slot="label" class="text-black">{{item.evaluation||'未点评'}}</view>
					<view slot="label" class="u-font-12">{{item.evaluateTeacherName}} {{item.evaluateTime}}</view>
					<view><u-rate :count="5" v-model="item.score||0" :disabled="true"></u-rate></view>
				</u-cell-item>
			</u-cell-group>
			
			<view class="u-padding-30 bg-white" v-if="list.length == 0" >
				<u-empty mode="list" text="暂无点评记录"></u-empty>
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
				list: [],
			}
		},
		onLoad() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				this.$http.get('tCenter/lesson/evaluateLog',{page: this.pageData.page}, res => {
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
					url: '../index/index'
				});
			},
		}
	}
</script>
<style lang="scss" scoped>
</style>
