<template>
	<section>

		<el-form :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
			<el-form-item>
				<el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(95)">添加</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="data" v-loading="loading" size="medium" style="width: 100%">
			<el-table-column type="index" :index="(index)=>index+1"></el-table-column>
			<el-table-column prop="name" width="180" label="name"></el-table-column>
			<el-table-column prop="properties" label="properties">
				<template slot-scope="scope">
					<el-form :inline="true">
						<el-form-item v-for="item in scope.row.properties" :key="item.id" style="margin-bottom: 0;">
							<el-input v-model='item.name' placeholder="请输入内容" size="mini" style="width: 100px" @change="changeProperty(item.id,$event)"></el-input>
							<el-button icon="el-icon-circle-close" type="text" size="mini" style="margin-left: 5px;" @click="delProperty(item.id,item.name)"
							 v-if="$util.hasPermission(100)"></el-button>
						</el-form-item>
						<el-form-item style="margin-bottom: 0;">
							<el-button size="mini" type="text" icon="el-icon-plus" @click="addProperty(scope.row.id)" v-if="$util.hasPermission(98)">添加</el-button>
						</el-form-item>
					</el-form>
				</template>
			</el-table-column>
			<el-table-column label="" width="140" class-name="action-column">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="handleEdit(scope.row)" icon="el-icon-edit" v-if="$util.hasPermission(96)">编辑</el-button>
					<el-button type="text" size="small" @click="handleDel(scope.$index, scope.row)" icon="el-icon-delete" v-if="$util.hasPermission(97)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px">
			<br />
			<el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
				<el-form-item label="名称" prop="name">
					<el-input v-model="dataForm.name" class='dialog-form-item'></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<reqButton @handleReq="handleSave('dataForm')" />
			</div>
		</el-dialog>
	</section>
</template>
<script>
	export default {
		data() {
			return {
				data: [],
				urlParam: {},
				loading: false,
				dialogLoading: false,
				firstOpen: true, // 有页码的页码会发送两次请求，第一次过滤掉。
				signData: [],
				dialogFormVisible: false,
				dataForm: {},
				dialogTitle: '',
				rules: {
					name: [{
						required: true,
						message: '请输入名称',
						trigger: 'blur'
					}],
				},
				updateId: '',
				inputVisible: false,
				inputValue: '',
				tagInput: [],
			}
		},
		mounted() {
			this.getDataList();
		},
		methods: {
			dataFormInit() {
				this.dataForm = {
					id: '',
					name: '',
				};
			},
			handleCreate() {
				this.dataFormInit();
				this.dialogTitle = '添加类型';
				this.dialogFormVisible = true;
			},
			handleEdit(data) {
				this.dataFormInit();
				this.dialogTitle = '编辑类型';
				this.dataForm.id = data.id;
				this.dataForm.name = data.name;
				this.dialogFormVisible = true;
			},
			onSearchSubmit() {
				this.getDataList();
			},
			handleDel: function(index, row) {
				this.$confirm('确认删除该分类及属性?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.post('type/delete', {
						"id": row.id
					}).then((res) => {
						if (res.status == 'success') {
							this.$message({
								message: res.msg,
								type: 'success'
							});
							this.getDataList();
						}
					});
				}).catch(() => {});
			},
			handleSave(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (this.dataForm.id == '') {
							this.$http.post("type/create", this.dataForm).then((res) => {
								if (res.status == 'success') {
									this.$message({
										message: res.msg,
										type: 'success'
									});
									this.getDataList();
									this.dataFormInit();
									this.dialogFormVisible = false;
								}
							});
						} else {
							this.$http.post("type/edit", this.dataForm).then((res) => {
								if (res.status == 'success') {
									this.$message({
										message: res.msg,
										type: 'success'
									});
									this.getDataList();
									this.dataFormInit();
									this.dialogFormVisible = false;
								}
							});
						}
					} else {
						this.$message.error('提交失败!');
						return false;
					}
				});
			},
			getDataList() {
				this.loading = true;
				this.$http.fetch("type/table", this.urlParam).then((res) => {
					this.data = res.data;
					this.loading = false;
				});
			},
			addProperty(id) {
				//                console.log(id);
				this.$prompt('添加新属性', '', {
					confirmButtonText: '提交',
					cancelButtonText: '取消',
					inputValidator: (val) => val != null,
					inputErrorMessage: '请输入属性名'
				}).then(({
					value
				}) => {
					this.$http.post('type/createproperty', {
						"id": id,
						"name": value
					}).then((res) => {
						if (res.status == 'success') {
							this.$message({
								message: res.msg,
								type: 'success'
							});
							this.getDataList();
						}
					});
				}).catch(() => {});
			},
			changeProperty(id, value) {
				if (this.$util.hasPermission(99) == false) {
					this.$message.error('无编辑权限');
					return;
				}
				//                console.log(id,value);
				this.$http.post('type/editproperty', {
					"id": id,
					"name": value
				}).then((res) => {
					if (res.status == 'success') {
						this.$message({
							message: res.msg,
							type: 'success'
						});
						this.getDataList();
					}
				});
			},
			delProperty(id, name) {
				//                console.log(id,name);
				this.$confirm('确认删除属性: ' + name, '提示', {
					confirmButtonText: '确定',
					type: 'warning'
				}).then(() => {
					this.$http.post('type/deleteproperty', {
						"id": id
					}).then((res) => {
						if (res.status == 'success') {
							this.$message({
								message: res.msg,
								type: 'success'
							});
							this.getDataList();
						}
					});
				}).catch(() => {});
			},
		}
	}
</script>
<style>
	.el-tag+.el-tag {
		margin-left: 10px;
	}

	.button-new-tag {
		margin-left: 10px;
		height: 32px;
		line-height: 30px;
		padding-top: 0;
		padding-bottom: 0;
	}

	.input-new-tag {
		margin-left: 10px;
		vertical-align: bottom;
	}
</style>
