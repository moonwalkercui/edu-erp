<template>
  <section>
     <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
        <el-form-item label="优惠券">
          <select-builder table="voucher" v-model="urlParam.voucher" :style="{width: '150px'}" clearable></select-builder>
        </el-form-item>
        <el-form-item label="订单号">
            <el-input v-model="urlParam.order_sn" placeholder="" style="width: 150px" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
            <el-input v-model="urlParam.order_sn" placeholder="" style="width: 150px" clearable></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
        </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="voucher/log"
      :fields="tableProps.fields"
      :showIndex="true"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
      :showPaginate="false"
    ></table-builder>

  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
import selectBuilder from "@/components/SelectBuilder";
export default {
  components: { TableBuilder, selectBuilder },
  data() {
    return {
      tableProps: {
        fields: [
          {title :'名称' ,
            content:(row)=>{ return (<span>{row.voucher.name} 满{row.voucher.catch_price}减{row.voucher.price}</span>) }
          },
          {title :'学员' ,
            content:(row)=>{ return (<span>{row.member.nick_name}</span>) }
          },
          { title: "领取时间", name: "got_time" },
          { title: "使用时间", name: "use_time" },
          { title: "状态", name: "be_used" },
          { title: "用于订单号", name: "order_sn" },
        ],
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
.el-tree-node__content {
  padding: 6px 5px;
  border-bottom: 1px solid #efefef;
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
  width: 38%;
}
.el-transfer-panel__body,
.el-transfer-panel__list {
  height: 340px;
}
.el-transfer-panel__list.is-filterable {
  height: 295px;
}
</style>