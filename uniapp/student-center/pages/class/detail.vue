<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" :title="info.name" @clickLeft="clickLeft" />
		<view class="u-demo-wrap" style="">

			<view class="bg-white u-padding-30 u-border-bottom">
				基本信息
			</view>
			<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
				<view> <text class="text-gray u-margin-right-20">班级名称:</text> {{info.name}} </view>
				<view> <text class="text-gray u-margin-right-20">班主任:</text> {{info.teacherName}}</view>
				<view> <text class="text-gray u-margin-right-20">教室名称:</text> {{info.classroom}} </view>
				<view> <text class="text-gray u-margin-right-20">相关课程:</text> {{info.courseName}} </view>
				<view> <text class="text-gray u-margin-right-20">起止日期:</text> {{info.startDate}} ~ {{info.endDate}}</view>
				<view> <text class="text-gray u-margin-right-20">课次数量:</text> {{info.overLessonCount}}</view>
				<view> <text class="text-gray u-margin-right-20">班级人数:</text> {{info.studentCount}} </view>
				<view> <text class="text-gray u-margin-right-20">其他说明:</text> {{info.remark}} </view>
				<view> <text class="text-gray u-margin-right-20">班级状态:</text> {{info.beOver?'已结课':'进行中'}} </view>
			</view>

			<view class="bg-white u-padding-30 u-flex">
				<view class="u-flex-3">班级学员</view>
				<view class="u-flex-1 u-text-right">
					<!-- <u-button type="primary" shape="circle" size="mini" @click="selectStudent">添加学员</u-button> -->
				</view>
			</view>

			<view class="u-margin-bottom-20">
				<!-- <u-read-more :show-height="500" close-text="全部学员"> -->
					<u-cell-group title="">
						<u-cell-item v-for="(item,index) in studentList" :key='index' center :is-link="false" :arrow="false">
							<u-icon slot="icon" class="u-m-r-30" name="woman" color="#ff557f" size="28" v-if="item.gender == '女'"></u-icon>
							<u-icon slot="icon" class="u-m-r-30" name="man" color="#2979ff" size="28" v-if="item.gender == '男'"></u-icon>
							
							<view slot="title">
								{{item.name}}
							</view>
							<view ></view>
						</u-cell-item>
					</u-cell-group>
				<!-- </u-read-more> -->
				<view class="u-padding-30 bg-white" v-if="studentList.length == 0" >
					<u-empty mode="list" text="暂无学员"></u-empty>
				</view>
			</view>
		
			<!-- <StudentSelecter :limit="1" :visible.sync="showSelecter"></StudentSelecter> -->
		</view>
	</view>
</template>
<script>
	// import StudentSelecter from "../../components/selecter/student-selecter.vue"
	export default {
		components: {
			// StudentSelecter
		},
		data() {
			return {
				classId: '',
				info: {},
				studentList: [],

				showSelecter: false,
			}
		},
		onLoad(option) {
			this.classId = option.classId
			this.getInfo()
			this.getStudentList()
		},
		methods: {
			getInfo() {
				this.$http.get('sCenter/class/info', {
					id: this.classId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			getStudentList() {
				this.$http.get('sCenter/student/list', {
					classId: this.classId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.studentList = res
				})
			},
			studentdetail(id) {
				uni.navigateTo({
					url: `/pages/student/detail?studentId=${id}`
				});
			},
			selectStudent() {
				this.showSelecter = true
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
