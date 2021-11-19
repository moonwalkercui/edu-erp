(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-b5446b7a"],{"3d80":function(e,t,n){"use strict";var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("cui-dialog",{attrs:{title:e.formData.id?"修改课次":"添加课次",visible:e.dialogVisible,width:"700px"},on:{"update:visible":function(t){e.dialogVisible=t},submit:e.formSubmit}},[n("cui-form",{ref:"formRef",attrs:{config:e.formConfig},on:{update:e.formUpdate}})],1)},s=[],a=n("1da1"),o=(n("96cf"),n("ac1f"),n("1276"),n("cf45")),l=n("8897"),r=n("ed08"),c={id:"",classId:"",courseId:"",teacherIds:"",assistantIds:"",startDate:"",endDate:"",roomId:"",decCount:1},d={name:"CreateLesson",components:{},props:{visible:{type:Boolean,required:!0}},data:function(){return{lessonSetting:[],formConfig:{fields:[{label:"本课主题",name:"title",type:"input"},{label:"选择班级",name:"classId",type:"selectBox",code:"class",limit:1,condition:{over:!0}},{label:"上课老师",name:"teacherIds",type:"selectBox",code:"teacher",info:"支持多个老师，第一位为主讲人"},{label:"助教老师",name:"assistantIds",type:"selectBox",code:"teacher"},{label:"上课日期",name:"date",type:"date"},{label:"开始时间",name:"startTime",type:"time"},{label:"结束时间",name:"endTime",type:"time",minTime:"startTime"},{label:"选择教室",name:"roomId",type:"selectBox",code:"classroom",limit:1},{label:"消课次数",name:"decCount",type:"number"}],rules:{classId:[{required:!0,message:"请选择班级",trigger:"change"}],teacherIds:[{required:!0,message:"请选择上课老师",trigger:"change"}],date:[{required:!0,message:"请设置上课日期"}],startTime:[{required:!0,message:"请选择开始日期"}],endTime:[{required:!0,message:"请选择结束日期"}]}},formData:Object.assign({},c)}},computed:{dialogVisible:{get:function(){return!!this.visible},set:function(e){this.$emit("update:visible",e)}}},created:function(){},methods:{formUpdate:function(e){this.formData=e},formSubmit:function(){var e=Object(a["a"])(regeneratorRuntime.mark((function e(){var t,n,i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,this.$refs.formRef.onValidate();case 2:if(t=e.sent,!0===t){e.next=5;break}return e.abrupt("return");case 5:return n=Object.assign({},this.formData),n.teacherIds=this.formData.teacherIds?this.formData.teacherIds.split(","):[],n.assistantIds=this.formData.assistantIds?this.formData.assistantIds.split(","):[],e.next=10,Object(l["l"])(n);case 10:i=e.sent,Object(r["a"])(i)&&(this.$emit("update:visible",!1),this.$emit("onSubmit"),this.initData(),Object(o["e"])(i.msg,"success"));case 12:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),initData:function(){var e=Object(a["a"])(regeneratorRuntime.mark((function e(t){var n=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:"undefined"==typeof t&&(t=c),setTimeout((function(){n.$refs.formRef.setInitData(t)}),50);case 2:case"end":return e.stop()}}),e)})));function t(t){return e.apply(this,arguments)}return t}()}},u=d,f=n("2877"),m=Object(f["a"])(u,i,s,!1,null,null,null);t["a"]=m.exports},b1a5:function(e,t,n){"use strict";n.r(t);var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("cui-search",{attrs:{config:e.searchConfig,showExport:!0},on:{handleRequest:e.handleRequest,handleExport:e.handleExport}}),n("cui-table",{ref:"tableBuilder",attrs:{config:e.tableConfig,selectedRows:e.selectedRows},on:{"update:selectedRows":function(t){e.selectedRows=t},"update:selected-rows":function(t){e.selectedRows=t}}}),n("CreateLesson",{ref:"createLesson",attrs:{visible:e.dialogVisible},on:{"update:visible":function(t){e.dialogVisible=t},onSubmit:function(t){return e.$refs.tableBuilder.loadData()}}}),n("LessonInfo",{attrs:{visible:e.infoDialog,id:e.lessonId},on:{"update:visible":function(t){e.infoDialog=t}}}),n("RollCall",{attrs:{visible:e.rollcallDialog,id:e.lessonId},on:{"update:visible":function(t){e.rollcallDialog=t}}})],1)},s=[],a=n("d282"),o=n("a49b"),l=n("3d80"),r=n("69d7"),c=n("2cdf"),d=n("cf45"),u={name:"LessonSchedule",components:{CreateLesson:l["a"],LessonInfo:r["a"],RollCall:c["a"]},props:{},data:function(){var e=this,t=this.$createElement;return{infoDialog:!1,lessonId:"",dialogVisible:!1,rollcallDialog:!1,tableConfig:{url:a["a"].lessonList,selectable:!0,condition:{},fields:[{title:"上课时间",width:170,content:function(n){return t("span",{class:"text-link",on:{click:function(){return e.showInfo(n.id)}}},[n.date,"(",n.week,") ",n.startTime,"~",n.endTime])}},{title:"课程",name:"title"},{title:"班级名称",name:"className"},{title:"上课老师",name:"teacherNames",width:95},{title:"类型",name:"lessonType",width:60},{title:"助教",name:"assistantNames",width:100},{title:"教室",name:"classroom",width:80},{title:"学生数",name:"studentNum",width:50},{title:"签到数",name:"studentSignNum",width:50},{title:"到课率",width:50,content:function(e){return t("span",[e.studentNum>0?parseInt(100*e.studentSignNum/e.studentNum):0,"%"])}},{title:"消课数",width:50,name:"decCount"},{title:"状态",width:50,name:"state"}],actionWidth:120,actions:[{title:"编辑",click:function(t){e.dialogVisible=!0,e.$refs.createLesson.initData(t)}},{title:"点名消课",click:function(t){e.lessonId=t.id,e.rollcallDialog=!0}}],topActions:[{title:"课表日历",icon:"el-icon-date",type:"primary",click:function(){e.$router.push("/course/calendar")}},{title:"新增单个课次",icon:"el-icon-plus",click:function(){e.$refs.createLesson.initData(),e.dialogVisible=!0}},{title:"删除课次",icon:"el-icon-delete",click:function(){o["a"].deleteLesson(e.selectedRows,(function(){return e.handleRequest()}))}},{title:"停课",icon:"el-icon-circle-close",click:function(){o["a"].stopLesson(e.selectedRows,(function(){return e.handleRequest()}))}},{title:"恢复上课",icon:"el-icon-refresh",click:function(){o["a"].reopenLesson(e.selectedRows,(function(){return e.handleRequest()}))}}],rowClassName:function(e){var t=e.row;return"已结课"==t.state?"success-row":"已停课"==t.state?"warning-row":""}},selectedRows:[],searchConfig:{fields:[{label:"周期",name:"dateRange",type:"enum",code:"DateRangeNameEnum"},{label:"班级",name:"classIds",type:"selectBox",code:"class"},{label:"课程",name:"courseIds",type:"selectBox",code:"course"},{label:"老师",name:"teacherIds",type:"selectBox",code:"teacher"},{label:"科目",name:"subjectId",type:"select",code:"subject",hidden:!0},{label:"开始日期",name:"startDate",type:"date",hidden:!0},{label:"结束日期",name:"endDate",type:"date",hidden:!0}]}}},methods:{showInfo:function(e){this.lessonId=e,this.infoDialog=!0},handleRequest:function(e){e&&Object.assign(this.tableConfig.condition,e),this.$refs.tableBuilder.loadData()},handleExport:function(e){Object(d["h"])(a["a"].lessonList,e)}}},f=u,m=n("2877"),h=Object(m["a"])(f,i,s,!1,null,null,null);t["default"]=h.exports}}]);