<template>
	<view class="u-card-wrap">
		<uni-nav-bar left-icon="arrow-left" left-text="课表" right-text="+调课生" title=" " @clickLeft="clickLeft" @clickRight="clickRight"/>
		<view class="u-p-10">
			<u-alert-tips type="success" title="提示:" description="出勤和迟到会消课，缺勤和请假则不会；消费课程可以更换；添加调课生用于添加非本班学生加入本课次并消课。"></u-alert-tips>
		</view>
		<view class="u-m-b-30">
			<u-cell-group title="">
				<u-cell-item v-for="(item,index) in list" :key='index' :title="item.studentName" center
					:is-link="true" :arrow="true">
					<view slot="label" class="text-black">
						消费课程: {{item.consumeCourseName}} 
						<u-button  size="mini" @click="getStudentCourse(item.studentId)" class="u-m-l-20 u-m-r-20">更换</u-button>
					</view>
					<view slot="label">本课已消{{item.decLessonCount || 0}}次 </view>
					<view @click="handleRollCall(item)" class="text-black">{{getSignState(item.signState)}}</view>
				</u-cell-item>
			</u-cell-group>
			
			<view class="u-padding-30 bg-white" v-if="list.length == 0">
				<u-empty mode="list" text="本班级未安排学员"></u-empty>
			</view>
			
		</view>
	
		<view class="fix-bottom-buttons">
			<view class="btn" @tap="multiRollcall" style="background-color: #303133;">
				<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>批量点名
			</view>
		</view>
		<view class="fix-bottom-buttons-box"></view>

		<u-action-sheet :list="states" v-model="show" @click="rollcall"></u-action-sheet>
		<u-action-sheet :list="courseList" v-model="show1" @click="chooseCourse"></u-action-sheet>
		<StudentSelecter :limit="1" :visible.sync="studentSelecter" @onSubmit="chooseStudent"></StudentSelecter>
	</view>
</template>

<script>
	import StudentSelecter from "../../components/selecter/student-selecter.vue"
	export default {
		components: {StudentSelecter},
		data() {
			return {
				lessonId: '',
				show: false,
				curDate: '',
				states: [{
					text: '出勤（-1）',
					value: 1,
					color: 'green'
				}, {
					text: '迟到（-1）',
					value: 2,
					color: '#2979ff'
				}, {
					text: '缺勤',
					value: 0,
					color: 'orange'
				}, {
					text: '请假',
					value: 3,
					color: 'gray'
				}],
				list: [],
				// u-radio-group的v-model绑定的值如果设置为某个radio的name，就会被默认选中
				value: 'orange',
				requestParam: {
					studentId: [],
					lessonId: '',
					state: '',
				},
				
				courseList: [],
				show1: false,
				selectStudentId: '',
				
				studentSelecter: false,
			}
		},
		onLoad(option) {
			this.lessonId = option.lessonId
			this.getStudentList()
		},
		onReady() {

		},
		methods: {
			getStudentCourse(studentId) {
				this.selectStudentId = studentId;
				this.$http.get('common/studentCourse/validList', { studentId }, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					var list = [];
					for(var item of res) {
						list.push({
							text: item.courseName + ` (余${item.countLessonRemaining})`,
							value: item.courseId,
						})
					}
					this.courseList = list
					if(this.courseList.length == 0) {
						this.$common.showMsg("该学员无有效课程")
					} else {
						this.show1 = true
					}
				})
			},
			getStudentList() {
				this.$http.get('tCenter/lesson/classStudents', {
					lessonId: this.lessonId
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = res
				})
			},
			clickLeft() {
				uni.switchTab({
					url: `/pages/lesson/index`
				});
			},
			clickRight() {
				console.log('调课生')
				this.studentSelecter = true;
			},
			chooseStudent(s) {
				if(!s.id || s.id == '') {
					showMsg('请选择学生')
					return;
				}
				this.$http.post('common/lesson/addStudents', {
					lessonId: this.lessonId,
					studentIds: [s.id]
				}, res => {
					this.$common.showMsg(res.msg, ()=> {
						this.getStudentList()
					})
				})
			},
			getSignState(state) {
				if (!state) return '无记录';
				return state;
			},
			handleRollCall(item) {
				this.requestParam.studentId = item.studentId
				this.show = true
			},
			rollcall(index) {
				var item = this.states[index]
				this.requestParam.state = item.value 
				this.requestParam.lessonId = this.lessonId
				this.$http.post('common/lesson/rollCallBatch', [this.requestParam], res => {
					this.$common.showMsg(res.msg, ()=> {
						this.getStudentList()
					})
				})
			},
			chooseCourse(index) {
				if(this.selectStudentId == '') {
					this.$common.showMsg("未选择学生")
					return;
				}
				var chooseOne = this.courseList[index]
				this.$common.showAlert("操作确认", "确认更换消费课程？", () => {
					var lessonId = this.lessonId;
					var studentId = this.selectStudentId;
					var courseId = chooseOne.value;
					this.$http.get('common/classStudent/changeCourseAtSign', {
						lessonId, studentId, courseId
					}, res => {
						this.$common.showMsg(res.msg, ()=> {
							this.getStudentList()
						})
						this.selectStudentId = ''
					})
				})
				
			},
			multiRollcall() {
				uni.navigateTo({
					url: `/pages/lesson/multirollcall?lessonId=${this.lessonId}`
				});
			}
		}
	}
</script>
<style lang="scss" scoped>
</style>
