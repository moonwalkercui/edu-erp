<template>
	<view class="u-card-wrap">
		<uni-nav-bar left-icon="arrow-left" left-text="返回" right-icon="菜单" title="批量点名" @clickLeft="clickLeft" />
		<view class="bg-white u-margin-bottom-20">
			<view class="u-padding-10">
				<u-table align="left">
					<u-tr class="u-tr">
						<u-th class="u-th" width="29%"><text class="u-font-sm">姓名</text></u-th>
						<u-th class="u-th"><text class="u-font-sm">出勤情况</text></u-th>
					</u-tr>
					<u-tr class="u-tr" v-for="(item, index) in list" :key="item.studentId">
						<u-td class="u-td" width="29%" style="height: 80rpx" >
							<view class="u-font-sm">{{item.studentName}} </view>
							<view class="u-font-xs text-gray">本课已消{{item.decLessonCount || 0}}次</view>
						</u-td>
						<u-td class="u-td" style="height: 80rpx; ">
							<view class="u-text-center">
								<u-radio-group v-model="item.state">
									<u-radio v-for="(state, sindex) in states"
										:key="item.label" :name="state.label" :label-size="20"
										:active-color="state.color">
										{{state.label}}
									</u-radio>
								</u-radio-group>
							</view>

						</u-td>
					</u-tr>
				</u-table>
				<view class="u-padding-30 bg-white" v-if="list.length == 0">
					<u-empty mode="list" text="无需要点名的学员"></u-empty>
				</view>
			</view>
		</view>

		<view class="fix-bottom-buttons">
			<view class="btn" @tap="toSubmit">
				<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提 交
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
				lessonId: '',

				curDate: '',
				states: [{
					label: '出勤',
					value: 1,
					color: 'green'
				}, {
					label: '迟到',
					value: 2,
					color: '#2979ff'
				}, {
					label: '缺勤',
					value: 0,
					color: 'orange'
				}, {
					label: '请假',
					value: 3,
					color: 'gray'
				}],
				list: [],
				// u-radio-group的v-model绑定的值如果设置为某个radio的name，就会被默认选中
				value: 'orange',
			}
		},
		onLoad(option) {
			this.lessonId = option.lessonId
			this.getStudentList()
		},
		methods: {
			getStudentList() {
				this.$http.get('tCenter/lesson/classStudents', {
					lessonId: this.lessonId,
					unsigned: true
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = res
				})
			},
			clickLeft() {
				uni.navigateTo({
					url: `/pages/lesson/rollcall?lessonId=${this.lessonId}`
				});
			},
			toSubmit() {
				
				var getState = (stateLabel) => {
					for(var s of this.states) {
						if(s.label == stateLabel) return s.value;
					}
					return '';
				}
				
				var formData = []
				for(var i of this.list) {
					if(typeof i.state != 'undefined') {
						formData.push({
							studentId: i.studentId,
							lessonId: this.lessonId,
							state: getState(i.state),
						});
					}
				}
				if(formData.length == 0) {
					this.$common.showMsg("请设置出勤情况")
					return;
				}
			
				this.$http.post('common/lesson/rollCallBatch', formData, res => {
					this.$common.showMsg(res.msg, ()=> {
						this.getStudentList()
					})
				})
				
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
