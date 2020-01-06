<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus">添加</el-button>
            </el-form-item>
               <el-form-item label="名称" prop="search_name">
                <el-input v-model="urlParam.search_name" style="width: 150px" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
            ref="tableBuilder"
            dataUrl="proceeds/items"
            :fields="tableProps.fields"
            :showIndex="true"
            :condition="urlParam"
            :actionButtons = "tableProps.tableActionButtons"
            :actionWidth = "180"
            :actionMultiButtons = "tableProps.tableActionMultiButtons"
            :tableRowClassName = "tableRowClassName"
            @handleSortChange = "handleSortChange"
        >
        </table-builder>

      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px" :before-close="handleClose">
        <br/>
        <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
            <el-form-item label="款项名称" prop="name">
                <el-input v-model="dataForm.name" class='dialog-form-item'></el-input>
            </el-form-item>
            <el-form-item label="收款金额" prop="money">
                <el-input v-model="dataForm.money" class='dialog-form-item'></el-input>
            </el-form-item>
            <el-form-item label="说明" prop="remark">
                <el-input v-model="dataForm.remark"  type="textarea" :rows="3" class='dialog-form-item'></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                 <radio-status v-model="dataForm.status" statusName="switch" size="medium"></radio-status>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <!-- <el-button type="primary" @click="handleSave('dataForm')">提 交</el-button> -->
            <reqButton @handleReq = "handleSave('dataForm')"/>
        </div>
      </el-dialog>

    </section>
</template>
<script>
// import selectBuilder from "@/components/SelectBuilder";
import TableBuilder from "@/components/TableBuilder";
import radioStatus from '@/components/RadioStatus'
export default {
  components: { TableBuilder, radioStatus }, //  selectBuilder,
  data() {
    return {
      dialogTitle: '',
      dialogFormVisible:false,
      dataForm: {},
      rules: {
        money: [ {required: true, message: '请输入金额', trigger: 'blur'} ],
        name: [ {required: true, message: '请选择名称', trigger: 'blur'} ],
      },
      tableProps: {
        fields: [
          { title: "名称", name: "name" },
          {
            title: "收款金额",
            width: '120',
            sortable: true,
            content: row => {
              return <span>￥{row.money}</span>;
            }
          },
          { title: "状态", name: "status", width: '80' },
          { title: "说明", name: "remark" }
        ],
        tableActionButtons: [
          {
            title: "修改",
            pm: 79,
            icon: "el-icon-edit",
            click: row => {
              this.handleCreate(row);
            }
          },
          {
            title: "删除",
            pm: 79,
            icon: "el-icon-delete",
            click: row => {
              this.handleDel(row);
            }
          }
        ]
      },
      urlParam: {
        sort_by: "",
        sort_type: "",
        search_name: "",
      }
    };
  },

  methods: {
    handleCreate(row) {
      if(row.id) {
        this.dialogTitle = '修改收费项目'
        this.dataForm = {
          id: row.id,
          money: row.money,
          name: row.name,
          remark: row.remark,
          status: row.status
        }
      } else {
        this.dialogTitle = '添加收费项目'
        this.dataForm = {
          money: '',
          name: '',
          remark: '',
          status: '正常'
        }
      }
      this.dialogFormVisible = true
    },
    handleClose() {
      this.dialogFormVisible = false
    },
    handleSave(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
                if(this.dataForm.id){
                  this.$http.post("proceeds/edititem",this.dataForm).then((res) =>{
                      if(res.status == 'success'){
                          this.$message({ message: res.msg, type: 'success' });
                          this.$refs.tableBuilder.getData();
                          this.dialogFormVisible = false;
                      }
                  });
                }else{
                    this.$http.post("proceeds/createitem",this.dataForm).then((res) =>{
                      if(res.status == 'success'){
                          this.$message({ message: res.msg, type: 'success' });
                          this.$refs.tableBuilder.getData();
                          this.dialogFormVisible = false;
                      }
                  });
                }
            } else {
                this.$message.error('提交失败!');
                return false;
            }
        });
    },
    tableRowClassName({ row }) {
      if (row.status !== '正常') {
        return 'is-stop';
      }
    },
    handleDel: function(row) {
      this.$confirm("确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("proceeds/deleteitem", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
    },
    handleSortChange(params) {
      this.urlParam.sort_type = params.sort_type;
      switch (params.sort_by) {
        case "收款金额":
          this.urlParam.sort_by = "money";
          break;
        default:
          this.urlParam.sort_by = "";
          break;
      }
      if (this.urlParam.sort_by != "") this.$refs.tableBuilder.getData();
    }
  }
};
</script>
