import * as http from './http.js'

export function getCurrendUser() {
	return new Promise((resove, reject) => {
		http.get('common/auth/currentUser', {}, res => {
			resove(res)
		})
	})
}
