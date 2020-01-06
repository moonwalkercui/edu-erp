<template>
    <section>
        <div class="line page-line"></div>
        <el-main>
        <el-container>
            <el-aside width="200px" style="padding-left: 20px;">
                <el-card :body-style="{ padding: '5px'}">
                    <img :src="ruleForm.avatar" class="avatar">
                    <div style="padding: 8px; line-height: 150%">
                        <span>老师账号:  {{ $route.params.uname }}</span><br>
                        <span style="color: #999;">签名:  <br>{{ruleForm.intro}}</span><br>
                    </div>
                </el-card>
                <!-- <el-card :body-style="{ padding: '0'}" style="margin-top: 6px" v-if="isMe==false">
                    <el-button icon="fa fa-commenting" type="text" style="width: 100%"> 发消息</el-button>
                </el-card> -->
                <el-card :body-style="{ padding: '0'}" style="margin-top: 6px">
                    <el-button icon="fa fa-pencil" type="text" style="width: 100%"  @click="$router.push({ path: '/user/edit/' + $route.params.uname})"> 修改资料</el-button>
                </el-card>
            </el-aside>
            <el-container>
                <el-form size="small" :model="ruleForm" ref="ruleForm" label-width="200px" class="page-form">
                    <el-form-item label="姓名" >{{ruleForm.name}}</el-form-item>
                    <!--<el-form-item label="门店" >{{ruleForm.division}}</el-form-item>-->
                    <!--<el-form-item label="部门" >{{ruleForm.department}}</el-form-item>-->
                    <el-form-item label="电话">{{ruleForm.phone}}</el-form-item>
                    <el-form-item label="Email">{{ruleForm.email}}</el-form-item>
                    <el-form-item label="QQ">{{ruleForm.qq}}</el-form-item>
                    <el-form-item label="性别">{{ruleForm.gender}}</el-form-item>
                    <el-form-item label="婚姻">{{ruleForm.married}}</el-form-item>
                    <el-form-item label="祖籍">{{ruleForm.native_place}}</el-form-item>
                    <el-form-item label="地址">{{ruleForm.address}}</el-form-item>
                    <el-form-item label="学历">{{ruleForm.education}}</el-form-item>
                    <el-form-item label="生日">{{ruleForm.birthday}}</el-form-item>
                    <el-form-item label="毕业学校">{{ruleForm.graduation_school}}</el-form-item>
                    <el-form-item label="毕业日期">{{ruleForm.graduation_date}}</el-form-item>
                    <el-form-item label="加入日期">{{ruleForm.created_at}}</el-form-item>
                </el-form>
            </el-container>
        </el-container>
        </el-main>
    </section>
</template>
<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #525fe1;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 165px;
        height: 165px;
        line-height: 165px!important;
        text-align: center;
    }
    .avatar {
        width: 167px;
        height: 167px;
        display: block;
    }
</style>

<script>
    import avatar from '@/assets/avatar_def.jpg'
    export default {
        data() {
            return {
                ruleForm: {},
                imageUrl: '',
                isMe: this.$cookie.fetchJson('_userInfo').uname == this.$route.params.uname
            };
        },
        created(){
            this.getData();
        },
        methods: {
            getData(){
                this.$http.fetch('user/profile',{"uname":this.$route.params.uname}).then((res) => {
                    this.ruleForm = {
                        name: res.data.name,
                        department: res.data.department_id,
                        phone: res.data.phone,
                        email: res.data.email,
                        intro: res.data.intro,
                        avatar: res.data.avatar || avatar,
                        address: res.data.address,
                        qq: res.data.qq,
                        education: res.data.education,
                        idcard_number: res.data.idcard_number,
                        gender: res.data.gender,
                        married: res.data.married,
                        birthday: new Date(res.data.birthday).format("MM-dd"),
                        graduation_school: res.data.graduation_school,
                        graduation_date: res.data.graduation_date,
                        native_place: res.data.native_place,
                        division: res.data.division_id,
                        created_at: new Date(res.data.created_at).format("yyyy-MM-dd"),
                    }
                });
            },
        }
    }
</script>
