<template>
    <el-select v-model="content" placeholder="" clearable :style="styles" @change="handleChange">
    </el-select>
</template>
<script>
    export default {
        props: {
            toName:{},
            fromName:{},
        },
        data() {
            return {
                content: '',
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

            }
        }

    };
</script>