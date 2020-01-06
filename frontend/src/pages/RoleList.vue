<template>
    <section>
        <el-form :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item>
                <el-button type="primary" @click="$router.push('/rbac/roleCreate')" v-if="$util.hasPermission(114)">添加角色</el-button>
            </el-form-item>
        </el-form>

        <table-builder
                ref="tableBuilder"
                dataUrl="rbac/orgroles"
                :fields="tableProps.fields"
                :showIndex="true"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "180"
                :showPaginate="false"
        ></table-builder>

    </section>
</template>
<script>
    import TableBuilder from '@/components/TableBuilder'
    export default {
    components: { TableBuilder },
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'角色名' , name:"name" },
                        {title :'权限数' , name:"permissions_count" },
                        {title :'描述' , name:"description" },
                    ],
                    tableActionButtons:[
                        {title:'编辑' ,pm:115, icon:"el-icon-edit" , click:(row)=>{ this.$router.push({ path: '/rbac/roleEdit/' + row.id}) } },
                        {title:'删除' ,pm:113, icon:"el-icon-delete" , click:(row)=>{this.handleDel(row) } },                    ],
                },
                departments:[],
                urlParam : {
                    with: 'permission'
                },
                loading: false,
                formLabelWidth: '120px',
                dialogFormVisible: false,
            }
        },
        methods: {
            handleDel: function ( row) {
                this.$confirm('此操作将永久删除，是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('rbac/roledelete',{"id":row.id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
        }
    }
</script>
