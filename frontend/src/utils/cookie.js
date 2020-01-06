export default {
    setJson: function(name,json_obj,expiredays) {
        this.setCookie(name,JSON.stringify(json_obj), expiredays);
    },
    fetchJson: function(name) {
        if(this.getCookie(name) == null) return null;
        return JSON.parse(decodeURI(this.getCookie(name)));
    },
    // 这个不会返回反编码后的
    getCookie: function(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
      arr = document.cookie.match(reg);
        if (arr)
            return (arr[2]);
        else
            return null;
    },
    //设置cookie,增加到vue实例方便全局调用 过期天数,储存结果被decodeURI编码
    setCookie: function(c_name, value, expiredays) {
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + expiredays);
        document.cookie = c_name + "=" + decodeURI(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
    },
    //删除cookie
    delCookie: function(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = this.getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
