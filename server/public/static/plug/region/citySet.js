function SelCity(obj, e) {
    var ths = obj;
    var inputName = $(obj).attr('name') || 'my';
    var dal = '<div class="_citys"><span title="关闭" id="cColse" >×</span><div id="_citysheng" class="_citys0">请选择省份</div><div id="_citys0" class="_citys1"></div><div style="display:none" id="_citys1" class="_citys1"></div><div style="display:none" id="_citys2" class="_citys1"></div></div>';
    Iput.show({
        id: ths,
        event: e,
        content: dal,
        width: "470"
    });
    $("#cColse").click(function() {
        Iput.colse()
    });
    var tb_province = [];
    var b = province;
    for (var i = 0,
    len = b.length; i < len; i++) {
        tb_province.push('<a data-id="' + b[i]['id'] + '" data-name="' + b[i]['name'] + '" title="' + b[i]['name'] + '">' + b[i]['name'] + '</a>')
    }
    $("#_citys0").append(tb_province.join(""));
    $("#_citys0 a").click(function() {
        var g = getCity($(this));
        $("#_citys1 a").remove();
        $("#_citys1").append(g);
        $("._citys1").hide();
        $("._citys1:eq(1)").show();
        $("#_citys0 a,#_citys1 a,#_citys2 a").removeClass("AreaS");
        $(this).addClass("AreaS");
        var lev = $(this).data("name");
        ths.value = $(this).data("name");
        if (document.getElementById("r_province") == null) {
            var r_provinces = $('<input>', {
                type: 'hidden',
                name: inputName + "_province",
                "data-id": $(this).data("id"),
                id: "r_province",
                val: lev
            });
            $(ths).after(r_provinces)
        } else {
            $("#r_province").val(lev);
            $("#r_province").attr("data-id", $(this).data("id"))
        }
        $("#_citys1 a").click(function() {
            $("#_citys1 a,#_citys2 a").removeClass("AreaS");
            $(this).addClass("AreaS");
            var lev = $(this).data("name");
            if (document.getElementById("r_city") == null) {
                var r_provinces = $('<input>', {
                    type: 'hidden',
                    name: inputName + "_city",
                    "data-id": $(this).data("id"),
                    id: "r_city",
                    val: lev
                });
                $(ths).after(r_provinces)
            } else {
                $("#r_city").attr("data-id", $(this).data("id"));
                $("#r_city").val(lev)
            }
            var bc = $("#r_province").val();
            ths.value = bc + "/" + $(this).data("name");
            var ar = getArea($(this));
            $("#_citys2 a").remove();
            if (ar == '') Iput.colse();
            $("#_citys2").append(ar);
            $("._citys1").hide();
            $("._citys1:eq(2)").show();
            $("#_citys2 a").click(function() {
                $("#_citys2 a").removeClass("AreaS");
                $(this).addClass("AreaS");
                var lev = $(this).data("name");
                if (document.getElementById("r_area") == null) {
                    var r_provinces = $('<input>', {
                        type: 'hidden',
                        name: inputName + "_area",
                        "data-id": $(this).data("id"),
                        id: "r_area",
                        val: lev
                    });
                    $(ths).after(r_provinces)
                } else {
                    $("#r_area").val(lev);
                    $("#r_area").attr("data-id", $(this).data("id"))
                }
                var bc = $("#r_province").val();
                var bp = $("#r_city").val();
                ths.value = bc + "/" + bp + "/" + $(this).data("name");
                Iput.colse()
            })
        })
    })
}
function getCity(obj) {
    var c = obj.data('id');
    var e = province;
    var f = [];
    var g = '';
    for (var i = 0; i < e.length; i++) {
        if (e[i]['id'] == parseInt(c)) {
            f = e[i]['son'];
            break
        }
    }
    for (var j = 0; j < f.length; j++) {
        g += '<a data-id="' + f[j]['id'] + '" data-name="' + f[j]['name'] + '" title="' + f[j]['name'] + '">' + f[j]['name'] + '</a>'
    }
    $("#_citysheng").html('请选择城市');
    return g
}
function getArea(obj) {
    var c = obj.data('id');
    var e = province;
    var f = [];
    var g = '';
    for (var i = 0; i < e.length; i++) {
        for (var j = 0; j < e[i]['son'].length; j++) {
            if (e[i]['son'][j]['id'] == parseInt(c) && e[i]['son'][j]['sec']) {
                f = e[i]['son'][j]['sec'];
                break
            }
        }
    }
    if (f.length) {
        for (var k = 0; k < f.length; k++) {
            g += '<a data-id="' + f[k]['id'] + '" data-name="' + f[k]['name'] + '" title="' + f[k]['name'] + '">' + f[k]['name'] + '</a>'
        }
    }
    $("#_citysheng").html('请选择区县');
    return g
}