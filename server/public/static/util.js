var Util = {
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
    }
};
