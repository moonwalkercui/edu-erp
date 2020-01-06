import cookie from '@/utils/cookie'
import http,{fetch, post} from '@/utils/http'
import util from '@/utils/functions'
import {ServiceUrl,makeBaseUrl}  from "@/utils/global";

export default{
  // install(Vue,options)
  install(Vue)
  {
    Vue.prototype.$cookie = cookie;
    Vue.prototype.$util = util;
    Vue.prototype.serviceUrl = ServiceUrl;
    Vue.prototype.userInfo = {};
    Vue.prototype.http = http;
    Vue.prototype.$http = {fetch, post};
    Vue.prototype.$exportExcel = (url, param = {}) => {
      const token = cookie.getCookie("_token"); //获取Cookie
      window.open(makeBaseUrl() + url + util.queryParams({...param, export: 'excel', token: token}) , '_blank');
    };

    // 格式化时间 自动转变 今天 昨天 明天 (格式里须有d)
    // 用法 new Date(your-date-string, 'yyyy-MM-dd hh:mm:ss DD')  DD表示“周一” D表是“一”
    Date.prototype.format = function(format, humanized = true)
    {
      if(format == '') return '';
      var o = {
        "M+" : this.getMonth()+1, // month
        "d+" : this.getDate(),    // day
        "h+" : this.getHours(),   // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        // "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
        // "S" : this.getMilliseconds(), // millisecond
      },
        today = new Date(),
        yesterday = new Date(),
        tomorrow = new Date(),
        display = '';
        if (humanized) {
          if (today.toDateString() === this.toDateString()) {
            display = '今天'
          } else if ( new Date(yesterday.setDate(yesterday.getDate() - 1)).toDateString() === this.toDateString()) {
            display = '昨天'
          } else if ( new Date(tomorrow.setDate(tomorrow.getDate() + 1)).toDateString() === this.toDateString()) {
            display = '明天'
          }
        }
      if(/(D+)/.test(format)){
        var week = ['日' , '一' , '二' , '三' , '四' , '五' , '六'];
        var day = week[this.getDay()];
        format = format.replace(RegExp.$1, RegExp.$1.length==1 ? day : "周" + day);
      }
      if(/(y+)/.test(format)) {
        // if (display || new Date().getFullYear() === today.getFullYear()) // 隐藏 本年字符
        //   format = format.replace(new RegExp('(y+)[/-]?'), '')
        // else
          format = format.replace(RegExp.$1, display ? '' : (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      }
      for(var k in o)
        if(new RegExp("("+ k +")").test(format)) {
          if (display && k === "M+") {
            if (display)
              format = format.replace(new RegExp('(M+)[/-]?'), '');
            else
              format = format.replace(RegExp.$1, '');
          }
          else if (display && k === "d+")
            format = format.replace(/([-/]?d+)/, display);
          else
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
      return format;
    }
  }
}
