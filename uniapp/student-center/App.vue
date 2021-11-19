<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	@import "static/iconfont.css";
	@import "uview-ui/index.scss";
	/*每个页面公共css */
	@import "static/global.scss";
	body {
		background-color: $u-bg-color;
	}
	.topbg{
			position: fixed; top: 0; width: 100%; height: 240rpx;
			z-index: -1;
		}
		.submitbtn{
			background-color: $u-type-primary-dark;
			border: 4rpx solid $u-type-primary-dark;
			color: #fff;
			border: none;
			font-size: 32rpx;
			padding: 0rpx 0;
			border-radius: 40rpx;
			&::after {
				border: none;
			}
			&.light{
				border: 4rpx solid $u-type-primary-dark;
				background-color: white;
				color: $u-type-primary-dark;
			}
			&.orange{
				border: 4rpx solid $u-type-warning-dark;
				background-color: $u-type-warning-dark;
				color: #fff;
			}
		}
		.inputbox{
			border-radius: 50rpx;
			overflow: hidden;
			display: flex;
			border: 4rpx solid $u-type-primary-dark;
			height: 90rpx;
			background-color:$u-type-primary-dark;
			.inputlabel{
				color: #fff;
				width: 200rpx;
				padding: 20rpx;
				text-align: center;
			}
			.inputvalue{
				flex: 1;
				padding: 20rpx;
				background-color: #fff;
			}
		}
		
		.student-box {
			height: 185rpx;
			overflow: hidden;
		
			image.gender {
				width: 100rpx;
				height: 100rpx;
				margin-top: 30rpx;
				margin-right: 30rpx;
				margin-bottom: 40px;
			}
			.info {
				flex: 1;
				margin-left: 40rpx;
			}
		}
</style>
