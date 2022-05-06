<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="在线购课" @clickLeft="clickLeft"
			@clickRight="clickRight" />

		<u-tabs name="cate_name" count="cate_count" :list="list" active-color="#2ac79f" inactive-color="#606266"
			font-size="30" :current="current" @change="change"></u-tabs>
		<courseList/>
		
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
				list: [{
					name: '推 荐'
				}, {
					name: '语 文'
				}, {
					name: '数 学',
				}, {
					name: '英 语',
				}, {
					name: '物 理',
				}, {
					name: '生 物',
				}],
				current: 0
			}
		},
		onLoad() {
		},
		onReady() {},
		methods: {
			change(index) {
				this.current = index;
			},
			handleReq() {

			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
