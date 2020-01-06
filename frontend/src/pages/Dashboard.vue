<template>
    <section>
        <el-row :gutter="10" class="el-row-dashboard">
            <el-col :span="8" style="margin-bottom: 10px">
                <el-card shadow="hover">

                    <div slot="header">
                        <span><i class="fa fa-area-chart"></i> 快捷方式</span>
                        <!--<el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more"></el-button>-->
                    </div>
                    <el-row :gutter="10">
                        <!-- <el-col :span="6">
                            <el-badge :value="100" :max="10" style="display: block">
                                <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/order/create')">
                                    <i class="fa fa-cny fa-2x"></i>
                                    <span>报 单</span>
                                </el-button>
                            </el-badge>
                        </el-col> -->
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/course/schedule')">
                                <!-- <el-badge is-dot style="display: block"> -->
                                <i class="fa fa-object-group fa-2x"></i>
                                <!-- </el-badge> -->
                                <span>排 课</span>
                            </el-button>
                        </el-col>
                        <!-- <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/class/list')">
                                <i class="fa fa-users fa-2x"></i>
                                <span>排 班</span>
                            </el-button>
                        </el-col> -->
                        <el-col :span="6">
                              <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/order/list')">
                                  <i class="fa fa-cny fa-2x"></i>
                                  <span>订 单</span>
                              </el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/product/list')">
                                <i class="fa fa-coffee fa-2x"></i>
                                <span>课 程</span>
                            </el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/course/calendar')">
                                <i class="fa fa-calendar fa-2x"></i>
                                <span>课 表</span>
                            </el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/member/list')">
                                <i class="fa fa-user-circle-o fa-2x"></i>
                                <span>学 员</span>
                            </el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/user/list')">
                                <i class="fa fa-address-book-o fa-2x"></i>
                                <span>老 师</span>
                            </el-button>
                        </el-col>
                        <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/shop/goods')">
                                <i class="fa fa-shopping-bag fa-2x"></i>
                                <span>商 品</span>
                            </el-button>
                        </el-col>
                         <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/org/divisions')">
                                <i class="fa fa-sitemap fa-2x"></i>
                                <span>店 面</span>
                            </el-button>
                        </el-col>
                   
                    
                        <!-- <el-col :span="6">
                            <el-button class="big-button bg-purple btn-dsb" @click="$router.push('/material/list')">
                                <i class="fa fa-cubes fa-2x"></i>
                                <span>物 料</span>
                            </el-button>
                        </el-col> -->
                    </el-row>
                </el-card>

            </el-col>
            <el-col :span="8">
                <el-card shadow="hover">
                    <div slot="header">
                        <span><i class="fa fa-signal"></i> 系统数据</span>
                        <!--<el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more"></el-button>-->
                    </div>
                    <el-row :gutter="10">
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>订单数</span><h3>{{numbers.orders}}</h3>
                        </div></el-col>
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>课程数</span><h3>{{numbers.courses}}</h3>
                        </div></el-col>
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>老师数</span><h3>{{numbers.members}}</h3>
                        </div></el-col>
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>学员数</span><h3>{{numbers.users}}</h3>
                        </div></el-col>
                        <!-- <el-col :span="12"><div class="grid-content bg-purple">
                            <span>报单数</span><h3>66</h3>
                        </div></el-col> -->
                        <!-- <el-col :span="12"><div class="grid-content bg-purple">
                            <span>班级数</span><h3>66</h3>
                        </div></el-col> -->
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>商品数</span><h3>{{numbers.goods}}</h3>
                        </div></el-col>
                        <el-col :span="12"><div class="grid-content bg-purple">
                            <span>代金券</span><h3>{{numbers.vouchers}}</h3>
                        </div></el-col>
                    </el-row>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover">
                    <div slot="header">
                        <span><i class="fa fa-info-circle"></i> 最新订单</span>
                        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" @click="$router.push('/order/list')"></el-button>
                    </div>
                    <ul class="news">
                        <li v-for="item in orders" :key="item.sn">
                           [{{item.created_at|formatDate("MM/dd hh:mm")}}] ￥{{item.total_price}} {{item.status}}
                        </li>
                    </ul>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="10" class="el-row-dashboard">
            <el-col :span="16">
                <el-card shadow="hover" style="margin-bottom: 10px;">
                    <div slot="header">
                        <span><i class="fa fa-calendar"></i> 课表日程</span>
                        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" @click="$router.push('/course/calendar')"></el-button>
                    </div>
                    <course-calendar :data-list="courses" @update="getCourseList" ></course-calendar>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" style="margin-bottom: 10px;">
                    <div slot="header">
                        <span><i class="fa fa-bar-chart-o"></i> 本月成交金额曲线</span>
                        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" @click="$router.push('/statistics/orders')"></el-button>
                    </div>
                    <charts-builder content="orderDay" type="line"></charts-builder>
                </el-card>
                <el-card shadow="hover" style="margin-bottom: 10px;">
                    <div slot="header">
                        <span><i class="fa fa-area-chart"></i> 本月课时数曲线</span>
                        <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-more" @click="$router.push('/statistics/courses')"></el-button>
                    </div>
                    <charts-builder content="courseDay" type="line"></charts-builder>
                </el-card>
            </el-col>
        </el-row>
    </section>
</template>
<script type="text/ecmascript-6">
    import courseCalendar from '@/components/CourseCalendar'
    import ChartsBuilder from '@/components/ChartsBuilder'
    export default {
        components: { courseCalendar,ChartsBuilder},

        data() {
            return {
                name: '',
                courses:[],
                numbers:{
                  orders: 0,
                  members: 0,
                  users: 0,
                  courses: 0,
                },
                tableData:[],
                orders:[],
                loading:false,
            };
        },
        created: function () {
            this.getCourseList();
            this.getNumbers();
            this.getOrders();
        },
        methods: {
            getCourseList() {
                this.$http.fetch('course/table',this.urlParam).then((res) =>{
                    this.courses = res.data;
                });
            },
            getNumbers() {
                this.$http.fetch('normal/systemnumber').then((res) =>{
                    this.numbers = res.data;
                });
            },
            getOrders() {
                this.$http.fetch('order/table',{page:1, per_page:6}).then((res) =>{
                    this.orders = res.data;
                });
            },
        }
    }
</script>
<style lang="scss">
    /*.full-calendar-body .dates .dates-events .events-week .events-day{min-height: 80px;}*/
    /*.full-calendar-body .dates .week-row .day-cell{min-height: 80px;}*/
    .el-row-dashboard .el-card{
        min-height: 240px;
    }
    .el-row-dashboard .el-card .el-card__header ,.el-row-dashboard .el-card .el-card__body {
        padding: 10px;
    }
    .block-title {
        font-weight: bold;
    }
    .big-button {
        width: 100%;
        padding: 5px 0;
        margin: 5px;
    }
    .big-button span{
        padding-top: 7px;
        padding-bottom: 3px;
        display: block;
    }
    /*.el-row {*/
        /*margin-bottom: 20px;*/
        /*&:last-child {*/
             /*margin-bottom: 0;*/
         /*}*/
    /*}*/
    .el-row-dashboard .el-col {
        border-radius: 4px;
    }

    .bg-purple {
        background: #F2F6FC;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
        padding: 7px 8px;
        margin: 5px 0;
        color: #606266;
    }
    .grid-content h3{
        font-size: 24px;
        font-weight: normal;
        color: #0d71bb;
        margin: 5px;
        float: right;
    }
    .el-row-dashboard ul {
        margin: 0;
        padding: 0 ;
    }
    .el-row-dashboard ul.news li {
        list-style : none;
        background-color: #F2F6FC;
        line-height: 28px;
        height: 28px;
        margin: 2px 0px;
        padding: 0 10px;
        border-radius: 3px;
    }
    .el-row-dashboard ul.news li a{
        text-decoration: none;
        color: #606266;
    }
    .el-row-dashboard ul.news li:nth-child(0) a, ul.news li:nth-child(1) a
    {
        color:#F56C6C;
    }
    .el-row-dashboard .el-dialog__body {
        padding-top: 10px;
    }
    .btn-dsb{
      padding: 0!important;
      margin-bottom: 8px!important;
    }
</style>
