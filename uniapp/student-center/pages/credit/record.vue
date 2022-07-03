<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="签到记录" @clickLeft="clickLeft" @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" index="index" @click="showDetail(item.id)" 
				:title-style="{'width': '75%'}" :arrow="false">
					<view slot="title">{{item.lessonTitle}}</view>
					<view slot="title">{{item.signType}} 消课 {{item.decLessonCount}} 次</view>
					<view slot="label" class="u-font-12">课程: {{item.courseName}} 签到时间：{{item.signTime}}</view>
					<view class="u-text-center">
						<view>{{item.signState}}</view>
						<u-button v-if="item.countTeachEvaluation == 0" type="primary" shape="circle" size="mini" @click="handleEvaluate(item)">评 价</u-button>
					</view>
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
				courseId: '',
				checked: false,
				list: [],
			}
		},
		onLoad(option) {
			this.courseId = option.courseId
			this.handleReq()
		},
		methods: {
			handleReq() {
				this.$http.get('sCenter/lesson/signRecord',{page: this.pageData.page, courseId: this.courseId}, res => {
					if(!res) return;
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			showDetail(id) {
				uni.navigateTo({
					url: `/pages/course/detail?courseId=${id}`
				});
			},
			handleEvaluate(item) {
				uni.navigateTo({
					url: `/pages/lesson/teach-evaluate?lessonId=${item.lessonId}`
				})
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.navigateBack();
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
