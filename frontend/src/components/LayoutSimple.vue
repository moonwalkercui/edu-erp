<template>
    <el-container class="main-box">
        <el-header style="padding: 0">
            <el-col class="header-s" :span="24">
                <el-col :span="10" class="logo logo-width" @click.native="$router.push('/')">
                    小猿管家
                </el-col>
                <el-col :span="10">
                    <el-menu class="el-menu-top" mode="horizontal" background-color="#525fe1" text-color="#fff" active-text-color="#fff">
                        <el-menu-item index="jkchat" @click="$router.push('/jkchat')">JkChat</el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="4" class="userinfo">
                    <el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner">
					<img :src=avartarUrl /> {{uname}}
					</span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="$router.push('/i/dashboard')">返回首页</el-dropdown-item>
                            <el-dropdown-item @click.native="$router.push('/user/me')">个人资料</el-dropdown-item>
                            <!--<el-dropdown-item divided @click.native="$router.push('/service/createOrJoin')">创建或加入组织</el-dropdown-item>-->
                            <el-dropdown-item divided @click.native="handleLogout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
            </el-col>
        </el-header>
        <el-container class="main">
            <el-container>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
        <el-footer>Footer</el-footer>
    </el-container>
</template>
<script>
    import avatar from '@/assets/avatar_def.jpg'
    export default {
        data() {
            return {
                avartarUrl : this.$cookie.fetchJson('_userInfo').avatar || avatar,
                uname : this.$cookie.fetchJson('_userInfo').name,
            }
        },
        methods: {
            selectMenu(index) {
                this.$router.push(index);
            },
            handleLogout()
            {
                this.$confirm('确认退出吗?','提示',{
                    type: 'warning'
                }).then(() => {
                    this.$http.fetch('auth/logout',{_token: 1212}).then((res) => {
                        this.$message.success(res.msg);
                        this.$cookie.delCookie('_token');
                        this.$cookie.delCookie('_userInfo');
                        this.$router.push('/');
                    });
                }).catch(() => {
                });
            }
        }
    };
</script>
<style lang="scss">
    .main-box {
        display: flex;
        position: absolute;
        height: 100%;
        width: 100%;
    }
    .header-s {
        flex-grow: 0;
        height: 50px;
        line-height: 50px;
        background-color: #525fe1;
        color: #fff;
        box-shadow: 1px 1px 5px #ddd;

        .logo {
            height: 50px;
            font-size: 22px;
            padding-left: 20px;
            padding-right: 20px;
            cursor: pointer;
            text-align: center;
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
    .el-footer {
        background-color: #F2F6FC;
        color: #909399;
        line-height: 60px;
    }
</style>