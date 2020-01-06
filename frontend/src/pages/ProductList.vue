<template>
  <section>
    <el-form :inline="true" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-button
          type="primary"
          @click="$router.push('/product/create')"
          icon="el-icon-plus"
          v-if="$util.hasPermission(47)"
        >添加</el-button>
        <el-button v-if="$util.hasPermission(47)" @click="$exportExcel('product/table',urlParam)" icon="el-icon-download">导出</el-button>
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="urlParam.search_name" placeholder=""></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$refs.tableBuilder.getData(1)">查询</el-button>
      </el-form-item>
       <helper key-id='productlist' float='right'/>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="product/table"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="220"
      :actionMultiButtons="tableProps.tableActionMultiButtons"
      :handleSelection="handelSelect"
      :tableRowClassName="tableRowClassName"
      :expand="true"
    >
      <template slot="expandContent" slot-scope="row" class="demo-table-expand">
        <el-table
          :data="row.data.specs"
          style="width: 100%"
          :row-style="{background: '#f4f4f5', fontSize: '13px'}"
          :show-header="false"
        >
          <el-table-column prop="courses_quantity" label="" width="300">
            <template slot-scope="scope"><span style="margin-left:140px;">课时数: {{ scope.row.courses_quantity }}</span></template>
          </el-table-column>
          <el-table-column prop="price" label="" width="100">
            <template slot-scope="scope"> 价格: ￥{{ scope.row.price }}</template>
          </el-table-column>
          <el-table-column prop="name" label=""></el-table-column>
        </el-table>
      </template>
    </table-builder>
    <ProductMembers :visible.sync="memberVisible" :product-id = 'productId'/>
    <ProductGroupbuy :visible.sync="groupbuyVisible" :product-id = 'productIdGroupbuy'/>
  </section>
</template>
<script>
import {map} from "lodash";
import TableBuilder from "@/components/TableBuilder";
import ProductMembers from "@/components/ProductMembers";
import ProductGroupbuy from "@/components/ProductGroupbuy";
import img from "@/assets/img_def.jpg";

export default {
  components: { TableBuilder, ProductMembers, ProductGroupbuy },
  data() {
    return {
      memberVisible: false,
      groupbuyVisible: false,
      productId: 0,
      productIdGroupbuy: 0,
      // table相关的参数
      tableProps: {
        fields: [
          {
            title: " ",
            width: "70",
            content: row => {
              return (
                <img
                  src={row.image || img}
                  class="image"
                  style={{ maxWidth: "50px", maxHeight: "50px" }}
                />
              );
            }
          },
          {
            title: "名称",
            name: "name",
            content: row => {
              var name;
              if (row.slogan) {
                name = (
                  <el-tooltip
                    content={row.slogan}
                    placement="bottom"
                    effect="light"
                  >
                    <b>
                      <span style="color:orange">{row.is_groupbuy == '是'? '[团] ' : ''}</span>
                      <span style="color:red">{row.recommend == '是'? '[荐] ' : ''}</span>
                      {row.name} <i class="el-icon-more" />
                    </b>
                  </el-tooltip>
                );
              } else {
                name = <b>{row.name}</b>;
              }
              return (
                <div>
                  {name}
                </div>
              );
            }
          },
          {
            title: "报名时间", name: "name", width: "130",
            content: row => {
              return (
                  <span>
                    {row.start_at ? new Date(row.start_at).format("MM/dd") + " ~ " + new Date(row.end_at).format("MM/dd") : ''}
                  </span>
              );
            }
          },
          { title: "类型", name: "type", width: "80" },
          { title: "名额", name: "quantity", width: "60" },
          { title: "已排课时", name: "courses_count", width: "80" },
          // { title: "学员", name: "members_count", width: "60" },
          {title :'学员' , width:'60' , content:(row)=>{
              return (<el-button  type="text" size="small" ><el-tag size="mini" onClick={()=> {
                this.productId = row.id;
                this.memberVisible = true;
              } }> { row.members_count } </el-tag></el-button>)
          }},
          { title: "状态", name: "status", width: "60" }
        ],
        tableActionButtons: [
          // {
          //   title: "学员",
          //   icon: "el-icon-user",
          //   click: row => {
          //     this.productId = row.id;
          //     this.memberVisible = true;
          //   }
          // },
          // {
          //   title: "团购设置",
          //   icon: "el-icon-share",
          //   click: row => {
          //     this.productIdGroupbuy = row.id;
          //     this.groupbuyVisible = true;
          //   }
          // },
          {
            title: "编辑",
            pm: 48,
            icon: "el-icon-edit",
            click: row => {
              this.$router.push({ path: "/product/edit/" + row.id });
            }
          },
          {
            title: "删除",
            pm: 49,
            icon: "el-icon-delete",
            click: row => {
              this.handleDel(row);
            }
          }
        ],
        tableActionMultiButtons: [
          {
            title: "批量上架",
            pm: 51,
            icon: "el-icon-sort-up",
            click: val => {
              this.multipleOpen(val);
            }
          },
          {
            title: "批量下架",
            pm: 50,
            icon: "el-icon-sort-down",
            click: val => {
              this.multipleStop(val);
            }
          }
        ]
      },

      departments: [],
      urlParam: {
        search_name: "" // 搜索user名
      },
      formLabelWidth: "120px",
      multipleDialogSelection: []
    };
  },
  methods: {
    handelSelect() {
      return true;
    },
    handleDel: function(row) {
      this.$confirm("此操作将永久删除数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http
            .post("product/delete", { id: row.id })
            .then(res => {
              if (res.status == "success") {
                this.$message({ message: "删除成功", type: "success" });
                this.$refs.tableBuilder.getData();
              }
            });
        })
        .catch(() => {});
    },
    getDataList() {
      this.loading = true;
      this.$http.fetch("product/table", this.urlParam).then(res => {
        this.data = res.data;
        this.urlParam.page = res.meta.current_page;
        this.pageTotal = res.meta.total;
        this.loading = false;
        this.firstOpen = false;
      });
    },
    tableRowClassName({ row }) {
      if (row.status == "下架") return "is-stop";
      else return "";
    },
    multipleStop(val) {
      if (val.length == 0) {
        this.$message.error("未选择");
        return;
      }
      var ids = map(val, "id");
      this.$http.fetch("product/stop", { ids: ids }).then(res => {
        if (res.status == "success") {
          this.$message({ message: res.msg, type: "success" });
          this.$refs.tableBuilder.getData();
        }
      });
    },
    multipleOpen(val) {
      if (val.length == 0) {
        this.$message.error("未选择");
        return;
      }
      var ids = map(val, "id");
      this.$http.fetch("product/open", { ids: ids }).then(res => {
        if (res.status == "success") {
          this.$message({ message: res.msg, type: "success" });
          this.$refs.tableBuilder.getData();
        }
      });
    }
  }
};
</script>