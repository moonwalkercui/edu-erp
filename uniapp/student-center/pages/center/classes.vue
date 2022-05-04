<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="我的班级" @clickLeft="clickLeft" @clickRight="clickRight"/>
		<u-cell-group>
			<u-cell-item v-for="(item,index) in classList" :key="index" center :is-link="true" index="index" @click="showClassDetail(item.id)" :arrow="true">
				<view slot="title">{{item.name}} {{item.beOver ? '(已结课)' : ''}}</view>
				<view slot="label">班主任: {{item.teacherName}} 课程: {{item.courseName}}</view>
				<view slot="label">{{item.startDate}} ~ {{item.endDate}}</view>
				<view>{{item.studentCount}}人</view>
			</u-cell-item>
		</u-cell-group>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				classList: [],
			}
		},
		onLoad() {
			this.getClassList()
		},
		methods: {
			getClassList() {
				this.$http.get('sCenter/class/list',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.classList = res
				})
			},
			showClassDetail(id) {
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
				uni.navigateBack()
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
