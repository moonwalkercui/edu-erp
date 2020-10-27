<template>
	<view class="content">
	<!-- 	<view class="cu-bar bg-white search">
			<view class="search-form round">
				<text class="cuIcon-search"></text>
				<input v-model="mobile" type="text" placeholder="搜索的会员手机号" confirm-type="search" style="font-size: 1.6em;"></input>
			</view>
			<view class="action">
				<button class="cu-btn bg-gradual-green shadow-blur round" @tap="handleSearch">搜索</button>
			</view>
		</view> -->
		<view class="cu-bar bg-white solid-bottom">
			<view class="action">
				<text class="cuIcon-title text-orange "></text>请款记录
			</view>
		</view>
		<view class="cu-list menu sm-border" style="padding-bottom: 200upx;">
			<view class="cu-item" v-for="(item, index) in list" :key="index">
				<view class="content padding-sm">
					<view class="text-black text-df">[{{item.type.name}}] {{item.reason}}</view>
					<view class="text-black text-df">收款人: {{item.payee}}</view>
					<view class="text-black text-df">收款账号: {{item.payee_account}}</view>
					<view class="text-gray text-df">申请时间: {{item.add_time}}</view>
					<view class="text-gray text-df">发放时间: {{item.paid_remark}} {{item.paid_time}}</view>
					<view class="text-gray text-df">审核结果: {{item.verify_remark}} {{item.verify_time}}</view>
					<view class="text-gray text-df">
						<view>
							<image v-for="(img,idx) in item.images" :key="img" :src="img" @tap="previewImg(item.images, idx)"
								style="width: 160upx; height:160upx;display: inline-block;border:1px solid #ddd" class="margin-xs" mode="scaleToFill"></image>
						</view>
					</view>
				</view>
				<view class="action text-right">
					<view>{{item.status_text}}</view>
					<view class="text-orange">￥{{item.money}} </view>
				</view>
			</view>
			<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import paginateMix from "@/common/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				mobile: "",
				list: []
			}
		},
		onLoad() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				api.get('staff/payoutHistory', {page: this.pageData.page, mobile: this.mobile}, res => {
					if (res.result == 'success') {
						this.list = this.list.concat(res.data.data)
						this.pageData.page = parseInt(res.data.current_page);
						this.pageData.lastPage = parseInt(res.data.last_page);
					} else {
						common.showError(res.msg)
					}
				})
			},
			handleSearch() {
				this.pageData.page = 1;
				this.list = [];
				this.handleReq();
			},
			previewImg(imgs, index) {
				uni.previewImage({
				    urls: imgs,
					current: imgs[index],
				    longPressActions: {
				        itemList: ['发送给朋友', '保存图片', '收藏'],
				    }
				});
			},
		}
	}
</script>


<style>
</style>
