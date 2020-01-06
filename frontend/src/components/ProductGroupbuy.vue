<template>
  <div>
    <el-dialog
      title="团购设置"
      :visible.sync="visible"
      width="900px"
      :before-close="handleClose"
      :close-on-click-modal="true"
    >
      <el-form :model="dataForm" :rules="rules" ref="dataForm" size="small" label-width="150px" class="page-form-large" style="max-width: 1000px">
        <el-form-item label="是否开启团购" prop="is_groupbuy">
          <radio-status v-model="dataForm.is_groupbuy" statusName="is_yes"></radio-status>
        </el-form-item>
        <el-form-item label="成团人数" prop="number">
            <el-input type="number" v-model="dataForm.number" placeholder="最小为2"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="start">
          <el-date-picker v-model="dataForm.start" type="datetime" format = "yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm"></el-date-picker>
          <!-- <span class="tip-text" style="float: right">未开始不能参与</span> -->
        </el-form-item>
        <el-form-item label="结束时间" prop="end">
          <el-date-picker v-model="dataForm.end" type="datetime" format = "yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm"></el-date-picker>
          <span class="tip-text" style="float: right"></span>
        </el-form-item>
        <el-form-item label="未成团是否退款" prop="is_groupbuy">
          <radio-status v-model="dataForm.is_groupbuy" statusName="is_yes"></radio-status>
          <span class="tip-text" style="float: right">退款: 已支付的订单自动发起退款申请（见订单-退款管理）并取消订单</span>
        </el-form-item>
        <el-form-item label="价格设置">
            <ProductSpecificationGroupbuy :specs="specs" :groupbuy="groupbuy" :productId = "dataForm.id" @updateGroupbuyPrice="updateGroupbuyPrice"/>
        </el-form-item>
        <el-form-item>
          <reqButton @handleReq = "submitForm('dataForm')"/>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import ProductSpecificationGroupbuy from '@/components/ProductSpecificationGroupbuy'
import radioStatus from '@/components/RadioStatus'
export default {
  components: { radioStatus, ProductSpecificationGroupbuy },
  props: {
    visible: { default: false },
    productId: {
      type: Number
    }
  },
  data() {
    var checkNum = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('成团人数不能为空'));
      }
      if (value < 2) {
        callback(new Error('至少为2'));
      } else {
        callback();
      }
    };
    return {
      dataForm: {
        id: 0,
        is_groupbuy: '否',
        number: 0,
        start: '',
        end: '',
        groupbuy: [],
      },
      specs: [],
      groupbuy: [],
      rules: {
        is_groupbuy: [ {required: true, message: '请设置开关', trigger: 'blur'} ],
        number: [ {validator: checkNum, trigger: 'blur'} ],
        start: [ {required: true, message: '请设置开始时间', trigger: 'blur'} ],
        end: [ {required: true, message: '请设置结束时间', trigger: 'blur'} ],
      },
      urlParam: {
        search_product: ""
      }
    };
  },
  watch: {
    productId: function(newVal) {
      if(newVal != 0 ) {
        console.log('newVal2', newVal)
        this.getData(newVal);
      }
    },
  },
  methods: {
    updateGroupbuyPrice(value) {
      this.dataForm.groupbuy = value;
    },
    getData(id) {
        this.$http.fetch('product/find', {"id": id}).then((res) => {
          this.dataForm = {
            id:  res.data.id,
            is_groupbuy: res.data.is_groupbuy,
            number: res.data.groupbuy_num,
            start: res.data.groupbuy_start,
            end: res.data.groupbuy_end,
          }
          this.specs = res.data.specs;
          this.groupbuy = res.data.groupbuy;
        });
      },
    handleClose() {
      this.$emit("update:visible", false);
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("product/editgoupbuy", this.dataForm).then((res) => {
            console.log('resres',res)
            if (res.status == 'success') {
            }
          });
        } else {
          this.$message.error('提交失败,请检查输入内容!');
          return false;
        }
      });
    },
  }
};
</script>