<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <helper key-id='giftlist' float='right'/>
      <el-form-item>
        <el-button
          type="primary"
          @click="handleCreate"
          icon="el-icon-plus"
          v-if="$util.hasPermission(243)"
        >添加</el-button>
      </el-form-item>
      <el-form-item label="名称" prop="search_name">
        <el-input v-model="urlParam.search_name" placeholder="" clearable style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="onSearchSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="gift/table"
      :fields="tableProps.fields"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="180"
    ></table-builder>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px">
      <br>
      <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="所属分类" prop="category">
          <select-builder table="giftcategory" v-model="dataForm.category" size="normal"></select-builder>
        </el-form-item>
        <el-form-item label="星星数" prop="points">
          <el-input type="number" v-model="dataForm.points" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="库存数量" prop="storage">
          <el-input type="number" v-model="dataForm.storage" class="dialog-form-item"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <image-uploader :image-url.sync="dataForm.image" @upload="handleUploadedImage" :autoCrop="true" :cropWidth="500" :cropHeight="500"></image-uploader>
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input type="number" v-model="dataForm.sort" class="dialog-form-item" placeholder="数值越大越靠前"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <radio-status v-model="dataForm.status" statusName="switch"></radio-status>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
        <reqButton @handleReq = "handleSave('dataForm')" />
      </div>
    </el-dialog>
  </section>
</template>
<style>
.el-tree-node__content {
  padding: 5px;
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
  width: 38% !important;
}
.el-transfer-panel__body,
.el-transfer-panel__list {
  height: 340px !important;
}
.el-transfer-panel__list.is-filterable {
  height: 295px !important;
}
</style>
<script>
import TableBuilder from "@/components/TableBuilder";
import selectBuilder from "@/components/SelectBuilder";
 import radioStatus from '@/components/RadioStatus';
 import imageUploader from "@/components/ImageUploader";
export default {
  components: { selectBuilder, TableBuilder,radioStatus ,imageUploader},
  data() {
    return {
      dialogFormVisible: false,
      dialogTitle: "",
      dataForm: {},
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        category: [{ required: true, message: "请选择分类", trigger: "blur" }],
        points: [{ required: true, message: "请设置星星数", trigger: "blur" }],
        image: [{ required: true, message: "请上传图片", trigger: "blur" }],
        storage: [{ required: true, message: "请输入库存数", trigger: "blur" }],
      },

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
          { title: "名称", name: "name" },
          {
            title: "分类",
            content: row => {
              return <span>{row.category.title}</span>;
            }
          },
          { title: "星星数", name: "points" },
          { title: "兑换数量", name: "exchange_num" },
          { title: "库存", name: "storage" },
          { title: "状态", name: "status" }
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 243,
            icon: "el-icon-edit",
            click: row => {
              this.handleEdit(row);
            }
          },
          {
            title: "删除",
            pm: 244,
            icon: "el-icon-delete",
            click: row => {
              this.handleDel(row);
            }
          }
        ]
      },
      urlParam: {
        search_name: ""
      }
    };
  },
  mounted() {},
  methods: {
    handleUploadedImage(image) {
      this.dataForm.image = image;
    },
    onSearchSubmit() {
      this.$refs.tableBuilder.getData(1);
    },
    handleCurrentChange(val) {
      this.urlParam.page = val;
      this.firstOpen || this.$refs.tableBuilder.getData(); // 有页码的页码会发送两次请求，第一次过滤掉。
    },
    handleDel: function(row) {
      this.$confirm("确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("gift/delete", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
    },

    dataFormInit() {
      this.dataForm = {
        id: "",
        name: "",
        category: "",
        points: "",
        image: "",
        storage: 10,
        sort: 0,
        status: '正常',
      };
    },
    handleCreate() {
      this.dataFormInit();
      this.dialogTitle = "添加礼品";
      this.dialogFormVisible = true;
    },
    handleEdit(data) {
      this.dataFormInit();
      this.dialogTitle = "编辑礼品";
      this.dataForm.id = data.id;
      this.dataForm.name = data.name;
      this.dataForm.category = data.category_id;
      this.dataForm.points = data.points;
      this.dataForm.image = data.image;
      this.dataForm.status = data.status;
      this.dataForm.storage = data.storage;
      this.dataForm.sort = data.sort;
      this.dialogFormVisible = true;
    },

    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
            this.$http.post("gift/save", this.dataForm).then(res => {
              if (res.status == "success") {
                this.$message({ message: res.msg, type: "success" });
                this.$refs.tableBuilder.getData();
                this.dataFormInit();
                this.dialogFormVisible = false;
              }
            });
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    }
  }
};
</script>
