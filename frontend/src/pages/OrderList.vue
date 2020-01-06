<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <helper key-id='orderlist' float='right'/>
      <el-form-item>
      <!--<el-button type="primary" icon="el-icon-edit-outline" @click="$router.push('/order/create')" v-if="$util.hasPermission(104)">报单</el-button>-->
        <el-button v-if="$util.hasPermission(103)" @click="$exportExcel('order/table',urlParam)" icon="el-icon-download">导出</el-button>
      </el-form-item>
      <el-form-item size="mini" label="订单号">
        <el-input v-model="urlParam.search_sn" placeholder="" :style="{width: '100px'}"></el-input>
      </el-form-item>
        <el-form-item size="mini" label="交易号">
        <el-input v-model="urlParam.search_paysn" placeholder="" :style="{width: '100px'}"></el-input>
      </el-form-item>
      <el-form-item label="课程">
        <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <!-- <el-form-item label="老师">
                <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
      </el-form-item>-->
      <el-form-item label="学员">
        <select-builder table="member" v-model="urlParam.search_member" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item label="状态">
        <select-status
          v-model="urlParam.search_status"
          statusName="order"
          :style="{width: '100px'}"
        ></select-status>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$refs.tableBuilder.getData(1)">查询</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="order/table"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="80"
      :actionMultiButtons="tableProps.tableActionMultiButtons"
      :tableRowClassName="tableRowClassName"
      :expand="true"
    >
      <template slot="expandContent" slot-scope="row" class="demo-table-expand">
        <el-table
          :data="row.data.items"
          style="width: 100%"
          :row-style="{background: '#f4f4f5', fontSize: '13px', color: '#525fe1'}"
          :show-header="false"
        >
          <el-table-column prop="item_type" label="类型" width="170">
            <template slot-scope="scope">
              <span style="margin-left:30px;">类型: {{ scope.row.item_type }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="item_name" label="名称" >
            <template slot-scope="scope">名称: ￥{{ scope.row.item_name }}</template>
          </el-table-column>
          <el-table-column prop="remark" label="备注"></el-table-column>
          <el-table-column prop="num" label="课时" width="100">
            <template slot-scope="scope">课时: {{ scope.row.courses_quantity }}</template>
          </el-table-column>
          <el-table-column prop="deal_price" label="金额" width="150">
            <template slot-scope="scope">金额: ￥{{ scope.row.deal_price }}</template>
          </el-table-column>
  
        </el-table>
      </template>
    </table-builder>
    <el-dialog title="订单详情" :visible.sync="dialogDetailVisible" width="500px">
      <ul class="details-list">
        <li>类型 : {{ detailData.type }}</li>
        <li>订单号 : {{ detailData.sn }}</li>
        <li>交易号 : {{ detailData.pay_sn }}</li>
        <!-- <li>学员名 : {{ detailData.member.nick_name + ' ('+ detailData.member.mobile + ')' }}</li> -->
        <!-- <li>成单人 : {{ detailData.user_name }}</li> -->
        <li>总金额 : {{ detailData.total_price }}</li>
        <li>付款金额 : {{ detailData.pay_money }}</li>
        <li>付款方式 : {{ detailData.pay_type }}</li>
        <li>付款时间 : {{ detailData.paid_at }}</li>
        <li>成单时间 : {{ detailData.created_at }}</li>
        <li>状态 : {{ detailData.status }}</li>
      </ul>
    </el-dialog>
  </section>
</template>

<script>
import TableBuilder from "@/components/TableBuilder";
import selectBuilder from "@/components/SelectBuilder";
import selectStatus from "@/components/SelectStatus";
// import Clipboard from "@/components/Clipboard";
export default {
  components: { selectBuilder, selectStatus, TableBuilder, 
  // Clipboard 
  },
  data() {
    return {
      // table相关的参数
      tableProps: {
        fields: [
          { title: "类型", name: "type", width: "100" },
          // {title :'编号' , content:(row)=>{ return (<i style="fontSize:12px">{row.sn}</i>) } },
          // {title :'编号' , content:(row)=>{ return <clipboard text={row.sn}/> } },
          { title: "订单号", name: "sn", width: "140"  },
          // {title :'报单人' , name:"user_name" , width:'100' },
          {
            title: "学员",
            width: "200",
            content: row => {
              return (
                <span>
                  {row.member.nick_name + " (" + row.member.mobile + ")"}
                </span>
              );
            }
          },
          { title: "金额", name: "total_price", width: "100" },
          { title: "付款方式", name: "pay_type", width: "100" },
          { title: "状态", name: "status", width: "100" },
          { title: "备注", name: "remark" },
          {
            title: "时间",
            content: row => {
              return (
                <span>{new Date(row.created_at).format("MM-dd hh:mm")}</span>
              );
            },
            width: "100"
          }
        ],
        tableActionButtons: [
          // {title:'审核' ,pm:109, icon:"el-icon-check" , click:(row)=>{ this.handleApprove(row.id) } , visible:(row)=>{ return row.status=='审核中' }},
          // {title:'驳回' ,pm:110, icon:"el-icon-close" , click:(row)=>{ this.handleReject(row.id) } , visible:(row)=>{ return row.status=='审核中' }},
          {
            title: "详情",
            pm: 103,
            icon: "el-icon-info",
            click: row => {
              this.showDetails(row);
            }
          }
          // {title:'删除' ,pm:106, icon:"el-icon-delete" , click:(row)=>{ this.handleDel(row) } },
        ]
      },
      urlParam: {},
      dialogDetailVisible: false,
      detailData: {}
    };
  },
  methods: {
    handleApprove(id) {
      this.$http.post("order/approve", { ids: [id] }).then(res => {
        if (res.status == "success") {
          this.$message({ message: res.msg, type: "success" });
          this.$refs.tableBuilder.getData();
        }
      });
    },
    handleReject(id) {
      this.$http.post("order/reject", { ids: [id] }).then(res => {
        if (res.status == "success") {
          this.$message({ message: res.msg, type: "success" });
          this.$refs.tableBuilder.getData();
        }
      });
    },
    showDetails(row) {
      this.dialogDetailVisible = true;
      this.detailData = row;
    },
    handleDel: function(row) {
      this.$confirm("此操作将永久删除数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("order/delete", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
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
<style>
.el-table__expanded-cell {
  background: #f4f4f5 !important;
  padding: 5px !important;
}
</style>