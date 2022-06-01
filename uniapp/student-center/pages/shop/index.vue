<template>
	<view>
		<categoryTabs @change="change"/>
		<courseList ref="courseList" />
	</view>
</template>
<script>
	import courseList from './components/courseList.vue'
	import categoryTabs from './components/categoryTabs.vue'
	export default {
		components: {
			courseList, categoryTabs
		},
		data() {
			return {
				current: 0,
				selectId: '',
			}
		},
		onLoad() {
		},
		onReachBottom() {
			this.$refs.courseList.handleReachBottom()
		},
		onReady() {},
		methods: {
			handelReq() {
				this.$refs.courseList.handleReload({
					recommend: this.current == 0 ? 1 : "",
					subjectId: this.current == 0 ? '' : this.selectId
				});
			},
			change(curIndex, navId) {
				this.current = curIndex
				this.selectId = navId
				this.handelReq()
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
