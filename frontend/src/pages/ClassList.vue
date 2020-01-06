<template>
    <section>
        <template>
            <!--<el-alert title="若班级内学员有变动 建议给学员【更新课时】; 您也可以在课时列表的学员签到中给单个课时【刷新学员】" type="info" show-icon></el-alert>-->
            <el-alert title="若班级内学员有变动, 系统会自动更新班级对应课时(未结束)的签到记录" type="info" show-icon></el-alert>
        </template>
        <br>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(70)">添加</el-button>
                <el-button @click="$exportExcel('class')" icon="el-icon-download">导出</el-button>
            </el-form-item>
            <el-form-item label="sn" prop="search_sn">
                <el-input v-model="urlParam.search_sn" placeholder="" style="width: 150px" ></el-input>
            </el-form-item>
            <el-form-item label="name" prop="search_name">
                <el-input v-model="urlParam.search_name" placeholder="" style="width: 150px" ></el-input>
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
                dataUrl="class/table"
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
                <el-form-item label="division" prop="division">
                    <select-builder table="division" v-model="dataForm.division" size="medium"></select-builder>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input type="textarea" :rows="4" v-model="dataForm.description" class='dialog-form-item'></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button>
            </div>
        </el-dialog>
        <el-dialog title="学员管理" :visible.sync="editMemberDialogVisible" width="700px">
            <el-transfer v-model="memberSelected" :data="members" :titles="['待选择列表', '已选择列表']" :button-texts="['移除', '添加']" filterable>
            </el-transfer>

            <div slot="footer" class="dialog-footer">
                <el-button @click="editMemberDialogVisible = false">取 消</el-button>
                <reqButton @handleReq = "handleSaveMembers" />
            </div>
        </el-dialog>
    </section>
</template>
<style>
    .el-tree-node__content{
        padding: 5px;
    }
    .custom-tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
    }
    .el-transfer-panel{
        width:38%!important;
    }
    .el-transfer-panel__body,.el-transfer-panel__list{
        height:340px!important;
    }
    .el-transfer-panel__list.is-filterable{
        height:295px!important;
    }

</style>
<script>
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: { selectBuilder , TableBuilder },
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title: '名称', name: "name"},
                        {title: '门店', width: "80" , content:(row)=>{return (<span>{row.division.name}</span>)}},
                        {title: '学员数量', name: 'members_count'},
                        {title: '备注', name: 'description'},
                    ],
                    tableActionButtons:[
//                        {title:'更新课时' , icon:"el-icon-refresh" , click:(row)=>{ this.synchCourse(row) } , visible:(row)=>{return row.courses_count && row.members_count}},
                        {title:'编辑' ,pm:71, icon:"el-icon-edit" , click:(row)=>{ this.handleEdit(row)} },
                        {title:'学员管理' ,pm:73, icon:"el-icon-edit-outline" , click:(row)=>{ this.handleEditMember(row) } },
                        {title:'删除' ,pm:72, icon:"el-icon-delete" , click:(row)=>{ this.handleDel(row) } },
                    ],
                },

                urlParam: {
                    search_name : '',
                    search_sn : '',
                    search_division : '',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },
                dialogLoading: false,
                signData:[],
                dialogFormVisible: false,
                dataForm:{},
                dialogTitle: '',
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                    ],
                    division: [
                        {required: true, message: '请选择门店', trigger: 'blur'},
                    ],
                },
                members:[],
                memberSelected: [],
                editMemberDialogVisible: false,
                updateId: ''
            }
        },

        mounted() {
        },
        methods: {
            dataFormInit(){
                this.dataForm = {
                    id:'',
                    name:'',
                    division:'',
                    description:'',
                };
            },
            handleCreate(){
                this.dataFormInit();
                this.dialogTitle = '添加班级';
                this.dialogFormVisible = true;
            },
            handleEdit(data){
                this.dataFormInit();
                this.dialogTitle = '编辑班级';
                this.dataForm.id = data.id;
                this.dataForm.name = data.name;
                this.dataForm.division = data.division_id;
                this.dataForm.description = data.description;
                this.dialogFormVisible = true;
            },
            onSearchSubmit() {
                this.$refs.tableBuilder.getData(1);
            },
            handleCurrentChange(val) {
                this.urlParam.page = val;
                this.firstOpen || this.$refs.tableBuilder.getData(); // 有页码的页码会发送两次请求，第一次过滤掉。
            },
            handleDel: function (row) {
                this.$confirm('此操作将删除班级, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('class/delete',{"id":row.id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            synchCourse(row){
                this.$confirm('该操作将更新学员未开课的课时，是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('class/synchCourse',{"id":row.id}).then((res) => {
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
                            this.$http.post("class/create",this.dataForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$refs.tableBuilder.getData();
                                    this.dataFormInit();
                                    this.dialogFormVisible = false;
                                }
                            });
                        }else{
                            this.$http.post("class/edit",this.dataForm).then((res) =>{
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
            // 修改成员部分
            handleEditMember(data) {
                this.$http.fetch("member/table",{search_status:1}).then((res) =>{
                    var data = [];
                    res.data.map((u)=>{
                        data.push({
                            key: u.mobile,
                            value: u.mobile,
                            label: u.name + ' ' + u.mobile,
                        });
                    });
                    this.members = data;
                });

                this.memberSelected = [];
                this.editMemberDialogVisible = true;
                this.updateId = data.id;
                var selected = [];
                this.$http.fetch("member/getbyclass",{class:data.id}).then((res) =>{
                    res.data.map((u)=>{
                        selected.push(u.mobile);
                    });
                    this.memberSelected = selected;
                });
            },
            handleSaveMembers(){
                this.$http.post('class/savemembers',{id:this.updateId,members:this.memberSelected}).then((res) => {
                    if(typeof res.status != "undefined" && res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.updateId = '';
                        this.editMemberDialogVisible = false;
                        this.$refs.tableBuilder.getData();
                    }
                });
            },

        }
    }
</script>
