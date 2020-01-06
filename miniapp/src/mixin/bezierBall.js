import wepy from 'wepy'
export default class BezierBall extends wepy.mixin {
  data = {
    /* 抛物线 */
    finger: {},
    busPos: {
      x: 0,
      y: 0
    },
    goodsH: 0,
    linePos: {},
    hide_good_box: true,
    bus_x: 0,
    bus_y: 0,
  };
   /* 抛物线 */
   touchOnGoods(e) {
    
    var topPoint = { x: 0, y: 0 };
    var finger = { x: 0, y: 0 };

    finger.x = e.touches["0"].clientX;//点击的位置
    finger.y = e.touches["0"].clientY;

    if (finger.y < this.busPos.y) {
      topPoint.y = finger.y - 150;
    } else {
      topPoint.y = this.busPos.y - 150;
    }
    topPoint.x = Math.abs(finger.x - this.busPos.x) / 2;

    if (finger.x > this.busPos.x) {
      topPoint.x = (finger.x - this.busPos.x) / 2 + this.busPos.x;
    } else {//
      topPoint.x = (this.busPos.x - finger.x) / 2 + finger.x;
    }

    this.linePos = this.bezier([this.busPos, topPoint, finger], 30);
    this.hide_good_box = false
    this.bus_x = finger.x
    this.bus_y = finger.y
    this.finger = finger;
    this.$apply();
    this.startAnimation(e);
  }
  startAnimation (e) {
    var index = 0, that = this,
      bezier_points = this.linePos['bezier_points'];
    // console.log(bezier_points);return;
    var len = bezier_points.length;
    index = len
    let timer = setInterval(() => {
      for(let i = index - 1; i > -1; i--) {
          this.bus_x = bezier_points[i]['x'],
          this.bus_y = bezier_points[i]['y']
        if (i < 1) {
          clearInterval(timer);
          // 在这里执行加入购物车
          this.hide_good_box = true
        }
        this.$apply();
      }
    }, 25);
  }
  bezier (pots, amount) {
    var pot;
    var lines;
    var ret = [];
    var points;
    for (var i = 0; i <= amount; i++) {
      points = pots.slice(0);
      lines = [];
      while (pot = points.shift()) {
        if (points.length) {
          lines.push(pointLine([pot, points[0]], i / amount));
        } else if (lines.length > 1) {
          points = lines;
          lines = [];
        } else {
          break;
        }
      }
      ret.push(lines[0]);
    }
    function pointLine(points, rate) {
      var pointA, pointB, pointDistance, xDistance, yDistance, tan, radian, tmpPointDistance;
      var ret = [];
      pointA = points[0];//点击
      pointB = points[1];//中间
      xDistance = pointB.x - pointA.x;
      yDistance = pointB.y - pointA.y;
      pointDistance = Math.pow(Math.pow(xDistance, 2) + Math.pow(yDistance, 2), 1 / 2);
      tan = yDistance / xDistance;
      radian = Math.atan(tan);
      tmpPointDistance = pointDistance * rate;
      ret = {
        x: pointA.x + tmpPointDistance * Math.cos(radian),
        y: pointA.y + tmpPointDistance * Math.sin(radian)
      };
      return ret;
    }
    return {
      'bezier_points': ret
    };
  }
}