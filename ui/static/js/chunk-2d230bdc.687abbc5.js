(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d230bdc"],{ee28:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("cui-search",{attrs:{config:e.searchConfig},on:{handleRequest:e.handleRequest}}),n("cui-table",{ref:"tableBuilder",attrs:{config:e.tableConfig,selectedRows:e.selectedRows},on:{"update:selectedRows":function(t){e.selectedRows=t},"update:selected-rows":function(t){e.selectedRows=t}}}),n("cui-dialog-form",{ref:"cuiDialogForm",attrs:{title:e.formTitle,formConfig:e.formConfig,visible:e.formVisible},on:{"update:visible":function(t){e.formVisible=t},onSubmit:e.onSubmit}})],1)},i=[],a=n("1da1"),o=n("5530"),c=(n("96cf"),n("d282")),s=n("b775");function u(e){return l.apply(this,arguments)}function l(){return l=Object(a["a"])(regeneratorRuntime.mark((function e(t){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(s["a"])({url:"/common/advertisement/save",method:"post",data:t}));case 1:case"end":return e.stop()}}),e)}))),l.apply(this,arguments)}function m(e){return f.apply(this,arguments)}function f(){return f=Object(a["a"])(regeneratorRuntime.mark((function e(t){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(s["a"])({url:"/common/advertisement/delete",method:"post",data:t}));case 1:case"end":return e.stop()}}),e)}))),f.apply(this,arguments)}function d(e){return p.apply(this,arguments)}function p(){return p=Object(a["a"])(regeneratorRuntime.mark((function e(t){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(s["a"])({url:"/common/advertisement/open",method:"post",data:t}));case 1:case"end":return e.stop()}}),e)}))),p.apply(this,arguments)}function b(e){return h.apply(this,arguments)}function h(){return h=Object(a["a"])(regeneratorRuntime.mark((function e(t){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.abrupt("return",Object(s["a"])({url:"/common/advertisement/close",method:"post",data:t}));case 1:case"end":return e.stop()}}),e)}))),h.apply(this,arguments)}var w=n("2934"),g=n("ed08"),v=n("cf45"),R={turnOn:function(e,t){Object(v["a"])(e,"请先选择")&&Object(w["b"])("确认启用?",d,e,t)},turnOff:function(e,t){Object(v["a"])(e,"请先选择")&&Object(w["b"])("确认停用?",b,e,t)}},y={name:"AdvertisementList",components:{},props:{},data:function(){var e=this,t=this.$createElement;return{formTitle:"",tableConfig:{url:c["a"].advertisementList,selectable:!0,condition:{},fields:[{title:"图片",content:function(e){return t("el-image",{style:{width:"100px"},attrs:{src:e.cover}})},width:100},{title:"公告标题",name:"title"},{title:"内容",name:"content"},{title:"类型",name:"type",width:120},{title:"状态",content:function(e){return t("span",[e.state?"启用":"停用"])},width:80},{title:"发布者",name:"creatorName",width:80},{title:"发布时间",name:"addTime",width:120}],actions:[{title:"编辑",click:function(t){e.formVisible=!0,e.formTitle="修改公告信息";var n=Object(o["a"])({},t);n.cover=[n.cover],e.$refs.cuiDialogForm.initFormData(n)}}],topActions:[{title:"新增",icon:"el-icon-plus",type:"primary",click:function(){e.formVisible=!0,e.formTitle="新增公告信息",e.$refs.cuiDialogForm.onReset()}},{title:"删除",icon:"el-icon-delete",click:function(){Object(w["c"])(m,e.selectedRows,(function(){return e.handleRequest()}))}},{title:"启用",icon:"el-icon-check",click:function(){R.turnOn(e.selectedRows,(function(){return e.handleRequest()}))}},{title:"停用",icon:"el-icon-close",click:function(){R.turnOff(e.selectedRows,(function(){return e.handleRequest()}))}}]},selectedRows:[],searchConfig:{fields:[{label:"搜索标题",name:"title",type:"input"},{label:"发布者",name:"creator",type:"selectBox",code:"staff",limit:1},{label:"类型",name:"type",type:"enum",code:"AdvertisementTypeEnum"}]},formConfig:{fields:[{label:"公告标题",name:"title",type:"input"},{label:"类型",name:"type",type:"enum",code:"AdvertisementTypeEnum"},{label:"公告内容",name:"content",type:"textarea"},{label:"图片",name:"cover",type:"img",limit:1}],rules:{title:[{required:!0,message:"请输入标题"}],content:[{required:!0,message:"请输入内容"}],type:[{required:!0,message:"请选择类型"}]}},formVisible:!1}},methods:{handleRequest:function(e){e&&Object.assign(this.tableConfig.condition,e),this.$refs.tableBuilder.loadData()},onSubmit:function(){var e=Object(a["a"])(regeneratorRuntime.mark((function e(t){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,u(t);case 2:n=e.sent,Object(g["a"])(n)&&(this.$refs.cuiDialogForm.onReset(),this.$refs.tableBuilder.loadData());case 4:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}()}},O=y,j=n("2877"),k=Object(j["a"])(O,r,i,!1,null,null,null);t["default"]=k.exports}}]);