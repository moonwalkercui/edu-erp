<template>
    <div>
        <el-table :data="list" style="width: 100%;" size="mini"
                  :header-cell-style="{textAlign: 'center', lineHeight: '18px'}">
            <el-table-column label="课时数">
                <template slot-scope="scope">
                    <el-input type='number' placeholder="0" v-model="scope.row.courses_quantity"></el-input>
                </template>
            </el-table-column>
            <el-table-column label="价 格(元)" >
                <template slot-scope="scope">
                    <el-input type='number' placeholder="0.00" v-model="scope.row.price" @input="handlePriceInput(scope.$index, $event)"></el-input>
                </template>
            </el-table-column>
            <!-- <el-table-column label="名 额">
                <template slot-scope="scope">
                    <el-input placeholder="" v-model="scope.row.quantity" @input="handleQuantityInput(scope.$index, $event)"></el-input>
                </template>
            </el-table-column> -->
            <el-table-column label="备 注">
                <template slot-scope="scope">
                    <el-input placeholder="无" v-model="scope.row.name" ></el-input>
                </template>
            </el-table-column>
            <el-table-column label="" width="40">
                <template slot-scope="scope">
                    <el-button v-if="scope.$index != 0" type="text" icon="el-icon-delete"
                               @click="handleDelete(scope.$index)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-col :span="24" class="mt10">
            <el-button type="" plain icon="el-icon-plus" @click="handleAdd">添加</el-button>
            <span class="tip-text" style="float: right"></span>
        </el-col>
    </div>
</template>
<script>
  export default {
    props: {
      values: {
        type: Array,
        default: function () {
          return [{
            price: '',
            // quantity: 1,
            courses_quantity: '',
            name: '套餐1',
          }]
        }
      }
    },
    data() {
      return {
        list: [],
        max: 0
      }
    },
    watch: {
      values: function (val) {
        this.list = val
      },
    },
    created(){
      if(this.list.length ===0) this.handleAdd();
    },
    methods: {
      handleDelete(index) {
        this.list.splice(index, 1);
        this.$emit('update:values', this.list)
      },
      handleAdd() {
        this.max++;
        this.list.push({
          price: '',
          // quantity: 1,
          courses_quantity: '',
          name: '套餐' + this.max,
        })
        this.$emit('update:values', this.list)
      },
      handlePriceInput(index, value) {
        let newVal = value.trim().slice(0, value.indexOf('.') === -1 ? value.length : value.indexOf('.') + 3);
        setTimeout(() => {
          if (isNaN(newVal)) this.list[index].price = '';
          else this.list[index].price = newVal;

          this.$emit('update:values', this.list)
        }, 20);
      },
      // handleQuantityInput(index, value) {
      //   let newVal = parseInt(value);
      //   setTimeout(() => {
      //     if (isNaN(newVal)) this.list[index].quantity = '';
      //     else this.list[index].quantity = newVal;
      //     this.$emit('update:values', this.list)
      //   }, 20);
      // },
    }
  };
</script>
<style>
    .mt10 {
        margin-top: 10px;
    }
</style>
