<template>
    <el-popover
        placement="bottom"
        :title="title"
        width="200"
        trigger="hover"
        :content="content"
        @show="handleRequest"
        :style="{float:float}"
        >
        <i class="el-icon-question help-icon" slot="reference"></i>
    </el-popover>
</template>

<script>
    // import axios from "axios";
    export default {
        props: {
            keyId: {
                default: ''
            },
            float: {
                default: 'none'
            }
        },
        data() {
            return {
                title: '帮助',
                content: '加载中...',
                timeKey: '',
            }
        },
        // watch: {
        //     key: function (val) {
        //         console.log('watch key', val)
        //     }
        // },
        methods: {
            handleRequest() {
                let info = this.$cookie.fetchJson("helper_" + this.keyId);
                if (info) {
                    this.title = info.title;
                    this.content = info.content;
                } else {
                    this.title = '帮助';
                    this.content = '加载中...';
                    // this.$fetch("normal/helper", {params:{key: this.keyId}}).then((res) => {
                    //     let data = res.data.data
                    //     if (res.data.status == 'success') {
                    //         this.title = data.title
                    //         this.content = data.content
                    //         this.$cookie.setJson("helper_" + this.keyId, data, 1);
                    //     }
                    // });
                    this.$http.fetch("normal/helper", {key: this.keyId}).then((res) => {
                        if (res.status == 'success') {
                            this.title = res.data.title
                            this.content = res.data.content
                            this.$cookie.setJson("helper_" + this.keyId, res.data, 1);
                        }
                    });
                }
            },
           
        }
    };
</script>
<style scope>
    .help-icon {
        cursor: pointer;
        color: #ff6600;
		font-size: 120%;
    }
</style>