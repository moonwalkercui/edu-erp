<template>
	<view v-if="show">
		<u-modal v-model="show" :showTitle="false" :mask="false" :mask-close-able="false" width="600rpx" :closeable="true"
			close-icon="close-circle" :showConfirmButton="false">
			<view class="content">
				<u-image width="600rpx" height="600rpx" :src="image"  @click="clickNav"></u-image>
			</view>
		</u-modal>
		<view class="close-btn">
			<u-icon name="close-circle" color="#ffffff" size="64" @click="handleClose"></u-icon>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			show: {
				type: Boolean,
				default: false
			},
			image: {
				type: String,
				default: 'https://gtd.alicdn.com/sns_logo/i1/TB124_3NXXXXXasXVXXSutbFXXX.jpg_240x240xz.jpg'
			},
		},
		data() {
			return {
			}
		},
		methods: {
			handleClose() {
				this.$emit('update:show', false)
			},
			clickNav() {
				this.$emit('ClickNav')
			}
		}
	}
</script>

<style lang="scss" scoped>
	.close-btn {
		position: fixed;
		bottom: 200rpx;
		z-index: 20000;
		margin-left: 45%;
	}
</style>