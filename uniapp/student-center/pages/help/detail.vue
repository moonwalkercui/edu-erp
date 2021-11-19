<template>
	<view class="u-demo-wrap" style="">
		<view class="bg-white u-padding-30 u-border-bottom u-text-center u-font-16">
			{{info.title}}
		</view>
		<view class="bg-white u-padding-30 u-margin-bottom-20 line-height-md">
			<rich-text :nodes="info.content"></rich-text>
		</view>
	</view>
</template>
<script>
	export default {
		components: {},
		data() {
			return {
				id: '',
				info: {},
			}
		},
		onLoad(option) {
			this.id = option.id
			this.handleRequest()
		},
		methods: {
			handleRequest() {
				this.$http.get('sCenter/helpInfo', {
					id: this.id
				}, res => {
					this.info = res
				})
			},
		}
	}
</script>

<style lang="scss" scoped>

</style>
