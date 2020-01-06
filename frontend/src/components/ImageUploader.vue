<template>
  <div>
    <el-upload
      class="avatar-uploader"
      :action="action"
      :http-request="showCooper"
      :show-file-list="false"
    >
      <img v-if="image" :src="imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    <el-dialog
      :modal="false"
      title="上传图片"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
    >
      <div class="cropper" style="height: 500px;">
        <!-- <vueCropper
          ref="cropper"
          :img.sync="option.img"
          :outputSize="data.size"
          :outputType="data.outputType"
         
          :canScale="data.canScale"
          :autoCrop="autoCrop"
          :autoCropWidth="cropWidth"
          :autoCropHeight="cropHeight"
          :fixed="data.fixed"
          :fixedNumber="data.fixedNumber"
        ></vueCropper> -->
           <vueCropper
          ref="cropper"
          :img="option.img"
          :outputSize="option.size"
          :outputType="option.outputType"
          :autoCrop="autoCrop"
          :autoCropWidth="cropWidth"
          :autoCropHeight="cropHeight"
          :fixed="option.fixed"
          :fixedNumber="option.fixedNumber"
        ></vueCropper>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="handleUpload">上 传</el-button> -->
        <reqButton @handleReq = "handleUpload" text="上 传"/>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { VueCropper  } from "vue-cropper";
import {makeUploadUrl}  from "@/utils/global";
export default {
  components: { VueCropper },
  props: {
    imageUrl: { default: "" },
    cropWidth: { default: 350 }, // 只有自动截图开启 宽度高度才生效
    cropHeight: { default: 350 },
    autoCrop: { default: false },
    outputType: { default: "png" },
  },
  data() {
    return {
      dialogVisible: false,
      image: "",
      image2: "",
      action: makeUploadUrl(),
      option: {
        img: "",
        size: 0.8,
        outputType: this.outputType,
        info: true,
        fixed: true,
        fixedNumber: [this.cropWidth, this.cropHeight]
      },
      data: {
        img: "",
        info: true,
        size: 1,
        outputType: this.outputType,
        canScale: false,
        // autoCrop: false,
        // 只有自动截图开启 宽度高度才生效
        // autoCropWidth: this.cropWidth,
        // autoCropHeight: this.cropHeight,
        // 开启宽度和高度比例
        fixed: true,
        fixedNumber: [this.cropWidth, this.cropHeight]
      }
    };
  },
  created() {
    this.option.img = this.imageUrl;
    this.image = this.imageUrl;
  },
  watch: {
    imageUrl: function(val) {
      this.image = val;
    }
  },
  methods: {
    handleUpload() {
      this.$refs.cropper.getCropData(data => {
        // 直接上传base64
        this.$http
          .post(this.action, { action: "uploadavatar", image_data: data })
          .then(result => {
            if (result.status == "error") {
              this.$message.error(result.msg);
            } else {
              this.image = result.url;
              this.$emit("upload", this.image);
              this.dialogVisible = false;
            }
          });
      });
    },
    showCooper(obj) {
      this.handleChooseImg(obj.file);
      this.dialogVisible = true;
    },
    handleClose(done) {
      done();
      //                this.$confirm('确认关闭？') .then(_ => {  done(); }) .catch(_ => {});
    },
    handleChooseImg(file) {
      if (typeof FileReader === "undefined") {
        this.$message.error("抱歉，你的浏览器版本过低，请升级后再操作！");
        return;
      }
      if (
        !(
          file.type === "image/jpeg" ||
          file.type === "image/png" ||
          file.type === "image/gif"
        )
      ) {
        this.$message.error("上传的图片须是 JPG、PNG 或 GIF 格式");
        return;
      }
      if (file.size / 1024 / 1024 > 2) {
        this.$message.error("上传的图片大小不能超过 2MB");
        return;
      }
      var _this = this;
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function() {
        _this.option.img = this.result;
      };
    }
  }
};
</script>