(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6c42f660"],{"61db":function(t,e,i){"use strict";var a=i("1da1"),n=i("b85c"),l=(i("96cf"),i("a9e3"),i("a434"),i("cf45"));e["a"]={props:{visible:{type:void 0|Boolean,required:!0},condition:{type:Object,default:function(){return{}}},limit:{type:Number,default:0}},computed:{isSingle:function(){return 1==this.limit},dialogVisible:{get:function(){return this.visible&&this.handleClear(),!!this.visible},set:function(t){this.$emit("update:visible",t)}}},methods:{handleRequest:function(t){Object.assign(this.tableConfig.condition,this.condition),t&&Object.assign(this.tableConfig.condition,t),this.$refs.tableBuilder.loadData()},handleClear:function(){this.selectedData=[]},handleSelect:function(t){if(1==this.limit)return this.selectedData=[t],void this.formSubmit();var e,i=Object(n["a"])(this.selectedData);try{for(i.s();!(e=i.n()).done;){var a=e.value;if(a==t)return}}catch(s){i.e(s)}finally{i.f()}0!=this.limit&&this.selectedData.length>=this.limit?Object(l["e"])("最多选择 ".concat(this.limit," 项"),"info"):(this.selectedData.push(t),console.log(this.selectedData))},remove:function(t){this.selectedData.splice(t,1)},formSubmit:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:this.$emit("update:visible",!1),this.$emit("onSubmit",this.selectedData);case 2:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}()}}},edad:function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("cui-dialog",{attrs:{title:"选择老师",visible:t.dialogVisible,width:t.isSingle?"650px":"800px",submitBtnText:"选好了",showSubmitBtn:!t.isSingle,"custom-class":"custom-dialog"},on:{"update:visible":function(e){t.dialogVisible=e},submit:t.formSubmit}},[i("el-row",{attrs:{gutter:10}},[i("el-col",{attrs:{span:t.isSingle?24:12}},[i("cui-search",{attrs:{config:t.searchConfig},on:{handleRequest:t.handleRequest}}),i("cui-table",{ref:"tableBuilder",attrs:{config:t.tableConfig}})],1),t.isSingle?t._e():i("el-col",{attrs:{span:12}},[i("el-form",{staticStyle:{"margin-bottom":"16px"},attrs:{"label-width":"80px",size:"mini"}},[i("div",{staticStyle:{display:"flex"}},[i("div",{staticStyle:{flex:"1"}},[i("div",{staticStyle:{"lin-height":"30px"}},[t._v("已选列表")])]),i("div",{staticClass:"text-right",staticStyle:{width:"100px"}},[i("el-button",{attrs:{size:"mini",type:"primary",plain:""},on:{click:t.handleClear}},[t._v("清空")])],1)])]),i("el-table",{staticStyle:{width:"100%"},attrs:{border:"",data:t.selectedData,size:"small"}},[i("el-table-column",{attrs:{prop:"address",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[i("el-button",{attrs:{type:"text"},on:{click:function(i){return t.remove(e.$index)}}},[t._v(" < 移除")])]}}],null,!1,3858462404)}),i("el-table-column",{attrs:{prop:"name",label:"姓名"}})],1)],1)],1)],1)},n=[],l=i("d282"),s=i("61db"),c={name:"TeacherSelectDialog",mixins:[s["a"]],data:function(){var t=this;return{tableConfig:{url:l["a"].teacherList,condition:{isManager:!1},fields:[{title:"姓名",name:"name"},{title:"职位",name:"positionName"}],actions:[{title:"选择 >",click:function(e){t.handleSelect(e)}}],height:450},selectedData:[],searchConfig:{fields:[{label:"姓名",name:"keyword",type:"input",placeholder:"姓名"}]}}}},o=c,r=i("2877"),u=Object(r["a"])(o,a,n,!1,null,null,null);e["default"]=u.exports}}]);