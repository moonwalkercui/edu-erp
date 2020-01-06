<template>
    <section>
        <div class="line page-line"></div>
        <el-form :model="ruleForm" size="medium" ref="ruleForm" :rules="rules" label-width="150px" class="page-form">
            <el-form-item label="收款项目" prop="items">
                <proceeds-items-select @getRes="getTotalItems"/>
            </el-form-item>
            <el-form-item label="选择学员" prop="members">
                <proceeds-member-select @getRes="getTotalMember"/>
            </el-form-item>
            <span class="tip-text" style="float:right">合计: {{(ruleForm.members.length * ruleForm.money_receivable).toFixed(2)}} 元 </span>
            <el-form-item label="每人应收金额" prop="money_receivable">
                <el-input v-model="ruleForm.money_receivable" style="width:49%;"><template slot="append">元</template></el-input>
            </el-form-item>
            <el-form-item label="所属门店" prop="division">
                <select-builder table="division" v-model="ruleForm.division" size="medium" style="width:49%;"></select-builder>
            </el-form-item>
            <el-form-item label="交款截止日">
                <el-date-picker v-model="ruleForm.date_time" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width:49%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="收款说明">
                <el-input type="textarea" :rows="3" placeholder="" v-model="ruleForm.remark"></el-input>
            </el-form-item>
            <el-form-item>
                <!-- <el-button type="primary" @click="submitForm('ruleForm')">发起收款</el-button> -->
                <reqButton @handleReq = "submitForm('ruleForm')" text="发起收款"/>
            </el-form-item>
        </el-form>
    </section>
</template>
<script>
  import ProceedsItemsSelect from '@/components/ProceedsItemsSelect'
  import ProceedsMemberSelect from '@/components/ProceedsMemberSelect'
  import selectBuilder from '@/components/SelectBuilder'
  export default {
    components: { ProceedsItemsSelect, ProceedsMemberSelect, selectBuilder},
    data() {
      return {
        ruleForm: {
          items: [],
          members: [],
          money_receivable: 0.00,
          date_time: '',
          division: '',
          remark: ''
        },
        itemsMoney: 0,
        items: [],
        members: [],
        rules: {
          items: [ {required: true, message: '请输入收款项目', trigger: 'blur'} ],
          members: [ {required: true, message: '请选择学员', trigger: 'blur'} ],
          money_receivable: [ {required: true, message: '请输入应收款金额', trigger: 'blur'} ],
          division: [ {required: true, message: '请选择归属门店', trigger: 'blur'} ],
        },
      };
    },
    watch: {
      itemsMoney: function (val) {
        // this.ruleForm.money_receivable = (val * this.ruleForm.members.length).toFixed(2)
        this.ruleForm.money_receivable = parseFloat(val).toFixed(2)
      }
      // 'ruleForm.members'(val) {
      //   // this.ruleForm.money_receivable = (val.length * this.itemsMoney).toFixed(2)
      // }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let submitFun = () => {
              this.$http.post("proceeds/create", this.ruleForm).then((res) => {
                if (res.status == 'success') {
                  this.$message({message: res.msg, type: 'success'});
                  this.$router.push('/proceeds/list');
                }
              });
            }
            if (parseFloat(this.ruleForm.money_receivable) != parseFloat(this.itemsMoney) ) {
              this.$confirm('应收金额与收费项目金额不相等，是否继续提交', "提示", {
                confirmButtonText: '立刻提交',
                cancelButtonText: "修改一下",
                type: "warning"
              }).then(() => {
                submitFun()
              }).catch(() => {});
            } else {
              submitFun()
            }
          } else {
            this.$message.error('提交失败!');
            return false;
          }
        });
      },
      getTotalItems(res) {
        let items = []
        res.items.forEach( i => {
          items.push(i.id)
        })
        this.items = res.items
        this.ruleForm.items = items
        this.itemsMoney = res.total
      },
      getTotalMember(res) {
        let items = []
        res.items.forEach( i => {
          items.push(i.mobile)
        })
        this.members = res.items
        this.ruleForm.members = items
      }
    }
  }
</script>