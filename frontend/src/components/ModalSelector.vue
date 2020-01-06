<template>
  <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="700px">
      <div style="text-align: center;">
          <select-builder v-if="dataUrl == 'member/table'" table="class" placeholderText="搜班级" v-model="urlParam.search_class" size="small" :style="{width:'30%', marginRight:'10px', display:'inline-block'}"></select-builder>
          <el-input size="small" placeholder="搜索词" clearable v-model="urlParam.search_name" class="input-with-select" :style="{width:'60%'}">
              <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
          </el-input>
      </div>
      <table-builder
              ref="tableBuilder"
              :dataUrl="dataUrl"
              :fields="tableProps.fields"
              :showIndex="false"
              :condition="urlParam"
              :actionButtons = "tableProps.tableActionButtons"
              :actionWidth = "150"
              :actionMultiButtons = "tableProps.tableActionMultiButtons"
              :handleSelection = "handelSelect"
              :selectedValues.sync = 'selectedValues'
      ></table-builder>
      <span slot="footer" class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <!-- <el-button type="primary" @click="handleSubmit">选 择</el-button> -->
           <reqButton @handleReq = "handleSubmit" text="选 择"/>
        </span>
  </el-dialog>
</template>
<script>
  import TableBuilder from '@/components/TableBuilder'
  import selectBuilder from '@/components/SelectBuilder'
  export default {
    components: { TableBuilder, selectBuilder },
    props: {
      visible: {default: false},
      dataUrl: {required: true},
      title: {default: '标题'},
    },
    data() {
      return {
        data: [],
        urlParam: {
          per_page: 15,
          search_name: '',
          search_class: '',
          active: 'yes'
        },
        selectedValues: [],
      }
    },
    computed: {
      tableProps: function () {
        let fields = [];
        switch (this.dataUrl) {
          case 'product/table' :
            fields = [
              {title :'名称' , name:"name"},
              {title :'价格(元)' , name:"price", width:"80"},
              {title :'原价(元)' , name:'price_original', width:"80"},
              {title :'状态' , name:'status', width:"80"},
            ];
            break;
          case 'material/table' :
            fields = [
              {title :'名称' , name:"name" },
              {title :'库存' , name:'quantity'},
              {title :'单价' , name:'price' }
            ];
            break;
          case 'proceeds/items' :
            fields = [
              {title :'名称' , name:"name" },
              {title :'收款金额' , name:'money'},
              {title :'说明' , name:'remark' }
            ];
            break;
          case 'member/table' :
            fields = [
              {title :'姓名' , name:"name" },
              {title :'手机号' , name:'mobile'},
              {title :'班级' , content:(row)=>{
                var classes = '';
                row.classes.forEach(i => {
                  classes += i.name + ', '
                });
                return this.$createElement('span', [classes.replace(/(,\s$)/g,"")])
              }},
            ];
            break;
          default: fields = []
        }
        return {fields}
      }
    },
    created() {
    },
    methods: {
      handleSubmit() {
        if (this.selectedValues.length == 0) {
          this.$message('请选择')
        }else {
          this.$emit('selectComplete', this.selectedValues)
        }
      },
      handelSelect() {
        return true;
      },
      handleClose() {
        this.$emit('update:visible', false)
      },
      handleSearch() {
        this.$refs.tableBuilder.getData();
      }
    }
  };
</script>
<style>
    .modal-selector .el-dialog__body {
        padding: 0 10px;
    }
</style>