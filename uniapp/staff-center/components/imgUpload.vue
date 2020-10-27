<template>
	<view class="cu-form-group padding-top-sm">
		<view class="grid col-4 grid-square flex-sub">
			<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
			 <image :src="imgList[index]" mode="aspectFill"></image>
				<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
					<text class='cuIcon-close'></text>
				</view>
			</view>
			<view class="solids" @tap="ChooseImage" v-if="imgList.length<max">
				<text class='cuIcon-cameraadd'></text>
			</view>
		</view>
	</view>
</template>

<script>
	import * as api from "@/common/api.js"
	import {fixImgUrl} from "@/common/util.js"
	export default {
		props:{
			max: {
				type: Number,
				default: 9
			},
			list: {
				type: Array,
				default: ()=>{
					return []
				}
			}
		},
		data() {
			return {
				imgList: [],
			}
		},
		created() {
			if(this.list.length > 0) this.imgList = this.list
		},
		methods:{
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '删除确认',
					content: '确定要删除?',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			ChooseImage() {
				// uni.chooseImage({
				// 	count: 4, //默认9
				// 	sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
				// 	sourceType: ['album'], //从相册选择
				// 	success: (res) => {
				// 		if (this.imgList.length != 0) {
				// 			this.imgList = this.imgList.concat(res.tempFilePaths)
				// 		} else {
				// 			this.imgList = res.tempFilePaths
				// 		}
				// 	   
				// 	}
				// });
				
				api.uploadFiles(res => {
					// console.log('res')
					this.imgList.push(fixImgUrl(res.data.url))
					uni.$emit('choosedImg', res.data.url)
				})
				
			},
		}
	}
</script>

<style>
</style>
