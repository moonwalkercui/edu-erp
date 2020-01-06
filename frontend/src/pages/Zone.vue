<template>
  <section>
    <el-form :inline="true" class="action-box" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
        >添加</el-button>
      </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="normal/zone"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="100"
    ></table-builder>

    <el-dialog :title="dialogTitle" :visible.sync="editDialogVisible" width="700px">
      <br>
      <el-form size="medium" label-width="120px" :model="dataForm" ref="dataForm" :rules="rules">
        <el-form-item label="内容" prop="content">
            <el-input type="textarea" v-model="dataForm.content" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="">
          <image-draggable-uploader :imageList="dataForm.images" :limit="5" @upload="handleUploadedImage"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <reqButton @handleReq = "handleSave('dataForm')" />
      </div>
    </el-dialog>
  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
import imageDraggableUploader from '@/components/ImageDraggableUploader'
export default {
  components: { TableBuilder, imageDraggableUploader },
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
          {
            title: "发布者",
            width: "100",
            content: row => {
              var avar = '';
              if (row.avatar){
                avar = <img
                  src={row.avatar}
                  class="image"
                  style={{ maxWidth: "50px", maxHeight: "50px" ,  borderRadius: "50%" }}
                />;
              }
              return ( <div>{avar} <p> {row.user.name} </p></div>);
            }
          },
           {
            title: "内容",
            content: row => {
              var images = [];
              if (row.images.length > 0){
                for(var img of row.images) {
                  images.push(<img
                    src={img}
                    class="image"
                    style={{ maxWidth: "100px", maxHeight: "100px", marginRight: "8px" }}
                  />);
                }
              }
              return ( <div><p> {row.content} </p>  {images} <p><small> {new Date(row.edit_time).format('yy/MM/dd hh:mm D')} </small></p></div>);
            }
          },
          { title: "点赞数", name: "logs_count" , width: "80"},
        ],
        tableActionButtons: [
          {
            title: "删除",
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
        content: "",
        images: [],
      },
      rules: {
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      }
    };
  },
  created() {
    this.dataFormInit();
  },
  methods: {
    handleUploadedImage(image) {
      this.dataForm.images = image;
    },
    dataFormInit() {
      this.dialogTitle = "";
      this.dataForm = {
        id: "",
        content: "",
        images: [],
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加消息";
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
            .post("normal/zonedel", { id: data.id })
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
          this.$http.post("normal/zonesave", this.dataForm).then(res => {
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