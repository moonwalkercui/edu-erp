<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <helper key-id='ordersalesman' float='right'/>
      <el-form-item label="业务员">
        <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <!-- <el-form-item label="状态">
        <select-status
          v-model="urlParam.search_status"
          statusName="order"
          :style="{width: '150px'}"
        ></select-status>
      </el-form-item> -->
      <el-form-item label="时间选择">
        <el-date-picker
          v-model="urlParam.search_date"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd">
        </el-date-picker>
        <!-- <el-date-picker v-model="urlParam.date" type="month" placeholder="选择月" style="width: 150px"></el-date-picker> -->
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$refs.tableBuilder.getData(1)">查询</el-button>
      </el-form-item>
          <el-form-item>
        <el-button
          v-if="$util.hasPermission(102)"
          @click="$exportExcel('order/salesman',urlParam)"
          icon="el-icon-download"
        >导出</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="order/salesman"
      :fields="tableProps.fields"
      :showPaginate="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="80"
      :actionMultiButtons="tableProps.tableActionMultiButtons"
      :tableRowClassName="tableRowClassName"
      @handleSortChange="handleSortChange"
    ></table-builder>

     <el-dialog title="明细" :visible.sync="listVisible" width="700px">
        <el-table :data="orderList" style="width: 100%" >
          <el-table-column prop="sn" label="订单号"></el-table-column>
          <el-table-column prop="nick_name" label="学员"></el-table-column>
          <el-table-column prop="total_price" label="订单金额"></el-table-column>
          <el-table-column prop="created_at" label="订单时间"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
            <el-button @click="listVisible=!listVisible">关 闭</el-button>
        </div>
    </el-dialog>

  </section>
</template>

<script>
import TableBuilder from "@/components/TableBuilder";
import selectBuilder from "@/components/SelectBuilder";
// import selectStatus from "@/components/SelectStatus";
const startDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-1'
const endDate = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()
// console.log(startDate,endDate)
export default {
  components: { selectBuilder, TableBuilder }, //, selectStatus
  data() {
    return {
      // table相关的参数
      tableProps: {
        fields: [
          // {title :'业务员' , width:'200', content:(row)=>{ return (<span> { row.member_name +' (' +row.member_mobile + ')' } </span>) } },
          { title: "业务员", name: "name" },
          { title: "订单数量", sortable: true, name: "count" },
          {
            title: "订单金额",
            sortable: true,
            content: row => {
              return (
                <span style="text-align:right;display: block; width:100px;">
                  {"￥" + (row.total ? row.total : 0)}
                </span>
              );
            }
          }
        ],
        tableActionButtons: [
          {
            title: "明细",
            icon: "el-icon-info",
            click: row => {
              this.showList(row);
            }
          }
        ]
      },
      urlParam: {
        search_date: [ startDate, endDate ],
        date: ""
      },
      orderList: [],
      listVisible: false,
    };
  },
  methods: {
    showList(row){
      this.$http.fetch("order/listbysalesman", { search_user: row.username, search_date: this.urlParam.search_date }).then(res => {
        // console.log(res.data)
        if (res.status == "success") {
          this.listVisible = true;
          this.orderList = res.data;
        }
      });
    },
    handleSortChange(param) {
        this.urlParam.sort_by = param.sort_by;
        this.urlParam.sort_type = param.sort_type;
        this.$refs.tableBuilder.getData();
    },
    tableRowClassName({ row }) {
      var status = row.status;
      if (status == "已驳回") {
        return "is-stop";
      } else if (status == "已审核") {
        return "is-finish";
      } else {
        return "";
      }
    }
  }
};
</script>
