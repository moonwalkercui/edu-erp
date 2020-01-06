<template>
    <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="700px">
        <el-input
            type="textarea"
            :rows="10"
            placeholder="请设置内容"
            v-model="text"
        ></el-input>
        <span slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <!-- <el-button type="primary" @click="handleSubmit">上 传</el-button> -->
            <reqButton @handleReq = "handleSubmit" text="上 传"/>
        </span>
    </el-dialog>
</template>
<script>
export default {
  props: {
    visible: { default: false },
    title: { default: "标题" },
    item: { default: {
                type: '' // 这里面需要有个type
            } 
        }
  },
  data() {
    return {
      text: ""
    };
  },
  created() {},
  methods: {
    handleSubmit() {
      if (this.text == "") {
        this.$message.error("请输入内容");
      } else {
        this.$http.post("sys/valueupdate", {...this.item, value: this.text}).then(res => {
            if(res.status == 'success') {
                this.$message(res.msg)
                this.handleClose()
                this.$emit('handle-update-success')
            }
        });
      }
    },
    handleClose() {
      this.$emit("update:visible", false);
    }
  }
};
</script>
<style>
.modal-selector .el-dialog__body {
  padding: 0 10px;
}
</style>