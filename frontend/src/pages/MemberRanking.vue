<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
         <helper key-id='memberranking' float='right'/>
      <el-form-item >
          <el-button type="primary" @click="showProductAdd = true" icon="el-icon-plus" v-if="$util.hasPermission(324)">给学员加课</el-button>
      </el-form-item>
      <el-form-item label="手机号" prop="search_mobile">
        <el-input v-model="urlParam.search_mobile" style="width: 150px" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="昵 称" prop="search_nickname">
        <el-input v-model="urlParam.search_nickname" style="width: 150px" placeholder="昵 称"></el-input>
      </el-form-item>
      <el-form-item label="课 程" prop="search_product">
         <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="onSearchSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="member/ranking"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="100"
      :actionMultiButtons="tableProps.tableActionMultiButtons"
      :tableRowClassName="tableRowClassName"
      @handleSortChange="handleSortChange"
    ></table-builder>
    <MemberProductEdit :visible.sync="showProductEdit" :item.sync = "editItem" />
    <MemberProductAdd :visible.sync="showProductAdd" />
  </section>
</template>

<style>
</style>
<script>
import TableBuilder from "@/components/TableBuilder";
import MemberProductEdit from "@/components/MemberProductEdit";
import MemberProductAdd from "@/components/MemberProductAdd";
import selectBuilder from '@/components/SelectBuilder';
import img from "@/assets/img_def.jpg";
export default {
  components: { TableBuilder,MemberProductEdit,selectBuilder, MemberProductAdd },
  data() {
    return {
      showProductEdit: false,
      editItem: {},
      showProductAdd: false,
      // table相关的参数
      tableProps: {
        fields: [
          {
            title: " ",
            width: "70",
            content: row => {
              return (
                <img
                  src={row.member.avatar || img}
                  class="image"
                  style={{ maxWidth: "50px", maxHeight: "50px" }}
                />
              );
            }
          },
          {
            title: "学员",
            width: 100,
            content: row => {
              return <span>{row.member.nick_name}</span>;
            }
          },
          {
            title: "手机号",
            width: 110,
            content: row => {
              return <span>{row.member.mobile}</span>;
            }
          },
          {
            title: "课程名",
            content: row => {
              return <span>{row.product.name}</span>;
            }
          },
          {
            title: "总课时",
            width: 100,
            name: "total_quantity",
            sortable: "custom"
          },
          {
            title: "剩余课时",
            width: 100,
            name: "remaining_quantity",
            sortable: "custom"
          },
          {
            title: "进度",
            content: row => {
              var value = parseFloat(
                (
                  100 -
                  (row.remaining_quantity / row.total_quantity) * 100
                ).toFixed(2)
              );
              var color = "text";
              if (value < 30) color = "success";
              if (value > 80) color = "exception";
              var progress = (
                <el-progress
                  text-inside={true}
                  stroke-width={18}
                  percentage={value}
                  status={color}
                />
              );
              return <span> {progress} </span>;
            }
          }
        ],
        tableActionButtons: [
          {
            title: "调整",
            pm: 323,
            icon: "el-icon-edit-outline",
            click: row => {
              this.editItem = row;
              this.showProductEdit = true;
            }
          },
        ]
      },
      urlParam: {
        search_nickname: "", // 搜索学员手机
        search_mobile: "", // 搜索学员手机
        search_product: "", // 搜索学员手机
        search_name: "" // 搜索学员名
      }
    };
  },
  methods: {
    
    handleSortChange(params) {
      this.urlParam.sort_type = params.sort_type;
      switch (params.sort_by) {
        case "总课时":
          this.urlParam.sort_by = "total";
          break;
        case "剩余课时":
          this.urlParam.sort_by = "remaining";
          break;
        default:
          this.urlParam.sort_by = "";
          break;
      }
      if (this.urlParam.sort_by != "") this.$refs.tableBuilder.getData();
    },
    onSearchSubmit() {
      this.$refs.tableBuilder.getData(1);
    },
    tableRowClassName({ row }) {
      var status = row.status;
      if (status == "停用") {
        return "is-stop";
      }
      return "";
    }
  }
};
</script>
