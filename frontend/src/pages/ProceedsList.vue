<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <helper key-id='proccedlist' float='right'/>
            <el-form-item >
                <el-button v-if="$util.hasPermission(313)" @click="$exportExcel('proceeds/table',urlParam)" icon="el-icon-download">导出</el-button>
            </el-form-item>
            <el-form-item label="交易号">
                <el-input v-model="urlParam.search_sn" placeholder="" style="width: 150px" clearable></el-input>
            </el-form-item>
            <el-form-item label="学员手机号">
              <el-input v-model="urlParam.search_mobile" placeholder="" style="width: 150px" clearable></el-input>
                <!-- <select-builder table="member" v-model="urlParam.search_member" :style="{width: '150px'}"></select-builder> -->
            </el-form-item>
            <el-form-item label="收款人">
              <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
                <!-- <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder> -->
            </el-form-item>
              <el-form-item label="门店" prop="search_division">
                <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
            </el-form-item>
             <!-- <el-form-item label="付款人">
              <el-input v-model="urlParam.search_payer" placeholder="" style="width: 150px" clearable></el-input>
            </el-form-item> -->
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
            ref="tableBuilder"
            dataUrl="proceeds/table"
            :fields="tableProps.fields"
            :showIndex="false"
            :condition="urlParam"
            :actionButtons = "tableProps.tableActionButtons"
            :actionWidth = "150"
            :actionMultiButtons = "tableProps.tableActionMultiButtons"
            :tableRowClassName = "tableRowClassName"
            @handleSortChange = "handleSortChange"
            :expand = "true"
        >
            <template slot="expandContent" slot-scope="row">
                <el-table :data="row.data.log" style="width: 100%" :row-style="{background: '#f4f4f5', fontSize: '13px', color: '#525fe1'}">
                    <el-table-column prop="name" label="收款记录:姓名"></el-table-column>
                    <el-table-column prop="mobile" label="手机号" width="110"></el-table-column>
                    <el-table-column prop="money" label="金额"></el-table-column>
                    <el-table-column prop="payment_mode" label="付款方式"></el-table-column>
                    <el-table-column prop="remark" label="备注"></el-table-column>
                    <el-table-column prop="user_name" label="收款人"></el-table-column>
                    <el-table-column prop="created_at" label="收款时间"></el-table-column>
                </el-table>
            </template>
        </table-builder>
      <MoneyReceived :visible.sync="dialogFormVisible" :info="dataForm"/>

      <el-dialog title="请核对金额后并确认款已到账" :visible.sync="confirmVisible" width="700px">
        <br/>
        <el-form size="medium" label-width="120px" :model="confirmData" :rules="confirmRules" ref="confirmForm">
            <el-form-item label="到款金额">
                <label style="font-size: 130%; color:#525fe1;">￥{{confirmData.money}}</label>
            </el-form-item>
            <el-form-item label="收款门店" prop="division">
                <select-builder table="division" v-model="confirmData.division" :style="{width: '80%'}" size="middle"></select-builder>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="confirmVisible = !confirmVisible">取 消</el-button>
            <!-- <el-button type="primary" @click="handleConfirmMoney('confirmForm')">确认款已到账</el-button> -->
            <reqButton @handleReq = "handleConfirmMoney('confirmForm')" text="确认款已到账"/>
        </div>
    </el-dialog>
    <el-dialog title="订单详情" :visible.sync="dialogDetailVisible" width="500px">
      <ul class="details-list">
        <li>交易号 : {{ detailData.sn }}</li>
        <li>状态 : {{ detailData.status }}</li>
        <li>所属门店 : {{ detailData.division && detailData.division.name }}</li>
        <li>学员昵称 : {{ detailData.member && detailData.member.nick_name }}</li>
        <li>应收款金额 : {{ detailData.money_receivable }}</li>
        <li>支付方式 : {{ detailData.pay_type }}</li>
        <li>记录时间 : {{ detailData.created_at }}</li>
        <li>支付时间 : {{ detailData.paid_at }}</li>
        <li>认款时间 : {{ detailData.confirmed_at }}</li>
        <li>认款人 : {{ detailData.user_name }}</li>
        <li>备注信息 : {{ detailData.remark }}</li>
      </ul>
    </el-dialog>
    </section>
</template>
<script>
import selectBuilder from "@/components/SelectBuilder";
import TableBuilder from "@/components/TableBuilder";
import MoneyReceived from "@/components/MoneyReceived";
// import Clipboard from "@/components/Clipboard";
export default {
  components: { selectBuilder, TableBuilder, MoneyReceived }, // Clipboard
  data() {
    return {
      dialogFormVisible: false,
      dataForm: {},
      tableProps: {
        fields: [
          {title: "交易号", name: "sn" },
          // {
          //   title: "编号",
          //   content: row => { return <clipboard text={row.sn} />; }
          // },
          {title: '学员', content:(row)=>{return (<span>{(row.member.name!=null ? row.member.name : '') + '(' + row.member.nick_name + ')'}</span>)}},
          {title: '手机号', content:(row)=>{return (<span>{row.member.mobile}</span>)}},
          {title: '门店', content:(row)=>{return (<span>{row.division&&row.division.name}</span>)}},
          {title: "订单类型", name: "target_type" },
          {
            title: "应收款", sortable: 'custom', 
            content: row => { return <span style="float:right">{row.money_receivable}</span>; }
          },
          {
            title: "认款人",
            content: row => { return <span>{row.user_name || ''}</span>; }
          },
          {
            title: "时间",
            content: row => {
              return (
                <span>{new Date(row.created_at).format("MM-dd hh:mm")}</span>
              );
            },
            width: "100"
          },
          {title: "状态", name: "status" },
        ],
        tableActionButtons: [
          {
            title: "线下收款", pm: 320, icon: "el-icon-plus",
            click: row => { this.handleInc(row); },
            visible: (row) => row.status == '未付款',
          },
          {
            title: "认款", pm: 319, icon: "el-icon-check",
            click: row => { this.handleConfirm(row); },
            visible: (row) => row.status == '已付款',
          },
          {
            title: "详情", icon: "el-icon-info", click: row => { this.showDetails(row); },
          },
        ]
      },
      urlParam: {
        sort_by: "",
        sort_type: "",
        search_user: "",
        search_member: "",
        search_sn: "",
        search_division: ""
      },
      confirmData: {
        sn:'',
        money: 0,
        division: '',
      },
      confirmVisible: false,
      confirmRules: {
        division: [{ required: true, message: "请选择门店", trigger: "blur" }],
      },
      detailData: {},
      dialogDetailVisible: false,
    };
  },
  methods: {
    tableRowClassName({ row }) {
      if (row.status == '未付款') {
      //   return "is-finish";
      // } else {
        return "is-warning";
      }
    },
    handleInc(row) {
      this.dataForm = row;
      this.dialogFormVisible = true;
    },
    handleConfirm(row) {
      this.confirmData.sn = row.sn
      this.confirmData.money = row.money_receivable
      this.confirmVisible = true;
    },
    showDetails(row) {
      this.dialogDetailVisible = true;
      this.detailData = row;
    },
    // handleReceivedAll: function(row) {
    //   this.$confirm("确认收齐该应收款项?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   }).then(() => {
    //     this.$http.post("proceeds/verifyall", { sn: row.sn, state: 'pass'}).then(res => {
    //       if (res.status == "success") {
    //         this.$message.success(res.msg);
    //         this.$refs.tableBuilder.getData();
    //       }
    //     });
    //   }).catch(() => {});
    // },
    // handleCancel: function(row) {
    //   this.$confirm("取消后将无法进行收取操作, 是否确定继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
    //     })
    //     .catch(() => {});
    // },
    // handleVerify: function(row, sn, state) {
    //   let notice
    //   let btText
    //   switch(state) {
    //     case 'pass': notice = "确认收到金额:￥" + row.money_received + '?'; btText = '确定收款'; break;
    //     case 'reject': notice = "确认驳回收款？"; btText = '确定驳回'; break;
    //     default: notice = ''; break;
    //   }
    //   if (notice)
    //     this.$confirm(notice, "提示", {
    //       confirmButtonText: btText,
    //       cancelButtonText: "取消",
    //       type: "warning"
    //     }).then(() => {
    //       this.$http.post("proceeds/verify", { id: row.id, sn, state }).then(res => {
    //         if (res.status == "success") {
    //           this.$message.success(res.msg);
    //           this.$refs.tableBuilder.getData();
    //         }
    //       });
    //     }).catch(() => {});
    // },
    handleConfirmMoney: function(formName) {
       this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm('确认款已到账', "", {
            confirmButtonText: '提 交',
            cancelButtonText: "取 消",
            type: "warning"
          }).then(() => {
            this.$http.post("proceeds/confirm", { ...this.confirmData }).then(res => {
              if (res.status == "success") {
                this.confirmVisible = false;
                this.$message.success(res.msg);
                this.$refs.tableBuilder.getData();
              }
            });
          }).catch(() => {});
        } else {
          return false;
        }
      });
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
    refresh() {
      this.$refs.tableBuilder.getData();
    }
  }
};
</script>
