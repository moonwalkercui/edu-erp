<template>
    <section>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
             <helper key-id='coursesigns' float='right'/>
            <el-form-item label="学员">
                <select-builder table="member" v-model="urlParam.search_member" :style="{width: '150px'}" ></select-builder>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="$refs.tableBuilder.getData(1)">查询 </el-button>
            </el-form-item>
        </el-form>
        <table-builder
                ref="tableBuilder"
                dataUrl="coursesign/table"
                :fields="tableProps.fields"
                :condition="urlParam"
                :tableRowClassName="tableRowClassName"
                :actionButtons="tableProps.tableActionButtons"
        ></table-builder>
        <GivePoints :visible.sync="formVisible" :item.sync = "showItem"/>
    </section>
</template>
<script>
    import TableBuilder from '@/components/TableBuilder'
    import selectBuilder from '@/components/SelectBuilder'
    import GivePoints from '@/components/GivePoints'
    export default {
        components: { selectBuilder ,TableBuilder, GivePoints},
        data() {
            return {
                // table相关的参数
                tableProps : {
                    fields : [
                        {title: '学员昵称', content:(row)=>{return (<span>{row.member.nick_name}</span>)}},
                        {title: '学员姓名', content:(row)=>{return (<span>{row.member.name}</span>)}},
                        {title: '学员手机', content:(row)=>{return (<span>{row.member.mobile}</span>)}},
                        {title: '课程', content:(row)=>{return (<span>{row.product_name}</span>)}},
                        {title: '老师', content:(row)=>{return (<span>{row.user_name}</span>)}},
                        {title: '小星星', name: 'points'},
                        // {title: '状态', name: 'status'},
                        {title: '签到时间', name: "sign_at"},
                    ],
                    tableActionButtons:[
                        {title:'奖励小星星', icon:"el-icon-star-on",
                        visible: (row)=>{
                            return row.points == 0;
                        },
                        click:(row)=>{ this.givePoints(row)}},
                    ]
                },
                urlParam : {
                    search_member : '',
                    with:'course'
                },
                formVisible: false,
                showItem: {},
            }
        },
        methods: {
            givePoints(row) {
                this.formVisible = true
                this.showItem = {
                    member_id : row.member_id,
                    course_id : row.course_id,
                }
            },
            tableRowClassName({row}) {
                var status = row.status;
                if (status == '未签到') {
                    return 'is-stop';
                }
                return '';
            },
        }
    }
</script>
