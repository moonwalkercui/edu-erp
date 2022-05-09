<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="在线购课" @clickLeft="clickLeft"
			@clickRight="clickRight" />

		<u-tabs count="count" :list="tabs" active-color="#2ac79f" inactive-color="#606266"
			font-size="30" :current="current" @change="change"></u-tabs>
		<courseList ref="courseList" />

	</view>
</template>
<script>
	import courseList from './components/courseList.vue'
	export default {
		components: {
			courseList
		},
		data() {
			return {
				tabs: [{
					name: '推 荐'
				}],
				current: 0,
				selectId: '',
			}
		},
		onLoad() {
			this.getSubjectList()
		},
		onReachBottom() {
			this.$refs.courseList.handleReachBottom()
		},
		onReady() {},
		methods: {
			getSubjectList() {
				this.$http.get('sCenter/shop/subjectlist', {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					var navData = [{
						id: '',
						name: '推 荐'
					}];
					for(const item of res.data) {
						navData.push({
							id: item.id,
							name: item.name,
							count: 0
						});
					}
					this.tabs = navData
					this.change(0)
				})
			},
			change(index) {
				this.current = index;
				this.selectId = this.tabs[index].id;
				this.handelReq()
			},
			handelReq() {
				this.$refs.courseList.handleReload({
					recommend: this.current == 0 ? 1 : "",
					subjectId: this.current == 0 ? '' : this.selectId
				});
			},
			clickRight() {
				this.$refs.courseList.handleReload();
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
