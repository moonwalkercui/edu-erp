<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="上课点评" @clickLeft="clickLeft" />
		<view class="u-demo-wrap" style="">

			<view class="u-margin-bottom-20">
				<u-cell-group title="">
					<u-cell-item v-for="(item,index) in dataList" :key='index' center :is-link="false" :arrow="false" @click="showRate(item)">
						<view slot="title">{{item.name}}</view>
						<view slot="label" class="text-black">
							{{item.evaluation||'未点评'}}
							<view class="u-font-12 text-gray u-text-right">{{item.evaluateTeacherName}} {{item.evaluateTime}}</view>
						</view>
						<view><u-rate :count="5" v-model="item.score||0" :disabled="true"></u-rate></view>
					</u-cell-item>
				</u-cell-group>
				<view class="u-padding-30 bg-white" v-if="dataList.length == 0">
					<u-empty mode="list" text="暂无记录"></u-empty>
				</view>
			</view>

			<u-popup v-model="rateBox" mode="bottom">
				<view class="u-p-30">
					<view class="bg-white u-padding-30 u-border-bottom">
						评分:
					</view>
					<view class="u-text-center u-m-b-30">
						<u-rate :count="5" v-model="formData.score" :size="48"></u-rate>
					</view>
					<u-input v-model="formData.evaluation" type="textarea" :border="true" :height="300" :auto-height="true" placeholder="输入评语" />
					<view class="fix-bottom-buttons">
						<view class="btn" @tap="toSubmit">
							<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提 交
						</view>
					</view>
					<view class="fix-bottom-buttons-box"></view>
				</view>
			</u-popup>

		</view>
	</view>
</template>
<script>
	export default {
		components: {},
		data() {
			return {
				lessonId: '',
				dataList: [],
				rateBox: false,
				formData: {
					id: '',
					evaluation: '',
					score: 0
				}
			}
		},
		onLoad(option) {
			this.lessonId = option.lessonId
			this.handleRequest()
		},
		methods: {
			handleRequest() {
				this.$http.get('tCenter/lesson/students', {
					lessonId: this.lessonId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.dataList = res
				})
			},
			showRate(row) {
				this.formData.id = row.id;
				this.formData.score = row.score;
				this.formData.evaluation = row.evaluation;
				this.rateBox = true
			},
			clickLeft() {
				uni.navigateBack()
			},
			toSubmit() {
				this.$http.post('tCenter/lesson/evaluation', this.formData, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.$common.showMsg(res.msg)
					this.handleRequest()
					this.rateBox = false
				}, false)
					
			},
		}
	}
</script>

<style lang="scss" scoped>

</style>
