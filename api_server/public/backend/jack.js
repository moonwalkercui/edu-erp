// /*提示窗系列*/
// function showSuccess( msg , title ){
//   if(typeof title == 'undefined') title = '成功';
//   showMsg( msg , title , 'green' );
// }
// function showError(msg , title){
//   if(typeof title == 'undefined') title = '错误';
//   showMsg( msg , title , 'red' );
// }
// function showNotice(msg , title){
//   if(typeof title == 'undefined') title = '提示';
//   showMsg( msg , title , 'yellow' );
// }
// function showMsg( msg , title , color ) {
//   var random = Math.ceil(Math.random()*100);
//   var  html = '<div class="alert alert-' + color + '" id = "msg_tmp_' + random + '">' +
//       '<span class="close rotate-hover"></span>' +
//       '<strong>' + title + '：</strong>' +
//       '<span>' + msg + '</span>' +
//       '</div>';
//   //   layer.open({
//   //       type: 1,
//   //       title: false,
//   //       closeBtn: 0,
//   //       content: html
//   //   });
//     $('#msg-box').append(html);
//
//     setTimeout( function(){ $('#msg_tmp_' + random).remove(); } , 4000 );
// }

/* 提示框系列 */
/*
 * 先提示后请求
 * msg：提示框信息
 * url：确认后的跳转链接
 * color: 请求框颜色 默认red
 * */
function alertRequest(msg,url)
{
    layer.alert(msg, {
        icon: 0,
        yes: function () {
            window.location.href = url;
        }
    });
    //
    // if(typeof color == 'undefined'){
    //     color = 'red';
    // }
    // showAlertHtml(msg,function(){
    //     window.location.href = url;
    // },color);
}
/*
 * 带ajax请求的提示框
 * msg：提示框信息
 * url：如果url已经定义，则发起ajx请求 默认请求方式为post
 * config: 配置参数，默认格式如：
 * {
 *   '_type':'get', //请求类型
 *   '_redirect':'url', // 请求成功后跳转地址，设置了这个则不执行回调fn
 *   '_fn':'fn', // 请求成功后执行的回调函数，参数默认为ajax返回的data
 *   'a':1, // 提交ajax的数据
 * }
 * color: 请求框颜色 默认red
 * */
// function alertAjaxRequest(msg,url,config,color) {
//
//     var method = 'post' ,fn = '',redirect = '';
//
//     if(typeof config != 'undefined'){
//         if(typeof config._type != 'undefined'){
//             method = config._type;
//             delete (config._type);
//         }
//         if(typeof config._fn != 'undefined'){
//             fn = config._fn;
//             delete (config._fn);
//         }
//         if(typeof config._redirect != 'undefined'){
//             redirect = config._redirect;
//             delete (config._redirect);
//         }
//     }
//     if(typeof color == 'undefined'){
//         color = 'red';
//     }
//
//     showAlertHtml(msg,function(){
//         $.ajax({
//             type: method,
//             url: url,
//             data: config,
//             success: function(data){
//                 if(redirect != ''){
//                     window.location.href = redirect;
//                     return;
//                 }
//                 // fn && fun();
//                 if(fn != ''){
//                     fn.call(this,data);
//                     return;
//                 }
//             },
//             error: function () {
//                 alert('请求出错');
//             },
//             always: function () {
//                 alert('always');
//             },
//             // statusCode: {404: function() {
//             //     alert('page not found');
//             // }},
//         });
//     },color);
// }
// function showAlertHtml(msg,callback,color)
// {
//     if(typeof color=='undefined') color = 'red';
//     var html = '<div class="alert alert-' + color + '" style="position: absolute; top: 50%; left: 45%; width: 230px;">' +
//         '<span class="close rotate-hover"></span>' +
//         '<strong>提示:</strong>' +
//         '<p>' + msg + '</p>' +
//         '<button class="button bg do-cancel" style="margin-left: 36px;">取消</button> ' +
//         '<button class="button bg-' + color + ' do-confirm"> 确认</button>' +
//         '</div>';
//     $('body').append(html);
//     $(".alert .close,.alert .do-cancel").each(
//         function(){
//             $(this).click( function(){ $(this).closest(".alert").fadeOut(200,function () {
//                 $(this).remove();
//             })});
//         }
//     );
//     $(".alert .do-confirm").each(function(){
//         $(this).click( function(){
//             callback.call(this);
//         });
//     });
// }