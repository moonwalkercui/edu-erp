<template>
  <section>

    <table-builder
      ref="tableBuilder"
      data-url="setting/wxmsgtemplate"
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
        <el-form-item label="模板名" prop="temp_name">
          {{dataForm.temp_name}}
          <!-- <el-input v-model="dataForm.temp_name" placeholder class="dialog-form-item"></el-input> -->
        </el-form-item>
        <!-- <el-form-item label="模板编号" prop="temp_sn">
          <el-input v-model="dataForm.temp_sn" placeholder class="dialog-form-item"></el-input>
        </el-form-item> -->
        <el-form-item label="模板ID" prop="temp_id">
          <el-input v-model="dataForm.temp_id" placeholder class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="回复内容" prop="temp_content">
          <el-input type="textarea" v-model="dataForm.temp_content" placeholder class="dialog-form-item" :rows="10"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <radio-status v-model="dataForm.status" valueDefault="正常" statusName="switch"></radio-status>
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
import TableBuilder from "@/components/TableBuilder";
import radioStatus from '@/components/RadioStatus'
export default {
  components: { TableBuilder, radioStatus },
  data() {
    return {
      position:[
        {label: 'coursesbanner', size: '750 x 300px'},
        {label: 'coursesmiddle', size: '750 x 300px'},
        {label: 'divisionsbanner', size: '750 x 300px'},
        {label: 'membercenterbanner', size: '750 x 300px'},
      ],
      sizeText: '',
      tableProps: {
        fields: [
            { title: "模板名", name: "temp_name" },
            // { title: "模板编号", name: "temp_sn" },
            { title: "模板ID", name: "temp_id" },
            { title: "回复内容", name: "temp_content" },
            { title: "状态", name: "status", width:100 },
        ],
        tableActionButtons: [
          {
            title: "编辑",
            icon: "el-icon-edit",
            pm: 317,
            click: row => {
              this.handleEdit(row);
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
        temp_name: "",
        // temp_sn: "",
        temp_id: "",
        temp_content: "",
        status: "正常"
      },
      rules: {
        temp_name: [{ required: true, message: "请输入模板名", trigger: "blur" }],
        // temp_sn: [{ required: true, message: "请输入模板编号", trigger: "blur" }],
        temp_id: [{ required: true, message: "请输入模板ID", trigger: "blur" }],
        temp_content: [{ required: true, message: "请输入回复内容", trigger: "blur" }],
      }
    };
  },
  created() {
    this.dataFormInit();
    this.changePosition(0)
  },
  methods: {
    changePosition(index) {
      this.sizeText = this.position[index].size;
    },
    handleUploadedImage(image) {
      this.dataForm.image = image;
    },
    dataFormInit() {
      this.dialogTitle = "";
      this.dataForm = {
        id: "",
        temp_name: "",
        // temp_sn: "",
        temp_id: "",
        temp_content: "",
        status: "正常"
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加模板";
      this.editDialogVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "修改模板";
      this.dataForm = {
        id: data.id,
        temp_name: data.temp_name,
        // temp_sn: data.temp_sn,
        temp_id: data.temp_id,
        temp_content: data.temp_content,
        status: data.status,
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
                this.$http.post("setting/savewxmsgtemplate", this.dataForm).then(res => {
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