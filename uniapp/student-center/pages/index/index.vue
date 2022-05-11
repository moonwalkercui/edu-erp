<template>
	<view>
		<u-notice-bar mode="vertical" :list="tips" v-if="tips.length > 0" @click="clickTip"></u-notice-bar>

		<view class="u-margin-30 bg-white boder-radius-md" style="overflow: hidden;">
			<swiper class="swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="300"
				style="height: 360rpx;" indicator-active-color="#ff9900">
				<swiper-item v-for="(item,index) in banners" :key="index">
					<image :src="item.cover" mode="widthFix" style="height: 360rpx; width: 100%;"
						@click="showAdDetail(item)"></image>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="u-margin-30 boder-radius-md" style="overflow: hidden;">
			<u-grid :col="5">
				<u-grid-item v-for="(item,index) in navs" :key="index"
					@click="handleNav(item.page)">
					<u-badge :count="item.count"></u-badge>
					<u-icon :name="item.icon" :color="item.color" :size="46" custom-prefix="custom-icon"></u-icon>
					<view class="grid-text">{{item.label}}</view>
				</u-grid-item>
			</u-grid>
		</view>

		<view class="u-flex u-p-20 bg-primary u-margin-30 boder-radius-md" @click="studentManage">
			<view class="u-m-r-10">
				<u-avatar :src="avatar" size="80"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-10 text-white">{{info.name||'请先添加学生'}}
					<u-icon class="u-m-l-10" name="woman" color="#ff557f" size="28" v-if="info.gender == '女'"></u-icon>
					<u-icon class="u-m-l-10" name="man" color="#2979ff" size="28" v-if="info.gender == '男'"></u-icon>
				</view>
				<view class="u-font-14 u-tips-color text-white">{{info.age? info.age + '岁':''}}</view>
			</view>
			<view class="u-m-l-10 u-p-10">
				<text class="u-font-sm text-white"></text>
				<u-icon name="arrow-right" color="#fff" size="28"></u-icon>
			</view>
		</view>

		<!-- <view class="u-m-30">
			<button @click="studentManage" class="submitbtn orange" >添加学生</button>
		</view> -->

		<u-card title="今日课表" :sub-title="lessonList.length>0 ? `今日${lessonList.length}节课` : '无课'" :padding="20">
			<view class="" slot="body">
				<view class="bg-light-gray u-flex boder-radius-md u-padding-20 u-margin-bottom-10"
					v-for="(item,index) in lessonList" :key="index">
					<view class="u-flex-3">
						<view class="line-height-md">
							<view class="u-black u-type-primary-dark">{{item.startTime}} ~ {{item.endTime}}</view>
							<view class="u-font-md text-black">{{item.className}} {{item.courseName}}</view>
							<view class="u-font-sm">{{item.teacherNames}} {{item.classroom}} {{item.decCount}}课次</view>
						</view>
					</view>
					<view class="u-flex-1 u-text-right">
						<view class="u-flex-1 u-text-right">
							<view class="u-font-sm u-margin-bottom-40">{{item.studentSignState}}</view>
							<view>
								<u-button v-if="item.studentSignState == '未到课'" type="primary" shape="circle"
									size="mini" @click="handleSign(item)">签 到
								</u-button>
							</view>
						</view>
					</view>
				</view>
				<u-empty v-if="lessonList.length == 0" mode="list" text="今日无课"></u-empty>
			</view>
		</u-card>
		
		<view class="u-margin-30 bg-white boder-radius-md">
			<view class="u-padding-20 u-font-md u-flex">
				<view class="u-flex-3">我的课程</view>
				<view class="u-flex-1 u-text-right u-font-26 text-gray" @click="myCourse">全部</view>
			</view>
			<view class=" u-padding-bottom-20 ">
				<u-cell-group>
					<u-cell-item v-for="(item,index) in courseList" :key="index" @click="lessonSign(item)">
						<view slot="title" class="text-bold">{{item.courseName}}</view>
						<view slot="label">
							<text class="text-mini">{{item.expireDate}}前有效</text>
						</view>
						<view>
							<u-circle-progress
								:active-color="parseInt(item.countLessonComplete*100/item.countLessonTotal) >= 80 ? '#ff6600':  '#2ac79f'"
								:percent="parseInt(item.countLessonComplete*100/item.countLessonTotal)" width="110">
								<view class="u-progress-content">
									<text class="u-font-12"><text
											class="text-black">{{item.countLessonComplete}}</text>/{{item.countLessonTotal}}</text>
								</view>
							</u-circle-progress>
						</view>
					</u-cell-item>
				</u-cell-group>
				<u-empty v-if="courseList.length == 0" mode="list" text="未报名课程"></u-empty>
			</view>
		</view>
		<Popup :show.sync="adShow" :image="popupCover" @ClickNav="clickNav"/>
	</view>
</template>

<script>
	import SpecialBanner from "../../components/special-banner/special-banner.vue"
	import * as db from '@/util/db.js' //引入common
	import * as consts from '@/util/consts.js' //引入common
	import Popup from './components/popup.vue'
	export default {
		components: {
			SpecialBanner,
			Popup,
		},
		data() {
			return {
				info: {
					name: '未添加学生'
				},
				avatar: '',
				
				lessonList: [],
				courseList: [],
				banners: [],

				tips: [],
				tipsList: [],

				adShow: false,
				popupCover: '',
				adInfo: {
					title: '',
					content: '',
					cover1: '',
				},
				
				navs: [
					{
						label: "班级",
						icon: "banji",
						color: '#dc5d5d',
						count: 0,
						page: '/pages/center/classes'
					},
					{
						label: "试听卡",
						icon: "menu_check",
						color: '#5085ff',
						count: 0,
						page: "/pages/lesson/trial"
					},
					{
						label: "作业",
						icon: "kecheng",
						color: '#19c2ae',
						count: 0,
						page: "/pages/homework/index"
					},
					{
						label: "成绩",
						icon: "xuexichengji",
						color: '#0fc1e8',
						count: 0,
						page: '/pages/center/grade'
					},
					{
						label: "点评",
						icon: "tiaodupingjia_1",
						color: '#ea8c00',
						count: 0,
						page: '/pages/lesson/evaluate'
					},
				],
			}
		},
		onLoad() {
			this.getBanners()
		},
		onShow() {
			this.getStudentInfo();
			this.getLessons()
			this.getRedpoint()
			this.getCourse()
		},
		methods: {
			getRedpoint() {
				this.$common.getRedpoint((counts) => {
					this.navs[2].count = counts.homework_count || 0
					this.navs[3].count = counts.grade_count || 0
					this.navs[4].count = counts.evaluate_count || 0
				})
			},
			getStudentInfo() {
				this.$http.get('sCenter/student/info', {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
					this.avatar = res.headImg
					uni.setStorageSync("current-student-info", res)
				})
			},
			getLessons() {
				this.$http.get('sCenter/lesson/list', {
					isToday: true
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.lessonList = res
				})
			},
			getBanners() {
				this.$http.get('sCenter/advertisement', {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					var banners = [];
					var tips = [];
					var tipsList = [];
					var popupInfo;
					for (var ad of res) {
						if (ad.type == "学生端首页Banner") {
							banners.push(ad)
						} else if (ad.type == '学生端首页提示') {
							tips.push(ad.title)
							tipsList.push(ad)
						} else if (ad.type == '学生端首页提示') {
							popupInfo = {
								id: ad.id,
								cover: ad.cover
							}
						}
					}
					if (tips.length == 0) {
						tips = ["欢迎使用"]
						tipsList = [{
							title: "欢迎使用",
							content: ''
						}]
					}
					this.banners = banners
					this.tips = tips
					this.tipsList = tipsList
					
					if(popupInfo) {
						var key = "home-popup-read#" + popupInfo.id;
						var read = uni.getStorageSync(key)
						if (read == '' || !read) {
							this.adShow = true
							this.popupCover = popupInfo.cover
							uni.setStorageSync(key, true)
						}
					}
				})
			},
			getCourse() {
				this.$http.get('sCenter/course/list', {
					excludeExpired: true
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.courseList = res
				})
			},
			handleSign(lesson) {
				this.$common.showAlert("签到确认", `确认签到本课次？`, () => {
					this.$http.post(`sCenter/lesson/sign/${lesson.id}`, {}, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						this.$common.showMsg("已签到")
						this.getLessons()
					})
				})
			},
			myCourse() {
				this.handleNav('/pages/center/contract-record')
			},
			lessonSign(row) {
				this.handleNav('/pages/lesson/sign-record?courseId=' + row.courseId)
			},
			handleNav(url) {
				uni.navigateTo({
					url
				});
			},
			showAdDetail(item) {
				this.adShow = true;
				this.adInfo = {
					title: item.title,
					content: item.content,
					cover: item.cover,
				}
			},
			clickTip(index) {
				this.adShow = true;
				this.adInfo = {
					title: this.tipsList[index].title,
					content: this.tipsList[index].content,
					cover: this.tipsList[index].cover,
				}
			},
			studentManage() {
				uni.navigateTo({
					url: "/pages/student/index"
				})
			},
			clickNav() {
				console.log('点击广告')
			},
		}
	}
</script>

<style lang="scss" scoped>
	.grid-text {
		font-size: 24rpx;
		margin-top: 8rpx;
		color: $u-type-info;
	}
</style>
