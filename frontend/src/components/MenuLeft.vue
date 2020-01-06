<template>
    <aside class="aside-left" :class="isCollapse?'menu-collapsed':'menu-expanded'">
        <vue-scroll :ops="ops">
            <el-menu :default-active="activeMenu" :collapse="isCollapse" @select="selectMenu" class="el-menu-left"
                     :unique-opened="true"
                     background-color="#F2F6FC" text-color="#303133" active-text-color="#fff">
                <template v-for="(item, index) in menus">
                    <el-submenu :index="item.title" :key="index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title"> {{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item._child">
                            <!-- <el-badge is-dot class="menu-badge" :key="subItem.id"> -->
                                <el-menu-item :index="subItem.url" :key="subItem.title"> {{ subItem.title }}
                                </el-menu-item>
                            <!-- </el-badge> -->
                        </template>
                    </el-submenu>
                </template>
            </el-menu>
            <el-row style="text-align: center; margin:5px;">
                <el-button round size="mini" icon="el-icon-d-arrow-right" v-show="isCollapse" @click="toggleSider"
                           class="collapse-button"></el-button>
                <el-button round size="mini" icon="el-icon-d-arrow-left" v-show="!isCollapse" @click="toggleSider"
                           class="collapse-button"></el-button>
            </el-row>
        </vue-scroll>
    </aside>
</template>
<script>
  import {find} from 'lodash'

  export default {
    props: ['parent', 'items', 'activeMenu'],
    data() {
      return {
        isCollapse: false,
//                activeMenu: '',
        urlParam: {},
        ops: {
          scrollPanel: {
            scrollingX: false,
          },
          bar: {
            background: '#c1c1c1',
            opacity: 0.5,
          }
        }
      }
    },
    computed: {
      menus() {
        var parentMenu = find(this.items, {id: this.parent});
        if (typeof parentMenu != "undefined") return parentMenu._child;
        else return [];
      }
    },
    mounted() {
//            this.activeMenu = this.$route.path;
    },
    methods: {
      toggleSider() {
        this.isCollapse = !this.isCollapse;
      },
      selectMenu(index) {
        // 增加动态菜单
        this.$router.push(index);
      }
    }
  };
</script>
<style lang="scss" scope>
    .collapse-button {
        background-color: #fff;
        color: #8c8c8c;
        border: none;
    }

    .aside-left {
        background-color: #F2F6FC;
        overflow-x: hidden;
        box-shadow: 0px 0px 8px #C0C4CC;
        border-radius: 5px;
        margin: 10px;
        &.menu-expanded {
            flex: 0 0 200px;
            width: 200px;
        }
        &.menu-collapsed {
            flex: 0 0 60px;
            width: 60px;
        }
        .el-menu-left {
            border: none;
            width: 100%;
            /*background: #eef1f6;*/
            .el-submenu__title {
                height: 50px;
                line-height: 50px;
                i {
                    color: #2f3847;
                }
            }
            .fa {
                display: inline-block;
                width: 20px;
                text-align: center;
            }
            .el-menu-item.is-active {
                font-weight: bold;
                color: #fff;
                background: #525fe1 !important;
            }
            .el-submenu__title:hover, .el-menu-item:hover {
                border-radius: 4px;
            }
            .el-menu-item {
                border-radius: 4px;
            }
            .el-submenu .el-menu-item {
                line-height: 40px;
                height: 40px;
            }
        }
    }

    .menu-badge .el-badge__content.is-fixed.is-dot {
        top: 10px;
        right: 20px;
    }
</style>