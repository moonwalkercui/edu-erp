<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item label="物料">
                <select-builder table="material" v-model="urlParam.search_material" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="申请人">
                <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="状态">
                <select-status v-model="urlParam.search_status" statusName="material" :style="{width: '150px'}"></select-status>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>

        <table-builder
                ref="tableBuilder"
                dataUrl="material/log"
                :fields="tableProps.fields"
                :showIndex="false"
                :condition="urlParam"
                :actionButtons = "tableActionButtons"
                :actionWidth = "200"
                :actionMultiButtons = "tableActionMultiButtons"
                :handleSelection = "handelSelect"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>
    </section>
</template>
<script>
    import {map} from 'lodash'
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    import SelectStatus from '@/components/SelectStatus'
    export default {
        components: { selectBuilder ,SelectStatus , TableBuilder },
        data() {
            return {
                tableProps : {
                    fields : [
                        {title :'申请时间' , name:"created_at" , width:'180'},
                        {title :'物料名称' , name:'material_name'},
                        {title :'申请人' , name:'user_name'},
                        {title :'申请数量' , name:'quantity' },
                        {title :'申请说明' , name:'remark' },
                        {title :'状态' , name:'status' },
                        {title :'审核说明' , name:'handle_remark' },
                    ]
                },
                tableActionButtons:[
                    {title:'通过并出库' ,pm:81, icon:"el-icon-check" , click:(row)=>{ this.handleApprove(row.id) } ,visible:(row)=>{ return row.status == '申请中' }},
                    {title:'驳回' ,pm:82, icon:"el-icon-close" , click:(row)=>{ this.handleReject(row.id) } , visible:(row)=>{ return row.status == '申请中' }},
                ],
                tableActionMultiButtons:[
                    {title:'批量通过' ,pm:81, icon:"el-icon-check" , click:(val)=>{ this.handleMultiApprove(val) }},
                    {title:'批量驳回' ,pm:82, icon:"el-icon-close" , click:(val)=>{ this.handleMultiApprove(val) }},
                ],

                urlParam : {
                    search_material : '',
                    search_user : '',
                    search_status : ''
                },
            }
        },
         methods: {
             tableRowClassName({row}){
                 var status = row.status;
                 if (status == '被驳回') {
                     return 'is-stop';
                 }else if (status == '已出库' || status == '已入库'){
                     return 'is-finish';
                 }else{
                     return '';
                 }
             },
            handleApprove(id){
                this.$http.post('material/approve',{"ids":[id]}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.$refs.tableBuilder.getData();
                    }
                });
            },
            handleMultiApprove(val){
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');
                this.$http.post('material/approve',{"ids":ids}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
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
                    this.$http.post('material/reject',{"ids":[id] ,'remark':value}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            handleMultiReject(val){
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');
                this.$http.post('material/reject',{"ids":ids}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.$refs.tableBuilder.getData();
                    }
                });
            },
            handelSelect(row) {
                return row.status=='申请中';

            }
        }
    }
</script>
