export const debug = false;
export const baseUrl = debug ? 'http://erp.hzb-it.com/' : (window.location.protocol +'//'+window.location.host + '/');

export const paymentType = {
  //支付单类型
  order: 1, //订单
  recharge: 2, //充值
  form_order: 5, //表单付款码
  form_pay: 6 //表单订单
}
