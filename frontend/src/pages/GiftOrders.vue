<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <helper key-id='giftlist' float='right'/>
      <el-form-item label="学员">
        <select-builder table="member" v-model="urlParam.search_member" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item label="礼品">
        <select-builder table="gift" v-model="urlParam.search_gift" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="onSearchSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <table-builder
      ref="tableBuilder"
      data-url="gift/orders"
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
import selectBuilder from "@/components/SelectBuilder";
export default {
  components: { selectBuilder, TableBuilder},
  data() {
    return {
      // table相关的参数
      tableProps: {
        fields: [
          {
            title: "申请人",
            content: row => {
              return <span>{row.member.nick_name}</span>;
            }
          },
          { title: "申请时间", name: "created_at" },
          {
            title: "礼品名称",
            content: row => {
              return (
                <span>{row.gift.name}</span>
              );
            }
          },
          { title: "申请数", name: "gift_num" },
          { title: "小星星数", name: "points" },
          { title: "状态", name: "stage" },
        ],
        tableActionButtons: [
          {
            title: "确认发放",
            visible: function(row){
              return row.stage == '申请中';
            },
            pm: 77,
            icon: "el-icon-check",
            click: row => {
              this.handleApprove(row);
            }
          },
          {
            title: "驳回",
            visible: function(row){
              return row.stage == '申请中';
            },
            pm: 72,
            icon: "el-icon-close",
            click: row => {
              this.handleReject(row);
            }
          }
        ]
      },
      urlParam: {
        search_name: "",
        search_gift: "",
      }
    };
  },
  mounted() {},
  methods: {
    handleUploadedImage(image) {
      this.dataForm.image = image;
    },
    onSearchSubmit() {
      this.$refs.tableBuilder.getData(1);
    },
    handleCurrentChange(val) {
      this.urlParam.page = val;
      this.firstOpen || this.$refs.tableBuilder.getData(); // 有页码的页码会发送两次请求，第一次过滤掉。
    },
    handleApprove: function(row) {
      this.$confirm("通过申请并发放礼品?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("gift/approve", { id: row.id }).then(res => {
            if (res.status == "success") {
              this.$message({ message: res.msg, type: "success" });
              this.$refs.tableBuilder.getData();
            }
          });
        })
        .catch(() => {});
    },
    handleReject: function(row) {
      this.$confirm("确认驳回并退还小星星?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http.post("gift/reject", { id: row.id }).then(res => {
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
