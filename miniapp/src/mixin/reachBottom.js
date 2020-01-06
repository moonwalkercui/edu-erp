import wepy from 'wepy'
// import Tips from '../common/Tips';
// data里需要放list:[] , 页面里必须有一个handleRequest()
export default class ReachBottom extends wepy.mixin {
  data = {
    list: [],
    last_page: 1,
    params: {
      page: 1,
      // per_page: 6,
    }
  };
  // 下拉刷新 需要在页面的config参数里增加 enablePullDownRefresh: true, 否则下拉无效
  onPullDownRefresh() {
    this.params.page = 1;
    this.list = [];
    this.$apply();
    this.handleRequest()
  }
  onReachBottom() {
    if (this.last_page > this.params.page) {
      this.params.page ++;
      this.$apply()
      this.handleRequest()
    // } else {
    //   Tips.alert('没有更多了')
    }
  }
 
}