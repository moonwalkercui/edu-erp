<template>
    <span>
        <span :style="{fontSize: smallFontSize + 'px'}">￥</span>
        <span :style="{fontSize: bigFontSize + 'px'}">{{int}}</span>
        <span :style="{fontSize: smallFontSize + 'px'}">.{{float}}</span>
    </span>
</template>
<script>
  export default {
    props: {
      value: {required: true},
      size: {type: String},
    },
    data() {
      return {
        name: '金额格式化',
        bigFontSize: 0,
        smallFontSize: 0,
        int: '0',
        float: '00',
      }
    },
    watch: {
      value: function(val) {
        let newVal = (parseFloat(val).toFixed(2) + '').split(".")
        this.int = newVal[0];
        this.float = newVal[1];
      }
    },
    created() {
      switch (this.size) {
        case 'big':
          this.bigFontSize = 18;
          this.smallFontSize = 14;
          break;
        case 'small':
          this.bigFontSize = 14;
          this.smallFontSize = 12;
          break;
        default:
          this.bigFontSize = 16;
          this.smallFontSize = 13;
      }
    }
  };
</script>
