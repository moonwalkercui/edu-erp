<template>
  <el-tooltip :content="'点击复制-> ' + text " placement="bottom" effect="light">
    <span type="text" size="medium" class="clipboard-text" @click.stop="doCopy">{{subText}}</span>
  </el-tooltip>
</template>
<script>
export default {
  props: ["text"],
  data() {
    return {
      name: "clipboard"
    };
  },
  computed: {
    subText: function() {
      return this.text.substr(0,6) + '...' + this.text.substr(-4)
    }
  },
  methods: {
    doCopy() {
      this.$copyText(this.text).then(e => {
        this.$message("已复制", e);
      }, function () {
        this.$message("复制失败");
      })
    }
//     <button type="button"
//       v-clipboard:copy="text"
//       v-clipboard:success="onCopy"
//       v-clipboard:error="onError">Copy!</button>
    // onCopy(e) {
    //   this.$message("已复制", e);
    // },
    // onError() {
    //   this.$message("复制失败");
    // }
  }
};
</script>
<style>
.clipboard-text {
  border-bottom: 1px dotted #a8a8a8;
  cursor: pointer;
  font-size:12px;
}
</style>