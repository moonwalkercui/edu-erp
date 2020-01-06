<template>
	<section>
		<div style="margin-top: 10px;">
			<el-form ref="ruleForm" size="small" :inline="true" label-width="100px" class="add-buttons-line">
				<helper key-id='courseschedulew' float='right' />
				<el-form-item label="按门店查" style="margin-bottom: 0">
					<select-builder table="division" v-model="urlParam.search_division" :style="{width: '130px'}" size="small"></select-builder>
				</el-form-item>
				<el-form-item label="按老师查" style="margin-bottom: 0">
					<select-builder table="user" v-model="urlParam.search_user" :style="{width: '130px'}" size="small"></select-builder>
				</el-form-item>
				<el-form-item label="按课程查" style="margin-bottom: 0">
					<select-builder table="product" v-model="urlParam.search_product" :style="{width: '130px'}" size="small"></select-builder>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleRequest">查询</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div style="margin-bottom: 10px; margin-left: 5px;">
		<el-button-group>
			<el-button size="small" @click="dialogEdit = true" icon="el-icon-arrow-right" type="success">添加课时</el-button>
			<el-button size="small" @click="genarateWeek" icon="el-icon-arrow-right">生成【下周】课时</el-button>
			<el-button size="small" @click="genarateMonth" icon="el-icon-d-arrow-right">生成【下月】课时</el-button>
		</el-button-group>
		</div>
		<div class="week-box">
			<div class="week-day" v-for="(day,idx) in weekDays" :key="idx">
				<div class="week-day-name">周{{day}}</div>
				<div class="week-day-item" v-for="(item,index) in list[idx]" :key="index">
					<el-popover placement="right" trigger="hover">
						<el-table :data="[item]">
							<el-table-column label="时间" width="100">
								<template slot-scope="scope">
									{{scope.row.start_time}}~{{scope.row.end_time}}
								</template>
							</el-table-column>
							<el-table-column label="姓名" width="80">
								<template slot-scope="scope">
									{{scope.row.user.name}}
								</template>
							</el-table-column>
							<el-table-column label="课程" width="120">
								<template slot-scope="scope">
									{{scope.row.product.name}}
								</template>
							</el-table-column>
							<el-table-column label="门店" width="120">
								<template slot-scope="scope">
									{{scope.row.division.name}}
								</template>
							</el-table-column>
							
							<el-table-column label="操作">
								<template slot-scope="scope">
									<el-button @click="handleDel(scope.row)" type="text" size="small">删除</el-button>
								</template>
							</el-table-column>
						</el-table>
						<el-button slot="reference" type="primary" plain="">{{ item.start_time }}~{{ item.end_time }} {{ item.user.name }}</el-button>
					</el-popover>
				</div>
			</div>

			<el-dialog title="添加课时" :visible.sync="dialogEdit" width="600px">
				<el-form label-width="120px" size="normal" :model="dialogData" :rules="rules" ref="dialogForm">
					<el-form-item label="选择老师" prop="user">
						<select-builder table="user" v-model="dialogData.user" size="normal"></select-builder>
					</el-form-item>
					<el-form-item label="选择星期" prop="weekDay">
						<el-checkbox-group v-model="dialogData.weekDay" size="mini">
							<el-checkbox-button v-for="day in weekDays" :label="day" :key="day">周{{day}}</el-checkbox-button>
						</el-checkbox-group>
					</el-form-item>
					<el-form-item label="上课时间" prop="startTime">
						<el-time-select placeholder="起始时间" v-model="dialogData.startTime" style="width: 120px;margin-right:20px"
						 :picker-options="{ start: '06:30', step: '00:05', end: '23:00'}">
						</el-time-select>
						<el-time-select placeholder="结束时间" v-model="dialogData.endTime" style="width: 120px" :picker-options="{ start: '06:30', step: '00:05', end: '23:00', minTime: dialogData.startTime }">
						</el-time-select>
					</el-form-item>
					<el-form-item label="选择课程" prop="product">
						<select-builder table="product" v-model="dialogData.product" size="normal"></select-builder>
					</el-form-item>
					<el-form-item label="上课门店" prop="division">
						<select-builder table="division" v-model="dialogData.division" size="normal"></select-builder>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogEdit = false">取 消</el-button>
					<reqButton @handleReq="handleSubmit('dialogForm')" />
				</div>
			</el-dialog>

		</div>

	</section>
</template>
<script>
	import {
		map,
		reject
	} from 'lodash'
	import selectBuilder from '@/components/SelectBuilder'
	export default {
		components: {
			selectBuilder
		},
		data() {
			return {
				urlParam: {
					search_division: 0,
					search_user: "",
					search_product: "",
				},
				dialogEdit: false,
				weekDays: ['日', '一', '二', '三', '四', '五', '六'],
				list: [],
				dialogData: {
					user: '',
					product: '',
					weekDay: [],
					startTime: '',
					endTime: '',
					division: '',
					// class: '',
					// room: '',
				},
				rules: {
					user: [{
						required: true,
						message: '请选择老师'
					}],
					product: [{
						required: true,
						message: '请选择课程'
					}],
					weekDay: [{
						required: true,
						message: '请选择星期'
					}],
					startTime: [{
						required: true,
						message: '请选择开始时间'
					}],
					endTime: [{
						required: true,
						message: '请选择结束时间'
					}],
					division: [{
						required: true,
						message: '请选择门店'
					}],
				}
			}
		},
		created() {
			this.handleRequest();
		},
		methods: {
			genarateWeek() {
				this.$http.fetch("course/generatecoursebysch", {type: 'next_week'}).then((res) => {
					if(res.status == 'success') {
						this.$message({
							message: res.msg,
							type: 'success'
						});
					}
				});
			},
			genarateMonth() {
				this.$http.fetch("course/generatecoursebysch", {type: 'next_month'}).then((res) => {
					if(res.status == 'success') {
						this.$message({
							message: res.msg,
							type: 'success'
						});
					}
				});
			},
			handleRequest() {
				this.$http.fetch("course/getweekschedule", this.urlParam).then((res) => {
					if(res.status == 'success') {
						this.list = res.data;
					}
				});
			},
			handleSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid == false) {
						this.$message.error('提交失败!');
						return valid;
					}
					var selectDays = map(this.dialogData.weekDay, function(n) {
						var val;
						switch (n) {
							case '日':
								val = 0;
								break;
							case '一':
								val = 1;
								break;
							case '二':
								val = 2;
								break;
							case '三':
								val = 3;
								break;
							case '四':
								val = 4;
								break;
							case '五':
								val = 5;
								break;
							case '六':
								val = 6;
								break;
							default:
								val = null;
						}
						return val;
					});
					// console.log({...this.dialogData, week: selectDays})
					this.$http.post("course/createbyweek", { ...this.dialogData,
						week: selectDays
					}).then((res) => {
						if (res.status == 'success') {
							this.$message({
								message: res.msg,
								type: 'success'
							});
							this.dialogEdit = false;
							this.handleRequest();
						}
					});
				});
			},
			handleDel({id}) {
				this.$http.post("course/delweeksch", {id}).then((res) => {
					if (res.status == 'success') {
						this.$message({
							message: res.msg,
							type: 'success'
						});
						this.handleRequest();
					}
				});
			},
		}
	}
</script>
<style>
	.week-box {
		display: flex;
		width: 100%;
	}

	.week-box .week-day {
		flex: 1;
		margin: 5px;
		padding: 5px;
		border-radius: 8px;
		border: 1px solid #EFEFEF;
		background: #F5F6F8;
	}

	.week-box .week-day .week-day-name {
		text-align: center;
		font-weight: bold;
		margin-bottom: 15px;
	}

	.week-box .week-day .week-day-item {
		/* 	border-radius: 3px;
		background: #F4F4F5; */
		/* padding: 5px; */
		margin-bottom: 5px;
		text-align: center;
	}
</style>
