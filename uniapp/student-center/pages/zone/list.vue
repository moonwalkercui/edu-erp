<template>
	<view class="content">
	<scroll-view scroll-x class="bg-white nav">
		<view class="flex text-center">
			<view class="cu-item flex-sub" :class="index==TabCur?'text-orange cur':''" v-for="(item,index) in tabs" :key="index" @tap="tabSelect" :data-id="index">
				{{tabs[index]}}
			</view>
		</view>
	</scroll-view>
		<view class="cu-card dynamic no-card margin-bottom-sm"  v-for="(item, index) in list" :key="index" v-if="TabCur!=2">
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
					<view class="inline-block" @tap="handleLike(item.id, index)">
						<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.likes_count}}
					</view>
					<view  class="inline-block margin-left" @tap="handleComment(item.id, index)">
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
				<view class="padding-lr padding-bottom-sm ">
					<view class="bg-gray padding-xs radius margin-top-sm" v-for="(comm, idx) in item.comments" :key="idx">
						<text class="text-sm">{{comm.publisher ? comm.publisher.name : '评论人'}}：{{comm.content}}</text>
						<text class="text-sm margin-left text-blue" @tap="handleDelComment(comm.id, index)" v-if="comm.is_my == 1">删除</text>
					</view>
				</view>
			</view>
			<view v-if="list.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view>
		
		<view class="cu-list menu sm-border" style="padding-bottom: 200upx;" v-if="TabCur==2">
			<view class="cu-item " :class="{arrow: item.is_finish==0}" v-for="(item, index) in taskList" :key="index">
				<view class="content padding-sm">
					<view class="text-black text-df">{{index+1}}、{{item.content}}</view>
				</view>
				<view class="action text-right">
					<view class="text-orange" v-if="item.is_finish==0" @tap="toFinish(item.id)">去完成</view>
					<view class="text-gray" v-else>已完成</view>
				</view>
			</view>
				<view v-if="taskList.length == 0" class="text-center padding text-gray">暂无记录</view>
		</view>
		
		<view class="cu-modal" :class="showCommentModal?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">发布评论</view>
					<view class="action" @tap="showCommentModal=false">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="">
					<view class="cu-form-group">
						<textarea class="text-left" maxlength="-1" @input="textareaInput" :value="commentContent" placeholder="请输入评论..." style="height: 300upx;"></textarea>
					</view>
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-green text-green" @tap="showCommentModal=false">取消</button>
						<button class="cu-btn bg-green margin-left" @tap="publishComment">确定</button>
			
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {fixImgUrl} from "@/common/util.js" 
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import paginateMix from "@/common/paginateMixins.js"
	export default {
		mixins: [paginateMix],
		components: {
		},
		data() {
			return {
				tabs: ['班级发布','我的发布'], // ,'新任务'
				TabCur: 0,
				list: [],
				taskList: [],
				clazzId: '',
				isMine: 0,
				audioContext: null,
				showCommentModal: false,
				commentContent: '',
				commentId: 0,
				commentIndex: 0
			}
		},
		onLoad() {
			this.handleReq()
		},
		onShow() {
		
		},
		methods: {
			textareaInput(e) {
				this.commentContent = e.detail.value
			},
			handleReq() {
				api.get('student/zoneList', {page: this.pageData.page,mine: this.isMine}, res => {
					if (res.result == 'success') {
						this.list = this.list.concat(res.data.data)
						this.pageData.page = parseInt(res.data.current_page);
						this.pageData.lastPage = parseInt(res.data.last_page);
					} else {
						common.showError(res.msg)
					}
				})
			},
			handleReqTasks() {
				api.get('student/taskList', {page: this.pageData.page}, res => {
					if (res.result == 'success') {
						this.taskList = this.taskList.concat(res.data.data)
						this.pageData.page = parseInt(res.data.current_page);
						this.pageData.lastPage = parseInt(res.data.last_page);
					} else {
						common.showError(res.msg)
					}
				})
			},
			toFinish(id) {
				uni.navigateTo({
					url: '/pages/zone/publish?id=' + id ,
					animationDuration: 200
				})
			},
			handleLike(id, index) {
				api.get('student/zoneLike', {id: id}, res => {
					common.showError(res.msg)
					this.list[index].likes_count = res.data.count;
				})
			},
			handleComment(id, index) {
				this.commentContent = '';
				this.commentIndex = index;
				this.showCommentModal = true;
				this.commentId = id;
				// console.log(id,index)
			},
			handleDelComment(id, index) {
				api.post('student/zoneCommentDel', {id: id}, res => {
					common.showError(res.msg)
					this.list[index].comments = res.data.list;
				})
			},
			publishComment() {
				api.post('student/zoneComment', {id: this.commentId, content: this.commentContent}, res => {
					common.showError(res.msg)
					this.list[this.commentIndex].comments = res.data.list;
					this.showCommentModal = false;
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
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.list = [];
				this.pageData.page = 1;
				if(this.TabCur == 0) {
					this.list = [];
					this.isMine = 0;
					this.handleReq()
				} 
				if(this.TabCur == 1) {
					this.list = [];
					this.isMine = 1;
					this.handleReq()
				}
				if(this.TabCur == 2) {
					this.taskList = [];
					this.handleReqTasks()
				}
			}
		}
	}
</script>

<style>
</style>
