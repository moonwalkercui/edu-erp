// 下拉分页请求
import common from '@/common/common.js'
export default {
	data() {
		return {
			pageData: {
				page: 1,
				lastPage: 1
			}
		}
	},
	onReachBottom() {
		this.handleReachBottom()
	},
	methods: {
		foo: function() {
			console.log('foo')
		},
		handleReachBottom: function() {
			if ( this.pageData.lastPage == this.pageData.page )
				return;
			if (this.pageData.lastPage > this.pageData.page) {
				this.pageData.page ++
				this.handleReq(); // 印用页面需要有一个请求方法
			} else {
				common.showError("没有更多数据")
			}
		}
	}
}
