<template>
	<u-popup v-model="boxshow" mode="right" width="600">
		<view class="u-flex" style="height:100%;">
			<view style="height:100%; width: 200rpx; background-color: #F3F4F6;">
				<view v-for="(item,index) in regionList" :key="index" class="u-padding-20 u-border-bottom"
					style="margin-bottom: 4rpx; text-align: center;" @click="getSchool(index)">
					{{item.name}}
				</view>
			</view>
			<view style="height:100%; flex: 1; background-color: #fff;">
				<view v-for="(sitem,sindex) in schoolList" :key="sindex" class="u-padding-20 u-border-bottom"
					style="margin-bottom: 4rpx;" @click="selectSchool(sitem)">
					{{sitem.name}}
				</view>
			</view>
		</view>
	</u-popup>
</template>
<script>
	export default {
		name:"chooseSchool",
		props: {
			show: {
				type: Boolean,
				default: false
			},
		},
		data() {
			return {
				regionList: [],
				schoolList: [],
			}
		},
		computed: {
			boxshow: {
				get() {
					return this.show
				},
				set(newValue) {
					this.$emit('update:show', newValue)
				}
			}
		},
		created() {
			this.getSchoolList();
		},
		methods: {
			getSchoolList: async function() {
				if (this.regionList.length == 0) {
					var res = await this.$apis.getSchoolList();
					this.regionList = res.data.list
					this.schoolList = this.regionList[0] ? this.regionList[0].school : []
				}
			},
			getSchool(index) {
				this.schoolList = this.regionList[index].school
			},
			selectSchool(item) {
				this.boxshow = false
				this.$emit("onchange", item)
			},
		}
	};
</script>
<style lang="scss" scoped>
	
</style>
