import wepy from 'wepy'
// 用法 
// 1 定义view 定义为：
// <view class="fix-top-box" style="{{ fixTop<scrollTop ? fixBoxStyle : staticBoxStyle }}">
// 内容
// </view>
// 2 加入mixin：import fixTop from '../../mixin/fixTop' 和  mixins = [ fixTop ]
export default class FixTop extends wepy.mixin {
  data = {
    fixTop: 0,
    scrollTop: 0,
    showFiexTop: false,
    fixBoxStyle: 'position: fixed; top:0; left: 0; z-index: 99999; width: 100%;',
    staticBoxStyle: 'position: static;',
  }
  watch = {
    scrollTop(newval,oldvale) {
      this.showFiexTop = this.fixTop < newval;
      // console.log(this.showFiexTop)
      // this.$apply()
    }
  }
  onLoad () {
    wepy.createSelectorQuery().select('.fix-top-box').boundingClientRect( (rect) => {
      this.fixTop = rect.top
    }).exec()
  }
  onPageScroll (e) {
    this.scrollTop = e.scrollTop
    this.$apply()
  }
}