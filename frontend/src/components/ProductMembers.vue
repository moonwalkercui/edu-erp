<template>
  <div>
    <el-dialog
      title="预定该课程的学员列表"
      :visible.sync="visible"
      width="800px"
      :before-close="handleClose"
      :close-on-click-modal="true"
    >
      <table-builder
        ref="tableBuilder"
        data-url="member/ranking"
        :fields="tableProps.fields"
        :showIndex="false"
        :condition="urlParam"
        :actionButtons="tableProps.tableActionButtons"
        :actionMultiButtons="tableProps.tableActionMultiButtons"
        @handleSortChange="handleSortChange"
      ></table-builder>
    
    </el-dialog>
  </div>
</template>
<script>
import TableBuilder from "@/components/TableBuilder";
export default {
  components: { TableBuilder },
  props: {
    visible: { default: false },
    productId: {
      type: Number
    }
  },
  data() {
    return {
      tableProps: {
        fields: [
          {
            title: "会员",
            width: 150,
            content: row => {
              return <span>{row.member.nick_name}</span>;
            }
          },
          {
            title: "手机号",
            width: 130,
            content: row => {
              return <span>{row.member.mobile}</span>;
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
        ]
      },
      urlParam: {
        search_product: ""
      }
    };
  },
  watch: {
    productId: function(newVal) {
      if(newVal != 0 ) {
        this.urlParam.search_product = newVal
        this.$nextTick(() => {
          this.$refs.tableBuilder.getData();
        })
      }
    },
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
    handleClose() {
      this.$emit("update:visible", false);
    }
  }
};
</script>