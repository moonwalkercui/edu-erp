<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="small">
            <el-form-item >
                <el-button type="primary" @click="showTransmitter=true" icon="fa fa-envelope"> 发送邀请邮件</el-button>
            </el-form-item>
            <el-form-item label=" " prop="search_name">
                <el-input v-model="urlParam.search_name" placeholder="输入姓名或账号" ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
                ref="tableBuilder"
                dataUrl="join/applications"
                :fields="tableProps.fields"
                :showIndex="false"
                :showPaginate="true"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "140"
                :actionMultiButtons = "tableProps.tableActionMultiButtons"
                :handleSelection = "handelSelect"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>
        <email-transmitter v-bind="emailProps" v-bind:visible.sync="showTransmitter"></email-transmitter>

        <el-dialog title="给成员划分门店" :visible.sync="dialogFormVisible" :show-close="false">
            <el-form>
                <el-form-item label="选择门店" label-width="150px">
                    <select-builder table="division" v-model="division" :style="{width:'80%'}" ></select-builder>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleApprove">通过审核</el-button> -->
                 <reqButton @handleReq = "handleApprove" text="通过审核"/>
            </div>
        </el-dialog>

        <el-dialog title="给成员划分门店" :visible.sync="dialogMultiFormVisible">
            <el-form>
                <el-form-item label="选择门店" label-width="150px">
                    <select-builder table="division" v-model="division" :style="{width:'80%'}" ></select-builder>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogMultiFormVisible = false">取 消</el-button>
                <!-- <el-button type="primary" @click="handleMultiApprove">通过审核</el-button> -->
                <reqButton @handleReq = "handleMultiApprove" text="通过审核"/>
            </div>
        </el-dialog>

    </section>
</template>
<script type="text/ecmascript-6">
    import {map} from 'lodash'
    import TableBuilder from '@/components/TableBuilder'
    import EmailTransmitter from '@/components/EmailTransmitter'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: {selectBuilder , EmailTransmitter , TableBuilder},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'姓名' , name:"user_name" },
                        {title :'账号', name:"username" },
                        {title :'状态' , name:'status', width:"80"},
                        {title :'审核说明', name:'handle_remark' },
                        {title :'申请日期' , name:'created_at', width:"150",
                        content:(row)=>{
                            return (<span>{new Date(row.created_at).format("yyyy-MM-dd hh:mm")}</span>
                            )
                        }}
                    ],
                    tableActionButtons:[
                        {title:'通过',pm:17 , icon:"el-icon-check" , click:(row)=>{ this.showApproveDialog(row.id) } , visible:(row)=>{ return row.status=='申请中' }},
                        {title:'驳回',pm:18 , icon:"el-icon-close" , click:(row)=>{ this.handleReject(row.id) } , visible:(row)=>{ return row.status=='申请中' }},
                    ],
                    tableActionMultiButtons:[
                        {title:'批量通过',pm:17 , icon:"el-icon-check" , click:(val)=>{ this.showMultiApproveDialog(val) } },
                        {title:'批量驳回',pm:18 , icon:"el-icon-close" , click:(val)=>{ this.handleMultiReject(val) }},
                    ],
                },
                urlParam : {
                    search_name : '',
                    search_sn : '',
                    search_division : ''
                },

                rules: {
                    name: [ {required: true, message: '请输入名称', trigger: 'blur'} ],
                    division: [ {required: true, message: '请选择门店', trigger: 'blur'} ],
                    quantity: [ {required: true, message: '请输入数量', trigger: 'blur'} ],
                },
                members:[],
                // 邮件组件参数
                showTransmitter: false,
                emailProps:{
                    toemail: '5849@q.com',
                    type: 'application', // 后台实现发送类型逻辑
                    title: false, // false表示不显示由系统定义
                    content: false
                },
                dialogFormVisible : false,
                dialogMultiFormVisible : false,
                editId : '',
                editIds : [],
                division: '',
                // division: this.$cookie.fetchJson('_userInfo').division,
            }
        },
        watch: {
            dialogFormVisible:function(val){
                if(val == false) this.editId = '';
            },
            dialogMultiFormVisible:function(val){
                if(val == false) this.editIds = [];
            }
        },
        created() {
//            this.$nextTick(() => {console.log(this.$refs.tableBuilder)})
        },
        methods: {
            tableRowClassName({row}){
                var status = row.status;
                if (status == '被驳回') {
                    return 'is-stop';
                }else if (status == '已审核' ){
                    return 'is-finish';
                }else{
                    return '';
                }
            },
            handelSelect(row) {
                return row.status=='申请中';
            },
            onSearchSubmit() {
                this.$refs.tableBuilder.getData(1);
            },
            showApproveDialog(id){
                this.editId=id;
                this.dialogFormVisible = true;
            },
            handleApprove(){
                var id = this.editId;
                this.$http.post('join/approve',{"ids":[id], division : this.division}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.dialogFormVisible = false;
                        this.$refs.tableBuilder.getData();
                    }
                });
            },
            showMultiApproveDialog(val){
                this.editIds=val;
                this.dialogMultiFormVisible = true;
            },
            handleMultiApprove(){
                var val = this.editIds;
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');
                this.$http.post('join/approve',{"ids":ids , division : this.division}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.dialogMultiFormVisible = false;
                        this.$refs.tableBuilder.getData();
                    }
                });
            },
            handleReject(id){
                this.$prompt('请填写驳回原因', '驳回原因', {
                    confirmButtonText: '提交',
                    cancelButtonText: '取消',
                    inputPlaceholder: '输入原因',
                    inputErrorMessage: '请输入原因'
                }).then(({ value }) => {
                    this.$http.post('join/reject',{"ids":[id] ,'remark':value}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {});
            },
            handleMultiReject(val){
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');

                this.$prompt('请填写批量驳回原因', '驳回原因', {
                    confirmButtonText: '提交',
                    cancelButtonText: '取消',
                    inputPlaceholder: '输入原因',
                    inputErrorMessage: '请输入原因'
                }).then(({ value }) => {
                    this.$http.post('join/reject',{"ids":ids ,'remark':value}).then((res) => {
                        if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.$refs.tableBuilder.getData();
                    }
                });
                }).catch(() => {});
            }
        }
    }
</script>
