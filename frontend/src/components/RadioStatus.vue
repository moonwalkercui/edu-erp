<template>
    <!--<keep-alive>-->
    <el-radio-group v-model="newVal" @change="handleChange">
        <el-radio v-for="item in list" :key="item.k" :label="item.v"></el-radio>
    </el-radio-group>
    <!--</keep-alive>-->
</template>

<script>
    export default {
        props: ['value','statusName','styles' ] ,
        data() {
            return {
                newVal: '',
                name: '',
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
                this.$http.fetch('open/getstatus',{"name":this.statusName}).then((res) => {
                    this.list = res.list;
                    this.newVal = this.value;
                });
            }
        }
    };
</script>