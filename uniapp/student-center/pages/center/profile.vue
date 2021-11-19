<template>
	<view class="bg-white u-p-b-10">
		<view class="u-avatar-wrap u-p-t-80">
			<u-avatar :src="info.headImg" size="150"></u-avatar>
		</view>
		<view>
			<u-cell-group>
				<u-cell-item title="姓名(可修改)" :arrow="false">
					<view class="u-font-md text-black" @click="editName = true; newname=''">{{info.name ? info.name : info.nickname}}</view>
				</u-cell-item>
				<u-cell-item title="手机号" :arrow="false">
					<view class="u-font-md text-black">{{info.mobile}}</view>
				</u-cell-item>
				<u-cell-item title="注册时间" :arrow="false">
					<view class="u-font-md text-black">{{info.addTime}}</view>
				</u-cell-item>
			</u-cell-group>
		</view>
		<view class="u-m-30">
			<button class="submitbtn" @click="changepw">修改密码</button>
		</view>
		<view class="u-m-30">
			<button class="submitbtn light" @click="handleLogout">退出登录</button>
		</view>
		
		<u-popup v-model="editName" mode="bottom" border-radius="14">
			<view class="u-p-40 u-m-30">
				<u-form ref="uForm" label-width="160">
					<u-form-item label="输入姓名">
						<u-input v-model="newname" placeholder="请输入姓名" />
					</u-form-item>
				</u-form>
				<u-button @click="saveName">保 存</u-button>
			</view>
		</u-popup>
		
	</view>
</template>
<script>
	import UploadImg from "../../components/UploadImg.vue"
	export default {
		components:{UploadImg},
		data() {
			return {
				studentId: '',
				info: {},
				editName: false,
				newname: '',
			}
		},
		onLoad() {
			this.getCurrentUserInfo();
		},
		methods: {
			saveName() {
				if (this.newname == '') {
					this.$common.showMsg("请输入姓名")
					return;
				}
				this.updateUserInfo({
					name: this.newname
				})
				this.editName = false;
			},
			updateUserInfo(data) {
				this.$http.post('sCenter/updateUserInfo', data, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					if (res.errCode == 0) {
						this.$common.showMsg(res.msg)
						this.getCurrentUserInfo()
					}
				})
			},
			getCurrentUserInfo() {
				this.$http.get('sCenter/getCurrentUserInfo',{}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			handleLogout() {
				this.$store.commit("logout")
				uni.navigateTo({
					url: "/pages/login/index"
				})
			},
			changepw() {
				uni.navigateTo({
					url: "/pages/center/changepw"
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
