//    layer.config({
//        extend: 'jack.css', //加载您的扩展样式
//        skin: 'layer-ext-jack'
//    });
function openIframe(url,obj){
    var title = $(obj).attr('value') || $(obj).text();
    layer.open({
        type: 2,
        area: ['700px', '50%'],
        fixed: false, //不固定
        maxmin: true,
        shadeClose : false,
        title: title,
        content: url,
        cancel: function(index, layero){
            layer.close(index);
            location.reload();
        }
    });
}
// 获取cookie
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return (arr[2]);
    } else {
        return null;
    }
}
// 设置cookie
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + decodeURI(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}
//删除cookie
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}