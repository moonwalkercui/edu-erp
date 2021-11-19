import Vue from 'vue'
import App from './App'

import store from 'store/index.js'
Vue.prototype.$store = store

import uniNavBar from '@/components/uni-nav-bar/uni-nav-bar.vue'
Vue.component('uni-nav-bar', uniNavBar)

Vue.config.productionTip = false

App.mpType = 'app'

// 引入全局uView
import uView from 'uview-ui'
Vue.use(uView);

import * as apis from './util/apis.js'
import * as http from './util/http.js'
import * as db from './util/db.js'
import * as common from './util/common.js'
Vue.prototype.$apis = apis
Vue.prototype.$http = http
Vue.prototype.$db = db
Vue.prototype.$common = common

const app = new Vue({
	store,
	...App
})
app.$mount()
