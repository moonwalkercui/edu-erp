<template>
    <el-dialog title="线下收款" :visible.sync="visible" width="700px" :before-close="handleClose">
        <br>
        <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
            <el-form-item label="编号" prop="sn">
                <label v-if="dataForm.sn">{{dataForm.sn}}</label>
                <el-input v-model="dataForm.sn" class='dialog-form-item' v-else></el-input>
            </el-form-item>
            <el-form-item label="收款金额" prop="money">
                <label v-if="dataForm.money">{{dataForm.money}}</label>
                <el-input v-model="dataForm.money" class='dialog-form-item' v-else></el-input>
            </el-form-item>
            <el-form-item label="交款人姓名" prop="name">
                <el-input v-model="dataForm.name" class='dialog-form-item'></el-input>
            </el-form-item>
            <el-form-item label="交款人手机号" prop="mobile">
                <el-input type='number' v-model="dataForm.mobile" class='dialog-form-item'></el-input>
            </el-form-item>
            <el-form-item label="付款方式" prop="mode">
                <select-status v-model="dataForm.mode" statusName="payment" size="medium"></select-status>
            </el-form-item>
            <el-form-item label="说明" prop="remark">
                <el-input v-model="dataForm.remark"  type="textarea" :rows="3" class='dialog-form-item'></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <!-- <el-button type="primary" @click="handleSave('dataForm')">确认收款</el-button> -->
             <reqButton @handleReq = "handleSave('dataForm')" text="确认收款"/>
        </div>
    </el-dialog>
</template>
<script>
import selectStatus from "@/components/SelectStatus";
export default {
  components: { selectStatus },
  props: {
    visible: { default: false },
    info: {}
  },
  data() {
    return {
      dataForm: {
        sn: "",
        money: "",
        name: "",
        mobile: "",
        mode: "",
        remark: ""
      },
      rules: {
        sn: [{ required: true, message: "缺少收款编号", trigger: "blur" }],
        money: [{ required: true, message: "请输入金额", trigger: "blur" }],
        mode: [{ required: true, message: "请选择收款方式", trigger: "blur" }],
        name: [
          { required: true, message: "请输入交款人姓名", trigger: "blur" }
        ],
        mobile: [
          { required: true, message: "请输入交款人手机号", trigger: "blur" }
        ]
      }
    };
  },
  watch: {
    info: function(val) {
      this.dataForm.sn = val.sn;
      this.dataForm.money = val.money_receivable;
      this.dataForm.name = val.member.name;
      this.dataForm.mobile = val.member.mobile;
      this.dataForm.mode = '';
      this.dataForm.remark = '';
    }
  },
  methods: {
    handleClose() {
      this.$emit("update:visible", false);
    },
    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$http.post("proceeds/receive", this.dataForm).then(res => {
            if (res.status == "success") {
              this.$message.success(res.msg);
              this.handleClose();
              this.$parent.refresh && this.$parent.refresh();
            }
          });
        } else {
          return false;
        }
      });
    }
  }
};
</script>
