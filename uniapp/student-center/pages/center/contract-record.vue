<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="报名记录" @clickLeft="clickLeft"  right-icon="reload"  @clickRight="clickRight"/>
		<view class="u-demo-wrap" style="">
			<u-cell-group title="">
				<u-cell-item  v-for="(item,index) in list" :key="index" center :index="index" :arrow="false">
					<view slot="title" class="text-black">{{item.courseName}}</view>
					<view slot="label">科目: {{item.subjectName}}</view>
					<view slot="label">报名日期: {{item.addTime}}</view>
					<view slot="label" :class="checkExpired(item.expireDate)">有效期: {{item.expireDate}}</view>
					<view class="text-black line-height-md">
						<view>金额: {{item.amount}}元</view>
						<view>已付: {{item.paidAmount}}元</view>
						<view>共{{item.countLessonTotal}}次 剩{{item.countLessonRemaining}}次</view>
						<u-line-progress :height="24" :striped="true" active-color="#2979ff" :percent="item.countLessonTotal >0 ? parseInt(item.countLessonComplete*100 / item.countLessonTotal) : 0"></u-line-progress>
					</view>
				</u-cell-item>
			</u-cell-group>
			<u-empty v-if="list.length == 0" mode="list" text="无报名课程"></u-empty>
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
		onShow() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				this.$http.get('sCenter/course/list',{page: this.pageData.page}, res => {
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
			checkExpired(date) {
				if(date == null || typeof date == 'undefined') return '';
				if(new Date(date) < new Date()) return 'text-red';
				return "";
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
