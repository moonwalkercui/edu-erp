<template>
    <!--<keep-alive>-->
    <el-checkbox-group v-model="newVal" @change="handleChange">
        <el-checkbox v-for="item in list" :key="item.k" :label="item.v"
            style="margin-left: 0; margin-right: 30px; display: inline-block"
        ></el-checkbox>
    </el-checkbox-group>
    <!--</keep-alive>-->
</template>

<script>
    export default {
        props: ['value','statusName' ] ,
        data() {
            return {
                newVal: [],
                name: '',
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
                this.$http.fetch('open/getstatus',{"name":this.statusName}).then((res) => {
                    this.list = res.list;
                    this.newVal = this.value;
                });
            }
        }
    };
</script>