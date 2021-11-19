<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" @clickLeft="clickLeft"/>
		<view class="wrap">
			<view class="u-avatar-wrap">
				<u-avatar :src="avatar" size="150"></u-avatar>
			</view>
			<u-button @tap="chooseAvatar">选择图片</u-button>
		</view>
	</view>
</template>
<script>
	import { baseUrl } from '@/util/config.js';
	export default {
		data() {
			return {
				avatar: '',
			}
		},
		created() {
			// 监听从裁剪页发布的事件，获得裁剪结果
			uni.$on('uAvatarCropper', path => {
				this.avatar = path;
				this.$http.uploadAvatar(path)
			})
		},
		onLoad() {
			this.getLoginStaff()
		},
		methods: {
			getLoginStaff() {
				this.$http.get('tCenter/base/getLoginStaff',{isToday: true}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.avatar = res.headImg
				})
			},
			chooseAvatar() {
				// 此为uView的跳转方法，详见"文档-JS"部分，也可以用uni的uni.navigateTo
				this.$u.route({
					// 关于此路径，请见下方"注意事项"
					url: '/uview-ui/components/u-avatar-cropper/u-avatar-cropper',
					// 内部已设置以下默认参数值，可不传这些参数
					params: {
						// 输出图片宽度，高等于宽，单位px
						destWidth: 300,
						// 裁剪框宽度，高等于宽，单位px
						rectWidth: 200,
						// 输出的图片类型，如果'png'类型发现裁剪的图片太大，改成"jpg"即可
						fileType: 'jpg',
					}
				})
			},
			clickLeft() {
				uni.switchTab({
					url: '/pages/index/index'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.wrap {
		padding: 40rpx;
	}

	.u-avatar-wrap {
		margin-top: 80rpx;
		overflow: hidden;
		margin-bottom: 80rpx;
		text-align: center;
	}

	.u-avatar-demo {
		width: 150rpx;
		height: 150rpx;
		border-radius: 100rpx;
	}
</style>