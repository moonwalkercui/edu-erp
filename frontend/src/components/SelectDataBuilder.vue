<template>
    <el-select v-model="newVal" :multiple="isMultiple" placeholder="请选择" :style="styles" @change="handleChange">
        <el-option v-for="(item,index) in list" :key="index" :label="item.name" :value="item.id"></el-option>
    </el-select>
</template>
<script>
    export default {
        props: {
            value: {},
            styles: {},
            isMultiple: {},
            table: {
                type: String,
                required: true
            },
            list: {
                default: []
            },
        },
        data() {
            return {
                newVal: this.isMultiple ? [] : '',
            }
        },
        watch: {
            value: function (val) {
                this.newVal = val;
            }
        },
        methods: {
            handleChange(){
                this.$emit('input', this.newVal)
            }
        }

    };
</script>