;(function ($) {
    $.fn.extend({
        jkUploader: function () {
            $(this).after('<form style="display:none" class="jk-uploader-form">' +
                '<input type="file" name="file" value="" class="jk-uploader-input"/>' +
                '</form>');
            var dom_form = $(this).next('.jk-uploader-form');
            var dom_input = dom_form.children('input.jk-uploader-input');
            var cb = $(this).data('cb');
            var url = $(this).data('act');
            // 图片上传
            $(this).click(function () {
                dom_input.trigger('click');
            });

            function jkUploadCb(complete) {
                // console.log(complete)
                $(this).text(complete);
            }

            function uploadedCallBack(data) {
                cb && window[cb](data);
                layer.closeAll('loading');
            }

            var jkUploading = false;
            // 当表单文件有变化时执行提交动作
            dom_input.change(function (e) {
                layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                if ($(this).val()) {
                    //首先封装一个方法 传入一个监听函数 返回一个绑定了监听函数的XMLHttpRequest对象
                    var xhrOnProgress = function (fun) {
                        xhrOnProgress.onprogress = fun; //绑定监听
                        //使用闭包实现监听绑
                        return function () {
                            //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
                            var xhr = $.ajaxSettings.xhr();
                            //判断监听函数是否为函数
                            if (typeof xhrOnProgress.onprogress !== 'function')
                                return xhr;
                            //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
                            if (xhrOnProgress.onprogress && xhr.upload) {
                                xhr.upload.onprogress = xhrOnProgress.onprogress;
                            }
                            return xhr;
                        }
                    }

                    // let file = document.getElementById(input_id).files[0];
                    let file = e.currentTarget.files[0];
                    // 前段判断文件大小和格式
                    const isAllow = (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif');
                    const isLt2M = file.size / 1024 / 1024 < 2;
                    if (!isAllow) {
                        layer.msg('上传的图片格式错误');
                        return;
                    }
                    if (!isLt2M) {
                        layer.msg('上传的图片大小不能超过 2MB');
                        return;
                    }
                    var data = new FormData(dom_form[0]);  //要加【0】
                    // console.log('dom_form',data);
                    if (jkUploading) {
                        layer.alert("文件正在上传中，请稍候");
                        return false;
                    }
                    $.ajax({
                        type: 'POST',
                        url: url,    //当前路径
                        data: data,
                        dataType: 'json',
                        processData: false,    //序列化，no
                        contentType: false,    //不设置内容类型
                        beforeSend: function () {
                            jkUploading = true;
                        },
                        //进度条要调用原生xhr
                        xhr: xhrOnProgress(function (evt) {
                            var percent = Math.floor(evt.loaded / evt.total * 100);//计算百分比
                            // var txt = evt.loaded/1024 + 'K/'+ evt.total/1024 + 'K';
                            var complete = '上传中 ( ' + percent + '% )';
                            jkUploadCb(complete)
                        }),
                        success: function (data) {
                            if (data.code == 1) {
                                //成功后关闭修改页
                                // setTimeout(function(){
                                //   var index = parent.layer.getFrameIndex(window.name);  //先得到当前iframe的索引
                                //   parent.layer.close(index);   //在执行关闭
                                // } ,2000);
                                //还有刷新下iframe的界面
                                // location.reload();
                            } else {
                                layer.msg(data.msg);
                            }
                            uploadedCallBack(data);
                            jkUploading = false;
                        },
                        error: function (data) {
                            console.log('服务异常，请稍后重试' + data);
                            layer.open({
                                content: data.responseText
                            })
                            layer.closeAll('loading');
                        }
                    });
                }
            });
        }
    });
})(jQuery);