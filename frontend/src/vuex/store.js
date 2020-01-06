import Vue from 'vue'
import Vuex from 'vuex'
// import _ from 'lodash'
import cookie from '@/utils/cookie'
//import * as actions from './actions'
//import * as getters from './getters'
Vue.use(Vuex);
// import VueRouter from 'vue-router'
// const router = new VueRouter({
// })

// 应用初始状态
const state = {
  code: 1,
  tabActive : '/i/dashboard',
  tabList: [],
  apiPrefix: '',
};

// 定义所需的 mutations
const mutations = {
  // addTab(state,param) {
  //   if((typeof _.find(state.tabList, param)) == 'undefined'){
  //     state.tabList.push(param);
  //   }
  //   state.tabActive = param.path;
  // },
  // removeTab(state,targetName){
  //   if(targetName != '/i/dashboard') {
  //     state.tabList = _.reject(state.tabList, _.matches({ 'path':targetName }));
  //   }
  // },
  refreshTab(state) {
    state.tabList = cookie.fetchJson('live_navs');
  },
  activeTab(state,tabPath) {
    state.tabActive = tabPath;
  },
  setApiPrefix(state,value) {
    state.apiPrefix = value;
  }

};

// const actions ={
//   addTab(context,param){
//     context.commit('addTab',param)
//     return param;
//   }
// }

// 创建 store 实例
export default new Vuex.Store({
  // actions,
  //getters,
  state,
  mutations
});
