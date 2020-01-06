<template>
<div>
    <el-dialog title="课时签到" :visible.sync="visible" width="800px" :before-close="handleClose" :close-on-click-modal="true">
        <el-table :data="signData" style="width: 100%" v-loading="dialogLoading">
            <!-- <el-table-column type="selection" width="55" :selectable="handelDialogSelect"></el-table-column> -->
            <el-table-column type="index" width="50"></el-table-column>
            <el-table-column label="昵称" prop="member_nickname"></el-table-column>
            <el-table-column label="姓名" prop="member_name"></el-table-column>
            <el-table-column label="手机号" prop="member_mobile"></el-table-column>
            <el-table-column label="小星星" prop="points"></el-table-column>
            <el-table-column label="签到时间">
                <template slot-scope="scope">
                    {{ scope.row.sign_at == null ? '' : scope.row.sign_at|formatDate("MM-dd hh:mm") }}
                </template>
            </el-table-column>
            <el-table-column label="" width="110">
                <template slot-scope="scope">
                    <el-button icon="el-icon-check" @click="handleSign(scope.row)" type="text" size="mini" v-if="scope.row.sign_at == '' && $util.hasPermission(66)">代签</el-button>
                    <el-button icon="el-icon-star-on" @click="givePoints(scope.row)" type="text" size="mini" v-if="scope.row.course_id != '' && scope.row.point == 0">奖励小星星</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- <el-row class="row-bg">
            <el-col :span="24">
                <el-button size="small" plain @click="multipleSign" v-if="$util.hasPermission(66)">批量代签</el-button>
                <el-button size="small" plain @click="multipleDeleteSigns"  v-if="$util.hasPermission(67)">删除课时</el-button>
            </el-col>
        </el-row> -->
    </el-dialog>
    <GivePoints :visible.sync="formVisible" :item.sync = "showItem"/>
</div>
</template>
<script>
    import GivePoints from '@/components/GivePoints'
    export default {
        components: { GivePoints },
        props: {
            visible: {default: false},
            item: {default: () => {}},
        },
        data() {
            return {
                dialogLoading: false,
                signData:[],
                formVisible: false,
                showItem: {},
            }
        },
        watch: {
            item: function(newVal) {
                this.showSigns(newVal.id, newVal.product_id);
            },
            formVisible: function() {
                this.showSigns(this.item.id, this.item.product_id);
            }
        },
        methods: {
            givePoints(row) {
                this.formVisible = true
                this.showItem = {
                    member_id : row.member_id,
                    course_id : row.course_id,
                }
            },
            showSigns(course_id, product_id){
                this.signData = [];
                this.dialogLoading = true;
                this.$http.fetch("coursesign/getwithdeal",{search_course:course_id,search_product:product_id}).then((res) =>{
                    this.signData = res.data;
                    this.dialogLoading = false;
                });
            },
            handleClose() {
                this.$emit('update:visible', false)
            },
            //    handleDialogSelectionChange(val) {
            //     this.multipleDialogSelection = val;
            // },
            // handelDialogSelect(row) {
            //     return row.status != '已签到' ;
            // },
            handleSign(row) {
                this.$http.fetch("coursesign/allograph",{
                        course: this.item.id,
                        member: row.member_id
                    }).then((res) =>{
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.showSigns(this.item.id, this.item.product_id);
                            this.handledSign = true;
                        }
                });
            },
            // multipleSign() {
            //     if(this.multipleDialogSelection.length == 0 ) {
            //         this.$message.error('未选择'); return;
            //     }
            //     var ids = _.map(this.multipleDialogSelection, 'id');
            //     this.$http.fetch('coursesign/allograph',{"ids":ids}).then((res) => {
            //         if(res.status == 'success'){
            //             this.$message({ message: res.msg, type: 'success' });
            //             this.dialogFormVisible = false;
            //         }
            //     });
            // },
//            handleDel: function (row) {
//                this.$confirm('此操作将永久删除课时及签到记录！！！是否继续?', '提示', {
//                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
//                }).then(() => {
//                    this.$http.post('course/delete',{"id":row.id}).then((res) => {
//                        if(res.status == 'success'){
//                            this.$message({ message: '删除成功', type: 'success' });
//                               this.$refs.tableBuilder.getData();
//                        }
//                    });
//                }).catch(() => {
//                });
//            },
        }
    };
</script>