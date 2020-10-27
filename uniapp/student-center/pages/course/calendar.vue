<template>
	<view>
		<lxCalendar @change="change"></lxCalendar>
		<view style="margin-bottom: 200upx;">
			<view class="margin padding round bg-white shadow flex justify-between" v-for="(item, index) in list" :key="index">
				<view>{{item.times.name}}</view>
				<view>{{item.staff.name}}</view>
				<view>{{item.clazz.name}}</view>
				<view>{{item.section ? item.section.title : ''}}</view>
			</view>
		<view class="text-gray text-center" v-if="list.length == 0">今日无课</view>
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
				date: '',
				list: []
			}
		},
		onLoad() {
			
		},
		methods: {
			change(e) {
				this.date = e.fulldate
				this.handleReq()
			},
			handleReq() {
				api.get('student/calendar', {date: this.date}, res => {
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
