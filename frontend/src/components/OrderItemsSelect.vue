<template>
    <div><span class="tip-text" style="float:right">添加数量时请注意库存量</span>
        <el-button icon="el-icon-plus" @click="modalProductVisible = true">添加课程</el-button>
        <el-button icon="el-icon-plus" @click="modalMaterialVisible = true">添加物料</el-button>
        <modal-selecter title="选择课程" dataUrl="product/table" :visible.sync="modalProductVisible" @selectComplete="handleSelectProduct"/>
        <modal-selecter title="选择物料" dataUrl="material/table" :visible.sync="modalMaterialVisible" @selectComplete="handleMaterialProduct"/>

        <el-table :data="selectedList" style="width: 100%; margin-top:10px" size="small" empty-text = '请选择'>
            <el-table-column prop="mark" label="类型" width="50"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="price" label="原价" width="80"></el-table-column>
            <el-table-column prop="quantity" label="库存" width="50"></el-table-column>
            <el-table-column label="成交价" width="95">
              <template slot-scope="scope">
                <el-input v-model="scope.row.deal_price" size="mini" @input="calculateTotal"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="数量" width="125">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.num" size="mini" class="input-number-small" :min="1" @change="calculateTotal"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="备注">
                <template slot-scope="scope">
                    <el-input v-model="scope.row.remark" size="mini"></el-input>
                </template>
            </el-table-column>
            <el-table-column label="删除" width="50">
                <template slot-scope="scope">
                    <i class="el-icon-delete" style="font-size: 16px;" @click="handleDelete(scope.$index)"></i>
                </template>
            </el-table-column>
        </el-table>
        <!--<div>-->
          <!--<span class="tip-text">应收<b> <Money :value="res.total"/></b></span>-->
          <!--<span class="tip-text" style="float:right">添加数量时请注意库存量</span>-->
        <!--</div>-->
    </div>
</template>
<script>
import {findIndex} from 'lodash'
import modalSelecter from '@/components/ModalSelector'
// import Money from "./Money";

export default {
  components: { modalSelecter}, // Money,
  // props: {
  //   values: {default: []},
  // },
  data() {
    return {
      selectedList: [], // 选中后过滤的el-table里用的数据
      modalProductVisible: false,
      modalMaterialVisible: false,
      res: {
        items: [], // 选中的结果 [{id:1,num:1}, {id:2,num1}]
        total: 0  // 合计金额
      },
      // dbData: [], // 数据库里查询出来的数据结果
      // inputNums: [] // 加减输入框里的数值
    };
  },
  computed: {
  },
  watch: {
    selectedList(){
      this.calculateTotal();
    }
  },
  created() {
    // this.$http.fetch('product/table').then(res => {
    //   res.data.forEach((d) => {
    //     d.deal_price = d.price
    //     d.num = 1
    //   })
    //   this.dbData = res.data;
    // });
  },
  methods: {
    handleSelectProduct(val) {
      // console.log('handleSelectProduct', val)
      val.forEach((i) => {
          this.pushSelectedList(i.id, '课程', 'product', i.name, i.price, i.price, 1, i.order_remark, null, i.quantity)
          if (i.product_link && i.product_link.materials) {
            i.product_link.materials.forEach((m) => {
              this.pushSelectedList(m.id, '物料', 'material', m.name, m.price, 0, m.num, '', i.id, m.quantity)
            })
          }
      });
      this.modalProductVisible = false
    },
    handleMaterialProduct(val) {
      val.forEach((i) => {
          this.pushSelectedList(i.id, '物料', 'material', i.name, i.price, i.price, 1, '', null, i.quantity)
      })
      this.modalMaterialVisible = false
    },
    pushSelectedList(id, mark, type, name, price, deal_price, num, remark, link_product_id, quantity) {
      if ( findIndex(this.selectedList, function(o) { return (o.id === id && o.type === type) }) === -1) {
        this.selectedList.push({id, mark, type, name, price, deal_price, num, remark, link_product_id, quantity})
      } else {
        this.$message('不能重复添加:' + name)
      }
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
      let deleted = this.selectedList.splice(index, 1);
      if (deleted[0].type === 'product') {
        let indexes = [];
        this.selectedList.forEach((i, n) => {
          if (i.link_product_id === deleted[0].id) {
            indexes.push(n)
          }
        });
        let newIndexes = indexes.sort(function(a, b) { return b - a});
        newIndexes.forEach((index)=>{
          this.selectedList.splice(index, 1)
        })
      }
    }
  }
};
</script>
<style>
.input-number-small.el-input-number--mini {
  width: 100px;
}
</style>
