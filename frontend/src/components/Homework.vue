<template>
  <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="800px" :close-on-click-modal="false">
    <el-table :data="list" style="width: 100%">
      <el-table-column prop="caption" label="内容"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="附图">
        <template slot-scope="scope">
          <template v-for="(i, index) in scope.row.images" >
            <img :src="i" style="height: 90px;" :key="index" v-if="i"/>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">关闭</el-button>
    </span>
  </el-dialog>
</template>
<script>
  export default {
    props: {
      visible: {default: false},
      title: {default: '查看作业'},
      courseId: {required: true}
    },
    data() {
      return {
        list: []
      }
    },
    watch: {
      visible: function (val) {
        if (val) {
          this.$http.fetch('course/homework',{course_id: this.courseId}).then((res) => {
            this.list = [res.data]
          });
        }
      }
    },
    methods: {
      handleClose() {
        this.$emit('update:visible', false)
      }
    }
  };
</script>
