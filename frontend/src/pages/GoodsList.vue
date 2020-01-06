<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="$router.push({ path: '/shop/create'})"
          icon="el-icon-plus"
          v-if="$util.hasPermission(208)"
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
      data-url="shop/goods"
      :fields="tableProps.fields"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="180"
    ></table-builder>

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
// import selectBuilder from "@/components/SelectBuilder";
export default {
  components: { TableBuilder },
  data() {
    return {
      // table相关的参数
      tableProps: {
        fields: [
          {title :' ' , width:'70' ,
            content:(row)=>{ return ( <img src={row.image} class="image" style={{maxWidth: '50px' , maxHeight: '50px'}} />) }
          },
          { title: "名称", name: "name" },
          {
            title: "分类",
            width: "80",
            content: row => {
              return <span>{row.category.name}</span>;
            }
          },
          { title: "库存", name: "storage" },
          { title: "销量", name: "sale_quantity" },
          { title: "价格", name: "price" },
          { title: "状态", name: "status" },
        ],
        tableActionButtons: [
          {
            title: "编辑",
            pm: 208,
            icon: "el-icon-edit",
            click: row => {
              this.$router.push({ path: '/shop/create/' + row.id})
            }
          },
          {
            title: "删除",
            pm: 212,
            icon: "el-icon-delete",
            click: row => {
              this.handleDel(row);
            }
          }
        ]
      },

      urlParam: {
        search_name: "",
      },
    };
  },

  mounted() {},
  methods: {
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
          this.$http.post("shop/delgoods", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
    },
   
  }
};
</script>
