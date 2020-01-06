<template>
    <div>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item>
                <el-button type="primary" icon="el-icon-tickets" @click="$router.push('/course/mine')">列表</el-button>
            </el-form-item>
       
            <el-form-item label="课程" prop="search_product">
                <select-builder table="product" v-model="urlParam.search_product" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item label="状态" prop="search_user">
                <select-status v-model="urlParam.search_status" statusName="course" :style="{width: '100px'}"></select-status>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="getDataList()">查询</el-button>
            </el-form-item>
        </el-form>
        <course-calendar :data-list="data" showType="mine" @update="getDataList"></course-calendar>
    </div>
</template>
<script>
    import courseCalendar from '@/components/CourseCalendar'
    import selectStatus from '@/components/SelectStatus'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: { courseCalendar , selectStatus , selectBuilder},
        data () {
            return {
                data:[],
                loading:false,
                urlParam : {
                    search_product : '',
                    search_class : '',
                    search_status : '',
                    search_user : '',
                    search_division : '',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                    with: 'sign'
                },
            }
        },
        created(){
            this.getDataList();
        },
        methods: {
            getDataList() {
                this.loading = true;
                this.$http.fetch('course/mine',this.urlParam).then((res) =>{
                    this.data = res.data;
                    this.loading = false;
                });
            },
        }
    }
</script>