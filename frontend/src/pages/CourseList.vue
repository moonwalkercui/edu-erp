<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="small">
            <helper key-id='courselist' float='right'/>
            <el-form-item >
                <el-button type="primary" @click="$router.push('/course/schedule')" icon="el-icon-plus" v-if="$util.hasPermission(56)">排课</el-button>
                <el-button v-if="$util.hasPermission(56)" @click="$exportExcel('course/table',urlParam)" icon="el-icon-download">导出</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="text" @click="showFilter = !showFilter" icon="el-icon-arrow-down"> 筛选</el-button>
            </el-form-item>
        </el-form>
        <el-card :body-style="{ padding: '14px' }" style="margin-bottom: 15px;" v-if="showFilter">
            <div>
                <el-form :inline="true" ref="form" label-width="80px" size="mini">
                    <el-form-item label="门店">
                        <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
                    </el-form-item>
                    <!-- <el-form-item label="Class">
                        <select-builder table="class" v-model="urlParam.search_class" :style="{width: '150px'}" ></select-builder>
                    </el-form-item> -->
                    <el-form-item label="课程">
                        <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
                    </el-form-item>
                    <el-form-item label="老师">
                        <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
                    </el-form-item>
                    <el-form-item label="状态">
                        <select-status v-model="urlParam.search_status" statusName="course" :style="{width: '150px'}"></select-status>
                    </el-form-item>
                    <el-form-item label="日期">
                        <el-date-picker v-model="urlParam.search_days" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="编号">
                        <el-input v-model="urlParam.search_sn" placeholder="" :style="{width: '150px'}"></el-input>
                    </el-form-item>
                </el-form>
                <div class="bottom clearfix" style="margin-top: 10px;">
                    <el-button size="small" type="primary" @click="$refs.tableBuilder.getData(1)">查 询</el-button>
                </div>
            </div>
        </el-card>

        <table-builder
                ref="tableBuilder"
                dataUrl="course/table"
                :fields="tableProps.fields"
                :showIndex="false"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "220"
                :actionMultiButtons = "tableProps.tableActionMultiButtons"
                :handleSelection = "handelSelect"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>

        <el-dialog title="查看冲突的课时" :visible.sync="dialogClashVisible" width="700px">
            <el-table :data="clashesData" style="width: 100%" v-loading="dialogLoading">
                <el-table-column label="时间">
                    <template slot-scope="scope">
                        {{ scope.row.date}} {{ scope.row.start_at|formatTime}} ~ {{scope.row.end_at|formatTime }}
                    </template>
                </el-table-column>
                <el-table-column prop="user_name" label="老师"></el-table-column>
                <el-table-column prop="product_name" label="课程"></el-table-column>
                <!-- <el-table-column prop="class_name" label="班级"></el-table-column> -->
                <!-- <el-table-column prop="room_name" label="教室" width="100"></el-table-column> -->
                <el-table-column label="" width="100">
                    <template slot-scope="scope">
                        <el-button icon="el-icon-edit" @click="$router.push({ path: '/course/edit/' + scope.row.id})" type="text" size="mini" v-if="$util.hasPermission(62)">修改</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <CourseSigns :visible.sync="signsFormVisible" :item.sync = "showSignsItem"/>
        <homework :visible.sync="homeworkVisible" :courseId = "currentHomeworkCourseId"/>
    </section>
</template>
<style scope>
    .clearfix{
        text-align: center;
    }
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }
</style>
<script type="text/ecmascript-6">
    import {map} from 'lodash'
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    import selectStatus from '@/components/SelectStatus'
    import homework from '@/components/Homework'
    import CourseSigns from '@/components/CourseSigns'
    export default {
        components: { selectBuilder , selectStatus , TableBuilder, homework, CourseSigns},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'编号', name:"sn", width:'99' },
                        {title :'时间', name:"start_at", width:"180"
                            ,content:(row)=>{
                                var clash;
                                if(row.clash_ids){
                                    clash = (<el-button size='mini' type="text" style={{color:'red'}} icon="el-icon-info" onClick={()=>this.showClashes(row.clash_ids)} >有冲突 </el-button>);
                                }
                                return (
                                    <span>
                                        {new Date(row.date).format('MM/dd D') + " " + row.start_at.substring(0,5) + " ~ " +row.end_at.substring(0,5)}
                                        {clash}
                                    </span>
                                )
                            }
                        },
                        {title :'老师' , name:"user_name" , width:'100' },
                        {title :'课程名' , content:(row)=>{
                            return (
                                <span>{ row.product_name }</span>
                            )
                        } },
                        {title :'门店' , content:(row)=>{
                            return (
                                <span>{ row.division_name }</span>
                            )
                        } },
                        // {title :'课题' , name:'title' },
                         {title :'课题' , content:(row)=>{
                            return (
                                <span>{ row.title }
                                <el-button type="text" icon="el-icon-edit" onClick={()=>this.editTitle(row.id,row.title)}></el-button></span>
                            )
                        } },
                        {title :'签到' , width:'80' , content:(row)=>{
                          return (<el-button  type="text" size="small" ><el-tag size="mini" onClick={()=> this.showSigns(row) }>{ row.signs_count + ' / ' + row.deal_count  }</el-tag></el-button>)
                        } },
                        {title :'状态' , name:'status', width:"80"},
                        // {title :'班级' , name:"class_name" },
                        // {title :'教室' , name:'room_name' , width:"100"},
                    ],

                    tableActionButtons:[
                      {title:'编辑' ,pm:62, icon:"el-icon-edit" , click:(row)=>{ this.$router.push({ path: '/course/edit/' + row.id})} },
                      {title:'停课' ,pm:59, icon:"el-icon-close" , click:(row)=>{ this.handleStop(row) } , visible:(row)=>{ return row.status == '正常' }},
                      {title:'开课' ,pm:60, icon:"el-icon-check" , click:(row)=>{ this.handleOpen(row) } , visible:(row)=>{ return row.status == '已停课' }},
                      {title:'无作业' , icon:"el-icon-tickets" , click:(row)=>{ this.viewHomework(row) } , visible:(row)=>{ return row.homework_count == 0 }},
                      {title:'有作业' , icon:"el-icon-tickets" , click:(row)=>{ this.viewHomework(row) } , visible:(row)=>{ return row.homework_count > 0 }},
                    ],
                    tableActionMultiButtons:[
                        {title:'批量停课' ,pm:59, icon:"el-icon-check" , click:(val)=>{ this.multipleStop(val) } },
                        {title:'批量开课' ,pm:60, icon:"el-icon-close" , click:(val)=>{ this.multipleOpen(val) }},
                    ],
                },
                urlParam : {
                    search_days : [],
                    search_product : '',
                    search_class : '',
                    search_status : '',
                    search_user : '',
                    search_division : '',
                    search_sn : '',
                    with: 'sign',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },

                dialogLoading: false,
                formLabelWidth: '120px',
                formChangeOwner: {
                    owner: '',
                    ownerName: '',
                    mobile: '',
                },
                signsFormVisible: false,
                showSignsItem: {},
                dialogClashVisible: false,
                multipleSelection: [],
                multipleDialogSelection: [],
                clashesData:[],
                editingCourse:'',
                showFilter: false,
                handledSign: false,
                homeworkVisible: false,
                currentHomeworkCourseId: 0,
            }
        },

        created() {
//            var userInfo = this.$cookie.fetchJson('_userInfo');
//            this.urlParam.search_division = userInfo.org_division;
        },
        watch:{
            signsFormVisible:function() {
                this.$refs.tableBuilder.getData();
            }
        },
        methods: {
            handelSelect(row) {
                return Date.parse(row.date + ' ' + row.end_at) > Date.parse(new Date()) ;
            },
            editTitle(row_id,row_title){
                this.$prompt(' ', '编辑课题', {
                    confirmButtonText: '提交',
                    cancelButtonText: '取消',
                    inputPlaceholder: row_title,
                    inputPattern: /./,
                    inputErrorMessage: '请输入课题'
                }).then(({ value }) => {
                    this.$http.post('course/edittitle',{"id":row_id,'title':value}).then((res) => {
                        if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            handleStop: function (row) {
                this.$http.fetch('course/stop',{"ids":row.id}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                           this.$refs.tableBuilder.getData();
                    }
                });
            },
            handleOpen: function (row) {
                this.$http.fetch('course/open',{"ids":row.id}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                           this.$refs.tableBuilder.getData();
                    }
                });
            },
         
            multipleStop(val) {
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');
                this.$http.fetch('course/stop',{"ids":ids}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                           this.$refs.tableBuilder.getData();
                    }
                });
            },
            multipleOpen(val) {
                if(val.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                var ids = map(val, 'id');
                this.$http.fetch('course/open',{"ids":ids}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                           this.$refs.tableBuilder.getData();
                    }
                });
            },
        
            tableRowClassName({row}) {
                if (row.clash_ids) {
                    return 'is-warning';
                } else if (row.status == '已停课') {
                    return 'is-stop';
//                } else if ( Date.parse(row.end_at) < Date.parse(new Date()) ) {
//                    return 'is-finish';
                }else{
                    return '';
                }
            },
            showSigns(row){
                this.signsFormVisible = true;
                this.showSignsItem = row
            },
            showClashes(ids){
                this.clashesData = [];
                this.dialogClashVisible = true;
                this.dialogLoading = true;
                this.$http.fetch("course/table",{"search_ids" : ids}).then((res) =>{
                    this.clashesData = res.data;
                    this.dialogLoading = false;
                });
            },
            // handledivisionChange(id){
            //     this.getClasses(id);
            // },
//            refreshMembers() {
//                this.$confirm('此操作将重新生成该课时学员的签到记录(已签到的不受影响), 是否继续?', '提示', {
//                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
//                }).then(() => {
//                    this.$http.post('course/refreshMember',{"id":this.editingCourse}).then((res) => {
//                        if(res.status == 'success'){
//                            this.$message.success(res.msg);
//                            this.showSigns(this.editingCourse);
//                            this.$refs.tableBuilder.getData();
//                        }
//                    });
//                }).catch(() => {
//                });
//            },
            multipleDeleteSigns () {
                if(this.multipleDialogSelection.length == 0 ) {
                    this.$message.error('未选择'); return;
                }
                this.$confirm('删除操作会影响学员的课时数, 是否继续?', '提示', {
                    confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
                }).then(() => {
                    var ids = map(this.multipleDialogSelection, 'id');
                        this.$http.post('coursesign/delete',{"ids":ids}).then((res) => {
                            if(res.status == 'success'){
                            this.$message({ message: res.msg, type: 'success' });
                            // this.showSigns(this.editingCourse);
                            this.$refs.tableBuilder.getData();
                        }
                    });
                }).catch(() => {
                });
            },
            viewHomework(row) {
              this.currentHomeworkCourseId = row.id
              this.homeworkVisible = true
            },
        },
    }
</script>
