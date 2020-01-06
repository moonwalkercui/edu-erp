import wepy from 'wepy'
import Tips from './Tips'
import WxUtils from './WxUtils'

import Auth from './Auth'
function getDomain() {
  let prefix = wx.getStorageSync('orgcode');
  if (prefix == false) {
    wepy.redirectTo({ url: 'authorize' });
    throw new Error('缺少编号');
  } else {
    return 'https://域名/' + prefix + '/'; // prefix是组织编号
  }
}
export default class Api{
  static serviceDomain = 'https://域名/';
  // 后台根据code进行登录
  static async login(code) {
    let refid = wepy.getStorageSync('refid');
    let salid = wepy.getStorageSync('salid');
    let res = await Api.makeRequest('mauth/login', {code, refid, salid})
    if (res.status === 'success') {
      wepy.setStorageSync('sid', res.data.sid)
      wepy.setStorageSync('token', res.data.token)
      wepy.setStorageSync('profile', res.data.profile)
      let redirect_product_id = wepy.getStorageSync('redirect_product_id');
      // console.log('redirect_product_id', redirect_product_id)
      if (redirect_product_id) {
        wepy.redirectTo({ url: './product?id=' + redirect_product_id });
      } else {
        wepy.switchTab({ url: './home' });
      }
    }
  }
  // 验证码
  static captcha(randomCode) {
    return getDomain() + 'captcha/' + randomCode;
  }
  // 更新资料
  static async updateUserProfile(profile) {
    let params = {
      nick_name: profile.nickName,
      gender: profile.gender,
      city: profile.city,
      province: profile.province,
      avatar: profile.avatarUrl,
    }
    return await Api.makeRequest('mcenter/edit', params, 'POST') 
  }
  static getProfile(params) {
    return Api.makeRequest('mcenter/profile', params)
  }
  static collectFormIds(formIds) {
    return Api.makeRequest('mcenter/collectformids', {form_ids: formIds})
  }
  static editProfile(params) {
    return Api.makeRequest('mcenter/edit', params, 'POST')
  }
  static getPostUrl(productId) {
    return getDomain() + "mcenter/drawqrposter?product_id=" + productId + "&token=" +  Auth.getToken();
  }
  // user相关
  static async teacherLogin(params) {
    let res = await Api.makeRequest('mteacher/login', params)
    if (res.data.code) {
      wepy.setStorageSync('teacherCode', res.data.code)
      wepy.setStorageSync('teacherInfo', {
        name: res.data.name,
        avatar: res.data.avatar,
        uname: res.data.uname,
      })
      wepy.redirectTo({url: './scan'})
    }
  }
  static getPhoneNumber(params) {
    params.sid = wepy.getStorageSync('sid')
    return Api.makeRequest('mauth/getusermobile', params)
  }
  static bindPhoneNumber(params) {
    params.sid = wepy.getStorageSync('sid')
    return Api.makeRequest('mnormal/bindmobilebywx', params, 'POST')
  }
  static async homeData() {
    let res = await Api.makeRequest('mpage/homedata')
    if(res.status == 'success') {
      wepy.setStorageSync('orgInfo', res.data.org_info)
    }
    return res
  }
  static sendsmsode(params) {
    return Api.makeRequest('mnormal/sendsmsode', params, 'POST')
  }
  static bindmobile(params) {
    return Api.makeRequest('mnormal/bindmobile', params, 'POST')
  }
  static getScanLog(params) {
    return Api.makeRequest('mteacher/scanlog', params)
  }
  static getSignLog(params) {
    return Api.makeRequest('mteacher/signlog', params)
  }

  static scan(params) {
    return Api.makeRequest('mteacher/scan', params, 'POST')
  }
  static getCalendarDate(params) {
    return Api.makeRequest('mteacher/getcalendardate', params)
  }
  static getCourseByDay(params) {
    return Api.makeRequest('mteacher/getcoursebyday', params)
  }
  static givePoints(params) {
    return Api.makeRequest('mteacher/givepoint', params, 'POST')
  }
  static getMembers(params) {
    return Api.makeRequest('mteacher/getmembers', params)
  }
  static getHomework(params) {
    return Api.makeRequest('mteacher/homework', params)
  }
  static saveHomework(params) {
    return Api.makeRequest('mteacher/savehomework', params, 'POST')
  }
  // course相关
  static getCounrses(params) {
    return Api.makeRequest('mcourse/listbyweek', params)
  }
  static getCounrseList(params) {
    return Api.makeRequest('mpage/courselist', params)
  }
  static getCounrse(params) {
    return Api.makeRequest('mcourse/detail', params)
  }
  static getMyCourses(params) {
    return Api.makeRequest('mcenter/courses', params)
  }
  static getMySigns(params) {
    return Api.makeRequest('mcenter/signs', params)
  }
  static getQrcode(params) {
    return Api.makeRequest('mcourse/qrcode', params)
  }
  static getCourseDays(params) {
    return Api.makeRequest('mcourse/calendardays', params)
  }
  static getCourseSignByDay(params) {
    return Api.makeRequest('mcourse/getbyday', params)
  }
  static handleSign(params) {
    return Api.makeRequest('mcourse/sign', params, 'POST')
  }
  // 圈子相关
  static getZone(params) {
    return Api.makeRequest('mpage/zone',params)
  }
  static zoneLike(params) {
    return Api.makeRequest('mzone/like',params, 'POST')
  }
  // product相关
  static getTypeAndProperties() {
    return Api.makeRequest('mproduct/properties')
  }
  static getProducts(params) {
    return Api.makeRequest('mproduct/list',params)
  }
  static getProduct(params) {
    return Api.makeRequest('mproduct/detail',params)
  }
  static getProductRel() {
    return Api.makeRequest('mproduct/propertierel')
  }
  static getProductCategories() {
    return Api.makeRequest('mproduct/categories')
  }
  // 订单相关
  static getMyOrders(params) {
    return Api.makeRequest('mcenter/orders', params)
  }
  static makeOrder(params) {
    return Api.makeRequest('morder/courseconfirm', params, 'POST')
  }
  static makeOrderByProduct(params) {
    return Api.makeRequest('morder/productconfirm', params, 'POST')
  }
  static cancelOrder(params) {
    return Api.makeRequest('morder/cancelorder', params, 'POST')
  }
  static payOrder(params) {
    return Api.makeRequest('morder/pay', params, 'POST')
  }
  static refundOrder(params) {
    return Api.makeRequest('morder/refundorder', params, 'POST')
  }
  // 代金券
  static getVouchers(params) {
    return Api.makeRequest('mcenter/vouchers', params);
  }
  static getVoucher(params) {
    return Api.makeRequest('mcenter/getvoucher', params);
  }
  static getRedPacket(params) {
    return Api.makeRequest('mcenter/redpacket', params);
  }
  static getRedPacketFriends(params) {
    return Api.makeRequest('mcenter/redpacketfriends', params);
  }
  // 扫码后推荐人获得红包
  static makeRedPacketByShare(params) {
    return Api.makeRequest('mcenter/makeredpacketbyshare', params, "POST");
  }
  // user
  static getUsers(params) {
    return Api.makeRequest('muser/list',params);
  }
  static getUser(uname) {
    return Api.makeRequest('muser/profile',{uname: uname});
  }
  static handleAllograph(params) {
    return Api.makeRequest('mteacher/allograph', params, 'POST')
  }
  // 广告图
  static getAdv(params) {
    return Api.makeRequest('madv/find',params) // 可以接收id 和 position
  }
  static async getOneAdv(params) {
    let ad = await Api.makeRequest('madv/find',params) // 可以接收id 和 position
    return ad.data[0];
  }
  static getNotifications(params) {
    return Api.makeRequest('madv/notifications',params) // 可以接收id
  }
  // 充值
  static topupStage() {
    return Api.makeRequest('mcenter/topupstage') // 可以接收id 和 position
  }
  static topup(params) {
    return Api.makeRequest('mcenter/topup',params,'POST') // 可以接收id 和 position
  }
  // division相关
  static getRegions() {
    return Api.makeRequest('mdivision/tree')
  }
  static getDivisions(params) {
    return Api.makeRequest('mdivision/list', params)
  }
  static getDivision(params) {
    return Api.makeRequest('mdivision/detail', params)
  }
  static addCollection(params) {
    return Api.makeRequest('mcenter/addcollection', params)
  }
  static removeCollection(params) {
    return Api.makeRequest('mcenter/removecollection', params)
  }
  // member center
  static getMemberCenterInfo() {
    return Api.makeRequest('mcenter/index')
  }
  static getBadges() {
    return Api.makeRequest('mcenter/badges')
  }
  static getShareQr(params) {
    return Api.makeRequest('mcenter/shareqr', params)
  }
  /* shop部分 */
  static getGoods(params) {
    return Api.makeRequest('mpage/goods', params)
  }
  static cartConfirm(params) {
    return Api.makeRequest('mshop/confirm', params, 'POST')
  }
  static getAddress(params) {
    return Api.makeRequest('mshop/address', params)
  }
  static saveAddress(params) {
    return Api.makeRequest('mshop/saveaddress', params, 'POST')
  }
  static delAddress(params) {
    return Api.makeRequest('mshop/deladdress', params, 'POST')
  }
  static makeGoodsOrder(params) {
    return Api.makeRequest('mshop/makeorder', params, 'POST')
  }
  static getGoodsOrder(params) {
    return Api.makeRequest('mshop/orders', params)
  }
  /* Gift部分 */
  static getGifts(params) {
    return Api.makeRequest('mpage/gifts', params)
  }
  static getGiftCategory() {
    return Api.makeRequest('mpage/giftcategories')
  }
  static exchangeGift(params) {
    return Api.makeRequest('mgift/exchange',params, 'POST')
  }
  static getGiftOrders(params) {
    return Api.makeRequest('mgift/orders',params)
  }

  // 上传请求
  static async uploadFile(tempFilePath, fileName, otherParams) {
    var domain = getDomain();
    let res = await wepy.uploadFile({
      url: domain + 'mnormal/uploader',
      filePath: tempFilePath,
      name: fileName,
      formData: otherParams || {},
      header: {'Authorization': 'Bearer ' + Auth.getToken()}
    })
    return JSON.parse(res.data)
  }
  static makeRequest(url, params, method = 'GET') {
    var domain = getDomain();
    Tips.loading()
    return new Promise((resolve, reject) => {
      wepy.request({
        url: domain + url,
        data: params || {},
        method: method
      }).then((res) => {
        Tips.hideLoading()
        Api.handleError(res.data);
        resolve(res.data)
      }).catch(err => {
        Tips.hideLoading()
        Tips.alert(err.msg);
      })
    })
  }
  static makeServiceRequest(url, params, method = 'GET') {
    Tips.loading()
    return new Promise((resolve, reject) => {
      wepy.request({
        url: Api.serviceDomain + url,
        data: params || {},
        method: method
      }).then((res) => {
        Tips.hideLoading()
        resolve(res.data)
      }).catch(err => {
        Tips.hideLoading()
        Tips.alert(err.msg);
      })
    })
  }
  static handleError(data) {
    if (data.status == 'error') {
      Tips.alert(data.msg)
        // , () => {
        // setTimeout(() => {
        //   if (typeof cb === 'function') {
        //     cb()
        //   }
        // }, 1000) }
    }
  }
  static wxPay(payment) {
    return WxUtils.wxPay({ 
      timeStamp: payment.timestamp,
      nonceStr: payment.nonceStr,   //字符串随机数
      package: payment.package,
      signType: payment.signType,
      paySign: payment.paySign,
    });
  }
  // 服务API
  static async makeAdvice(content,info) {
    await Api.makeServiceRequest('advice', {content:content, info:info, platform: 'miniapp'}, 'POST');
    Tips.alert('谢谢反馈!');
  }
  static checkOrgcodeExist(params) {
    return Api.makeServiceRequest('checkorgcodeexist', params, 'POST');
  }
  static checkVersion(version) {
    return Api.makeServiceRequest('checkversion', {version});
  }
  static getTerm(params) {
    return Api.makeServiceRequest('memberterm', params);
  }
  static getInitData(params) {
    return Api.makeServiceRequest('initdata', params);
  }
  // 防止重复提交保存token
  // 用法onLoad里添加这个方法，然后在提交数据里增加_token参数到后台 Api.formData._token = await api.getMreqToken(flagName)
  // flagname的作用是区分表单储存到缓存
  // async getMreqToken(flagName) {
  //   let res = await Api.makeRequest('mnormal/mreqtoken')
  //   if (res.status === 'success') {
  //     Api.storeMreqToken(flagName, res.data)
  //     return res.data
  //   }
  // },
  // storeMreqToken(flagName, value) {
  //   let oldToken = wepy.getStorageSync('__token__')
  //   oldToken = Object.assign({}, oldToken, {
  //     [`${flagName}`]: value
  //   })
  //   wepy.setStorageSync('__token__', oldToken)
  // },
  // destryCsrfToken(flagName) {
  //   let oldToken = wepy.getStorageSync('__token__')
  //   delete oldToken[flagName]
  //   wepy.setStorageSync('__token__', oldToken)
  // }
}
function handleLoginRes(res) {
  if (res.status === 'success' && res.data.token ) {
    wepy.setStorageSync('token', res.data.token)     // 再次储存一下token
    // wepy.setStorageSync('uinfo', res.data.userInfo)     // 储存返回的userinfo
  } else {
    return false
  }
}