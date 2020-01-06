import axios from "axios";
import cookie from "@/utils/cookie";
import { makeBaseUrl } from "@/utils/global"; 
import qs from "qs";
import { Message } from "element-ui";

// axios 配置
const http = axios.create({
  timeout: 8000, // 请求超时时间
  transformRequest: [
    function(data) {
      if (typeof data != "undefined" && data.constructor.name != "FormData") { // 另外一种方式 if(config.method=="post")
        data = qs.stringify(data); // 这个方法get方式无效。这里可以在发送请求之前对请求数据做处理，比如form-data格式化等，这里可以使用开头引入的Qs
      }
      return data;
    }
  ]
});
// http request 拦截器
http.interceptors.request.use(
  config => {
    const token = cookie.getCookie("_token"); //获取Cookie
    if (typeof config.headers["Content-Type"] == "undefined") {
      config.headers = {
        "Content-Type": "application/x-www-form-urlencoded", //设置跨域头部
      };
    }
    if (token) {
      config.params = Object.assign({}, config.params, { token: token }); //后台接收的参数
    }
    return config;
  },
  err => {
    Message.error("请求错误 " + err);
    return Promise.reject(err);
  }
);

// 响应拦截器
http.interceptors.response.use(
  response => {
    if (typeof response.data == "undefined") {
      Message.error({ message: "数据响应错误" });
      return response;
    }
    if (response.data.status == "error") {
      Message.error(response.data.msg);
    }
    return response.data;
  },
  err => {
    if (err && err.response && err.response.data.msg) {
      if (err.response.data.msg == "Unauthenticated.") {
        window.location.href = "/";
      } else {
        Message.error(err.response.data.msg);
      }
    } else {
      Message.error("错误: " + err);
    }
    return Promise.reject(err);
  }
);
export default http;
export function fetch(url, params = {}) {
  return new Promise((resolve, reject) => {
    http
      .get(makeBaseUrl() + url, { params: params })
      .then(response => {
        resolve(response);
      })
      .catch(err => {
        reject(err);
      });
  });
}
export function post(url, data = {}) {
  return new Promise((resolve, reject) => {
    http
      .post(makeBaseUrl() + url, data)
      .then(response => {
        resolve(response);
      })
      .catch(err => {
        reject(err);
      });
  });
}
export function patch(url, data = {}) {
  return new Promise((resolve, reject) => {
    http
      .patch(makeBaseUrl() + url, data)
      .then(response => {
        resolve(response);
      })
      .catch(err => {
        reject(err);
      });
  });
}
export function put(url, data = {}) {
  return new Promise((resolve, reject) => {
    http
      .put(makeBaseUrl() + url, data)
      .then(response => {
        resolve(response);
      })
      .catch(err => {
        reject(err);
      });
  });
}
