<template>
	<view>
		<view class="cu-form-group" style="position: fixed; bottom: 0;width: 100%;">
			<view class="title">Only my class:</view>
			<switch class='orange radius' @change="changeType" :class="onlyMe?'checked':''" :checked="onlyMe?true:false"></switch>
		</view>
		<lxCalendar @change="change"></lxCalendar>
		<view style="margin-bottom: 300upx;">
			<view class="margin padding round bg-white shadow flex justify-between" v-for="(item, index) in list" :key="index">
				<view style="flex: 1;">{{item.times.name}}</view>
				<view style="flex: 1;">{{item.staff.name}}</view>
				<view style="flex: 1;">{{item.clazz.name}}</view>
				<view style="flex: 1;">{{item.section ? item.section.title : ''}}</view>
			</view>
			<view class="text-gray text-center" v-if="list.length == 0">No Class</view>
		</view>
	</view>
</template>
<script>
	import * as common from "@/common/common.js"
	import * as api from "@/common/api.js"
	import lxCalendar from '@/components/lx-calendar/lx-calendar.vue'
	export default {
		components: {
			lxCalendar,
		},
		data() {
			return {
				title: 'Hello',
				onlyMe: false,
				date: '',
				list: [],
			}
		},
		onLoad() {
			this.handleReq()
		},
		methods: {
			change(e) {
				this.date = e.fulldate
				this.handleReq()
			},
			changeType(e) {
				this.onlyMe = e.detail.value
				this.handleReq()
			},
			handleReq() {
				api.get('staff/calendar', {date: this.date, only_me: this.onlyMe ? 1 : 0}, res => {
					if (res.result == 'success') {
						this.list = res.data
					} else {
						common.showError(res.msg)
					}
				})
			}
		}
	}
</script>

<style>
</style>
