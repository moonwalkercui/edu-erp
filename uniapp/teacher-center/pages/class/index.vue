<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" title="我的班级" @clickLeft="clickLeft" right-icon="reload"  @clickRight="clickRight"/>
		<view class="u-demo-area">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key="index" center :is-link="true" :value="'人数:' + item.studentCount" index="index" @click="showDetail(item.id)" :arrow="true">
					<view slot="title">{{item.name}} {{item.beOver ? '(已结课)' : ''}}</view>
					<view slot="label">班主任: {{item.teacherName}} 课程: {{item.courseName}}</view>
					<view slot="label">{{item.startDate}} ~ {{item.endDate}}</view>
					<view slot="label">{{item.remark}}</view>
				</u-cell-item>
			</u-cell-group>
			
			<view class="u-padding-30 bg-white" v-if="list.length == 0" >
				<u-empty mode="list" text="暂无 班主任/上课老师/助教相关的班级信息"></u-empty>
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
				this.$http.get('tCenter/class/list',{page: this.pageData.page}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			showDetail(id) {
				uni.navigateTo({
					url: `/pages/class/detail?classId=${id}`
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
