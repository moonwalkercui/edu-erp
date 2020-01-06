<template>
    <component v-bind:is="componentIs" :data="chartData" height="300px" :toolbox="toolbox" :loading="loading" :extend="extend"></component>
</template>

<script>
    import veHistogram from 'v-charts/lib/histogram'
    import veLine from 'v-charts/lib/line'
    import vePie from 'v-charts/lib/pie'
    export default {
        props: {
            content:{},
            type:{default:"line"},
            date:{}
        },
        data() {
            this.extend = {
                label: { show: true, position: "top" }
            }
            return {
                componentIs:'',
                veHistogram,
                veLine,
                vePie,
                chartData: {
                    columns:[],
                    rows:[]
                },
                loading: false,
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                }
            }
        },
        watch:{
            date : function(){
                this.getData();
            }
        },
        created(){
            if(this.type == 'line'){
                this.componentIs = this.veLine;
            }else if(this.type == 'pie'){
                this.componentIs = this.vePie;
            }else{
                this.componentIs = this.veHistogram;
            }
            this.getData();
        },
        methods: {
            getData(){
                switch (this.content) {
                    case 'orderDay' :       url='statistics/orderday';       this.title = "日订单金额统计";      break;
                    case 'orderDayCount' :  url='statistics/orderdaycount';  this.title = "日订单数统计";      break;
                    case 'orderMonth' :     url='statistics/ordermonth';     this.title = "月订单统计";      break;
                    case 'proceedsDay' :    url='statistics/proceedsday';    this.title = "日收款金额统计";      break;
                    case 'proceedsMonth' :  url='statistics/proceedsmonth';  this.title = "月收款金额统计";      break;
                    case 'signDay' :        url='statistics/signday';        this.title = "日签到统计";      break;
                    case 'memberSigns' :    url='statistics/membersigns';    this.title = "学员签到统计";    break;
                    case 'courseDay' :      url='statistics/courseday';      this.title = "日课时统计";      break;
                    case 'courseMonth' :    url='statistics/coursemonth';    this.title = "月课时统计";      break;
                    case 'userCourses' :    url='statistics/usercourses';    this.title = "老师课时统计";    break;
                    case 'productOrders' :  url='statistics/productorders';  this.title = "产品成单量统计";  break;
                    case 'userOrders' :     url='statistics/userorders';     this.title = "老师成单量统计";  break;
                    case 'userOrdersMoney' :     url='statistics/userordersmoney';     this.title = "老师成单金额统计";  break;
                    case 'userOrdersCount' :     url='statistics/userorderscount';     this.title = "老师成单量统计";  break;

                    default: this.$message.error('请求内容有误');
                }
                var url;
                this.loading = true;
                this.$http.fetch(url,{date:this.date}).then((res) =>{
                    if(typeof res.status != "undefined" && res.status == 'success'){
                        this.chartData.columns = res.data.columns;
                        this.chartData.rows = res.data.rows;
                    }
                    this.loading = false;
                });
            },
        }
    };
</script>