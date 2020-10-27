<template>
	<view class="content">
		<view>
			<view class="cu-form-group">
				<view class="title">用户身份</view>
				<picker @change="pickerChange" :value="typeIndex" :range="types">
					<view class="picker">
						{{types[typeIndex]}}
					</view>
				</picker>
			</view>
			
			<view class="cu-form-group">
				<view class="title">选择班级</view>
				<picker @change="clazzChange" :value="clazzIndex" :range="clazzList">
					<view class="picker">
						{{clazzList[clazzIndex]}}
					</view>
				</picker>
			</view>
			
			<view class="cu-form-group">
				<view class="title">学生姓名</view>
				<input placeholder="请输入" name="input" v-model="name" type="text"></input>
			</view>
			
			<view class="cu-form-group">
				<view class="title">绑定密码</view>
				<input placeholder="请联系负责老师获取密码" name="input" v-model="password" type="text"></input>
			</view>
			
			<view class="cu-form-group">
				<view class="title">手机号码</view>
				<input placeholder="请输入" name="input" v-model="mobile" type="text"></input>
			</view>
			
			<view class="cu-form-group">
				<view class="title">短信验证</view>
				<input placeholder="请输入验证码" name="code" v-model="code"></input>
				<button class='cu-btn bg-green shadow' @tap="sendSms" :disabled="sendBtnDisabled">{{sendSec == 0 ? '发送验证码' : sendSec+'秒后重发'}}</button>
			</view>
			
			<view class="padding flex flex-direction" style="position: fixed;bottom: 100upx; width: 100%;">
				<button class="cu-btn bg-green margin-tb-sm lg shadow" @tap="handleLogin">立即绑定</button>
			</view>
		</view>
	</view>
</template>

<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import * as db from "@/common/db.js"
	
	export default {
		data() {
			return {
				name: '',
				password: '',
				mobile: '',
				code: '',
				sendBtnDisabled: false,
				sendSec: 0,
				typeIndex: 0,
				types: ['学生', '家长'], 
				clazzList: [],
				clazzIndex: 0,
			}
		},
		onLoad() {
			this.loadClazz();
		},
		onShow() {},
		methods: {
			loadClazz() {
				api.get('student/clazzList', {}, res => {
					if (res.result == 'success') {
						this.clazzList = res.data.clazz
						this.types = res.data.role
					} else {
						common.showError(res.msg)
					}
				})
			},
			pickerChange(e) {
				this.typeIndex = e.detail.value
			},
			clazzChange(e) {
				this.clazzIndex = e.detail.value
			},
			handleLogin() {
				if (this.mobile == '') { common.showError('请输入账号'); return; }
				if (this.code == '') { common.showError('请输入短信验证码'); return; }
				if (this.name == '') { common.showError('请输入学生姓名'); return; }
				if (this.password == '') { common.showError('请输入绑定密码'); return; }
				api.post('student/bind', {
					mobile: this.mobile,
					code: this.code,
					name: this.name,
					clazz: this.clazzList[this.clazzIndex],
					password: this.password,
					type: this.typeIndex + 1 // 1'学生' 2'家长'
				}, res => {
					if (res.result == 'success') {
						common.showSuccess(res.msg)
						uni.navigateTo({
							url: '/pages/index/index',
							animationDuration: 200
						})
					} else {
						common.modelShow('错误提示',res.msg)
					}
				})
			},
			sendSms() {
				if(common.isPhoneNumber(this.mobile) == false) {
					common.showError('无效手机号')
					return;
				}
				this.sendBtnDisabled = true;
				this.sendSec = 30; // 倒计时60秒
				var t = setInterval(()=> {
					if(this.sendSec == 0) {
						this.sendBtnDisabled = false;
						clearInterval(t);
					} else {
						this.sendBtnDisabled = true;
						this.sendSec--;
					}
				}, 1000)
				this.handleSent();
			},
			handleSent() {
				api.post('sendsmscode', {
					mob: this.mobile,
				}, res => {
					if (res.result == 'success') {
						common.showSuccess(res.msg)
					} else {
						if(res.data > 0) this.sendSec = res.data;
						common.showError(res.msg)
					}
				})
			}
		}
	}
</script>

<style>
</style>
