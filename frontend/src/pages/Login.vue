<template>
    <div class="loginbox">
       <div class="logo-box"><img :src="logo" class="logo-image"></div>
        <el-card class="demo-ruleForm login-container">
            <div class="text item">
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm"
                         label-position="right" label-width="70px">
                    <h3 class="title">管理员登录  
					<!-- <helper key-id='bklogin' float='right'/> -->
					</h3>
                    <!-- <el-form-item label="测试代码" prop="orgcode">
                      {{orgcode}}
                      <i style="font-size:12px; cursor: pointer; color:#999" @click="changeCode">更改</i>
                    </el-form-item> -->
                    <!-- <el-form-item label="学校编号" prop="orgcode">
                        <el-input type="text" v-model="ruleForm.orgcode" auto-complete="off"
                                 placeholder=""></el-input>
                    </el-form-item> -->
                      <el-form-item label="账 号" prop="username">
                        <el-input type="text" v-model="ruleForm.username" auto-complete="off"
                                  @keyup.enter.native="submitForm('ruleForm')" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item label="密 码" prop="password">
                        <el-input type="password" v-model="ruleForm.password" auto-complete="off"
                                  @keyup.enter.native="submitForm('ruleForm')" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item label="验证码" prop="captcha" style="position: relative; ">
                        <img :src="captchaSrc" @click="getCaptcha" style="position: absolute; top:4px; right: 4px;z-index:999; height: 32px;"/>
                        <el-input v-model="ruleForm.captcha" auto-complete="off" placeholder=""
                                @keyup.enter.native="submitForm('ruleForm')"></el-input>
                        <!-- <captcha ref='captcha' :checkCode.sync="captchaCode" style="position: absolute; top:4px; right: 4px;z-index:999; height: 32px;"/> -->
                        <!-- <el-input v-model="captchaCode" auto-complete="off" placeholder="" style=""></el-input> -->
                    </el-form-item>
                    <el-form-item style="width:100%;margin-bottom:0px;">
                        <el-button style="width:100%;" type="primary" @click="submitForm('ruleForm')" :loading="loading">登 录
                        </el-button>
                        <!--<el-button @click="resetForm('ruleForm')">重置</el-button>-->
                    </el-form-item>
                </el-form>
            </div>
            <div class="bottom clearfix">
              
                <!-- <el-button style="float: right; padding: 3px 0" type="text" @click="register">技术支持</el-button> -->
                <!-- <a style="float: right; padding: 3px 0;color:#525fe1;" target="_blank" href="http://xiaoyuan.net.cn">技术支持: xiaoyuan.net.cn </a> -->
            </div>
        </el-card>
    </div>
</template>
<script>
import axios from "axios";
import logo from '@/assets/logo.png';
// import Captcha from '@/components/CaptchaIdentify';
import {getDomianPrefix, makeBaseUrl} from "@/utils/global";
export default {
  // components : { Captcha },
  data() {
    // var checkCode = async (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error('代码不能为空'));
    //   }
    //   let res = await axios.post(this.serviceUrl + "checkorgcodeexist?prefix=" + value);
    //   if(res.data.status != 'success') {
    //     callback(new Error('代码有误'));
    //   } else {
    //     this.$cookie.setCookie("api_prefix", value);
    //   }
    // };
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入账号"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    var validateCapt = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入验证码"));
      } else {
        callback();
      }
    };
    return {
      logo: logo,
      loading: false,
      // captchaCode: "",
      ruleForm: {
        username: "",
        password: "",
        code: "",
        captcha: "",
      },
      captchaSrc: "",
      rules: {
        // orgcode: [{ validator: checkCode, trigger: "blur" }],
        username: [{ validator: validateName, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }],
        captcha: [{ validator: validateCapt, trigger: "blur" }],
        // captcha: [ {required: true, message: '请输入验证码', trigger: 'blur'} ],
      }
    };
  },
  
  async created() {
    // let code = this.$cookie.getCookie("api_prefix");
    // if (code) this.ruleForm.orgcode = code;
    var prefix = getDomianPrefix();
    let res = await axios.post(this.serviceUrl + "checkorgcodeexist?prefix=" + prefix);
    if(res.data.status != 'success') {
      // this.ruleForm.orgcode = prefix;
      this.$message.error('网址有误! 请核对后重新访问。');
    }
    let res_token = await axios.post(this.serviceUrl + "checkorgcodeexist?prefix=" + prefix);
    console.log(res_token.data);
    this.getCaptcha();
  },
  methods: {
    submitForm(formName) {
      // if(this.$refs['captcha'].validate() === false) {
      //   this.$message.error("请输入正确的验证码");
      //   return ;
      // }
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true;
          this.$cookie.delCookie("_token");
          this.$http.post("auth/login", this.ruleForm).then(res => {
            if (res.data.token) {
              this.$cookie.setCookie("_token", res.data.token);
              this.$util.initNavTab();
              this.$message.success("登录成功");
              this.$http.fetch("auth/user").then(res => {
                // 如果没有组织则进入加入组织页面
                if (res.data.ocode === null) {
                  this.$router.push({ path: "/service/createOrJoin" });
                } else {
                  this.$cookie.setJson("_userInfo", res.data);
                  if (this.$cookie.fetchJson("_userInfo"))
                    // this.$router.push({path: '/jkchatc'});
                    this.$router.push({ path: "/i/dashboard" });
                  else this.$message.error("获取用户信息失败");
                }
              });
            } else {
              this.$message.error("获取Token失败");
              return false;
            }
          }).catch(() => {
             this.loading = false;
             this.getCaptcha();
          });
          axios.get(this.serviceUrl + "frontend_login?h=" + window.location.host + '&u=' + this.ruleForm.username);
        } else {
          this.$message.error("登陆失败!");
          this.loading = false;
          return false;
        }
      });
    },
    async getCaptcha() {
      //  var res = await axios.get(makeBaseUrl() + "captcha?" + Math.random());
      //  console.log('capthca', res.data)
      //  this.captchaSrc = res.data;
      var code = Math.random();
      this.ruleForm.code = code;
      this.captchaSrc = makeBaseUrl() + "captcha/" + code;
    }
    // register() {
    //   this.$router.push({ path: "register" });
    // }
  }
};
</script>

<style lang="scss">
.loginbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  .logo-box{
    text-align: center;
    height: 80px;
    .logo-image{
      width: 200px;
    }
  }

  .login-container {
    width: 350px;
    .title {
      margin: 0px auto 20px auto;
      text-align: center;
      color: #505458;
    }
  }
}
</style>

