<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="添加意向学员" @clickLeft="clickLeft" />
		<view class="bg-white u-padding-20">

			<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">
				<u-form-item label-width="130" label="学员姓名" prop="name">
					<u-input placeholder="必填" v-model="model.name"></u-input>
				</u-form-item>
				<u-form-item label-width="130" label="手机号码" prop="mobile">
					<u-input placeholder="必填" v-model="model.mobile"></u-input>
				</u-form-item>
				<u-form-item label-width="130" label="选择性别" prop="gender">
					<u-radio-group v-model="selectedGender" @change="radioGroupChange">
						<u-radio shape="circle" v-for="(item, index) in genderEnum" :key="index" :name="item.name">
							{{ item.name }}
						</u-radio>
					</u-radio-group>
				</u-form-item>
				<u-form-item label-width="130" label="出生年月" prop="birthday">
					<view @click="selectDate=true" style="width: 80%; height: 40rpx; line-height: 150%;"><text
							v-if="model.birthday == ''">请选择</text>{{model.birthday}}</view>
					<u-picker v-model="selectDate" mode="time" :params="pickerParams" @confirm="chooseDate">
					</u-picker>
				</u-form-item>
				<u-form-item label-width="130" label="身份证号" prop="idcard">
					<u-input v-model="model.idcard"></u-input>
				</u-form-item>
				<u-form-item label-width="130" label="备注信息" prop="remark">
					<u-input type="textarea" v-model="model.remark"></u-input>
				</u-form-item>
			</u-form>

			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提 交
				</view>
			</view>
			<view class="fix-bottom-buttons-box"></view>

		</view>
	</view>
</template>
<script>
	export default {
		components: {},
		data() {
			return {
				selectDate: false,

				pickerParams: {
					year: true,
					month: true,
					day: true,
					hour: false,
					minute: false,
					second: false
				},
				model: {
					name: '',
					mobile: '',
					gender: '',
					birthday: '',
					idcard: '',
					remark: '',
				},
				rules: {
					name: [{
						required: true,
						message: '请输入姓名',
						trigger: 'change'
					}],
					mobile: [{
							required: true,
							message: '请输入手机号码',
							trigger: 'change'
						},
						{
							// 自定义验证函数，见上说明
							validator: (rule, value, callback) => {
								// 上面有说，返回true表示校验通过，返回false表示不通过
								// this.$u.test.mobile()就是返回true或者false的
								return this.$u.test.mobile(value);
							},
							message: '手机号码不正确',
							// 触发器可以同时用blur和change
							trigger: ['change', 'blur'],
						}
					],
					gender: [{
						required: true,
						message: '请选择性别',
						trigger: 'change'
					}],
				},

				selectedGender: '未知',
				genderEnum: [{
						name: '未知',
						checked: true
					},
					{
						name: '男',
						checked: false
					},
					{
						name: '女',
						checked: false
					}
				],
				errorType: ['message'],
			}
		},
		onLoad() {},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			radioGroupChange(v) {
				console.log(v)
				this.model.gender = v
			},
			chooseDate(a) {
				this.model.birthday = a.year + '-' + a.month + '-' + a.day
			},
			toSubmit() {
				this.$refs.uForm.validate(valid => {
					if (valid) {
						this.$http.post('tCenter/student/save', this.model, res => {
							if (!this.$common.handleResponseMsg(res)) return;
							this.$common.showAlert("", "已添加成功！", () => {
								uni.navigateTo({
									url: '/pages/student/index',
								})
							})
						}, false)
					} else {
						console.log('验证失败');
					}
				});
			},
			clickLeft() {
				uni.switchTab({
					url: '/pages/index/index',
				})
			},
			clickRight() {
				uni.navigateTo({
					url: '/pages/student/index',
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
</style>
