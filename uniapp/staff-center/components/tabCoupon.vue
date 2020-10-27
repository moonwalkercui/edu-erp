<template>
	<scroll-view scroll-x class="bg-white nav">
		<view class="flex text-center">
			<view class="cu-item flex-sub" :class="index==curIndex?'text-blue cur':''" v-for="(item,index) in tabs" :key="index"
			 @tap="tabSelect(index)" :data-id="index">
				{{item.name}}
			</view>
		</view>
	</scroll-view>
</template>
<script>
	export default {
		props:{
			curIndex: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				tabs: [
					{name: '代金券', url: 'coupon/index'},
					{name: '抢券', url: 'coupon/grab'},
				]
			}
		},
		methods:{
			tabSelect(index) {
				// this.$emit('update:curIndex', index)
				uni.navigateTo({
					url: '/pages/' + this.tabs[index].url,
					animationDuration: 200
				})
			}
		}
	}
</script>

<style>
</style>
