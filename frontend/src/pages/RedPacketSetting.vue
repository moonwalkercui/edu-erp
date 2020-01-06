<template>
    <section>
        <el-tabs v-model="activeName">
            <el-tab-pane label="规则设置" name="tab1">
                <div class="line page-line"></div>
                <el-form size="medium" :model="orgInfo" ref="orgInfo" :rules="rule1" label-width="220px" class="page-form">
                    <el-form-item label="发放红包最小金额">
                        <el-input type="number" v-model="orgInfo.red_packet_min"></el-input>
                        红包在最小与最大之间随机发放，获取几率与金额大小成反比
                    </el-form-item>
                    <el-form-item label="发放红包最大金额">
                        <el-input type="number" v-model="orgInfo.red_packet_max"></el-input>
                        设置为0表示关闭红包模块，系统默认的红包失效时间为3个月
                    </el-form-item>
                    <el-form-item label="每天每人领红包次数">
                        <el-input type="number" v-model="orgInfo.red_packet_day_times"></el-input>
                        设置为0表示不限制
                    </el-form-item>
                    <el-form-item label="每天每人助力总次数">
                        <el-input type="number" v-model="orgInfo.red_packet_help_times"></el-input>
                        设置为0表示不限制
                    </el-form-item>
                    <el-form-item label="每天给同一人的助力次数">
                        <el-input type="number" v-model="orgInfo.red_packet_helpsame_times"></el-input>
                        设置为0表示不限制
                    </el-form-item>
                    <el-form-item label="小程序转发配图">
                        <image-uploader
                            :imageUrl.sync="orgInfo.red_packet_img"
                            @upload="handleUploadedImage"
                            :autoCrop="true" :cropWidth="500" :cropHeight="500"
                        ></image-uploader>
                        <span class="tip-text">建议尺寸500x500px</span>
                    </el-form-item>
                    <el-form-item>
                        <reqButton @handleReq = "submitForm('orgInfo')"/>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
       
        </el-tabs>
    </section>
</template>
<script>
    // import helper from '@/components/Help'
    import imageUploader from "@/components/ImageUploader";
    export default {
        components : { imageUploader }, // helper
        data() {
            return {
                orgInfo: {},
                rule1: {},
                activeName: 'tab1',
            };
        },
        created() {
            this.$http.fetch('setting/getsetting').then((res) => {
                this.orgInfo = {
                    red_packet_min:  res.data.list.red_packet_min,
                    red_packet_max: res.data.list.red_packet_max,
                    red_packet_day_times: res.data.list.red_packet_day_times,
                    red_packet_help_times: res.data.list.red_packet_help_times,
                    red_packet_helpsame_times: res.data.list.red_packet_helpsame_times,
                    red_packet_img: res.data.list.red_packet_img,
                }
            });
        },
        methods: {
            handleUploadedImage(image) {
                this.orgInfo.red_packet_img = image;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("share/redpacketsetting", {...this[formName]}).then((res) => {
                            if (res.status == 'success') {
                                this.$message({message: res.msg, type: 'success'});
                            }
                        });
                    } else {
                        this.$message.error('请检查内容');
                        return false;
                    }
                });
            },
        }
    }
</script>