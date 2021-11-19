<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="报单" @clickLeft="clickLeft" />

		<view class="bg-white u-padding-20">

			<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">
				<u-form-item label-width="130" label="选择学生" prop="studentName">
					<view @click="showSelecter=true" style="width: 80%; height: 40rpx; line-height: 150%;"><text
							v-if="model.studentName == ''">请选择</text>{{model.studentName}}</view>
					</u-picker>
				</u-form-item>

				<u-form-item label-width="130" label="选择课程" prop="courseName">
					<view @click="showSelecter2=true" style="width: 80%; height: 40rpx; line-height: 150%;"><text
							v-if="model.courseName == ''">请选择</text>{{model.courseName}}</view>
					</u-picker>
				</u-form-item>
				<view v-if="showMore">
					<u-form-item label-width="130" label="开始日期" prop="startDate">
						<view @click="selectStartDate=true" style="width: 80%; height: 40rpx; line-height: 150%;"><text
								v-if="model.startDate == ''">请选择</text>{{model.startDate}}</view>
						<u-picker v-model="selectStartDate" mode="time" :params="pickerParams"
							@confirm="chooseStartDate">
						</u-picker>
					</u-form-item>
					<u-form-item label-width="130" label="有效期至" prop="expireDate">
						<view @click="selectExpireDate=true" style="width: 80%; height: 40rpx; line-height: 150%;"><text
								v-if="model.expireDate == ''">请选择</text>{{model.expireDate}}</view>
						<u-picker v-model="selectExpireDate" mode="time" :params="pickerParams"
							@confirm="chooseExpiredDate">
						</u-picker>
					</u-form-item>
					<u-form-item label-width="130" label="课次数" prop="countLessonTotal">
						<u-input type="number" v-model="model.countLessonTotal"></u-input>
						<text slot="right">次</text>
					</u-form-item>
					<u-form-item label-width="130" label="课程金额" prop="courseAmount">
						<u-input type="number" v-model="model.courseAmount"></u-input>
						<text slot="right">元</text>
					</u-form-item>
					<u-form-item label-width="130" label="实收金额" prop="paidAmount">
						<u-input type="number" v-model="model.paidAmount"></u-input>
						<text slot="right">元</text>
					</u-form-item>
					<u-form-item label-width="130" label="优惠金额" prop="discount">
						<u-input type="number" v-model="model.discount"></u-input>
						<text slot="right">元</text>
					</u-form-item>

					<u-form-item label-width="130" label="备注" prop="remark">
						<u-input type="textarea" v-model="model.remark"></u-input>
					</u-form-item>

				</view>


			</u-form>

			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提 交
				</view>
			</view>
			<view class="fix-bottom-buttons-box"></view>

			<StudentSelecter :limit="1" :visible.sync="showSelecter" @onSubmit="chooseStudent"></StudentSelecter>
			<CourseSelecter :limit="1" :visible.sync="showSelecter2" @onSubmit="chooseCourse"></CourseSelecter>

		</view>
	</view>
</template>

<script>
	import StudentSelecter from "../../components/selecter/student-selecter.vue"
	import CourseSelecter from "../../components/selecter/course-selecter.vue"
	export default {
		components: {
			StudentSelecter,
			CourseSelecter
		},
		data() {
			return {
				showSelecter: false,
				showSelecter2: false,
				selectStartDate: false,
				selectExpireDate: false,
				showMore: false,
				pickerParams: {
					year: true,
					month: true,
					day: true,
					hour: false,
					minute: false,
					second: false
				},
				model: {
					studentId: '',
					studentName: '',
					courseName: '',
					startDate: '',
					expireDate: '',
					countLessonTotal: '',
					courseAmount: '',
					paidAmount: '',
					discount: 0,
					remark: '',
				},
				rules: {
					studentId: [{
						required: true,
						message: '请选择学员',
						trigger: 'change'
					}],
					studentName: [{
						required: true,
						message: '请选择学员',
						trigger: 'change'
					}],
					courseName: [{
						required: true,
						message: '请选择课程',
						trigger: 'change'
					}],
					startDate: [{
						required: true,
						message: '请选择开始日期',
						trigger: 'change'
					}],
					expireDate: [{
						required: true,
						message: '请选择有效期',
						trigger: 'change'
					}],
					countLessonTotal: [{
						required: true,
						type: 'number',
						message: '请输入课次数',
						trigger: ['change', 'blur']
					}],
					courseAmount: [{
						required: true,
						type: 'number',
						message: '请输入课程金额',
						trigger: ['change', 'blur']
					}],
					paidAmount: [{
						required: true,
						type: 'number',
						message: '请输入收款金额',
						trigger: ['change', 'blur']
					}],
				},

				errorType: ['message'],
			}
		},
		onLoad() {

		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			chooseStartDate(a) {
				this.model.startDate = a.year + '-' + a.month + '-' + a.day
			},
			chooseExpiredDate(a) {
				this.model.expireDate = a.year + '-' + a.month + '-' + a.day
			},
			chooseStudent(s) {
				if(!s.id) {
					showMsg('请选择学生')
					return;
				}
				this.model.studentId = s.id
				this.model.studentName = s.name
			},
			chooseCourse(s) {
				if(!s.id) {
					showMsg('请选择课程')
					return;
				}
				this.model.courseId = s.id
				this.model.courseName = s.name
				this.getCourseInfo(s.id)
			},
			getCourseInfo: async function(id) {

				if (id) {
					this.$http.get('tCenter/course/info', {id}, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						const info = res

						this.model.startDate = this.$common.timeFormat(new Date(), 'yyyy-MM-dd')
						this.model.expireDate = this.$common.timeFormat(this.$common.dateAddMonth(new Date(),
							info.expireMonths), 'yyyy-MM-dd')
						this.model.countLessonTotal = info.lessonCount
						this.model.courseAmount = info.price
						this.model.paidAmount = info.price
						this.model.courseId = info.id

						this.showMore = true;
					})
				}
			},
			toSubmit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						this.$http.post('tCenter/course/saveCourse', this.model, res => {
							if (!this.$common.handleResponseMsg(res)) return;
							this.$common.showAlert("提交成功", "已报单成功！", () => {
								uni.navigateTo({
									url: '/pages/contract/index',
								})
							})
						}, false)
					} else {
						console.log('验证失败');
					}
				});
			},
			clickLeft() {
				uni.navigateBack()
			},
			clickRight() {
				uni.navigateTo({
					url: '/pages/contract/index',
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
</style>
