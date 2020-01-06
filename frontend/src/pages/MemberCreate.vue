<template>
    <section>
        <el-dialog :title="parentProps.title" :visible.sync="parentProps.visible" width="700px" @close="$emit('closeDialog')">
            <br/>
            <el-form size="medium" :model="parentProps.data" :rules="rules" ref="dataForm" label-width="120px" >
                <el-form-item label="手机号码" prop="mobile" >
                    <!--<label v-if="parentProps.data.id">{{parentProps.data.mobile}}</label>-->

                    <el-input v-model="parentProps.data.mobile" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="member_name">
                    <el-input v-model="parentProps.data.member_name" class='dialog-form-item'></el-input>
                </el-form-item>
                <!-- <el-form-item label="公司全称" prop="company_name">
                    <el-input v-model="parentProps.data.company_name" class='dialog-form-item'></el-input>
                </el-form-item> -->
                <el-form-item label="电子邮箱" prop="email">
                    <el-input v-model="parentProps.data.email" class='dialog-form-item'></el-input>
                </el-form-item>
                <el-form-item label="职业" prop="occupation">
                    <select-status v-model="parentProps.data.occupation" statusName="occupation" ></select-status>
                </el-form-item>
                <el-form-item label="来源" prop="source">
                    <select-status v-model="parentProps.data.source" statusName="source" ></select-status>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="$emit('closeDialog')">取 消</el-button>
                <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
                <reqButton @handleReq = "handleSave('dataForm')" />
            </div>
        </el-dialog>
    </section>
</template>
<script>
    import selectStatus from '@/components/SelectStatus'
    export default {
        data() {
            return {
                rules: {
                    mobile: [
                        {required: true, message: '请输入手机号码', trigger: 'blur'},
                        {min: 11, max: 11, message: '长度为11个数字', trigger: 'blur'}
                    ],
                    email: [
                        {type: 'email', message: '请输入正确的邮箱', trigger: 'blur'}
                    ],
                    member_name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ]
                }
            }
        },
        components: { selectStatus },
        props: ['dialogProps'],
        computed: {
            parentProps: function () {
                return this.dialogProps;
            },
        },
        created(){
        },
        methods: {
            handleSave(formName) {
//                console.log(this.parentProps.data);return;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(typeof this.parentProps.data.id != 'undefined'){
                            this.$http.post("member/edit",this.parentProps.data).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$emit('closeDialogAndRefresh');
                                }
                            });
                        }else{
                            this.$http.post("member/create",this.parentProps.data).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$emit('closeDialogAndRefresh', this.parentProps.data.mobile);
                                }
                            });
                        }
                    } else {
                        this.$message.error('提交失败!');
                        return false;
                    }
                });
            },
        }
    }
</script>