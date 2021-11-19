<template>
	<u-popup v-model="boxshow" mode="right" width="600">
		<view class="u-flex" style="height:100%;">
			<view style="height:100%; flex: 1; background-color: #fff;">
				<view v-for="(citem,cindex) in classList" :key="cindex" class="u-padding-20 u-border-bottom"
					style="margin-bottom: 4rpx;" @click="selectClass(citem)">
					{{citem.name}}
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
			schoolId: {
				type: Number | String,
				default: false
			}
		},
		data() {
			return {
				classList: [],
			}
		},
		computed: {
			boxshow: {
				get() {
					if(this.show) {
						this.getClassList()
					}
					return this.show
				},
				set(newValue) {
					this.$emit('update:show', newValue)
				}
			}
		},
		created() {
		},
		methods: {
			getClassList() {
				this.$http.get('school/classList', {
					school_id: this.schoolId
				}, res => {
					this.classList = res.data
				})
			},
			selectClass(item) {
				this.$emit("onchange", item)
			},
		}
	};
</script>
<style lang="scss" scoped>
	
</style>
