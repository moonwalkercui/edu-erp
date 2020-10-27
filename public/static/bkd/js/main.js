/* 快捷键处理 */
var myKeyBoard = {
    checkCbFunc: function (cb) {
        if (typeof cb !== 'function') {
            alert('参数须为函数');
            return false;
        } else {
            return true;
        }
    },
    esc: function (cb) {
        myKeyBoard.switchKey('Escape', cb);
    },
    ent: function (cb) {
        myKeyBoard.switchKey('Enter', cb);
    },
    switchKey: function (keyName, cb) {
        if (myKeyBoard.checkCbFunc(cb)) {
            document.onkeydown = function (key) {
                if (key.key === keyName) {
                    cb();
                }
            }
        }
    },
};

$(function () {
    $('.dropdowner').dropdown();
    $('.popuper').popup();
    $('.checkbox').checkbox();

    $('.loader').removeClass('loader');
    // 左侧菜单操作
    // $('.nav-item>a').on('click', function () {
    //
    //     if (!$('.nav').hasClass('nav-mini')) {
    //         if ($(this).next().css('display') == "none") {
    //             $(this).next('ul').slideDown(300);
    //             $(this).parent('li').addClass('nav-show');
    //             // $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
    //             // $(this).parent('li').addClass('nav-show').siblings('li').find('ul').slideUp(300);
    //         } else {
    //             setTimeout(() => {
    //                 $(this).parent('li').removeClass('nav-show');
    //             }, 300);
    //             $(this).next('ul').slideUp(300);
    //
    //             // setTimeout(() => {
    //             //     $(this).parent('li.nav-show').removeClass('nav-show');
    //             // }, 300);
    //         }
    //     }
    // });
    // $('.nav-item').first().addClass('nav-show').find('ul').show();
    $('.nav-item').find('ul').slideDown(300);

    //nav-mini切换
    $('#miniwin').on('click', function () {
        if (!$('.nav').hasClass('nav-mini')) {
            $('.nav-item.nav-show').removeClass('nav-show');
            $('.nav-item').children('ul').removeAttr('style');
            $('.nav').addClass('nav-mini');
            $('.page-left').addClass('mini-left');
            $('.page-right').addClass('mini-right');
        } else {
            $('.nav').removeClass('nav-mini');
            $('.page-left').removeClass('mini-left');
            $('.page-right').removeClass('mini-right');
        }
    });
    $('#fullwin').on('click', function () {
        var elem = document.body;
        if ((document.fullScreenElement !== undefined && document.fullScreenElement === null) || (document.msFullscreenElement !== undefined && document.msFullscreenElement === null) || (document.mozFullScreen !== undefined && !document.mozFullScreen) || (document.webkitIsFullScreen !== undefined && !document.webkitIsFullScreen)) {
            if (elem.requestFullScreen) {
                elem.requestFullScreen();
            } else if (elem.mozRequestFullScreen) {
                elem.mozRequestFullScreen();
            } else if (elem.webkitRequestFullScreen) {
                elem.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
            } else if (elem.msRequestFullscreen) {
                elem.msRequestFullscreen();
            }
        } else {
            if (document.cancelFullScreen) {
                document.cancelFullScreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            }
        }
    });
    // 左侧菜单操作 end
});

function openBigWin(obj, id, iframeHeight, iframeWidth) {
    openWin(obj, id, iframeHeight || '95%', iframeWidth || '95%')
}

function openWin(obj, id, height, width) {
    var title = $(obj).data('title') || '信息窗口';
    var url = $(obj).data('url') || '';
    if (id) url += "?id=" + id;
    height = height || '400';
    width = width || '700';
    popupPage(title, url, width, height)
}

function ajaxReq(obj, id, cb) {
    var url = $(obj).attr('data-url');
    return ajaxRequest(url, {id: id}, function (res) {
        layer.msg(res.msg, {
            time: 1000,
            end: function () {
                if (cb) {
                    cb(res);
                } else {
                    pageReload();
                }
            }
        });
    }, 'POST');
}

function pageBack() {
    window.history.back();
}

function pageReload() {
    window.location.reload();
}

function popupPage(title, url, w, h, close_cb, is_parent) {
    h = !isNaN(h) ? h + 'px' : h;
    w = !isNaN(w) ? w + 'px' : w;
    if (title == null || title == '') {
        title = false;
    }
    if (url == null || url == '') {
        url = "404.html";
    }
    if (w == null || w == '') {
        w = ($(window).width() * 0.8);
    }
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    }
    var opt = {
        type: 2,
        area: [w, h],
        fix: false,
        shadeClose: false,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        end: function (i) {
            close_cb && close_cb(i)
        }
    };
    var index;
    if (is_parent == true) {
        index = parent.layer.open(opt);
    } else {
        index = layer.open(opt);
    }
    myKeyBoard.esc(function () {
        layer.close(index);
    });
}

// // 操作按钮点击
// function handleClick(obj, id, iframeHeight, iframeWidth) {
//     var title = $(obj).attr('data-title'),
//         url = $(obj).attr('data-url');
//     if (id) url = url + '?id=' + id;
//     popupPage(title, url, iframeWidth || 800, iframeHeight || 550)
// }

// 点击跳转
function redirect(obj, id) {
    var title = $(obj).attr('data-title'),
        url = $(obj).attr('data-url');
    location.href = url + '?id=' + id;
}

// 点击刷新
function refresh() {
    location.reload();
}

// 点击输入后操作
function handlePrompt(obj, id) {
    var title = $(obj).attr('data-title'),
        url = $(obj).attr('data-url');
    layer.prompt({
        title: title || '请输入',
    }, function (value, index, elem) {
        ajaxRequest(url, {id: id, value: value}, '', 'POST');
        layer.close(index);
        location.reload();
    });
}

// 点击输入后操作
function handleConfirm(obj, id) {
    var title = $(obj).attr('data-title'),
        url = $(obj).attr('data-url');

    layer.msg(title + "?", {
        time: 0 //不自动关闭
        , btn: ['确认', '取消']
        , yes: function (index) {
            layer.close(index);
            ajaxRequest(url, {id: id}, '', 'POST');
            location.reload();
        }
    });
}

// 单选并关闭层，父级会多一个chooseId变量
function chooseOne(obj, id) {
    parent.window.chooseId = id
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);//关闭当前页
}
// 单个删除
function delOne(obj, id) {
    var t = $(obj).attr('data-title') || '删除';
    var url = $(obj).attr('data-url');
    if (url) {
        layer.confirm('确认' + t + '?', {title: '删除确认'}, function (index) {
            ajaxRequest(url, {id: id}, function (res) {
                // $(obj).parents("tr").remove();
                if (res != 1) layer.msg('删除失败');
                else location.reload();
            });
            layer.close(index);
        });
    } else {
        console.log('缺少url');
    }
}
// 批量删除
function delMulti(obj, ele_class) {
    var t = $(obj).attr('data-title') || '删除';
    var url = $(obj).attr('data-url');
    if (url) {
        layer.confirm('确认' + t + '?', {title: '删除确认'}, function (index) {
            var ids = [];
            $('.'+ele_class).each(function(){
                if($(this).prop('checked')){
                    ids.push($(this).val())
                }
            });
            if(ids.length == 0) {
                layer.msg('请先选择');
                return;
            }
            ajaxRequest(url, {ids: ids}, function (res) {
                if (res != 1) layer.msg('删除失败');
                else location.reload();
            });
            layer.close(index);
        });
    } else {
        console.log('缺少url');
    }
}
function delAll(argument) {
    var data = tableCheck.getData();
    layer.confirm('确认要删除吗？' + data, {title: 'INFO'}, function (index) {
        //捉到所有被选中的，发异步进行删除
        layer.msg('成功', {icon: 1});
        $(".layui-form-checked").not('.header').parents('tr').remove();
    });
}

function ajaxRequest(url, param, cb, type) {
    var param = param || {};
    var type = type || 'GET';
    var loadIndex = layer.load(1);
    $.ajax({
        type: type,
        url: url,
        data: param,
        success: function (data) {
            //批量显示没有返回1的，就是没成功
            if (data == 1 || data.code == 1) {
                if (cb) {
                    cb(data);
                } else {
                    layer.msg('成功');
                }
            } else {
                layer.msg(data.msg || '操作出错');
            }
            layer.closeAll('loading');
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg('操作出错');
        },
        complete: function() {
            layer.close(loadIndex);
        }
    });
}

function previewImage(obj) {
    var src = $(obj).attr('src');
    layer.open({
        type: 1,
        title: false,
        closeBtn: true,
        area: '700px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        content: `<img src="${src}" style="width:100%"/>`
    });
}

// 表单提交
function doFormSubmit(form_selecter, action, method) {
    ajaxRequest(action, $(form_selecter).serialize(), function (res) {
        layer.msg(res.msg, {icon: 1, time: 800}, function (res) {
            var index = parent.layer.getFrameIndex(window.name);
            layer.close(index);
            parent.location.reload();
        });
    }, method || 'POST');
}



// 编辑器初始化
function editorInit(selecter) {
    return tinymce.init({
        language: 'zh_CN',
        selector: selecter,
        height: 150,
        menubar: false,
        statusbar: false,
        plugins: "image media preview fullscreen",
        toolbar: 'image media preview fullscreen',
        toolbar_items_size: 'small',
        images_upload_url: '/api/service/editorUp.html',
        // setup: function (ed) {
        //     ed.on("change", function () {
        //         textContentOnChangeHandler && textContentOnChangeHandler(ed);
        //     })
        // },
        // images_upload_base_path: ApiBaseUrl,
        // file_picker_types: 'file image media',
        file_picker_callback: function (callback, value, meta) {
            //文件分类
            var filetype = '.pdf, .txt, .zip, .rar, .7z, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .mp3, .mp4';
            //后端接收上传文件的地址
            var upurl = '/demo/upfile.php';
            //为不同插件指定文件类型及后端地址
            switch (meta.filetype) {
                case 'image':
                    filetype = '.jpg, .jpeg, .png, .gif';
                    upurl = '/api/service/editorUpImg';
                    break;
                case 'media':
                    filetype = '.mp3, .mp4';
                    upurl = '/api/service/editorUpMedia';
                    break;
                // case 'file':
                default:
            }
            //模拟出一个input用于添加本地文件
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', filetype);
            input.click();
            input.onchange = function () {
                var file = this.files[0];

                var xhr, formData;
                console.log(file.name);
                xhr = new XMLHttpRequest();
                xhr.withCredentials = false;
                xhr.open('POST', upurl);
                xhr.onload = function () {
                    var json;
                    if (xhr.status != 200) {
                        failure('HTTP Error: ' + xhr.status);
                        return;
                    }
                    json = JSON.parse(xhr.responseText);
                    if (!json || typeof json.location != 'string') {
                        failure('Invalid JSON: ' + xhr.responseText);
                        return;
                    }
                    callback(json.location);
                };
                formData = new FormData();
                formData.append('file', file, file.name);
                xhr.send(formData);
            }
        }
    });
}

function closeCurrentWin() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
