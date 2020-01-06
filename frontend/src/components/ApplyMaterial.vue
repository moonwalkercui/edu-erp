<template>
    <el-dialog title="物料申请表" :visible.sync="visible" width="700px" :before-close="handleClose">
        <br/>
        <el-form size="medium" label-width="120px" :model="dataForm" :rules="rules" ref="dataForm">
            <el-form-item label="选择物料" prop="material">
                <select-builder table="material" v-model="dataForm.material" class='dialog-form-item'></select-builder>
            </el-form-item>
            <el-form-item label="申请数量" prop="quantity">
                <el-input-number v-model="dataForm.quantity" :min="1" label="申请数量"></el-input-number>
            </el-form-item>
            <el-form-item label="申请说明" prop="remark">
                <el-input type="textarea" v-model="dataForm.remark"  class='dialog-form-item'></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <!-- <el-button type="primary" @click="handleSubmit('dataForm')">提 交</el-button> -->
            <reqButton @handleReq = "handleSubmit('dataForm')"/>
        </div>
    </el-dialog>
</template>
<script>
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: {selectBuilder},
        props: ["visible"],
        data() {
            return {
                content: '',
                dataForm:{
                    material: '',
                    quantity: '',
                    remark: ''
                },
                rules: {
                    material: [ {required: true, message: '请选择物料', trigger: 'blur'} ],
                    quantity: [ {required: true, message: '请输入数量', trigger: 'blur'} ],
//                    remark: [ {required: true, message: '申请说明', trigger: 'blur'} ],
                },
            }
        },
        methods: {
            handleClose(){
                this.$emit('update:visible', false);
            },
            handleSubmit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("material/apply",this.dataForm).then((res) =>{
                            if(res.status == 'success'){
                                this.$message.success(res.msg);
                                this.handleClose();
                            }
                        });
                    } else {
                        this.$message.error('提交失败!');
                        return false;
                    }
                });
            }
        }
    };
</script>