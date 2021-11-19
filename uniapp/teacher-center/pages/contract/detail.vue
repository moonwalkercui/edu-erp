<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="保名信息" @clickLeft="clickLeft" />
		<view>
			<view class="bg-white u-padding-30 u-border-bottom">
				基本信息
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<view> <text class="text-gray u-margin-right-20">学员姓名:</text> {{info.studentName}}</view>
				<view> <text class="text-gray u-margin-right-20">课程名称:</text> {{info.courseName}} </view>
				<view> <text class="text-gray u-margin-right-20">课程科目:</text> {{info.subjectName}} </view>
				<view> <text class="text-gray u-margin-right-20">开始日期:</text> {{info.startDate}} </view>
				<view> <text class="text-gray u-margin-right-20">课程金额:</text> ￥{{info.amount}} </view>
				<view> <text class="text-gray u-margin-right-20">已付金额:</text> ￥{{info.paidAmount}} </view>
				<view> <text class="text-gray u-margin-right-20">待付金额:</text> ￥{{info.remainingAmount}} </view>
				<view> <text class="text-gray u-margin-right-20">折扣金额:</text> ￥{{info.discountAmount}} </view>
				<view> <text class="text-gray u-margin-right-20">过期日期:</text> {{info.expireDate}} </view>
				<view> <text class="text-gray u-margin-right-20">提交时间:</text> {{info.addTime}} </view>
				<view> <text class="text-gray u-margin-right-20">备注说明:</text> {{info.remark}} </view>
			</view>
			<view class="bg-white u-padding-30 u-border-bottom">
				课次进度
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<view> <text class="text-gray u-margin-right-20">已完成课次:</text> {{info.countLessonComplete}} </view>
				<view> <text class="text-gray u-margin-right-20">剩余课次数:</text> {{info.countLessonRemaining}} </view>
				<view> <text class="text-gray u-margin-right-20">总购买课次:</text> {{info.countLessonTotal}} </view>
			</view>
			<view class="bg-white u-padding-30 u-border-bottom">
				退款信息
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md" v-if="info.refundAmount">
				<view> <text class="text-gray u-margin-right-20">退款金额:</text> {{info.refundAmount}} </view>
				<view> <text class="text-gray u-margin-right-20">退款课次:</text> {{info.refundLessonCount}} </view>
				<view> <text class="text-gray u-margin-right-20">退款状态:</text> {{info.refundState}} </view>
				<view> <text class="text-gray u-margin-right-20">退款说明:</text> {{info.refundRemark}} </view>
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md" v-else>
				<view> <text class="text-gray u-margin-right-20">无</text></view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		components: {},
		data() {
			return {
				contractId: '',
				info: {},
				studentList: [],
			}
		},
		onLoad(option) {
			this.contractId = option.contractId
			this.getInfo()
		},
		methods: {
			getInfo() {
				this.$http.get('tCenter/course/contractInfo', {
					id: this.contractId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			clickLeft() {
				uni.navigateTo({
					url: '/pages/contract/index'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
