<template>
  <el-col class="header" :span="24">
    <el-col :span="10" class="logo logo-width">
      <img :src="logoImg"/>
      小猿管家
      </el-col>
    <el-col :span="10">
      <el-menu
        class="el-menu-top"
        mode="horizontal"
        background-color="#525fe1"
        text-color="#fff"
        active-text-color="#fff"
      >
        <template v-for="item in items">
          <template>
            <el-menu-item
              :index="item.title"
              @click="changeSubMenu(item.id)"
              :key="item.id"
            >{{item.title}}</el-menu-item>
          </template>
          <!-- <template v-else-if="item.position == 2">
            <el-submenu :index="item.title" :key="item.id">
              <template slot="title">{{item.title}}</template>
              <template v-for="subItem in item._child" v-if="item._child.length !=0">
                <el-menu-item index="'index'+subItem.id" :key="subItem.id">{{ subItem.title }}</el-menu-item>
              </template>
            </el-submenu>
          </template>
          <template v-else-if="item.position == 3">
            <el-menu-item
              :index="item.title"
              @click="jumpUrl(item.url)"
              :key="item.id"
            >{{item.title}}</el-menu-item>
          </template>
          <template v-else> -->
            <!-- <el-menu-item :index="item.title" :key="item.id"><a href="https://ccccccccccccc.me" target="_blank">{{item.title}}</a></el-menu-item> -->
          <!-- </template> -->
        </template>
        <el-menu-item index="qrcode" @click="showQr" key="qrcode">小程序码</el-menu-item>
        <!-- <el-menu-item index="jkchat" @click="$router.push('/jkchat')">JkChat</el-menu-item> -->
      </el-menu>
    </el-col>
    <el-col :span="4" class="userinfo">
  
      <el-dropdown trigger="hover">
        <span class="el-dropdown-link userinfo-inner">
          <img :src="avartarUrl">
          {{name}}
        </span>
        <el-dropdown-menu slot="dropdown">
          <!-- <el-dropdown-item @click.native="$router.push('/user/me')">个人资料</el-dropdown-item> -->
          <!-- <el-dropdown-item @click.native="$router.push('/service/switchover')">切换组织</el-dropdown-item> -->
          <el-dropdown-item @click.native="sendAdvice">使用反馈</el-dropdown-item>
          <el-dropdown-item divided @click.native="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-col>
    <Advice :visible.sync="adviceDialog"/>
    <Qrcode :visible.sync="qrcodeDialog"/>
  </el-col>
  <!--<el-menu class="el-menu-top" mode="horizontal" @select="selectMenu" :default-active="activeIndex"-->
  <!--background-color="#2f3847" text-color="#fff" active-text-color="#69baee">-->
  <!--<el-menu-item index="logo">测试框架</el-menu-item>-->
  <!--<el-submenu index="about" style="float: right">-->
  <!--<template slot="title">-->
  <!--&lt;!&ndash;<img src="favicon.ico" style="height: 40px; width: 40px;border-radius: 20px;"/>&ndash;&gt;-->
  <!--<span>user</span>-->
  <!--</template>-->
  <!--<el-menu-item @click="$router.push('/service/switchover')" index="/service/switchover">switchover</el-menu-item>-->
  <!--<el-menu-item @click="$router.push('/service/createOrJoin')" index="/service/createOrJoin">createOrJoin</el-menu-item>-->
  <!--<el-menu-item @click="$router.push('/login')" index="/login">login</el-menu-item>-->
  <!--<el-menu-item @click="handleLogout" index="logout">退出</el-menu-item>-->
  <!--</el-submenu>-->
  <!--<el-menu-item index="jkchat" style="float: right" @click="$router.push('/jkchat')">JkChat</el-menu-item>-->
  <!--</el-menu>-->
</template>
<script>
// import _ from "lodash";
import avatar from "@/assets/avatar_def.jpg"
import logo from "@/assets/logo-single.jpg"
import Advice from '@/components/Advice'
import Qrcode from '@/components/Qrcode'
export default {
  components: { Advice, Qrcode },
  props: ["items"],
  data() {
    return {
      isCollapse: false,
      activeMenu: "",
      urlParam: {},
      avartarUrl: this.$cookie.fetchJson("_userInfo").avatar || avatar,
      name: this.$cookie.fetchJson("_userInfo").name,
      adviceDialog: false,
      qrcodeDialog: false,
      logoImg: logo,
    };
  },
  // computed: {
  //   activeIndex() {
  //     var parentId = this.$route.meta.topMenu;
  //     if (parentId) {
  //       var parent = _.find(this.data, { id: parentId });
  //       return typeof parent != "undefined" ? parent.title : "";
  //     }
  //   }
  // },
  created() {},
  methods: {
    toggleSider() {
      this.isCollapse = !this.isCollapse;
    },
    //            selectMenu() {
    //                // 增加动态菜单
    ////                this.$router.push(index);
    //            },
    changeSubMenu(id) {
      this.$emit("changeSubMenu", id);
    },
    handleLogout() {
      this.$confirm("确认退出吗?", "提示", {
        type: "warning"
      })
        .then(() => {
          this.$http.fetch("auth/logout", { _token: 1212 }).then(res => {
            this.$message.success(res.msg);
            this.$cookie.delCookie("_token");
            this.$cookie.delCookie("_userInfo");
            // this.$cookie.delCookie("api_prefix");
            this.$router.push("/");
          });

          //                    sessionStorage.removeItem('user');
          //                    this.$router.push({path:'/login'})
        })
        .catch(() => {});
    },
    sendAdvice() {
      this.adviceDialog = true;
    },
    showQr() {
      this.qrcodeDialog = true;
    },
    jumpUrl(url) {
      window.open(url, "_blank");
    }
  }
};
</script>
<style lang="scss">
.header {
  flex-grow: 0;
  height: 52px;
  overflow: hidden;
  line-height: 52px;
  background-color: #525fe1; // 525fe1
  color: #fff;
  box-shadow: 1px 1px 5px #ddd;
  .logo {
    height: 52px;
    font-size: 22px;
    padding-left: 20px;
    padding-right: 20px;
    cursor: pointer;
    text-align: center;
 
    *{
   vertical-align: middle;
    }
    img{
      border-radius: 50%;
      width: 30px;
      height: 30px;
    }
  }
  .logo-width {
    width: 220px;
  }
  .logo-collapse-width {
    width: 50px;
  }
  .tools {
    padding: 0 23px;
    /*width:14px;*/
    cursor: pointer;
  }
  .userinfo {
    text-align: right;
    padding-right: 35px;
    float: right;
    .userinfo-inner {
      cursor: pointer;
      color: #fff;
      img {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        margin: 10px 0px 10px 10px;
        float: right;
      }
    }
  }
}
</style>