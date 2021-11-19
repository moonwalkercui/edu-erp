<template>
  <view class="banner-container">
    <swiper :style="{width: '100vw', height: '410rpx'}" 
      :indicator-dots="swiperConfig.indicatorDots" 
      :indicator-color="swiperConfig.indicatorColor" 
      :indicator-active-color="swiperConfig.indicatorActiveColor"
      :autoplay="swiperConfig.autoplay" 
      :interval="swiperConfig.interval" 
      :duration="swiperConfig.duration" 
      :circular="swiperConfig.circular"
      :previous-margin="swiperConfig.previousMargin"
      :next-margin="swiperConfig.nextMargin"
      @change="swiperChange" 
      @animationfinish="animationfinish">
      <swiper-item v-for="(item, i) in bannerList" :key="i">
		<!-- 1.当前展示为第1项时，bannerList最后一项和第二项的justifyContent值分别为flex-end和flex-start，其余项值为center -->
		<!-- 2.当前展示为最后一项时，bannerList倒数第2项和第1项的justifyContent值分别为flex-end和flex-start，其余项值为center -->
		<!-- 3.当前展示为其他项（非第1和最后1项）时，bannerList当前项的前1项和后1项的justifyContent值分别为flex-end和flex-start，其余项值为center -->
		<!-- 4.padding值也需要根据不同项设定不同值，但理同justifyContent -->
        <view class="image-container" 
			:class="[curIndex===0?((i===listLen-1)?'item-left':(i===1?'item-right':'item-center')):(curIndex===listLen-1?(i===0?'item-right':(i===listLen-2?'item-left':'item-center')):(i===curIndex-1?'item-left':(i===curIndex+1?'item-right':'item-center')))]">
          <image :src="item.cover" 
            class="slide-image" 
            :style="{
              transform: curIndex===i?'scale(' + scaleX + ',' + scaleY + ')':'scale(1,1)',
              transitionDuration: '.3s',
              transitionTimingFunction: 'ease'
            }" 
            @click="getBannerDetail(item)"/>
        </view>
      </swiper-item>
    </swiper>
    <view class="desc-wrap" :class="[isDescAnimating?'hideAndShowDesc':'']">
      <view class="title" v-if="bannerList[descIndex]">{{bannerList[descIndex].title}}</view>
    </view>
  </view>
</template>
<script>
export default {
  props: {
	bannerList: {
		type: Array,
		default () {
			return []
		}
	},
	swiperConfig: {
		type: Object,
		default () {
			return {
				indicatorDots: true,
				indicatorColor: 'rgba(255, 255, 255, .4)',
				indicatorActiveColor: 'rgba(255, 255, 255, 1)',
				autoplay: true,
				interval: 3000,
				duration: 300,
				circular: true,
				previousMargin: '58rpx',
				nextMargin: '58rpx'
			}
		}
	},
	scaleX: {
		type: String,
		default: (634 / 550).toFixed(4)
	},
	scaleY: {
		type: String,
		default: (378 / 328).toFixed(4)
	}
  },
  computed:{
	listLen () {
		return this.bannerList.length
	}
  },
  data () {
    return {
      curIndex: 0,
      descIndex: 0,
      isDescAnimating: false
    }
  },
  methods: {
    swiperChange (e) {
      const that = this
      this.curIndex = e.mp.detail.current
      this.isDescAnimating = true
      let timer = setTimeout(function () {
        that.descIndex = e.mp.detail.current
        clearTimeout(timer)
      }, 150)
    },
    animationfinish (e) {
      this.isDescAnimating = false
    },
    getBannerDetail (item) {
		this.$emit("showDetail", item)
    }
  }
}
</script>
<style lang="scss" scoped>
.banner-container {
  width: 100vw;
  height: 480rpx;
  .image-container {
	box-sizing: border-box;
	width: 100%;
	height: 100%;
	display: flex;
	
	.slide-image {
	  width: 550rpx;
	  height: 328rpx;
	  z-index: 200;
	  border-radius: 30rpx;
	}
  }
  .item-left {
	justify-content: flex-end;
	padding: 56rpx 26rpx 0 0;
  }
  .item-right {
	justify-content: flex-start;
	padding: 56rpx 0 0 26rpx;
  }
  .item-center {
	justify-content: center;
	padding: 56rpx 0 0 0;
  }
  .desc-wrap {
    box-sizing: border-box;
    width: 100%;
    // height: 98rpx;
    padding: 24rpx 66rpx 0;
    .title {
      width: 100%;
      height: 42rpx;
      line-height: 42rpx;
      color: #4d7df9;
      font-size: 30rpx;
      font-family: 'PingFangTC-Regular';
      font-weight: 600;
      text-align: left;
    }
    // .desc {
    //   margin-top: 4rpx;
    //   width: 100%;
    //   height: 34rpx;
    //   line-height: 34rpx;
    //   color: #999999;
    //   font-size: 24rpx;
    //   font-family: 'PingFangTC-Regular';
    //   text-align: left;
    // }
  }
  @keyframes descAnimation {
    0% {
      opacity: 1;
    }
    25% {
      opacity: .5;
    }
    50% {
      opacity: 0;
    }
    75% {
      opacity: .5;
    }
    100% {
      opacity: 1;
    }
  }
  @-webkit-keyframes descAnimation {
    0% {
      opacity: 1;
    }
    25% {
      opacity: .5;
    }
    50% {
      opacity: 0;
    }
    75% {
      opacity: .5;
    }
    100% {
      opacity: 1;
    }
  }
  .hideAndShowDesc {
    animation: descAnimation .3s ease 1;
    -webkit-animation: descAnimation .3s ease 1;
  }
}
</style>
