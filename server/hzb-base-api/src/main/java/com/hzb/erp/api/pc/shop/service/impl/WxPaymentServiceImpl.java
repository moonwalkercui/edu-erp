package com.hzb.erp.api.pc.shop.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hzb.erp.api.pc.shop.entity.Order;
import com.hzb.erp.api.pc.shop.mapper.OrderItemMapper;
import com.hzb.erp.api.pc.shop.service.OrderRefundService;
import com.hzb.erp.api.pc.shop.service.OrderService;
import com.hzb.erp.wechat.entiry.WxOrder;
import com.hzb.erp.wechat.entiry.WxOrderRefund;
import com.hzb.erp.wechat.service.WxPaymentService;
import com.hzb.erp.common.enums.OrderStateEnum;
import com.hzb.erp.configuration.SystemConfig;
import com.hzb.erp.exception.BizException;
import com.hzb.erp.utils.IpUtil;
import com.hzb.erp.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WxPaymentServiceImpl implements WxPaymentService {

    private final WxPayService wxPayService;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
    private final OrderRefundService orderRefundService;
    private final SystemConfig systemConfig;
    /**
     * 支付参数
     * 微信官方文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * */
    @Override
    public WxPayUnifiedOrderRequest buildPayParamByOrder(WxOrder order, String openid, String tradeType) {
        Order findOrder = orderService.getById(order.getId());
        if(!findOrder.getState().equals(OrderStateEnum.UNPAID)) {
            throw new BizException(findOrder.getState().getDist() + "的订单无法支付，请返回重试");
        }
        String notifyUrl;
        String createIp;
        try {
            HttpServletRequest request = RequestUtil.getRequest();
            notifyUrl = getPayNotifyUrl(request);
            createIp = IpUtil.getIpAddr(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        BigDecimal payMoney;
        if(systemConfig.getIsDemo()) {
            payMoney = new BigDecimal("0.1"); // todo DEMO演示模式会用1毛钱 ，注意 !!!!!!!!!!!!!!
        } else {
            payMoney = order.getOrderMoney() == null ? BigDecimal.ZERO : order.getOrderMoney();
        }
        orderRequest.setOpenid(openid);
        orderRequest.setBody("订单号"+order.getSn());
        orderRequest.setOutTradeNo(order.getSn());
        orderRequest.setTotalFee((new BigDecimal(100).multiply(payMoney).intValue()));
        orderRequest.setSpbillCreateIp(createIp);
        orderRequest.setNotifyUrl(notifyUrl);
        orderRequest.setTradeType(tradeType);
        return orderRequest;
    }

    /**
    * 退款参数
    * 微信官方文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
    * */
    @Override
    public WxPayRefundRequest buildRefundParamByOrderRefund(WxOrderRefund orderRefund) {
        BigDecimal refundMoney = orderRefund.getRefundMoney() == null ? BigDecimal.ZERO : orderRefund.getRefundMoney();
        if (BigDecimal.ZERO.compareTo(refundMoney) >= 0) {
            throw new BizException("推荐金额有误");
        }
        Order order = orderService.getById(orderRefund.getOrderId());

        String notifyUrl;
        try {
            HttpServletRequest request = RequestUtil.getRequest();
            notifyUrl = getRefundNotifyUrl(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        WxPayRefundRequest refundRequest = new WxPayRefundRequest();
        refundRequest.setNotifyUrl(notifyUrl);
        refundRequest.setOutTradeNo(order.getSn());
        refundRequest.setOutRefundNo(orderRefund.getRefundSn());
        refundRequest.setSignType("MD5");
        refundRequest.setTotalFee(new BigDecimal(100).multiply(order.getPayMoney()).intValue());
        refundRequest.setRefundFee(new BigDecimal(100).multiply(refundMoney).intValue());
        return refundRequest;
    }

    @Override
    public void payNotify(String xmlData) throws WxPayException {
        log.info("==============支付回调参数===================");
        final WxPayOrderNotifyResult result = this.wxPayService.parseOrderNotifyResult(xmlData);
        log.info(String.valueOf(result));
        orderService.paySuccessNotify(result.getOpenid(), result.getOutTradeNo(), result.getTransactionId(), result.getTotalFee());
        log.info("==============支付回调结束===================");

    }

    @Override
    public void refundNotify(String xmlData) throws WxPayException {
        log.info("==============退款回调参数===================");
        final WxPayRefundNotifyResult result = this.wxPayService.parseRefundNotifyResult(xmlData);
        log.info(String.valueOf(result));
        WxPayRefundNotifyResult.ReqInfo reqInfo = result.getReqInfo();
        orderRefundService.refundSuccessNotify(reqInfo.getOutRefundNo(), reqInfo.getRefundId(), new BigDecimal(reqInfo.getTotalFee() / 100));
    }

    /**
     * 支付回调地址
     */
    private String getPayNotifyUrl(HttpServletRequest request) throws MalformedURLException {
        URL requestUrl = new URL(request.getRequestURL().toString());
        return request.getScheme() + "://" + requestUrl.getHost() + "/app/wx/pay/payNotify";
    }

    /**
     * 退款回调地址 注意:微信官方要求退款通知需要使用 https !!!
     * 这个可以不用配置，那么就使用商户号后台配置的通知地址了
     */
    private String getRefundNotifyUrl(HttpServletRequest request) throws MalformedURLException {
        URL requestUrl = new URL(request.getRequestURL().toString());

        return request.getScheme() + "://" + requestUrl.getHost() + "/app/wx/pay/refundNotify";
    }

}
