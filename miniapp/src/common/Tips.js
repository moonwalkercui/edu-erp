import wepy from 'wepy'
export default class Tips {
  static alert(text, cb, icon = 'none') {
    wepy.showToast({
      title: text,
      icon: icon,
      duration: 2500
    })
    if (cb && typeof cb === 'function') {
      setTimeout(() => {
        cb();
      }, 1000);
    }
  }
  static success(title, cb) {
    wepy.showToast({
      title: title,
      icon: "success",
      mask: true,
      duration: 1000
    });
    if (cb && typeof cb === 'function') {
      setTimeout(() => {
        cb();
      }, 1000);
    }
  }
  static error(title, cb) {
    wepy.showToast({
      title: title,
      image: "../images/error.png",
      mask: true,
      duration: 1000
    });
    // 隐藏结束回调
    if (cb && typeof cb === 'function') {
      setTimeout(() => {
        onHide();
      }, 1000);
    }
  }
  static async confirm(title, content = '', cb_ok, cb_cancle, confirmText='确定', cancelText='取消') {
    let res = await wepy.showModal({
      title: title,
      content: content,
      confirmText: confirmText,
      cancelText: cancelText,
    })
    if (res.confirm) {
      cb_ok()
    } else {
      cb_cancle && cb_cancle()
    }
  }
  static async loading(title = '加载中...') {
    wepy.showLoading({ title: title })
  }
  static async hideLoading() {
    wepy.hideLoading()
  }
}
