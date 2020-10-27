<template>
	<view class="content">
		<scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item flex-sub" :class="index==TabCur?'text-orange cur':''" v-for="(item,index) in tabs" :key="index" @tap="tabSelect" :data-id="index">
					{{tabs[index]}}
				</view>
			</view>
		</scroll-view>
		<view class="cu-form-group">
			<textarea maxlength="-1" @input="textareaInput" placeholder="请输入内容..." style="height: 300upx;" class="text-df"></textarea>
		</view>
		<view v-if="TabCur == 0">
			<imgUpload :max="3" />
		</view>
		<view v-else>
			<view>
				<view class="padding text-center">
					<button class="cu-btn shadow text-lg" style="width: 300upx; height: 300upx; border-radius: 50%;" :class="recordIng ? 'bg-grey' : 'bg-orange'"
						@tap="startRecord()">{{recordIng ? '停止录音' : '开始录音'}}</button>
				</view >
				<view class=" text-center">
					<button class="cu-btn bg-grey margin-tb-sm lg shadow" v-if="recordId && TabCur == 1" @tap="playRecord" >播放录音</button>
				</view>
			</view>
		</view>
		<view class="padding flex flex-direction" style="position: fixed;bottom: 0 ; width: 100%;">
			<button class="cu-btn bg-blue margin-tb-sm lg shadow" @tap="handleSubmit" >立即发布</button>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import wechat from '@/common/wechat.js'
	import {
		formatTime
	} from "@/common/util.js"
	import imgUpload from "@/components/imgUpload.vue"

	export default {
		components: {
			imgUpload
		},
		data() {
			return {
				tabs: ['发布图文','发布录音'],
				taskId: '',
				types: [],
				TabCur: 0,
				typeIndex: 0,
				content: '',
				imgList: [],
				recordIng: false,
				playing: false,
				recordId: null,
				serviceId: null, // 音频上传后的id
			}
		},
		onLoad(q) {
			this.taskId = q.id

			uni.$on('choosedImg', res => {
				this.imgList.push(res)
			})
		},
	
		methods: {
			textareaInput(e) {
				this.content = e.detail.value
			},
			PickerChange(e) {
				this.typeIndex = e.detail.value
			},
		
			handleReq() {
				api.get('staff/payoutTypes', {}, res => {
					if (res.result == 'success') {
						this.types = res.data;
					} else {
						common.showError(res.msg)
					}
				})
			},
	
			handleSubmit() {
				if (this.content == '') {
					common.showError("请输入内容")
					return
				}
				var data = {
					task_id: this.taskId,
					content: this.content
				};
				if(this.TabCur == 0) {
					if(this.imgList.length == 0 ){
						common.showError("请选择图片")
						return
					}
					data.images = this.imgList;
				} else {
					if(this.serviceId == null ){
						common.showError("请录音")
						return
					}
					data.voice = this.serviceId;
				}
			
				common.modelShow('请确认', '确认发布内容？', () => {
					api.post('student/zonePublish', data, res => {
						if (res.result == 'success') {
							common.showSuccess(res.msg, () => {
								uni.navigateTo({
									url: '/pages/index/index' ,
									animationDuration: 200
								})
							})
						} else {
							common.showError(res.msg)
						}
					})
				})
			},

			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.list = [];
			},
			startRecord() {
				if(this.recordIng) {
					this.recordIng =false;
					wechat.stopRecord((recId) => {
						this.recordId = recId;
						// uni.showToast({title: '已结束'})
						this.uploadVoice(recId)
					});
				} else {
					this.recordIng = true;
					wechat.startRecord((recId) => {
						this.recordId = recId;
					});
				}
			},
			playRecord() {
				if(this.playing) {
					this.playing =false;
					wechat.stopVoice(this.recordId);
				} else{
					this.playing =true;
					wechat.playVoice(this.recordId);
				}
			},
			uploadVoice() {
				wechat.uploadVoice(this.recordId, (serviceId) => {
					this.serviceId = serviceId;
				});
			},
		}
	}
</script>


<style>
</style>
