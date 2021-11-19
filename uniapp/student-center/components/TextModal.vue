<template>
	<view style="u-m-t-40 u-m-b-40">
		<u-modal v-model="boxshow" :title="info.title" :mask-close-able="true" confirm-text="关 闭" width="90%"
			:confirm-style="{fontSize: '28rpx'}" :title-style="{paddingTop: '30rpx'}">
			<view class="slot-content">
				<view class="u-padding-20">
					<rich-text :nodes="info.content" ></rich-text>
				</view>
			</view>
		</u-modal>
	</view>
</template>
<script>
	export default {
		name: "TextModal",
		props: {
			show: {
				type: Boolean,
				default: false
			},
			code: {
				type: String,
				default: "term"
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
		data() {
			return {
				info: {
					title: '',
					content: ''
				}
			}
		},
		created() {
			this.getInfo()
		},
		methods: {
			getInfo() {
				this.$http.get('common/open/article', {
					code: this.code
				}, res => {
					if (!this.$common.handleResponseMsg(res)) return;
					this.info = res
				})
			},
		}
	};
</script>
