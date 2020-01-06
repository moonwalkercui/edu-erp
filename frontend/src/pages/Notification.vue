<template>
  <section>
    <el-form :inline="true" class="action-box" size="mini">
      <helper key-id='notifications' float='right'/>
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(321)"
        >添加</el-button>
      </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="normal/notifications"
      :fields="tableProps.fields"
      :showIndex="true"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
    ></table-builder>

    <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
      <br>
      <el-form size="medium" label-width="120px" :model="dataForm" ref="dataForm" :rules="rules">
        <el-form-item label="标题" prop="title">
          <el-input v-model="dataForm.title" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
            <el-input type="textarea" v-model="dataForm.content" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="">
          <image-uploader
            :imageUrl.sync="dataForm.image"
            @upload="handleUploadedImage"
            :autoCrop="true" :cropWidth="500" :cropHeight="500"
          ></image-uploader>
           <span class="tip-text">建议尺寸500x500px</span>
        </el-form-item>
        <el-form-item label="是否置顶" prop="is_top">
          <radio-status v-model="dataForm.is_top" valueDefault="否" statusName="is_yes"></radio-status>
          <span class="tip-text">显示在小程序首页滚动通知栏</span>
        </el-form-item>
        <el-form-item label="首页弹窗" prop="is_popup">
          <radio-status v-model="dataForm.is_popup" valueDefault="否" statusName="is_yes"></radio-status>
          <span class="tip-text">只能有一个可首页弹窗的消息, 且必须添加图片, 首页仅提示显示一次</span>
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
import imageUploader from "@/components/ImageUploader";
import radioStatus from '@/components/RadioStatus'
export default {
  components: { TableBuilder, imageUploader, radioStatus },
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
          { title: "标题", name: "title" },
          {
            title: " ",
            width: "80",
            content: row => {
              var cont = '';
              if (row.image){
                cont  = <img
                  src={row.image}
                  class="image"
                  style={{ maxWidth: "50px", maxHeight: "50px" }}
                />;
              }
              return ( cont );
            }
          },
          { title: "置顶", name: "is_top" , width: "100"},
          { title: "首页弹窗", name: "is_popup" , width: "100"}
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 321,
            icon: "el-icon-edit",
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            pm: 322,
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
        content: "",
        image: "",
        is_top: "否",
        is_popup: "否",
      },
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
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
        content: "",
        image: "",
        is_top: "否",
        is_popup: "否",
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加消息";
      this.editDialogVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "修改消息";
      this.dataForm = {
        id: data.id,
        title: data.title,
        content: data.content,
        image: data.image,
        is_top: data.is_top,
        is_popup: data.is_popup,
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
            .post("normal/notificationdel", { id: data.id })
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
           this.$http.post("normal/notificationsave", this.dataForm).then(res => {
              if (typeof res.status != "undefined" && res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.editDialogVisible = false;
                this.dataFormInit();
                this.$refs.tableBuilder.getData();
              }
            });
          } else {
            this.$message.error('提交失败!');
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