<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="交作业" @clickLeft="clickLeft" />

		<view class="bg-white u-padding-30">

			<u-form :model="model" :rules="rules" ref="uForm" :errorType="errorType">

				<u-upload ref="uUpload" :action="action" :header="header" :file-list="fileList"
				@on-success="uploadSuccess" @on-error="uploadError" @on-remove="onRemove" @on-uploaded="onUploaded"
				:max-size="10 * 1024 * 1024" max-count="6" :auto-upload="true"></u-upload>

				<u-form-item label="" prop="content">
					<u-input type="textarea" v-model="model.content" autoHeight></u-input>
				</u-form-item>

			</u-form>

			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon>提交作业
				</view>
			</view>
			<view class="fix-bottom-buttons-box"></view>

		</view>
	</view>
</template>
<script>
	import {baseUrl} from "@/util/config"
	import * as consts from '@/util/consts.js' //引入common
	export default {
		components: {},
		data() {
			return {
				id: '',
				action: baseUrl + 'sCenter/upload',
				header: {},
				fileList: [],
				uploadList: [],

				model: {
					content: '',
				},
				rules: {
					content: [{
						required: true,
						message: '请输入内容',
						trigger: ['change', 'blur']
					}],
				},

				errorType: ['message'],
			}
		},
		onLoad(option) {
			this.id = option.id
			this.getHeader();
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			getHeader() {
				this.header = this.$common.getRequestHeader()
			},
			chooseStartDate(a) {
				this.model.startDate = a.year + '-' + a.month + '-' + a.day
			},
			chooseExpiredDate(a) {
				this.model.expireDate = a.year + '-' + a.month + '-' + a.day
			},
			chooseStudent(s) {
				this.model.studentName = s
			},
			chooseCourse(s) {
				this.model.courseName = s
				this.getCourseInfo(s)
			},
			getCourseInfo: async function(name) {
			},
			uploadSuccess(data, index, lists, name) {
				this.makeUploadList(lists)
			},
			onRemove(index, lists, name) {
				console.log("remove")
				this.makeUploadList(lists)
			},
			makeUploadList(lists) {
				var fileList = [];
				for(var res of lists) {
					if(res.response) {
						if(res.response.errCode != 0) {
							this.$common.showMsg(res.response.msg)
							return;
						}
						fileList.push(res.response.data)
					}
				}
				this.uploadList = fileList;
				// console.log("uploadlist", this.uploadList)
			},
			uploadError(res) {
				this.$common.showMsg("上传出错")
			},	
			onUploaded(lists, name) {
				this.makeUploadList(lists)
			},	
				
			toSubmit() {
				
				var imgUrls = [];
				if(this.uploadList.length > 0) {
					for(var file of this.uploadList) {
						imgUrls.push(file.url)
					}
				}
				
				this.$refs.uForm.validate(valid => {
					if (valid) {
						var data = {
							homeworkId : this.id,
							content : this.model.content,
							imgList : imgUrls
						}
						this.$http.post('sCenter/homework/saveRecord',data, res => {
							if (!this.$common.handleResponseMsg(res)) return;
							this.$common.showSuccess("提交成功", () => {
								this.clickLeft()
							})
						}, false)
					} else {
						console.log('验证失败');
					}
				});
			},
			clickLeft() {
				uni.navigateTo({
					url: '/pages/homework/detail?id='+this.id,
				})
			},
			clickRight() {
				uni.navigateTo({
					url: '/pages/contract/index',
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #fff;
	}
</style>
