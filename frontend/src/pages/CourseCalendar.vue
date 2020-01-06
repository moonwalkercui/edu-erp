<template>
    <div>
        <el-form :model="urlParam" :inline="true" ref="searchForm" class="demo-form-inline" size="mini">
            <el-form-item>
                <el-button type="primary" icon="el-icon-tickets" @click="$router.push('/course/list')">列表</el-button>
            </el-form-item>
            <el-form-item label="门店">
                <select-builder table="division" v-model="urlParam.search_division" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <!-- <el-form-item label="Class">
                <select-builder table="class" v-model="urlParam.search_class" :style="{width: '150px'}" :condition="{'search_division' : urlParam.search_division}"></select-builder>
            </el-form-item> -->
            <el-form-item label="老师">
                <select-builder table="user" v-model="urlParam.search_user" :style="{width: '150px'}"></select-builder>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="getDataList()">查询</el-button>
            </el-form-item>
        </el-form>
        <course-calendar :data-list="data" showType="all" @update="getDataList"></course-calendar>
    </div>
</template>
<script>
    import courseCalendar from '@/components/CourseCalendar'
    // import selectStatus from '@/components/SelectStatus'
    import selectBuilder from '@/components/SelectBuilder'
    export default {
        components: { courseCalendar, selectBuilder }, 
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
                    sort_type : 'asc',
                    // search_division : this.$cookie.fetchJson('_userInfo').division,
                },
            }
        },
        created(){
            this.getDataList();
        },
        methods: {
            getDataList() {
                this.loading = true;
                this.$http.fetch('course/table',this.urlParam).then((res) =>{
                    this.data = res.data;
                    this.loading = false;
                });
            },
        }
    }
</script>