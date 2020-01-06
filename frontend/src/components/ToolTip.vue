<template>
     <el-popover
      placement="top-start"
      title=""
      width="200"
      trigger="hover"
      popper-class="tooltip-popover"
      :content="content">
      <i slot="reference" class="el-icon-question" style="color: #ccc; float:right;"></i>
    </el-popover>
</template>
<script>
    export default {
        props: {
            content:{},
            type:{default:'right'},
        },
        data() {
            return {
                name: '',
            }
        }
    };
</script>
<style>
.tooltip-popover{
  padding:10px;
  font-size: 12px;
  color: #999;
}
</style>
