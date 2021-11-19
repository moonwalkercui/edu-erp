<template>
	<view class="u-m-30 bg-white boder-radius-md u-p-30">
		<u-form :model="form" ref="uForm" label-width ="150">
			<u-form-item label="旧密码">
				<u-input type="password" v-model="form.oldpassword" />
			</u-form-item>
			<u-form-item label="新密码">
				<u-input type="password" v-model="form.newpassword" />
			</u-form-item>
			<u-form-item label="重复新密码">
				<u-input type="password" v-model="form.repassword" />
			</u-form-item>
		</u-form>
		<view class="u-m-t-20">
			<button @click="submit" class="submitbtn light" style="margin-top: 50rpx;">提 交</button>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				form: {
					oldpassword: '',
					newpassword: '',
					repassword: '',
				},
			};
		},
		methods: {
			submit() {
				if(this.form.oldpassword == '' || this.form.newpassword == '' || this.form.repassword == '' ) {
					this.$common.showMsg("密码不能为空")
					return;
				}
				if(this.form.newpassword != this.form.repassword) {
					this.$common.showMsg("两次输入的密码不一致")
					return;
				}
				this.$common.showAlert("确认提示", `确认修改密码？`, () => {
					this.$http.post('sCenter/changePw', this.form, res => {
						if (!this.$common.handleResponseMsg(res)) return;
						if (res.errCode == 0) {
							this.$common.showMsg(res.msg)
							uni.navigateTo({
								url: "/pages/login/index"
							})
						}
					})
				})
			}
		}
	};
</script>
