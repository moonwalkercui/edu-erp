(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-video-index"],{4449:function(t,e,n){"use strict";n.r(e);var i=n("8f6f"),a=n("4c65");for(var r in a)"default"!==r&&function(t){n.d(e,t,(function(){return a[t]}))}(r);var o,s=n("f0c5"),u=Object(s["a"])(a["default"],i["b"],i["c"],!1,null,"01d3af53",null,!1,i["a"],o);e["default"]=u.exports},"4c65":function(t,e,n){"use strict";n.r(e);var i=n("b186"),a=n.n(i);for(var r in i)"default"!==r&&function(t){n.d(e,t,(function(){return i[t]}))}(r);e["default"]=a.a},"69d9":function(t,e,n){"use strict";var i=n("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=i(n("79cd")),r={data:function(){return{pageData:{page:1,lastPage:1}}},onReachBottom:function(){this.handleReachBottom()},methods:{foo:function(){console.log("foo")},handleReachBottom:function(){this.pageData.lastPage!=this.pageData.page&&(this.pageData.lastPage>this.pageData.page?(this.pageData.page++,this.handleReq()):a.default.showError("没有更多数据"))}}};e.default=r},"8f6f":function(t,e,n){"use strict";var i;n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return r})),n.d(e,"a",(function(){return i}));var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",{staticClass:"content"},t._l(t.list,(function(e,i){return n("v-uni-view",{key:i,staticClass:"cu-card dynamic no-card margin-bottom-sm"},[n("v-uni-view",{staticClass:"cu-item shadow padding-top"},[n("v-uni-view",{staticClass:"text-content"},[t._v(t._s(e.title)),n("v-uni-view",[t._v(t._s(e.content))])],1),e.video_url?n("v-uni-view",{staticClass:"grid flex-sub padding-lr col-3 grid-square"},[n("v-uni-video",{staticStyle:{width:"100%",height:"391upx"},attrs:{id:"myVideo",src:e.video_url,controls:!0},on:{error:function(e){arguments[0]=e=t.$handleEvent(e),t.videoErrorCallback.apply(void 0,arguments)}}})],1):n("v-uni-view",{staticClass:"grid flex-sub padding col-1 grid-square"},t._l(e.images,(function(i,a){return n("v-uni-image",{key:a,staticStyle:{width:"100%"},attrs:{src:t.fixImgUrlFunc(i),mode:"aspectFit"},on:{click:function(n){arguments[0]=n=t.$handleEvent(n),t.previewImg(e.images,a)}}})})),1),n("v-uni-view",{staticClass:"text-gray text-right padding-sm margin-top-sm"},[n("v-uni-view",{staticClass:"inline-block",on:{click:function(n){arguments[0]=n=t.$handleEvent(n),t.handleLike(e.id,i)}}},[n("v-uni-text",{staticClass:"cuIcon-appreciatefill margin-lr-xs"}),t._v(t._s(e.likes_count))],1)],1)],1),0==t.list.length?n("v-uni-view",{staticClass:"text-center padding text-gray"},[t._v("暂无记录")]):t._e()],1)})),1)},r=[]},b186:function(t,e,n){"use strict";var i=n("dbce"),a=n("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var r=a(n("b85c")),o=n("d90e"),s=i(n("79cd")),u=i(n("149b")),c=a(n("69d9")),l={mixins:[c.default],components:{},data:function(){return{list:[],taskList:[],clazzId:"",isMine:0,audioContext:null,showCommentModal:!1,commentContent:"",commentId:0,commentIndex:0}},onLoad:function(){this.handleReq(),uni.setNavigationBarTitle({title:"视频课程开发中"})},onShow:function(){},methods:{videoErrorCallback:function(t){console.log(t.target),uni.showModal({content:t.target.errMsg,showCancel:!1})},handleReq:function(){},fixImgUrlFunc:function(t){return(0,o.fixImgUrl)(t)},previewImg:function(t,e){var n,i=[],a=(0,r.default)(t);try{for(a.s();!(n=a.n()).done;){var o=n.value;i.push(this.fixImgUrlFunc(o))}}catch(s){a.e(s)}finally{a.f()}uni.previewImage({urls:i,current:i[e],longPressActions:{itemList:["发送给朋友","保存图片","收藏"],success:function(t){console.log("选中了第"+(t.tapIndex+1)+"个按钮,第"+(t.index+1)+"张图片")},fail:function(t){console.log(t.errMsg)}}})},handleLike:function(t,e){var n=this;u.get("student/mediaLike",{id:t},(function(t){s.showError(t.msg),n.list[e].likes_count=t.data.count}))}}};e.default=l},d90e:function(t,e,n){"use strict";n("a15b"),n("d81d"),n("fb6a"),n("b680"),n("d3b7"),n("acd8"),n("e25e"),n("ac1f"),n("25f0"),n("5319"),n("841c"),n("1276");var i=n("5d40");function a(t){if("number"!==typeof t||t<0)return t;var e=parseInt(t/3600);t%=3600;var n=parseInt(t/60);t%=60;var i=t;return[e,n,i].map((function(t){return t=t.toString(),t[1]?t:"0"+t})).join(":")}function r(t,e){return"string"===typeof t&&"string"===typeof e&&(t=parseFloat(t),e=parseFloat(e)),t=t.toFixed(2),e=e.toFixed(2),{longitude:t.toString().split("."),latitude:e.toString().split(".")}}var o={UNITS:{"年":315576e5,"月":26298e5,"天":864e5,"小时":36e5,"分钟":6e4,"秒":1e3},humanize:function(t){var e="";for(var n in this.UNITS)if(t>=this.UNITS[n]){e=Math.floor(t/this.UNITS[n])+n+"前";break}return e||"刚刚"},format:function(t){var e=this.parse(t),n=Date.now()-e.getTime();if(n<this.UNITS["天"])return this.humanize(n);var i=function(t){return t<10?"0"+t:t};return e.getFullYear()+"/"+i(e.getMonth()+1)+"/"+i(e.getDate())+"-"+i(e.getHours())+":"+i(e.getMinutes())},parse:function(t){var e=t.split(/[^0-9]/);return new Date(e[0],e[1]-1,e[2],e[3],e[4],e[5])}};function s(t){return t&&-1==t.search("http")?("/"==t.slice(0,1)&&(t=t.substr(1)),i.baseUrl+t.replace("\\","/")):t}t.exports={formatTime:a,formatLocation:r,dateUtils:o,fixImgUrl:s}}}]);