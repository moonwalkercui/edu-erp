<template>
	<u-popup v-model="show" mode="right" width="80%">
		<view class="popup-content">
			<scroll-view scroll-y="true">
				<view class="u-padding-20">
				<u-search placeholder="输入姓名" v-model="keyword" :clearabled="true"></u-search>
				</view>
				<u-radio-group v-model="value" :wrap="true" @change="onChange" v-if="limit ==1">
					<u-radio v-for="(item,index) in list" :key="index" :name="item.name" style="padding: 10px"
						class="u-border-bottom">{{item.name}}</u-radio>
				</u-radio-group>
				<u-checkbox-group :wrap="true" @change="onChange" v-else>
					<u-checkbox v-model="item.checked" v-for="(item,index) in list" :key="index" :name="item.name" style="padding: 10px"
						class="u-border-bottom">{{item.name}}</u-checkbox>
				</u-checkbox-group>
				<view class="u-padding-30 bg-white" v-if="list.length == 0" >
					<u-empty mode="list" text="暂无学员"></u-empty>
				</view>
			</scroll-view>
			<view class="fix-bottom-buttons">
				<view class="btn" @tap="toSubmit">
					<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon> {{btnText}}
				</view>
			</view>
		</view>
	</u-popup>
</template>

<script>
	import mixins from './mixins.js'
	export default {
		mixins: [mixins],
		methods: {
			getList() {
				this.$http.get('t_center/course/list', {}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = this.listStore = res
				})
			},
		}
	}
</script>
<style lang="scss" scoped>
</style>
