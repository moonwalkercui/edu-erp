<template>
    <section>
        <template>
            <el-alert title="一次可发布50个值班计划; 学员可以在小程序里按老师的值班情况预约课程; 只有购买长期课的学员才可以预定。" type="info" show-icon></el-alert>
        </template>
        <div style="margin-top: 20px; margin-bottom: 20px">
            <el-form ref="ruleForm" size="mini" :inline="true" label-width="100px" class="add-buttons-line">
                <el-form-item label="上课地点">
                   <select-builder table="division" v-model="division" :style="{width: '150px'}"></select-builder>
                    <!-- <select-data-builder :list="divisions" table="division" v-model="division"></select-data-builder> -->
                </el-form-item>
                <el-form-item label="" style="margin-left: 20px">
                    <el-button-group>
                        <el-button size="mini" @click="insertTimes" icon="el-icon-arrow-right">单次添加</el-button>
                        <el-button size="mini" @click="dialogMulitiInsert = true" icon="el-icon-d-arrow-right">批量添加</el-button>
                    </el-button-group>
                </el-form-item>
            </el-form>
        </div>
        <el-form :model="formData" ref="validateForm" :show-message="false" size="small">
            <el-table
                    ref="multipleTable"
                    :data="data"
                    tooltip-effect="dark"
                    empty-text="暂无课时请添加"
                    style="width: 100%"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="30">
                </el-table-column>
                <el-table-column label="值班老师" width="150" sortable>
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.user'" :rules="{required: true, message: '老师须必选', trigger: 'click,blur,change' }">
                            <el-select v-model="scope.row.user"  placeholder="请选择" >
                                <el-option v-for="item in users" :key="item.username" :label="item.name" :value="item.username"></el-option>
                            </el-select>
                        </el-form-item>
                    </template>
                </el-table-column>
                <!-- <el-table-column label="班级" width="150" sortable>
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.class'" :rules="{required: true, message: '班级须必选', trigger: 'click,blur,change' }" >
                            <el-select v-model="scope.row.class" placeholder="请选择" >
                                <el-option v-for="item in classes" :key="item.id" :label="item.name" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </template>
                </el-table-column> -->

                <!-- <el-table-column label="教室" width="110" sortable prop="room">
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.room'" :rules="{required: true, message: '教室须必选', trigger: 'click,blur,change' }" >
                            <el-select v-model="scope.row.room" placeholder="请选择" >
                                <el-option v-for="item in rooms" :key="item.id" :label="item.name" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </template>
                </el-table-column> -->
                <el-table-column label="长期课" width="240" sortable prop="product">
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.product'" :rules="{required: true, message: '课程须必选', trigger: 'click,blur,change' }" >
                            <el-select v-model="scope.row.product" placeholder="请选择" >
                                <el-option v-for="item in products" :key="item.id" :label="item.name" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="值班日期" width="150" sortable prop="date">
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.date'" :rules="{required: true, message: '日期须必选'}" >
                            <el-date-picker v-model="scope.row.date" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" size="small" style="width:140px"></el-date-picker>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="开始时间" width="110" >
                    <template  slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.start_at'" :rules="{required: true, message: '开始时间须必选'}" >
                            <el-time-select placeholder="开始" v-model="scope.row.start_at" size="small" style="width:100px"
                                    :picker-options="{ start: '06:30', step: '00:05', end: '23:00'}">
                            </el-time-select>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="结束时间" width="110" >
                    <template  slot-scope="scope">
                        <el-form-item style="margin-bottom: 0" :prop="'row.' + scope.$index + '.end_at'" :rules="{required: true, message: '结束时间须必选'}" >
                            <el-time-select placeholder="结束" v-model="scope.row.end_at" size="small" style="width:100px"
                                    :picker-options="{ start: '06:30', step: '00:05', end: '23:00', minTime: scope.row.start_at }">
                            </el-time-select>
                        </el-form-item>
                    </template>
                </el-table-column>

                <el-table-column label="" >
                    <template slot-scope="scope">
                        <el-form-item style="margin-bottom:0">
                            <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        </el-form-item>
                    </template>
                </el-table-column>
            </el-table>
            <el-row class="row-bg">
                <el-col :span="8">
                    <el-button size="medium" plain @click="deleteSelection">删除选中</el-button>
                    <el-button type="primary" size="medium" @click="submitForm('validateForm')">立刻发布</el-button>
                </el-col>
            </el-row>
        </el-form>
        <el-dialog title="批量添加" :visible.sync="dialogMulitiInsert" width="700px">
            <el-form label-width="120px" size="small" class="dialogForm" :model="dialogData" :rules="rules" ref="dialogInsertForm" >
                <el-form-item label="起始日期" prop="day">
                    <el-date-picker v-model="dialogData.day" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <!-- <el-form-item label="选择班级">
                    <select-data-builder :list="classes" table="class" v-model="dialogData.class"></select-data-builder>
                </el-form-item> -->
                <el-form-item label="选择课程">
                    <select-data-builder :list="products" table="product" v-model="dialogData.product"></select-data-builder>
                </el-form-item>
                <el-form-item label="选择老师">
                    <el-select v-model="dialogData.user" placeholder="请选择" >
                      <el-option v-for="user in users" :key="user.username" :label="user.name" :value="user.username"></el-option>
                    </el-select>
                    <!-- <select-data-builder :list="users" table="user" v-model="dialogData.user"></select-data-builder> -->
                </el-form-item>
                <!-- <el-form-item label="选择教室">
                    <select-data-builder :list="rooms" table="room" v-model="dialogData.room"></select-data-builder>
                </el-form-item> -->
                <el-form-item label="上课时间">
                    <el-time-select placeholder="起始时间" v-model="dialogData.startTime" style="width: 120px"
                                    :picker-options="{ start: '06:30', step: '00:05', end: '23:00'}">
                    </el-time-select>
                    <el-time-select placeholder="结束时间" v-model="dialogData.endTime" style="width: 120px"
                                    :picker-options="{ start: '06:30', step: '00:05', end: '23:00', minTime: dialogData.startTime }">
                    </el-time-select>
                </el-form-item>
                <el-tabs type="card" value="first"  @tab-click="changeTab">
                    <el-tab-pane label="周频率" name="first">
                        <el-form-item label="选择星期">
                            <el-checkbox-group v-model="dialogData.weekDay" size="mini" >
                                <el-checkbox-button v-for="day in dialogData.weekDays" :label="day" :key="day">{{day}}</el-checkbox-button>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-tab-pane>
                    <el-tab-pane label="日频率" name="second">
                        <el-form-item label="选择日">
                        <el-select v-model="dialogData.monthDay" multiple style="width: 450px">
                            <el-option v-for="n in 10" :key="n" :label="n + '日'" :value="n"></el-option>
                        </el-select>
                        </el-form-item>
                    </el-tab-pane>
                </el-tabs>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogMulitiInsert = false">取 消</el-button>
                <reqButton @handleReq = "submitInsert('dialogInsertForm')" />
            </div>
        </el-dialog>
    </section>
</template>
<script>
    import {reject, map} from 'lodash'
    import selectDataBuilder from '@/components/SelectDataBuilder'
    import selectBuilder from '@/components/SelectBuilder'
//    const weekDays = ['一', '二', '三', '四','五', '六','日'];
    export default {
        data() {
            return {
                data: [],
                division: '',
                multipleSelection: [],
                users: [],
              divisions: [],
                products: [],
                classes: [],
                rooms: [],
                dialogMulitiInsert: false,
                dialogData: {
                    day: '',
                    weekDays: [ '日', '一', '二', '三', '四', '五', '六'],
                    weekDay: [],
                    monthDay: [],
                    startTime: '',
                    endTime: '',
                    product: '',
                    class: '',
                    room: '',
                    user: '',
                    type: 'week' // week 按星期 day 按日
                },
                rules: {
                    day: [ { required: true, message: '起始日期须必选' } ]
                }
            }
        },
        components: { selectBuilder, selectDataBuilder },
        computed: {
            formData: function () { return { row : this.data } }
        },
        watch: {
            division: function () {
                // this.getClassList();
                // this.getRoomList();
            }
        },
        created() {
            this.insertTimes();
            this.getUserList();
            this.getdivisionList();
            this.getProductList();
            // this.getClassList();
            // this.getRoomList();
              
        },
        methods: {
            changeTab(a) {
                if(a.index == 0) this.dialogData.type = 'week';
                if(a.index == 1) this.dialogData.type = 'day';
            },
            submitInsert(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid == false) {
                        this.$message.error('有内容未填充或不符合要求');
                        return false;
                    }
//                console.log(this.dialogData);
                    var startDay = this.dialogData.day[0],
                            endDay = this.dialogData.day[1],
                            sencondOnDay = 1000*24*60*60 , // 一天的毫秒数
                            dayLength = (endDay.getTime() - startDay.getTime()) / sencondOnDay + 1, // 计算天数
                            dayArr=[];

//                console.log(endDay);return;
                    if(this.dialogData.type == 'week') {
                        var selectDay = map(this.dialogData.weekDay, function(n){
                            var val;
                            switch (n) {
                                case '日' : val = 0 ; break;
                                case '一' : val = 1 ; break;
                                case '二' : val = 2 ; break;
                                case '三' : val = 3 ; break;
                                case '四' : val = 4 ; break;
                                case '五' : val = 5 ; break;
                                case '六' : val = 6 ; break;
                                default: val = null;
                            }
                            return val;
                        });

                        for( var i = 0; i < dayLength; i++ ) {
                            var d = startDay.getTime() + sencondOnDay * i;
//                        if(selectDay.length == 7 || selectDay.length == 0 ){
//                            dayArr[i] = new Date(d).format("yyyy-MM-dd");
//                        }else{
                            if(selectDay.indexOf(new Date(d).getDay()) >= 0)
                                dayArr[i] = new Date(d).format("yyyy-MM-dd");
//                        }
                        }
                    }
                    if(this.dialogData.type == 'day') {
                        for( var m = 0; m < dayLength; m++ ) {
                            var dd = startDay.getTime() + sencondOnDay * m;
                            if(this.dialogData.monthDay.indexOf(new Date(dd).getDate()) >= 0)
                                dayArr[m] = new Date(dd).format("yyyy-MM-dd");
                        }
                    }
                    if(dayArr.length == 0 ){
                        this.$message('请设置频率该范围内无课时!');
                    }else{
                        for (var j = 0; j < dayArr.length; j++) {
                            if(dayArr[j]){
                                this.data.push({
                                    class: this.dialogData.class,
                                    product: this.dialogData.product,
                                    room: this.dialogData.room,
                                    date: dayArr[j],
                                    start_at: this.dialogData.startTime,
                                    end_at: this.dialogData.endTime,
                                    user: this.dialogData.user,
                                });
                            }
                        }
                        this.dialogMulitiInsert = false;
                    }
                });
//                console.log(this.dialogData.weekDay);
            },
            insertTimes() {
                this.data.push({
                    class: '',
                    product: '',
                    date: '',
                    room: '',
                    start_at: '',
                    end_at:'',
                    user: '',
                });
            },
            submitForm(formName) {
                if(this.data.length > 30) {
                    this.$message.error('发布数量超限制: 一次最多可发布30个课时');
                    return null;
                }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post("course/create",{"data": this.data , division:this.division }).then((res) =>{
                            if(res.status == 'success'){
                                this.$message({ message: res.msg, type: 'success' });
                                this.$router.push('/course/list');
                            }
                        });
                    } else {
                        this.$message.error('有内容未填充或不符合要求');
                        return false;
                    }
                });
            },
            handleDelete(index) {
                this.data.splice(index,1);
            },
            deleteSelection() {
                this.multipleSelection.forEach((row) => {
                    this.data = reject(this.data, row);
                });
//                console.log(this.data);
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            getdivisionList() {
                this.$http.fetch("org/divisions").then((res) =>{
                    this.divisions = res.data;
                    // this.division = this.$cookie.fetchJson('_userInfo').division;
                });
            },
            getUserList() {
                this.$http.fetch("user/table",{search_status:1}).then((res) =>{
                    this.users = res.data;
                });
            },
            getProductList() {
                this.$http.fetch("product/table").then((res) =>{
                    this.products = res.data;
                });
            },
            // getClassList() {
            //     this.$http.fetch("class/table",{search_division : this.division}).then((res) =>{
            //         this.classes = res.data;
            //     });
            // },
            // getRoomList() {
            //     this.$http.fetch("room/table",{search_division : this.division}).then((res) =>{
            //         this.rooms = res.data;
            //     });
            // }
        }
    }
</script>
<style >
    .add-buttons-line .el-form-item{
        margin:0;
        padding:0;
    }
    .dialogForm .el-form-item{
        padding-bottom:18px;
    }
</style>