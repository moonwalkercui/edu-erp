<template>
  <el-dialog title="数据导入" :visible.sync="visible" :before-close="handleClose" width="500px" :close-on-click-modal="false">

    <el-upload
      class="upload-demo"
      ref="upload"
      :action="uploadUrl"
      :file-list="fileList"
      :auto-upload="false"
      :on-change="handleChange"
      :limit = '1'
      :http-request="diyRequest"
      >
      <el-button slot="trigger" size="small" type="primary">第一步: 选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">第二步: 导入数据</el-button>
      <div slot="tip" class="el-upload__tip">只能上传Excel文件, 且不超过2M.</div>
    </el-upload>

     <el-button type="text" @click="$exportExcel(reqUrl, {type})">点击此处下载Excel模板文件, 编辑后再导入(表头勿更改).</el-button>
  </el-dialog>
</template>
<script>
  import { makeImportXlsUrl } from "@/utils/global";
  export default {
    props: {
      visible: {default: false},
      type: { type: String }
    },
    data() {
      return {
        fileList: [],
        reqUrl: makeImportXlsUrl(),
        uploadUrl: makeImportXlsUrl(true) + '?type=' + this.type,
      }
    },
    created() {
      // this.uploadUrl = makeImportXlsUrl(true)  + '?uploadtype=' + this.type;
      // console.log(this.uploadUrl)
    },
    methods: {
      handleChange(file, fileList) {
          console.log(file)
          // console.log(fileList)
          this.fileList = fileList;
      },
      submitUpload() {
        if(this.fileList.length == 0) {
          this.$message.error('请先选取文件');
        } else {
          this.$refs.upload.submit();
        }
      },
      handleClose() {
        this.$emit('update:visible', false)
      },
      diyRequest(obj) {
        // console.log(obj.file)
        // // this.$refs.upload.submit();
        // this.$http.post(this.reqUrl + '?uploadtype=' + this.type, { file: obj.file})
        //   .then(res => {
        //     console.log(res)
        //   });
        // console.log(obj.action)
        // return;
        this.$util.uploadExcel(obj.file, 'excelfile', obj.action, (result)=>{
          // console.log(result)
          if(result.status === 'error')
            this.$message.error(result.msg);
          else{
            this.$message({
              message: result.msg,
              duration: 4000
            });
            // 上传后 图片列表元素自动会有2个参数status (success) 和uid，现在加上 name 和 url
            // this.images.push({
            //   'name' : obj.file.name,
            //   'url': result.url,
            //   'size': result.size
            // });
            // this.$emit('upload',this.images);
          }
        });
      },

    }
  };
</script>
