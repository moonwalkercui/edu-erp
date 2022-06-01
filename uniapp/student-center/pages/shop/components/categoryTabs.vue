<template>
	<view class="nav-box">
		<u-tabs count="count" :list="tabs" active-color="#2ac79f" inactive-color="#606266"
			font-size="30" :current="current" @change="change"></u-tabs>
		<view class="more-btn"><u-icon name="grid" size="38" @click="showPop=true;"></u-icon></view>
		<u-popup v-model="showPop" mode="top" close-icon-pos='bottom-right' :closeable="true" border-radius="40"
			:custom-style="{marginTop: '90rpx'}" :mask-custom-style="{background: 'rgba(0, 0, 0, 0.1)'}">
			<view class="nav-content">
				<scroll-view scroll-y="true" style="max-height: 700rpx;">
					<view>
						<view v-for="(n,i) in tabs" :key="i" class="cate-item" :class="{'cate-active': current == i , 'cate-recommend': i == 0}"
							@click="changeItem(i)">
							{{n.name}}
						</view>
					</view>
				</scroll-view>
			</view>
		</u-popup>
	</view>
</template>

<script>
export default {
	data() {
		return {
			tabs: [{
				name: '推 荐'
			}],
			current: 0,
			selectId: '',
			showPop: false,
		}
	},
	created() {
		this.getSubjectList()
	},
	methods: {
		getSubjectList() {
			this.$http.get('sCenter/shop/subjectlist', {}, res => {
				if (!this.$common.handleResponseMsg(res)) return;
				var navData = [{
					id: '',
					name: '推 荐'
				}];
				for(const item of res.data) {
					navData.push({
						id: item.id,
						name: item.name,
						count: 0
					});
				}
				this.tabs = navData
				this.change(0)
			})
		},
		change(index) {
			this.current = index;
			this.$emit('change', index, this.tabs[index].id)
		},
		changeItem(index) {
			this.showPop = false
			this.change(index)
		},
	}
}
</script>

<style lang="scss" scoped>
.nav-box {
	position: relative;
	background: none;
}
.more-btn {
	position: absolute;
	right: 0;
	top: 0;
	background-color: #fdfdfd;
	width: 90rpx;
	height: 87rpx;
	text-align: center;
	padding-top: 24rpx;
	color: #3abf9d;
	box-shadow: -10rpx 0rpx 10rpx #eeeeee;
}
.nav-content {
	padding: 30rpx 24rpx;
	text-align: left;
	.cate-item{
		display: inline-block;
		border-radius: 20rpx;
		border: 2rpx solid #eeeeee;
		margin: 10rpx;
		padding: 15rpx 25rpx;
		min-width: 158rpx;
		text-align: center;
		background-color: #efefef;
	}
	.cate-active{
		border: 2rpx solid #2ac79f;
		color: #ffffff;
		background-color: #2ac79f;
		font-weight: bold;
	}
	.cate-recommend{
		border: 2rpx solid #ffe5c7!important;
		color: #ff9900!important;
		background-color: #ffffff!important;
		font-weight: bold;
	}
}
</style>
