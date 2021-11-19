<template>
	<view>

		<view class="u-flex user-box u-p-30 bg-primary">
			<view class="u-m-r-10">
				<u-avatar :src="avatar || pic" size="140"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-10 text-white">{{name}}</view>
				<view class="u-font-14 u-tips-color text-white">{{mobile}}</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<text class="u-font-sm text-white">退出</text>
				<u-icon name="arrow-right" color="#fff" size="28"></u-icon>
			</view>
		</view>

		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="rmb-circle-fill" title="保单记录" @click="contractrecord"></u-cell-item>
				<u-cell-item icon="checkmark-circle-fill" title="点名记录" @click="rollcallrecord"></u-cell-item>
				<u-cell-item icon="plus-people-fill" title="我的客户" @click="mycustomer"></u-cell-item>
			</u-cell-group>
		</view>

		<!-- <view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item icon="setting" title="设置"></u-cell-item>
			</u-cell-group>
		</view> -->

	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				pic:  '',
				show: true,
			}
		},
		computed: {
			...mapState({
				name: state => state.curUser.name,
				mobile: state => state.curUser.mobile,
				avatar: state => state.curUser.avatarUrl,
			})
		},
		onLoad() {
			console.log(this.$store.state.curUser)
		},
		methods: {
			contractrecord() {
				uni.navigateTo({
					url: '/pages/contract/index'
				});
			},
			rollcallrecord() {
				uni.navigateTo({
					url: '/pages/center/rollcall-record'
				});
			},
			mycustomer() {
				uni.navigateTo({
					url: '/pages/center/my-customer'
				});
			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ededed;
	}

	.camera {
		width: 54px;
		height: 44px;

		&:active {
			background-color: #ededed;
		}
	}
</style>
