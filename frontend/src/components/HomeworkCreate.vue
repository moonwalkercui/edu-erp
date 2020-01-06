<template>
  <el-dialog :title="title" :visible.sync="visible" :before-close="handleClose" width="800px" :close-on-click-modal="false">
      <el-form :model="ruleForm" size="medium" ref="ruleForm" label-width="100px" style="width:90%">

          <el-form-item label="作业内容" prop="caption">
            <el-input type="textarea" v-model="ruleForm.caption"></el-input>
          </el-form-item>
          <el-form-item label="备注说明">
            <el-input type="textarea" v-model="ruleForm.remark"></el-input>
          </el-form-item>
          <el-form-item label="附加图片">
            <image-draggable-uploader :imageList="uploadImgs" :limit="3" @upload="handleUploadedImage"/>
          </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <!-- <el-button type="primary" @click="submitForm()">提 交</el-button> -->
        <reqButton @handleReq = "submitForm"/>
      </span>
  </el-dialog>
</template>
<script>
  // import _ from 'lodash'
  import {makeUploadUrl}  from "@/utils/global";
  import imageDraggableUploader from '@/components/ImageDraggableUploader'
  export default {
    components: {imageDraggableUploader},
    props: {
      visible: {default: false},
      title: {default: '布置作业'},
      courseId: {required: true}
    },
    data() {
      return {
        uploadUrl: makeUploadUrl(),
        uploadImgs: [],
        ruleForm: {
          course_id: '',
          caption: '',
          images: [],
          remark: '',
        }
      }
    },
    watch: {
      visible: function (val) {
        if (val) {
          this.$http.fetch('course/homework',{course_id: this.courseId}).then((res) => {
            this.ruleForm.course_id = this.courseId;
            this.ruleForm.caption = res.data.caption;
            this.ruleForm.images = res.data.images;
            var imgs = [];
            for(var key in res.data.images) {
              imgs.push({url : res.data.images[key]});
            }
            this.uploadImgs = imgs;
            this.ruleForm.remark = res.data.remark;
          });
        }
      }
    },
    methods: {
      submitForm() {
        this.$http.post("course/homeworkcreate", {id: this.courseId, ...this.ruleForm}).then((res) => {
          if (res.status == 'success') {
            this.$message.success(res.msg);
            this.handleClose();
            this.$parent.refresh();
          }
        });
      },
      handleUploadedImage(images) {
        var imgs = [];
        for(var key in images) {
          imgs.push(images[key].url);
        }
        this.ruleForm.images = imgs;
      },
      handleClose() {
        this.$emit('update:visible', false)
      }
    }
  };
</script>
