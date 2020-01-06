<template>
    <div class="box">
        <div class="box-child">
            <h2><i class="el-icon-refresh"></i> 切换组织</h2>
            <ul style="text-align: left">
                <li>请从以下列表中选择要切换的组织</li>
                <li>加入或创建组织请<el-button type="text" @click="$router.push('/service/createOrJoin')">点击这里</el-button></li>
            </ul>
            <el-card class="box-card" style="margin: 20px 0;">
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="code" label="编码" ></el-table-column>
                    <el-table-column prop="name" label="名称" >
                        <template slot-scope="scope">
                            <h4>{{scope.row.name}}</h4>
                        </template>
                    </el-table-column>
                    <el-table-column label="" width="120">
                        <template slot-scope="scope">
                            <el-button type="success" style="padding: 2px 6px;" icon="el-icon-refresh" @click="handleChange(scope.row.code)" v-if="$cookie.fetchJson('_userInfo').ocode != scope.row.code"> 切换 </el-button>
                            <span v-else>已登陆</span>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </div>
    </div>
</template>
<style scpoed>
    .box{
        height: 60%;
        width: 96%;
        display: flex;
        justify-content: center;/*实现水平居中*/
        /*align-items:center; !*实现垂直居中*!*/
    }
    .box-child
    {
        margin-top: 20px;
        width: 700px;
        /*height: 150px;*/
        /*background-color: #00aaee;*/
    }
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }
    .box-card {
    }
    .el-table tr:last-child td{
        border-bottom: #fff;
    }
</style>
<script>
    export default {
        data() {
            return {
                tableData: [],
            };
        },
        created(){
            this.$http.fetch('normal/getuserorgs').then((res) => {
                this.tableData = res.data;
            });
        },
        methods: {
            handleChange(code){
                this.$http.post('normal/switchover',{code : code}).then(() => {
                    this.$http.fetch('auth/user').then((res) => {
                        this.$cookie.setJson('_userInfo', res.data);
                        if(this.$cookie.fetchJson('_userInfo'))
                            this.$router.push({path: '/i/dashboard'});
                        else
                            this.$message.error('获取用户信息失败');
                    });
                });
            }
        }
    }
</script>


