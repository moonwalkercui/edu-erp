<template>
    <div><span class="tip-text" style="float:right">请选择收款学员</span>
        <el-button icon="el-icon-plus" @click="modalMemberVisible = true">选择学员</el-button>
        <modal-selecter title="选择学员" dataUrl="member/table" :visible.sync="modalMemberVisible" @selectComplete="handleSelectItems"/>
        <el-table :data="selectedList" style="width: 100%; margin-top:10px" size="small" empty-text = '请选择' >
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="mobile" label="手机号"></el-table-column>
            <el-table-column label="删除" width="50">
                <template slot-scope="scope">
                    <i class="el-icon-delete" style="font-size: 16px;" @click="handleDelete(scope.$index)"></i>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin-top:10px;">
          <span class="tip-text">已选人数: {{res.items.length}}</span>
          <!-- <span class="tip-text" style="float:right">添加数量时请注意库存量</span> -->
        </div>
    </div>
</template>
<script>
import {findIndex} from 'lodash'
import modalSelecter from '@/components/ModalSelector'
export default {
  components: {modalSelecter},
  data() {
    return {
      selectedList: [], // 选中后过滤的el-table里用的数据
      modalMemberVisible: false,
      res: {
        items: [], // 选中的结果 [{id:1,num:1}, {id:2,num1}]
        total: 0  // 合计金额
      }
    };
  },
  watch: {
    selectedList(){
      this.calculateTotal();
    }
  },
  methods: {
    handleSelectItems(val) {
      val.forEach((i) => {
        if ( findIndex(this.selectedList, function(o) { return (o.mobile === i.mobile) }) === -1) {
          this.selectedList.push({mobile: i.mobile, name: i.name})
        }
      });
      this.modalMemberVisible = false
    },
    calculateTotal() {
      let total = 0;
      this.selectedList.forEach(i => {
        total += parseInt(i.num) * parseFloat(i.deal_price);
      });
      this.res = {
        items: this.selectedList,
        total: total.toFixed(2)
      }
      this.$emit("getRes", this.res);
    },
    handleDelete(index) {
      this.selectedList.splice(index, 1);
    }
  }
};
</script>
<style>
.input-number-small.el-input-number--mini {
  width: 100px;
}
</style>
