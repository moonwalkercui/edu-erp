import wepy from 'wepy'
export default class ScrollRefresh extends wepy.mixin {
  onPullDownRefresh() {
    this.$broadcast('reload-list')
  }
  onReachBottom() {
    this.$broadcast('load-next-page')
  }
  // onShow() {
  //   this.$broadcast('reload-list')
  // }
}
