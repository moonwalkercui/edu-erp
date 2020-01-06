<template>
  <el-dialog title="本校专属小程序码" :visible.sync="visible" width="600px" :before-close="handleClose" center>
    <div style="text-align:center;">
      <img :src.sync="logoImg" style="width:400px;"/>
      <div>{{info}}</div>
    </div>
  </el-dialog>
</template>
<script>
import axios from "axios";
import logo from "@/assets/logo-gray.jpg"
export default {
  props: {
    visible: { required: false }
  },
  data() {
    return {
      logoImg: logo,
      info: '微信扫码登录小猿管家小程序',
      dataForm: {
        content: "",
      },
      rules: {
        content: [{ required: true, message: "请输入内容", trigger: "blur" }]
      }
    };
  },
  created() {
    this.$http.fetch('sys/qrcode').then(res => {
      this.logoImg = res.data.url;
      this.info = res.data.info || this.info;
    });
  },
  methods: {
    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let info = this.$cookie.fetchJson("_userInfo")
          axios.post(this.serviceUrl + "advice",{content: this.dataForm.content, info: info.uname + '|' + info.name}).then(() => {
            this.$message.success("谢谢反馈!");
            this.dataForm.content = ''
            this.handleClose()
          });
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    },
    handleClose() {
      this.$emit('update:visible', false)
    }
  }
};
</script>