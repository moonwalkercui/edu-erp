<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="成绩单" @clickLeft="clickLeft" @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="false" index="index"
				:value-style="{'width': '350rpx'}" :arrow="false">
					<view slot="title">{{item.gradeTitle}}</view>
					<view slot="label" class="u-font-12">{{item.add_time}}</view>
					<view style="color: black;">成绩: {{item.score}}</view>
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
				this.$http.get('sCenter/student/gradeRecord',{page: this.pageData.page}, res => {
					if(!res) return;
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
				uni.navigateBack()
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
