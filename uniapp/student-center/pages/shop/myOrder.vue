<template>
	<view>
		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="tabs" :current="current" @change="change" :is-scroll="false" swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish" :display-multiple-items="1">
				<swiper-item class="swiper-item" v-for="(orderLi, oi) in dataList" :key="oi">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view v-for="(ord, index) in orderLi" :key="index">
								<orderItem :orderInfo="ord" :key="ord.id" @handleEvaluate="handleEvaluate"/>
							</view>
							<u-loadmore :status="loadStatus[oi]" bgColor="#f2f2f2" v-if="orderLi.length > 0"></u-loadmore>
							<view class="centre" v-if="orderLi.length == 0">
								<view class="explain">
									您还没有相关的订单
								</view>
								<view class="btn" @click="courseCenter">在线购课</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<u-popup v-model="popShow" mode="bottom" border-radius="14">
			<view style="padding: 60rpx;">
				<u-form :model="evaluateFrom[currentId]" ref="uForm">
					<u-form-item label="评 分"><u-rate :count="5" v-model="evaluateFrom[currentId].score" size="42"></u-rate><text class="u-m-l-30">{{evaluateFrom[currentId].score}}分</text></u-form-item>
					<u-form-item label="评 价"><u-input v-model="evaluateFrom[currentId].content" type="textarea" :height="100" :auto-height="true"/></u-form-item>
				</u-form>
				<view class="u-text-center">
					<u-button type="primary" @click="orderEvaluate">提交评价</u-button>
				</view>
				<view class="u-m-t-30 u-text-center">
					<u-button @click="popShow=false">取 消</u-button>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
import orderItem from './components/orderItem.vue'
export default {
	components: {
		orderItem
	},
	data() {
		return {
			dataList: [],
			tabs: [
				{
					name: '全部',
					condition: {}
				},
				{
					name: '待付款',
					condition: {state: "0"},
				},
				{
					name: '已完成',
					condition: {state: "10"},
				},
				{
					name: '待评价',
					condition: {state: "1"},
					count: 0
				}
			],
			current: 0,
			swiperCurrent: 0,
			tabsHeight: 0,
			dx: 0,
			loadStatus: [],
			pageData: [],
			popShow: false,
			evaluateFrom: [{ orderId: 0, score: 5, content: '默认好评' }],
			currentId: 0
		};
	},
	onLoad() {
		for (var i = 0; i < this.tabs.length; i++) {
			this.dataList.push([])
			this.loadStatus.push('loadmore')
			this.pageData.push({ page: 1, lastPage: 1 })
			this.getDataList(i);
		}
	},
	onShow() {
		this.reload(this.current)
		this.countUnevaluate()
	},
	methods: {
		// 页面数据
		getDataList(idx) {
			this.loadStatus.splice(idx,1,"loading")
			this.handleReq(idx)
		},
		reachBottom() {
			var idx = this.current;
			if ( this.pageData[idx].lastPage == this.pageData[idx].page )
				this.loadStatus.splice(idx,1,"loading")
				return;
			if (this.pageData[idx].lastPage > this.pageData[idx].page) {
				this.pageData[idx].page ++
				this.getDataList(idx); // 引用页面需要有一个请求方法
			} else {
				uni.showToast({
					icon: 'none',
					title: '没有更多数据'
				})
			}
		},
		
		reload(idx) {
			this.pageData[idx].page = 1;
			this.pageData[idx].lastPage = 1;
			this.dataList[idx] = []
			this.getDataList(idx)
		},
		handleReq(idx) {
			this.$http.get('sCenter/shop/orderList', {
				page: this.pageData[idx].page,
				pageSize: 10,
				...this.tabs[idx].condition
			}, res => {
				if(!res) return;
				if (!this.$common.handleResponseMsg(res)) return;
				this.$set(this.dataList, idx, this.dataList[idx].concat(res.records))
				this.pageData[idx].page = parseInt(res.page);
				this.pageData[idx].lastPage = parseInt(res.pageCount);
				this.loadStatus.splice(idx, 1 ,"loadmore")
			})
		},
		// 待评价的数量
		countUnevaluate() {
			this.$http.get('sCenter/shop/orderCountUnevaluate', {}, res => {
				if(!res) return;
				if (!this.$common.handleResponseMsg(res)) return;
				this.tabs[3].count = res
			})
		},
		handleEvaluate(orderId) {
			this.currentId = orderId
			if(!this.evaluateFrom[orderId]) {
				this.evaluateFrom[orderId] = {
					orderId,
					score: 5,
					content: '默认好评',
				}
			}
			this.popShow = true
		},
		orderEvaluate() {
			if(!this.evaluateFrom[this.currentId]) {
				return;
			}
			
			if(!this.evaluateFrom[this.currentId].orderId) {
				this.$common.showMsg("缺少ID")
				return;
			}
			
			if(!this.evaluateFrom[this.currentId].score) {
				this.$common.showMsg("缺少评分")
				return;
			}
			
			if(!this.evaluateFrom[this.currentId].content) {
				this.$common.showMsg("缺少评价内容")
				return;
			}

			this.$http.post('sCenter/shop/orderEvaluate', this.evaluateFrom[this.currentId], res => {
				if(!res) return;
				if (!this.$common.handleResponseMsg(res)) return;
				this.$common.showMsg(res.msg)
				this.popShow = false
				this.reload(3)
				this.countUnevaluate()
			})
		},
		// tab栏切换
		change(index) {
			this.swiperCurrent = index;
			// this.getDataList(index);
		},
		transition({ detail: { dx } }) {
			this.$refs.tabs.setDx(dx);
		},
		courseCenter() {
			uni.switchTab({
				url: '/pages/shop/index'
			})
		},
		animationfinish({ detail: { current } }) {
			this.$refs.tabs.setFinishCurrent(current);
			this.swiperCurrent = current;
			this.current = current;
		}
	}
};
</script>
<style lang="scss" scoped>
/* #ifndef H5 */
page {
	height: 100%;
	background-color: #f2f2f2;
}
/* #endif */
.centre {
	text-align: center;
	margin: 200rpx auto;
	font-size: 32rpx;

	image {
		width: 164rpx;
		height: 164rpx;
		border-radius: 50%;
		margin-bottom: 20rpx;
	}

	.tips {
		font-size: 24rpx;
		color: #999999;
		margin-top: 20rpx;
	}

	.btn {
		margin: 80rpx auto;
		width: 200rpx;
		border-radius: 32rpx;
		line-height: 64rpx;
		color: #ffffff;
		font-size: 26rpx;
		background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
	}
}

.wrap {
	display: flex;
	flex-direction: column;
	height: calc(100vh - var(--window-top));
	width: 100%;
}

.swiper-box {
	flex: 1;
}

.swiper-item {
	height: 100%;
}
</style>
