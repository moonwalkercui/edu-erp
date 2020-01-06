<template>
  <el-button :size = "size" type="primary" @click="handleReq" :disabled.sync="disable">{{ text }}</el-button>
</template>
<script>
export default {
  props: {
    text: {
      type: String,
      default: "提 交"
    },
    size: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      disable: false
    };
  },
  created() {},
  methods: {
    handleReq() {
      this.$util.buttonDisabler(this, "disable");
      this.$emit("handleReq");
    }
  }
};
</script>