<template>
    <section>
        <div class="line page-line"></div>
        <el-main>
        <el-container>
            <el-aside width="200px" style="padding-left: 20px;">
                <el-card :body-style="{ padding: '5px'}">
                    <image-uploader
                        :image-url="ruleForm.avatar"
                        @upload="handleUploadedImage"
                        :cropWidth="400"
                        :cropHeight="400"
                        :autoCrop="true"
                    ></image-uploader>
                    <div style="padding: 8px; line-height: 150%">
                        <span>老师账号:  {{ $route.params.uname }}</span><br>
                        <span style="color: #999;">加入时间: {{ ruleForm.created_at }}</span>
                    </div>
                </el-card>

            </el-aside>
            <el-container>
                <el-form size="medium" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="page-form">
                    <el-form-item label="姓名" prop="name"><el-input v-model="ruleForm.name"></el-input></el-form-item>
                    <!-- <el-form-item label="所属门店" prop="division">
                        {{ ruleForm.division }}
                    </el-form-item> -->
                    <!-- <el-form-item label="所属部门" prop="department">
                        {{ ruleForm.department }}
                    </el-form-item> -->
                    <el-form-item label="电话" prop="phone"><el-input v-model="ruleForm.phone"></el-input></el-form-item>
                    <el-form-item label="Email" prop="email"><el-input v-model="ruleForm.email"></el-input></el-form-item>
                    <el-form-item label="简介"><el-input type="textarea" :rows="4" placeholder="请输入内容" v-model="ruleForm.intro"></el-input></el-form-item>
                    <el-form-item label="排序权重" prop="sort"><el-input type="number" v-model="ruleForm.sort" placeholder="数组越大越靠前"></el-input></el-form-item>
                    <!-- <el-form-item label="设置管理员">
                        <el-radio-group v-model="ruleForm.is_manager">
                            <el-radio label="否"></el-radio>
                            <el-radio label="是"></el-radio>
                        </el-radio-group>
                    </el-form-item> -->
                    <el-form-item label="状态">
                        <el-radio-group v-model="ruleForm.status">
                            <el-radio label="正常"></el-radio>
                            <el-radio label="停用"></el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-collapse>
                        <el-collapse-item>
                            <template slot="title">
                               <div style="color: #999">更多资料</div>
                            </template>
                            <el-form-item label="性别">
                                <el-radio-group v-model="ruleForm.sex">
                                    <el-radio label="男"></el-radio>
                                    <el-radio label="女"></el-radio>
                                    <el-radio label="未知"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="婚姻状况">
                                <el-radio-group v-model="ruleForm.married">
                                    <el-radio label="未婚"></el-radio>
                                    <el-radio label="已婚"></el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="祖籍"><el-input v-model="ruleForm.native_place"></el-input></el-form-item>
                            <el-form-item label="联系地址"><el-input v-model="ruleForm.address"></el-input></el-form-item>
                            <el-form-item label="QQ号"><el-input v-model="ruleForm.qq"></el-input></el-form-item>
                            <el-form-item label="学历"><el-input v-model="ruleForm.education"></el-input></el-form-item>
                            <el-form-item label="身份证号码"><el-input v-model="ruleForm.idcard_number"></el-input></el-form-item>
                            <el-form-item label="出生日期"><el-date-picker v-model="ruleForm.birthday" type="date" value-format="yyyy-MM-dd"></el-date-picker></el-form-item>
                            <el-form-item label="毕业学校"><el-input v-model="ruleForm.graduation_school"></el-input></el-form-item>
                            <el-form-item label="毕业日期"><el-date-picker v-model="ruleForm.graduation_date" type="date" value-format="yyyy-MM-dd"></el-date-picker></el-form-item>

                        </el-collapse-item>
                    </el-collapse>
                    <br>
                    <!-- <el-form-item><el-button type="primary" @click="submitForm('ruleForm')">保 存</el-button></el-form-item> -->
                    <reqButton @handleReq = "submitForm('ruleForm')"/>
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
        width: 165px;
        height: 165px;
        display: block;
    }
</style>
<script>
    import imageUploader from "@/components/ImageUploader";
    export default {
        components: { imageUploader }, //imageUploader
        data() {
            return {
                ruleForm: {
                    uname: this.$route.params.uname,
                    name: '',
                    department: '',
                    division: '',
                    phone: '',
                    email: '',
                    intro: '',
                    sort: 0,
                    avatar: '',
                    address: '',
                    qq: '',
                    education: '',
                    idcard_number: '',
                    gender: '',
                    married: '',
                    birthday: '',
                    graduation_school: '',
                    graduation_date: '',
                    native_place: '',
                    // is_manager: '否',
                    status: '正常',
                },
                imageUrl: '',
                rules: {
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                        {max: 50, message: '字数过多', trigger: 'blur'}
                    ],
                    email: [{required: false, type: 'email', message: '请输入正确的邮箱', trigger: 'blur'}],
                }
            };
        },
     
        created(){
            this.getData();
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post('user/edit',this.ruleForm).then((res) =>{
                            if(res.status == 'success'){
                                this.$message({ message: res.msg, type: 'success' });
                            }
                        });
                    } else {
                        this.$message.error('更新失败!');
                        return false;
                    }
                });
            },
            getData(){
                this.$http.fetch('user/profile',{"uname":this.$route.params.uname}).then((res) => {
                    this.ruleForm.name = res.data.name;
                    this.ruleForm.phone = res.data.phone;
                    this.ruleForm.email = res.data.email;
                    this.ruleForm.intro = res.data.intro;
                    this.ruleForm.sort = res.data.sort;
                    this.ruleForm.avatar = res.data.avatar;
                    this.ruleForm.address = res.data.address;
                    this.ruleForm.qq = res.data.qq;
                    this.ruleForm.education = res.data.education;
                    this.ruleForm.idcard_number = res.data.idcard_number;
                    this.ruleForm.gender = res.data.gender;
                    this.ruleForm.married = res.data.married;
                    this.ruleForm.birthday = res.data.birthday;
                    this.ruleForm.graduation_school = res.data.graduation_school;
                    this.ruleForm.graduation_date = res.data.graduation_date;
                    this.ruleForm.native_place = res.data.native_place;
                    // this.ruleForm.is_manager = res.data.is_manager;
                    this.ruleForm.status = res.data.status;
                    this.ruleForm.created_at = new Date(res.data.created_at).format("yyyy-MM-dd");
                    this.ruleForm.department = res.data.department ? res.data.department.department_name : '无';
                    this.ruleForm.division =  res.data.division ? res.data.division.name : '无';
                    this.isUpdate = true;
                });
            },

            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            handleDel: function () {
                this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleUploadedImage(image) {
                this.ruleForm.avatar = image;
            }
        }
    }
</script>
