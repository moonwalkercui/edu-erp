<template>
    <section>

        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item>
                <el-button type="primary" icon="el-icon-edit-outline" @click="$router.push('/order/create')">报单</el-button>
            </el-form-item>
            <el-form-item label="Sn">
                <el-input size="mini" v-model="urlParam.search_sn" placeholder="编号"></el-input>
            </el-form-item>
            <el-form-item label="课程">
                <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="member">
                <select-builder table="mymember" v-model="urlParam.search_member" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="状态">
                <select-status v-model="urlParam.search_status" statusName="order" :style="{width: '150px'}"></select-status>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>

        <table-builder
                ref="tableBuilder"
                dataUrl="order/mine"
                :fields="tableProps.fields"
                :showIndex="false"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "150"
                :actionMultiButtons = "tableProps.tableActionMultiButtons"
                :tableRowClassName = "tableRowClassName"
                :expand = "true"

        >
        <template slot="expandContent" slot-scope="row">
            <el-table :data="row.data.items" style="width: 100%;" :row-style="{background: '#f4f4f5', fontSize: '13px'}" :show-header="false">
                <el-table-column prop="item_type" label="类型" width="100">
                    <template slot-scope="scope">
                        <i class="el-icon-arrow-right" style="font-size: 12px; padding-left: 30px"></i> {{ scope.row.item_type }}
                    </template>
                </el-table-column>
                <el-table-column prop="item_name" label="名称"></el-table-column>
                <el-table-column prop="num" label="数量" width="80">
                    <template slot-scope="scope">
                        x {{ scope.row.num }}
                    </template>
                </el-table-column>
                <el-table-column prop="deal_price" label="金额" width="100">
                    <template slot-scope="scope">
                        ￥ {{ scope.row.deal_price }}
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
            </el-table>
        </template>
        </table-builder>
        <el-dialog title="订单详情" :visible.sync="dialogDetailVisible" width="500px">
            <ul class="details-list">
                <li>编号 : {{ detailData.sn }}</li>
                <li>学员名 : {{ detailData.member_name + ' ('+ detailData.member_mobile + ')' }}</li>
                <li>成单人 : {{ detailData.user_name }}</li>
                <li>总金额 : {{ detailData.total_price }}</li>
                <li>类型 : {{ detailData.type }}</li>
                <li>成单时间 : {{ detailData.created_at }}</li>
                <li>状态 : {{ detailData.status }}</li>
            </ul>
        </el-dialog>
    </section>
</template>

<script>
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    import selectStatus from '@/components/SelectStatus'
    import Clipboard from "@/components/Clipboard";
    export default {
        components: {selectBuilder, selectStatus, TableBuilder, Clipboard},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'编号' ,content:(row)=>{ return <Clipboard text={row.sn}/>  } },
                        {title :'学员' , width:'200', content:(row)=>{ return (<span> { row.member_name +' (' +row.member_mobile + ')' } </span>) } },
                        {title :'报单人' , name:"user_name", width:'100'},
                        {title :'金额' , name:"total_price", width:'100'},
                        {title :'状态' , name:"status", width:'100'},
                        {title :'报单时间' , content:(row)=>{ return ( <span>{ new Date(row.created_at).format("MM-dd hh:mm") }</span> ) } },
                    ],

                    tableActionButtons:[
                        {title:'修改',pm:105 , icon:"el-icon-edit" , click:(row)=>{ this.$router.push({ path: '/order/edit/' + row.sn}) } , visible:(row)=>{ return row.status=='已驳回' }},
                        {title:'详情',pm:103 , icon:"el-icon-delete" , click:(row)=>{ this.showDetails(row) } },
                    ],
                },
                urlParam : {},
                detailData:{},
                dialogDetailVisible: false,
            }
        },
        methods: {
            showDetails(row) {
                this.dialogDetailVisible = true;
                this.detailData = row;
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
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '已驳回') {
                    return 'is-stop';
                }else if (status == '已审核'){
                    return 'is-finish';
                }else{
                    return '';
                }
            }
        }
    }
</script>
