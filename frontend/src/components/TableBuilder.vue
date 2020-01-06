<template>
    <div>
        <!-- :default-expand-all = "expand" -->
        <el-table :data="data" v-loading="loading" size="medium" ref="buildedTable" 
                  @selection-change="handleSelectionChange"
                  @sort-change="handleSortChange"
                  :row-class-name="tableRowClassName"
                  @row-click="handleToggleExpansion">
            <el-table-column type="expand" v-if="expand">
                <template slot-scope="scope">
                    <slot name="expandContent" :data="scope.row"></slot>
                </template>
            </el-table-column>
            <el-table-column type="index" :index="indexMethod" v-if="showIndex"></el-table-column>
            <el-table-column type="selection" width="30" :selectable="handleSelection" v-if="handleSelection"></el-table-column>
            <el-table-column v-for="(item,index) in fields" :key="index" :label="item.title" :width="item.width" :sortable = "item.sortable || false">
                <template slot-scope="scope">
                    <node-content :content="item.content" :row="scope.row" :text="scope.row[item.name]"></node-content>
                </template>
            </el-table-column>
            <el-table-column label="" :width="actionWidth" class-name="action-column" v-if="actionButtonVisible">
                <template slot-scope="scope">
                    <el-button v-for="(item,index) in actionButtons" :key="index" type="text"
                               @click.stop="item.click(scope.row)" :icon="item.icon" size="small"
                               v-show="handleButtonVisible(item,scope.row)">
                       {{item.title}}
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-row class="row-bg">
            <el-col :span="actionMultiButtons.length > 0 ? 12 : 0">
                <el-button v-for="(item,index) in actionMultiButtons" :key="index" :type="item.type" v-show="handleMultiButtonVisible(item)" @click="item.click(multipleSelectionVal)" plain :icon="item.icon" size="small">{{item.title}}</el-button>
            </el-col>
            <el-col :span="actionMultiButtons.length > 0 ? 12 : 24" style="text-align: right" v-if="this.showPaginate">
                <el-pagination background @current-change="handleCurrentChange" :current-page.sync="urlParam.page" :page-size="pagination.pagePer" :total="pagination.pageTotal" layout="prev, pager, next, total"></el-pagination>
            </el-col>
        </el-row>
        <!--{{ item.content ? item.content(scope.row) : scope.row[item.name] }}-->
        <!--<node-content :content="item.content" :text="scope.row[item.name]" :row="scope.row"></node-content>-->
        <!--<node-content :content="item.content(h,{scope.row})"></node-content>-->

    </div>
</template>
<script>
   /* eslint-disable */
    /* 用法
    <table-builder
        :dataUrl = "dataUrl"                  // 表单加载的数据请求地址 GET请求
        :condition = 'getDataCondition'       // 查询数据的筛选条件
        :fields = "fields"                    // name是字段名，title是显示th名，content是自定义显示内容，name和content二选一
        :actionButtons = "actionButtons"      // 操作按钮
        :actionWidth = "260"
        :showIndex = 'true'                   // 显示序列
        :showPaginate = "true"                 // 显示分页
        :tableRowClassName = "true"                 // 每行样式
        :actionMultiButtons = "tableActionMultiButtons"    // 同actionButtons
        :handleSelection = 'handleSelection'               // 多选框是否可以选中方法 只要定义就会显示多选框 不定义 不显示多选框
    ></table-builder>

     tableProps : {
         fields : [
             {name:"user_name" ,  title :'姓名'  },
             {name:"username" ,  title :'账号'  },
             {name:'created_at', title :'申请日期'},
             {name:'status', title :'状态'},
         ],
         tableActionButtons:[
             {title:'通过' , icon:"el-icon-check" , click:(row)=>{ this.handleApprove(row.id) }},
             {title:'驳回' , icon:"el-icon-close" , click:(row)=>{ this.handleReject(row.id) }},
         ],
         tableActionMultiButtons:[
             {title:'批量通过' , icon:"el-icon-check" , click:(val)=>{ this.handleMultiApprove(val) } },
             {title:'批量驳回' , icon:"el-icon-close" , click:(val)=>{ this.handleMultiReject(val) }},
         ],
     },
     urlParam : {
         search_name : '',
         search_sn : '',
         search_division : ''
     },
     如果要使用组件里的方法，需要用在mounted中
    */

    export default {
        props: {
            dataUrl: {required: true},
            fields: {},
            fieldsStyle: {},
            fieldsWidth: {},
            condition: {},
            actionButtons: {default:() => []},
            actionWidth: {},
            handleSelection: {default : false},
            showIndex : {default : true},
            showPaginate : {default : true},
            actionMultiButtons: {default:() => []},
            // actionButtonVisible: {default : true},
            expand: {default : false}, // 是否有展开列
            tableRowClassName: {},
        },
        computed: {
            actionButtonVisible: function() {
                return this.actionButtons.length > 0
            }
        },
        components: {
            NodeContent: {
                props: {
                    content:{},
                    row:{},
                    text:{}
                },
                render(h) {
                  // let con = this.content ? this.content(this.row) : this.text
                  // return h('span',[con])
                  if(typeof(this.content) === 'function') return this.content(this.row)
                  else  return h('span',[this.text])
                }
                // render(h) {
                //   if(this.content)
                //     return this.content(this.row);
                //   else
                //     return <span>{this.text}</span>;
                // }
            }
        },
        data() {
            return {
                data : [],
                loading : false,
                urlParam : {
                    page : 1, // 当前页码
                },
                firstOpen: true, // 有页码的页码会发送两次请求，第一次过滤掉。
                multipleSelectionVal:[],
                pagination : {
                    pageTotal : 0 , // 总共列表数据数量
                    pagePer : 20 , // 每页显示数据数量
                }
            }
        },
        created(){
            this.getData();
        },
        methods: {
            getData(p) {
                if(p) this.urlParam.page = p;
                this.loading = true;
                this.$http.fetch(this.dataUrl , Object.assign(this.urlParam,this.condition || {}) ).then((res) =>{
                    this.loading = false;
                    if(this.showPaginate) {
                        this.data = res.data;
                        this.firstOpen = false;
                        this.urlParam.page = res.meta.current_page;
                        this.pagination.pageTotal = res.meta.total;
                    }else{
                        this.data = res.data;
                    }
                });
            },
            handleSelectionChange(val) {
                this.multipleSelectionVal = val;
                this.$emit('update:selectedValues', val)
            },
            handleSortChange(params) {
              if (params.column) {
                let sort_type;
                if (params.order === 'ascending') {
                  sort_type = 'asc';
                } else {
                  sort_type = 'desc';
                }
                this.$emit('handleSortChange', {sort_by: params.column.label, sort_type})
              }
            },
            handleCurrentChange(val) {
                this.urlParam.page = val;
                this.firstOpen || this.getData(); // 有页码的页码会发送两次请求，第一次过滤掉。
            },
            indexMethod(index) {
                return (this.urlParam.page - 1) * this.pagination.pagePer + index + 1;
            },
            handleButtonVisible(item,row){
                if(item.pm != null){
                    if(this.$util.hasPermission(item.pm)){
                        return item.visible==null ? true : item.visible(row)
                    }else{
                        return false;
                    }
                }else{
                    return item.visible==null ? true : item.visible(row)
                }
            },
            handleMultiButtonVisible(item){
                if(item.pm){
                    return this.$util.hasPermission(item.pm)
                }else{
                    return false;
                }
            },
            handleToggleExpansion(row) {
              if (this.expand) {
                this.$refs.buildedTable.toggleRowExpansion(row);
              }
            }
        }
    };
</script>