<template>
	<u-upload ref="uUpload" :action="action" :header="header" :file-list="fileList"
		@on-success="uploadSuccess" @on-error="uploadError" @on-remove="onRemove" @on-uploaded="onUploaded"
		:max-size="10 * 1024 * 1024" :max-count="limit" :auto-upload="true"></u-upload>
</template>

<script>
	import { baseUrl } from "@/util/config"
	export default {
		props: {
			limit: {
				type: Number,
				default: 1
			}
		},
		data() {
			return {
				action: baseUrl + 'service/uploadImg',
				header: {},
				fileList: [],
				uploadList: [],
			}
		},
		created() {
			this.getHeader();
		},
		methods: {
			getHeader() {
				this.header = this.$common.getRequestHeader()
			},
			uploadSuccess(data, index, lists, name) {
				this.makeUploadList(lists)
			},
			onRemove(index, lists, name) {
				this.makeUploadList(lists)
			},
			onUploaded(lists, name) {
				this.makeUploadList(lists)
			},
			makeUploadList(lists) {
				
				var fileList = [];
				for (var res of lists) {
					if (res.response) {
						if (res.response.code == 0) {
							this.$common.showMsg(res.response.msg)
							return;
						}
						fileList.push(res.response.data)
					}
				}
				this.uploadList = fileList;
				// console.log("uploadlist", this.uploadList)
				this.$emit('uploaded', this.uploadList)
			},
			uploadError(res) {
				this.$common.showMsg("上传出错")
			},
		
		}
	}
</script>

<style>
</style>
