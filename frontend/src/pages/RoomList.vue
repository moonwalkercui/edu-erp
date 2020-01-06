<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(84)">添加</el-button>
            </el-form-item>
            <el-form-item label="division" prop="search_division">
                <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>

        <table-builder
                ref="tableBuilder"
                dataUrl="room/table"
                :fields="tableProps.fields"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "300"
        ></table-builder>
        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px">
            <br/>
            <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="dataForm.name" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="所属门店" prop="division">
                    <select-builder table="division" v-model="dataForm.division" size="medium"></select-builder>
                </el-form-item>
                <el-form-item label="位置" prop="position">
                    <el-input v-model="dataForm.position" class='dialog-form-item'></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
                <reqButton @handleReq = "handleSave('dataForm')"/>
            </div>
        </el-dialog>
    </section>
</template>
<script>
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: { selectBuilder ,TableBuilder},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title: '名称', name: "name"},
                        {title: '门店', content:(row)=>{return (<span>{row.division.name}</span>)}},
                        {title: '位置', name: 'position'},
                    ],
                    tableActionButtons:[
                        {title:'编辑' ,pm:85, icon:"el-icon-edit" , click:(row)=>{ this.handleEdit(row)} },
                        {title:'删除' ,pm:86, icon:"el-icon-delete" , click:(row)=>{ this.handleDel(row) } },
                    ],
                },
                urlParam : {
                    search_division : ''
                },
                dialogFormVisible: false,
                dataForm:{},
                dialogTitle: '',
                rules: {
                    name: [ {required: true, message: '请输入名称', trigger: 'blur'} ],
                    division: [ {required: true, message: '请选择门店', trigger: 'blur'} ],
                },
            }
        },
        methods: {
            dataFormInit(){
                this.dataForm = {
                    id:'',
                    name:'',
                    division:'',
                    // division:this.$cookie.fetchJson('_userInfo').division,
                    position:'',
                };
            },
            handleCreate(){
                this.dataFormInit();
                this.dialogTitle = '添加教室';
                this.dialogFormVisible = true;
            },
            handleEdit(data){
                this.dataFormInit();
                this.dialogTitle = '编辑教室';
                this.dataForm.id = data.id;
                this.dataForm.name = data.name;
                this.dataForm.division = data.division_id;
                this.dataForm.position = data.position;
                this.dialogFormVisible = true;
            },
            onSearchSubmit() {
                this.$refs.tableBuilder.getData(1);
            },
            handleDel: function (row) {
                this.$confirm('此操作将删除教室 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('room/delete',{"id":row.id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            handleSave(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(this.dataForm.id == ''){
                            this.$http.post("room/create",this.dataForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$refs.tableBuilder.getData();
                                    this.dataFormInit();
                                    this.dialogFormVisible = false;
                                }
                            });
                        }else{
                            this.$http.post("room/edit",this.dataForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$refs.tableBuilder.getData();
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

        }
    }
</script>
