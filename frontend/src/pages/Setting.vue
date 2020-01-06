<template>
    <section>
        <el-tabs v-model="activeName">
            <el-tab-pane label="基本配置" name="tab1">

                <div class="line page-line"></div>
                <el-form size="medium" :model="orgInfo" ref="orgInfo" :rules="rule1" label-width="220px" class="page-form">
                   
                    <el-form-item label="学校名称" prop="org_name">
                        <el-input v-model="orgInfo.org_name" ></el-input>
                    </el-form-item>
                    <el-form-item label="学校简介">
                        <el-input type="textarea" v-model="orgInfo.org_intro"></el-input>
                    </el-form-item>
                    <el-form-item label="客服电话">
                        <el-input v-model="orgInfo.org_phone" ></el-input>
                    </el-form-item>
                    <el-form-item label="学校LOGO">
                        <image-uploader
                            :imageUrl.sync="orgInfo.org_logo"
                            @upload="handleUploadedImage"
                            :autoCrop="true" :cropWidth="500" :cropHeight="500"
                        ></image-uploader>
                        <span class="tip-text">建议尺寸500x500px</span>
                    </el-form-item>
                    <el-form-item label="学校图片">
                        <image-uploader
                            :imageUrl.sync="orgInfo.org_img"
                            @upload="handleUploadedImage2"
                            :autoCrop="true" :cropWidth="600" :cropHeight="400"
                        ></image-uploader>
                        <span class="tip-text">建议尺寸500x500px</span>
                    </el-form-item>
                    <el-form-item label="学员当日可签到">
                        <el-switch v-model="orgInfo.only_sign_today" active-value="1" inactive-value="0"></el-switch>
                        学员只能签到当日的课程，不开启表示学员签到无时间限制。
                    </el-form-item>
                    <el-form-item label="老师代签">
                        <el-switch v-model="orgInfo.only_sign_myself" active-value="1" inactive-value="0"></el-switch>
                        开启表示老师仅能给上自己课的学员代签到, 不开启表示仅可给上自己课的学员代签到。
                    </el-form-item>
                    <el-form-item label="不限制奖励小星星">
                        <el-switch v-model="orgInfo.only_award_others" active-value="1" inactive-value="0"></el-switch>
                        开启表示老师可给所有学员奖励小星星, 不开启表示仅可给上自己课的学员奖励。
                    </el-form-item>
                    <el-form-item>
                        <!-- <el-button type="primary" @click="submitForm('orgInfo')">保 存</el-button> -->
                        <reqButton @handleReq = "submitForm('orgInfo')"/>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="微信配置" name="tab2">
                <div class="line page-line"></div>
                    <helper key-id='wxpay' float='right'/>
                <el-form :model="wxInfo" size="medium" ref="wxInfo" :rules="rule2" label-width="220px" class="page-form">
                    <el-form-item label="APPID" prop="wx_appid">
                        <el-input v-model="wxInfo.wx_appid"></el-input>
                    </el-form-item>
                    <el-form-item label="密 钥" prop="wx_secret">
                        <el-input v-model="wxInfo.wx_secret"></el-input>
                    </el-form-item>
                    <el-form-item label="是否开启微信支付">
                        <el-radio-group v-model="wxInfo.wxpay" size="small">
                            <el-radio-button label="1">是</el-radio-button>
                            <el-radio-button label="0">否</el-radio-button>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="商户号">
                        <el-input v-model="wxInfo.wx_mchid"><template slot="append">开启微信支付必填</template></el-input>
                    </el-form-item>
                    <el-form-item label="商户Key">
                        <el-input v-model="wxInfo.wx_key"><template slot="append">开启微信支付必填</template></el-input>
                    </el-form-item>
                    <el-form-item label="支付证书APICLIENT_CERT">
                        <el-button @click="showTextCertDialog = true">{{has_apiclient_cert == 1 ? '更新证书(已上传)' : '上传证书'}}</el-button>
                    </el-form-item>
                    <el-form-item label="支付证书APICLIENT_KEY">
                        <el-button @click="showTextKeyDialog = true">{{has_apiclient_key == 1 ? '更新KEY(已上传)' : '上传KEY'}}</el-button>
                    </el-form-item>
                    <el-form-item>
                        <!-- <el-button type="primary" @click="submitForm('wxInfo')">保 存</el-button> -->
                        <reqButton @handleReq = "submitForm('wxInfo')"/>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <!-- <el-tab-pane label="模板消息" name="tab3">11</el-tab-pane>
            <el-tab-pane label="其他" name="tab4">00</el-tab-pane> -->
        </el-tabs>
        <ModalTextUploader :visible.sync="showTextCertDialog" :item="textCertItem" @handle-update-success="handleUpCertSuccess" title="请用记事本打开微信证书文件'apiclient_cert.pem'拷贝并粘贴到输入框中"/>
        <ModalTextUploader :visible.sync="showTextKeyDialog" :item="textKeyItem" @handle-update-success="handleUpKeySuccess" title="请用记事本打开微信证书文件'apiclient_key.pem'拷贝并粘贴到输入框中"/>
    </section>
</template>
<script>
    // import helper from '@/components/Help'
    import imageUploader from "@/components/ImageUploader";
    import ModalTextUploader from "@/components/ModalTextUploader";
    export default {
        components : { imageUploader, ModalTextUploader }, // helper
        data() {
            return {
                orgInfo: {
                    org_name: '',
                    org_intro: '',
                    org_phone: '',
                    org_logo: '',
                    org_img: '',
                    only_sign_today: '0',
                    only_sign_myself: '0',
                    only_award_others: '0',
                },
                rule1: {
                    org_name: [{ required: true, message: "请输入学校名", trigger: "blur" }],
                },
                wxInfo: {
                    wx_appid: '1',
                    wx_mchid: '1',
                    wx_secret: '1',
                    wx_key: '1',
                    wxpay: '0',
                },
                rule2: {
                    wx_appid: [{ required: true, message: "请输入Appid", trigger: "blur" }],
                    wx_secret: [{ required: true, message: "请输入密钥", trigger: "blur" }],
                },
                activeName: 'tab1',
                showTextCertDialog: false,
                showTextKeyDialog: false,
                textCertItem: {type: 'wx_cert'},
                textKeyItem: {type: 'wx_key'},
                has_apiclient_cert: 0,
                has_apiclient_key: 0,
            };
        },
        created() {
            this.$http.fetch('setting/getsetting').then((res) => {
                this.orgInfo = {
                    org_name:  res.data.list.org_name,
                    org_intro: res.data.list.org_intro,
                    org_phone: res.data.list.org_phone,
                    org_logo: res.data.list.org_logo,
                    org_img: res.data.list.org_img,
                    only_sign_today: res.data.list.only_sign_today + '',
                    only_sign_myself: res.data.list.only_sign_myself + '',
                    only_award_others: res.data.list.only_award_others + '',
                }
                this.wxInfo = {
                    wx_appid:  res.data.list.wx_appid,
                    wx_mchid: res.data.list.wx_mchid,
                    wx_secret: res.data.list.wx_secret,
                    wx_key: res.data.list.wx_key,
                    wxpay: res.data.list.wxpay,
                }
                this.has_apiclient_cert = res.data.list.has_apiclient_cert
                this.has_apiclient_key = res.data.list.has_apiclient_key
            });
        },
        methods: {
            handleUpCertSuccess() {
                this.has_apiclient_cert = 1
            },
            handleUpKeySuccess() {
                this.has_apiclient_key = 1
            },
            handleUploadedImage(image) {
                this.orgInfo.org_logo = image;
            },
            handleUploadedImage2(image) {
                this.orgInfo.org_img = image;
            },
            // handleClick(tab, event) {
            //     console.log(tab, event);
            // },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("setting/savesetting", {...this[formName]}).then((res) => {
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