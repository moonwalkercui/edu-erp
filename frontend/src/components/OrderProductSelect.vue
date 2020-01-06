<template>
    <div>
        <el-select  v-model="value" :multiple="true" filterable placeholder="请选择" :style="{width: '100%'}">
            <el-option v-for="(item, index) in dbData" :key="index+1"
                        :label="item.name" :value="item.id">
            </el-option>
        </el-select>
        <el-table :data="selectedList" style="width: 100%; margin-top:10px" size="small" empty-text = '请选择' border>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column label="一对一" width="60">
                <template slot-scope="scope">
                  {{scope.row.is_one2one == 1 ? '是' : '-'}}
                </template>
            </el-table-column>
            <el-table-column prop="price" label="价格" width="80"></el-table-column>
            <el-table-column label="调整价" width="95">
              <template slot-scope="scope">
                <el-input v-model="scope.row.deal_price" size="mini" @input="calculateTotal"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="数量" width="125">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.num" size="mini" class="input-number-small" :min="1" @change="calculateTotal"></el-input-number>
              </template>
            </el-table-column>
        </el-table>
        <div>
          <span class="tip-text">课程费小计<b> {{res.total}} 元</b></span>
          <span class="tip-text" style="float:right">一对一课程会自动生成生成班级</span>
        </div>
    </div>
</template>
<script>
import {find} from "lodash";
export default {
  data() {
    return {
      value: [], // 多选框选中的数据IDS
      dbData: [], // 数据库里查询出来的数据结果
      selectedList: [], // 选中后过滤的el-table里用的数据
      res: {
        items: [], // 选中的结果 [{id:1,num:1}, {id:2,num1}]
        total: 0  // 合计金额
      },
      inputNums: [] // 加减输入框里的数值
    };
  },
  computed: {
  },
  watch: {
    value(values){
      let res = []
      values.forEach(v => {
        res.push(find(this.dbData, { id: v }));
      })
      this.selectedList = res;
      this.calculateTotal();
    }
  },
  created() {
    this.$http.fetch('product/table').then(res => {
      res.data.forEach((d) => {
        d.deal_price = d.price
        d.num = 1
      })
      this.dbData = res.data;
    });
  },
  methods: {
    calculateTotal() {
      let total = 0;
      let items = [];
      this.selectedList.forEach(i => {
        total += i.num * i.deal_price;
        items.push({ id: i.id, num: i.num, deal_price: i.deal_price, link_materials: i.link_materials.list});
      });
      this.res = {
        items: items, 
        total: total
      }
      this.$emit("getRes", this.res);
    }
  }
};
</script>
<style>
.input-number-small.el-input-number--mini {
  width: 100px;
}
</style>
