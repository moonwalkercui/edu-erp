<template>
    <div>
        <el-select  v-model="value" :multiple="true" filterable placeholder="未关联" :style="{width: '100%'}">
            <el-option v-for="(item, index) in dbData" :key="index+1"
                        :label="item.name" :value="item.id">
            </el-option>
        </el-select>
        <el-table :data="selectedList" style="width: 100%; margin-top:10px" size="small" empty-text = '请选择' border>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="quantity" label="库存" width="80"></el-table-column>
            <!--<el-table-column prop="cost" label="成本" width="80"></el-table-column>-->
            <el-table-column prop="price" label="价格(元)" width="80"></el-table-column>
            <el-table-column label="数量" width="125">
                <template slot-scope="scope">
                  <el-input-number class="input-number-small" size="mini" v-model="scope.row.num" :min="1" :max="scope.row.quantity" @change="calculateTotal"></el-input-number>
                </template>
            </el-table-column>
        </el-table>
        <div>
            <!--<span class="tip-text">成本小计<b> {{res.total}} 元</b></span>-->
            <span class="tip-text" style="float: right"> 附赠品是学员购买课程时免费获得的物料 成交利润不包括附赠品的成本</span>
        </div>
    </div>
</template>
<script>
import {find} from "lodash";
export default {
  props : ['values'],
  data() {
    return {
      value: [], // 多选框选中的数据IDS
      dbData: [], // 数据库里查询出来的数据结果
      selectedList: [], // 选中后过滤的el-table里用的数据
      res: {
        items: [], // 选中的结果 [{id:1,num:1}, {id:2,num1}]
        total: 0 // 合计金额
      },
      inputNums: [] // 加减输入框里的数值
    };
  },
  computed: {
  },
  watch: {
    value(values) {
      let selected = []
      values.forEach(v => {
        selected.push(find(this.dbData, { id: v }));
      })
      this.selectedList = selected
      this.calculateTotal()
    },
    values(values) {
      if(values.length !== 0) {
        this.getData(() => {
          let selectedValues = []
          values.forEach(v => {
            this.dbData.forEach(d => {
              if (v.id === d.id) {
                d.num = v.quantity
                selectedValues.push(d.id)
              }
            })
          })
          this.value = selectedValues
        })
      }
    }
  },
  created() {
    if (this.values.length === 0 ) this.getData()
  },
  methods: {
    getData(cb) {
      this.$http.fetch("material/table").then(res => {
        res.data.forEach((d) => {
            d.num = 1
        })
        this.dbData = res.data;
        cb && cb()
      });
    },
    calculateTotal() {
      let total = 0;
      let items = [];
      this.selectedList.forEach(i => {
        total += i.num * i.cost;
        items.push({ id: i.id, quantity: i.num, cost: i.cost });
      });
      this.res = {
        items: items,
        total: total
      }
      // this.$emit('update:values', this.res)
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
