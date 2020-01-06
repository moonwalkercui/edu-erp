<template>
    <el-cascader v-model="newVal"
                 class='dialog-form-item'
                 :options="list"
                 placeholder="留空表示最高级"
                 change-on-select :props ="{ value: 'id', label : 'department_name' , children: '_child' }"
                 @change="handleChange">
    </el-cascader>
</template>

<script>
    export default {
        props:['value','styles','condition'],
        data() {
            return {
                newVal: [],
                list: [],
                urlParams: {}
            }
        },
        watch: {
            value: function (val) {
                if(val.length != 0 && this.list.length == 0){
                    this.getDate();
                }else{
                    this.newVal = val;
                }
            },
            condition: function(val) {
                this.urlParams = val;
                this.getDate();
            }
        },
        created(){
            this.getDate();
        },
        methods: {
            handleChange(){
                this.$emit('input', this.newVal)
            },
            getDate(){
                this.$http.fetch("depart/tablewithchild",this.urlParams).then((res) => {
                    this.list = res.data;
                    this.newVal = this.value;
                });
            }
        }
    };
</script>