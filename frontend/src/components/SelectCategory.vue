<template>
    <el-cascader
            placeholder="请选择"
            v-model="newVal"
            :options="list"
            :style="styles"
            :props="{ value: 'id', label : 'name', children: '_child' }"
            @change="handleChange"
    ></el-cascader>
</template>

<script>
    export default {
        props:['value','styles'],
        data() {
            return {
                newVal: [],
                list: [],
            }
        },
        watch: {
            value: function (val) {
                if(this.list.length == 0){
                    this.getDate();
                }else{
                    this.newVal = val;
                }
            },
        },
        created(){
            this.getDate();
        },
        methods: {
            handleChange(){
                this.$emit('input', this.newVal)
            },
            getDate(){
                this.$http.fetch("category/table").then((res) => {
                    this.list = res.data;
                    this.list.push({ id:0, name:'根分类' }); // 显示最顶级分类
                    this.newVal = this.value;
                });
            }
        }
    };
</script>