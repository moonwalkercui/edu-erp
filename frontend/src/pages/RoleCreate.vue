<template>
    <section>
        <div class="line page-line"></div>
        <el-form size="medium" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px" style="width:100%" >
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="查看门店范围" prop="divisions">
                <checkbox-division v-model="ruleForm.divisions"></checkbox-division>
            </el-form-item>
            <el-form-item label="设置权限" prop="permissions">
                <el-checkbox-group v-model="ruleForm.permissions">
                    <el-checkbox v-for="item in permissions" :key='item.id' :label="item.id" style="width: 160px; margin-left: 0; margin-right: 10px; display: inline-block">{{item.display_name}} {{item.id}}</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="描述">
                <el-input v-model="ruleForm.description"></el-input>
            </el-form-item>
            <el-form-item>
                <!-- <el-button type="primary" @click="submitForm('ruleForm')"> 提 交 </el-button> -->
                <reqButton @handleReq = "submitForm('ruleForm')"/>
            </el-form-item>
        </el-form>
    </section>
</template>
<script>
    import {map} from 'lodash'
    import checkboxDivision from '@/components/CheckboxDivision'
    export default {
        components: { checkboxDivision},
        data() {
            return {
                ruleForm: {
                    name: '',
                    divisions: [],
                  // divisions: [this.$cookie.fetchJson('_userInfo').division],
                    permissions: [],
                    description: '',
                },
                permissions: [],
                rules: {
                    name: [
                        {required: true, message: '请输入角色名', trigger: 'blur'},
                        {max: 50, message: '字数过多', trigger: 'blur'}
                    ],
                    permissions: [
                        { type: 'array', required: true, message: '请至少选择一个', trigger: 'change' }
                    ],
                  divisions: [
                        { type: 'array', required: true, message: '请至少选择一个', trigger: 'change' }
                    ],
                },
                isUpdate: false,
            };
        },
        created(){
            this.getPermissionList();
            if(this.$route.params.id) this.getData();
        },
        methods: {
            getPermissionList() {
                this.$http.fetch("rbac/permissions",this.urlParam).then((res) =>{
                    this.permissions = res.data;
                });
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(this.isUpdate){
                            this.$http.post("rbac/roleedit",this.ruleForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                }
                            });
                        }else{
                            this.$http.post("rbac/rolecreate",this.ruleForm).then((res) =>{
                                if(res.status == 'success'){
                                    this.$message({ message: res.msg, type: 'success' });
                                    this.$router.push('/rbac/roles');
                                }
                            });
                        }
                    } else {
                        this.$message.error('提交失败!');
                        return false;
                    }
                });
            },
            getData(){
                this.$http.fetch('rbac/findrole',{"id":this.$route.params.id}).then((res) => {
                    this.ruleForm.id = res.data.id;
                    this.ruleForm.name = res.data.name;
                    this.ruleForm.description = res.data.description;
                    this.ruleForm.divisions = map(res.data.view_division_ids.split(","),(i) => {
                        return parseInt(i)
                    });
                  
                    var select = map(res.data.permissions, 'id');
                    this.ruleForm.permissions = select;
                    this.isUpdate = true;
                });
            },
        }
    }
</script>