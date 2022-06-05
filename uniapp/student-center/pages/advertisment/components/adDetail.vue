<template>
	<u-popup v-model="showBox" mode="bottom"  border-radius="14" height="1200rpx">
		<view class="u-text-center" style="min-height: 1050rpx; overflow-y: scroll;">
			<view class="u-font-14 u-p-30 text-bold">{{detail.title}}</view>
			<view class="u-font-12 text-gray">{{detail.addTime}}</view>
			<view class="u-p-30 ">
				<u-image v-if="detail.cover && detail.cover!=''" style="width: 100%;" height="600rpx" :src="detail.cover"></u-image>
				<view class="u-font-14 u-text-left u-m-t-20">
					<text v-html="detail.content"></text>
				</view>
			</view>
		</view>
		<view class="u-text-center">
			<u-button @click="showBox = false;" size="medium">知道了</u-button>
		</view>
	</u-popup>
</template>
<script>
	export default {
		data() {
			return {
				showBox: false,
				detail: {
					content: '',
					title: '',
					addTime: '',
					cover: null,
				},
			}
		},
		methods: {
			show(detail) {
				this.detail = detail;
				this.showBox = true;
			},
		}
	}
</script>
<style lang="scss" scoped>
</style>
