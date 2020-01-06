<template>
    <section>
        <!-- <el-container> -->
            <!--<el-aside width="200px">-->
                <!--<el-main style="padding-top:0;">-->
                <!--<h4 style="padding: 7px 10px; margin: 0; color: #909399">组织结构</h4>-->
                <!--<el-tree :data="departmentWithChild" :expand-on-click-node="false" :default-expand-all="true" :props="{ children: '_child', label: 'name'}" @node-click="handleNodeClick"></el-tree>-->
                <!--</el-main>-->
            <!--</el-aside>-->
            <!-- <el-main style="padding-top:0;"> -->
                <el-form :inline="true" class="demo-form-inline" size="mini">
                    <helper key-id='userlist' float='right'/>
                    <el-form-item>
                        <el-button type="primary"  @click="showCreateForm" icon="el-icon-plus"  v-if="$util.hasPermission(200)">添加</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button v-if="$util.hasPermission(200)" @click="$exportExcel('user/table',urlParam)" icon="el-icon-download">导出</el-button>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="urlParam.search_user" placeholder="" style="width: 150px"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="Status">-->
                        <!--<select-status v-model="urlParam.search_status" statusName="user" :style="{width: '150px'}"></select-status>-->
                    <!--</el-form-item>-->
                    <el-form-item>
                        <el-button @click="$refs.tableBuilder.getData(1)">查询</el-button>
                    </el-form-item>
                </el-form>

                <table-builder
                        ref="tableBuilder"
                        dataUrl="user/table"
                        :fields="tableProps.fields"
                        :showIndex="true"
                        :condition="urlParam"
                        :actionButtons = "tableProps.tableActionButtons"
                        :actionWidth = "200"
                        :actionMultiButtons = "tableProps.tableActionMultiButtons"
                        :tableRowClassName = "tableRowClassName"
                ></table-builder>

            <!-- </el-main>
        </el-container> -->
        <el-dialog title="设置权限" :visible.sync="editRoleVisible" width="700px">
            <el-alert title="" type="info" style="margin-bottom: 20px; font-weight: 100;">
                <el-button type="text" @click="$router.push('/rbac/roles')" style="display: inline-block;">如需编辑角色权限, 请点击这里.</el-button>
                </el-alert>
            <el-form>
                <el-form-item label="设置权限角色" label-width="120px" prop="name">
                    <el-select v-model="userRoles" multiple placeholder="请选择" style="width: 400px" v-loading="loadingUserRoles">
                        <el-option v-for="item in orgRoles" :key="item.id" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editRoleVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitPermission" v-if="$util.hasPermission(117)">保 存</el-button>
            </div>
        </el-dialog>

         <el-dialog title="添加账号" :visible.sync="editDialogVisible" width="700px">
          <br>
          <el-form size="medium" label-width="120px" :model="newUser" :rules="rules">
            <el-form-item label="姓 名" prop="name">
              <el-input v-model="newUser.name" placeholder class="dialog-form-item"></el-input>
            </el-form-item>
            <el-form-item label="账 号" prop="username">
              <el-input v-model="newUser.username" placeholder class="dialog-form-item"></el-input>
            </el-form-item>
            <el-form-item label="密 码" prop="password">
              <el-input v-model="newUser.password" placeholder class="dialog-form-item"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleCreate">保 存</el-button>
          </div>
        </el-dialog>

    </section>
</template>
<style>
    .el-table tr.is-stop {
        background: #f4f4f5;
    }
</style>
<script>
    import {map} from 'lodash'
    import TableBuilder from '@/components/TableBuilder'
    // import selectStatus from '@/components/SelectStatus'
    import img from '@/assets/img_def.jpg';
    export default {
        components: { TableBuilder },
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :' ' , width:'70' ,
                            content:(row)=>{ return ( <img src={row.avatar||img} class="image" style={{maxWidth: '50px' , maxHeight: '50px'}} />) }
                        },
                        {title :'姓名' , content:(row)=>{
                                return ( <span>
                                <i class="fa" class={{faMale : row.gender == '男', faFemale : row.gender == '女' }} style={{ color: row.gender == '女' ? '#F56C6C' : '#525fe1' }}></i>
                                {row.name}
                                </span>)
                            }
                        },
                        {title :'账号' , name:"username" },
                        {title :'邮箱' , name:"email" },
                        {title :'管理员' , name:"is_manager" },
                        {title :'状态' , name:"status", width:80 },
                        //{title :'到课率' , content:(row)=>{
                            //return (<el-progress textInside={true} strokeWidth="18" color="#DCDFE6" percentage="70"></el-progress>)
                            //}
                        //},
                    ],
                    tableActionButtons:[
                        {title:'编辑', pm:15, icon:"el-icon-edit" , click:(row)=>{ this.$router.push({ path: '/user/edit/' + row.username}) } },
                        {title:'改密', pm:201, icon:"el-icon-edit-outline" , click:(row)=>{ this.changePw(row.username) } },
                        {title:'权限', pm:116 , icon:"el-icon-document" , click:(row)=>{this.handleEditRole(row) } },
                    ],
                },
                departments:[],
                departmentWithChild: [],
                editData: {},
                userRoles: [],
                orgRoles:[],
                urlParam : {
                    search_user : '', // 搜索user名
                    search_department : '', // 搜索部门
                    search_status:'',
                    search_division : '',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },
                newUser : {
                    useranme : '', 
                    password : '',
                    name:'',
                },
                rules: {
                  title: [{ required: true, message: "请输入标题", trigger: "blur" }],
                  position: [{ required: true, message: "请选择位置", trigger: "blur" }],
                  image: [{ required: true, message: "请上传图片", trigger: "blur" }],
                },
                // 编辑提示框
                editRoleVisible: false,
                editDialogVisible: false,
                loadingUserRoles: false
            }
        },

        mounted(){
            this.getDeparments();
            this.getDeparmentsWithChild();
            this.getOrgRoles();
        },
        methods: {
          changePw(uname) {
            this.$prompt('请输入新密码', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
            }).then(({ value }) => {
              this.$http.post("user/changepw",{username:uname, password:value}).then((res) =>{
                if(res.status=="success"){
                  this.$message({
                    type: 'success',
                    message: '密码已修改为: ' + value
                  });
                }
              });
            }).catch(() => {
            });
          },
            handleEditRole: function ( row) {
                this.editRoleVisible = true;
                this.loadingUserRoles = true;
                this.editData = row;
                this.$http.fetch("rbac/finduserroles",{"username":row.username}).then((res) =>{
                    this.userRoles = map(res.data, 'id');
                    this.loadingUserRoles = false;
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
                this.$refs.tableBuilder.getData();
            },
            submitPermission() {
                var update = () => {
                    this.$http.post('rbac/edituserroles',{"username":this.editData.username,"roles":this.userRoles}).then((res) => {
                        if(typeof res.status != "undefined" && res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.editRoleVisible = false;
                        }
                    });
                };
                if((this.userRoles.length == 0)){
                    this.$confirm('确认设置为空权限？', '提示', {
                        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                    }).then(() => {
                        update();
                    }).catch(() => {
                    });
                }else{
                    update();
                }
//                this.$http.post('rbac/editUserRoles',{"username":this.editData.username,"roles":this.userRoles}).then((res) => {
//                    if(typeof res.status != "undefined" && res.status == 'success'){
//                        this.$message({ message: res.msg, type: 'success' });
//                        this.editData = {};
//                    }
//                });
            },
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '离职') {
                    return 'is-stop';
                }else{
                    return '';
                }
            },
            showCreateForm() {
              this.newUser = {
                useranme : '', 
                password : '',
                name:'',
              };
              this.editDialogVisible = true;
            },
            handleCreate() {
              this.$http.post("user/create", this.newUser).then(res => {
                if (typeof res.status != "undefined" && res.status == "success") {
                  this.$message({ message: res.msg, type: "success" });
                  this.$refs.tableBuilder.getData();
                  this.editDialogVisible = false;
                  this.dataFormInit();
                }
              });
            }
        }
    }
</script>