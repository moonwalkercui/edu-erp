<template>
   <el-dialog title="更换业务员" :visible.sync="visible" width="700px" label-width="120px" >
      <helper key-id='changemembersalesman' float='right'/>
    <br>
    <el-form :model="formData">
        <el-form-item label="选择业务员" label-width="200px">
            <select-builder table="user" v-model="formData.salesman" :style="{width: '300px'}" size="middle"></select-builder>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <reqButton @handleReq = "handleSave" />
    </div>
</el-dialog>
</template>
<script>
import selectBuilder from '@/components/SelectBuilder';
export default {
components: { selectBuilder },
  props: {
    visible: { default: false },
    customers: {
      type: Array
    },
  },
  data() {
    return {
      formData: {
        salesman: null
      }
    };
  },
  methods: {
    handleSave() {
        this.$http.post("member/changesalesman", { salesman: this.formData.salesman, customers: this.customers }).then(res => {
            if (typeof res.status != "undefined" && res.status == "success") {
                this.handleClose();
                this.$parent.$refs.tableBuilder.getData();
            }
            this.$message.success(res.msg);
        });
    },
    handleClose() {
      this.$emit("update:visible", false);
    }
  }
};
</script>