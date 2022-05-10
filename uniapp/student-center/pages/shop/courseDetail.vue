<template>
	<view id="dingbu">
		<u-sticky :enable="true" h5-nav-height="0">
			<view :style="{ opacity: navOpacity }" class="top-tabs" v-if="scrollTop>60">
				<u-tabs :list="list" :is-scroll="false" active-color="#2ac79f" inactive-color="#606266" font-size="24"
					:current="current" @change="change"></u-tabs>
			</view>
		</u-sticky>
		<u-back-top :scroll-top="scrollTop" top="600"></u-back-top>
		<view class="arrow-back" @click="handleBack">
			<u-icon name="arrow-left" color="#dddddd" size="32"></u-icon>
		</view>

		<u-image width="100%" height="400rpx" :src="info.cover"></u-image>
		<view class="u-p-30 bg-white u-border-bottom">
			<view class="u-flex-1 u-font-13">
				<view>
					<u-tag :text="info.lessonType" type="warning" size="mini" v-if="info.lessonType=='大班课'" />
					<u-tag :text="info.lessonType" type="error" size="mini" v-if="info.lessonType=='小班课'" />
					<u-tag :text="info.lessonType" type="success" size="mini" v-if="info.lessonType=='一对一'" />
					<text class="u-font-md text-bold u-m-l-10">{{info.name}}</text>
				</view>
			<!-- 	<view class="u-m-t-10">
					<u-tag :text="info.type" type="warning" size="mini" />
				</view> -->
				<view class="u-m-t-10 text-gray">{{info.lessonCount}}课时 · {{info.expireMonths}}个月有效期 </view>
				<view class="u-m-t-10 text-gray">{{info.closeDate ? '报名截至日 ' + info.closeDate : ''}}</view>
				<view class="u-flex u-m-t-10">
					<view class="u-flex-2">
						<!-- <u-avatar v-for="(tea,ind) in info.teachers" :key="ind" :src="info.img" size="48" class="u-m-r-10"></u-avatar> -->
						{{info.teacherInfo}}
					</view>
					<view class="u-flex-1 u-text-right" style="color: #ff6600;">
						￥<text class="u-font-16 text-bold">{{info.price}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="u-p-30 u-m-b-10 bg-white text-mini u-border-bottom">
			<u-tag text="服务" type="warning" size="mini" /><text class="u-m-l-10">{{info.serviceInfo ? info.serviceInfo : '名师指导、专业教育、快乐学习'}}</text>
		</view>
		<!-- 老师 -->
		<!-- <view class="u-p-30 u-m-b-10 bg-white u-flex" id="dagang">
			<view class="u-m-r-30 u-flex" style="width: 260rpx;" v-for="(tea,tindex) in info.teachers" :key="tindex">
				<view class="u-m-r-10">
					<u-avatar :src="tea.img"></u-avatar>
				</view>
				<view>
					<view class="u-font-12">王老师</view>
					<view class="u-font-12 text-gray">班主任</view>
				</view>
			</view>
		</view> -->
		<!-- 课程大纲 -->
		<view class="u-p-30 u-p-b-0 bg-white" id="dagang">
			<view class="u-font-md u-flex">
				<view class="u-flex-3 text-bold">课程大纲</view>
				<view class="u-flex-1 u-text-right text-mini text-gray ">共18课时</view>
			</view>
		</view>
		<view class="u-m-b-10">
			<u-cell-group>
				<u-cell-item v-for="(sec,secInd) in sectionList" :key="secInd" :arrow="false">
					<view slot="title" style="max-width: 520rpx;">{{sec.title}}</view>
					<view slot="label">第{{secInd+1}}部分</view>
					<text class="text-mini" style="min-width: 80rpx;"> {{sec.lessonCount}}节 </text>
				</u-cell-item>
			</u-cell-group>
		</view>
		<!-- 评价 -->
		<view class="bg-white u-m-b-10">
			<view class="u-p-30 u-p-b-0 u-font-md u-flex u-border-bottom" id="pingjia">
				<view class="u-flex-3 text-bold">课程评价(1002)</view>
				<view class="u-flex-1 u-text-right text-mini text-orange">好评率 97%</view>
			</view>
			<view class="comment " v-for="(com, index) in commentList" :key="com.id">
				<view class="left">
					<image :src="com.headImg" mode="aspectFill"></image>
				</view>
				<view class="right">
					<view class="top">
						<view class="name">{{ com.studentName }}</view>
						<view class="like" >
							<u-rate :count="5" v-model="com.score" :disabled="true" active-color="#ffaa00" inactive-color="#dfdfdf"></u-rate>
						</view>
					</view>
					<view class="content">{{ com.content }}</view>
					<view class="bottom">
						{{ com.addDate }}
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
			<view class="u-p-30 u-p-b-20 u-font-md u-flex u-border-bottom">
				<view class="u-flex-3 text-bold">课程详情</view>
			</view>
			<view class="u-padding-bottom-20" style="line-height: 0;">
				<image v-for="(img, imageIndex) in info.imageList" :key="imageIndex" mode="widthFix" style="width: 100%; height: auto;" :src="img.imageUrl"></u-image>
			</view>
		</view>
		<submitBar :courseInfo="info"/>
	</view>
</template>

<script>
	import submitBar from './components/submitBar.vue'
	export default {
		components: {
			submitBar
		},
		data() {
			return {
				id: '',
				scrollTop: 0,
				navOpacity: 0,
				info: {},
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

				sectionList: [],
				commentList: [],

				elIdNames: ['dingbu', 'dagang', 'pingjia', 'xiangqing'],
				elTop: [0, 0, 0, 0],
				hotline: '',
				shoppingGuide: '',
			}
		},
		onPageScroll(e) {
			this.handlePageScroll(e)
		},
		onLoad(option) {
			this.id = option.id
			this.handleRequest();
			this.systemSetting()
		},
		methods: {
			systemSetting() {
				this.$common.systemSettings(['hotline','shopping_guide']).then(res =>{
					this.hotline = res.hotline
					this.shopping_guide = res.shopping_guide
				})
			},
			handleRequest() {
				this.$http.get('sCenter/shop/courseInfo', {
					id: this.id,
				}, res => {
					this.info = res.data
					this.commentList = res.data.commentList
					this.sectionList = res.data.sectionList
					this.getElTops();
				})
			},
			
			moreComment() {
				uni.navigateTo({
					url: '/pages/shop/comment?courseId=' + this.id
				})
			},

			// 以下是视图方法
			async getElTops() {
				for (let i in this.elIdNames) {
					const res = await this.$u.getRect('#' + this.elIdNames[i]);
					this.elTop[i] = res.top
				}
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
			handleBack() {
				uni.navigateBack()
			}
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
		left: 20rpx;
		top: 30rpx;
		z-index: 99;
	}
</style>
