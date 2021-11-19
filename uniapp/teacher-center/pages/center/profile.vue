<template>
	<view>
		<uni-nav-bar left-icon="arrow-left" left-text="" title="我的资料" @clickLeft="clickLeft" />
		<view>
			<u-cell-group>
				<u-cell-item :arrow="false" title="头像">
					<u-avatar :src="info.headImg" size="100" @click="editAvatar"></u-avatar>
				</u-cell-item>
				<u-cell-item :arrow="false" title="姓名"><view class="u-font-md text-black">{{info.name}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="手机号码"><view class="u-font-md text-black">{{info.mobile}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="性别"><view class="u-font-md text-black">{{info.gender}}</view></u-cell-item>
				<u-cell-item :arrow="false" title="生日"><view class="u-font-md text-black">{{info.birthday}}</view></u-cell-item>
			</u-cell-group>
		</view>
		<view class="fix-bottom-buttons">
			<view class="btn gray" @click="logout">
			退出登录
			</view>
		</view>
		
	</view>
</template>
<script>
	export default {
		data() {
			return {
				info: {},
				courseList: [],
			}
		},
		onShow() {
			this.getLoginStaff()
		},
		methods: {
			getLoginStaff() {
				this.$http.get('tCenter/base/getLoginStaff',{isToday: true}, res => {
					if(!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
			clickLeft() {
				uni.navigateBack()
			},
			logout() {
				this.$store.commit("logout")
				uni.redirectTo({
					url: "/pages/login/index"
				})
			},
			editAvatar() {
				uni.navigateTo({
					url: `/pages/center/avatar`
				});
			}
		}
	}
</script>
