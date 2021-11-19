<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" :title="info.title" @clickLeft="clickLeft" />
		<view class="u-demo-wrap" style="">

			<view class="bg-white u-padding-30 u-border-bottom">
				<view class="u-font-12 text-gray u-text-right">{{info.teacherName}} {{info.addTime}}</view>
			</view>
			
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<u-read-more :show-height="500" close-text="查看全部">
					<rich-text :nodes="info.content"></rich-text>
				</u-read-more>
			</view>
			
			<view v-if="info.completed">
				<view class="bg-white u-padding-30 u-flex">
					<view class="u-flex-3">我的提交:</view>
					<view class="u-flex-1 u-text-right">
					</view>
				</view>
				
				<view class="u-margin-bottom-20">
					<u-cell-group title="">
						
						<u-cell-item v-for="(item,index) in dataList" :key='index'  :is-link="false" :arrow="false" :title-style="{'width': '100%'}" >
							<view slot="title" class="u-flex">
								<view class="u-flex-1">{{item.studentName}}:</view>
								<view class="u-flex-1 u-text-right"><u-button size="mini" shape="circle" @click="handleDelete(item.id)">X</u-button></view>
							</view>
							<view slot="label" class="text-black u-m-b-20">
								<rich-text :nodes="item.content"></rich-text>
								<view class="u-flex u-m-t-30">
									<view class="u-flex-1">
										<u-rate :count="5" v-model="item.score||0" :disabled="true"></u-rate>
									</view>
									<view class="u-flex-1 u-text-right u-font-12 text-gray u-m-l-30">{{item.addTime}}</view>
								</view>
								<view class="u-flex-1 u-font-12 text-gray">{{item.commentTeacherName ? item.commentTeacherName + ' : ' : ''}} {{item.comment}}</view>
							</view>
						
						</u-cell-item>
						
					</u-cell-group>
					<view class="u-padding-30 bg-white" v-if="dataList.length == 0">
						<u-empty mode="list" text="无提交记录"></u-empty>
					</view>
				</view>
			</view>

		</view>
		
		<view class="fix-bottom-buttons" v-if="!info.completed">
			<view class="btn" @tap="toSubmit">
				<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提交作业
			</view>
		</view>
		
		<view class="fix-bottom-buttons-box"></view>
		
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
			}
		},
		onLoad(option) {
			this.id = option.id
			this.getInfo()
		},
		methods: {
			getInfo() {
				this.$http.get('sCenter/homework/info', {
					id: this.id
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
					this.dataList = res.recordList || []
				})
			},
			clickLeft() {
				uni.switchTab({
					url: '/pages/homework/index'
				})
			},
			toSubmit() {
				uni.navigateTo({
					url: '/pages/homework/new?id=' + this.id
				})
				// this.$http.post('sCenter/homework/comment', this.formData, res => {
				// 	if (!this.$common.handleResponseMsg(res)) return;
				// 	this.$common.showMsg(res.msg)
				// 	this.getInfo()
				// 	this.rateBox = falses
				// }, false)
			},
			handleDelete(id) {
				this.$common.showAlert("删除确认", `确认删除重做？`, ()=> {
					this.$http.post(`sCenter/homework/deleteRecord/${id}`, {}, res => {
						if(!this.$common.handleResponseMsg(res)) return;
						this.$common.showMsg("已删除")
						this.getInfo()
					})
				})
			},
		}
	}
</script>

<style lang="scss" scoped>

</style>
