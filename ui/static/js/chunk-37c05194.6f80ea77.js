(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-37c05194"],{"24fd":function(t,e,n){"use strict";n.d(e,"b",(function(){return l})),n.d(e,"c",(function(){return s})),n.d(e,"d",(function(){return u})),n.d(e,"a",(function(){return p}));var i=n("1da1"),r=(n("96cf"),n("b775")),a=n("d282");function l(t){return o.apply(this,arguments)}function o(){return o=Object(i["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",Object(r["a"])({url:a["a"].orgList,method:"get",params:e}));case 1:case"end":return t.stop()}}),t)}))),o.apply(this,arguments)}function s(t){return c.apply(this,arguments)}function c(){return c=Object(i["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",Object(r["a"])({url:"/common/org/info",method:"get",params:{id:e}}));case 1:case"end":return t.stop()}}),t)}))),c.apply(this,arguments)}function u(t){return d.apply(this,arguments)}function d(){return d=Object(i["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",Object(r["a"])({url:"/common/org/save",method:"post",data:e}));case 1:case"end":return t.stop()}}),t)}))),d.apply(this,arguments)}function p(t){return h.apply(this,arguments)}function h(){return h=Object(i["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",Object(r["a"])({url:"/common/org/delete",method:"post",data:e}));case 1:case"end":return t.stop()}}),t)}))),h.apply(this,arguments)}},5487:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("cui-dialog",{attrs:{title:"选择机构",visible:t.dialogVisible,width:t.isSingle?"650px":"800px",submitBtnText:"选好了",showSubmitBtn:!t.isSingle,"custom-class":"custom-dialog"},on:{"update:visible":function(e){t.dialogVisible=e},submit:t.formSubmit}},[n("el-row",{attrs:{gutter:10}},[n("el-col",{attrs:{span:t.isSingle?24:12}},[n("el-form",{staticStyle:{"margin-bottom":"16px"},attrs:{"label-width":"80px",size:"mini"}},[n("div",{staticStyle:{display:"flex"}},[n("div",{staticStyle:{flex:"1"}},[n("div",{staticStyle:{"lin-height":"30px"}},[t._v("请选择")])])])]),n("OrgTree",{attrs:{onlyTree:!0,type:0},on:{onSelect:t.handleSelect}})],1),t.isSingle?t._e():n("el-col",{attrs:{span:12}},[n("el-form",{staticStyle:{"margin-bottom":"16px"},attrs:{"label-width":"80px",size:"mini"}},[n("div",{staticStyle:{display:"flex"}},[n("div",{staticStyle:{flex:"1"}},[n("div",{staticStyle:{"lin-height":"30px"}},[t._v("已选列表")])]),n("div",{staticClass:"text-right",staticStyle:{width:"100px"}},[n("el-button",{attrs:{size:"mini",type:"primary",plain:""},on:{click:t.handleClear}},[t._v("清空")])],1)])]),n("el-table",{staticStyle:{width:"100%"},attrs:{border:"",data:t.selectedData,size:"small"}},[n("el-table-column",{attrs:{prop:"address",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return t.remove(e.$index)}}},[t._v(" < 移除")])]}}],null,!1,3858462404)}),n("el-table-column",{attrs:{prop:"name",label:"机构名"}})],1)],1)],1)],1)},r=[],a=n("d149"),l=n("d282"),o=n("61db"),s={name:"StaffOrgDialog",components:{OrgTree:a["a"]},mixins:[o["a"]],data:function(){var t=this;return{tableConfig:{url:l["a"].orgList,condition:this.condition,fields:[{title:"机构名",name:"name"}],actions:[{title:"选择 >",click:function(e){t.handleSelect(e)}}],height:450},selectedData:[]}}},c=s,u=n("2877"),d=Object(u["a"])(c,i,r,!1,null,null,null);e["default"]=d.exports},"61db":function(t,e,n){"use strict";var i=n("1da1"),r=n("b85c"),a=(n("96cf"),n("a9e3"),n("a434"),n("cf45"));e["a"]={props:{visible:{type:void 0|Boolean,required:!0},condition:{type:Object,default:function(){return{}}},limit:{type:Number,default:0}},computed:{isSingle:function(){return 1==this.limit},dialogVisible:{get:function(){return this.visible&&this.handleClear(),!!this.visible},set:function(t){this.$emit("update:visible",t)}}},methods:{handleRequest:function(t){Object.assign(this.tableConfig.condition,this.condition),t&&Object.assign(this.tableConfig.condition,t),this.$refs.tableBuilder.loadData()},handleClear:function(){this.selectedData=[]},handleSelect:function(t){if(1==this.limit)return this.selectedData=[t],void this.formSubmit();var e,n=Object(r["a"])(this.selectedData);try{for(n.s();!(e=n.n()).done;){var i=e.value;if(i==t)return}}catch(l){n.e(l)}finally{n.f()}0!=this.limit&&this.selectedData.length>=this.limit?Object(a["e"])("最多选择 ".concat(this.limit," 项"),"info"):(this.selectedData.push(t),console.log(this.selectedData))},remove:function(t){this.selectedData.splice(t,1)},formSubmit:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:this.$emit("update:visible",!1),this.$emit("onSubmit",this.selectedData);case 2:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}()}}},d149:function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticStyle:{"min-width":"210px"}},[n("el-row",{staticStyle:{"margin-bottom":"10px"},attrs:{gutter:10}},[n("el-col",[t.onlyTree?t._e():n("el-button",{attrs:{type:"primary",icon:"el-icon-plus",size:"mini"},on:{click:t.handleAdd}},[t._v("添加机构")]),n("div",{staticStyle:{display:"inline-block","margin-left":"6px","max-width":"130px"}},[n("el-input",{attrs:{placeholder:"搜索名称",size:"mini"},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}})],1),n("div",{staticStyle:{display:"inline-block","margin-left":"6px"}},[n("el-button",{attrs:{icon:"el-icon-search",circle:"",size:"mini"},on:{click:function(e){return t.handleSearch()}}}),n("el-button",{attrs:{icon:"el-icon-refresh",circle:"",size:"mini"},on:{click:function(e){return t.handleRefresh()}}})],1)],1)],1),n("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,"row-key":"id","highlight-current-row":"",lazy:"",load:t.load,"tree-props":{children:"children",hasChildren:"hasChildren"}},on:{"row-click":t.rowClick}},[n("el-table-column",{attrs:{prop:"name",label:t.onlyTree?"机构列表":"机构名称"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("b",[t._v(" "+t._s(e.row.name)+" ")])]}}])}),t.onlyTree?t._e():n("el-table-column",{attrs:{prop:"level",label:"级别"}}),t.onlyTree?t._e():n("el-table-column",{attrs:{prop:"contactName",label:"联系人"}}),t.onlyTree?t._e():n("el-table-column",{attrs:{prop:"phone",label:"电话"}}),t.onlyTree?t._e():n("el-table-column",{attrs:{label:"操作",width:"220"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{attrs:{type:"text",size:"mini"},on:{click:function(n){return n.stopPropagation(),t.handleAddChild(e.row)}}},[t._v("添加子机构")]),n("el-button",{attrs:{type:"text",size:"mini"},on:{click:function(n){return n.stopPropagation(),t.handleEdit(e.row)}}},[t._v("编辑")]),n("el-button",{attrs:{type:"text",size:"mini"},on:{click:function(n){return n.stopPropagation(),t.handleDelete(e.row)}}},[t._v("删除")]),n("el-button",{attrs:{type:"text",size:"mini"},on:{click:function(n){return n.stopPropagation(),t.showInfo(e.row.id)}}},[t._v("查看")])]}}],null,!1,3816045926)})],1),n("cui-dialog-form",{ref:"cuiDialogForm",attrs:{title:t.formTitle,formConfig:t.formConfig,visible:t.formVisible},on:{"update:visible":function(e){t.formVisible=e},onSubmit:t.onSubmit}})],1)},r=[],a=n("1da1"),l=(n("96cf"),n("a9e3"),n("b0c0"),n("24fd")),o=n("2934"),s=n("ed08"),c=n("cf45"),u={name:"OrgTree",components:{},props:{onlyTree:{type:Boolean,default:!0},type:{type:Number|String,default:""}},data:function(){return{name:"",tableData:[],parentId:"",formTitle:"",formConfig:{fields:[{label:"机构名",name:"name",type:"input"},{label:"级别",name:"level",type:"enum",code:"OrgLevelEnum",width:"50%"},{label:"简称",name:"shortname",type:"input",width:"50%"},{label:"联系人",name:"contactName",type:"input",width:"50%"},{label:"电话",name:"phone",type:"input",width:"50%"},{label:"传真",name:"fax",type:"input",width:"50%"},{label:"邮箱",name:"email",type:"input",width:"50%"},{label:"排序",name:"sortNum",type:"number",width:"50%"},{label:"状态",name:"state",type:"switch",width:"50%",default:!0},{label:"说明",name:"info",type:"textarea"}],rules:{name:[{required:!0,message:"请输入名称"}],level:[{required:!0,message:"请选择级别"}]},labelWidth:"90px"},formVisible:!1}},mounted:function(){this.handleRequest(0)},methods:{showInfo:function(t){this.$router.push("/teacher/orgDetail?id="+t)},handleSearch:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(){var e;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(""!=this.name){t.next=3;break}return Object(c["e"])("请输入名称","error"),t.abrupt("return");case 3:return t.next=5,Object(l["b"])({type:this.type,name:this.name});case 5:e=t.sent,this.tableData=e;case 7:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}(),handleRefresh:function(){this.name="",this.handleRequest(0)},handleRequest:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(e){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(l["b"])({pid:e||0,type:this.type,name:this.name});case 2:n=t.sent,this.tableData=n;case 4:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),load:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(e,n,i){var r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(l["b"])({pid:e.id});case 2:r=t.sent,i(r);case 4:case"end":return t.stop()}}),t)})));function e(e,n,i){return t.apply(this,arguments)}return e}(),handleAdd:function(){this.parentId=0,this.formTitle="新增机构",this.formVisible=!0,this.$refs.cuiDialogForm.onReset()},handleAddChild:function(t){this.parentId=t.id,this.formTitle="新增子机构",this.formVisible=!0,this.$refs.cuiDialogForm.onReset()},handleEdit:function(t){this.parentId=t.pid,this.formTitle="修改机构信息",this.formVisible=!0,this.$refs.cuiDialogForm.initFormData(t)},handleDelete:function(t){var e=this;Object(o["c"])(l["a"],[t],(function(){e.tableData=[],e.handleRequest(0)}))},onSubmit:function(){var t=Object(a["a"])(regeneratorRuntime.mark((function t(e){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.pid=this.parentId,t.next=3,Object(l["d"])(e);case 3:n=t.sent,Object(s["a"])(n)&&(this.$refs.cuiDialogForm.onReset(),this.tableData=[],this.handleRequest(0));case 5:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),rowClick:function(t){this.$emit("onSelect",t)}}},d=u,p=n("2877"),h=Object(p["a"])(d,i,r,!1,null,null,null);e["a"]=h.exports}}]);