<template>
    <el-row :gutter="20">
        <el-col :span="24">
            <el-card style="margin-bottom: 10px">
                <div slot="header">
                    <span style="font-size: 120%;">课程订单量统计</span>
                    <el-date-picker v-model="date1" type="month" placeholder="选择月" size="mini" style="float: right; width: 120px"></el-date-picker>
                </div>
                <charts-builder content="productOrders" :date="date01" type="histogram"></charts-builder>
            </el-card>
        </el-col>

    </el-row>
</template>
<script>
    import ChartsBuilder from '@/components/ChartsBuilder'
    export default {
        components: {ChartsBuilder},
        data() {
            return {
                name: '统计',
                date1: '',

            }
        },
        computed: {
            date01: function () {
                if(this.date1) return new Date(this.date1).format("yyyy-MM-dd");
                else return null;
            },

        },
    };
</script>