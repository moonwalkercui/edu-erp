<template>
    <el-checkbox-group v-model="newVal">
        <el-checkbox v-for="item in list" :key="item.id" :label="item.id"
                     style="margin-left:0; margin-right:20px"  @change="handleChange"
        >{{item.name}}</el-checkbox>
    </el-checkbox-group>
</template>
<script>
    export default {
        props:['value'],
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
                this.$http.fetch("org/divisions").then((res) => {
                    this.list = res.data;
                    this.newVal = this.value;
                });
            }
        }
    };
</script>