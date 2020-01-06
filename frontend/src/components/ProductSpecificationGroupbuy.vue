<template>
  <div>
    <el-table :data.sync="list" style="width: 100%;" size="mini" :header-cell-style="{lineHeight: '18px'}">
      <el-table-column prop="name" label="规格名称"></el-table-column>
      <el-table-column prop="price" label="原价(元)"></el-table-column>
      <el-table-column label="团购价(元)" >
        <template slot-scope="scope">
          <el-input type='number' placeholder="0.00" v-model="scope.row.price_groupbuy" @input="handlePriceInput"></el-input>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
  export default {
    props: {
      productId: {
        type: Number
      },
      specs: {
        type: Array
      },
      groupbuy: {
        type: Array
      }
    },
    data() {
      return {
        list: [],
      }
    },
    watch: {
      specs: function (val) {
        var list = [];
        for(var s of val) {
          var price = s.price;
          for(var g of this.groupbuy) {
            if(g.spec_id == s.id) price = g.price;
          }
          list.push({
            id: s.id,
            name: s.name,
            price: s.price,
            price_groupbuy: price,
          });
        }
        this.list = list;
        this.handleEmit();
      },
    },
    created(){
    },
    methods: {
      handlePriceInput() {
        this.handleEmit();
      },
      handleEmit() {
        var res = [];
        for(var v of this.list) {
          res.push({
            spec_id: v.id,
            price: v.price_groupbuy,
          });
        }
        this.$emit('updateGroupbuyPrice', res)
      }
    }
  };
</script>
<style>
    .mt10 {
        margin-top: 10px;
    }
</style>
