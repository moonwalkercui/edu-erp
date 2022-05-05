<template>
	<view>
		<view class="u-margin-30 u-p-20 bg-white boder-radius-md u-flex" v-for="(item,index) in list" :key="index" @click="showDetail(item)">
			<view style="width: 35%;" class="u-p-r-20" v-if="item.img">
				<u-image width="100%" height="200rpx" :src="item.img"></u-image>
			</view>
			<view class="u-flex-1 u-font-13">
				<view> <text class="u-font-md text-bold u-m-r-10">{{item.title}} </text><u-tag :text="item.type" type="warning" size="mini" /></view>
			<!-- 	<view class="u-m-t-10">
					<u-tag :text="item.type" type="warning" size="mini" />
				</view> -->
				<view class="u-m-t-10 text-gray">难度: 五颗星</view>
				<view class="u-m-t-10 text-gray">2022年5月1日-5月20日 · 五年级</view>
				<view class="u-flex u-m-t-10">
					<view class="u-flex-2">
						<u-avatar v-for="(tea,ind) in item.teachers" :key="ind" :src="item.img" size="48" class="u-m-r-10"></u-avatar>
					</view>
					<view class="u-flex-1 u-text-right" style="color: #ff6600;">
						￥<text class="u-font-16 text-bold">{{item.price}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<u-divider bg-color="#f3f4f6">没有更多了</u-divider>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [{
					title: '现货 原创jk制服',
					img: '//img13.360buyimg.com/n7/jfs/t1/103005/7/17719/314825/5e8c19faEb7eed50d/5b81ae4b2f7f3bb7.jpg',
					type: '小班课',
					deliveryTime: '付款后7天内发货',
					price: '128.05',
					number: 1,
					teachers: [1, 2, 3]
				},
				{
					title: '现货 原创jk制服',
					img: '',
					type: '小班课',
					deliveryTime: '付款后7天内发货',
					price: '128.05',
					number: 1,
					teachers: [1, 2, 3]
				},
				{
					title: '现货 原创jk制服',
					img: '//img13.360buyimg.com/n7/jfs/t1/103005/7/17719/314825/5e8c19faEb7eed50d/5b81ae4b2f7f3bb7.jpg',
					type: '小班课',
					deliveryTime: '付款后7天内发货',
					price: '128.05',
					number: 1,
					teachers: [1, 2, 3]
				},
				{
					title: '现货 原创jk制服',
					img: '//img13.360buyimg.com/n7/jfs/t1/103005/7/17719/314825/5e8c19faEb7eed50d/5b81ae4b2f7f3bb7.jpg',
					type: '小班课',
					deliveryTime: '付款后7天内发货',
					price: '128.05',
					number: 1,
					teachers: [1, 2, 3]
				}],
			}
		},
		methods: {
			
			showDetail() {
				uni.navigateTo({
					url: '/pages/shop/courseDetail'
				});
			}
		}
	}
</script>

<style>
</style>
