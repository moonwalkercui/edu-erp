<template>
    <el-row class="container">
        <menu-top :items="menus" @changeSubMenu="handleChangeSubMenu"></menu-top>
        <el-col class="main" :span="24">
            <menu-left :parent="menuParent" :items="menus" :active-menu = "$route.path"></menu-left>
            <section class="content-container" id="scrollDiv">
                <div class="nav-box">
                    <el-row >
                        <el-tooltip class="item" effect="light" content="关闭全部" placement="bottom">
                            <el-button type="text" style="margin: 2px; float: right" ><i class="el-icon-close" @click="closeAllTab"></i></el-button>
                        </el-tooltip>
                        <el-tabs v-model="$store.state.tabActive" class="j-tabs" style="padding-right: 30px"
                                 type="card" closable @tab-remove="removeTab" @tab-click="changeTab">
                            <el-tab-pane v-for="item in $store.state.tabList" class="el-tabs-tab"
                                         :key="item.path" :label="item.title" :name="item.path">
                            </el-tab-pane>
                        </el-tabs>
                    </el-row>
                </div>
                <div class="content-box">
                    <!--<vue-scroll :ops="ops">-->
                    <el-row class="page-title-box" id="navBar" v-if="$route.meta.title">
                        <el-col :span="12"><h2>  {{ $route.meta.title }}</h2></el-col>
                        <!--<el-col :span="12"><h2><i class="el-icon-menu"></i> {{ $route.meta.title }}</h2></el-col>-->
                        <el-col :span="12">
                            <!--<el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb-inner" v-if="$route.matched[0].name!=''">-->
                                <!--<el-breadcrumb-item v-for="item in $route.matched" :key="item.path" :to="{path: item.path==''?'/':item.path}">-->
                                    <!--{{ item.name }}-->
                                <!--</el-breadcrumb-item>-->
                            <!--</el-breadcrumb>-->
                            <el-breadcrumb separator-class="el-icon-arrow-right" class="j-breadcrumb">
                                <el-breadcrumb-item :to="{ path: '/i/dashboard' }">首页</el-breadcrumb-item>
                                <el-breadcrumb-item v-if="$route.matched[0].meta.title">{{ $route.matched[0].meta.title}}
                                </el-breadcrumb-item>
                                <el-breadcrumb-item v-if="$route.meta.title">{{ $route.meta.title }}
                                </el-breadcrumb-item>
                            </el-breadcrumb>
                        </el-col>
                    </el-row>

                    <el-col :span="24" class="content-wrapper">
                        <transition name="fade" mode="out-in">
                            <router-view></router-view>
                        </transition>
                    </el-col>
                    <NoticeBox/>
                    <!--</vue-scroll>-->
                </div>
            </section>
        </el-col>
    </el-row>
</template>

<script>
    import MenuTop from '@/components/MenuTop'
    import MenuLeft from '@/components/MenuLeft'
    import NoticeBox from '@/components/NoticeBox'
    export default {
        data() {
            return {
                menus: [],
                tabIndex: 1,
                urlParam : {},
                menuParent: 1,
                username: '',
                auto_fixed: '',
                ops: {
                    scrollPanel: {
                        // scrollingX: false,
                    },
                    bar: {
                        vBar: {
                            background: "#999",
                        },
                        hBar: {
                            background: "#999",
                        }
                    }
                }
            }
        },
        components : { MenuTop , MenuLeft, NoticeBox},
        created() {
            this.getMenus();
            this.updateTab();
            this.handelActiveTopMenu();
        },
        watch: {
            // 如果路由有变化，会再次执行该方法
            '$route': 'handelActiveTopMenu'
        },
        methods: {
            
            handelActiveTopMenu(){
                this.menuParent = this.$route.meta.topMenu;
            },
            removeTab(targetName) {
                this.$util.removeNavTab(targetName);
                this.updateTab();
//                this.$store.commit('removeTab',targetName);
                this.$router.push({ path: '/i/dashboard' });
            },
            changeTab: function(el) {
                this.$router.push({ path: el.name });
            },
            closeAllTab: function() {
                this.$util.initNavTab();
                this.$router.push({ path: '/i/dashboard' });
                this.$store.commit('refreshTab');
            },
            getMenus() {
//                this.$cookie.delCookie('menu_list'); // todo 正式发布的时候去掉
//                if(this.$cookie.fetchJson('menu_list')){
//                    this.menus = this.$cookie.fetchJson('menu_list');
//                }else{
                    this.$http.fetch("sys/leftmenu",this.urlParam).then((res) =>{
                        this.$cookie.setJson('menu_list',res.data);
                        this.menus = res.data;
                    });
//                }
            },
            updateTab(){
                this.$store.commit('refreshTab');
            },
            handleChangeSubMenu(id){
//                console.log('父级',id);
                this.menuParent = id;
            },
        }
    };
</script>
<style lang="scss" rel="stylesheet/scss">

    /*@import '../assets/public';*/
    .container{
        display: flex;
        height: 100%;
        flex-direction: column;
        background-color: #fff;
        .main{
            flex: auto;
            overflow:auto;
            height: 100%;
            display: flex;
            .content-container{
                flex: auto;
                overflow:auto;
                display: flex;
                flex-direction: column;
                .nav-box{
                    flex-grow: 0;
                    height: 40px;
                    margin-top: 10px;
                    padding: 0 10px;
                }
                .content-box{
                    height: 100%;
                    overflow:auto;
                    .page-title-box{
                        margin-top: 15px;
                        padding:0 20px;
                        box-sizing: border-box;
                        font-size: 14px;
                        h2 {
                            font-size: 20px;
                            margin: 0;
                            font-weight: normal;
                            display: inline-block;
                        }
                        .j-breadcrumb {
                            float: right;
                            margin-top: 5px;
                            font-weight: 100;
                        }
                    }
                    .content-wrapper {
                        padding: 20px;
                    }
                }
                .breadcrumb-container{
                    flex: 1;
                    line-height:25px;
                    height:25px;

                    .title{
                        max-width: 300px;
                        float: left;
                    }
                    .breadcrumb-inner{
                        float: right;
                        line-height:25px;
                        height:25px;
                    }

                }
            }
        }
    }
    #app {
        top: 0;
        bottom: 0;
    }
    .fade-enter-active,
    .fade-leave-active {
        transition: all .2s ease;
    }
    .fade-enter,
    .fade-leave-active{
        opacity: 0;
    }
    /*// 滚动条*/
    /*::-webkit-scrollbar {*/
        /*width: 5px;*/
        /*padding-right: 4px;*/
        /*background-color: #EBEEF5;*/
        /*-webkit-border-radius: 4px;*/
        /*border-radius: 4px;*/
        /*!*其他样式，比如圆角等*!*/
    /*}*/

    /*!*滑块样式*!*/
    /*::-webkit-scrollbar-thumb {*/
        /*-webkit-border-radius: 8px;*/
        /*border-radius: 8px;*/
        /*background-color: #DCDFE6;*/
    /*}*/

    /*!*当前窗口失去焦点时的滑块样式*!*/
    /*::-webkit-scrollbar-thumb:window-inactive {*/
        /*background-color: #fff;*/
    /*}*/
</style>