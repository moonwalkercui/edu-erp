<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
      <helper key-id='redpacket' float='right'/>
      <el-form-item>
        <el-button type="primary" icon="el-icon-s-tools" @click="$router.push('/order/create')" v-if="$util.hasPermission(326)">红包规则</el-button>
      </el-form-item>
      <el-form-item size="mini" label="订单号">
        <el-input v-model="urlParam.search_sn" placeholder="" :style="{width: '100px'}"></el-input>
      </el-form-item>
      <el-form-item label="学员">
        <select-builder table="member" v-model="urlParam.search_member" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item label="推荐人">
        <select-builder table="member" v-model="urlParam.search_referral" :style="{width: '150px'}"></select-builder>
      </el-form-item>
      <el-form-item label="原因">
        <select-status
          v-model="urlParam.search_stage"
          statusName="red_packet_stage"
          :style="{width: '100px'}"
        ></select-status>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$refs.tableBuilder.getData(1)">查询</el-button>
      </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="share/redpacketlog"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
      :showPaginate="false"
    ></table-builder>

  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder"
import selectBuilder from "@/components/SelectBuilder";
import selectStatus from "@/components/SelectStatus";
export default {
  components: { TableBuilder, selectBuilder, selectStatus },
  data() {
    return {
      tableProps: {
        fields: [
          { title: '学员', content:(row)=>{return (<span>{row.member.nick_name}</span>)}},
          { title: "变动", name: "amount" },
          { title: "变动原因", name: "stage" },
          { title: "日期", name: "created_at" },
          { title: '备注', content:(row)=>{return (<span>
            {row.order ? "订单号" + row.order.sn : ""}
            {row.referral ? "推荐人" + row.referral.nick_name : ""}
            {row.expired_at ? " 红包失效时间" + row.expired_at : ""}
          </span>)}},
        ]
      },
      data: [],
      urlParam: {
      },
    };
  },
  created() {
  },
  methods: {
  }
};
</script>
<style>
</style>