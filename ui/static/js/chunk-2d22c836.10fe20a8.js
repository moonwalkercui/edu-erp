(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d22c836"],{f40b:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("cui-search",{attrs:{config:e.searchConfig},on:{handleRequest:e.handleRequest}}),a("cui-table",{ref:"tableBuilder",attrs:{config:e.tableConfig,selectedRows:e.selectedRows},on:{"update:selectedRows":function(t){e.selectedRows=t},"update:selected-rows":function(t){e.selectedRows=t}}})],1)},s=[],i=a("d282"),l={name:"MyContace",components:{},props:{},data:function(){return{tableConfig:{url:i["a"].contactRecord,condition:{self:!0},fields:[{title:"跟进时间",name:"contactTime",width:110},{title:"客户",name:"studentName",width:70},{title:"阶段",name:"stage",width:100},{title:"跟进方式",name:"contactType",width:80},{title:"下次跟进",name:"contactNextTime",width:110},{title:"记录时间",name:"addTime",width:110},{title:"跟进内容",name:"info"}],rowClassName:function(e){var t=e.row;return"售后阶段"===t.stage?"success-row":""}},selectedRows:[],searchConfig:{fields:[{label:"学员姓名",name:"name",type:"input",placeholder:"姓名或电话"},{label:"跟进阶段",name:"stage",type:"enum",code:"ContactStageEnum"},{label:"开始日期",name:"startDate",type:"date"},{label:"开始结束",name:"endDate",type:"date"}]}}},methods:{handleRequest:function(e){e&&Object.assign(this.tableConfig.condition,e),this.$refs.tableBuilder.loadData()}}},o=l,c=a("2877"),d=Object(c["a"])(o,n,s,!1,null,null,null);t["default"]=d.exports}}]);