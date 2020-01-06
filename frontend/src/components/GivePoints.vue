<template>
    <el-dialog
        title="奖励小星星"
        :visible.sync="visible"
        width="300px"
        :before-close="handleClose"
        :close-on-click-modal="true"
        center
    >
        <div style="text-align:center;">
            <el-rate
                v-model="value"
                :icon-classes="['el-icon-star-on big-star', 'el-icon-star-on big-star', 'el-icon-star-on big-star']"
                void-icon-class="el-icon-star-on big-star"
                :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
            ></el-rate>
        </div>
        <span slot="footer" class="dialog-footer">
            <!-- <el-button type="primary" @click="handleSubmit">确认奖励</el-button> -->
             <reqButton @handleReq = "handleSubmit" text="确认奖励"/>
        </span>
    </el-dialog>
</template>
<script>
export default {
  props: {
    visible: { default: false },
    item: { default: () => {} }
  },
  watch: {
    item: function() {
      this.value = 0
    }
  },
  data() {
    return {
      value: 0
    };
  },
  methods: {
    handleSubmit() {
      if(this.value) {
        this.$http.post("coursesign/givepoints",{
          course:this.item.course_id,
          member:this.item.member_id,
          points:this.value
          }).then((res) =>{
            if(res.status=='success') {
              this.$message.success(res.msg);
              this.handleClose()
            } else {
               this.$message.error(res.msg);
            }
        });
      } else {
        this.$message.error('小星星数至少是1个');
      }
    },
    handleClose() {
      this.$emit("update:visible", false);
    }
  }
};
</script>
<style>
.el-icon-star-on.big-star{
  font-size:30px;
}
</style>
