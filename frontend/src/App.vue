<template>
    <div id="app">
        <transition name="fade"
                    mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>
<script>
  export default {
    name: 'app'
  }
</script>
<style lang="scss">
    @import './assets/public';
    #app {
        position: absolute;
        top: 0px;
        bottom: 0px;
        width: 100%;
    }

    .el-menu-content .el-menu-item {
        height: 40px;
        line-height: 40px;
    }

    .el-table__expanded-cell {
        background: #f4f4f5 !important;
        padding: 5px !important;
        font-size: 12px;
    }

    .el-table__expanded-cell th {
        font-weight: normal !important;
    }
</style>
