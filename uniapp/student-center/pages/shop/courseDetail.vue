<template>
	<view id="dingbu">
		<u-sticky :enable="true" h5-nav-height="0">
			<view :style="{ opacity: navOpacity }" class="top-tabs">
				<u-tabs :list="list" :is-scroll="false" active-color="#2ac79f" inactive-color="#606266" font-size="24"
					:current="current" @change="change"></u-tabs>
			</view>
		</u-sticky>
		<u-back-top :scroll-top="scrollTop" top="600"></u-back-top>
		<view class="arrow-back">
			<u-icon name="arrow-left" color="#dddddd" size="36"></u-icon>
		</view>

		<u-image width="100%" height="400rpx" :src="info.img"></u-image>
		<view class="u-p-30 bg-white u-border-bottom">
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
		<view class="u-p-30 bg-white text-mini u-border-bottom">
			<u-tag text="服务" type="warning" size="mini" /><text class="u-m-l-10">自主调班、支持退课、及时答疑</text>
		</view>
		<!-- 课程大纲 -->
		<view class="u-p-30 u-p-b-0 bg-white " id="dagang">
			<view class=" u-p-b-20 u-font-md u-flex">
				<view class="u-flex-3 text-bold">课程大纲</view>
				<view class="u-flex-1 u-text-right text-mini text-gray ">共18课时</view>
			</view>
			<view class="u-padding-bottom-20 ">
				<u-cell-group>
					<u-cell-item v-for="(sec,secInd) in sections" :key="secInd">
						<view slot="title" class="text-black">想象写作</view>
						<view slot="label">
							<text class="text-mini"> 5月27日 周五 18:00</text>
						</view>
						<view></view>
					</u-cell-item>

				</u-cell-group>
			</view>
		</view>
		<!-- 评价 -->
		<view class="bg-white">
			<view class="u-p-30 u-p-b-0 u-font-md u-flex u-border-bottom" id="pingjia">
				<view class="u-flex-3 text-bold">课程评价(1002)</view>
				<view class="u-flex-1 u-text-right text-mini text-orange">好评率 97%</view>
			</view>
			<view class="comment " v-for="(res, index) in commentList" :key="res.id">
				<view class="left">
					<image :src="res.url" mode="aspectFill"></image>
				</view>
				<view class="right">
					<view class="top">
						<view class="name">{{ res.name }}</view>
						<view class="like" :class="{ highlight: res.isLike }">
							<u-icon v-if="res.isLike" name="thumb-up-fill" :size="30" @click="getLike(index)"></u-icon>
						</view>
					</view>
					<view class="content">{{ res.contentText }}</view>
					<view class="reply-box">
						<view class="item" v-for="(item, index) in res.replyList" :key="item.index">
							<view class="username">{{ item.name }}</view>
							<view class="text">{{ item.contentStr }}</view>
						</view>
						<view class="all-reply" @tap="toAllReply" v-if="res.replyList != undefined">
							共{{ res.allReply }}条回复
							<u-icon class="more" name="arrow-right" :size="26"></u-icon>
						</view>
					</view>
					<view class="bottom">
						{{ res.date }}
					</view>
				</view>
			</view>
			<view class="u-p-30 u-p-t-10">
				<view class="u-text-center">
					<u-tag text="全部评价" type="info" @click="moreComment" />
				</view>
			</view>
		</view>
		<!-- 课程详情 -->
		<view class="bg-white " id="xiangqing">
			<view class="u-p-l-30 u-p-r-30 u-p-b-20 u-font-md u-flex u-border-bottom">
				<view class="u-flex-3 text-bold">课程详情</view>
			</view>
			<view class="u-padding-bottom-20 ">
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
				<u-image width="100%" height="750rpx" :src="info.img"></u-image>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				scrollTop: 0,
				navOpacity: 0,
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
				current: 0,
				sections: [1, 2, 2, 2, 2, 2, 2, 2, 2],

				commentList: [{
						id: 1,
						name: '叶轻眉',
						date: '12-25 18:58',
						contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
						url: 'https://cdn.uviewui.com/uview/template/SmilingDog.jpg',
					},
					{
						id: 2,
						name: '叶轻眉1',
						date: '01-25 13:58',
						contentText: '我不信伊朗会没有后续反应，美国肯定会为今天的事情付出代价的',
						url: 'https://cdn.uviewui.com/uview/template/niannian.jpg',
					}
				],


				elIdNames: ['dingbu', 'dagang', 'pingjia', 'xiangqing'],
				elTop: [0, 0, 0, 0],
			}
		},
		onPageScroll(e) {
			this.handlePageScroll(e)

		},
		onLoad() {
			setTimeout(() => {
				this.getElTops();
			}, 500)
		},
		methods: {

			moreComment() {
				uni.navigateTo({
					url: '/pages/shop/comment'
				})
			},


			// 以下是视图方法
			async getElTops() {
				for (let i in this.elIdNames) {
					const res = await this.$u.getRect('#' + this.elIdNames[i]);
					this.elTop[i] = res.top
				}
				console.log('tops', this.elTop)
			},
			scrollEl(elId) {
				this.$u.getRect('#' + elId).then(res => {
					uni.pageScrollTo({
						scrollTop: this.scrollTop + res.top - 50,
						duration: 0
					})
				})
			},

			handlePageScroll(e) {
				const limit = 200;
				const top = e.scrollTop;
				this.scrollTop = e.scrollTop;
				this.navOpacity = (limit - top) > 0 ? (1 - (limit - top) / limit).toFixed(2) : 1
				for (const i in this.elTop) {
					if (top - this.elTop[i] < 0) {
						this.$u.throttle(() => {
							this.current = i
						}, 100)
						break;
					}
				}
			},
			change(index) {
				this.current = index;
				this.scrollEl(this.elIdNames[index])
			},

		}
	}
</script>

<style lang="scss" scoped>
	@import "./static/comment.scss";

	.top-tabs {
		position: absolute;
		width: 100%;
		z-index: 100;
	}

	.arrow-back {
		position: absolute;
		left: 30rpx;
		top: 100rpx;
		z-index: 99;
	}
</style>
