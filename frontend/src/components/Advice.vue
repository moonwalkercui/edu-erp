<template>
  <el-dialog title="使用反馈" :visible.sync="visible" width="700px" :before-close="handleClose" center>
    <el-form size="medium" label-width="80px" :model="dataForm" :rules="rules" ref="dataForm">
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" placeholder="请描述反馈内容" :rows="6" v-model="dataForm.content" style="width:90%;"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <!-- <el-button type="primary" @click="handleSave('dataForm')">提 交</el-button> -->
      <reqButton @handleReq = "handleSave('dataForm')"/>
    </div>
  </el-dialog>
</template>
<script>
import axios from "axios";
export default {
  props: {
    visible: { required: false }
  },
  data() {
    return {
      dataForm: {
        content: "",
      },
      rules: {
        content: [{ required: true, message: "请输入内容", trigger: "blur" }]
      }
    };
  },
  created() {},
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