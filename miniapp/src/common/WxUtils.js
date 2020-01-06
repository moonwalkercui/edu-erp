export default class WxUtils {
    static wxPay(param) {
        return new Promise((resolve, reject) => {
            wx.requestPayment({
                ...param,
                complete: res => {
                    if (res.errMsg == 'requestPayment:ok') {
                        resolve(res);
                    } else {
                        reject(res);
                    }
                }
            });
        });
    }
}
