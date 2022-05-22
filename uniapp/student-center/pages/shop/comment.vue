<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" right-icon="reload" title="上课点评" @clickLeft="clickLeft"
			@clickRight="clickRight" />

		<view class="bg-white">
			<view class="comment u-border-bottom" v-for="(res, index) in list" :key="res.id">
				<view class="left">
					<image :src="res.headImg" mode="aspectFill"></image>
				</view>
				<view class="right">
					<view class="top">
						<view class="name">{{ res.studentName }}</view>
						<view class="like" >
							<u-rate :count="5" v-model="res.score" :disabled="true" active-color="#ffaa00" inactive-color="#dfdfdf"></u-rate>
						</view>
					</view>
					<view class="content">{{ res.content }}</view>
					<view class="bottom">
						{{ res.date }}
					</view>
				</view>
			</view>
		</view>
		<view class="u-padding-30 bg-white" v-if="list.length == 0">
			<u-empty mode="list" text="~ 暂无留言 ~"></u-empty>
		</view>
	</view>
</template>

<script>
	import paginateMix from "@/mixins/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		data() {
			return {
				courseId: '',
				list: []
			};
		},
		onLoad(option) {
			this.courseId = option.courseId
			this.handleReq();
		},
		methods: {
			// 跳转到全部回复
			toAllReply() {
				uni.navigateTo({
					url: '/pages/template/comment/reply'
				});
			},

			// 评论列表
			handleReq() {
				this.$http.get('sCenter/shop/commentList', {
					courseId: this.courseId,
					page: this.pageData.page
				}, res => {
					if(!res) return;
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records)
					this.pageData.page = parseInt(res.page);
					this.pageData.lastPage = parseInt(res.pageCount);
				})
			},
			clickRight() {
				this.list = [];
				this.pageData.page = 1
				this.handleReq()
			},
			clickLeft() {
				uni.navigateBack()
			}
		}
	};
</script>

<style lang="scss" scoped>
	@import "./static/comment.scss";
</style>
