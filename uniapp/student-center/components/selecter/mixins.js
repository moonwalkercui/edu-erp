import {
	showMsg
} from "../../util/common.js"
export default {
	props: {
		visible: {
			type: Boolean,
			default: false
		},
		limit: {
			type: Number,
			default: 1
		},
		btnText: {
			default: "选好了"
		}
	},
	data() {
		return {
			keyword: '',
			value: '',
			listStore: [],
			list: [],
		}
	},
	watch: {
		visible(v) {
			if (v) this.getList()
		},
		keyword(v) {
			if (v == '') {
				this.list = [...this.listStore]
			} else {
				let newList = this.list.filter(item => {
					return item.name && item.name.indexOf(v) !== -1
				})
				this.list = newList
			}
		}
	},
	computed: {
		show: {
			get() {
				return this.visible
			},
			set(v) {
				this.$emit("update:visible", v)
			}
		}
	},
	onShow() {

	},
	methods: {
		// 需要重写
		// getList() {},
		onChange(val) {
			this.value = val
		},
		toSubmit() {
			if (this.value.length == 0) {
				showMsg("请先选择!");
				return;
			}
			var res;
			if (this.value instanceof Array) {
				res = this.value.join(',')
			} else {
				res = this.value
			}
			console.log(this.list)
			this.$emit("onSubmit", res)
			this.show = false
		},
	}
}
