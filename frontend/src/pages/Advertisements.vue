<template>
  <section>
    <el-form :inline="true" class="action-box" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(196)"
        >添加</el-button>
      </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="normal/advertisements"
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
        <el-form-item label="广告标题" prop="title">
          <el-input v-model="dataForm.title" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="位置" prop="position">
          <select-status v-model="dataForm.position" statusName="adv" size="medium" ></select-status>
          <!-- <el-radio-group v-model="dataForm.position">
            <el-radio :label="option.label" v-for="(option,index) in position" :key="index"></el-radio>
          </el-radio-group> -->
        </el-form-item>
        <el-form-item label="图片">
          <image-uploader
            :image-url="dataForm.image"
            @upload="handleUploadedImage"
          ></image-uploader>
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
import selectStatus from '@/components/SelectStatus'
import imageUploader from "@/components/ImageUploader";
export default {
  components: { TableBuilder, imageUploader, selectStatus },
  data() {
    return {
      sizeText: '',
      tableProps: {
        fields: [
          {
            title: " ",
            width: "70",
            content: row => {
              return (
                <img
                  src={row.image}
                  class="image"
                  style={{ maxWidth: "50px", maxHeight: "50px" }}
                />
              );
            }
          },
          { title: "标题", name: "title" },
          { title: "位置", name: "position" }
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 196,
            icon: "el-icon-edit",
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            pm: 197,
            icon: "el-icon-delete",
            click: row => {
              this.handleDelete(row);
            }
          }
        ]
      },
      data: [],
      loading: false,
      divisionLoading: false,
      urlParam: {
      },
      editDialogVisible: false,
      dialogTitle: "",
      dataForm: {
        id: "",
        title: "",
        position: "",
        image: ""
      },
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        position: [{ required: true, message: "请选择位置", trigger: "blur" }],
        image: [{ required: true, message: "请上传图片", trigger: "blur" }],
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
        title: "",
        position: "",
        image: ""
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加广告";
      this.editDialogVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "修改广告";
      this.dataForm = {
        id: data.id,
        title: data.title,
        position: data.position,
        image: data.image
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
            .post("normal/advertisementdel", { id: data.id })
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
              this.$http.post("normal/advertisementsave", this.dataForm).then(res => {
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