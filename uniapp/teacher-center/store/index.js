import Vue from 'vue'
import Vuex from 'vuex'

import curUser from '@/store/modules/curUser'

Vue.use(Vuex)

// 获取模块里的state: this.$store.state.curUser.token
const store = new Vuex.Store({
	modules: {
		curUser
	}
})
export default store
