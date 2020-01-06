<template>
    <el-select v-model="newVal" placeholder="请选择门店" :style="styles" @change="handleChange">
        <el-option key="0" label="请选择" value="" v-if="showAll"></el-option>
        <el-option v-for="item in list" :key="item.id" :label="item.name" :value="item.id" ></el-option>
    </el-select>
</template>
<script>
    export default {
        props: {
            value:{},
            styles:{},
            showAll: {
                default: true
            }
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
                this.$http.fetch('org/divisions',{}).then((res) =>{
                    this.list = res.data;
                    this.newVal = this.value;
                });
            }
        }
    };
</script>