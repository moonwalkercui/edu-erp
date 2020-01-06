<template>
    <el-dialog
            title="发送邮件"
            :visible.sync="visible"
            width="600px"
            :before-close="handleClose">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="medium">
            <el-form-item label="接收邮箱" prop="toemail">
                <el-input v-model="form.toemail"></el-input>
            </el-form-item>
            <el-form-item label="邮件标题" prop="title" v-if="form.title !== false">
                <el-input v-model="form.title"></el-input>
            </el-form-item>
            <el-form-item label="发送内容" prop="content" v-if="form.content !== false">
                <el-input type="textarea" v-model="form.content"></el-input>
            </el-form-item>
            <el-form-item size="large" style="text-align: right">
                <el-button @click="handleClose">取 消</el-button>
                <!-- <el-button type="primary" @click="onSubmit('form')">发 送</el-button> -->
                <reqButton @handleReq = "onSubmit('form')" text="发 送"/>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>
<script>
    export default {
        props: {
            emailProps:{},
            toemail:{},
            title:{},
            type:{},
            content:{},
            visible:{},
        },
        data() {
            return {
//                dialogVisible:false,
                form:{
                    toemail:'',
                    title:'',
                    type:'',
                    content:''
                },
                rules: {
                    toemail: [
                        { required: true, message: '请输入邮箱地址', trigger: ['blur', 'change'] },
                        { type: 'email', required: true, message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ],
                    title: [
                        { required: true, message: '请输入邮件标题', trigger: 'change' }
                    ],
                    content: [
                        { required: true, message: '请填写邮件内容', trigger: 'blur' }
                    ]
                }
            }
        },
//        watch: {
//            showTransmitter: function (val) {
//                this.dialogVisible = val
//            },
//        },
        created(){
            // 发送type类型为normal时，后台验证须有title 和content
            this.form = {
                toemail:this.toemail,
                type: this.type||'normal',
                title:this.title,
                content:this.content
            };
//            console.log(this.form);
        },
        methods: {
            handleClose(){
                this.$emit('update:visible', false)
            },
            onSubmit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("sys/sentemail",this.form).then((res) =>{
                            if(res.status=='success') {
                                this.$message.success(res.msg);
                                this.handleClose();
                            }
                        });
                    } else {
                        this.$message.error('提交错误');
                        return false;
                    }
                });
            }
        }
    };
</script>