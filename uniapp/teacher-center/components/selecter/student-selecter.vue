<template>
	<u-popup v-model="show" mode="right" width="80%">
		<view class="popup-content">
			<view>
				<view class="u-padding-20">
					<u-search placeholder="输入姓名或手机号" v-model="pageData.keyword" :clearabled="true" :show-action="false"></u-search>
				</view>
				<scroll-view scroll-y="true" :style="{height: scrollHeight + 'px'}" @scrolltolower="tobottom">
					<u-radio-group v-model="value" :wrap="true" @change="onChange" v-if="limit ==1">
						<u-radio v-for="(item,index) in list" :key="index" :name="item.id" style="padding: 10px"
							class="u-border-bottom">{{item.name}} <text style="font-size: 90%">{{item.mobile}}</text>
						</u-radio>
					</u-radio-group>
					<u-checkbox-group :wrap="true" @change="onChange" v-else>
						<u-checkbox v-for="(item,index) in list" :key="index" :name="item.id" style="padding: 10px"
							v-model="item.checked" class="u-border-bottom">{{item.name}} <text
								style="font-size: 90%">{{item.mobile}}</text></u-checkbox>
					</u-checkbox-group>
					<view class="u-padding-30 bg-white" v-if="list.length == 0">
						<u-empty mode="list" text="暂无学员"></u-empty>
					</view>
				</scroll-view>
				<view class="fix-bottom-buttons">
					<view class="btn" @tap="toSubmit">
						<u-icon name="checkbox-mark" color="#ffffff" class="icon" :size="30"></u-icon> {{btnText}}
					</view>
				</view>
				<view class="fix-bottom-buttons-box"></view>
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
				this.$http.get('tCenter/student/list', this.pageData, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.list = this.list.concat(res.records) 
					this.pageCount = res.pageCount
				})
			},
		}
	}
</script>
<style lang="scss" scoped>

</style>
