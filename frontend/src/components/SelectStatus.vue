<template>
    <el-select :size="size" v-model="newVal" placeholder="请选择" clearable :style="styles" @change="handleChange">
        <el-option v-for="(item,index) in list" :key="index" :label="item.v" :value="item.k"></el-option>
    </el-select>
</template>

<script>
    export default {
        props: {
            value:{},
            statusName:{},
            size:{default:'mini'},
            styles:{}
        },
        data() {
            return {
                newVal: '',
                list: [],
            }
        },
        watch: {
            value: function (val) {
                if(val != '' && this.list.length == 0){
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
                this.$http.fetch('open/getstatus', {"name":this.statusName}).then((res) => {
                    this.list = res.list;
                    if(this.value) this.newVal = this.value;
                });
            }
        }
    };
</script>