<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" :title="info.name" @clickLeft="clickLeft" />
		<view class="u-demo-wrap" style="">

			<view class="bg-white u-padding-30 u-border-bottom">
				课程信息
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<view> <text class="text-gray u-margin-right-20">课程名称:</text> {{info.name}} </view>
				<view> <text class="text-gray u-margin-right-20">所属科目:</text> {{info.subjectName}}</view>
				<view> <text class="text-gray u-margin-right-20">课程类型:</text> {{info.lessonType}}</view>
				<view> <text class="text-gray u-margin-right-20">课次数:</text> {{info.lessonCount}}</view>
				<view> <text class="text-gray u-margin-right-20">价格:</text> ￥{{info.price}} / {{info.unitName}}</view>
				<view> <text class="text-gray u-margin-right-20">有效期:</text> {{info.expireMonths}}个月</view>
				<view> <text class="text-gray u-margin-right-20">说明:</text> {{info.info}}</view>
			</view>

		</view>
	</view>
</template>
<script>
	export default {
		components: {
		},
		data() {
			return {
				courseId: '',
				info: {},
			}
		},
		onLoad(option) {
			this.courseId = option.courseId
			this.getInfo()
		},
		methods: {
			getInfo() {
				this.$http.get('tCenter/course/info', {
					id: this.courseId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			clickLeft() {
				uni.navigateTo({
					url: '/pages/course/index'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
