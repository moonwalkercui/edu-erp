<template>
	<view>
		<view class="padding text-center margin-top">
			<view class="cu-avatar xl round" :style="[{backgroundImage : (staff.photo ?  'url('+staff.photo+')' : 'url(\'./static/img/photo_df.png\')')}]"
			 style="width: 140upx;height: 140upx;"></view>
			<view class="text-center margin-top">{{staff.name}}</view>
		</view>

		<view class="cu-bar bg-white solid-bottom margin-top">
			<view class="action">
				<text class="cuIcon-title text-blue "></text>Please select:
			</view>
			<view class="action">
			</view>
		</view>
		<view class="cu-list grid col-3">
			<view class="cu-item" v-for="(item,index) in cuIconList" :key="index" @tap="redirect(item.url)">
				<view :class="['cuIcon-' + item.cuIcon,'text-' + item.color]">
					<view class="cu-tag badge" v-if="item.badge!=0">
						<block v-if="item.badge!=1">{{item.badge>99?'99+':item.badge}}</block>
					</view>
				</view>
				<text>{{item.name}}</text>
			</view>
		</view>
		<view class="text-xs text-center" style="color:#aaa; width: 100%; position: fixed; bottom: 50upx;">宏之博软件提供技术支持</view>
	</view>
</template>
<script>
	import {
		baseUrl
	} from "@/common/config.js"
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	// import wechat from '@/common/wechat.js'
	export default {
		data() {
			return {
				cuIconList: [{
					cuIcon: 'punch',
					color: 'red',
					badge: 0,
					url: 'index/sign',
					name: 'Sign in'
					// 			}, {
					// cuIcon: 'qr_code',
					// color: 'cyan',
					// badge: 0,
					// url: 'scan',
					// name: '扫码核销'
					// },{
					// 	cuIcon: 'choicenessfill',
					// 	color: 'orange',
					// 	badge: 0,
					// 	url: 'activate/index',
					// 	name: '激活会员'
					// },  {
					// 	cuIcon: 'choiceness',
					// 	color: 'yellow',
					// 	badge: 0,
					// 	url: 'activate/give',
					// 	name: '发放年卡'
					// }, {
					// 	cuIcon: 'rechargefill',
					// 	color: 'olive',
					// 	badge: 0,
					// 	url: 'give/index',
					// 	name: '发放次卡'
				}, {
					cuIcon: 'calendar',
					color: 'olive',
					badge: 0,
					url: 'course/calendar',
					name: 'Schedule'
				}, {
					cuIcon: 'recharge',
					color: 'blue',
					badge: 0,
					url: 'payout/apply',
					name: 'Fund',
				}, {
					cuIcon: 'exit',
					color: 'purple',
					badge: 0,
					url: 'offduty/apply',
					name: 'Absence'

				}, {
					cuIcon: 'community',
					color: 'cyan',
					badge: 0,
					url: 'zone/index',
					name: 'Homework'
					// }, {
					// 	cuIcon: 'commandfill',
					// 	color: 'mauve',
					// 	badge: 0,
					// 	url: 'login/index',
					// 	name: '退出'
				}],
				staff: {}
			};
		},
		onShow() {
			this.handleReq();
		},
		methods: {
			handleReq() {
				api.get('staff/home', {}, res => {
					if (res.result == 'success') {
						this.staff = res.data.staff;
						var zone_count = res.data.zone_count;
						if(zone_count > 0)
							for (var item of this.cuIconList) {
								if (item.url == 'zone/index') {
									item.badge = zone_count;
									break;
								}
							}
					} else {
						common.showError(res.msg)
					}
				})
			},

			redirect(url) {
				uni.navigateTo({
					url: '/pages/' + url,
					animationDuration: 200
				})
				// switch (url) {
				// 	case "sign":
				// 		window.location.href = baseUrl + 'wxauth.html?state=sign';
				// 		break;
				// 		// case "scan":  this.handelScan(); break;
				// 		// case "logout":  this.handleLogout(); break;
				// 	default:
				// 		uni.navigateTo({
				// 			url: '/pages/' + url,
				// 			animationDuration: 200
				// 		})
				// }
			},
			handelScan() {
				wechat.scanQRCode(function(res) {
					uni.navigateTo({
						url: '/pages/verify/scan?code=' + res,
						animationDuration: 200
					})
					// api.get('handleScan', {code: res}, res => {
					// 	if (res.result == 'success') {
					// 		common.showSuccess(res.msg)
					// 		setTimeout(() => {this.handleReq() }, 1500)
					// 	} else {
					// 		common.showError(res.msg)
					// 	}
					// })
				})
			},
			handleLogout() {
				console.log('logout')
			}
		}
	}
</script>

<style>
</style>
