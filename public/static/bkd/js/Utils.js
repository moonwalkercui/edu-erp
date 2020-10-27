var JsUtils = {
    unique: function (arr) {
        if (Array.hasOwnProperty('from')) {
            return Array.from(new Set(arr));
        } else {
            var n = {},
                r = [];
            for (var i = 0; i < arr.length; i++) {
                if (!n[arr[i]]) {
                    n[arr[i]] = true;
                    r.push(arr[i]);
                }
            }
            return r;
        }
    },
    inArray: function (value, arr) {
        for (var i = 0; i < arr.length; i++) {
            if (value === arr[i]) {
                return true
            }
        }
        return false
    },
    // 数组中最大值
    maxInArr: function (ary) {
        var max = 0;
        for (var i = 0; i < ary.length; i++) {
            if (ary[i] > max) {
                max = ary[i];
            }
        }
        return max;
    },
    // 获取当前路径的参数
    getUrlQueryStr: function () {
        var qs = window.location.search.substr(1), // 获取url中"?"符后的字串
            args = {}, // 保存参数数据的对象
            items = qs.length ? qs.split("&") : [], // 取得每一个参数项,
            item = null,
            len = items.length;

        for (var i = 0; i < len; i++) {
            item = items[i].split("=");
            var name = decodeURIComponent(item[0]),
                value = decodeURIComponent(item[1]);
            if (name) {
                args[name] = value;
            }
        }
        return args;
    }
};
