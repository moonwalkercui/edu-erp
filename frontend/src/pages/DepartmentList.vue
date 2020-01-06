<template>
    <section>
        <el-form :inline="true" class="action-box" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(41)">添加</el-button>
            </el-form-item>
            <el-form-item label="divisions">
                <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList">查询</el-button>
            </el-form-item>
        </el-form>
        <el-tree v-loading="loading" :data="data" show-checkbox node-key="id" :props="{ children: '_child', label: 'department_name'}" default-expand-all
                 :expand-on-click-node="false" :render-content="renderContent"></el-tree>
        <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
            <br/>
            <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules">
                <el-form-item label="name" prop="name">
                    <el-input v-model="dataForm.name" placeholder="部门名称" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="division" prop="division">
                    <select-builder table="division" v-model="dataForm.division" class='dialog-form-item'></select-builder>
                </el-form-item>
                <el-form-item label="parent">
                    <select-departments v-model="dataForm.parent" :condition="{'search_division' : dataForm.division}"></select-departments>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleSave">保 存</el-button> -->
                <reqButton @handleReq = "handleSave" />
            </div>
        </el-dialog>
        <el-dialog title="成员管理" :visible.sync="editUserDialogVisible" width="700px">
            <el-transfer v-model="userSelected" :data="users" :titles="['选择成员', '已选择']" :button-texts="['移除', '添加']" filterable>
            </el-transfer>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editUserDialogVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleSaveUsers">保 存</el-button> -->
                <reqButton @handleReq = "handleSaveUsers" />
            </div>
        </el-dialog>
    </section>
</template>
<style>
    .el-tree-node__content{
        padding: 6px 5px;
        border-bottom: 1px solid #efefef;
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
        width:38%;
    }
    .el-transfer-panel__body,.el-transfer-panel__list{
        height:340px;
    }
    .el-transfer-panel__list.is-filterable{
        height:295px;
    }
</style>
<script>
    import selectBuilder from '@/components/SelectBuilder'
    import selectDepartments from '@/components/SelectDepartments'

    export default {
        components: { selectBuilder ,selectDepartments },
        data() {
            return {
                data: [],
                loading: false,
                divisionLoading: false,
                urlParam : {
                    search_division : '',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },
                editDialogVisible: false,
                dialogTitle:'',
                dataForm:{
                    id:'',
                    name:'',
                    division:'',
                    parent:[],
                },
                editUserDialogVisible: false,
                users:[],
                userSelected:[],
                updateId:'',
                rules: {
                    name: [ {required: true, message: '请输入部门名称', trigger: 'blur'}],
                    division: [ {required: true, message: '请选择门店', trigger: 'blur'}],
                },
            }
        },
        created() {
//            var userInfo = this.$cookie.fetchJson('_userInfo');  // 从cookie里选择门店
//            this.urlParam.search_division = parseInt(userInfo.org_division);
            this.getDataList();
            this.dataFormInit();
        },
        methods: {
            dataFormInit(){
                this.dialogTitle = '';
                this.dataForm = {
                    id:'',
                    name:'',
                    division:'',
                    // division:this.$cookie.fetchJson('_userInfo').division,
                    parent:[],
                };
            },
            handleCreate() {
                this.dataFormInit();
                this.dialogTitle = '添加部门';
                this.dataForm.division = this.urlParam.search_division;
                this.editDialogVisible = true;
            },
            handleEdit(data) {
                this.dataFormInit();
                this.dialogTitle = '修改部门';
                this.dataForm.id = data.id;
                this.dataForm.name = data.department_name;
                this.dataForm.division = data.division_id;
                this.dataForm.parent = data.parent_ids ? data.parent_ids.split(",").map((val) => {
                    return parseInt(val);
                }) : [];
//                this.dataForm.division = this.urlParam.search_division;
                this.editDialogVisible = true;
            },
            handleDelete(node, data) {
                this.$confirm('删除后该部门下的老师将设定为无部门。是否删除?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('depart/delete',{"id":data.id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.getDataList();
                        }
                    });
                }).catch(() => {
                });

//                const parent = node.parent;
//                const children = parent.data.children || parent.data;
//                const index = children.findIndex(d => d.id === data.id);
//                children.splice(index, 1);
            },
            renderContent(h, { node, data }) {
                var showDel = typeof data._child != 'undefined' ? 'none' : 'normal';
                var buttonE = '',buttonD = '',buttonC = '';
                if(this.$util.hasPermission(42)){buttonE = (<el-button size="mini" type="text" icon="el-icon-edit" on-click={ () => this.handleEdit(data) }>修改</el-button>)}
                if(this.$util.hasPermission(44)){buttonC = (<el-button size="mini" type="text" icon="el-icon-edit" on-click={ () => this.handleEditUsers(data) }>成员管理</el-button>)}
                if(this.$util.hasPermission(43)){buttonD = (<el-button size="mini" type="text" icon="el-icon-delete" style={{ display: showDel }} on-click={ () => this.handleDelete(node, data) }>删除</el-button>)}
                return (
                    <span class="custom-tree-node">
                    <span>{node.label}</span>
                    <span>{buttonE}{buttonC}{buttonD}</span>
                    </span>
                );
            },
            getDataList() {
                this.loading = true;
                this.$http.fetch("depart/tablewithchild",this.urlParam).then((res) =>{
                    this.data = res.data;
                    this.loading = false;
                });
            },
            handleSave(){
//                console.log(this.dataForm);return;
                if(this.dataForm.id == ''){
                    this.$http.post('depart/create',this.dataForm).then((res) => {
                        if(typeof res.status != "undefined" && res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.getDataList();
                            this.dataFormInit();
                            this.editDialogVisible = false;
                        }
                    });
                }else{
                    this.$http.post('depart/edit',this.dataForm).then((res) => {
                        if(typeof res.status != "undefined" && res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.getDataList();
                            this.dataFormInit();
                            this.editDialogVisible = false;
                        }
                    });
                }
            },
            // 修改成员部分
            handleEditUsers(data) {
                this.getUserList();
                this.userSelected = [];
                this.editUserDialogVisible = true;
                this.updateId = data.id;
                var selected = [];
                this.$http.fetch("user/table",{department:data.id}).then((res) =>{
                    res.data.map((u)=>{
                        selected.push(u.username);
                    });
                    this.userSelected = selected;
                });
            },
            handleSaveUsers(){
                this.$http.post('depart/saveUsers',{id:this.updateId,users:this.userSelected}).then((res) => {
                    if(typeof res.status != "undefined" && res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.updateId = '';
                        this.editUserDialogVisible = false;
                    }
                });
            },
            getUserList() {
                var data = [];
                this.$http.fetch("user/table",{}).then((res) =>{
                    res.data.map((u)=>{
                        data.push({
                            key: u.username,
                            value: u.username,
                            label: u.name + (u.department_id==null ? ' [ 无部门 ]' : '') ,
                        });
                    });
                    this.users = data;
                });
            }
        }
    }
</script>