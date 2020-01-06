<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <helper key-id='memberstudent' float='right'/>
            <el-form-item >
                <el-button v-if="$util.hasPermission(24)" @click="$exportExcel('member/table',urlParam)" icon="el-icon-download">导出</el-button>
                <el-button @click="importExcelVisible=true" icon="el-icon-download">导入</el-button>
            </el-form-item>
            <el-form-item label="姓 名" prop="search_name">
                <el-input v-model="urlParam.search_name" style="width: 150px" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="search_mobile">
                <el-input v-model="urlParam.search_mobile" style="width: 150px" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="昵 称" prop="search_nickname">
                <el-input v-model="urlParam.search_nickname" style="width: 150px" placeholder=""></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>

        <table-builder
                ref="tableBuilder"
                dataUrl="member/table"
                :fields="tableProps.fields"
                :showIndex="false"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "160"
                :actionMultiButtons = "tableProps.tableActionMultiButtons"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>
 
        <el-dialog title="课时签到" :visible.sync="dialogFormVisible" width="900px">
            <br>
            <el-table :data="signData" style="width: 100%" v-loading="dialogLoading" @selection-change="handleDialogSelectionChange">
                <el-table-column type="selection" width="55" :selectable="handelDialogSelect"></el-table-column>
                <el-table-column label="上课时间" width="140">
                    <template slot-scope="scope">
                        {{ scope.row.course_date }} {{ scope.row.course_start_at.substring(0,5)}}
                    </template>
                </el-table-column>
                <el-table-column label="课程" width="150">
                    <template slot-scope="scope">
                        {{  scope.row.product_name }}
                    </template>
                </el-table-column>
                <el-table-column label="老师">
                    <template slot-scope="scope">
                        {{  scope.row.user_name}}
                    </template>
                </el-table-column>
                <el-table-column label="学员姓名" >
                    <template slot-scope="scope">
                        {{ scope.row.member.name }}
                    </template>
                </el-table-column>
                <el-table-column label="昵称">
                    <template slot-scope="scope">
                        {{ scope.row.member.nick_name }}
                    </template>
                </el-table-column>
                <el-table-column label="学员手机号" width="120">
                    <template slot-scope="scope">
                        {{ scope.row.member.mobile }}
                    </template>
                </el-table-column>
                <el-table-column label="状态">
                    <template slot-scope="scope">
                        {{ scope.row.status }} {{ scope.row.sign_at == null ? '' : new Date(scope.row.sign_at).format("MM-dd hh:mm") }}
                    </template>
                </el-table-column>
            </el-table>
            <el-row class="row-bg">
                <el-col :span="8">
                    <el-button size="small" plain @click="multipleDeleteSigns">删除课时</el-button>
                </el-col>
            </el-row>
        </el-dialog>
        <el-dialog title="课时签到" :visible.sync="dialogProductFormVisible" width="900px">
            <br>
            <el-table :data="productData" style="width: 100%" v-loading="dialogLoading" @selection-change="handleDialogSelectionChange">
                <el-table-column label="学员" width="150">
                    <template slot-scope="scope">
                        {{ scope.row.member.nick_name }}
                    </template>
                </el-table-column>
                <el-table-column label="课程">
                    <template slot-scope="scope">
                        {{ scope.row.product.name }}
                    </template>
                </el-table-column>
                <el-table-column label="课程有效期" width="150">
                    <template slot-scope="scope">
                        {{ scope.row.product.expired_at }}
                    </template>
                </el-table-column>
                <el-table-column label="总课时" prop="total_quantity" width="100"></el-table-column>
                <el-table-column label="剩余课时" prop="remaining_quantity" width="100"></el-table-column>
            </el-table>
        </el-dialog>
        <member-create :dialog-props='dialogProps' @closeDialog="handleDialogClose" @closeDialogAndRefresh="handleDialogCloseAndRefresh"></member-create>
        <MemberChangeSalesman :visible.sync="showChangeSalesman" :customers = "changingCustomers" />
        <ImportExcel :visible.sync="importExcelVisible" type="student"/>
    </section>
</template>

<style>
</style>
<script>
    import {map} from 'lodash'
    import TableBuilder from '@/components/TableBuilder'
    import MemberCreate from '@/pages/MemberCreate'
    import MemberChangeSalesman from "@/components/MemberChangeSalesman";
    import ImportExcel from "@/components/ImportExcel";
    // import selectBuilder from '@/components/SelectBuilder'
    import img from '@/assets/img_def.jpg'
    export default {
        components : { MemberCreate, TableBuilder, MemberChangeSalesman, ImportExcel },
        data() {
            return {
                importExcelVisible: false,
                showChangeSalesman: false,
                changingCustomers: [],
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :' ' ,width:"70" ,
                            content:(row)=>{ return ( <img src={row.avatar||img} class="image" style={{maxWidth:'50px',maxHeight:'50px'}} /> ) }
                        },
                        {title :'昵称', name:"nick_name" },
                        {title :'姓名', name:"name" },
                        {title :'手机号', name:"mobile", width:'180' },
                        {title :'小星星数' , name:"points" },
                        {title :'购买课时数', content:(row)=>{ return (
                            row.deal_course ?
                          <el-button type="text" size="small"><el-tag size="mini" onClick={()=>{this.showProducts(row.member_id)}}>{ row.remaining_course + ' / ' +  row.deal_course }</el-tag></el-button>
                          : <span></span>
                        )} },
                        {title :'课时签到数', content:(row)=>{ return (
                            <el-button type="text" size="small" ><el-tag size="mini" onClick={()=>{this.showSigns(row.member_id)}}>{ row.signs_count }</el-tag></el-button>
                        )} },
                        {title :'业务员', width:'100', content:(row)=>{ var uname= row.salesman ? row.salesman.name : '';
                            return ( <span>{ uname }</span> )} },
                        // {title :'职业' , name:"occupation" },
                        // {title :'销售顾问' , width:"80" , content:(row)=>{
                        //         var name = row.user ?  row.user.name : null;
                        //         return (<span>{name}</span>)
                        //     }
                        // },
                        // {title :'班级' , content:(row)=>{
                        //     var classes = [];
                        //     for(var i = 0; i < row.classes.length; i++) {
                        //         classes.push(<span style='margin-right:5px;' key={i}>{row.classes[i].name}</span>)
                        //     }
                        //     return (<span>{classes}</span>)
                        // }}
                    ],
                    tableActionButtons:[
                        {title:'编辑',pm:24, icon:"el-icon-edit" , click:(row)=>{ this.handleEdit(row)} },
                        // {title:'删除' ,pm:25, icon:"el-icon-delete" , click:(row)=>{ this.handleDel(row) } },
                        {title:'变更业务员' ,pm:26, icon:"el-icon-edit-outline" , click:(row)=>{ this.clickChangeOwner(row) } },
                    ],
                    // tableActionMultiButtons:[
                        // {title:'批量划学员' ,pm:26, icon:"el-icon-edit-outline" , click:(val)=>{ this.multipleChangeOwner(val) } },
                        // {title:'批量删除' ,pm:25, icon:"el-icon-delete" , click:(val)=>{ this.multipleDelete(val) }},
                    // ],
                },
                urlParam : {
                    search_nickname : '',
                    search_mobile : '', // 搜索学员手机
                    search_name : '', // 搜索学员名
                    only_student : 1, // 搜索班级
                },
                rules: {
                    mobile: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},
                        {min: 11, max: 11, message: '长度为11个数字', trigger: 'blur'}
                    ],
                    email: [
                        {type: 'email', message: '请输入正确的邮箱', trigger: 'blur'}
                    ],
                    member_name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ]
                },
                dialogProps: {
                    visible: false,
                    title: '',
                    data: {}
                },
                dialogLoading:false,
                dialogFormVisible:false,
                dialogProductFormVisible:false,
                signData:[],
                productData:[],
                multipleDialogSelection: [],
            }
        },
        methods: {
         
            handelSelect() {
                return true;
            },
            onSearchSubmit() {
                 this.$refs.tableBuilder.getData(1);
            },
            handleDel: function (row) {
                this.$confirm('此操作将删除学员信息, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('member/delete',{"mobile":[row.mobile]}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: '删除成功', type: 'success' });
                             this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            multipleDelete(val) {
                var mobiles =  map(val, 'mobile');
                if(JSON.stringify(mobiles) == '[]') {
                    this.$message({ message: '请先选择', type: 'error' });
                    return;
                }
                this.$confirm('此操作将批量删除学员信息, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('member/delete',{"mobile":mobiles}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: '删除成功', type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            clickChangeOwner(row){
                this.changingCustomers = [row.member_id];
                this.showChangeSalesman = true;
            },
            // multipleChangeOwner(val) {
            //     this.formChangeOwner.owner = '';
            //     this.changeUserDialogVisible = true;
            //     this.formChangeOwner.mobile =  map(val, 'mobile');
            // },
            handleCreate() {
                this.dialogProps.visible = true;
                this.dialogProps.title = '添加学员';
                this.dialogProps.data = {};
            },
            handleEdit(row) {
                this.dialogProps.data.id = '';
                this.$http.fetch('member/find',{"id":row.id}).then((res) => {
                    this.dialogProps.data = {
                        id : res.data.id,
                        mobile : res.data.mobile,
                        member_name : res.data.name,
                        company_name : res.data.company_name,
                        email : res.data.email,
                        occupation : res.data.occupation,
                        source : res.data.source
                    };
                    this.dialogProps.visible = true;
                    this.dialogProps.title = '编辑学员';
                });
            },
            handleDialogClose() {
                this.dialogProps.visible = false;
                this.dialogProps.title = '';
            },
            handleDialogCloseAndRefresh() {
                this.handleDialogClose();
                this.$refs.tableBuilder.getData();
            },
            showProducts(member_id){
                this.productData = [];
                this.dialogLoading = true;
                this.$http.fetch("product/members",{search_member:member_id}).then((res) =>{
                    this.productData = res.data;
                    this.dialogLoading = false;
                });
                this.dialogProductFormVisible = true;
            },
            showSigns(member_id){
                this.signData = [];
                this.dialogLoading = true;
                this.$http.fetch("coursesign/table",{search_member:member_id,with:'course'}).then((res) =>{
                    this.signData = res.data;
                    this.dialogLoading = false;
                });
                this.dialogFormVisible = true;
            },
            handelDialogSelect(row) {
                return row.status != '已签到' ;
            },
            handleDialogSelectionChange(val) {
                this.multipleDialogSelection = val;
            },
            multipleDeleteSigns () {
                if(this.multipleDialogSelection.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                this.$confirm('删除操作会影响学员的课时数, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    var ids = map(this.multipleDialogSelection, 'id');
                    this.$http.post('coursesign/delete',{"ids":ids}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                             this.$refs.tableBuilder.getData();
                            this.dialogFormVisible = false;
                        }
                    });
                }).catch(() => {
                });
            },
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '停用') {
                    return 'is-stop';
                }
                return '';
            },
        }
    }
</script>
