<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-if="$util.hasPermission(76)">新增</el-button>
                <el-button type="primary" @click="dialogApplyVisible = true" icon="el-icon-document"  v-if="$util.hasPermission(78)">申请</el-button>
                <el-button @click="$exportExcel('material')" icon="el-icon-download">导出</el-button>
            </el-form-item>
            <el-form-item label="division" prop="search_division">
                <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="name" prop="search_name">
                <el-input v-model="urlParam.search_name" placeholder="" style="width: 150px" ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
                ref="tableBuilder"
                dataUrl="material/table"
                :fields="tableProps.fields"
                :showIndex="true"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "260"
                :actionMultiButtons = "tableProps.tableActionMultiButtons"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>

        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px">
            <br/>
            <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="dataForm.name" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="所属门店" prop="division">
                    <select-builder table="division" v-model="dataForm.division"></select-builder>
                </el-form-item>
                <el-form-item label="价格">
                    <el-input v-model="dataForm.price" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="库存数量" v-if="!dataForm.id">
                    <el-input v-model="dataForm.quantity" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="存放位置">
                    <el-input v-model="dataForm.position" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <radio-status v-model="dataForm.status" statusName="switch"></radio-status>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
                <reqButton @handleReq = "handleSave('dataForm')" />
            </div>
        </el-dialog>
        <el-dialog title="出入库明细" :visible.sync="dialogDetailVisible" width="900px">
            <el-table :data="detailData" style="width: 100%">
                <el-table-column label="日期" width="120">
                    <template slot-scope="scope">
                        {{ new Date(scope.row.created_at).format("yy-MM-dd hh:mm")}}
                    </template>
                </el-table-column>
                <el-table-column prop="user_name" label="申请人" > </el-table-column>
                <el-table-column prop="material_name" label="物料名称" > </el-table-column>
                <el-table-column prop="quantity" label="变动数量"> </el-table-column>
                <el-table-column prop="status" label="状态" width="80"> </el-table-column>
                <el-table-column prop="remark" label="申请说明"> </el-table-column>
            </el-table>
        </el-dialog>
        <apply-material v-bind:visible.sync='dialogApplyVisible'></apply-material>
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
    import TableBuilder from '@/components/TableBuilder'
    import radioStatus from '@/components/RadioStatus'
    import ApplyMaterial from '@/components/ApplyMaterial'
    export default {
        components: {selectBuilder ,radioStatus , ApplyMaterial ,TableBuilder},
        data() {
            return {
                tableProps : {
                    fields : [
                        {title :'名称' , name:"name" },
                        {title :'库存' , name:'quantity'},
                        {title :'价格' , name:'price' },
                        {title :'门店', content:(row)=>{return (<span>{row.division.name}</span>)} },
                        {title :'位置' , name:'position'},
                        {title :'状态' , name:'status' },
                    ],
                    tableActionButtons:[
                        {title:'编辑' ,pm:77, icon:"el-icon-edit" , click:(row)=>{ this.handleEdit(row) }},
                        {title:'入库' ,pm:80, icon:"el-icon-edit-outline" , click:(row)=>{ this.handleChangeQuantity(row) }},
                        {title:'明细' ,pm:75, icon:"el-icon-info" , click:(row)=>{ this.showDetails(row) }},
                        {title:'删除' ,pm:79, icon:"el-icon-delete" , click:(row)=>{ this.handleDel(row) }},
                    ],
                },
                dialogApplyVisible: false,
                data: [],
                urlParam : {
                    search_name : '',
                    search_sn : '',
                    search_division:'',
                    // search_division : this.$cookie.fetchJson('_userInfo').division
                },
                dialogFormVisible: false,
                dialogDetailVisible: false,
                dataForm:{},
                dialogTitle: '',
                detailData: '',
                rules: {
                    name: [ {required: true, message: '请输入名称', trigger: 'blur'} ],
                    division: [ {required: true, message: '请选择门店', trigger: 'blur'} ],
                    quantity: [ {required: true, message: '请输入数量', trigger: 'blur'} ],
                },
            }
        },
        methods: {
            tableRowClassName({row}){
              if(row.quantity == 0){
                return 'is-warning'
              }else if (row.status !== '正常') {
                return 'is-stop';
              }
            },
            dataFormInit(){
                this.dataForm = {
                    id:'',
                    name:'',
                    division:'',
                    // division:this.$cookie.fetchJson('_userInfo').division,
                    quantity:'',
                    position:'',
                    status:'',
                    price:'',
                };
            },
            handleCreate(){
                this.dataFormInit();
                this.dialogTitle = '添加物料';
                this.dialogFormVisible = true;
            },
            handleEdit(data){
                this.dataFormInit();
                this.dialogTitle = '编辑物料';
                this.dataForm.id = data.id;
                this.dataForm.name = data.name;
                this.dataForm.division = data.division_id;
                this.dataForm.position = data.position;
                this.dataForm.quantity = data.quantity;
                this.dataForm.status = data.status;
                this.dataForm.price = data.price;
                this.dialogFormVisible = true;
            },
            handleDel: function (row) {
                this.$confirm('此操作将删除物料, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('material/delete',{"id":row.id}).then((res) => {
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
                            this.$http.post("material/create",this.dataForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$refs.tableBuilder.getData();
                                    this.dataFormInit();
                                    this.dialogFormVisible = false;
                                }
                            });
                        }else{
                            this.$http.post("material/edit",this.dataForm).then((res) =>{
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
            handleChangeQuantity(row) {
                this.$prompt('请填写入库数量', '入库', {
                    confirmButtonText: '提交',
                    cancelButtonText: '取消',
                    inputPlaceholder: '输入数量',
                    inputPattern: /[0-9]+/,
                    inputErrorMessage: '请输入数量'
                }).then(({ value }) => {
                    this.$http.post('material/changequantity',{"id":row.id,'number':value}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            showDetails(row) {
                this.dialogDetailVisible = true;
                this.detailData = row.logs;
            }
        }
    }
</script>