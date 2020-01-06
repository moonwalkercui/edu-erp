<template>
    <div><span class="tip-text" style="float:right">请选择收款项目</span>
        <el-button icon="el-icon-plus" @click="modalVisible = true">选择项目</el-button>
        <modal-selecter title="选择项目" dataUrl="proceeds/items" :visible.sync="modalVisible" @selectComplete="handleSelectItem"/>
        <el-table :data="selectedList" style="width: 100%; margin-top:10px" size="small" empty-text = '请选择'>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="money" label="收款金额(元)" width="100"></el-table-column>
            <el-table-column prop="remark" label="说明"></el-table-column>
            <el-table-column label="删除" width="50">
                <template slot-scope="scope">
                    <i class="el-icon-delete" style="font-size: 16px;" @click="handleDelete(scope.$index)"></i>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin-top:10px;">
          <span class="tip-text">每人收款: {{res.total}}元</span>
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
      modalVisible: false,
      res: {
        items: [], // 选中的结果 [{id:1,num:1}, {id:2,num1}]
        total: 0  // 合计金额
      },
    };
  },
  watch: {
    selectedList(){
      this.calculateTotal();
    }
  },
  methods: {
    handleSelectItem(val) {
      val.forEach((i) => {
        if ( findIndex(this.selectedList, function(o) { return (o.id === i.id) }) === -1) {
          this.selectedList.push({id: i.id,name: i.name,money: i.money,remark: i.remark})
        }
      });
      this.modalVisible = false
    },
    calculateTotal() {
      let total = 0;
      this.selectedList.forEach(i => {
        total += parseFloat(i.money);
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
