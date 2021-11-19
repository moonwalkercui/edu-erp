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
			value: null,
			listStore: [],
			list: [],
			scrollHeight: 0,
			pageData: {
				page: 1,
				pageSize: 30,
				keyword: ''
			},
			pageCount: 1,
		}
	},
	watch: {
		visible(v) {
			if (v) {
				this.list = []
				this.pageData.page = 1
				this.getList()
			}
		},
		'pageData.keyword': function(v) {
			this.list = []
			this.pageData.page = 1
			this.getList()
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
	created() {
		let self = this;
		uni.getSystemInfo({
			success: function(res) {
				self.scrollHeight = res.windowHeight - 130
				// console.log(res.screenHeight); //屏幕高度  注意这里获得的高度宽度都是px 需要转换rpx
				// console.log(res.windowWidth); //可使用窗口宽度
				// console.log(res.windowHeight); //可使用窗口高度
				// console.log(res.screenWidth); //屏幕宽度
				// self.emulator_Height = (res.screenHeight * (750 / res.windowWidth)) //将px 转换rpx
				// console.log("rpx*********", self.emulator_Height)
			}
		});
	},
	methods: {
		// 需要重写
		// getList() {},
		onChange(val) {
			this.value = val
			// console.log(val)
		},
		toSubmit() {
			if (this.value == null) {
				showMsg("请先选择!");
				return;
			}
			var res = null;
			if (this.value instanceof Array) {
				res = [];
				for (var item of this.list) {
					for (var id of this.value) {
						if (item.id == id) {
							res.push(item);
						}
					}

				}
			} else {
				for (var item of this.list) {
					if (item.id == this.value) {
						res = item;
						break;
					}
				}
				if (res == null) {
					showMsg("请先选择!");
					return;
				}
			
			}

			this.$emit("onSubmit", res)
			this.show = false
		},
		
		tobottom: function(e) {
			if(this.pageCount> this.pageData.page) {
				this.pageData.page++
				this.getList();
			}
		},
	}
}
