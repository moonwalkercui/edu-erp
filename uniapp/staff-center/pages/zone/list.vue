<template>
	<view class="content">

		<view class="cu-card dynamic no-card margin-bottom-sm"  v-for="(item, index) in list" :key="index">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round" :style="{'background-image': 'url(' + item.student_avatar +')'}"></view>
						<view class="content flex-sub flex justify-between">
							<view>{{item.student_name}}</view>
							<view class="text-gray text-xs ">
								{{item.add_time}}
							</view>
						</view>
					</view>
				</view>
				<view class="text-content margin-top-sm" v-if="item.content" >
					{{item.content}}
				</view>
				<view class="grid flex-sub padding-lr col-3 grid-square" v-if="item.type != 1">
					<view class="bg-img" :style="{backgroundImage: 'url(' + fixImgUrlFunc(img) +')'}" v-for="(img,ind) in item.attach" :key="ind" v-if="item.type == 3" @tap="previewImg(item.attach, ind)"></view>
					<view class="bg-gray" @tap="playAudio(item)" v-if="item.type == 2">
						<text class="cuIcon-stop margin-lr-xs" v-if="item.playing"></text>
						<text class="cuIcon-playfill margin-lr-xs" v-else></text>
						</view>
				</view>
				<view class="text-gray text-right padding">
					<view class="inline-block">
						<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.likes_count}}
					</view>
					<view  class="inline-block margin-left">
						<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.comments.length}}
					</view>
				</view>
				<view class="cu-list menu-avatar comment solids-top" v-if="item.verify_content">
					<view class="cu-item">
						<view class="cu-avatar round" :style="{'background-image': 'url(' + fixImgUrlFunc(item.verifier_photo) +')'}"></view>
						<view class="content">
							<view class="text-grey flex justify-between ">
								<view class="text-sm">
									{{item.verifier_name}} 点评:
								</view>
								<view class="text-xs ">
									{{item.add_time}}
								</view>
							</view>
							<view class="radius margin-top-sm">
								<view class="flex">
									<view class="flex-sub">{{item.verify_content}}</view>
									<view class="margin-left text-lg text-orange">{{item.verify_score}}分</view>
								</view>
							</view>
						</view>
					</view>
				</view>
				<view class="padding-sm text-center" v-else>
					<button class="cu-btn bg-orange round" @tap="handleVerify(item.id)">点 评</button>
				</view>
				<view class="padding-lr padding-bottom-sm ">
					<view class="bg-gray padding-xs radius margin-top-sm" v-for="(comm, idx) in item.comments" :key="idx">
						<text class="text-sm">{{comm.publisher ? comm.publisher.name : '评论人'}}：{{comm.content}}</text>
					</view>
				</view>
			</view>
		</view>
		<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
	</view>
</template>

<script>
	import {fixImgUrl} from "@/common/util.js" 
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	// import bttomNav from "@/components/bttomNav.vue"
	// import wechat from '@/common/wechat.js'
	import paginateMix from "@/common/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		components: {
			// bttomNav
		},
		data() {
			return {
				list: [],
				clazzId: '',
				audioContext: null
			}
		},
		onLoad(q) {
			this.clazzId = q.id;
		},
		onShow() {
			this.handleReq()
		},
		methods: {
			handleReq() {
				api.get('staff/zoneList', {
					clazz_id: this.clazzId
				}, res => {
					if (res.result == 'success') {
						this.list = this.list.concat(res.data.data)
						this.pageData.page = parseInt(res.data.current_page);
						this.pageData.lastPage = parseInt(res.data.last_page);
					} else {
						common.showError(res.msg)
					}
				})
			},
			handleVerify(id) {
				uni.navigateTo({
					url: '/pages/zone/check?id=' + id + '&clazz_id=' + this.clazzId,
					animationDuration: 200
				})
			},
			fixImgUrlFunc(url) {
				return fixImgUrl(url)
			},
			playAudio(item) {
				var playing = item.playing;
				for(var it of this.list) {
					if(it.type == 2) {
						this.$set(it, 'playing', false)
					}
				}
				if(playing == true) {
					this.$set(item, 'playing', false)
					this.audioContext.stop();
					return;
				} else {
					this.$set(item, 'playing', true)
				}
				if(this.audioContext) this.audioContext.stop();
				this.audioContext = uni.createInnerAudioContext();
				this.audioContext.src = item.attach;
				this.audioContext.autoplay = true;
				this.audioContext.onPlay(() => {
				  console.log('开始播放');
				});
				this.audioContext.onError((res) => {
				  console.log(res.errMsg);
				  console.log(res.errCode);
				});
			},
			previewImg(imgs, index) {
				var arr = [];
				for(var img of imgs) {
					arr.push(this.fixImgUrlFunc(img))
				}
				uni.previewImage({
				    urls: arr,
					current: arr[index],
				    longPressActions: {
				        itemList: ['发送给朋友', '保存图片', '收藏'],
				        success: function(data) {
				            console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
				        },
				        fail: function(err) {
				            console.log(err.errMsg);
				        }
				    }
				});
			},
		}
	}
</script>

<style>
</style>
