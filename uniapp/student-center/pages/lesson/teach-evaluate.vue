<template>
	<view class="bg-white" style="">
		<view class="u-p-20 text-black u-border-bottom">
			<view class="u-m-b-20">课程名称：{{info.courseName}}</view>
			<view class="u-m-b-20">授课老师：{{info.teacherNames}}</view>
			<view>上课日期：{{info.date}} {{info.startTime}}</view>
		</view>
		<view>
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					综合评分:
				</view>
				<view class="u-flex-1">
					<u-rate :count="5" active-color="#f60" v-model="formData.score1" :size="48"></u-rate>
				</view>
			</view>
			
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					课堂气氛:
				</view>
				<view class="u-flex-1">
					<u-rate :count="5" active-color="#f60" v-model="formData.score2" :size="48"></u-rate>
				</view>
			</view>
		
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					授课态度:
				</view>
				<view class="u-flex-1">
					<u-rate :count="5" active-color="#f60" v-model="formData.score3" :size="48"></u-rate>
				</view>
			</view>
			
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					教学效果:
				</view>
				<view class="u-flex-1">
					<u-rate :count="5" active-color="#f60" v-model="formData.score4" :size="48"></u-rate>
				</view>
			</view>

			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					评语:
				</view>
			</view>
			<view class="u-p-20">
				<u-input v-model="formData.content" type="textarea" :border="true" :height="200" :auto-height="true"
					placeholder="输入评语" />
			</view>
			
			<view class="u-flex u-p-20">
				<view style="width: 200rpx;">
					匿名评价:
				</view>
				<view class="u-flex-1 u-text-right">
					<u-switch v-model="formData.anonymity" size="40" active-color="#2ac79f"></u-switch>
				</view>
			</view>
			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提 交
				</view>
			</view>
		</view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				info: {},
				formData: {
					id: '',
					lessonId: '',
					content: '',
					anonymity: true,
					score1: 0,
					score2: 0,
					score3: 0,
					score4: 0,
				}
			}
		},
		onLoad(option) {
			this.formData.lessonId = option.lessonId
			this.handleRequest()
		},
		methods: {
			handleRequest() {
				this.$http.get('sCenter/lesson/info', {id: this.formData.lessonId}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			toSubmit() {
				this.$http.post('sCenter/lesson/teachEvaluate', this.formData, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.$common.showMsg(res.msg, () => {
						uni.switchTab({
							url: "/pages/index/index"
						})
					})
				}, false)

			},
		}
	}
</script>
