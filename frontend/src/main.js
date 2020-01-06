// import 'normalize.css' // 给浏览器统一样式
import Vue from 'vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import App from './App'
import Element from 'element-ui'
import routes from './routes'
import store from './vuex/store'
import common from './utils/common'
import cookie from './utils/cookie'
import util from './utils/functions'
import './assets/font-awesome/css/font-awesome.min.css'
import './assets/element-jack.scss'
import 'element-ui/lib/theme-chalk/display.css'
import vuescroll from 'vuescroll'
import VueClipboard from 'vue-clipboard2'
import Helper from '@/components/Help'
import ReqButton from '@/components/ReqButton'
// import VueSocketio from 'vue-socket.io';
// Vue.use(VueSocketio, 'ws://127.0.0.1:7272');

// import 'element-ui/lib/theme-chalk/index.css' // 这个采用自定义elment-jack了所以不用
// import 'babel-polyfill' // 已经卸载 以后再装 自动匹配css的浏览器版本
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(Element)
Vue.use(VueClipboard)
Vue.use(common)
const router = new VueRouter({
//   mode: 'history', // 这个可以去掉路径里的#（后端需要配置），但是如果使用nwjs客户端里不能用这种格式。
  routes
});
// 滚动条组件
Vue.use(vuescroll)
Vue.component("helper", Helper);
Vue.component("reqButton", ReqButton);
// 路由变化时的逻辑
// router.push({ name: '老师列表'})
router.beforeEach((to, from, next) => {
    // console.log(to);
    // store.commit('addTab',{
    //   title : to.name,
    //   path : to.path
    // });

    if (to.meta.openTab == true) {   // 判断是否添加到动态导航里

        var url, toPath = to.path.split("/");
        if (toPath.length <= 3) {
            url = to.path;
        } else {
            toPath.pop();
            url = toPath.join("/");
        }
        // console.log(url);
        util.saveNavDate({
            title: to.name,
            path: url
        });
    }
    store.commit('activeTab', url); // 激活tab
    store.commit('refreshTab');
    // 判断权限
    if (to.matched.some(record => record.meta.requireAuth)) {
        const token = cookie.getCookie('_token');
        if (token ) {
            next();
        } else {
            next({
                path: '/',
                query: {redirect: to.fullPath}
            })
        }
    } else {
        next();
    }
});
// 过滤器
Vue.filter('formatDate', function (value,formatStr) {
    if (!value) return ''
    var str;
    if(formatStr) str = formatStr;
    else str = "yyyy-MM-dd";
    return new Date(value).format(str)
})
Vue.filter('formatTime', function (value) {
    return value ? value.substring(0,5) : ''
})
Vue.config.productionTip = false
new Vue({
    //el: '#app',
    router,
    store,
    //template: '<App/>',
    // components: {App},
    render: h => h(App)
}).$mount('#app');