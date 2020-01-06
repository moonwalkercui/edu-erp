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
                title="编辑头像"
                :visible.sync="dialogVisible"
                width="800px"
                :before-close="handleClose">
            <div class="cropper" style="height: 500px;">
                <vueCropper
                        ref="cropper"
                        :img="data.img"
                        :outputSize="data.size"
                        :outputType="data.outputType"
                        :info="data.info"
                        :canScale="data.canScale"
                        :autoCrop="data.autoCrop"
                        :autoCropWidth="data.autoCropWidth"
                        :autoCropHeight="data.autoCropHeight"
                        :fixed="data.fixed"
                        :fixedNumber="data.fixedNumber"
                ></vueCropper>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <!-- <el-button type="primary" @click="handleUpload">保 存</el-button> -->
            <reqButton @handleReq = "handleUpload"/>
          </span>
        </el-dialog>
    </div>
</template>
<script>
  import vueCropper from 'vue-cropper'
    import {makeUploadUrl}  from "@/utils/global";
    export default {
        components: { vueCropper },
        props:["imageUrl"],
        data() {
            return {
                dialogVisible: false,
                image:'',
                action: makeUploadUrl(),
                data: {
                    img: '',
                    info: true,
                    size: 1,
                    outputType: 'jpeg',
                    canScale: false,
                    autoCrop: true,
                    // 只有自动截图开启 宽度高度才生效
                    autoCropWidth: 300,
                    autoCropHeight: 300,
                    // 开启宽度和高度比例
                    fixed: true,
                    fixedNumber: [1, 1]
                }
            };
        },
        created(){
        },
        watch: {
            imageUrl: function (val) {
                this.image = val;
            },
        },
        methods: {
            handleUpload () {
                this.$refs.cropper.getCropData((data) => {
                    // 直接上传base64
                    this.$http.post( this.action , { action:"uploadavatar", image_data : data } ).then((result) => {
                        if(result.status == 'error') {
                            this.$message.error(result.msg);
                        }
                        else{
//                            this.$message.success(result.msg);
                            this.image = result.url;
                            this.$emit('upload',this.image);
                            this.dialogVisible = false;
                        }
                    });
//                  以下是  base64转file 然后上传 的方法
//                    var arr = data.split(','), mime = arr[0].match(/:(.*?);/)[1],
//                        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
//                    while(n--){
//                        u8arr[n] = bstr.charCodeAt(n);
//                    }
//                    var file = new File([u8arr], '_filename', {type:mime});
//
//                    uploadImg(file, "useravatar" ,this.action, (result)=>{
//                        if(result.status == 'error') {
//                            this.$message.error(result.msg);
//                        }
//                        else{
//                            this.$message.success(result.msg);
//                            this.image = result.url;
//                            this.$emit('upload',this.image);
//                            this.dialogVisible = false;
//                        }
//                    });
                })
            },
            showCooper(obj){
                this.handleChooseImg(obj.file);
                this.dialogVisible = true;
            },
            handleClose(done) {
                done();
//                this.$confirm('确认关闭？') .then(_ => {  done(); }) .catch(_ => {});
            },
            handleChooseImg (file) {
                if(typeof(FileReader) === 'undefined'){
                    this.$message.error('抱歉，你的浏览器版本过低，请升级后再操作！');
                    return;
                }
                if (!(file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif')) {
                    this.$message.error('上传的图片须是 JPG、PNG 或 GIF 格式');
                    return ;
                }
                if (file.size / 1024 / 1024 > 2) {
                    this.$message.error('上传的图片大小不能超过 2MB');
                    return ;
                }
                var _this = this;
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function(){
                    _this.data.img = this.result;
                };

            }
        }
    }
</script>