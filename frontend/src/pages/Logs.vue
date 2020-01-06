<template>
  <section>
    <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
        <el-form-item label="管理员">
            <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
        </el-form-item>
        <el-form-item label="类型">
            <select-builder table="logtype" v-model="urlParam.search_type" :style="{width: '150px'}"></select-builder>
        </el-form-item>
        <el-form-item>
            <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询</el-button>
        </el-form-item>
    </el-form>

    <table-builder
      ref="tableBuilder"
      data-url="setting/getlogs"
      :fields="tableProps.fields"
      :showIndex="false"
      :condition="urlParam"
      :actionButtons="tableProps.tableActionButtons"
      :actionWidth="150"
    ></table-builder>
  </section>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
// import imageUploader from "@/components/ImageUploader";
import selectBuilder from '@/components/SelectBuilder'
export default {
  components: { TableBuilder,selectBuilder },
  data() {
    return {
      tableProps: {
        fields: [
          { title: "时间", name: "created_at" , width: "190"},
          { title: "管理员账号", name: "username" , width: "120"},
          { title: "类型", name: "type" , width: "120"},
          { title: "备注", name: "remark"},
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
</style>