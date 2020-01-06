<template>
    <section>

        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item>
                <el-button type="primary" icon="el-icon-date" @click="$router.push('/course/myCalendar')" >日历</el-button>
            </el-form-item>
            <el-form-item label="编号">
                <el-input v-model="urlParam.search_sn" placeholder="" :style="{width: '150px'}"></el-input>
            </el-form-item>
            <el-form-item label="日期" prop="search_days">
                <el-date-picker v-model="urlParam.search_days" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item label="课程" prop="search_product">
                <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="状态" prop="search_user">
                <select-status v-model="urlParam.search_status" statusName="course" :style="{width: '100px'}"></select-status>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="onSearchSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <table-builder
                ref="tableBuilder"
                dataUrl="course/mine"
                :fields="tableProps.fields"
                :showIndex="true"
                :condition="urlParam"
                :actionButtons = "tableProps.tableActionButtons"
                :actionWidth = "100"
                :tableRowClassName = "tableRowClassName"
        ></table-builder>

        <CourseSigns :visible.sync="signsFormVisible" :item.sync = "showSignsItem"/>
        <HomeworkCreate :visible.sync="homeworkVisible" :courseId = "currentHomeworkCourseId"/>
    </section>
</template>
<script>
    import {map} from 'lodash'
    import selectStatus from '@/components/SelectStatus'
    import selectBuilder from '@/components/SelectBuilder'
    import TableBuilder from '@/components/TableBuilder'
    import HomeworkCreate from '@/components/HomeworkCreate'
    import CourseSigns from '@/components/CourseSigns'
    export default {
        components: {selectStatus, selectBuilder, TableBuilder, HomeworkCreate, CourseSigns},
        data() {
            return {
                homeworkVisible: false,
                currentHomeworkCourseId: '',
                // table相关的参数
                tableProps : {
                    fields : [
                        {title :'编号' , name:"sn", width:'99' },
                        {title :'时间' ,width:"180" ,name:"start_at"
                            ,content:(row)=>{
                                return (
                                    <span>
                                        {new Date(row.date).format('MM/dd D') + " " + row.start_at.substring(0,5) + " ~ " +row.end_at.substring(0,5)}
                                    </span>
                                )
                            }
                        },
                        {title :'课程' , name:"product_name" },
                        {title :'课题' , content:(row)=>{
                            return (
                                <span>{ row.title }
                                <el-button type="text" icon="el-icon-edit" onClick={()=>this.editTitle(row.id,row.title)}></el-button></span>
                            )
                        } },
                        {title :'状态' , name:'status', width:"80"},
                        {title :'签到' , width:"80", content:(row)=>{
                            return (
                                <el-button type="text" size="small"><el-tag size="mini" onClick={()=>this.showSigns(row)}>{ row.signs_count } / { row.deal_count }</el-tag></el-button>
                            )
                        }},
                        {title :'作业' , width:"80", content:(row)=>{
                          let text
                          let icon
                          if (row.homework_count > 0) {
                            icon = 'el-icon-info'
                            text = '修改'
                          } else {
                            icon = 'el-icon-plus'
                            text = '布置'
                          }
                          let button = <el-button onClick={()=>this.createHomework(row.id)} type="text" size="small" icon={icon}>{text}</el-button>
                          return <span>{button}</span>
                        }}
                    ]
               
                },
                urlParam : {
                    search_days : [],
                    search_product : '',
                    search_class : '',
                    search_status : '',
                    search_sn : '',
                    with: 'sign'
                },
                formLabelWidth: '120px',
                formChangeOwner: {
                    owner: '',
                    ownerName: '',
                    mobile: '',
                },
                signsFormVisible: false,
                showSignsItem: {},
                multipleDialogSelection: [],
                signData:[],
                status: [],
                handledSign: false
            }
        },
        created() {
        },
        watch:{
            signsFormVisible:function() {
                this.$refs.tableBuilder.getData();
            }
        },
        methods: {
            createHomework(id) {
              this.currentHomeworkCourseId = id
              this.homeworkVisible = true
            },
        
            onSearchSubmit() {
                this.$refs.tableBuilder.getData(1);
            },
       
            multipleStop() {
                var ids = map(this.multipleSelection, 'id');
                this.$http.fetch('course/stop',{"ids":ids}).then((res) => {
                    if(res.status == 'success'){
                        this.$message({ message: res.msg, type: 'success' });
                        this.$refs.tableBuilder.getData();
                    }
                });
            },
           
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '已停课') {
                    return 'is-stop';
                }
                // else if ( Date.parse(row.end_at) < Date.parse(new Date()) ){
                //     return 'is-finish';
                // }
                return '';
            },
            showSigns(row){
                this.signsFormVisible = true;
                this.showSignsItem = row
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
          
        }
    }
</script>
