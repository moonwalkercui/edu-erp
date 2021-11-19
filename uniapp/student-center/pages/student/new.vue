<template>
	<view class="bg-white u-p-b-30">
		<view class="u-avatar-wrap u-p-t-80" @click="changeavatar">
			<u-avatar :src="avatar" size="150"></u-avatar>
		</view>
		<view class="u-m-30">
			<u-form ref="uForm" label-width="200">
				<u-form-item label="姓名">
					<u-input v-model="name" />
				</u-form-item>
				<u-form-item label="性别" right-icon="arrow-down">
					<picker @change="bindPickerChange" :value="sexIndex" :range="sex" style="width: 100%;">
						<view class="uni-input text-black">{{sex[sexIndex]}}</view>
					</picker>
				</u-form-item>
				<u-form-item label="您的身份" right-icon="arrow-down">
					<picker @change="familyRelChange" :value="familyRelIndex" :range="familyRel" style="width: 100%;">
						<view class="uni-input text-black">{{familyRel[familyRelIndex]}}</view>
					</picker>
				</u-form-item>
				<u-form-item label="身份证号">
					<u-input v-model="idcard" />
				</u-form-item>

				<u-form-item label="出生年月">
					<picker mode="date" :value="date" @change="birthdayChange" :end="endDate" style="width: 100%;">
						<view class="uni-input">{{date}}</view>
					</picker>
				</u-form-item>

				<view class="u-m-t-20">
					<button @click="submit" class="submitbtn" style="margin-top: 50rpx;" :disabled="disabled">保 存</button>
				</view>
				<view class="u-m-t-20 ">
					<button class="submitbtn light" @click="handleDel">移 除</button>
				</view>
			</u-form>
		</view>

	</view>
</template>
<script>
	export default {
		components: {},
		data() {
			const currentDate = this.getDate({
				format: true
			})
			return {
				disabled: false,
				avatar: "",
				idcard: "",
				name: "",
				sex: ['男', '女'],
				sexIndex: 0,
				familyRel: ['妈妈', '爸爸', '爷爷', '奶奶', '姥爷', '姥姥', '叔叔', '姑姑', '其他'],
				familyRelIndex: 0,
				date: currentDate,
				studentId: "",
			}
		},
	    computed: {
			endDate() {
				return this.getDate('end');
			}
		},
		onShow() {
			var avatar = uni.getStorageSync("avatar-tmp")
			if (avatar != '') this.avatar = avatar
		},
		onLoad(option) {
			uni.removeStorageSync("avatar-path")
			if(typeof option.studentId != "undefined") {
				this.studentId = option.studentId
				this.getStudentInfo(option.studentId)
			}
			uni.removeStorageSync("avatar-tmp")
		},
		methods: {
			getStudentInfo(id) {
				this.$http.get('sCenter/student/info',{id}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					console.log(res)
					this.name = res.name
					this.avatar = res.headImg
					this.date = res.birthday
					this.idcard = res.idcard
					for(var s in this.sex) {
						if(this.sex[s] == res.gender) {
							this.sexIndex = s
							break;
						}
					}
					for(var f in this.familyRel) {
						if(this.familyRel[f] == res.familyRel) {
							this.familyRelIndex = f
							break;
						}
					}
				})
			},
			handleUploaded(fileList) {
				if (fileList[0]) {
					this.uploadImg = fileList[0].url
				}
			},
			bindPickerChange: function(e) {
				this.sexIndex = e.target.value
			},
			familyRelChange: function(e) {
				this.familyRelIndex = e.target.value
			},
			changeavatar() {
				uni.navigateTo({
					url: '/pages/student/avatar'
				})
			},
			birthdayChange: function(e) {
			    this.date = e.target.value
		    },
			submit() {
				var data = {
					id: this.studentId,
					name: this.name,
					gender: this.sex[this.sexIndex],
					familyRel: this.familyRel[this.familyRelIndex],
					headImg: this.avatar,
					idcard: this.idcard,
					birthday: this.date,
				}
				if (data.name == "") {
					this.$common.showMsg("缺少姓名")
					return;
				}
				if (data.gender == "") {
					this.$common.showMsg("未选择性别")
					return;
				}
				this.disabled = true
				this.$http.post('sCenter/student/register', data, res => {
					this.disabled = false
					if (!this.$common.handleResponseMsg(res)) return;
					if (res.errCode == 0) {
						this.$common.showMsg(res.msg)
						uni.navigateTo({
							url: '/pages/student/index'
						})
					}
				})
			},
		   getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				// if (type === 'start') {
				// 	year = year - 60;
				// } else 
				// if (type === 'end') {
				// 	year = year + 1;
				// }
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			handleDel() {
				this.$common.showAlert("操作提示", `确认移除该学员?`, () => {
					this.$http.get('sCenter/student/deleteStudent', {
						id: this.studentId
					}, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						if (res.errCode == 0) {
							this.$common.showMsg(res.msg)
							uni.navigateTo({
								url: '/pages/student/index'
							})
						}
					})
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ededed;
	}

	.camera {
		width: 54px;
		height: 44px;

		&:active {
			background-color: #ededed;
		}
	}

	.user-box {
		background-color: #fff;
	}

	.u-avatar-wrap {
		overflow: hidden;
		margin-bottom: 80rpx;
		text-align: center;
	}
</style>
