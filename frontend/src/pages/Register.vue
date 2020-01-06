<template>
  <el-card class="login-box" >
    <div slot="header" class="clearfix" >
      <span>用户注册</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="login">登陆</el-button>
    </div>
    <div class="text item">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="手机号:" prop="username">
          <el-input type="text" v-model="ruleForm.username" ></el-input>
        </el-form-item>

        <el-form-item label="密 码:" prop="password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码:" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
        </el-form-item>
          <el-form-item label="姓名:" prop="nickname">
            <el-input type="text" v-model="ruleForm.nickname" placeholder="请输入姓名(可修改)" ></el-input>
          </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <!--<el-button @click="resetForm('ruleForm')">重置</el-button>-->
        </el-form-item>
      </el-form>
  </div>
  </el-card>

</template>
<script type="text/ecmascript-6">
  export default {
    data() {
      var validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账号'));
        } else {
          callback();
        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.password !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          username: '',
          password: '',
          checkPass: '',
          nickname: ''
        },
        rules: {
          username: [
            { validator: validateUsername, trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {

            this.$http.post('auth/register',this.ruleForm).then(res => {
                if(res.status=='success') {
                  this.$message.success(res.msg);
                  this.$router.push({path: '/login'});
                }else{
                  if(res.msg.username[0] != undefined && res.msg.username[0]) this.$message.error(res.msg.username[0]);
                  if(res.msg.password[0] != undefined && res.msg.password[0]) this.$message.error(res.msg.password[0]);
                }
              });
          } else {
            this.$message.error('输入信息有误');
            return false;
          }
      });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      login(){
        this.$router.push({ path: 'login' })
      }
    }
  }
</script>
<style>
  .login-box {
    width: 480px;
    margin: 50px auto;
  }
</style>
