import * as apis from "../../util/apis.js";
import * as common from '../../util/common.js';
export default {
	namespace: true,
	state: {
		login: false,
		token: '',
		// avatarUrl: '',
		// name: '',
		// mobile: '',
	},
	mutations: {
		login: async function(state, token) {
			if( token ) {
				state.login = true;
				state.token = token;
				common.setAccessToken(token)
				// const res = await apis.getCurrendUser()
				// state.avatarUrl = res.avatar
				// state.userId = res.id
				// state.name = res.name
				// state.mobile = res.mobile
			}
		},
		logout(state) {
			state.login = false;
			state.token = '';
			common.removeAccessToken()
			// state.userName = '';
			// state.avatarUrl = '';
		}
	},

}
