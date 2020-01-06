<template>
    <el-dialog title="调整课时" :visible.sync="visible" width="700px" :before-close="handleClose"
      :close-on-click-modal="true">
      <br>
      <helper key-id='editmemberproduct' float='right'/>
      <br>
      <el-form size="medium" label-width="120px" ref="editData">
        <el-form-item label="学员">
          {{editData.member.nick_name}}
        </el-form-item>
        <el-form-item label="课程">
          {{editData.product.name}}
        </el-form-item>
        <el-form-item label="总课时">
          <el-input v-model="editData.total_quantity" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="剩余课时">
          <el-input v-model="editData.remaining_quantity" class="dialog-form-item"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <reqButton @handleReq = "handleSave" />
      </div>
    </el-dialog>
</template>
<script>
export default {
  props: {
    visible: { default: false },
    item: {
      type: Object
    }
  },
  data() {
    return {
      editData: {
        member: {},
        product: {},
        total_quantity: 0,
        remaining_quantity: 0,
      }
    };
  },
  watch: {
    item: function(newVal) {
      this.editData = newVal;
    },
  },
  methods: {
   handleSave() {
      this.$http.post("member/productquantityedit", {
       member: this.editData.member_id,
       product: this.editData.product_id,
       total: this.editData.total_quantity,
       remaining: this.editData.remaining_quantity,
     }).then(res => {
        if (typeof res.status != "undefined" && res.status == "success") {
          this.handleClose();
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