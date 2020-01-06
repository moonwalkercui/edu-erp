import cookie from '@/utils/cookie'
import http from '@/utils/http'
import {find, matches, reject} from 'lodash'
export default {
    initNavTab() {
        var navCookieName = 'live_navs'; // 储存tab的cookie名
        cookie.setJson(navCookieName, [{title: '首 页', path: '/i/dashboard'}]);
    },
    saveNavDate(newObj) {
        var navCookieName = 'live_navs'; // 储存tab的cookie名
        var liveTab = cookie.fetchJson(navCookieName);
        if (liveTab == null) {
            cookie.setJson(navCookieName, [newObj]);
        } else {
            var exist = find(liveTab, newObj);
            // 如果cookie里不存在这个导航则增加newObj.path != "/i/dashboard"
            if (typeof exist == "undefined" && (newObj.path != "/" || newObj.path != "/login")) {
                liveTab.push(newObj);
                cookie.setJson(navCookieName, liveTab);
            }
        }
    },
    removeNavTab(pathName) {
        // console.log(pathName);
        var navCookieName = 'live_navs';
        if (pathName != '/i/dashboard') {
            var liveTab = cookie.fetchJson(navCookieName);
            var newTab = reject(liveTab, matches({'path': pathName}));
            // console.log(newTab);
            cookie.setJson(navCookieName, newTab);
        }
    },
    inArray(needle,array) {
        if(typeof needle=="string"||typeof needle=="number"){
            for(var i in array){
                if(needle === array[i]) return true;
            }
            return false;
        }
    },
    hasPermission(permissionID){
        var pms = cookie.fetchJson('_userInfo').pms;
        if(pms == false) return false;
        if(pms == -1) return true;
        return this.inArray(permissionID,cookie.fetchJson('_userInfo').pms)
    },
    buttonDisabler(that, valName) {
        that[valName] = true;
        setTimeout(() => {
            that[valName] = false;
        }, 3000)
    },
    uploadImg(file,inputName,action,cb) {
        const isAllow = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isAllow) {
            cb({ "status" : 'error' , 'msg' : '上传的图片须是 JPG、PNG 或 GIF 格式' });
            return ;
        }
        if (!isLt2M) {
            cb({ "status" : 'error' , 'msg' : '上传的图片大小不能超过 2MB' });
            return ;
        }

        let formData = new FormData();
        formData.append(inputName, file);
        let config = {
            headers: {'Content-Type': 'multipart/form-data'},
            params: {
                "action": "uploadimage",
                'filename': inputName
            }
        };
        http.post(action , formData, config).then(function (res) {
            cb(res);
            return ;
        });
    },
    uploadExcel(file,inputName,action,cb) {
        const isAllow = file.type == 'application/vnd.ms-excel' || file.type == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isAllow) {
            cb({ "status" : 'error' , 'msg' : '上传文件只能是 Excel 格式!' });
            return ;
        }
        if (!isLt2M) {
            cb({ "status" : 'error' , 'msg' : '上传文件大小不能超过 2MB' });
            return ;
        }
        let formData = new FormData();
        formData.append(inputName, file);
        let config = {
            headers: {'Content-Type': 'multipart/form-data'},
            params: {
                "action": "uploadxls",
                'filename': inputName
            }
        };
        http.post(action , formData, config).then(function (res) {
            cb(res);
            return ;
        });
    },
    /**
   * 对象转url参数
   * @param {*} data
   * @param {*} isPrefix
   */
  queryParams (data, isPrefix = true) {
    let prefix = isPrefix ? '?' : ''
    let _result = []
    for (let key in data) {
      let value = data[key]
      // 去掉为空的参数
      if (['', undefined, null].includes(value)) {
        continue
      }
      if (value.constructor === Array) {
        value.forEach(_value => {
          _result.push(encodeURIComponent(key) + '[]=' + encodeURIComponent(_value))
        })
      } else {
        _result.push(encodeURIComponent(key) + '=' + encodeURIComponent(value))
      }
    }

    return _result.length ? prefix + _result.join('&') : ''
  },
}