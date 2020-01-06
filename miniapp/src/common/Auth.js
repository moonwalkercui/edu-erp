import wepy from 'wepy'
import Tips from './Tips'
import Api from './Api'
export default class Auth {
  // 认证页用到
  static async login(userinfo) {
    let res = await wepy.login()
    if (res.errMsg === 'login:ok' && res.code) {
      // wepy.setStorageSync('loginCode', res.code)
      await Api.login(res.code);
      await Api.updateUserProfile(userinfo);
    } else {
      Tips.alert('登录失败,请退出重试!')
      return null
    }
  }
  // 检查登录状态 其他验证登录状态的页面用到
  static async checkSession() {
    wepy.checkSession({
      success: (r)=>{
        console.log('session未过期')
      },
      fail: ()=>{
        console.log('session过期')
        wepy.redirectTo({ url: 'authorize' });
      }
    })
  }
  // 获取token
  static getToken() {
    return wepy.getStorageSync('token')
  }
  static getUserInfo() {
    return wepy.getStorageSync('userInfo')
  }
  // static hasMobile() {
  //   var info = getUserInfo();
  //   return info.mobile != ''
  // }
}
