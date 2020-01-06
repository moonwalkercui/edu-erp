<template>
    <section>
        <el-form
            :model="urlParam"
            :inline="true"
            ref="searchForm"
            class="demo-form-inline"
            size="mini"
        >
            <helper key-id='orderrefund' float='right'/>
            <el-form-item label="订单号" prop="search_order_sn">
                <el-input
                    v-model="urlParam.search_order_sn"
                    placeholder=""
                    style="width: 150px"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="退款号" prop="search_sn">
                <el-input
                    v-model="urlParam.search_sn"
                    placeholder=""
                    style="width: 150px"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
            ref="tableBuilder"
            data-url="order/refunds"
            :fields="tableProps.fields"
            :condition="urlParam"
            :actionButtons="tableProps.tableActionButtons"
            :actionWidth="180"
        ></table-builder>
    </section>
</template>
<style>
</style>
<script>
import TableBuilder from "@/components/TableBuilder";
export default {
  components: { TableBuilder },
  data() {
    return {
      // table相关的参数
      tableProps: {
        fields: [
          {
            title: "订单号",
            content: row => {
              return <span>{row.order.sn}</span>;
            }
          },
          { title: "退款号", name: "refund_sn" },
          { title: "支付金额", name: "total_fee" },
          { title: "退款金额", name: "refund_fee" },
          { title: "时间", name: "created_time" },
           {
            title: "状态",
            content: row => {
              return <span>{row.status} {row.reject_reason}</span>;
            }
          },
        ],
        tableActionButtons: [
          {
            title: "同意",
            pm: 71,
            icon: "el-icon-check",
            click: row => {
              this.handleAccept(row);
            },
            visible: row => {
              return row.status == "退款中";
            }
          },
          {
            title: "驳回",
            pm: 72,
            icon: "el-icon-close",
            click: row => {
              this.handleReject(row);
            },
            visible: row => {
              return row.status == "退款中";
            }
          }
        ]
      },

      urlParam: {
        search_order_sn: "",
        search_sn: ""
      }
    };
  },
  methods: {
    onSearchSubmit() {
      this.$refs.tableBuilder.getData(1);
    },
    handleCurrentChange(val) {
      this.urlParam.page = val;
      this.firstOpen || this.$refs.tableBuilder.getData(); // 有页码的页码会发送两次请求，第一次过滤掉。
    },
    handleAccept: function(row) {
      this.$confirm("确认退款?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post('order/refund',{id: row.id, type: "accept" }).then((res) => {
              if(res.status == 'success'){
                  this.$message({ message: res.msg, type: 'success' });
                  this.$refs.tableBuilder.getData();
              }
          });
        })
        .catch(() => {});
    },
    handleReject: function(row) {
      this.$prompt("请输入描述驳回原因", "驳回", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
            this.$http.post("order/refund", {id: row.id, type: "reject", reason: value })
                .then(res => {
                if (res.status == "success") {
                    this.$message({ message: res.msg, type: "success" });
                    this.$refs.tableBuilder.getData();
                }
            });
        })
        .catch(() => {
        });

    //   this.$confirm("确认驳回退款?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
          
    //     })
    //     .catch(() => {});
    }
  }
};
</script>
