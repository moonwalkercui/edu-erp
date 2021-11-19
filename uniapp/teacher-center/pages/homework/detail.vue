<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" :title="info.name" @clickLeft="clickLeft" />
		<view class="u-demo-wrap" style="">

			<view class="bg-white u-padding-30 u-border-bottom">
				{{info.title}}
				<view class="u-font-12 text-gray u-text-right">{{info.teacherName}} {{info.addTime}}</view>
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<u-read-more :show-height="500" close-text="查看全部">
					<rich-text :nodes="info.content"></rich-text>
				</u-read-more>
			</view>
			<view class="bg-white u-padding-30 u-flex">
				<view class="u-flex-3">提交记录</view>
				<view class="u-flex-1 u-text-right">
				</view>
			</view>

			<view class="u-margin-bottom-20">
				<u-cell-group title="">
					<u-cell-item v-for="(item,index) in dataList" :key='index' center :is-link="false" :arrow="false">
						<view slot="title">{{item.studentName}}:</view>
						<view slot="label" class="text-black">
							<rich-text :nodes="item.content"></rich-text>
							<view class="u-flex u-m-t-30">
								<view class="u-flex-1" @click="showRate(item)">
									<u-rate :count="5" v-model="item.score||0" :disabled="true"></u-rate>
								</view>
								<view class="u-flex-1 u-font-12 text-gray u-text-right">{{item.addTime}}</view>
							</view>
						</view>
					</u-cell-item>
				</u-cell-group>
				<view class="u-padding-30 bg-white" v-if="dataList.length == 0">
					<u-empty mode="list" text="暂无提交记录"></u-empty>
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
					<u-input v-model="formData.comment" type="textarea" :border="true" :height="300" :auto-height="true" placeholder="输入评语" />
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
				id: '',
				info: {},
				dataList: [],
				rateBox: false,
				formData: {
					id: '',
					homeworkId: '',
					comment: '',
					score: 0
				}
			}
		},
		onLoad(option) {
			this.id = option.id
			this.getInfo()
		},
		methods: {
			getInfo() {
				this.$http.get('tCenter/homework/info', {
					id: this.id
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
					this.dataList = res.recordList || []
				})
			},
			showRate(row) {
				this.formData.id = row.id;
				this.formData.homeworkId = row.homeworkId;
				this.formData.score = row.score;
				this.formData.comment = row.comment;
				this.rateBox = true
		
			},
			clickLeft() {
				uni.navigateTo({
					url: '/pages/homework/index'
				})
			},
			toSubmit() {
				this.$http.post('tCenter/homework/comment', this.formData, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.$common.showMsg(res.msg)
					this.getInfo()
					this.rateBox = false
				}, false)
					
			},
		}
	}
</script>

<style lang="scss" scoped>

</style>
