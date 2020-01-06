<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item >
                <el-button @click="$router.push('/statistics/proceeds')" icon="el-icon-sort">统计</el-button>
            </el-form-item>
            <el-form-item label="编号">
                <el-input v-model="urlParam.search_sn" placeholder="" style="width: 150px" clearable></el-input>
            </el-form-item>
            <el-form-item label="学员">
              <el-input v-model="urlParam.search_member" placeholder="" style="width: 150px" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
            ref="tableBuilder"
            dataUrl="proceeds/mine"
            :fields="tableProps.fields"
            :showIndex="false"
            :condition="urlParam"
            :actionButtons = "tableProps.tableActionButtons"
            :actionWidth = "100"
            :actionMultiButtons = "tableProps.tableActionMultiButtons"
            :tableRowClassName = "tableRowClassName"
            @handleSortChange = "handleSortChange"
            :expand = "true"
        >
            <template slot="expandContent" slot-scope="row">
                <el-table :data="row.data.money" style="width: 100%" :row-style="{background: '#f4f4f5', fontSize: '13px'}">
                    <el-table-column label="收款信息">
                        <template slot-scope="scope">
                            姓名: {{scope.row.user_receiver_name}}<br>
                            账号: {{scope.row.user_receiver}}<br>
                            时间: {{ new Date(scope.row.received_at).format("yy-MM-dd hh:mm")}}<br>
                        </template>
                    </el-table-column>
                    <el-table-column label="付款信息">
                        <template slot-scope="scope">
                            姓名: {{scope.row.member_payer_name}}<br>
                            手机: {{scope.row.member_payer}}<br>
                            付款方式: {{scope.row.payment_mode}}
                        </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="备注说明"></el-table-column>
                    <el-table-column prop="money_received" label="收款金额(元)" width="110"></el-table-column>
                    <el-table-column prop="verify_status" label="认款状态"></el-table-column>
                    <el-table-column label="认款信息">
                        <template slot-scope="scope">
                            姓名: {{scope.row.verify_user_name}}<br>
                            账号: {{scope.row.verify_user}}<br>
                            时间: {{scope.row.verify_at}}
                        </template>
                    </el-table-column>
                </el-table>
            </template>
        </table-builder>
      <MoneyReceived :visible.sync="dialogFormVisible" :info="dataForm"/>
    </section>
</template>
<script>
// import selectBuilder from "@/components/SelectBuilder";
import TableBuilder from "@/components/TableBuilder";
import MoneyReceived from "@/components/MoneyReceived";
import Clipboard from "@/components/Clipboard";
export default {
  components: {  TableBuilder, MoneyReceived, Clipboard }, //selectBuilder,
  data() {
    return {
      dialogFormVisible: false,
      dataForm: {},
      tableProps: {
        fields: [
          // { title :'编号', content:(row)=>{ return (<i style="fontSize:12px">{row.sn}</i>) } },
          {
            title: "编号",
            content: row => {
              return <Clipboard text={row.sn} />;
            }
          },
          { title: "收款类型", name: "target_type", width: "80" },
          { title: "学员姓名", name: "member_name", sortable: true },
          { title: "手机号", name: "member_mobile", width: "110" },
          {
            title: "应收款",
            sortable: true,
            width: "110",
            content: row => {
              return <span>￥{row.money_receivable}</span>;
            }
          },
          {
            title: "实收款",
            content: row => {
              let icon = ''
              if(row.money_received_count > 0) {
                icon = <el-tooltip content="待认款" placement="bottom" effect="light"><i class="el-icon-warning" style="color:#f56c6c"></i></el-tooltip>
              }
              return <span>￥{row.money_received} {icon}</span>;
            }
          },
          {
            title: "尾款",
            content: row => {
              return <span>￥{row.money_receivable - row.money_received}</span>;
            }
          },
          {
            title: "交款截止日",
            content: row => {
              return row.to_pay_at ? (
                <span>{new Date(row.to_pay_at).format("MM-dd")}</span>
              ) : ('');
            }
          },
          {
            title: "发起时间",
            content: row => {
              return (
                <span>{new Date(row.created_at).format("MM-dd hh:mm")}</span>
              );
            }
          }
        ],
        tableActionButtons: [
          {
            title: "追加收款",
            pm: 189,
            icon: "el-icon-plus",
            click: row => {
              this.handleInc(row);
            }
          }
        ]
      },
      urlParam: {
        sort_by: "",
        sort_type: "",
        search_user: "",
        search_member: "",
        search_sn: "",
        search_division: this.$cookie.fetchJson("_userInfo").division
      }
    };
  },
  methods: {
    tableRowClassName({ row }) {
      if (row.money_received >= row.money_receivable) {
        return "is-finish";
      } else {
        return "is-warning";
      }
    },
    handleInc(row) {
      this.dataForm = {
        sn: row.sn,
        money: row.money_receivable - row.money_received,
        name: row.member_name,
        mobile: row.member_mobile
      };
      this.dialogFormVisible = true;
    },
    handleSortChange(params) {
      this.urlParam.sort_type = params.sort_type;
      switch (params.sort_by) {
        case "应收款":
          this.urlParam.sort_by = "receivable";
          break;
        case "学员姓名":
          this.urlParam.sort_by = "member";
          break;
        default:
          this.urlParam.sort_by = "";
          break;
      }
      if (this.urlParam.sort_by != "") this.$refs.tableBuilder.getData();
    },
    handleButtonVisible(row) {
      if (row.money_received >= row.money_receivable) return false;
      else return true;
    },
    refresh() {
      this.$refs.tableBuilder.getData();
    }
  }
};
</script>
