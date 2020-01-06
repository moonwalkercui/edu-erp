<template>
    <section>
        <div class="line page-line"></div>
        <el-form :model="ruleForm" size="medium" ref="ruleForm" label-width="150px" class="page-form-large">
            <el-form-item label="选择学员(必填)">
                <select-builder size="medium" ref="selectMember" table="member" clearable filterable v-model="ruleForm.member"
                                :style="{width: '100%'}"></select-builder>
                <span class="tip-text">如果以上列表中没有 可以</span>
                <el-button type="text" @click="handleCreateMember">新增学员</el-button>
            </el-form-item>
            <el-form-item label="成单项目(必选)">
                <order-items-select @getRes="getTotalItems"/>
            </el-form-item>
            <el-form-item label="应收金额" style="width:49%; display: inline-block">
                <el-input v-model="ruleForm.money_receivable"><template slot="append">元</template></el-input>
            </el-form-item>
            <el-form-item label="实收金额" style="width:49%; display: inline-block">
                <el-input v-model="ruleForm.money_received"><template slot="append">元</template></el-input>
            </el-form-item>
            <el-form-item label="收款方式" style="width:49%; display: inline-block">
              <select-status v-model="ruleForm.payment_mode" statusName="payment" size="medium" :style="{width: '100%'}"></select-status>
            </el-form-item>
            <el-form-item label="待收金额" style="width:49%; display: inline-block">
                <el-input v-model="residual" :disabled="true"><template slot="append">元</template></el-input>
            </el-form-item>
            <el-form-item label="其他说明">
                <el-input type="textarea" :rows="3" placeholder="" v-model="ruleForm.remark"></el-input>
            </el-form-item>
            <!--<el-form-item label="合计金额">-->
                <!--<b class="money-text" style="font-size: 120%">-->
                    <!--<Money :value="total" size="big"/>-->
                <!--</b>-->
            <!--</el-form-item>-->
            <el-form-item>
                <!-- <el-button type="primary" @click="submitForm('ruleForm')"> 提交审核</el-button> -->
                <reqButton @handleReq = "submitForm('ruleForm')" text="提交审核"/>
            </el-form-item>
        </el-form>
        <member-create :dialog-props='dialogProps' @closeDialog="handleDialogClose"
                       @closeDialogAndRefresh="handleDialogCloseAndRefresh"></member-create>
    </section>
</template>
<script>
  import selectBuilder from '@/components/SelectBuilder'
  // import radioStatus from '@/components/RadioStatus'
  import MemberCreate from '@/pages/MemberCreate'
  import orderItemsSelect from '@/components/OrderItemsSelect'
  // import Money from "../components/Money";
  import selectStatus from '@/components/SelectStatus'
  export default {
    components: {selectBuilder,  MemberCreate, orderItemsSelect, selectStatus}, //Money,  radioStatus,
    data() {
      return {
        ruleForm: {
          items: [],
          money_receivable: 0.00,
          money_received: 0.00,
          payment_mode: 0,
          member: '',
          remark: ''
        },
        dialogProps: {
          visible: false,
          title: '新增学员',
          data: {}
        },
      };
    },
    computed: {
      residual: function () {
        return (this.ruleForm.money_receivable - this.ruleForm.money_received).toFixed(2)
      }
    },
    created() {
      // if (this.$route.params.sn) this.getData();
    },
    watch: {},
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post("order/create", this.ruleForm).then((res) => {
              if (res.status == 'success') {
                this.$message({message: res.msg, type: 'success'});
                this.$router.push('/order/mine');
              }
            });
          } else {
            this.$message.error('提交失败!');
            return false;
          }
        });
      },
      getData() {
        // this.$http.fetch('order/find', {"sn": this.$route.params.sn}).then((res) => {
        //   this.ruleForm.member = res.data.member_mobile;
        //   this.ruleForm.type = res.data.type;
        //   this.ruleForm.price = res.data.product_price;
        //   this.ruleForm.otherPrice = res.data.other_price;
        //   this.ruleForm.remark = res.data.remark;
        //   this.isUpdate = true;
        // });
      },
      getTotalItems(res) {
        // console.log('emit getTotalItems', res)
        this.ruleForm.items = res.items
        this.ruleForm.money_receivable = res.total
        this.ruleForm.money_received = res.total
      },
      handleCreateMember() {
        this.dialogProps.visible = true;
        this.dialogProps.data = {};
      },
      handleDialogCloseAndRefresh(mobile) {
        this.dialogProps.visible = false
        this.ruleForm.member = mobile
        this.$refs.selectMember.getData()
      },
      handleDialogClose() {
        this.dialogProps.visible = false;
      },
    }
  }
</script>