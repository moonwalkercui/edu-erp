<template>
    <el-dialog title="给学员加课" :visible.sync="visible" width="700px" :before-close="handleClose"
      :close-on-click-modal="true">
      <br>
      <helper key-id='addmemberproduct' float='right'/>
      <br>
      <el-form size="medium" label-width="120px" ref="editData">
        <el-form-item label="学员">
          <select-builder table="member" v-model="editData.member_id" ></select-builder>
        </el-form-item>
        <el-form-item label="课程">
          <select-builder table="product" v-model="editData.product_id" ></select-builder>
        </el-form-item>
        <el-form-item label="总课时">
          <el-input type="number" v-model="editData.total_quantity" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="剩余课时">
          <el-input type="number" v-model="editData.remaining_quantity" class="dialog-form-item"></el-input>
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
  },
  data() {
    return {
      editData: {
        member_id: null,
        product_id: null,
        total_quantity: 0,
        remaining_quantity: 0,
      }
    };
  },
  methods: {
   handleSave() {
      this.$http.post("member/productquantityadd", {
       member: this.editData.member_id,
       product: this.editData.product_id,
       total: this.editData.total_quantity,
       remaining: this.editData.remaining_quantity,
     }).then(res => {
        if (typeof res.status != "undefined" && res.status == "success") {
          this.handleClose();
          this.$parent.$refs.tableBuilder.getData(1);
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