<template>
	<view>
		<u-sticky :enable="true">
			<u-tabs :list="list" :is-scroll="false" active-color="#2ac79f" inactive-color="#606266" font-size="24"
				:current="current" @change="change"></u-tabs>
		</u-sticky>
		<u-image width="100%" height="400rpx" :src="info.img"></u-image>
		<view class="u-p-30 bg-white">
			<view class="u-font-13">
				<view> <text class="u-font-md text-bold u-m-r-10">{{info.title}} </text>
					<u-tag :text="info.type" type="warning" size="mini" />
				</view>
				<view class="u-m-t-10 text-gray">难度: 五颗星</view>
				<view class="u-m-t-10 text-gray">2022年5月1日-5月20日 · 五年级</view>
				<view class="u-flex u-m-t-10">
					<view class="u-flex-2">
						<u-avatar v-for="(tea,ind) in info.teachers" :key="ind" :src="info.img" size="48"
							class="u-m-r-10"></u-avatar>
					</view>
					<view class="u-flex-1 u-text-right" style="color: #ff6600;">
						￥<text class="u-font-16 text-bold">{{info.price}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="u-p-30 bg-white text-mini">
			<u-tag text="服务" type="warning" size="mini" /><text class="u-m-l-10">自主调班、支持退课、及时答疑</text>
		</view>

		<view class="u-p-30 bg-white ">
			<view class=" u-p-b-20 u-font-md u-flex">
				<view class="u-flex-3 text-bold">课程大纲</view>
				<view class="u-flex-1 u-text-right text-mini text-gray ">共18课时</view>
			</view>
			<view class="u-padding-bottom-20 ">
				<u-cell-group>
					<u-cell-item>
						<view slot="title" class="text-black">想象写作</view>
						<view slot="label">
							<text class="text-mini"> 5月27日 周五 18:00</text>
						</view>
						<view></view>
					</u-cell-item>
					<u-cell-item>
						<view slot="title" class="text-black">想象写作</view>
						<view slot="label">
							<text class="text-mini"> 5月27日 周五 18:00</text>
						</view>
						<view></view>
					</u-cell-item>
				</u-cell-group>
			</view>
		</view>

		<view class="u-p-30 bg-white ">
			<view class=" u-p-b-20 u-font-md u-flex">
				<view class="u-flex-3 text-bold">课程评价(1002)</view>
				<view class="u-flex-1 u-text-right text-mini text-orange">好评率 97%</view>
			</view>
			<view class="u-padding-bottom-20 ">
				<u-cell-group>
					<u-cell-item>
						<view slot="title" class="text-black">想象写作</view>
						<view slot="label">
							<text class="text-mini"> 5月27日 周五 18:00</text>
						</view>
						<view></view>
					</u-cell-item>
				</u-cell-group>
			</view>
			<view class="u-text-center">
				<u-tag text="查看全部评价" type="info" />
			</view>
		</view>

		<view class="bg-white ">
			<view class="u-p-l-30 u-p-r-30 u-p-b-20 u-font-md u-flex">
				<view class="u-flex-3 text-bold">课程详情</view>
			</view>
			<view class="u-padding-bottom-20 ">
				正文
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				info: {
					title: '现货 原创jk制服',
					img: '//img13.360buyimg.com/n7/jfs/t1/103005/7/17719/314825/5e8c19faEb7eed50d/5b81ae4b2f7f3bb7.jpg',
					type: '小班课',
					deliveryTime: '付款后7天内发货',
					price: '128.05',
					number: 1,
					teachers: [1, 2, 3]
				},
				list: [{
					name: '课程'
				}, {
					name: '大纲'
				}, {
					name: '评价',
				}, {
					name: '详情',
				}],
				current: 0
			}
		},
		methods: {
			change(index) {
				this.current = index;
			},
		}
	}
</script>

<style>
</style>
