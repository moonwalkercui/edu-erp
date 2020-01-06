<template>
    <section>
        <template>
            <el-alert title="若本课时未结束，则课时修改后系统会自动更新该课时的学员的签到记录;" type="info" show-icon></el-alert>
        </template>
        <br>
        <div class="line page-line"></div>
        <el-form size="medium" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px" class="page-form" >
            <!-- <el-form-item label="班级" prop="class">
                <select-builder table="class" v-model="ruleForm.class" :style="{width: '400px'}"></select-builder>
            </el-form-item> -->
            <el-form-item label="门店">
                {{divisionName}}
            </el-form-item>
            <el-form-item label="课程">
                {{productName}}
            </el-form-item>
            <el-form-item label="老师" prop="user">
                <select-builder size="normal" table="user" v-model="ruleForm.user" :style="{width: '400px'}"></select-builder>
            </el-form-item>
            <!-- <el-form-item label="教室" prop="room">
                <select-builder table="room" v-model="ruleForm.room" :style="{width: '400px'}"></select-builder>
            </el-form-item> -->
            <el-form-item label="课题">
                <el-input v-model="ruleForm.title" :style="{width: '400px'}"></el-input>
            </el-form-item>
            <el-form-item label="日期" prop="day">
                <el-date-picker v-model="ruleForm.day" type="date" style="width: 400px" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item label="开始时间" prop="start">
                <el-time-select placeholder="起始时间" v-model="ruleForm.start" style="width: 173px"
                                :picker-options="{ start: '06:30', step: '00:05', end: '23:00'}">
                </el-time-select>
            </el-form-item>
            <el-form-item label="结束时间" prop="end">
                <el-time-select placeholder="结束时间" v-model="ruleForm.end" style="width: 173px"
                                :picker-options="{ start: '06:30', step: '00:05', end: '23:00', minTime: ruleForm.start }">
                </el-time-select>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <radio-status v-model="ruleForm.status" statusName="course"></radio-status>
            </el-form-item>
            <el-form-item>
                <reqButton @handleReq = "submitForm('ruleForm')" />
            </el-form-item>
        </el-form>

        <div style="margin-top: 60px" v-if="$util.hasPermission(57)">
            <template>
                <el-alert title="此操作会永久删除本课时的信息与学员签到记录. " :closable="false">  <el-button size="small" type="danger" @click="handleDel(ruleForm.id)"> 删除该课时 </el-button></el-alert>
            </template>
            <br>
        </div>
    </section>
</template>
<script>
    import radioStatus from '@/components/RadioStatus'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        data() {
            return {
                divisionName: '',
                productName: '',
                ruleForm: {
                  // class: '',
                    // room: '',
                    product: '',
                    id: '',
                    user: '',
                    day: '',
                    start : '',
                    end : '',
                    status: '',
                    title: '',
                },
                rules: {
                    // class: [{required: true, message: '请选择班级', trigger: 'blur'}],
                    // room: [{required: true, message: '请选择教室', trigger: 'blur'}],
                    // product: [{required: true, message: '请选择课程', trigger: 'blur'}],
                    day: [{required: true, message: '请选择日期', trigger: 'blur'}],
                    user: [{required: true, message: '请选择日期', trigger: 'blur'}],
                    start: [{required: true, message: '请选择开始时间', trigger: 'blur'}],
                    end: [{required: true, message: '请选择结束时间', trigger: 'blur'}],
                    status: [{required: true, message: '请选择状态', trigger: 'blur'}],
                }
            };
        },
        components: { radioStatus ,selectBuilder},
        created(){
            this.$http.fetch('course/find',{"id":this.$route.params.id}).then((res) => {
                this.ruleForm.id = res.data.id;
                // this.ruleForm.class = res.data.class_id;
                // this.ruleForm.room = res.data.room_id;
                this.ruleForm.product = res.data.product_id;
                this.ruleForm.title = res.data.title;
                this.ruleForm.user = res.data.username;
                this.ruleForm.day = res.data.date;
                this.ruleForm.start = res.data.start_at;
                this.ruleForm.end = res.data.end_at;
                this.ruleForm.status = res.data.status;

                this.productName = res.data.product_name;
                this.divisionName = res.data.division_name;
            });
        },
        methods: {
            submitForm(formName) {
              // console.log(this.ruleForm);return;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("course/edit",this.ruleForm).then((res) =>{
                            if(res.status == 'success'){
                                this.$message({ message: res.msg, type: 'success' });
                                this.$router.push('/course/list');
                            }
                        });
                    } else {
                        this.$message.error('提交失败!');
                        return false;
                    }
                });
            },
            handleDel: function (id) {
                this.$confirm('此操作将永久删除课时及签到记录,请谨慎操作！！！', '提示', {
                    confirmButtonText: '确定删除', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    this.$http.post('course/delete',{"id":id}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: '删除成功', type: 'success' });
                            this.$router.push('/course/list');
                        }
                    });
                }).catch(() => {
                });
            }
        }
    }
</script>
<style scoped>
</style>