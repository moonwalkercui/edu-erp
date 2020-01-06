<template>
  <section>
    <el-form :inline="true" class="action-box" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(203)"
        >添加</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="voucher/table"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
      :showPaginate="false"
    ></table-builder>

    <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
      <br>
      <el-form size="medium" label-width="120px" :model="dataForm" ref="dataForm" :rules="rules">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="满足金额" prop="catch_price">
          <el-input v-model="dataForm.catch_price" type="number" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="优惠金额" prop="price">
          <el-input v-model="dataForm.price" type="number" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="失效时间" prop="invalid_at">
            <el-date-picker v-model="dataForm.invalid_at" type="date" placeholder="选择日期"></el-date-picker>
          <!-- <el-input v-model="dataForm.invalid_at" placeholder class="dialog-form-item"></el-input> -->
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-uploader
            :image-url="dataForm.image"
            @upload="handleUploadedImage"
          ></image-uploader>
          <span class="tip-text"> </span>
        </el-form-item>
        <el-form-item label="说明" prop="term">
          <el-input v-model="dataForm.term" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
     
        <el-form-item label="状态" prop="status">
            <radio-status v-model="dataForm.status" statusName="switch" ></radio-status>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
        <reqButton @handleReq = "handleSave('dataForm')"/>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder"
import imageUploader from "@/components/ImageUploader"
import radioStatus from '@/components/RadioStatus'
export default {
  components: { TableBuilder, imageUploader, radioStatus },
  data() {
    return {
      tableProps: {
        fields: [
          {title :' ' , width:'70' ,
            content:(row)=>{ return ( <img src={row.image} class="image" style={{maxWidth: '50px' , maxHeight: '50px'}} />) }
          },
          { title: "名称", name: "name" },
          { title: "领取码", name: "code" },
          { title: "优惠金额", name: "price" },
          { title: "满足金额", name: "catch_price" },
          { title: "失效日期", name: "invalid_at" },
          { title: "状态", name: "status" },
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 203,
            icon: "el-icon-edit",
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            pm: 204,
            icon: "el-icon-delete",
            click: row => {
              this.handleDelete(row);
            }
          }
        ]
      },
      data: [],
      urlParam: {
      },
      editDialogVisible: false,
      dialogTitle: "",
      dataForm: {},
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        catch_price: [{ required: true, message: "请输入满足金额", trigger: "blur" }],
        price: [{ required: true, message: "请输入优惠金额", trigger: "blur" }],
        invalid_at: [{ required: true, message: "请设置失效日期", trigger: "blur" }],
      }
    };
  },
  created() {
    this.dataFormInit();
  },
  methods: {
    handleUploadedImage(image) {
      this.dataForm.image = image;
    },
    dataFormInit() {
      this.dialogTitle = "";
      this.dataForm = {
        id: "",
        name: "",
        catch_price: "",
        price: "",
        image: "",
        term: "",
        invalid_at: "",
        status: "正常",
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加优惠券";
      this.editDialogVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "修改优惠券";
      this.dataForm.id = data.id;
      this.dataForm.name = data.name;
      this.dataForm.catch_price = data.catch_price;
      this.dataForm.price = data.price;
      this.dataForm.image = data.image;
      this.dataForm.term = data.term;
      this.dataForm.invalid_at = data.invalid_at;
      this.dataForm.status = data.status;
      this.editDialogVisible = true;
    },
    handleDelete(data) {
      this.$confirm("确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http
            .post("voucher/delete", { id: data.id })
            .then(res => {
              if (res.status == "success") {
                this.$refs.tableBuilder.getData();
                this.$message({ message: res.msg, type: "success" });
              }
            });
        })
        .catch(() => {});
    },
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
            this.$http.post("voucher/save", this.dataForm).then(res => {
              if (typeof res.status != "undefined" && res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.editDialogVisible = false;
                this.dataFormInit();
                this.$refs.tableBuilder.getData();
              }
           });
        } else {
            this.$message.error('请检查内容');
            return false;
        }
      });
    }
  }
};
</script>
<style>
.el-tree-node__content {
  padding: 6px 5px;
  border-bottom: 1px solid #efefef;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.el-transfer-panel {
  width: 38%;
}
.el-transfer-panel__body,
.el-transfer-panel__list {
  height: 340px;
}
.el-transfer-panel__list.is-filterable {
  height: 295px;
}
</style>