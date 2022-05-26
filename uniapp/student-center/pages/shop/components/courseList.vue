<template>
	<view>
		<view class="u-margin-30 u-p-20 bg-white boder-radius-md u-flex" v-for="(item,index) in list" :key="index" @click="showDetail(item.id)">
			<view style="width: 35%;" class="u-p-r-20" v-if="item.cover">
				<u-lazy-load height="200" :image="item.cover"></u-lazy-load>
			</view>
			<view class="u-flex-1 u-font-13">
				<view>
					<u-tag :text="item.lessonType" type="warning" size="mini" v-if="item.lessonType=='大班课'" />
					<u-tag :text="item.lessonType" type="error" size="mini" v-if="item.lessonType=='小班课'" />
					<u-tag :text="item.lessonType" type="success" size="mini" v-if="item.lessonType=='一对一'" />
					<text class="u-font-md text-bold u-m-l-10">{{item.name}}</text>
				</view>
			<!-- 	<view class="u-m-t-10">
					<u-tag :text="item.type" type="warning" size="mini" />
				</view> -->
				<view class="u-m-t-10 text-gray">{{item.lessonCount}}课时 · {{item.expireMonths}}个月</view>
				<view class="u-m-t-10 text-gray">{{item.closeDate ? '报名截至' + item.closeDate : ''}} · 剩余{{item.storage}}</view>
				<view class="u-flex u-m-t-10">
					<view class="u-flex-2">
						<!-- <u-avatar v-for="(tea,ind) in item.teachers" :key="ind" :src="item.img" size="48" class="u-m-r-10"></u-avatar> -->
						{{item.teacherInfo}}
					</view>
					<view class="u-flex-1 u-text-right" style="color: #ff6600;">
						￥<text class="u-font-16 text-bold">{{item.price}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<view class="u-padding-30 bg-white" v-if="list.length == 0">
			<u-empty mode="list" text="~ 暂无课程 敬请期待 ~"></u-empty>
		</view>
		
	</view>
</template>

<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				recommend: '',
				subjectId: '',
				list: [],
			}
		},
		methods: {
			showDetail(id) {
				uni.navigateTo({
					url: '/pages/shop/courseDetail?id=' + id
				});
			},
			handleReq() {
				this.$http.get('sCenter/shop/list', {
					subjectId: this.subjectId,
					recommend: this.recommend,
					page: this.pageData.page
				}, res => {
					if(!res) return;
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			handleReload(param) {
				if(param) {
					this.recommend = param.recommend
					this.subjectId = param.subjectId
				}
				this.list = []
				this.pageData.page = 1
				this.handleReq()
			}
		}
	}
</script>

<style>
</style>
