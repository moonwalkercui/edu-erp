<template>
    <section>
        <el-container>
            <!-- <el-aside width="200px">
                <el-main style="padding-top:0;">
                <h4 style="padding: 7px 10px; margin: 0; color: #909399">组织结构</h4>
                <el-tree :data="departmentWithChild" :default-expand-all="true" :props="{ children: '_child', label: 'name'}" @node-click="handleNodeClick"></el-tree>
                </el-main>
            </el-aside> -->
            <el-container>
                <el-main style="padding-top:0; max-width: 1300px">
                    <el-row :gutter="12">
                        <el-col :sm="12" :md="12" :lg="8" :xl="6" v-for="(item , index) in data" :key="index" :label="item" style="margin-bottom: 12px;">
                            <el-card :body-style="{ padding: '0px' }" shadow="hover">
                                <div style="padding: 15px 10px; text-align: center">
                                    <div style="display:inline-block; text-align: center; width: 35%; margin-right: 5px; vertical-align: top">
                                        <img :src="item.avatar||avatar" class="image-avatar">
                                    </div>
                                    <div style="display:inline-block;line-height: 180% ; text-align: left; width: 60%">

                                        <div style="font-size: 120%; font-weight: bold; margin-bottom: 5px; ">
                                            <div style="float: right">
                                                <!-- <el-tooltip class="item" effect="dark" content="发消息" placement="top">
                                                    <el-button type="text" icon="fa fa-commenting" style=" padding:3px; margin:0;" ></el-button>
                                                </el-tooltip> -->
                                                <el-tooltip class="item" effect="dark" content="资料" placement="top" v-if="$util.hasPermission(13)">
                                                    <el-button type="text" icon="fa fa-info-circle" style=" padding:3px; margin:0;" @click="$router.push({ path: '/user/view/' + item.username})"></el-button>
                                                </el-tooltip>
                                            </div>
                                            {{item.name}}
                                            <i class="fa"
                                               :class="{'fa-male' : item.gender == '男', 'fa-female' : item.gender == '女' }"
                                               :style="{ color: item.gender == '女' ? '#F56C6C' : '#525fe1'}"></i>

                                        </div>
                                        <div style="white-space:nowrap; color: gray"><i class="fa fa-phone-square"></i> {{item.username}}</div>
                                        <div style="white-space:nowrap; color: gray"><i class="fa fa-envelope-square"></i> {{item.email}}</div>
                                    </div>
                                </div>
                                <!--<div style="padding:0 10px 10px 10px; text-align: center">-->
                                    <!--<el-button icon="fa fa-commenting" size="mini" circle></el-button>-->
                                    <!--<el-button icon="fa fa-info-circle" size="mini" circle @click="$router.push({ path: '/user/view/' + item.username})"></el-button>-->
                                <!--</div>-->
                            </el-card>
                        </el-col>
                    </el-row>
                    <el-row class="row-bg">
                        <el-col :span="24" style="text-align: right">
                            <el-pagination background @current-change="handleCurrentChange" :current-page.sync="urlParam.page"
                                           :page-size="pagePer" :total="pageTotal" layout="prev, pager, next, total"></el-pagination>
                        </el-col>
                    </el-row>
                </el-main>
            </el-container>
        </el-container>
    </section>
</template>
<style>
    .image-avatar{
        border-radius : 50%;
        width: 90%;
        max-width: 64px;
    }
</style>
<script>
    // import selectStatus from '@/components/SelectStatus'
    import avatar from '@/assets/avatar_def.jpg'
    export default {
        data() {
            return {
                data: [],
                avatar: avatar,
                departments:[],
                // departmentWithChild: [],
                editData: {},
                userRoles: [],
                orgRoles:[],
                urlParam : {
                    page : 1, // 当前页码
                    search_user : '', // 搜索user名
                    search_department : '', // 搜索部门
                    search_status:'1',
                    search_division : '',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },
                pageTotal : 0 , // 总共列表数据数量
                pagePer : 20 , // 每页显示数据数量
                loading: false ,
                firstOpen: true, // 有页码的页码会发送两次请求，第一次过滤掉。
                // 编辑提示框
                editDialogVisible: false,
                loadingUserRoles: false
            }
        },
        // components: { selectStatus },
        mounted(){
            // this.getDeparments();
            // this.getDeparmentsWithChild();
            this.getDataList();
            this.getOrgRoles();
        },
        methods: {
            onSearchSubmit() {
                this.getDataList();
            },
            handleCurrentChange(val) {
                this.urlParam.page = val;
                this.firstOpen || this.getDataList(); // 有页码的页码会发送两次请求，第一次过滤掉。
            },
            getDataList() {
                this.loading = true;
                this.$http.fetch("user/table",this.urlParam).then((res) =>{
                    this.data = res.data;
                    this.urlParam.page = res.meta.current_page;
                    this.pageTotal = res.meta.total;
                    this.loading = false;
                    this.firstOpen = false
                });
            },
            getOrgRoles() {
                this.$http.fetch("rbac/orgroles",this.urlParam).then((res) =>{
                    this.orgRoles = res.data;
                });
            },
            getDeparments() {
                this.$http.fetch("depart/table").then((res) =>{
                    this.departments = res.data;
                });
            },
            getDeparmentsWithChild() {
                this.$http.fetch("depart/tablewithdivision").then((res) =>{
                    this.departmentWithChild = res.data;
                });
            },
            handleNodeClick(data) {
                if(data.is_division == true) this.urlParam.search_division = data.id;
                else this.urlParam.search_department = data.id;
                this.getDataList();
            },
            submitPermission() {
                var update = () => {
                    this.$http.post('rbac/edituserroles',{"username":this.editData.username,"roles":this.userRoles}).then((res) => {
                        if(typeof res.status != "undefined" && res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.editDialogVisible = false;
                        }
                    });
                };
                if((this.userRoles.length == 0)){
                    this.$confirm('确认不赋予用户任何自定义角色？', '提示', {
                        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                    }).then(() => {
                        update();
                    }).catch(() => {
                    });
                }else{
                    update();
                }
            },
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '离职') {
                    return 'is-stop';
                }
                return '';
            },
        }
    }
</script>