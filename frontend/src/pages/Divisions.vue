  <template>
  <section>
    <el-form :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(215)"
        >添加</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="org/divisions"
      :fields="tableProps.fields"
      :showIndex="true"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="220"
      :showPaginate="false"
    ></table-builder>
    <el-dialog
      :title="dialogTitle == 'create' ? '添加门店' : '编辑门店'"
      :visible.sync="editDialogVisible"
      width="700px"
    >
      <!--<template>-->
      <!--<el-alert title="门店添加后将不能被删除" type="info" show-icon></el-alert>-->
      <!--</template>-->
      <br>
      <el-form label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="region_id">
          <select-builder table="region" v-model="dataForm.region_id" size="normal"></select-builder>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="dataForm.phone" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="dataForm.address" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="排序权重" prop="sort">
          <el-input type="number" v-model="dataForm.sort" class="dialog-form-item" placeholder="数值越大越靠前"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-uploader :imageUrl.sync="dataForm.image" @upload="handleUploadedImage" :autoCrop="true" :cropWidth="400" :cropHeight="200" ></image-uploader>
          <span class="tip-text">建议尺寸</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
         <reqButton @handleReq = "handleSave('dataForm')" />
      </div>
    </el-dialog>
    <!-- <map-picker :visibleDialog.sync="mapDialogVisible" :config.sync="mapConfig" @getCoordinate="getCoordinate"/> -->
  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
// import MapPicker from "@/components/MapPicker";
import ImageUploader from "@/components/ImageUploader";
import selectBuilder from '@/components/SelectBuilder'
export default {
  components: { TableBuilder,  ImageUploader, selectBuilder },// MapPicker,
  data() {
    return {
      mapForm: {},
      // table相关的参数
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
          // {title :'名称' ,
          //     content:(row)=>{ return (<span>{ row.name + (row.is_default == 1 ? " (默认)" : "") }</span> ) }
          // },
          { title: "名称", name: "name" },
          { title: "电话", name: "phone" },
          { title: "地址", name: "address" },
          { title: "经度", name: "lat", width: "100" },
          { title: "纬度", name: "lng", width: "100" }
        ],
        tableActionButtons: [
          // {title:'设为默认',pm:36 , icon:"el-icon-check" , click:(row)=>{ this.handleSetDefault(row.id) } , visible:(row)=>{ return row.is_default == 0 }},
          {
            title: "编辑",
            icon: "el-icon-edit",
            pm: 216,
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            icon: "el-icon-delete",
            pm: 219,
            click: row => {
              this.handleDel(row);
            }
          },
          {
            title: "坐标",
            icon: "el-icon-location",
            pm: 217,
            click: row => {
              this.$router.push({
                path:
                  "/org/coordinate/" + row.id + "/" + row.lat + "/" + row.lng
              });
            }
          }
        ]
      },
      editDialogVisible: false,
      // mapDialogVisible: false,
      // mapConfig: {},
      dataForm: {},
      dialogTitle: "",
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        region_id: [{ required: true, message: "请选择地区", trigger: "blur" }],
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
    // getCoordinate(row, coordinate) {
    //    this.$http.post("org/divisioncoordinate", { id: row.id, coordinate: coordinate }).then(res => {
    //       if (res.status == "success") {
    //         this.$message({ message: res.msg, type: "success" });
    //       }
    //     });
    // },
    dataFormInit() {
      this.dataForm = {
        id: "",
        name: "",
        address: "",
        sort: 0,
        phone: "",
        image: "",
        region_id: "",
      };
    },
    handleEdit(row) {
      this.dialogTitle = "edit";
      this.dataForm = row;
      this.editDialogVisible = true;
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "create";
      this.editDialogVisible = true;
    },
    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.dataForm.id == "") {
            this.$http.post("org/divisioncreate", this.dataForm).then(res => {
              if (res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.$refs.tableBuilder.getData();
                this.dataFormInit();
                this.editDialogVisible = false;
              }
            });
          } else {
            this.$http.post("org/divisionedit", this.dataForm).then(res => {
              if (res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.$refs.tableBuilder.getData();
                this.dataFormInit();
                this.editDialogVisible = false;
              }
            });
          }
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    },
    handleSaveMap(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.dataForm.id == "") {
            this.$http.post("org/divisioncreate", this.dataForm).then(res => {
              if (res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.$refs.tableBuilder.getData();
                this.dataFormInit();
                this.editDialogVisible = false;
              }
            });
          } else {
            this.$http.post("org/divisionedit", this.dataForm).then(res => {
              if (res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.$refs.tableBuilder.getData();
                this.dataFormInit();
                this.editDialogVisible = false;
              }
            });
          }
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    },
    handleDel(row) {
      this.$confirm("确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("org/divisiondelete", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
    },
    handleSetDefault: function(id) {
      this.$http.post("org/divisionsetdefault", { id: id }).then(res => {
        if (res.status == "success") {
          this.$message({ message: res.msg, type: "success" });
          this.$refs.tableBuilder.getData();
        }
      });
    }
  }
};
</script>