<template>
    <div class="draggable-list">
        <!--<SlickList lockAxis="x" axis="x" v-model="images">-->
        <SlickList lockAxis="x" axis="x" v-model="images">
            <SlickItem v-for="(item, index) in images" :index="index" :key="index">
                <div class="images-list">
                    <el-tooltip content="删除" placement="bottom" effect="light">
                        <img v-if="item.url" :src="item.url" width="100" height="100">
                        <div slot="content">
                            <i class="el-icon-close" style="color: #8a8a8a; cursor: pointer" v-on:click="handleRemove(index)">删除</i>
                        </div>
                    </el-tooltip>
                </div>
            </SlickItem>

        </SlickList>
        <el-upload
                class="avatar-uploader"
                :action="action"
                :show-file-list="false"
                :http-request="handleUploadImage"
                v-if="images.length < limit"
        >
            <i class="el-icon-plus image-uploader-icon"></i>
        </el-upload>
    </div>
</template>
<script>
  import { SlickList, SlickItem } from 'vue-slicksort';
  import {makeUploadUrl}  from "@/utils/global";
  export default {
    props:{
      imageList: {
        type: Array
      },
      limit: {
        type: Number,
        default: 5
      }
    },
    components: { SlickList, SlickItem }, // imageUploader
    watch: {
      imageList: function (val) {
        this.images = val;
      },
    },
    data() {
      return {
        action: makeUploadUrl(true),
        images: []
      };
    },
    created() {
    },
    methods: {
      handleUploadImage(obj){
        this.$util.uploadImg(obj.file, "productalbum" ,obj.action, (result)=>{
          if(result.status === 'error')
            this.$message.error(result.msg);
          else{
            this.$message({
              message: result.msg,
              duration: 800
            });
            // 上传后 图片列表元素自动会有2个参数status (success) 和uid，现在加上 name 和 url
            this.images.push({
              'name' : obj.file.name,
              'url': result.url,
              'size': result.size
            });
            this.$emit('upload',this.images);
          }
        });
//                console.log(obj);
      },
      handleRemove(index) {
        this.images.splice(index, 1);
        this.$emit('upload',this.images);
      },
    },
  }
</script>
<style>
    .draggable-list {
        min-width: 630px;
    }
    .draggable-list div, .avatar-uploader{
        display: inline-block;
    }
    .draggable-list .images-list img {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        width: 100px;
        margin-right: 10px;
    }
    .draggable-list .images-list .delete-button {
        color: #0BB20C;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #525fe1;
    }
    .avatar-uploader .image-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px!important;
        text-align: center;
    }

</style>
