<template>
    <section>
        <el-form :inline="true" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus">添加</el-button>
            </el-form-item>
            <el-form-item label="Name">
                <el-input v-model="urlParam.search_name" placeholder="" style="width: 150px" ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
                ref="tableBuilder"
                dataUrl="member/mine"
                :fields="tableProps.fields"
                :showIndex="true"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "100"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>
        <member-create :dialog-props='dialogProps' @closeDialog="handleDialogClose" @closeDialogAndRefresh="handleDialogCloseAndRefresh"></member-create>
    </section>
</template>
<script>
    import TableBuilder from '@/components/TableBuilder'
    import MemberCreate from '@/pages/MemberCreate';
    export default {
        components : {MemberCreate ,TableBuilder},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'姓名' , name:"name"  },
                        {title :'手机号' , name:"mobile" , width:'180' },
                        {title :'邮箱' , name:"email" },
                        {title :'行业' , name:"industry" },
                    ],
                    tableActionButtons:[
                        {title:'编辑' , icon:"el-icon-edit" , click:(row)=>{ this.handleEdit(row)} },
                    ]
                },
                urlParam : {
                    search_name : '', // 搜索user名
                },
                formLabelWidth: '120px',
                dialogProps: {
                    visible: false,
                    title: '',
                    data: {}
                }
            }
        },
        mounted() {
        },
        methods: {
            onSearchSubmit() {
                this.$refs.tableBuilder.getData(1);
            },
            handleDel: function (index, row) {
                this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('member/delete',{"mobile":row.mobile}).then((res) => {
//                        console.log(res);
                        this.data.splice(index, 1);
                        this.pageTotal--;
                        if(res.status == 'success'){
                            this.$message({ message: '删除成功', type: 'success' });
                            this.$router.push('/member/my');
                        }
                    });
                }).catch(() => {
                });
            },
            handleCreate() {
                this.dialogProps.visible = true;
                this.dialogProps.title = '添加学员';
                this.dialogProps.data = {};
            },
            handleEdit(row) {
                this.dialogProps.data.id = '';
                this.$http.fetch('member/find',{"mobile":row.mobile}).then((res) => {
                    this.dialogProps.data = {
                        id : res.data.id,
                        mobile : res.data.mobile,
                        member_name : res.data.name,
                        company_name : res.data.company_name,
                        email : res.data.email,
                        industry : String(res.data.industry),
                        source : String(res.data.source),
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