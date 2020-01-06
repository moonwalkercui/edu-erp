<template>
    <section>
        <el-form :inline="true" class="action-box" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(90)">添加</el-button>
            </el-form-item>
        </el-form>
        <el-tree v-loading="loading" :data="data" node-key="id" :props="{ children: '_child', label: 'name'}" default-expand-all
                 :expand-on-click-node="false" :render-content="renderContent"></el-tree>
        <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
            <br/>
            <el-form label-width="120px" :model="dataForm" ref="dataForm" :rules="rules">
                <el-form-item label="分类名称" prop="name">
                    <el-input v-model="dataForm.name" placeholder="分类名称" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="上级分类">
                    <select-category v-model="dataForm.parent" :style="{width:'400px'}"></select-category>
                </el-form-item>
                <el-form-item label="选择类型">
                    <select-builder table="catetype" v-model="dataForm.types" :isMultiple="true" :style="{width: '400px'}"></select-builder>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button>
            </div>
        </el-dialog>
    </section>
</template>
<style>
    .el-tree-node__content{
        padding: 10px 5px;
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
    import selectCategory from '@/components/SelectCategory'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        data() {
            return {
                data: [],
                loading: false,
                editDialogVisible: false,
                dialogTitle:'',
                dataForm:{},
                updateId:'',
                rules: {
                    name: [ {required: true, message: '请输入部门名称', trigger: 'blur'}],
                },
            }
        },
        components: { selectCategory ,selectBuilder},
        created() {
            this.getDataList();
            this.dataFormInit();
        },
        methods: {
            dataFormInit(){
                this.dialogTitle = '';
                this.dataForm = {
                    id:'',
                    name:'',
                    types:[],
                    parent:[0],
                };
            },
            handleCreate() {
                this.dataFormInit();
                this.dialogTitle = '添加分类';
                this.editDialogVisible = true;
            },
            handleEdit(data) {
                this.dataFormInit();
                this.dialogTitle = '修改分类';
                this.dataForm.id = data.id;
                this.dataForm.name = data.name;
                this.dataForm.types = data.types.map(val => {
                    return val.id;
                });
                this.dataForm.parent = data.parent_ids ? data.parent_ids.split(",").map((val) => {
                    return parseInt(val);
                }) : [0];
                this.editDialogVisible = true;
            },
            handleDelete(node, data) {
                this.$confirm('确认删除?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('category/delete',{"id":data.id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.getDataList();
                        }
                    });
                }).catch(() => {
                });
            },
            renderContent(h, { node, data }) {
                var showDel = typeof data._child != 'undefined' ? 'none' : 'normal';
                var tags = [];
                var buttonE = '' , buttonD = '';
                for(var i = 0; i < data.types.length; i++) {
                    tags.push(<span class='el-tag el-tag--small' style='margin-right:5px;' key={i}>{data.types[i].name}</span>)
                }
                if(this.$util.hasPermission(91)) buttonE = (<el-button size="mini" type="text" icon="el-icon-edit" on-click={ () => this.handleEdit(data) }>修改</el-button>);
                if(this.$util.hasPermission(92)) buttonD = (<el-button size="mini" type="text" icon="el-icon-delete" style={{ display: showDel }} on-click={ () => this.handleDelete(node, data) }>删除</el-button>);
                return (
                    <span class="custom-tree-node">
                        <span>{data.name} {tags} </span>
                        <span>{buttonE}{buttonD}</span>
                    </span>
                );
            },
            getDataList() {
                this.loading = true;
                this.$http.fetch("category/table",{}).then((res) =>{
                    this.data = res.data;
                    this.loading = false;
                });
            },
            handleSave(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(this.dataForm.id == ''){
                            this.$http.post('category/create',this.dataForm).then((res) => {
                                if(typeof res.status != "undefined" && res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.getDataList();
                                    this.dataFormInit();
                                    this.editDialogVisible = false;
                                }
                            });
                        }else{
                            this.$http.post('category/edit',this.dataForm).then((res) => {
                                if(typeof res.status != "undefined" && res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.getDataList();
                                    this.dataFormInit();
                                    this.editDialogVisible = false;
                                }
                            });
                        }
                    } else {
                        this.$message.error('请检查内容');
                        return false;
                    }
                });
            }
        }
    }
</script>
