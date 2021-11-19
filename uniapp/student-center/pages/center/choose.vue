<template>
	<view>
		<chooseStudent />
	</view>
</template>

<script>
	import chooseStudent from "../../components/ChooseStudent.vue"
	export default {
		components: {
			chooseStudent
		},
		onLoad(option) {
			// this.$common.showMsg("缺少参数url")
			// } else {
			if(typeof option.url != "undefined") {
				this.$common.setStorageUrl(option.url)
			}
		},
		
		data() {
			return {
				list: [],
			}
		},
		methods: {
		}
	}
</script>

<style>

</style>
