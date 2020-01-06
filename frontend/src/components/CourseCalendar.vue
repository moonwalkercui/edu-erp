<template>
    <section >
        <Calendar :startDate="startDay" :renderHeader="renderHeader" :dateData="fcEvents" :mode="calendarMode" locale="zh-cn" style="min-height: 600px">
            <div slot="header-left">
                <el-button size="mini" round @click="changeMode('month')" v-if="calendarMode !== 'month'">月视图</el-button>
                <el-button size="mini" round @click="changeMode('week')" v-if="calendarMode !== 'week'">周视图</el-button>
            </div>

            <div :class="['calendar-item', { 'is-otherMonth': !item.isCurMonth }]" slot-scope="item" >
                <div :class="['calendar-item-date']" @click="dayClick( item.date.year + '-' + item.date.month + '-' + item.date.date)">
                    {{item.date.date}}
                </div>
                <div class="calendar-item-name" v-for="(i,n) in item.data" :key="n">
                    <div class="calendar-event" :class="i.cssClass" @click="eventClick(i)" v-if="calendarMode === 'week'"><events :row="i.data"/></div>
                    <div class="calendar-event" :class="i.cssClass" @click="eventClick(i)" v-else>{{i.title}}</div>
                </div>
            </div>
        </Calendar>

        <el-dialog title="课程" :visible.sync="dialogVisible" width="700px">
            <el-table :data="dayData">
                <el-table-column label="时间" width="180">
                    <template slot-scope="scope">
                        {{ scope.row.start_at|formatTime }} ~ {{scope.row.end_at|formatTime }}
                    </template>
                </el-table-column>
                <el-table-column property="user_name" label="老师"></el-table-column>
                <el-table-column property="product_name" label="课程名" width="220"></el-table-column>
                <el-table-column property="status" label="状态"></el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog title="课时详情" :visible.sync="dialogDetailVisible" width="500px">
            <ul style="padding-left:30px" class="details-list">
                <li>课 题 : <b>{{ detailData.title || '无' }}</b>
                    <el-button type="text" icon="el-icon-edit" @click="editTitle(detailData.id,detailData.title)"
                               v-if="type=='mine'"></el-button>
                </li>
                <li>课 程 : {{ detailData.product_name }}</li>
                <li>老 师 : {{ detailData.user_name }}</li>
                <!-- <li>班 级 : {{ detailData.class_name }}</li> -->
                <!-- <li>教 室 : {{ detailData.room_name }}</li> -->
                <li>时 间 : {{ detailData.start_at|formatTime }} ~ {{ detailData.end_at|formatTime}}</li>
                <li>人 数 : {{ detailData.quantity_member }}</li>
                <li>状 态 : {{ detailData.status }}</li>
                <li>学员签到 : 
                  <el-button type="text" size="small">
                    <el-tag size="mini" @click="showSigns(detailData)">{{ '签到 ' + detailData.signs_count + '人 / 报名' + detailData.deal_count + '人' }}</el-tag>
                  </el-button>
                </li>
            </ul>
        </el-dialog>
        <CourseSigns :visible.sync="signsFormVisible" :item.sync = "showSignsItem"/>
    </section>
</template>
<script>
  /* 需要安装日历插件：
  * 安装：npm install vue2-event-calendar --save
  * import 'vue2-event-calendar/default.css'
  * import Calendar from 'vue2-event-calendar'
  * */
  import 'vue2-event-calendar/default.css'
  import Calendar from 'vue2-event-calendar'
  import CourseSigns from '@/components/CourseSigns'
  export default {
    props: ['showType', 'dataList'],
    data() {
      return {
        data: [],
        fcEvents: [],
        loading: false,
        dialogVisible: false,
        dayData: [],
        dialogDetailVisible: false,
        detailData: {},
        type: this.showType,
        calendarMode: 'month',
        startDay: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(),

        signsFormVisible: false,
        showSignsItem: {},
      }
    },
    components: {
      Calendar,
      CourseSigns,
      events: {
        props: {
          row:{},
        },
        render: function (h) {
          const startTime = h('h4', [this.row.start_at + ' ~ ' + this.row.end_at ])
          const user = h('li', ['老师: ' + this.user_name])
          const title = h('li', ['课题: ' + this.row.title])
          const status = h('li', ['状态: ' + this.row.status])
          // const className = h('li', ['班级: ' + this.row.class_name])
          // const classRoom = h('li', ['教室: ' + this.row.room_name])
          return h('div', [startTime, title, user, status])
        }
      }
    },
    watch: {
      dataList: function () {
        this.data = this.dataList;
        var events = [];
        this.data.forEach((row) => {
          var css;
          if (row.status == '已停课') {
            css = 'is-stop';
          }
          else if (Date.parse(row.date + ' ' + row.end_at) < Date.parse(new Date())) {
            css = 'is-finish';
          }
          else {
            css = 'is-opening'
          }
          events.push({
            title: row.start_at.substring(0,5) + ' ' + (this.type == 'all' ? row.user_name : '') + ' - ' + row.product_name ,
            date : row.date,
            start: row.start_at,
            end: row.end_at,
            cssClass: css,
            data: row
          });
        });
        this.fcEvents = events;
      },
    },
    created() {},
    methods: {
      showSigns(row){
        this.signsFormVisible = true;
        this.showSignsItem = row
      },
      changeMode(mode) {
        this.calendarMode = mode
      },
      // renderEventDetail(row) {
      //   // const h = this.$createElement
      //   // const user = h('div', [row.usernmae])
      //   // const classRoom = h('div', [row.room_name])
      //   // return h('div', [user, classRoom])
      // },
      renderHeader({ prev, next, selectedDate }) {
        const h = this.$createElement
        const prevButton = h('i', {
          class: ['calendar-prev-button', 'el-icon-arrow-left'],
          on: { click: prev }
        })
        const nextButton = h('i', {
          class : ['calendar-next-button', 'el-icon-arrow-right'],
          on: { click: next }
        })
        const middleDate = h('span', {
          class : ['calendar-middle-date'],
          on: { click: next }
        },[selectedDate])
        return h('span', [prevButton, middleDate, nextButton])
      },
      dayClick(day) {
        // console.log(day)
        // this.$http.fetch("course/minebyday", {"day": new Date(day).format("yyyy-MM-dd")}).then((res) => {
        this.$http.fetch("course/tablebyday", {"day": new Date(day).format("yyyy-MM-dd")}).then((res) => {
          this.dayData = res.data;
          this.dialogVisible = true;
        });
      },
      eventClick(event) {
        this.detailData = event.data;
        this.dialogDetailVisible = true;
      },
      editTitle(row_id, row_title) {
        this.$prompt(' ', '编辑课题', {
          confirmButtonText: '提交',
          cancelButtonText: '取消',
          inputPlaceholder: row_title,
          inputPattern: /./,
          inputErrorMessage: '请输入课题'
        }).then(({value}) => {
          this.$http.post('course/editTitle', {"id": row_id, 'title': value}).then((res) => {
            if (res.status == 'success') {
              this.$message({message: res.msg, type: 'success'});
              this.dialogDetailVisible = false;
              this.$emit('update');
            }
          });
        }).catch(() => {
        });
      }
    }
  }
</script>
<style>
    .vue-calendar .vue-calendar-header-center {
        flex: 2;
    }
    .vue-calendar .calendar-event {
        height: 100%;
        padding: 2px;
        text-overflow: clip;
        border-radius: .25em;
        margin: 1px 0;
        font-size: .8em;
        cursor: pointer;
        color: white;
    }
    .vue-calendar .calendar-event li {
        line-height: 1.6;
    }
    .vue-calendar .calendar-middle-date {
        font-size: 1.1em;
    }
    .vue-calendar .calendar-prev-button, .vue-calendar .calendar-next-button{
        margin: 0px 20px ;
        width: 25px;
        height: 25px;
        line-height: 25px;
        border-radius: 50%;
        border: 1px solid #e8ebee;
        cursor: pointer;
        text-align: center;
    }
    .vue-calendar .calendar-prev-button:hover, .vue-calendar .calendar-next-button:hover{
        background: #e8ebee
    }
    .vue-calendar .vue-calendar-week-title-item {
        text-align: center;
    }
    .vue-calendar .calendar-item {
        line-height: 1.2;
        color: #666;
        padding: 5px;
    }
    .vue-calendar .calendar-item h4 {
        margin: 8px 0;
        font-size: 1.5em;
        text-align: center;
        font-weight: normal;
    }
    .vue-calendar .vue-calendar-day-item:hover .calendar-item {
        background: #e8ebee !important;
        cursor: pointer;
    }
    .calendar-item .calendar-item-date{
        font-family: georgia;
    }
    .vue-calendar .calendar-item.is-otherMonth {
        color: #cdcdcd
    }
    .vue-calendar .vue-calendar-week-title, .vue-calendar-body {
        border-right: 1px solid #e8ebee;

    }
    .vue-calendar .is-stop { background-color: #999!important;}
    .vue-calendar .is-opening { background-color: #525fe1 !important; }
    .vue-calendar .is-finish { background-color: #21ba45!important;}
</style>
