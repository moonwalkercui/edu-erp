<template>
  <section>
    <el-form :inline="true" class="action-box" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(210)"
        >添加</el-button>
      </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="shop/category"
      :fields="tableProps.fields"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
      :showPaginate="false"
    ></table-builder>

    <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
      <br>
      <el-form size="medium" label-width="120px" :model="dataForm"  ref="dataForm" :rules="rules">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="排序权重" prop="sort">
          <el-input type="number" v-model="dataForm.sort" class="dialog-form-item" placeholder="数值越大越靠前"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
         <reqButton @handleReq = "handleSave('dataForm')" />
      </div>
    </el-dialog>
  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
// import imageUploader from "@/components/ImageUploader";
export default {
  components: { TableBuilder,  },// imageUploader
  data() {
    return {
      tableProps: {
        fields: [
          { title: "名称", name: "name" },
          { title: "排序权重", name: "sort" },
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 210,
            icon: "el-icon-edit",
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            pm: 311,
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
      dataForm: {
        id: "",
        name: "",
        sort: 0,
      },
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
      }
    };
  },
  created() {
    this.dataFormInit();
  },
  methods: {
    dataFormInit() {
      this.dialogTitle = "";
      this.dataForm = {
        id: "",
        name: "",
        sort: 0
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加分类";
      this.editDialogVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "修改分类";
      this.dataForm = {
        id: data.id,
        name: data.name,
        sort: data.sort,
      };
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
            .post("shop/delcategory", { id: data.id })
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
            this.$http.post("shop/savecategory", this.dataForm).then(res => {
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